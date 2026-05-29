const fs = require('fs');
const path = require('path');
const ROOT = path.resolve(__dirname, '..');
const dir = path.join(ROOT, 'docs-site', 'projects');
const files = fs.readdirSync(dir).filter(f => f.endsWith('.md'));
let stats = { total: 0, hasRoles: 0, hasRealAccounts: 0, hasDesc: 0, hasStructure: 0, hasDevNotes: 0, hasTechHL: 0, hasDB: 0, hasFrontendPages: 0, hasAPI: 0 };
let noAccounts = [], noRoles = [], noDesc = [];
for (const f of files) {
  const c = fs.readFileSync(path.join(dir, f), 'utf-8');
  const num = f.replace('.md','');
  stats.total++;
  // Roles
  if (/## 角色权限/.test(c)) stats.hasRoles++;
  else noRoles.push(num);
  // Accounts - check if has real account data (not just placeholder)
  if (/### 默认账号/.test(c)) {
    const accSection = c.match(/### 默认账号[\s\S]*?(?=\n## |\n### |$)/);
    if (accSection && (accSection[0].includes('| `') || accSection[0].includes('| ``'))) {
      if (accSection[0].includes('请查看项目 README')) noAccounts.push(num);
      else stats.hasRealAccounts++;
    } else noAccounts.push(num);
  } else noAccounts.push(num);
  // Project description
  if (/## 项目简介/.test(c)) {
    const m = c.match(/## 项目简介\s*\n\n([\s\S]*?)(?=\n## )/);
    if (m && m[1].trim().length > 10) stats.hasDesc++;
    else noDesc.push(num);
  } else noDesc.push(num);
  if (/## 项目结构/.test(c)) stats.hasStructure++;
  if (/### 开发注意事项/.test(c)) stats.hasDevNotes++;
  if (/## (特色亮点|技术亮点)/.test(c)) stats.hasTechHL++;
  if (/## 数据库设计/.test(c)) stats.hasDB++;
  if (/## 前端页面/.test(c)) stats.hasFrontendPages++;
  if (/## API 接口/.test(c)) stats.hasAPI++;
}
console.log('=== 覆盖率统计 ===');
console.log('总项目数:', stats.total);
const pct = (n) => (n/stats.total*100).toFixed(1)+'%';
console.log('有角色权限:', stats.hasRoles, pct(stats.hasRoles));
console.log('有真实账号:', stats.hasRealAccounts, pct(stats.hasRealAccounts));
console.log('有项目简介:', stats.hasDesc, pct(stats.hasDesc));
console.log('有项目结构:', stats.hasStructure, pct(stats.hasStructure));
console.log('有开发注意事项:', stats.hasDevNotes, pct(stats.hasDevNotes));
console.log('有技术亮点:', stats.hasTechHL, pct(stats.hasTechHL));
console.log('有数据库设计:', stats.hasDB, pct(stats.hasDB));
console.log('有前端页面:', stats.hasFrontendPages, pct(stats.hasFrontendPages));
console.log('有API接口:', stats.hasAPI, pct(stats.hasAPI));
console.log('\n=== 缺少账号的项目 (前40) ===');
console.log(noAccounts.slice(0,40).join(', '));
console.log('\n=== 缺少角色的项目 (前40) ===');
console.log(noRoles.slice(0,40).join(', '));
console.log('\n=== 缺少简介的项目 (前40) ===');
console.log(noDesc.slice(0,40).join(', '));