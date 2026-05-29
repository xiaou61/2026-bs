/**
 * 审计脚本 - 分析所有项目源文件的提取覆盖情况
 */
import fs from 'fs';
import path from 'path';
import { fileURLToPath } from 'url';

const __dirname = path.dirname(fileURLToPath(import.meta.url));
const ROOT = path.resolve(__dirname, '..');

const roleTitles = ['权限说明', '系统功能', '核心功能', '角色权限', '角色说明', '功能概览', '用户角色', '功能设计', '功能模块'];

const dirs = fs.readdirSync(ROOT).filter(d => /^\d{3}-backend$/.test(d) && fs.existsSync(path.join(ROOT, d, 'README.md')));

const results = {
  noRoleSection: [],
  withRoleButNotExtracted: [],
  accountFormats: {},
  allSectionHeaders: {}
};

for (const dir of dirs) {
  const content = fs.readFileSync(path.join(ROOT, dir, 'README.md'), 'utf-8');
  const num = dir.replace('-backend', '');

  // 提取所有 ## 标题
  const headers = [...content.matchAll(/^## (.+)$/gm)].map(m => m[1].trim());

  let hasRole = false;
  let roleTitle = '';
  for (const t of roleTitles) {
    if (content.includes('## ' + t)) { hasRole = true; roleTitle = t; break; }
  }

  if (!hasRole) {
    // 检查是否有其他功能标题 + ### 子标题
    const altTitles = ['功能列表', '功能', '主要功能', '模块说明', '项目功能', '功能介绍'];
    let altFound = false;
    for (const t of altTitles) {
      if (content.includes('## ' + t)) {
        const funcMatch = content.match(new RegExp('## ' + t + '\\s*\\n([\\s\\S]*?)(?=\\n## |$)'));
        if (funcMatch) {
          const subs = [...funcMatch[1].matchAll(/### (.+)$/gm)].map(m => m[1].trim());
          if (subs.length > 0) {
            results.noRoleSection.push({ num, altTitle: t, subs });
            altFound = true;
            break;
          }
        }
      }
    }
    if (!altFound) {
      results.noRoleSection.push({ num, headers: headers.slice(0, 10), noAltSection: true });
    }
  }

  // 统计 section headers
  for (const h of headers) {
    results.allSectionHeaders[h] = (results.allSectionHeaders[h] || 0) + 1;
  }
}

// ACCOUNTS.md 格式分析
const accDirs = fs.readdirSync(ROOT).filter(d => /^\d{3}-backend$/.test(d) && fs.existsSync(path.join(ROOT, d, 'ACCOUNTS.md')));
for (const dir of accDirs) {
  const content = fs.readFileSync(path.join(ROOT, dir, 'ACCOUNTS.md'), 'utf-8');
  const num = dir.replace('-backend', '');
  let format = 'unknown';
  if (content.match(/### .+?账号/g)) format = 'subsection';
  else if (content.includes('| 角色') || content.includes('| 类型')) format = '4col-table';
  else if (content.match(/\|[^|]+\|[^|]+\|[^|]+\|/g)) format = '3col-table';
  else if (content.match(/-\s+\*\*.+?\*\*[:：]/g)) format = 'bold-line';
  else if (content.match(/-\s+.+?[:：]\s*\S+\s*\/\s*\S+/g)) format = 'plain-line';
  results.accountFormats[num] = format;
}

console.log('=== 没有标准角色章节的 README ===');
console.log('有替代章节(功能列表等):');
for (const r of results.noRoleSection.filter(r => r.altTitle)) {
  console.log(`  ${r.num}: ## ${r.altTitle}, subs: ${JSON.stringify(r.subs)}`);
}
console.log('\n完全没有功能/角色章节:');
for (const r of results.noRoleSection.filter(r => r.noAltSection)) {
  console.log(`  ${r.num}: headers = ${JSON.stringify(r.headers)}`);
}

console.log('\n=== 所有 ## 标题频率 (Top 30) ===');
const sortedHeaders = Object.entries(results.allSectionHeaders).sort((a, b) => b[1] - a[1]);
for (const [h, c] of sortedHeaders.slice(0, 30)) {
  console.log(`  ${c}x: ## ${h}`);
}

console.log('\n=== ACCOUNTS.md 格式分布 ===');
const formatCounts = {};
for (const [num, format] of Object.entries(results.accountFormats)) {
  formatCounts[format] = (formatCounts[format] || 0) + 1;
}
for (const [format, count] of Object.entries(formatCounts)) {
  console.log(`  ${format}: ${count} 个`);
}