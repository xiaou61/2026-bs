/**
 * 校验项目索引、生成文档和项目源码之间的关键一致性。
 * 用法: node scripts/validate-docs.mjs
 */
import fs from 'fs';
import path from 'path';
import { fileURLToPath } from 'url';

const __dirname = path.dirname(fileURLToPath(import.meta.url));
const ROOT = path.resolve(__dirname, '..');
const PROJECT_COUNT = 200;
const expectedNumbers = Array.from({ length: PROJECT_COUNT }, (_, index) => String(index + 1).padStart(3, '0'));
const failures = [];
const warnings = [];

function readText(filePath) {
  return fs.readFileSync(filePath, 'utf-8').replace(/\r\n?/g, '\n');
}

function parseProjectNumbers(content) {
  return [...content.matchAll(/^### (\d{3}) - .+$/gm)].map(match => match[1]);
}

function compareNumbers(label, actual) {
  const expected = new Set(expectedNumbers);
  const actualSet = new Set(actual);
  const missing = expectedNumbers.filter(number => !actualSet.has(number));
  const extra = actual.filter(number => !expected.has(number));
  const duplicates = actual.filter((number, index) => actual.indexOf(number) !== index);

  if (missing.length > 0) failures.push(`${label} 缺少: ${missing.join(', ')}`);
  if (extra.length > 0) failures.push(`${label} 多出: ${[...new Set(extra)].join(', ')}`);
  if (duplicates.length > 0) failures.push(`${label} 重复: ${[...new Set(duplicates)].join(', ')}`);
}

function listMarkdownFiles(dir) {
  if (!fs.existsSync(dir)) return [];
  return fs.readdirSync(dir, { withFileTypes: true })
    .filter(entry => entry.isFile() && entry.name.endsWith('.md'))
    .map(entry => entry.name);
}

const rootReadmePath = path.join(ROOT, 'readme.md');
const simpleReadmePath = path.join(ROOT, 'readme_simple.md');
const projectPagesDir = path.join(ROOT, 'docs-site', 'projects');
const projectListPath = path.join(ROOT, 'docs-site', '.vitepress', 'project-list.json');
const sourceDataPath = path.join(ROOT, 'docs-site', '.vitepress', 'source-data.json');
const configPath = path.join(ROOT, 'docs-site', '.vitepress', 'config.mts');

if (!fs.existsSync(rootReadmePath)) failures.push('缺少根 readme.md');
if (!fs.existsSync(simpleReadmePath)) failures.push('缺少 readme_simple.md');
if (!fs.existsSync(projectPagesDir)) failures.push('缺少 docs-site/projects 目录');

if (fs.existsSync(simpleReadmePath)) {
  compareNumbers('readme_simple.md', parseProjectNumbers(readText(simpleReadmePath)));
}

const pageNumbers = listMarkdownFiles(projectPagesDir)
  .map(file => file.match(/^(\d{3})\.md$/)?.[1])
  .filter(Boolean);
compareNumbers('docs-site/projects', pageNumbers);

for (const fileName of ['project-list.json', 'source-data.json']) {
  const filePath = path.join(ROOT, 'docs-site', '.vitepress', fileName);
  if (!fs.existsSync(filePath)) {
    failures.push(`缺少 docs-site/.vitepress/${fileName}`);
    continue;
  }

  try {
    const data = JSON.parse(readText(filePath));
    const numbers = fileName === 'project-list.json'
      ? data.map(project => project.number)
      : Object.keys(data);
    compareNumbers(`docs-site/.vitepress/${fileName}`, numbers);
  } catch (error) {
    failures.push(`无法解析 ${fileName}: ${error.message}`);
  }
}

if (fs.existsSync(rootReadmePath)) {
  const rootReadme = readText(rootReadmePath);
  const localLinks = [...rootReadme.matchAll(/\[[^\]]+\]\(([^)]+)\)/g)]
    .map(match => match[1])
    .filter(target => !/^(?:https?:\/\/|#)/.test(target));

  for (const target of localLinks) {
    const relativeTarget = target.split('#')[0];
    if (relativeTarget && !fs.existsSync(path.resolve(ROOT, relativeTarget))) {
      failures.push(`readme.md 本地链接不存在: ${target}`);
    }
  }

  if (!rootReadme.includes('<a name="项目索引"></a>')) {
    failures.push('readme.md 缺少“项目索引”锚点');
  }
}

if (fs.existsSync(configPath) && /ignoreDeadLinks:\s*true/.test(readText(configPath))) {
  failures.push('VitePress 仍然开启 ignoreDeadLinks: true');
}

for (const number of expectedNumbers) {
  const pagePath = path.join(projectPagesDir, `${number}.md`);
  if (!fs.existsSync(pagePath)) continue;

  const page = readText(pagePath);
  if (/^- ## /m.test(page)) failures.push(`${number}.md 包含被错误当作列表项的标题`);

  const overviewPort = page.match(/\| 后端默认端口 \| `(\d+)` \|/i)?.[1];
  const notePorts = [...page.matchAll(/后端默认端口(?:为|是)\s*`(\d+)`/g)].map(match => match[1]);
  if (overviewPort && notePorts.some(port => port !== overviewPort)) {
    failures.push(`${number}.md 的概览端口与启动说明不一致`);
  }

  const overviewTables = page.match(/\| 数据库表 \| (\d+) 张 \|/i)?.[1];
  const detailTables = page.match(/本项目共包含 \*\*(\d+)\*\* 张数据库表/i)?.[1];
  if (overviewTables && detailTables && overviewTables !== detailTables) {
    failures.push(`${number}.md 的数据库表数量不一致`);
  }

  const pomPath = path.join(ROOT, `${number}-backend`, 'pom.xml');
  const javaVersion = fs.existsSync(pomPath)
    ? readText(pomPath).match(/<java\.version>\s*([^<]+)\s*<\/java\.version>/)?.[1]
    : null;
  const pageJdk = page.match(/\| JDK \| ([^|]+) \|/i)?.[1];
  if (javaVersion && pageJdk && !pageJdk.includes(javaVersion.trim())) {
    failures.push(`${number}.md 的 JDK 要求与 pom.xml 不一致`);
  }

  const hasBackend = fs.existsSync(path.join(ROOT, `${number}-backend`));
  const hasFrontend = fs.existsSync(path.join(ROOT, `${number}-frontend`));
  if (!hasBackend && page.includes(`cd ${number}-backend`)) {
    failures.push(`${number}.md 为不存在的后端目录生成了启动命令`);
  }
  if (!hasFrontend && page.includes(`cd ${number}-frontend`)) {
    failures.push(`${number}.md 为不存在的前端目录生成了启动命令`);
  }
}

const moduleDirs = fs.readdirSync(ROOT, { withFileTypes: true })
  .filter(entry => entry.isDirectory() && /^\d{3}-(backend|frontend|miniprogram|miniapp)$/.test(entry.name));
const missingReadmes = moduleDirs.filter(entry => {
  const dir = path.join(ROOT, entry.name);
  return !fs.existsSync(path.join(dir, 'README.md')) && !fs.existsSync(path.join(dir, 'README_SIMPLE.md'));
});
if (missingReadmes.length > 0) {
  warnings.push(`${missingReadmes.length} 个模块目录尚未提供 README，项目页仍可作为统一入口`);
}

if (failures.length > 0) {
  console.error('文档校验失败:');
  for (const failure of failures) console.error(`- ${failure}`);
  process.exitCode = 1;
} else {
  console.log(`文档校验通过: ${PROJECT_COUNT} 个项目索引、项目页和生成数据一致。`);
}

for (const warning of warnings) console.warn(`警告: ${warning}`);
