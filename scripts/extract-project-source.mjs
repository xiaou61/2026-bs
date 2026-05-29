/**
 * 从项目源码提取实际信息（controllers、entities、views、routes）
 * 用法: node scripts/extract-project-source.mjs
 */
import fs from 'fs';
import path from 'path';
import { fileURLToPath } from 'url';

const __dirname = path.dirname(fileURLToPath(import.meta.url));
const ROOT = path.resolve(__dirname, '..');

// 递归查找文件
function findFiles(dir, pattern, maxDepth = 5, depth = 0) {
  if (depth > maxDepth || !fs.existsSync(dir)) return [];
  const results = [];
  try {
    for (const entry of fs.readdirSync(dir, { withFileTypes: true })) {
      const fullPath = path.join(dir, entry.name);
      if (entry.isDirectory()) {
        results.push(...findFiles(fullPath, pattern, maxDepth, depth + 1));
      } else if (pattern.test(entry.name)) {
        results.push(fullPath);
      }
    }
  } catch (e) { /* skip unreadable dirs */ }
  return results;
}

// 从 controller 文件提取 API 接口
function extractControllers(backendDir) {
  const controllers = [];
  const files = findFiles(path.join(backendDir, 'src'), /Controller\.java$/);

  for (const file of files) {
    try {
      const content = fs.readFileSync(file, 'utf-8');
      const className = path.basename(file, '.java').replace('Controller', '');

      // 提取类级别的 @RequestMapping
      const classMapping = content.match(/@RequestMapping\s*\(\s*["']([^"']+)["']/);
      const basePath = classMapping ? classMapping[1] : '';

      // 提取方法级别的映射
      const apis = [];
      const methodRegex = /@(GetMapping|PostMapping|PutMapping|DeleteMapping|PatchMapping|RequestMapping)\s*\(\s*(?:value\s*=\s*)?["']([^"']+)["']/g;
      let m;
      while ((m = methodRegex.exec(content)) !== null) {
        apis.push({
          method: m[1].replace('Mapping', '').replace('Request', 'ALL').toUpperCase(),
          path: basePath + m[2]
        });
      }

      // 如果没有找到方法级别的，尝试无路径的映射
      if (apis.length === 0) {
        const simpleMethods = content.match(/@(GetMapping|PostMapping|PutMapping|DeleteMapping)\s*(?:\(|$)/g);
        if (simpleMethods) {
          for (const sm of simpleMethods) {
            const methodType = sm.match(/@(GetMapping|PostMapping|PutMapping|DeleteMapping)/)[1];
            apis.push({
              method: methodType.replace('Mapping', '').toUpperCase(),
              path: basePath || '/'
            });
          }
        }
      }

      if (apis.length > 0) {
        controllers.push({ name: className, basePath, apis });
      }
    } catch (e) { /* skip unreadable files */ }
  }

  return controllers;
}

// 从 entity 文件提取数据库表信息
function extractEntities(backendDir) {
  const entities = [];
  const files = findFiles(path.join(backendDir, 'src'), /(?<!Test)\.java$/);

  for (const file of files) {
    if (!file.includes('/entity/') && !file.includes('\\entity\\')) continue;
    try {
      const content = fs.readFileSync(file, 'utf-8');
      const className = path.basename(file, '.java');

      // 提取 @TableName
      const tableMatch = content.match(/@TableName\s*\(\s*["']?(\w+)["']?\s*\)/);
      const tableName = tableMatch ? tableMatch[1] : className.toLowerCase();

      // 提取字段
      const fields = [];
      const fieldRegex = /private\s+(\w+(?:<[\w\s,<>]+>)?)\s+(\w+)\s*;/g;
      let fm;
      while ((fm = fieldRegex.exec(content)) !== null) {
        // 跳过常见框架字段
        if (['serialVersionUID', 'id'].includes(fm[2])) continue;
        fields.push({ type: fm[1], name: fm[2] });
      }

      entities.push({ className, tableName, fields: fields.slice(0, 15) }); // 限制字段数
    } catch (e) { /* skip */ }
  }

  return entities;
}

// 从 SQL 文件提取表结构
function extractSqlSchema(backendDir) {
  const tables = [];
  const sqlFiles = findFiles(backendDir, /\.sql$/);

  for (const file of sqlFiles) {
    try {
      const content = fs.readFileSync(file, 'utf-8');
      // 提取 CREATE TABLE 语句 - 使用贪婪匹配到最后的 ) 后跟 ; 或 ENGINE
      const tableRegex = /CREATE\s+TABLE\s+[`"]?(\w+)[`"]?\s*\(([\s\S]+?)\)\s*(?:ENGINE\s*=|;)/gi;
      let tm;
      while ((tm = tableRegex.exec(content)) !== null) {
        const tableName = tm[1];
        const body = tm[2];

        // 提取列定义
        const columns = [];
        const colRegex = /^\s*[`"]?(\w+)[`"]?\s+(\w+(?:\([\d,]+\))?)\s*(.*)$/gm;
        let cm;
        while ((cm = colRegex.exec(body)) !== null) {
          const colName = cm[1].toLowerCase();
          // 跳过约束行
          if (['primary', 'key', 'unique', 'index', 'constraint', 'foreign', 'check'].some(k => colName.startsWith(k))) continue;
          columns.push({
            name: cm[1],
            type: cm[2],
            extra: cm[3].trim().substring(0, 50) // 截断过长的额外信息
          });
        }

        if (columns.length > 0) {
          tables.push({ name: tableName, columns });
        }
      }
    } catch (e) { /* skip */ }
  }

  return tables;
}

// 从前端路由提取页面信息
function extractRoutes(frontendDir) {
  const routes = [];
  const routerFiles = findFiles(path.join(frontendDir, 'src'), /router.*\.js$|router.*\.ts$/);

  for (const file of routerFiles) {
    try {
      const content = fs.readFileSync(file, 'utf-8');

      // 提取路由定义
      const routeRegex = /path:\s*['"]([^'"]+)['"][\s\S]*?name:\s*['"]([^'"]*)['"][\s\S]*?(?:title|meta):\s*['"]?([^'",}]+)/g;
      let rm;
      while ((rm = routeRegex.exec(content)) !== null) {
        routes.push({
          path: rm[1],
          name: rm[2],
          title: rm[3].trim()
        });
      }

      // 简化版：只提取 path 和 title
      if (routes.length === 0) {
        const simpleRegex = /path:\s*['"]([^'"]+)['"]/g;
        let sm;
        while ((sm = simpleRegex.exec(content)) !== null) {
          if (sm[1] !== '/' && !sm[1].includes(':')) {
            routes.push({ path: sm[1], name: '', title: sm[1] });
          }
        }
      }
    } catch (e) { /* skip */ }
  }

  return routes;
}

// 从前端 views 目录提取页面组件
function extractViews(frontendDir) {
  const views = [];
  const viewsDir = path.join(frontendDir, 'src', 'views');
  if (!fs.existsSync(viewsDir)) return views;

  function scanDir(dir, prefix = '') {
    try {
      for (const entry of fs.readdirSync(dir, { withFileTypes: true })) {
        if (entry.isDirectory()) {
          scanDir(path.join(dir, entry.name), prefix ? `${prefix}/${entry.name}` : entry.name);
        } else if (entry.name.endsWith('.vue')) {
          const pageName = entry.name.replace('.vue', '');
          views.push({
            name: pageName,
            path: prefix ? `${prefix}/${pageName}` : pageName
          });
        }
      }
    } catch (e) { /* skip */ }
  }

  scanDir(viewsDir);
  return views;
}

// 从 application.yml 提取配置信息
function extractAppConfig(backendDir) {
  const config = { port: '', dbName: '', dbUrl: '', springBootVersion: '' };

  const ymlFiles = findFiles(path.join(backendDir, 'src'), /application.*\.yml$/);
  for (const file of ymlFiles) {
    try {
      const content = fs.readFileSync(file, 'utf-8');

      // 端口
      const portMatch = content.match(/port:\s*(\d+)/);
      if (portMatch && !config.port) config.port = portMatch[1];

      // 数据库 URL
      const urlMatch = content.match(/url:\s*jdbc:mysql:\/\/[^\s]+\/(\w+)/);
      if (urlMatch && !config.dbName) {
        config.dbName = urlMatch[1];
        config.dbUrl = urlMatch[0].replace(/url:\s*/, '');
      }
    } catch (e) { /* skip */ }
  }

  // 从 pom.xml 提取 Spring Boot 版本
  const pomFile = path.join(backendDir, 'pom.xml');
  if (fs.existsSync(pomFile)) {
    try {
      const pomContent = fs.readFileSync(pomFile, 'utf-8');
      const versionMatch = pomContent.match(/spring-boot-starter-parent[^>]*>\s*<version>([^<]+)<\/version>/s);
      if (versionMatch) config.springBootVersion = versionMatch[1];
    } catch (e) { /* skip */ }
  }

  return config;
}

// 主函数：提取所有项目信息
const projectList = JSON.parse(
  fs.readFileSync(path.join(ROOT, 'docs-site', '.vitepress', 'project-list.json'), 'utf-8')
);

const enhancedData = {};
let processed = 0;

for (const project of projectList) {
  const num = project.number;
  const backendDir = path.join(ROOT, `${num}-backend`);
  const frontendDir = path.join(ROOT, `${num}-frontend`);
  const miniprogramDir = path.join(ROOT, `${num}-miniprogram`);
  const miniappDir = path.join(ROOT, `${num}-miniapp`);

  const data = {
    number: num,
    controllers: [],
    entities: [],
    sqlTables: [],
    routes: [],
    views: [],
    appConfig: {},
    hasBackend: fs.existsSync(backendDir),
    hasFrontend: fs.existsSync(frontendDir),
    hasMiniprogram: fs.existsSync(miniprogramDir) || fs.existsSync(miniappDir)
  };

  // 提取后端信息
  if (data.hasBackend) {
    data.controllers = extractControllers(backendDir);
    data.entities = extractEntities(backendDir);
    data.sqlTables = extractSqlSchema(backendDir);
    data.appConfig = extractAppConfig(backendDir);
  }

  // 提取前端信息
  if (data.hasFrontend) {
    data.routes = extractRoutes(frontendDir);
    data.views = extractViews(frontendDir);
  }

  enhancedData[num] = data;
  processed++;

  if (processed % 20 === 0) {
    console.log(`  已处理 ${processed}/${projectList.length} 个项目...`);
  }
}

// 保存增强数据
fs.writeFileSync(
  path.join(ROOT, 'docs-site', '.vitepress', 'source-data.json'),
  JSON.stringify(enhancedData, null, 2),
  'utf-8'
);

// 统计
const stats = {
  total: projectList.length,
  withControllers: Object.values(enhancedData).filter(d => d.controllers.length > 0).length,
  withEntities: Object.values(enhancedData).filter(d => d.entities.length > 0).length,
  withSqlTables: Object.values(enhancedData).filter(d => d.sqlTables.length > 0).length,
  withRoutes: Object.values(enhancedData).filter(d => d.routes.length > 0).length,
  withViews: Object.values(enhancedData).filter(d => d.views.length > 0).length,
  totalControllers: Object.values(enhancedData).reduce((sum, d) => sum + d.controllers.length, 0),
  totalApis: Object.values(enhancedData).reduce((sum, d) => sum + d.controllers.reduce((s, c) => s + c.apis.length, 0), 0),
  totalEntities: Object.values(enhancedData).reduce((sum, d) => sum + d.entities.length, 0),
  totalSqlTables: Object.values(enhancedData).reduce((sum, d) => sum + d.sqlTables.length, 0),
  totalRoutes: Object.values(enhancedData).reduce((sum, d) => sum + d.routes.length, 0),
  totalViews: Object.values(enhancedData).reduce((sum, d) => sum + d.views.length, 0),
};

console.log('\n提取完成！');
console.log(`  项目总数: ${stats.total}`);
console.log(`  有 Controller 信息: ${stats.withControllers} 个项目 (${stats.totalControllers} 个 Controller, ${stats.totalApis} 个 API)`);
console.log(`  有 Entity 信息: ${stats.withEntities} 个项目 (${stats.totalEntities} 个 Entity)`);
console.log(`  有 SQL 表结构: ${stats.withSqlTables} 个项目 (${stats.totalSqlTables} 张表)`);
console.log(`  有路由信息: ${stats.withRoutes} 个项目 (${stats.totalRoutes} 条路由)`);
console.log(`  有页面组件: ${stats.withViews} 个项目 (${stats.totalViews} 个页面)`);
