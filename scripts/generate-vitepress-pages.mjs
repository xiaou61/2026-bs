/**
 * 从 readme.md + 源码数据 + 检查报告 + 项目README 生成 VitePress 页面
 * 用法: node scripts/generate-vitepress-pages.mjs
 */
import fs from 'fs';
import path from 'path';
import { fileURLToPath } from 'url';

const __dirname = path.dirname(fileURLToPath(import.meta.url));
const ROOT = path.resolve(__dirname, '..');
const README_PATH = path.join(ROOT, 'readme.md');
const DOCS_SITE_DIR = path.join(ROOT, 'docs-site');
const PROJECTS_DIR = path.join(DOCS_SITE_DIR, 'projects');
const PREVIEW_ASSETS_SOURCE_DIR = path.join(ROOT, 'docs', 'previews', 'assets');
const PREVIEW_ASSETS_PUBLIC_DIR = path.join(DOCS_SITE_DIR, 'public', 'previews', 'assets');

// 确保目录存在
if (!fs.existsSync(PROJECTS_DIR)) fs.mkdirSync(PROJECTS_DIR, { recursive: true });

// 将历史截图同步到 VitePress public 目录，生成后的页面可通过 /previews/assets/... 访问。
function syncPreviewAssets() {
  if (!fs.existsSync(PREVIEW_ASSETS_SOURCE_DIR)) return;
  const imageExts = new Set(['.png', '.jpg', '.jpeg', '.webp']);

  function copyFileWithRetry(sourcePath, targetPath) {
    let lastError;
    for (let attempt = 0; attempt < 3; attempt += 1) {
      try {
        fs.copyFileSync(sourcePath, targetPath);
        return;
      } catch (error) {
        lastError = error;
        const waitUntil = Date.now() + 150 * (attempt + 1);
        while (Date.now() < waitUntil) {
          // Small synchronous backoff for transient Windows copyfile locks.
        }
      }
    }
    throw lastError;
  }

  function copyImages(sourceDir, targetDir) {
    fs.mkdirSync(targetDir, { recursive: true });
    for (const entry of fs.readdirSync(sourceDir, { withFileTypes: true })) {
      const sourcePath = path.join(sourceDir, entry.name);
      const targetPath = path.join(targetDir, entry.name);
      if (entry.isDirectory()) {
        copyImages(sourcePath, targetPath);
      } else if (entry.isFile() && imageExts.has(path.extname(entry.name).toLowerCase())) {
        copyFileWithRetry(sourcePath, targetPath);
      }
    }
  }

  copyImages(PREVIEW_ASSETS_SOURCE_DIR, PREVIEW_ASSETS_PUBLIC_DIR);
}

syncPreviewAssets();

// HTML 转义（防止 Vue 编译错误）
function escapeHtml(str) {
  return str.replace(/</g, '&lt;').replace(/>/g, '&gt;');
}

function getProjectScreenshots(number) {
  const dir = path.join(PREVIEW_ASSETS_PUBLIC_DIR, number);
  if (!fs.existsSync(dir)) return [];
  const imageExts = new Set(['.png', '.jpg', '.jpeg', '.webp']);
  return fs.readdirSync(dir)
    .filter(file => imageExts.has(path.extname(file).toLowerCase()))
    .sort((a, b) => a.localeCompare(b, 'zh-CN', { numeric: true }))
    .map(file => ({
      file,
      stem: path.basename(file, path.extname(file)),
      role: path.basename(file, path.extname(file)).split('-', 1)[0] || 'preview',
      url: `/previews/assets/${number}/${encodeURIComponent(file)}`,
    }));
}

function roleDisplayName(role) {
  const names = {
    guest: '访客',
    admin: '管理员',
    teacher: '教师',
    student: '学生',
    farmer: '农户',
    buyer: '买家',
    user: '用户',
    user1: '用户1',
    user2: '用户2',
    zhangsan: '张三',
    lisi: '李四',
  };
  return names[role] || role;
}

function buildScreenshotSection(number) {
  const screenshots = getProjectScreenshots(number);
  if (screenshots.length === 0) return '';

  const groups = new Map();
  for (const shot of screenshots) {
    if (!groups.has(shot.role)) groups.set(shot.role, []);
    groups.get(shot.role).push(shot);
  }

  let md = `## 项目截图\n\n`;
  md += `已收录 **${screenshots.length}** 张运行截图。\n\n`;
  for (const [role, files] of groups) {
    md += `### ${roleDisplayName(role)}\n\n`;
    for (const shot of files) {
      md += `#### ${escapeHtml(shot.stem)}\n\n`;
      md += `![${escapeHtml(shot.stem)}](${shot.url})\n\n`;
    }
  }
  return md;
}

// Smart SQL value splitter - respects quoted strings and nested parentheses
function smartSplit(str) {
  const result = [];
  let current = '';
  let inQuote = false;
  let quoteChar = '';
  let parenDepth = 0;
  for (let i = 0; i < str.length; i++) {
    const ch = str[i];
    if (inQuote) {
      current += ch;
      if (ch === quoteChar) inQuote = false;
    } else if (ch === "'" || ch === '"') {
      inQuote = true;
      quoteChar = ch;
      current += ch;
    } else if (ch === '(') {
      parenDepth++;
      current += ch;
    } else if (ch === ')') {
      parenDepth--;
      current += ch;
    } else if (ch === ',' && parenDepth === 0) {
      result.push(current.trim());
      current = '';
    } else {
      current += ch;
    }
  }
  if (current.trim()) result.push(current.trim());
  return result;
}

// 加载源码提取数据
const SOURCE_DATA_PATH = path.join(DOCS_SITE_DIR, '.vitepress', 'source-data.json');
const sourceData = fs.existsSync(SOURCE_DATA_PATH)
  ? JSON.parse(fs.readFileSync(SOURCE_DATA_PATH, 'utf-8'))
  : {};

const CHECKS_DIR = path.join(ROOT, 'docs', 'checks');

// === 从项目源码目录提取真实文档信息 ===
function extractProjectDocs(number) {
  const result = {
    projectStructure: '',      // 后端项目结构代码块
    apiDetails: [],             // API 接口分组详情 [{group, apis: [{method, path, desc}]}]
    roles: [],                  // 角色 [{name, permissions: [string]}]
    techHighlights: [],         // 技术亮点
    backendDesc: '',            // 后端项目简介
    frontendDesc: '',           // 前端项目简介
    defaultAccounts: [],        // 默认账号 [{role, username, password, note}]
    frontendPages: [],          // 前端页面列表 [{name, path}]
    frontendApiCount: {},       // 前端API封装统计
    devNotes: [],               // 开发说明/注意事项
  };

  // --- 读取后端 README ---
  const backendReadmePath = path.join(ROOT, `${number}-backend`, 'README.md');
  if (fs.existsSync(backendReadmePath)) {
    const content = fs.readFileSync(backendReadmePath, 'utf-8').replace(/\r\n?/g, '\n');

    // 提取项目简介
    const descMatch = content.match(/## (?:项目简介|项目说明|项目概述|项目介绍|项目定位|简介|说明|概述|介绍|项目描述)\s*\n([\s\S]*?)(?=\n## )/);
    if (descMatch) result.backendDesc = descMatch[1].trim();

    // 如果没有显式简介，提取第一个 ## 之前的描述性文本
    if (!result.backendDesc) {
      const firstH2 = content.indexOf('\n## ');
      if (firstH2 > 10) {
        const preH2 = content.substring(0, firstH2).replace(/^#\s+.+\n?/, '').trim();
        if (preH2.length > 15) result.backendDesc = preH2.substring(0, 300);
      }
    }

    // 提取项目结构（代码块）
    const structMatch = content.match(/## 项目结构\s*\n```\n([\s\S]*?)```/);
    if (structMatch) result.projectStructure = structMatch[1].trim();

    // 提取API接口详情
    const apiSectionMatch = content.match(/## API接口\s*\n([\s\S]*?)(?=\n## )/);
    if (apiSectionMatch) {
      const apiSection = apiSectionMatch[1];
      // 按模块分组
      const groupRegex = /### (.+?)\s*\n([\s\S]*?)(?=### |$)/g;
      let gMatch;
      while ((gMatch = groupRegex.exec(apiSection)) !== null) {
        const groupName = gMatch[1].trim();
        const groupContent = gMatch[2];
        const apis = [];
        // 格式1: - `GET /path` - 描述（有反引号）
        const lineRegex = /^-\s+`(GET|POST|PUT|DELETE|ALL|PATCH)\s+([^`]+)`\s*[-–—]?\s*(.*)$/gm;
        let lMatch;
        while ((lMatch = lineRegex.exec(groupContent)) !== null) {
          apis.push({ method: lMatch[1], path: lMatch[2].trim(), desc: lMatch[3].trim() });
        }
        // 格式2: 表格格式 | GET | /path | 描述 |
        if (apis.length === 0) {
          const tableRegex = /\|\s*(GET|POST|PUT|DELETE|ALL|PATCH)\s*\|\s*`?([^`|\n]+)`?\s*\|/g;
          let tMatch;
          while ((tMatch = tableRegex.exec(groupContent)) !== null) {
            apis.push({ method: tMatch[1], path: tMatch[2].trim(), desc: '' });
          }
        }
        // 格式3: - METHOD /path - 描述（无反引号）
        if (apis.length === 0) {
          const plainLineRegex = /^-\s+(GET|POST|PUT|DELETE|PATCH)\s+(\S+)\s*[-–—]?\s*(.*)$/gm;
          let plMatch;
          while ((plMatch = plainLineRegex.exec(groupContent)) !== null) {
            apis.push({ method: plMatch[1], path: plMatch[2].trim(), desc: plMatch[3].trim() });
          }
        }
        if (apis.length > 0) result.apiDetails.push({ group: groupName, apis });
      }
    }

    // 提取权限/角色信息 - 格式1: ## 权限说明
    const permMatch = content.match(/## 权限说明\s*\n([\s\S]*?)(?=\n## )/);
    if (permMatch) {
      const permSection = permMatch[1];
      const roleRegex = /-\s+\*\*(.+?)\*\*\s*[:：]\s*(.+)/g;
      let rMatch;
      while ((rMatch = roleRegex.exec(permSection)) !== null) {
        result.roles.push({ name: rMatch[1], permissions: [rMatch[2].trim()] });
      }
    }
    // 格式2: ## 系统功能 + ### 学生功能
    if (result.roles.length === 0) {
      const funcMatch2 = content.match(/## 系统功能\s*\n([\s\S]*?)(?=\n## |$)/);
      if (funcMatch2) {
        const subRegex = /### (.+?)\s*\n([\s\S]*?)(?=### |$)/g;
        let sMatch;
        while ((sMatch = subRegex.exec(funcMatch2[1])) !== null) {
          const perms = [];
          for (const line of sMatch[2].split('\n')) {
            const p = line.replace(/^\d+\.\s*/, '').trim();
            if (p.length > 0) perms.push(p);
          }
          if (perms.length > 0) result.roles.push({ name: sMatch[1].replace(/功能$/, ''), permissions: perms });
        }
      }
    }
    // 格式3: ## 核心功能/角色权限/角色说明/功能概览/用户角色 + ### 子标题
    if (result.roles.length === 0) {
      const coreFuncMatch = content.match(/## (?:核心功能|角色权限|角色说明|功能概览|用户角色)\s*\n([\s\S]*?)(?=\n## |$)/);
      if (coreFuncMatch) {
        const subRegex = /### (.+?)\s*\n([\s\S]*?)(?=### |$)/g;
        let sMatch;
        while ((sMatch = subRegex.exec(coreFuncMatch[1])) !== null) {
          const roleName = sMatch[1].replace(/功能$/, '').replace(/端$/, '').trim();
          const perms = [];
          for (const line of sMatch[2].split('\n')) {
            const p = line.replace(/^-\s*\*?\*?/, '').replace(/\*\*\s*[:：]\s*/, ' - ').replace(/^[✅❌]\s*/, '').trim();
            if (p.length > 0 && !p.startsWith('#') && !p.startsWith('|')) perms.push(p);
          }
          if (perms.length > 0) result.roles.push({ name: roleName, permissions: perms });
        }
      }
    }

    // 提取技术亮点
    const hlMatch = content.match(/## (?:技术亮点|特色|项目特色)\s*\n([\s\S]*?)(?=\n## )/);
    if (hlMatch) {
      for (const line of hlMatch[1].split('\n')) {
        const h = line.replace(/^-\s*/, '').replace(/\*\*/g, '').trim();
        if (h.length > 0 && !h.startsWith('#') && !h.startsWith('|')) result.techHighlights.push(h);
      }
    }

    // 提取开发说明/注意事项
    const notesMatch = content.match(/## (?:注意事项|开发说明|注意[^意]|部署说明|使用说明|运行说明|环境说明|配置说明|安装说明|开发文档|常见问题|部署文档|启动说明|运行文档|部署|快速开始|快速启动|运行部署|开发配置|环境与配置|开发与部署|项目配置)\s*\n([\s\S]*?)(?=\n## |$)/);
    if (notesMatch) {
      for (const line of notesMatch[1].split('\n')) {
        const n = line.replace(/^\d+\.\s*/, '').replace(/^-\s*/, '').trim();
        if (n.length > 0 && !n.startsWith('#') && !n.startsWith('|')) result.devNotes.push(n);
      }
    }

    // 提取测试账号 - 多种格式
    // 检查是否指向 ACCOUNTS.md，如果是则跳过让后面处理
    const accSectionMatch = content.match(/## (?:测试账号|默认账号|账号)[^\n]*\n([\s\S]*?)(?=\n## |$)/);
    if (accSectionMatch) {
      const accSection = accSectionMatch[1];
      if (accSection.includes('ACCOUNTS.md') || accSection.includes('accounts')) {
        // 即使引用了ACCOUNTS.md，也尝试提取表格数据（README中可能同时有表格和引用）
        const row3Regex = /\|\s*([^|\n]+?)\s*\|\s*([^|\n]+?)\s*\|\s*([^|\n]+?)\s*\|/g;
        let r3Match;
        while ((r3Match = row3Regex.exec(accSection)) !== null) {
          const role = r3Match[1].trim();
          const username = r3Match[2].trim();
          const password = r3Match[3].trim();
          if (role !== '角色' && role !== '类型' && role !== '---' && !role.startsWith('-') && !role.startsWith('|')) {
            result.defaultAccounts.push({ role, username, password, note: '' });
          }
        }
      } else {
        // 格式1a: 4列表格 | 角色 | 用户名 | 密码 | 说明 |（单行匹配）
        const row4Regex = /\|\s*([^|\n]+?)\s*\|\s*([^|\n]+?)\s*\|\s*([^|\n]+?)\s*\|\s*([^|\n]+?)\s*\|/g;
        let rMatch;
        while ((rMatch = row4Regex.exec(accSection)) !== null) {
          const role = rMatch[1].trim();
          const username = rMatch[2].trim();
          const password = rMatch[3].trim();
          const note = rMatch[4].trim();
          if (role !== '角色' && role !== '类型' && role !== '---' && !role.startsWith('-')) {
            result.defaultAccounts.push({ role, username, password, note });
          }
        }
        // 格式1b: 3列表格 | 角色 | 用户名 | 密码 |（单行匹配）
        if (result.defaultAccounts.length === 0) {
          const row3Regex = /\|\s*([^|\n]+?)\s*\|\s*([^|\n]+?)\s*\|\s*([^|\n]+?)\s*\|/g;
          let r3Match;
          while ((r3Match = row3Regex.exec(accSection)) !== null) {
            const role = r3Match[1].trim();
            const username = r3Match[2].trim();
            const password = r3Match[3].trim();
            if (role !== '角色' && role !== '类型' && role !== '---' && !role.startsWith('-') && !role.startsWith('|')) {
              result.defaultAccounts.push({ role, username, password, note: '' });
            }
          }
        }
        // 格式2: - **管理员**: admin / 123456
        if (result.defaultAccounts.length === 0) {
          const lineRegex = /-\s+\*\*(.+?)\*\*[:：]\s*(\S+)\s*\/\s*(\S+)/g;
          let lMatch;
          while ((lMatch = lineRegex.exec(accSection)) !== null) {
            result.defaultAccounts.push({ role: lMatch[1], username: lMatch[2], password: lMatch[3], note: '' });
          }
        }
        // 格式3: - 管理员: admin / 123456（无加粗）
        if (result.defaultAccounts.length === 0) {
          const plainLineRegex = /-\s+(.+?)[:：]\s*(\S+)\s*\/\s*(\S+)/g;
          let pMatch;
          while ((pMatch = plainLineRegex.exec(accSection)) !== null) {
            if (pMatch[1].trim().length < 20) { // 角色名不会太长
              result.defaultAccounts.push({ role: pMatch[1].trim(), username: pMatch[2], password: pMatch[3], note: '' });
            }
          }
        }
        // 格式4: 键值对格式 - 手机号/账号/用户名: xxx / - 密码: xxx（分两行）
        if (result.defaultAccounts.length === 0) {
          const kvLines = accSection.split('\n');
          let currentUsername = '', currentPassword = '', currentRole = '';
          for (const line of kvLines) {
            const uMatch = line.match(/^-\s*\*{0,2}\s*(?:手机号|账号|用户名|学号|账户)\s*\*{0,2}\s*[：:]\s*(\S+)/);
            const pMatch = line.match(/^-\s*\*{0,2}\s*密码\s*\*{0,2}\s*[：:]\s*(\S+)/);
            const rMatch = line.match(/^-\s*\*{0,2}\s*角色\s*\*{0,2}\s*[：:]\s*(.+)/);
            if (uMatch) currentUsername = uMatch[1];
            if (pMatch) currentPassword = pMatch[1];
            if (rMatch) currentRole = rMatch[1].trim();
            // If we have both username and password, save
            if (currentUsername && currentPassword) {
              result.defaultAccounts.push({ role: currentRole || (currentUsername === 'admin' ? '管理员' : '用户'), username: currentUsername, password: currentPassword, note: '' });
              currentUsername = ''; currentPassword = ''; currentRole = '';
            }
          }
        }
      }
    }
  }

  // 如果仍然没有角色信息，从默认账号推导角色
  if (result.roles.length === 0 && result.defaultAccounts.length > 0) {
    const roleSet = new Set();
    for (const acc of result.defaultAccounts) {
      if (acc.role && acc.role.length < 20 && !roleSet.has(acc.role)) {
        roleSet.add(acc.role);
        result.roles.push({ name: acc.role, permissions: ['基本操作权限'] });
      }
    }
  }

  // --- 读取 ACCOUNTS.md（独立账号文档） ---
  const accountsPath = path.join(ROOT, `${number}-backend`, 'ACCOUNTS.md');
  if (fs.existsSync(accountsPath) && result.defaultAccounts.length === 0) {
    const content = fs.readFileSync(accountsPath, 'utf-8').replace(/\r\n?/g, '\n');

    // 提取角色权限（从 ## xxx账号 标题推导）
    if (result.roles.length === 0) {
      const roleHeadings = [...content.matchAll(/(?:###|##) (.+?(?:账号|账户|用户))[^\n]*\n/g)];
      for (const rh of roleHeadings) {
        const roleName = rh[1].replace(/账号.*$/, '').replace(/账户.*$/, '').replace(/用户.*$/, '').trim();
        if (roleName && roleName.length < 20 && roleName.length > 0) {
          result.roles.push({ name: roleName, permissions: ['基本操作权限'] });
        }
      }
      // Also try ## 权限说明
      const permMatch = content.match(/## 权限说明\s*\n([\s\S]*?)(?=\n## )/);
      if (permMatch) {
        result.roles = []; // reset to use actual permissions
        const roleRegex = /-\s+\*\*(.+?)\*\*[：:]\s*(.+)/g;
        let rMatch;
        while ((rMatch = roleRegex.exec(permMatch[1])) !== null) {
          result.roles.push({ name: rMatch[1], permissions: rMatch[2].split(/[,，、]/).map(s => s.trim()).filter(Boolean) });
        }
      }
    }

    // 提取测试账号 - 格式A: ###/## xxx账号 / - 用户名：xxx / - 密码：xxx
    const accSubsections = content.match(/(?:###|##) (.+?(?:账号|账户|用户)[^\n]*)\s*\n([\s\S]*?)(?=### |## |$)/g);
    if (accSubsections) {
      for (const section of accSubsections) {
        const titleMatch = section.match(/(?:###|##) (.+?)\s*\n/);
        const body = section.replace(/(?:###|##) .+?\s*\n/, '');
        const usernameMatch = body.match(/^[-]\s*\*{0,2}\s*(?:用户名|账号|手机号|学号|账户)\s*\*{0,2}\s*[：:]\s*(\S+)/m);
        const passwordMatch = body.match(/^[-]\s*\*{0,2}\s*密码\s*\*{0,2}\s*[：:]\s*(\S+)/m);
        const roleMatch = body.match(/^[-]\s*\*{0,2}\s*角色\s*\*{0,2}\s*[：:]\s*(.+)/m);
        const permMatch2 = body.match(/^[-]\s*\*{0,2}\s*权限\s*\*{0,2}\s*[：:]\s*(.+)/m);
        if (usernameMatch && passwordMatch) {
          const roleName = roleMatch ? roleMatch[1].trim() : titleMatch[1].replace(/(?:账号|账户|用户).*$/, '').trim();
          result.defaultAccounts.push({
            role: roleName,
            username: usernameMatch[1],
            password: passwordMatch[1],
            note: permMatch2 ? permMatch2[1].trim() : ''
          });
        }
      }
    }
    // 格式A2: ## 角色/身份标题（不含账号关键词）+ - 用户名: xxx / - 密码: xxx 键值对
    if (result.defaultAccounts.length === 0) {
      const kvSubsections = content.match(/(?:###|##) ([^\n]+)\s*\n([\s\S]*?)(?=### |## |$)/g);
      if (kvSubsections) {
        for (const section of kvSubsections) {
          const titleMatch = section.match(/(?:###|##) (.+?)\s*\n/);
          const title = titleMatch[1].trim();
          // Skip non-role headings (测试数据, 功能测试, 系统配置, etc.)
          if (/(?:测试数据|功能测试|系统配置|API|注意事项|说明|配置)/.test(title)) continue;
          const body = section.replace(/(?:###|##) .+?\s*\n/, '');
          const usernameMatch = body.match(/^[-]\s*\*{0,2}\s*(?:用户名|账号|手机号|学号|账户)\s*\*{0,2}\s*[：:]\s*(\S+)/m);
          const passwordMatch = body.match(/^[-]\s*\*{0,2}\s*密码\s*\*{0,2}\s*[：:]\s*(\S+)/m);
          const roleMatch = body.match(/^[-]\s*\*{0,2}\s*角色\s*\*{0,2}\s*[：:]\s*(.+)/m);
          const permMatch2 = body.match(/^[-]\s*\*{0,2}\s*权限\s*\*{0,2}\s*[：:]\s*(.+)/m);
          if (usernameMatch && passwordMatch) {
            const roleName = roleMatch ? roleMatch[1].trim() : title.replace(/(?:账号|账户|用户).*$/, '').trim();
            result.defaultAccounts.push({
              role: roleName,
              username: usernameMatch[1],
              password: passwordMatch[1],
              note: permMatch2 ? permMatch2[1].trim() : ''
            });
          }
        }
      }
    }
    // 格式A3: Table format with any column order (e.g., | 用户名 | 密码 | 角色 | ...)
    if (result.defaultAccounts.length === 0) {
      const tableSection = content.match(/## [^\n]*账户[^\n]*\n([\s\S]*?)(?=\n## |$)/);
      if (tableSection) {
        // Parse header row to find column positions
        const headerLine = tableSection[1].split('\n').find(l => l.trim().startsWith('|') && !l.match(/^[\s|:-]+$/));
        if (headerLine) {
          const headers = headerLine.split('|').map(c => c.trim()).filter(c => c.length > 0);
          const usernameIdx = headers.findIndex(h => h === '用户名' || h === '账号' || h === '学号');
          const passwordIdx = headers.findIndex(h => h === '密码');
          const roleIdx = headers.findIndex(h => h === '角色' || h === '权限');
          if (usernameIdx >= 0 && passwordIdx >= 0) {
            const dataLines = tableSection[1].split('\n').filter(l => l.trim().startsWith('|') && !l.match(/^[\s|:-]+$/) && l !== headerLine);
            for (const line of dataLines) {
              const cells = line.split('|').map(c => c.trim());
              const username = cells[usernameIdx + 1] || '';  // +1 because split adds empty cell before first |
              const password = cells[passwordIdx + 1] || '';
              const role = roleIdx >= 0 ? (cells[roleIdx + 1] || '') : '';
              if (username && password && username.length < 50) {
                result.defaultAccounts.push({ role: role || (username === 'admin' ? '管理员' : '用户'), username, password, note: '' });
              }
            }
          }
        }
      }
    }
    // 格式B: "所有账号密码均为：xxx" + 列表
    if (result.defaultAccounts.length === 0) {
      const commonPwdMatch = content.match(/(?:所有|全部).*?密码.*?[均为：:]+\s*(\S+)/);
      if (commonPwdMatch) {
        const commonPwd = commonPwdMatch[1];
        const userMatches = [...content.matchAll(/[-]\s*([a-zA-Z]\w{2,})/g)];
        for (const um of userMatches) {
          const username = um[1];
          result.defaultAccounts.push({ role: username === 'admin' ? '管理员' : '用户', username, password: commonPwd, note: '' });
        }
      }
    }
    // 也尝试表格和行格式
    if (result.defaultAccounts.length === 0) {
      // 智能表格格式：先检测表头行来确定列含义
      const tableLines = content.split('\n').filter(l => l.trim().startsWith('|'));
      let headerLine = '';
      for (const tl of tableLines) {
        if (tl.includes('账号') || tl.includes('用户名') || tl.includes('密码') || tl.includes('角色') || tl.includes('学号') || tl.includes('手机号')) {
          headerLine = tl.toLowerCase();
          break;
        }
      }
      const tableCols = headerLine ? headerLine.split('|').map(c => c.trim().replace(/[-]/g, '')) : [];
      const usernameColIdx = tableCols.findIndex(c => c === '账号' || c === '用户名' || c === '学号' || c === '手机号');
      const passwordColIdx = tableCols.findIndex(c => c === '密码');
      const roleColIdx = tableCols.findIndex(c => c === '角色' || c === '类型');
      const noteColIdx = tableCols.findIndex(c => c === '说明' || c === '备注');

      if (usernameColIdx >= 0 && passwordColIdx >= 0) {
        // 使用检测到的列位置提取数据
        const rowRegex = /\|/g;
        for (const line of tableLines) {
          if (line.match(/^[\s|:-]+$/)) continue; // 跳过分隔线
          const cells = line.split('|').map(c => c.trim()).filter(Boolean);
          if (cells.length <= 1) continue;
          const username = cells[usernameColIdx] || '';
          const password = cells[passwordColIdx] || '';
          if (!username || !password || username === '账号' || username === '用户名' || username === '学号' || username.startsWith('-')) continue;
          const role = roleColIdx >= 0 && cells[roleColIdx] ? cells[roleColIdx] : '';
          const note = noteColIdx >= 0 && cells[noteColIdx] ? cells[noteColIdx] : '';
          if (username.length < 50 && password.length < 50) {
            result.defaultAccounts.push({ role: role || (username === 'admin' ? '管理员' : '用户'), username, password, note });
          }
        }
      } else {
        // 回退：默认 | 角色 | 用户名 | 密码 | 说明 | 格式
        const rowRegex = /\|\s*([^|\n]+?)\s*\|\s*([^|\n]+?)\s*\|\s*([^|\n]+?)\s*\|\s*([^|\n]+?)\s*\|/g;
        let rMatch;
        while ((rMatch = rowRegex.exec(content)) !== null) {
          const role = rMatch[1].trim();
          const username = rMatch[2].trim();
          const password = rMatch[3].trim();
          const note = rMatch[4].trim();
          if (role !== '角色' && role !== '---' && !role.startsWith('-')) {
            result.defaultAccounts.push({ role, username, password, note });
          }
        }
      }
    }
    if (result.defaultAccounts.length === 0) {
      const lineRegex = /-\s+\*\*(.+?)\*\*[:：]\s*(\S+)\s*\/\s*(\S+)/g;
      let lMatch;
      while ((lMatch = lineRegex.exec(content)) !== null) {
        result.defaultAccounts.push({ role: lMatch[1], username: lMatch[2], password: lMatch[3], note: '' });
      }
    }
    if (result.defaultAccounts.length === 0) {
      const plainLineRegex = /-\s+(.+?)[:：]\s*(\S+)\s*\/\s*(\S+)/g;
      let pMatch;
      while ((pMatch = plainLineRegex.exec(content)) !== null) {
        if (pMatch[1].trim().length < 20) {
          result.defaultAccounts.push({ role: pMatch[1].trim(), username: pMatch[2], password: pMatch[3], note: '' });
        }
      }
    }
  }

  // 如果仍然没有角色信息，从默认账号推导角色
  if (result.roles.length === 0 && result.defaultAccounts.length > 0) {
    const roleSet = new Set();
    for (const acc of result.defaultAccounts) {
      if (acc.role && acc.role.length < 20 && !roleSet.has(acc.role)) {
        roleSet.add(acc.role);
        result.roles.push({ name: acc.role, permissions: ['基本操作权限'] });
      }
    }
  }

  // --- 读取后端 PRD ---
  let prdPaths = [
    path.join(ROOT, `${number}-backend`, 'PRD', 'PRD.md'),
    path.join(ROOT, `${number}-backend`, 'PRD.md'),
  ];
  // Also find any .md file in the PRD directory
  const prdDir = path.join(ROOT, `${number}-backend`, 'PRD');
  if (fs.existsSync(prdDir)) {
    const prdFiles = fs.readdirSync(prdDir).filter(f => f.endsWith('.md') && f !== 'PRD.md');
    for (const f of prdFiles) {
      prdPaths.push(path.join(prdDir, f));
    }
  }
  for (const prdPath of prdPaths) {
    if (!fs.existsSync(prdPath)) continue;
    const content = fs.readFileSync(prdPath, 'utf-8').replace(/\r\n?/g, '\n');

    // 提取项目概述/定位
    if (!result.backendDesc) {
      const overviewMatch = content.match(/## [一二三四五六七八九十]*[、．.、]?\s*(?:项目概述|项目定位|项目简介|项目介绍)\s*\n([\s\S]*?)(?=\n## )/);
      if (overviewMatch) {
        const desc = overviewMatch[1].replace(/^核心功能包括：\s*\n/g, '').replace(/^- .+\n/gm, '').trim();
        if (desc.length > 20) result.backendDesc = desc;
        // If overview is just a short line before "核心功能", use the whole section
        if (desc.length <= 20) {
          result.backendDesc = overviewMatch[1].replace(/^-\s+/gm, '').trim().substring(0, 300);
        }
      }
    }

    // 提取用户角色 (## 用户角色 / ## 二、用户角色)
    // Allow PRD roles to replace generic account-derived roles (those with only "基本操作权限")
    const currentRolesAreGeneric = result.roles.every(r => r.permissions.length === 1 && r.permissions[0] === '基本操作权限');
    if (result.roles.length === 0 || currentRolesAreGeneric) {
      let prdRoles = [];
      const roleMatch = content.match(/## [一二三四五六七八九十]*[、．.]?\s*用户角色[^第\n]*\n([\s\S]*?)(?=\n## )/);
      if (roleMatch) {
        const roleSection = roleMatch[1];
        // 格式1: ### 2.1 学生用户 / ### 学生 (subsections with permissions)
        const subRoleRegex = /###\s*\d*\.?\d*\s*(.+?)\s*\n([\s\S]*?)(?=###|$)/g;
        let srMatch;
        while ((srMatch = subRoleRegex.exec(roleSection)) !== null) {
          const roleName = srMatch[1].replace(/功能$/, '').replace(/端$/, '').trim();
          const permissions = [];
          const subContent = srMatch[2];
          for (const line of subContent.split('\n')) {
            const p = line.replace(/^-\s*/, '').replace(/\*\*/g, '').trim();
            if (p.length > 0 && !p.startsWith('#') && !p.startsWith('|')) permissions.push(p);
          }
          // 格式1b: 表格格式角色描述（属性-描述表，如 | 用户特征 | xxx | / | 核心需求 | xxx |）
          if (permissions.length === 0) {
            const tableRows = subContent.split('\n').filter(l => l.trim().startsWith('|') && !l.match(/^[\s|:-]+$/));
            for (const row of tableRows) {
              const cells = row.split('|').map(c => c.trim()).filter(c => c.length > 0 && c !== '属性' && c !== '描述' && !c.startsWith('---') && !c.match(/^-+$/));
              if (cells.length >= 2) {
                const key = cells[0];
                const val = cells[1];
                // Skip separator rows and empty values
                if (val.length > 0 && !val.match(/^-+$/) && key.length > 0 && !key.match(/^-+$/)) permissions.push(`${key}：${val}`);
              }
            }
          }
          if (roleName.length < 20) prdRoles.push({ name: roleName, permissions: permissions.length > 0 ? permissions : ['基本操作权限'] });
        }
        // 格式2: - 管理员：权限描述 / 1. 管理员：权限描述 (line format with number or dash)
        if (prdRoles.length === 0) {
          for (const line of roleSection.split('\n')) {
            const r = line.replace(/^[-\d]+\.\s*/, '').replace(/\*\*/g, '').trim();
            if (r.length > 0 && !r.startsWith('#') && !r.startsWith('|')) {
              const parts = r.split(/[:：]/, 2);
              if (parts.length === 2 && parts[0].trim().length < 20) {
                prdRoles.push({ name: parts[0].trim(), permissions: parts[1].split(/[,，、]/).map(s => s.trim()).filter(Boolean) });
              }
            }
          }
        }
        // 格式2b: numbered list with bold role names: "1. **普通用户 (前台)**" / "2. **管理员 (后台)**"
        if (prdRoles.length === 0) {
          const boldRoleRegex = /^\d+\.\s*\*\*(.+?)\*\*\s*(?:\([^)]*\))?\s*$/gm;
          let brMatch;
          while ((brMatch = boldRoleRegex.exec(roleSection)) !== null) {
            let roleName = brMatch[1].replace(/\s*\(.*?\)\s*$/, '').trim();
            if (roleName.length > 0 && roleName.length < 20) {
              prdRoles.push({ name: roleName, permissions: ['基本操作权限'] });
            }
          }
        }
        // 格式3: comma-separated role names (e.g., "系统管理员、门诊医生、检查技师、患者本人")
        if (prdRoles.length === 0) {
          const roleNames = roleSection.replace(/\n/g, '').split(/[,，、]/).map(s => s.trim()).filter(s => s.length > 0 && s.length < 20);
          for (const rn of roleNames) {
            prdRoles.push({ name: rn, permissions: ['基本操作权限'] });
          }
        }
        // If PRD found richer roles, replace existing generic roles
        if (prdRoles.length > 0 && prdRoles.length >= result.roles.length) {
          result.roles = prdRoles;
        }
      }
      // Also try ## 角色 (old format)
      if (prdRoles.length === 0 && result.roles.length === 0) {
        const roleMatch2 = content.match(/## 角色[^\n]*\n([\s\S]*?)(?=\n## )/);
        if (roleMatch2) {
          for (const line of roleMatch2[1].split('\n')) {
            const r = line.replace(/^-\s*/, '').replace(/\*\*/g, '').trim();
            if (r.length > 0 && !r.startsWith('#')) {
              const parts = r.split(/[:：]/, 2);
              if (parts.length === 2 && parts[0].trim().length < 20) {
                result.roles.push({ name: parts[0].trim(), permissions: parts[1].split(/[,，、]/).map(s => s.trim()).filter(Boolean) });
              }
            }
          }
        }
      }
      // 格式4: 从功能列表中提取内联角色（如 "1. 账号权限：管理员、合规专员、研发负责人、审计员多角色登录"）
      if (prdRoles.length === 0 && result.roles.length === 0) {
        const funcMatch = content.match(/## [一二三四五六七八九十]*[、．.]?\s*(?:功能模块|系统功能|核心功能|功能列表)[^\n]*\n([\s\S]*?)(?=\n## )/);
        if (funcMatch) {
          const permLine = funcMatch[1].match(/(?:账号权限|权限管理|角色说明)[：:]\s*(.+)/);
          if (permLine) {
            const roleNames = permLine[1].replace(/多角色登录.*$/, '').replace(/多角色.*$/, '').split(/[,，、和及与]/).map(s => s.trim()).filter(s => s.length > 0 && s.length < 15);
            for (const rn of roleNames) {
              result.roles.push({ name: rn, permissions: ['基本操作权限'] });
            }
          }
        }
      }
      // 格式5: 角色行内列出，如 "ADMIN（admin / 123456）、PILOT（pilot / 123456）"
      if (prdRoles.length === 0 && result.roles.length === 0) {
        const roleSection = content.match(/## [一二三四五六七八九十]*[、．.]?\s*用户角色[^\n]*\n([\s\S]*?)(?=\n## )/);
        if (roleSection) {
          const inlineRoleRegex = /([A-Z_]{2,})\s*[（(]\s*\w+\s*\/\s*\w+\s*[)）]/g;
          let irMatch;
          const foundRoles = new Set();
          while ((irMatch = inlineRoleRegex.exec(roleSection[1])) !== null) {
            const roleName = irMatch[1];
            if (!foundRoles.has(roleName) && roleName.length < 20) {
              foundRoles.add(roleName);
              result.roles.push({ name: roleName, permissions: ['基本操作权限'] });
            }
          }
        }
      }
    }

    // 提取默认账号 (## 默认账号 / ## 十二、测试数据)
    if (result.defaultAccounts.length === 0) {
      // 格式1: ## 默认账号 with - username / password
      const accMatch = content.match(/## [一二三四五六七八九十]*[、．.]?\s*(?:默认账号|测试数据)\s*\n([\s\S]*?)(?=\n## |$)/);
      if (accMatch) {
        const accSection = accMatch[1];
        // 格式1a: - admin / 123456
        const lineRegex = /^-+\s*(\S+)\s*\/\s*(\S+)\s*(?:（角色[：:]?\s*(.+?)）)?\s*$/gm;
        let lMatch;
        while ((lMatch = lineRegex.exec(accSection)) !== null) {
          const username = lMatch[1];
          const password = lMatch[2];
          const roleNote = lMatch[3] || '';
          if (username.length < 50 && password.length < 50 && /^[a-zA-Z0-9_]/.test(username)) {
            const role = roleNote || (username === 'admin' ? '管理员' : '用户');
            result.defaultAccounts.push({ role, username, password, note: '' });
          }
        }
        // 格式1b: - admin/123456 (no spaces around /)
        if (result.defaultAccounts.length === 0) {
          const lineRegex2 = /^-+\s*(\w+)\/(\w+)\s*$/gm;
          let lMatch2;
          while ((lMatch2 = lineRegex2.exec(accSection)) !== null) {
            const role = lMatch2[1] === 'admin' ? '管理员' : '用户';
            result.defaultAccounts.push({ role, username: lMatch2[1], password: lMatch2[2], note: '' });
          }
        }
        // 格式1c: - 管理员：admin / 123456
        if (result.defaultAccounts.length === 0) {
          const lineRegex3 = /^-+\s*(.+?)[：:]\s*(\S+)\s*\/\s*(\S+)\s*$/gm;
          let lMatch3;
          while ((lMatch3 = lineRegex3.exec(accSection)) !== null) {
            const roleName = lMatch3[1].trim();
            if (roleName.length < 20) {
              result.defaultAccounts.push({ role: roleName, username: lMatch3[2], password: lMatch3[3], note: '' });
            }
          }
        }
      }
      // 格式2: "默认账号密码：各角色账号均使用 `123456`"
      if (result.defaultAccounts.length === 0) {
        const commonPwdMatch = content.match(/(?:默认账号|账号).*?密码.*?[均为：:]+\s*`?(\w+)`?/);
        if (commonPwdMatch) {
          const commonPwd = commonPwdMatch[1];
          // Try to find usernames from ## 默认账号 section or user role section
          const accSection = content.match(/## [一二三四五六七八九十]*[、．.]?\s*默认账号\s*\n([\s\S]*?)(?=\n## |$)/);
          if (accSection) {
            const userMatches = [...accSection[1].matchAll(/^-\s*(\w+)\s*\/\s*(\w+)/gm)];
            for (const um of userMatches) {
              result.defaultAccounts.push({ role: um[1] === 'admin' ? '管理员' : '用户', username: um[1], password: um[2], note: '' });
            }
          }
          // If still no accounts, try to extract from role names
          if (result.defaultAccounts.length === 0 && result.roles.length > 0) {
            result.defaultAccounts.push({ role: result.roles[0].name, username: 'admin', password: commonPwd, note: '' });
          }
        }
      }
      // 格式3: 角色行内包含账号，如 "ADMIN（admin / 123456）、PILOT（pilot / 123456）"
      if (result.defaultAccounts.length === 0) {
        const roleSection = content.match(/## [一二三四五六七八九十]*[、．.]?\s*用户角色[^\n]*\n([\s\S]*?)(?=\n## )/);
        if (roleSection) {
          const inlineAccRegex = /([A-Z_]{2,})\s*[（(]\s*(\w+)\s*\/\s*(\w+)\s*[)）]/g;
          let iaMatch;
          while ((iaMatch = inlineAccRegex.exec(roleSection[1])) !== null) {
            const roleName = iaMatch[1];
            const username = iaMatch[2];
            const password = iaMatch[3];
            if (username.length < 50 && password.length < 50) {
              result.defaultAccounts.push({ role: roleName, username, password, note: '' });
            }
          }
        }
      }
      // 格式4: 功能列表中提取账号行，如 "1. 账号权限：管理员、合规专员、研发负责人、审计员多角色登录"
      // 结合默认密码提取
      if (result.defaultAccounts.length === 0 && result.roles.length > 0) {
        const commonPwdMatch = content.match(/(?:所有|全部|默认).*?密码.*?[均为：:]+\s*`?(\w+)`?/);
        if (commonPwdMatch) {
          const commonPwd = commonPwdMatch[1];
          result.defaultAccounts.push({ role: result.roles[0].name, username: 'admin', password: commonPwd, note: '' });
        }
      }
    }

    // 提取技术设计信息 (数据库、端口等)
    const techMatch = content.match(/## (?:技术设计|技术要求|技术栈)[^\n]*\n([\s\S]*?)(?=\n## )/);
    if (techMatch) {
      // 提取数据库名
      const dbMatch2 = techMatch[1].match(/数据库[：:]\s*(\w+)/);
      if (dbMatch2 && !result._dbName) result._dbName = dbMatch2[1];
      // 提取端口
      const portMatch2 = techMatch[1].match(/后端端口[：:]\s*(\d+)/);
      if (portMatch2 && !result._port) result._port = portMatch2[1];
    }

    break; // Use first found PRD file
  }

  // --- 读取前端 README / PROJECT_OVERVIEW ---
  const frontendReadmePath = path.join(ROOT, `${number}-frontend`, 'README.md');
  const frontendOverviewPath = path.join(ROOT, `${number}-frontend`, 'PROJECT_OVERVIEW.md');

  for (const fp of [frontendOverviewPath, frontendReadmePath]) {
    if (!fs.existsSync(fp)) continue;
    const content = fs.readFileSync(fp, 'utf-8').replace(/\r\n?/g, '\n');

    // 提取前端项目简介
    if (!result.frontendDesc) {
      const descMatch = content.match(/## 项目(?:简介|总览|概述|完成情况|说明|描述)\s*\n([\s\S]*?)(?=\n## )/);
      if (descMatch) result.frontendDesc = descMatch[1].replace(/^[✅❌\-\s]+\n?/gm, '').trim();
    }

    // 提取角色权限（前端通常有更详细的描述）
    if (result.roles.length === 0) {
      const roleMatch = content.match(/## (?:角色|角色权限|权限)\s*\n([\s\S]*?)(?=\n## )/);
      if (roleMatch) {
        // 尝试匹配 ### 学生 / ### 教师 格式
        const subRoleRegex = /### (.+?)\s*\n([\s\S]*?)(?=### |$)/g;
        let srMatch;
        while ((srMatch = subRoleRegex.exec(roleMatch[1])) !== null) {
          const permissions = [];
          for (const line of srMatch[2].split('\n')) {
            const p = line.replace(/^-\s*\[.\]\s*/, '').replace(/^\s*[-✅❌]+\s*/, '').trim();
            if (p.length > 0 && !p.startsWith('#') && !p.startsWith('|')) permissions.push(p);
          }
          if (permissions.length > 0) result.roles.push({ name: srMatch[1], permissions });
        }
        // 如果没有子标题格式，尝试行格式
        if (result.roles.length === 0) {
          const lineRoleRegex = /### (.+?)\s*\n((?:- .+\n)+)/g;
          let lrMatch;
          while ((lrMatch = lineRoleRegex.exec(roleMatch[1])) !== null) {
            const permissions = [];
            for (const line of lrMatch[2].split('\n')) {
              const p = line.replace(/^-\s*\[.\]\s*/, '').trim();
              if (p.length > 0) permissions.push(p);
            }
            if (permissions.length > 0) result.roles.push({ name: lrMatch[1], permissions });
          }
        }
      }
    }

    // 提取前端API封装统计
    const apiStatMatch = content.match(/## API 接口封装\s*\n([\s\S]*?)(?=\n## )/);
    if (apiStatMatch) {
      const tableRegex = /\|\s*(\S+\.js)\s*\|\s*(.+?)\s*\|\s*(\d+)\s*个?\s*\|/g;
      let tMatch;
      while ((tMatch = tableRegex.exec(apiStatMatch[1])) !== null) {
        result.frontendApiCount[tMatch[1]] = { desc: tMatch[2].trim(), count: parseInt(tMatch[3]) };
      }
    }

    // 提取技术亮点
    if (result.techHighlights.length === 0) {
      const hlMatch = content.match(/## (?:技术亮点|项目特色)\s*\n([\s\S]*?)(?=\n## )/);
      if (hlMatch) {
        for (const line of hlMatch[1].split('\n')) {
          const h = line.replace(/^-\s*/, '').replace(/\*\*/g, '').trim();
          if (h.length > 0 && !h.startsWith('#') && !h.startsWith('|')) result.techHighlights.push(h);
        }
      }
    }

    // 提取默认账号
    if (result.defaultAccounts.length === 0) {
      // 表格格式
      const tableMatch = content.match(/## (?:测试账号|默认账号|账号)\s*\n([\s\S]*?)(?=\n## )/);
      if (tableMatch) {
        const rowRegex = /\|\s*([^|\n]+?)\s*\|\s*([^|\n]+?)\s*\|\s*([^|\n]+?)\s*\|\s*([^|\n]+?)\s*\|/g;
        let rMatch;
        while ((rMatch = rowRegex.exec(tableMatch[1])) !== null) {
          const role = rMatch[1].trim();
          const username = rMatch[2].trim();
          const password = rMatch[3].trim();
          const note = rMatch[4].trim();
          if (role !== '角色' && role !== '---' && !role.startsWith('-')) {
            result.defaultAccounts.push({ role, username, password, note });
          }
        }
      }
      // 行格式
      if (result.defaultAccounts.length === 0) {
        const lineRegex = /-\s+\*\*(.+?)\*\*[:：]\s*(\S+)\s*\/\s*(\S+)/g;
        let lMatch;
        while ((lMatch = lineRegex.exec(tableMatch ? tableMatch[1] : content)) !== null) {
          result.defaultAccounts.push({ role: lMatch[1], username: lMatch[2], password: lMatch[3], note: '' });
        }
      }
    }

    break; // 优先使用 PROJECT_OVERVIEW，找到后就不再读 README
  }

  // --- 从 SQL init.sql 提取默认账号（最后手段） ---
  if (result.defaultAccounts.length === 0) {
    const sqlPaths = [
      path.join(ROOT, `${number}-backend`, 'sql', 'init.sql'),
      path.join(ROOT, `${number}-backend`, 'src', 'main', 'resources', 'sql', 'init.sql'),
      path.join(ROOT, `${number}-backend`, 'src', 'main', 'resources', 'db', 'init.sql'),
      path.join(ROOT, `${number}-backend`, 'src', 'main', 'resources', 'data-h2.sql'),
      path.join(ROOT, `${number}-backend`, 'src', 'main', 'resources', 'data-mysql.sql'),
      path.join(ROOT, `${number}-backend`, 'src', 'main', 'resources', 'schema.sql'),
      path.join(ROOT, `${number}-backend`, 'database', 'init.sql'),
      path.join(ROOT, `${number}-backend`, 'db', 'init.sql'),
      path.join(ROOT, `${number}-backend`, 'doc', 'init.sql'),
      path.join(ROOT, `${number}-backend`, 'docs', 'init.sql'),
      path.join(ROOT, `${number}-backend`, 'schema.sql'),
      path.join(ROOT, `${number}-backend`, 'sql', 'schema.sql'),
      path.join(ROOT, `${number}-backend`, 'src', 'main', 'resources', 'sql', 'schema.sql'),
      path.join(ROOT, `${number}-backend`, 'src', 'main', 'resources', 'sql', 'data-h2.sql'),
      path.join(ROOT, `${number}-backend`, 'src', 'main', 'resources', 'sql', 'data-mysql.sql'),
    ];
    for (const sqlPath of sqlPaths) {
      if (!fs.existsSync(sqlPath)) continue;
      try {
        const sqlContent = fs.readFileSync(sqlPath, 'utf-8').replace(/\r\n?/g, '\n');
        // Look for INSERT INTO or MERGE INTO xxx_user / sys_user / user / admin values
        const insertRegex = /(?:INSERT|MERGE)\s+INTO\s+(\w*user\w*|\w*admin\w*|\w*account\w*)\s*(?:KEY\([^)]*\)\s*)?\(([^)]+)\)\s*(?:VALUES\s*)?(\([^;]+(?:\)\s*;?))/gi;
        let iMatch;
        while ((iMatch = insertRegex.exec(sqlContent)) !== null) {
          const tableName = iMatch[1].toLowerCase();
          const columns = iMatch[2].split(',').map(c => c.trim().replace(/`/g, '').toLowerCase());
          const valuesSection = iMatch[3];

          // Find username and password column indices
          const usernameIdx = columns.findIndex(c => c === 'username' || c === 'user_name' || c === 'login_name' || c === 'account');
          const passwordIdx = columns.findIndex(c => c === 'password' || c === 'pwd' || c === 'pass_word');
          const roleIdx = columns.findIndex(c => c === 'role' || c === 'role_id' || c === 'user_type' || c === 'type');

          if (usernameIdx === -1 || passwordIdx === -1) continue;

          // Parse VALUES - use smarter splitting that handles nested parentheses like NOW()
          // First, split by rows looking for patterns like (val1, val2, ...),
          const rows = [];
          const rowSplitRegex = /\(([^)]*(?:\([^)]*\)[^)]*)*)\)/g;
          let rMatch2;
          while ((rMatch2 = rowSplitRegex.exec(valuesSection)) !== null) {
            rows.push(rMatch2[1]);
          }
          // If no rows found with nested parens, try simpler regex
          if (rows.length === 0) {
            const simpleRowRegex = /\(([^)]+)\)/g;
            while ((rMatch2 = simpleRowRegex.exec(valuesSection)) !== null) {
              rows.push(rMatch2[1]);
            }
          }

          for (const rowStr of rows) {
            // Smart comma-split that respects quoted strings and parentheses
            const vals = smartSplit(rowStr);
            const username = (vals[usernameIdx] || '').replace(/^['"]|['"]$/g, '').trim();
            const password = (vals[passwordIdx] || '').replace(/^['"]|['"]$/g, '').trim();
            if (username && password && username.length < 50 && password.length < 50 &&
                !username.startsWith('(') && username !== 'id' &&
                !/^\$2[aby]?\$/.test(password) && // Skip bcrypt/bcrypt-like hashes
                !/^[a-f0-9]{32}$/i.test(password) && // Skip MD5 hashes
                password.length > 2) {
              const role = roleIdx >= 0 && vals[roleIdx] ? vals[roleIdx].replace(/^['"]|['"]$/g, '').trim() : (username === 'admin' ? '管理员' : '用户');
              result.defaultAccounts.push({ role, username, password, note: '' });
            }
          }
        }

        // MERGE INTO without column names: MERGE INTO sys_user KEY(id) VALUES (v1, v2, ...)
        if (result.defaultAccounts.length === 0) {
          const mergeRegex = /MERGE\s+INTO\s+(\w*user\w*|\w*admin\w*|\w*account\w*)\s+KEY\([^)]*\)\s+VALUES\s*([^;]+);/gi;
          let mMatch;
          while ((mMatch = mergeRegex.exec(sqlContent)) !== null) {
            const valuesSection = mMatch[2];
            const rows = [];
            const rowSplitRegex = /\(([^)]*(?:\([^)]*\)[^)]*)*)\)/g;
            let rMatch2;
            while ((rMatch2 = rowSplitRegex.exec(valuesSection)) !== null) {
              rows.push(rMatch2[1]);
            }

            // Detect column positions by examining first row for known patterns
            // Common patterns: (id, username, password, role, ...) or (id, username, password_md5, ..., role, ...)
            for (const rowStr of rows) {
              const vals = smartSplit(rowStr).map(v => v.replace(/^['"]|['"]$/g, '').trim());
              // Find username: look for value matching known admin usernames
              let usernameIdx = -1, passwordIdx = -1, roleIdx = -1;
              for (let i = 0; i < vals.length; i++) {
                if (/^(admin|root|user\d*|test|owner\d*)$/.test(vals[i])) {
                  usernameIdx = i;
                  passwordIdx = i + 1; // password usually follows username
                  break;
                }
              }
              // If no admin found, try to find a column that looks like a short alphanumeric username
              if (usernameIdx === -1) {
                for (let i = 1; i < vals.length - 1; i++) { // skip id (index 0)
                  if (/^\w{3,20}$/.test(vals[i]) && !/^\d+$/.test(vals[i])) {
                    usernameIdx = i;
                    passwordIdx = i + 1;
                    break;
                  }
                }
              }
              if (usernameIdx === -1 || passwordIdx >= vals.length) continue;

              const username = vals[usernameIdx];
              const password = vals[passwordIdx];

              // Skip hashes
              if (!username || !password || password.length < 3 ||
                  /^\$2[aby]?\$/.test(password) ||
                  /^[a-f0-9]{32}$/i.test(password) ||
                  password.length > 50) continue;

              // Find role: check surrounding columns for role-like values
              for (let i = 0; i < vals.length; i++) {
                if (i !== usernameIdx && i !== passwordIdx &&
                    /^(ADMIN|USER|OWNER|MANAGER|STAFF|CUSTOMER|TEACHER|STUDENT|DOCTOR|NURSE)$/i.test(vals[i])) {
                  roleIdx = i;
                  break;
                }
              }
              const role = roleIdx >= 0 ? vals[roleIdx] : (username === 'admin' ? '管理员' : '用户');
              result.defaultAccounts.push({ role, username, password, note: '' });
            }
          }
        }
        // Last resort: look for password hints in SQL comments
        if (result.defaultAccounts.length === 0) {
          const commentPwRegex = /[-]{2,}\s*(?:.*(?:password|密码)\s*[：:]\s*)(\S+)/gi;
          let cpMatch;
          while ((cpMatch = commentPwRegex.exec(sqlContent)) !== null) {
            const pw = cpMatch[1].replace(/[)\],]+$/, '').trim();
            if (pw.length >= 3 && pw.length < 50) {
              // Found a password hint, pair it with 'admin' if no username found
              const userMatch = sqlContent.match(/[-]{2,}\s*(?:.*(?:username|用户名)\s*[：:]\s*)(\S+)/i);
              const username = userMatch ? userMatch[1].replace(/[)\],]+$/, '').trim() : 'admin';
              if (username.length < 50 && /^\w/.test(username)) {
                result.defaultAccounts.push({ role: username === 'admin' ? '管理员' : '用户', username, password: pw, note: '' });
              }
            }
          }
        }
      } catch (e) {
        // SQL parsing errors are non-fatal
      }
      if (result.defaultAccounts.length > 0) break;
    }
    // Deduplicate accounts (same username)
    const seenUsernames = new Set();
    result.defaultAccounts = result.defaultAccounts.filter(acc => {
      if (seenUsernames.has(acc.username)) return false;
      seenUsernames.add(acc.username);
      return true;
    });
    // Limit to 5 accounts
    if (result.defaultAccounts.length > 5) {
      result.defaultAccounts = result.defaultAccounts.slice(0, 5);
    }
  }

  // --- 读取小程序 README ---
  const miniprogramReadmePath = path.join(ROOT, `${number}-miniprogram`, 'README.md');
  if (fs.existsSync(miniprogramReadmePath)) {
    const content = fs.readFileSync(miniprogramReadmePath, 'utf-8').replace(/\r\n?/g, '\n');
    if (!result.frontendDesc) {
      const descMatch = content.match(/## 项目简介\s*\n([\s\S]*?)(?=\n## )/);
      if (descMatch) result.frontendDesc = descMatch[1].trim();
    }
  }

  // --- 从检查报告提取默认账号（最后手段） ---
  if (result.defaultAccounts.length === 0) {
    const checkFiles = fs.existsSync(CHECKS_DIR) ? fs.readdirSync(CHECKS_DIR).filter(f => f.startsWith(number + '-')) : [];
    if (checkFiles.length > 0) {
      const checkContent = fs.readFileSync(path.join(CHECKS_DIR, checkFiles[0]), 'utf-8').replace(/\r\n?/g, '\n');
      // 格式1: "默认账号 `admin/123456`" or "默认账号 `admin / 123456`"
      const bt = '`';
      const accPatterns = [
        // "使用默认账号 `admin/123456`"
        new RegExp(`使用(?:默认|测试)?(?:管理员)?账号\\s*${bt}(\\w+)\\s*/\\s*(\\w+)${bt}`, 'g'),
        // "`admin / 123456` 登录" (with spaces, also numeric usernames)
        new RegExp(`${bt}(\\w+)\\s*/\\s*(\\w+)${bt}\\s*登录`, 'g'),
        // "管理员：admin / 123456" or "管理员：`admin` / `123456`" (also numeric like 13800138001)
        /(?:管理员|用户|学生|买家|卖家)[：:]\s*`?(\w+)`?\s*\/\s*`?(\w+)`?/g,
        // "- admin / 123456" in check tables (also numeric usernames)
        /^\s*-\s+`?(\w+)`?\s*\/\s*`?(\w+)`?/gm,
        // "- 登录账号：`admin / 123456`"
        new RegExp(`(?:默认|测试)?(?:登录)?账号[：:]\\s*${bt}(\\w+)\\s*/\\s*(\\w+)${bt}`, 'g'),
        // "- 账号 `buyer01 / 123456`" (账号 without colon, just space)
        new RegExp(`账号\\s+${bt}(\\w+)\\s*/\\s*(\\w+)${bt}`, 'g'),
        // "- `admin / 123456`" in list format (standalone backtick-wrapped)
        /^-\s+`(\w+)\s*\/\s*(\w+)`\s*$/gm,
        // "默认可用演示账号包括：" followed by lines
        // These will be caught by the next patterns
      ];
      for (const pattern of accPatterns) {
        let aMatch;
        while ((aMatch = pattern.exec(checkContent)) !== null) {
          const username = aMatch[1];
          const password = aMatch[2];
          // Filter out non-account patterns like src/test, total/pages etc
          if (username.length < 30 && password.length < 30 &&
              !['src', 'test', 'total', 'pages', 'GET', 'POST', 'PUT', 'DELETE', 'http', 'https', 'localhost'].includes(username) &&
              !['4', '5', '6', '7', '8'].includes(password) &&
              /^\w/.test(username) && !/^[\d]+$/.test(username)) {
            const role = username === 'admin' ? '管理员' : '用户';
            result.defaultAccounts.push({ role, username, password, note: '' });
          }
        }
        if (result.defaultAccounts.length > 0) break;
      }
      // Deduplicate
      const seen = new Set();
      result.defaultAccounts = result.defaultAccounts.filter(acc => {
        if (seen.has(acc.username)) return false;
        seen.add(acc.username);
        return true;
      });
      if (result.defaultAccounts.length > 5) {
        result.defaultAccounts = result.defaultAccounts.slice(0, 5);
      }
    }
  }

  // Filter out spurious accounts/roles that are not real user accounts
  const spuriousRoles = ['基础 URL', 'Base URL', 'URL', '请求', '响应', '格式', '认证',
    '端点', '接口', '模块', '配置', '部署', '服务', '环境', '设计', '架构',
    '数据库配置', 'MySQL配置', '初始化数据库', '启动应用',
    '后端', '前端', '技术栈', '项目结构', '项目简介'];
  result.defaultAccounts = result.defaultAccounts.filter(acc => {
    const role = acc.role.toLowerCase();
    const username = acc.username.toLowerCase();
    for (const sp of spuriousRoles) {
      if (role === sp.toLowerCase() || role.startsWith(sp.toLowerCase())) return false;
    }
    // Filter out purely numeric role names (like "0", "2")
    if (/^\d+$/.test(role)) return false;
    // Filter out if username looks like a URL path (e.g. /api, v1, /v1)
    if (/^[/\\]/.test(username) || username === 'v1' || username === 'v2' || username === 'api') return false;
    // Filter out if password is too short (< 3) or looks like a version number
    if (acc.password.length < 3 || /^\d+$/.test(acc.password) && acc.password.length <= 2) return false;
    return true;
  });
  result.roles = result.roles.filter(r => {
    const name = r.name.toLowerCase();
    for (const sp of spuriousRoles) {
      if (name === sp.toLowerCase() || name.startsWith(sp.toLowerCase())) return false;
    }
    // Filter out purely numeric role names (like "0", "2")
    if (/^\d+$/.test(name)) return false;
    return true;
  });

  // If all roles were filtered out, create fallback roles from remaining accounts
  if (result.roles.length === 0 && result.defaultAccounts.length > 0) {
    const roleSet = new Set();
    for (const acc of result.defaultAccounts) {
      if (acc.role && acc.role.length < 20 && !roleSet.has(acc.role)) {
        roleSet.add(acc.role);
        result.roles.push({ name: acc.role, permissions: ['基本操作权限'] });
      }
    }
  }

  return result;
}

// === 加载检查报告 ===
function readCheckReport(number) {
  if (!fs.existsSync(CHECKS_DIR)) return null;
  const files = fs.readdirSync(CHECKS_DIR).filter(f => f.startsWith(number + '-'));
  if (files.length === 0) return null;
  const result = {
    fileName: files[0], hasReport: true,
    checkDate: '', conclusion: '', dbInfo: '', portInfo: '',
    defaultAccount: '', environment: '', issues: [],
    defaultAccounts: [],  // structured accounts from check report
    roles: [],            // roles from check report
  };

  // Iterate all check files, prefer the one with most account data
  let bestContent = null;
  let bestAccountCount = -1;
  let bestFileName = files[0];
  for (const f of files) {
    const content = fs.readFileSync(path.join(CHECKS_DIR, f), 'utf-8').replace(/\r\n?/g, '\n');
    // Count accounts in this file
    const bt = '`';
    const accCountPatterns = [
      new RegExp(`(?:管理员|学生|教师|医生|护士|患者|用户|普通用户|店长|顾客|卖家|买家|业主|房东|飞手|维修员|操作员|院长|物业人员|店员|快递员|创作者|待认证用户|审核员|运营人员)[：:]\\s*${bt}\\w+${bt}\\s*/\\s*${bt}\\w+${bt}`, 'g'),
      /^-\s+`\w+\s*\/\s*\w+`\s*$/gm,
    ];
    let count = 0;
    for (const pattern of accCountPatterns) {
      const matches = content.match(pattern);
      if (matches) count += matches.length;
    }
    // Also check for "默认账号" section
    if (content.includes('默认账号') || content.includes('测试数据')) count += 10;
    if (count > bestAccountCount) {
      bestAccountCount = count;
      bestContent = content;
      bestFileName = f;
    }
  }

  if (!bestContent) bestContent = fs.readFileSync(path.join(CHECKS_DIR, files[0]), 'utf-8').replace(/\r\n?/g, '\n');
  const content = bestContent;
  result.fileName = bestFileName;

  const dateMatch = content.match(/检查日期[：:]\s*`?(\d{4}-\d{2}-\d{2})`?/);
  if (dateMatch) result.checkDate = dateMatch[1];
  const dbMatch = content.match(/数据库[：:]\s*`(\w+)`/);
  if (dbMatch) result.dbInfo = dbMatch[1];
  const portMatch = content.match(/后端默认端口[：:]\s*`(\d+)`/);
  if (portMatch) result.portInfo = portMatch[1];
  const accountMatch = content.match(/默认账号.*?`(\w+\/\w+)`/);
  if (accountMatch) result.defaultAccount = accountMatch[1];
  const conclusionMatch = content.match(/(?:综合结论|当前结论)[：:]\s*`(.+?)`/);
  if (conclusionMatch) result.conclusion = escapeHtml(conclusionMatch[1]);

  // 提取环境信息
  const envMatch = content.match(/### 3\.2 环境前置\s*\n([\s\S]*?)(?=\n## )/);
  if (envMatch) result.environment = envMatch[1].trim();

  // 提取问题清单
  const issueSection = content.match(/## 7\. 当前问题清单\s*\n([\s\S]*?)(?=\n## )/);
  if (issueSection) {
    const rowRegex = /\|\s*(\d+-\d+)\s*\|\s*([^|\n]+?)\s*\|\s*([^|\n]+?)\s*\|\s*([^|\n]+?)\s*\|/g;
    let iMatch;
    while ((iMatch = rowRegex.exec(issueSection[1])) !== null) {
      if (iMatch[1] !== '编号' && !iMatch[1].startsWith('-'))
        result.issues.push({ id: iMatch[1], desc: iMatch[2].trim(), severity: iMatch[3].trim(), status: iMatch[4].trim() });
    }
  }

  // 提取角色信息 from check report
  // Look for role mentions in verification tables
  const rolePatterns = [
    // "角色为 `ADMIN`" / "角色为 ADMIN"
    /角色[为是]\s*`?(\w+)`?/g,
    // "管理员登录..." / "学生登录..."
    /(?:管理员|学生|教师|医生|护士|患者|用户|普通用户|评审员|工程师|审计员|合规专员|巡检员|快递员|代收点管理员|飞手|运营经理|班组长|安全主管|车主|岗亭员|分析员|维修员|家庭用户|能耗分析师|SRE|运维工程师|运维主管|研发负责人|发布经理|运营专员|物业人员|业主|院长|操作员|店长|顾客|卖家|买家|房东|店员)[^\n]{0,5}登录/g,
  ];
  for (const pattern of rolePatterns) {
    let rMatch;
    const foundRoles = new Set();
    while ((rMatch = pattern.exec(content)) !== null) {
      const roleText = rMatch[0].trim();
      // Extract role name
      if (pattern === rolePatterns[0]) {
        const roleName = rMatch[1];
        // Skip purely numeric role values like "0", "2" (these are role IDs, not names)
        if (/^\d+$/.test(roleName)) continue;
        foundRoles.add(roleName);
      } else {
        // Extract Chinese role name
        const chineseMatch = roleText.match(/^(管理员|学生|教师|医生|护士|患者|用户|普通用户|评审员|工程师|审计员|合规专员|巡检员|快递员|代收点管理员)/);
        if (chineseMatch) foundRoles.add(chineseMatch[1]);
      }
    }
    for (const role of foundRoles) {
      result.roles.push({ name: role, permissions: ['基本操作权限'] });
    }
    // Only break if we found non-numeric roles (allow fallback to Chinese role patterns)
    if (result.roles.length > 0) break;
  }

  // 提取默认账号 from check report verification tables
  const bt = '`';
  const accPatterns = [
    // "使用默认账号 `admin/123456`" or "使用默认管理员账号 `admin/admin123`"
    new RegExp(`使用(?:默认|测试)?(?:管理员)?账号\\s*${bt}(\\w+)\\s*/\\s*(\\w+)${bt}`, 'g'),
    // "`admin / 123456` 登录成功"
    new RegExp(`${bt}(\\w+)\\s*/\\s*(\\w+)${bt}\\s*登录`, 'g'),
    // "管理员：`admin / 123456`" (backticks wrap entire pair)
    new RegExp(`(?:管理员|学生|教师|医生|护士|患者|用户|普通用户|店长|顾客|卖家|买家|业主|房东|飞手|维修员|操作员|院长|物业人员|店员|快递员|创作者|待认证用户|审核员|运营人员)[：:]\\s*${bt}(\\w+)\\s*/\\s*(\\w+)${bt}`, 'g'),
    // "管理员：`admin` / `123456`" (separate backticks)
    new RegExp(`(?:管理员|学生|教师|医生|护士|患者|用户|普通用户|店长|顾客|卖家|买家|业主|房东|飞手|维修员|操作员|院长|物业人员|店员|快递员|创作者|待认证用户|审核员|运营人员)[：:]\\s*${bt}(\\w+)${bt}\\s*/\\s*${bt}(\\w+)${bt}`, 'g'),
    // "管理员：admin / 123456" (no backticks)
    /(?:管理员|学生|教师|医生|护士|患者|用户|普通用户|店长|顾客|卖家|买家|业主|房东|飞手|维修员|操作员|院长|物业人员|店员|快递员|创作者|待认证用户|审核员|运营人员)[：:]\s*(\w+)\s*\/\s*(\w+)/g,
    // "- admin / 123456（角色：ADMIN）" or "- admin / 123456"
    /^-\s+`?(\w+)`?\s*\/\s*`?(\w+)`?\s*(?:（[^)]*）)?\s*$/gm,
    // "station1 / 123456" or "student1 / 123456" in verification section
    /`(\w+)\s*\/\s*(\w+)`\s*(?:登录|返回)/g,
  ];
  for (const pattern of accPatterns) {
    let aMatch;
    while ((aMatch = pattern.exec(content)) !== null) {
      const username = aMatch[1];
      const password = aMatch[2];
      if (username.length < 30 && password.length < 30 &&
          !['src', 'test', 'total', 'pages', 'GET', 'POST', 'PUT', 'DELETE', 'http', 'https', 'localhost'].includes(username) &&
          !['4', '5', '6', '7', '8'].includes(password) &&
          /^\w/.test(username) && !/^[\d]+$/.test(username)) {
        // Try to detect role from the matched text (e.g. "管理员：`admin / 123456`")
        const roleMatch = aMatch[0].match(/(管理员|学生|教师|医生|护士|患者|用户|普通用户|店长|顾客|卖家|买家|业主|房东|飞手|维修员|操作员|院长|物业人员|店员|快递员|创作者|待认证用户|审核员|运营人员)[：:]/);
        const role = roleMatch ? roleMatch[1] : (username === 'admin' ? '管理员' : '用户');
        result.defaultAccounts.push({ role, username, password, note: '' });
      }
    }
    if (result.defaultAccounts.length > 0) break;
  }
  // Deduplicate
  const seenAccs = new Set();
  result.defaultAccounts = result.defaultAccounts.filter(acc => {
    if (seenAccs.has(acc.username)) return false;
    seenAccs.add(acc.username);
    return true;
  });
  if (result.defaultAccounts.length > 5) {
    result.defaultAccounts = result.defaultAccounts.slice(0, 5);
  }

  // If no roles found but accounts have role info, derive roles from accounts
  if (result.roles.length === 0 && result.defaultAccounts.length > 0) {
    const roleSet = new Set();
    for (const acc of result.defaultAccounts) {
      if (acc.role && acc.role.length > 0 && acc.role.length < 20 && !roleSet.has(acc.role)) {
        roleSet.add(acc.role);
        result.roles.push({ name: acc.role, permissions: ['基本操作权限'] });
      }
    }
  }

  // Filter out spurious accounts/roles
  const spuriousRoles = ['基础 URL', 'Base URL', 'URL', '请求', '响应', '格式', '认证',
    '端点', '接口', '模块', '配置', '部署', '服务', '环境', '设计', '架构',
    '后端', '前端', '技术栈', '项目结构', '项目简介'];
  result.defaultAccounts = result.defaultAccounts.filter(acc => {
    const role = acc.role.toLowerCase();
    const username = acc.username.toLowerCase();
    for (const sp of spuriousRoles) {
      if (role === sp.toLowerCase() || role.startsWith(sp.toLowerCase())) return false;
    }
    if (/^\d+$/.test(role)) return false;
    if (/^[/\\]/.test(username) || username === 'v1' || username === 'v2' || username === 'api') return false;
    return true;
  });
  result.roles = result.roles.filter(r => {
    const name = r.name.toLowerCase();
    for (const sp of spuriousRoles) {
      if (name === sp.toLowerCase() || name.startsWith(sp.toLowerCase())) return false;
    }
    if (/^\d+$/.test(name)) return false;
    return true;
  });

  return result;
}

// === 解析 readme.md ===
const readmeContent = fs.readFileSync(README_PATH, 'utf-8').replace(/\r\n?/g, '\n');
const projectRegex = /^### (\d{3}) - (.+)$/gm;
const positions = [];
let match;
while ((match = projectRegex.exec(readmeContent)) !== null) {
  positions.push({ number: match[1], title: match[2], start: match.index });
}

const projects = [];
for (let i = 0; i < positions.length; i++) {
  const start = positions[i].start;
  const end = i + 1 < positions.length ? positions[i + 1].start : readmeContent.length;
  const block = readmeContent.substring(start, end);
  const num = positions[i].number;
  const title = positions[i].title;

  const nameMatch = block.match(/#### 🏷️ 项目名称\s*\n(.+)/);
  const projectName = nameMatch ? nameMatch[1].trim() : title;

  const techStackMatch = block.match(/#### 💻 技术栈\s*\n([\s\S]*?)(?=#### 🎯|#### ✨|---|$)/);
  let backendTech = [], frontendTech = [];
  if (techStackMatch) {
    const techBlock = techStackMatch[1];
    const parts = techBlock.split(/\*\*前端/);
    backendTech = parts[0].replace(/\*\*后端[^*]*\*\*\s*\n?/, '').split('\n').map(l => l.replace(/^-\s*/, '').trim()).filter(l => l.length > 0 && !l.startsWith('**'));
    frontendTech = (parts.length > 1 ? parts[1] : '').replace(/[^*]*\*\*\s*\n?/, '').split('\n').map(l => l.replace(/^-\s*/, '').trim()).filter(l => l.length > 0 && !l.startsWith('**'));
  }

  const modulesMatch = block.match(/#### 🎯 功能模块\s*\n([\s\S]*?)(?=#### ✨|#### 🎬|#### 💡|---|$)/);
  const modules = [];
  if (modulesMatch) {
    for (const line of modulesMatch[1].split('\n')) {
      if (line.trim().startsWith('<')) continue;
      const m = line.match(/^\d+\.\s+\*\*(.+?)\*\*\s*[-–—]\s*(.+)/);
      if (m) modules.push({ name: m[1], description: m[2].trim() });
    }
  }

  const highlightsMatch = block.match(/#### ✨ 特色亮点\s*\n([\s\S]*?)(?=#### 🎬|#### 💡|---|$)/);
  const highlights = [];
  if (highlightsMatch) {
    for (const line of highlightsMatch[1].split('\n')) {
      if (line.trim().startsWith('<')) continue;
      const h = line.replace(/^-\s*/, '').trim();
      if (h.length > 0 && !h.startsWith('**') && !h.startsWith('####')) highlights.push(h);
    }
  }

  const demoMatch = block.match(/#### 🎬 演示流程\s*\n([\s\S]*?)(?=####|---|$)/);
  const demoSteps = [];
  if (demoMatch) {
    for (const line of demoMatch[1].split('\n')) {
      if (line.trim().startsWith('<') || line.trim().startsWith('####')) continue;
      const s = line.replace(/^\d+\.\s*/, '').trim();
      if (s.length > 0 && !s.startsWith('-') && !s.startsWith('**')) demoSteps.push(s);
    }
  }

  let projectType = 'web';
  if (block.includes('小程序') || block.includes('miniprogram') || block.includes('miniapp') || block.includes('微信小程序')) projectType = 'miniprogram';
  const sd = sourceData[num];
  if (sd && sd.hasMiniprogram && !sd.hasFrontend) projectType = 'miniprogram';

  const isIntegrated = block.includes('一体化') || block.includes('集成');

  let backendVersion = '';
  let totalApiCount = 0;
  let totalControllerCount = 0;
  let totalEntityCount = 0;
  let totalSqlTableCount = 0;
  let totalViewCount = 0;
  if (sd) {
    totalControllerCount = sd.controllers?.length || 0;
    totalApiCount = sd.controllers ? sd.controllers.reduce((s, c) => s + c.apis.length, 0) : 0;
    totalEntityCount = sd.entities?.length || 0;
    totalSqlTableCount = sd.sqlTables?.length || 0;
    totalViewCount = sd.views?.length || 0;
    backendVersion = sd.appConfig?.springBootVersion || '';
  }

  // 提取项目源码文档
  const projectDocs = extractProjectDocs(num);

  projects.push({
    number: num, title, projectName, backendTech, frontendTech, modules, highlights, demoSteps,
    projectType, isIntegrated, backendVersion,
    totalApiCount, totalControllerCount, totalEntityCount, totalSqlTableCount, totalViewCount,
    projectDocs
  });
}

console.log(`解析到 ${projects.length} 个项目`);

// === 分类 ===
const categories = {
  'campus': { name: '校园生活', desc: '校园相关的管理系统与服务平台', projects: [] },
  'education': { name: '教育培训', desc: '在线学习、考试与教育管理平台', projects: [] },
  'ecommerce': { name: '电商交易', desc: '商品交易、订单管理与电商平台', projects: [] },
  'health': { name: '医疗健康', desc: '健康管理、医疗预约与养老服务', projects: [] },
  'community': { name: '社区服务', desc: '社区管理、物业与生活服务平台', projects: [] },
  'culture': { name: '文化娱乐', desc: '文化传承、娱乐休闲与社交平台', projects: [] },
  'enterprise': { name: '企业管理', desc: '企业办公、ERP与管理系统', projects: [] },
  'travel': { name: '旅游出行', desc: '旅游预订、出行与交通服务', projects: [] },
  'agriculture': { name: '农业环保', desc: '农业科技、环保与绿色能源', projects: [] },
  'ai-tech': { name: 'AI与前沿技术', desc: '人工智能、区块链与前沿技术平台', projects: [] },
};
const categoryKeywords = {
  'campus': ['校园', '学生', '课程', '选课', '图书馆', '自习室', '社团', '表白', '快递', '宿舍', '报修', '请假', '志愿', '失物', '考勤', '签到', '毕设', '考研', '课堂', '校友', '校园共享', '校园二手', '校园点餐', '校园运动', '校园学习', '校园视频', '校园事务'],
  'education': ['教育', '学习', '教学', '在线课程', '编程学习', '知识', '题库', '智能学习', '教师评价', '数学', '教学资源', '考试', '公考'],
  'ecommerce': ['交易', '电商', '购物', '订单', '商城', '团购', '二手', '拍卖', '购物车', '零食', '仓库', '库存', '游戏交易', '工艺销售', '牛奶', '电影票', '球赛', '票务', '点餐', '理发', '民宿', '剧本杀', '壁纸', '宠物咖啡'],
  'health': ['医疗', '健康', '医院', '养老', '护理', '体检', '心理', '药', '中医', '食疗', '饮食', '营养', '门诊', '药品', '老年人', '养老院', '幼儿', '幼儿园', '儿童领养'],
  'community': ['社区', '物业', '停车', '访客', '缴费', '家政', '维修', '家电维修', '社区服务', '垃圾分类', '噪音', '设备共享', '充电桩', '时间银行'],
  'culture': ['文化', '非遗', '民俗', '戏曲', '艺术品', '画师', '接稿', '短视频', '白板', '协作', '写作', '竞赛', '博物馆', '文物', '设计', '文创'],
  'enterprise': ['企业', 'ERP', 'OA', 'HRM', 'MES', '招聘', '求职', '实习', '面试', '管理', '办公', '工单', '客户服务', '质量', '合规', 'DevOps', '监控', '云', '数据脱敏', '隐私', '零信任', '防欺诈', 'API', '测试'],
  'travel': ['旅游', '出行', '酒店', '租车', '共享单车', '共享自行车', '铁路', '火车票', '自行车', '哈尔滨', '球赛订票', '电影', '足球'],
  'agriculture': ['农', '扶贫', '助农', '农业', '大米', '水稻', '环保', '垃圾', '回收', '充电', '新能源', '农业追溯'],
  'ai-tech': ['AI', '智能', '推荐', 'AIGC', '版权', '多模态', '大模型', '学术不端', '智能匹配', '提示词', 'AI菜谱', '知识图谱', '区块链'],
};

for (const project of projects) {
  const text = `${project.title} ${project.projectName} ${project.modules.map(m => m.name).join(' ')}`;
  let bestCategory = 'campus', bestScore = 0;
  for (const [cat, keywords] of Object.entries(categoryKeywords)) {
    let score = 0;
    for (const kw of keywords) { if (text.includes(kw)) score++; }
    if (score > bestScore) { bestScore = score; bestCategory = cat; }
  }
  const num = parseInt(project.number);
  if (num >= 97 && num <= 112) bestCategory = 'enterprise';
  if (num >= 113) {
    if (text.includes('农') || text.includes('充电')) bestCategory = 'agriculture';
    else if (text.includes('药') || text.includes('医疗') || text.includes('健康') || text.includes('门诊') || text.includes('实验室')) bestCategory = 'health';
    else if (text.includes('会议') || text.includes('选题') || text.includes('创新')) bestCategory = 'education';
    else if (text.includes('噪音') || text.includes('设备')) bestCategory = 'community';
  }
  project.category = bestCategory;
  categories[bestCategory].projects.push(project);
}

// === 生成项目页面 ===
for (const project of projects) {
  const { number, title, projectName, backendTech, frontendTech, modules, highlights, demoSteps,
    projectType, isIntegrated, backendVersion,
    totalApiCount, totalControllerCount, totalEntityCount, totalSqlTableCount, totalViewCount,
    projectDocs } = project;

  const checkReport = readCheckReport(number);
  const sd = sourceData[number] || {};
  const dbName = checkReport?.dbInfo || sd.appConfig?.dbName || `db_${number}`;
  const port = checkReport?.portInfo || sd.appConfig?.port || '';

  // Merge check report accounts/roles into projectDocs - prefer richer data
  if (checkReport && projectDocs.defaultAccounts.length === 0 && checkReport.defaultAccounts.length > 0) {
    projectDocs.defaultAccounts = checkReport.defaultAccounts;
  }
  // For roles: prefer source with more distinct role names (not just "用户"/"基本操作权限" generics)
  const projectRoleNames = new Set(projectDocs.roles.map(r => r.name));
  const checkRoleNames = new Set(checkReport?.roles?.map(r => r.name) || []);
  const projectHasOnlyGeneric = projectDocs.roles.every(r => r.permissions.length === 1 && r.permissions[0] === '基本操作权限');
  if (checkReport && checkReport.roles.length > 0) {
    // Use check report roles if project roles are empty, or if project has only generic roles and check report has richer data
    if (projectDocs.roles.length === 0 || (projectHasOnlyGeneric && checkRoleNames.size > projectRoleNames.size)) {
      projectDocs.roles = checkReport.roles;
    }
  }

  let md = '';
  md += `---\n`;
  md += `title: ${number} - ${title}\n`;
  md += `description: ${projectName}\n`;
  md += `---\n\n`;
  md += `# ${title}\n\n`;
  md += `> ${projectName}\n\n`;

  // === 项目概览 ===
  md += `## 项目概览\n\n`;
  md += `| 属性 | 内容 |\n`;
  md += `|------|------|\n`;
  md += `| 项目编号 | ${number} |\n`;
  md += `| 项目名称 | ${title} |\n`;
  md += `| 项目全称 | ${projectName} |\n`;
  md += `| 项目类型 | ${projectType === 'miniprogram' ? '微信小程序' : 'Web应用'} |\n`;
  if (isIntegrated) md += `| 部署方式 | 前后端一体化 |\n`;
  if (backendVersion) md += `| Spring Boot 版本 | ${backendVersion} |\n`;
  if (port) md += `| 后端默认端口 | \`${port}\` |\n`;
  md += `| 数据库名 | \`${dbName}\` |\n`;
  if (totalControllerCount > 0) md += `| 后端 Controller | ${totalControllerCount} 个 |\n`;
  if (totalApiCount > 0) md += `| 后端 API 接口 | ${totalApiCount} 个 |\n`;
  if (totalSqlTableCount > 0) md += `| 数据库表 | ${totalSqlTableCount} 张 |\n`;
  if (totalEntityCount > 0) md += `| 实体类 | ${totalEntityCount} 个 |\n`;
  if (totalViewCount > 0) md += `| 前端页面 | ${totalViewCount} 个 |\n`;
  // 前端API封装统计
  const apiCountEntries = Object.entries(projectDocs.frontendApiCount);
  if (apiCountEntries.length > 0) {
    const totalFrontendApis = apiCountEntries.reduce((s, [, v]) => s + v.count, 0);
    md += `| 前端 API 封装 | ${totalFrontendApis} 个 |\n`;
  }
  md += `\n`;

  // === 项目简介（从源码README提取） ===
  if (projectDocs.backendDesc || projectDocs.frontendDesc) {
    md += `## 项目简介\n\n`;
    if (projectDocs.backendDesc) {
      md += `${escapeHtml(projectDocs.backendDesc)}\n\n`;
    }
    if (projectDocs.frontendDesc && projectDocs.frontendDesc !== projectDocs.backendDesc) {
      md += `**前端：** ${escapeHtml(projectDocs.frontendDesc.substring(0, 200))}${projectDocs.frontendDesc.length > 200 ? '...' : ''}\n\n`;
    }
  }

  md += buildScreenshotSection(number);

  // === 技术栈 ===
  md += `## 技术栈\n\n`;
  md += `### 后端\n\n`;
  if (backendTech.length > 0) { for (const t of backendTech) md += `- ${t}\n`; }
  else { md += `- Spring Boot 3.x\n- MyBatis-Plus\n- MySQL 8.0\n`; }
  md += `\n`;

  md += `### 前端\n\n`;
  if (frontendTech.length > 0) { for (const t of frontendTech) md += `- ${t}\n`; }
  else if (projectType === 'miniprogram') { md += `- 微信小程序原生开发\n- WXML + WXSS + JavaScript\n`; }
  else { md += `- Vue 3\n- Element Plus\n`; }
  md += `\n`;

  // === 角色权限（从源码文档提取） ===
  // 如果没有显式角色信息，从默认账号的角色列推导
  if (projectDocs.roles.length === 0 && projectDocs.defaultAccounts.length > 0) {
    const roleSet = new Set();
    for (const acc of projectDocs.defaultAccounts) {
      if (acc.role && acc.role.length > 0 && acc.role.length < 20 && !roleSet.has(acc.role)) {
        roleSet.add(acc.role);
        projectDocs.roles.push({ name: acc.role, permissions: ['基本操作权限'] });
      }
    }
  }

  // 对于只有"基本操作权限"的角色，从功能模块推导更有意义的权限
  const allModules = modules.map(m => m.name);
  // 如果没有模块数据，尝试从源数据的 Controller 推导
  if (allModules.length === 0 && sd.controllers && sd.controllers.length > 0) {
    for (const c of sd.controllers) {
      allModules.push(c.name + '管理');
    }
  }
  const hasOnlyGeneric = projectDocs.roles.every(r => r.permissions.length === 1 && r.permissions[0] === '基本操作权限');
  if (hasOnlyGeneric && projectDocs.roles.length > 0 && allModules.length > 0) {
    for (const role of projectDocs.roles) {
      const rn = role.name;
      if (rn.includes('管理') || rn === 'ADMIN' || rn === 'admin') {
        // 管理员拥有所有功能模块权限
        role.permissions = allModules.map(m => m + '管理');
      } else {
        // 其他角色：根据角色名匹配相关模块
        const rolePerms = [];
        // 角色名中的关键字匹配模块
        for (const m of allModules) {
          // 如果模块名包含角色名，或角色名包含模块名的核心词
          if (m.includes(rn) || rn.includes(m.replace(/管理$/, ''))) {
            rolePerms.push(m);
          }
        }
        // 如果没匹配到，给前几个非管理模块
        if (rolePerms.length === 0) {
          const nonAdminModules = allModules.filter(m => !m.includes('管理') && !m.includes('系统') && !m.includes('配置'));
          rolePerms.push(...nonAdminModules.slice(0, 3));
        }
        if (rolePerms.length > 0) {
          role.permissions = rolePerms.length > 6 ? [...rolePerms.slice(0, 6), '其他操作权限'] : rolePerms;
        }
      }
    }
  }

  if (projectDocs.roles.length > 0) {
    md += `## 角色权限\n\n`;
    md += `系统支持 **${projectDocs.roles.length}** 种角色，各角色权限如下：\n\n`;
    for (const role of projectDocs.roles) {
      md += `### ${role.name}\n\n`;
      for (const p of role.permissions.slice(0, 8)) {
        md += `- ${escapeHtml(p)}\n`;
      }
      if (role.permissions.length > 8) md += `- ...还有 ${role.permissions.length - 8} 项权限\n`;
      md += `\n`;
    }
  }

  // === 功能模块 ===
  if (modules.length > 0) {
    md += `## 功能模块\n\n`;
    md += `| 模块 | 说明 |\n`;
    md += `|------|------|\n`;
    for (const m of modules) md += `| **${escapeHtml(m.name)}** | ${escapeHtml(m.description)} |\n`;
    md += `\n`;

    md += `### 模块架构\n\n`;
    md += `\`\`\`\n${title}\n`;
    md += `├── ${modules.map(m => escapeHtml(m.name)).join('\n├── ')}\n`;
    md += `\`\`\`\n\n`;
  }

  // === 特色亮点 ===
  if (highlights.length > 0) {
    md += `## 特色亮点\n\n`;
    for (const h of highlights) md += `- ${escapeHtml(h)}\n`;
    md += `\n`;
  }
  // 补充源码文档中的技术亮点
  if (projectDocs.techHighlights.length > 0) {
    if (highlights.length === 0) md += `## 技术亮点\n\n`;
    for (const h of projectDocs.techHighlights.slice(0, 8)) md += `- ${escapeHtml(h)}\n`;
    md += `\n`;
  }

  // === 演示流程 ===
  if (demoSteps.length > 0) {
    md += `## 演示流程\n\n`;
    for (let i = 0; i < demoSteps.length; i++) md += `${i + 1}. ${escapeHtml(demoSteps[i])}\n`;
    md += `\n`;
  }

  // === 数据库设计 ===
  if (sd.sqlTables && sd.sqlTables.length > 0) {
    // 去重：同一表名只保留第一次出现
    const seenTables = new Set();
    const uniqueTables = sd.sqlTables.filter(t => {
      if (seenTables.has(t.name)) return false;
      seenTables.add(t.name);
      return true;
    });

    md += `## 数据库设计\n\n`;
    md += `本项目共包含 **${uniqueTables.length}** 张数据库表。\n\n`;

    const tablesToShow = uniqueTables.slice(0, 10);
    for (const table of tablesToShow) {
      md += `### ${table.name}\n\n`;
      md += `| 字段 | 类型 | 说明 |\n`;
      md += `|------|------|------|\n`;
      for (const col of table.columns.slice(0, 8)) {
        const extra = col.extra ? escapeHtml(col.extra.substring(0, 50)) : '';
        md += `| \`${col.name}\` | ${col.type} | ${extra} |\n`;
      }
      if (table.columns.length > 8) md += `| ... | ... | 更多字段请查看源码 |\n`;
      md += `\n`;
    }

    if (uniqueTables.length > 10) {
      md += `> 还有 ${uniqueTables.length - 10} 张表未展示，完整表结构请查看 \`${number}-backend/src/main/resources/sql/init.sql\`\n\n`;
    }
  }

  // === API 接口（优先使用源码文档中的详细分组） ===
  if (projectDocs.apiDetails.length > 0) {
    md += `## API 接口\n\n`;
    const totalApis = projectDocs.apiDetails.reduce((s, g) => s + g.apis.length, 0);
    md += `本项目共包含 **${projectDocs.apiDetails.length}** 个接口分组，**${totalApis}** 个 API 接口。\n\n`;

    for (const group of projectDocs.apiDetails.slice(0, 10)) {
      md += `### ${group.group}\n\n`;
      md += `| 方法 | 路径 | 说明 |\n`;
      md += `|------|------|------|\n`;
      for (const api of group.apis.slice(0, 10)) {
        md += `| ${api.method} | \`${escapeHtml(api.path)}\` | ${escapeHtml(api.desc)} |\n`;
      }
      if (group.apis.length > 10) md += `| ... | 还有 ${group.apis.length - 10} 个接口 | |\n`;
      md += `\n`;
    }
    if (projectDocs.apiDetails.length > 10) {
      md += `> 还有 ${projectDocs.apiDetails.length - 10} 个接口分组未展示。\n\n`;
    }
  } else if (sd.controllers && sd.controllers.length > 0) {
    // 回退：使用源码提取的Controller信息
    md += `## API 接口\n\n`;
    md += `本项目共包含 **${sd.controllers.length}** 个 Controller，**${totalApiCount}** 个 API 接口。\n\n`;

    for (const controller of sd.controllers.slice(0, 8)) {
      md += `### ${controller.name}\n\n`;
      md += `| 方法 | 路径 |\n`;
      md += `|------|------|\n`;
      for (const api of controller.apis.slice(0, 6)) {
        if (api.method === 'ALL' && api.path.includes('/api/')) continue; // 跳过转发路由
        md += `| ${api.method} | \`${escapeHtml(api.path)}\` |\n`;
      }
      if (controller.apis.length > 6) md += `| ... | 还有 ${controller.apis.length - 6} 个接口 |\n`;
      md += `\n`;
    }
    if (sd.controllers.length > 8) {
      md += `> 还有 ${sd.controllers.length - 8} 个 Controller 未展示。\n\n`;
    }
  }

  // === 前端页面 ===
  if (sd.views && sd.views.length > 0) {
    md += `## 前端页面\n\n`;
    md += `本项目前端共包含 **${sd.views.length}** 个页面组件。\n\n`;

    // 按目录分组
    const viewGroups = {};
    for (const view of sd.views) {
      const dir = view.path.includes('/') ? view.path.split('/')[0] : '根目录';
      if (!viewGroups[dir]) viewGroups[dir] = [];
      viewGroups[dir].push(view);
    }

    md += `| 目录 | 页面 | 路径 |\n`;
    md += `|------|------|------|\n`;
    for (const [dir, views] of Object.entries(viewGroups).slice(0, 15)) {
      for (const view of views.slice(0, 5)) {
        md += `| ${dir} | ${view.name} | \`${view.path}\` |\n`;
      }
      if (views.length > 5) md += `| ${dir} | ... | 还有 ${views.length - 5} 个页面 |\n`;
    }
    md += `\n`;
  }

  // === 前端API封装统计 ===
  const feApiEntries = Object.entries(projectDocs.frontendApiCount);
  if (feApiEntries.length > 0) {
    md += `### 前端 API 封装\n\n`;
    md += `| 文件 | 说明 | 接口数量 |\n`;
    md += `|------|------|----------|\n`;
    for (const [file, info] of feApiEntries) {
      md += `| \`${file}\` | ${info.desc} | ${info.count} 个 |\n`;
    }
    md += `\n`;
  }

  // === 项目结构（从源码README提取，或从源数据生成） ===
  if (projectDocs.projectStructure) {
    md += `## 项目结构\n\n`;
    md += `\`\`\`\n${projectDocs.projectStructure}\n\`\`\`\n\n`;
  } else {
    // Generate tree from source data
    md += `## 项目结构\n\n`;
    md += `\`\`\`\n`;
    md += `${number}-backend/\n`;
    md += `├── src/main/java/com/xiaou/\n`;
    if (sd.controllers && sd.controllers.length > 0) {
      md += `│   ├── controller/     # 控制器 (${sd.controllers.length} 个)\n`;
    } else {
      md += `│   ├── controller/     # 控制器\n`;
    }
    if (sd.entities && sd.entities.length > 0) {
      md += `│   ├── entity/         # 实体类 (${sd.entities.length} 个)\n`;
    } else {
      md += `│   ├── entity/         # 实体类\n`;
    }
    md += `│   ├── mapper/         # MyBatis映射\n`;
    md += `│   ├── service/        # 业务逻辑\n`;
    md += `│   ├── dto/            # 数据传输对象\n`;
    md += `│   ├── config/         # 配置类\n`;
    md += `│   └── common/         # 通用类\n`;
    md += `├── src/main/resources/\n`;
    md += `│   ├── application.yml # 配置文件\n`;
    md += `│   └── sql/            # 数据库脚本\n`;
    md += `└── pom.xml             # Maven配置\n`;
    md += `\n`;
    if (projectType === 'miniprogram') {
      md += `${number}-miniprogram/\n`;
      if (sd.views && sd.views.length > 0) {
        md += `├── pages/             # 页面 (${sd.views.length} 个)\n`;
      } else {
        md += `├── pages/             # 页面\n`;
      }
      md += `├── utils/             # 工具类\n`;
      md += `├── app.js             # 应用入口\n`;
      md += `├── app.json           # 应用配置\n`;
      md += `└── app.wxss           # 全局样式\n`;
    } else {
      md += `${number}-frontend/\n`;
      if (sd.views && sd.views.length > 0) {
        md += `├── src/views/         # 页面组件 (${sd.views.length} 个)\n`;
      } else {
        md += `├── src/views/         # 页面组件\n`;
      }
      md += `├── src/api/           # API封装\n`;
      md += `├── src/router/        # 路由配置\n`;
      md += `├── src/utils/         # 工具类\n`;
      md += `├── package.json       # 依赖配置\n`;
      md += `└── vite.config.js     # Vite配置\n`;
    }
    md += `\`\`\`\n\n`;
  }

  // === 快速启动 ===
  md += `## 快速启动\n\n`;
  md += `### 环境要求\n\n`;
  md += `| 工具 | 版本 |\n`;
  md += `|------|------|\n`;
  md += `| JDK | ${backendVersion && backendVersion.startsWith('2') ? '8+ / 11+' : '17+'} |\n`;
  md += `| Maven | 3.8+ |\n`;
  md += `| MySQL | 8.0+ |\n`;
  if (projectType !== 'miniprogram') md += `| Node.js | 18+ |\n`;
  md += `\n`;

  md += `### 后端启动\n\n`;
  md += `\`\`\`bash\n`;
  md += `cd ${number}-backend\n`;
  md += `# 1. 创建数据库并执行 init.sql\n`;
  md += `# 2. 修改 application.yml 数据库配置\n`;
  md += `# spring.datasource.url=jdbc:mysql://localhost:3306/${dbName}\n`;
  md += `# spring.datasource.username=root\n`;
  md += `# spring.datasource.password=你的密码\n`;
  md += `mvn spring-boot:run\n`;
  md += `\`\`\`\n\n`;

  if (projectType === 'miniprogram') {
    md += `### 小程序启动\n\n`;
    md += `1. 使用微信开发者工具打开 \`${number}-miniprogram\` 目录\n`;
    md += `2. 确认 AppID 配置正确\n`;
    md += `3. 修改后端接口地址（通常在 config.js 或 utils/request.js 中）\n`;
    md += `4. 点击编译运行\n\n`;
  } else {
    md += `### 前端启动\n\n`;
    md += `\`\`\`bash\n`;
    md += `cd ${number}-frontend\n`;
    md += `npm install\n`;
    md += `# 修改后端接口地址（通常在 .env.development 或 src/utils/request.js 中）\n`;
    md += `npm run dev\n`;
    md += `\`\`\`\n\n`;
  }

  // === 默认账号（优先使用源码文档中的表格格式） ===
  md += `### 默认账号\n\n`;
  if (projectDocs.defaultAccounts.length > 0) {
    md += `| 角色 | 用户名 | 密码 | 说明 |\n`;
    md += `|------|--------|------|------|\n`;
    for (const acc of projectDocs.defaultAccounts) {
      md += `| ${escapeHtml(acc.role)} | \`${acc.username}\` | \`${acc.password}\` | ${escapeHtml(acc.note)} |\n`;
    }
    md += `\n`;
  } else if (checkReport?.defaultAccount) {
    md += `- 管理员：\`${checkReport.defaultAccount}\`\n\n`;
  } else {
    md += `- 管理员：\`admin / admin123\` 或 \`admin / 123456\`\n`;
    md += `- 具体账号密码请查看项目 README.md\n\n`;
  }

  // === 开发说明 ===
  if (projectDocs.devNotes.length > 0) {
    md += `### 开发注意事项\n\n`;
    for (let i = 0; i < projectDocs.devNotes.slice(0, 6).length; i++) {
      md += `${i + 1}. ${escapeHtml(projectDocs.devNotes[i])}\n`;
    }
    md += `\n`;
  } else {
    // Auto-generate basic dev notes from source data
    md += `### 开发注意事项\n\n`;
    md += `1. 数据库配置：修改 \`application.yml\` 中的数据库连接信息`;
    if (dbName && dbName !== `db_${number}`) md += `（数据库名：\`${dbName}\`）`;
    md += `\n`;
    md += `2. 后端启动前需先创建数据库并执行 SQL 初始化脚本\n`;
    if (port) md += `3. 后端默认端口为 \`${port}\`，前端代理需配置对应端口\n`;
    md += `4. 默认账号密码请查看上方表格，首次登录后建议修改密码\n`;
    md += `\n`;
  }

  // === 检查报告 ===
  if (checkReport?.hasReport) {
    md += `## 检查报告\n\n`;
    md += `**检查日期：** ${checkReport.checkDate || '未知'}\n\n`;
    if (checkReport.conclusion) {
      md += `**综合结论：** ${checkReport.conclusion}\n\n`;
    }
    if (checkReport.issues.length > 0) {
      md += `### 已知问题\n\n`;
      md += `| 编号 | 问题 | 严重度 | 状态 |\n`;
      md += `|------|------|--------|------|\n`;
      for (const issue of checkReport.issues) {
        md += `| ${issue.id} | ${escapeHtml(issue.desc)} | ${issue.severity} | ${issue.status} |\n`;
      }
      md += `\n`;
    }
    md += `> 完整检查报告请查看 \`docs/checks/${checkReport.fileName}\`\n\n`;
  }

  fs.writeFileSync(path.join(PROJECTS_DIR, `${number}.md`), md, 'utf-8');
}

console.log(`已生成 ${projects.length} 个项目页面`);

// === 分类索引页 ===
const catDir = path.join(DOCS_SITE_DIR, 'categories');
if (!fs.existsSync(catDir)) fs.mkdirSync(catDir, { recursive: true });

for (const [catKey, cat] of Object.entries(categories)) {
  if (cat.projects.length === 0) continue;
  let md = `---\ntitle: ${cat.name}\ndescription: ${cat.desc}\n---\n\n`;
  md += `# ${cat.name}\n\n> ${cat.desc}\n\n`;
  for (const project of cat.projects) {
    md += `## [${project.number} - ${project.title}](/projects/${project.number})\n\n`;
    md += `${project.projectName}\n\n`;
    if (project.modules.length > 0) md += `**核心模块：** ${project.modules.map(m => m.name).join('、')}\n\n`;
    md += `**技术栈：** `;
    const techs = [...project.backendTech.slice(0, 2), ...project.frontendTech.slice(0, 2)].filter(Boolean);
    md += techs.length > 0 ? techs.join('、') : 'Spring Boot + Vue';
    if (project.totalApiCount > 0) md += ` | **API：** ${project.totalApiCount} 个`;
    if (project.totalSqlTableCount > 0) md += ` | **表：** ${project.totalSqlTableCount} 张`;
    if (project.totalViewCount > 0) md += ` | **页面：** ${project.totalViewCount} 个`;
    md += `\n\n---\n\n`;
  }
  fs.writeFileSync(path.join(catDir, `${catKey}.md`), md, 'utf-8');
}

// === 生成 JSON 数据 ===
const sidebarData = {};
for (const [catKey, cat] of Object.entries(categories)) {
  if (cat.projects.length === 0) continue;
  sidebarData[catKey] = { name: cat.name, items: cat.projects.map(p => ({ number: p.number, title: p.title })) };
}
fs.writeFileSync(path.join(DOCS_SITE_DIR, '.vitepress', 'projects-data.json'), JSON.stringify(sidebarData, null, 2), 'utf-8');

const projectList = projects.map(p => ({
  number: p.number, title: p.title, category: p.category,
  projectType: p.projectType, backendVersion: p.backendVersion,
  totalApiCount: p.totalApiCount, totalControllerCount: p.totalControllerCount,
  totalEntityCount: p.totalEntityCount, totalSqlTableCount: p.totalSqlTableCount,
  totalViewCount: p.totalViewCount,
  backendTech: p.backendTech, frontendTech: p.frontendTech,
  modules: p.modules.map(m => m.name)
}));
fs.writeFileSync(path.join(DOCS_SITE_DIR, '.vitepress', 'project-list.json'), JSON.stringify(projectList, null, 2), 'utf-8');

console.log('\n分类统计:');
for (const [catKey, cat] of Object.entries(categories)) {
  if (cat.projects.length > 0) console.log(`  ${cat.name}: ${cat.projects.length} 个项目`);
}
