/**
 * 生成对比选择指南所需的前端数据
 * 用法: node scripts/generate-guide-data.mjs
 */
import fs from 'fs';
import path from 'path';
import { fileURLToPath } from 'url';

const __dirname = path.dirname(fileURLToPath(import.meta.url));
const ROOT = path.resolve(__dirname, '..');
const DOCS_SITE_DIR = path.join(ROOT, 'docs-site');

// 读取项目列表数据
const projectList = JSON.parse(
  fs.readFileSync(path.join(DOCS_SITE_DIR, '.vitepress', 'project-list.json'), 'utf-8')
);

// 读取源码数据
const sourceData = JSON.parse(
  fs.readFileSync(path.join(DOCS_SITE_DIR, '.vitepress', 'source-data.json'), 'utf-8')
);

// 分类映射
const categoryNames = {
  campus: '校园生活',
  education: '教育培训',
  ecommerce: '电商交易',
  health: '医疗健康',
  community: '社区服务',
  culture: '文化娱乐',
  enterprise: '企业管理',
  travel: '旅游出行',
  agriculture: '农业环保',
  'ai-tech': 'AI与前沿技术'
};

// === 技术栈统计 ===
const techStats = {
  backend: {},
  frontend: {},
  springBootVersions: {},
  dbTypes: {}
};

for (const project of projectList) {
  const sd = sourceData[project.number] || {};

  // 后端技术统计
  for (const tech of project.backendTech) {
    const key = tech.replace(/\s+/g, ' ').trim();
    techStats.backend[key] = (techStats.backend[key] || 0) + 1;
  }

  // 前端技术统计
  for (const tech of project.frontendTech) {
    const key = tech.replace(/\s+/g, ' ').trim();
    techStats.frontend[key] = (techStats.frontend[key] || 0) + 1;
  }

  // Spring Boot 版本统计
  if (project.backendVersion) {
    const v = project.backendVersion;
    techStats.springBootVersions[v] = (techStats.springBootVersions[v] || 0) + 1;
  }
}

// === 复杂度分级 ===
function getComplexity(project) {
  const score =
    (project.totalApiCount || 0) * 1 +
    (project.totalControllerCount || 0) * 3 +
    (project.totalSqlTableCount || 0) * 2 +
    (project.totalViewCount || 0) * 1 +
    (project.modules?.length || 0) * 5;

  if (score <= 80) return '简单';
  if (score <= 180) return '中等';
  return '复杂';
}

// === 生成对比数据 ===
const guideData = projectList.map(project => {
  const sd = sourceData[project.number] || {};
  const complexity = getComplexity(project);

  return {
    number: project.number,
    title: project.title,
    category: project.category,
    categoryName: categoryNames[project.category] || project.category,
    projectType: project.projectType,
    backendVersion: project.backendVersion,
    complexity,
    totalApiCount: project.totalApiCount,
    totalControllerCount: project.totalControllerCount,
    totalEntityCount: project.totalEntityCount,
    totalSqlTableCount: project.totalSqlTableCount,
    totalViewCount: project.totalViewCount,
    backendTech: project.backendTech,
    frontendTech: project.frontendTech,
    modules: project.modules,
    dbName: sd.appConfig?.dbName || '',
    port: sd.appConfig?.port || '',
  };
});

// 按复杂度统计
const complexityStats = { '简单': 0, '中等': 0, '复杂': 0 };
for (const p of guideData) complexityStats[p.complexity]++;

// === 按功能场景分类 ===
const scenarios = {
  '用户权限管理': { desc: '包含多角色登录、权限控制、菜单管理', projects: [] },
  'CRUD台账管理': { desc: '标准增删改查、列表分页、状态流转', projects: [] },
  '工作流审批': { desc: '申请-审核-驳回流程、状态机驱动', projects: [] },
  '数据可视化': { desc: 'ECharts图表、统计看板、数据分析', projects: [] },
  '文件上传下载': { desc: '文件管理、附件上传、图片预览', projects: [] },
  '消息通知': { desc: '系统通知、消息推送、站内信', projects: [] },
  '预约排期': { desc: '预约管理、排期调度、时间选择', projects: [] },
  '订单交易': { desc: '下单支付、订单管理、结算流程', projects: [] },
  '地图定位': { desc: '地图展示、位置标注、路径规划', projects: [] },
  '导入导出': { desc: 'Excel导入导出、数据批量处理', projects: [] },
  'JWT认证': { desc: 'Token登录、Redis缓存、拦截鉴权', projects: [] },
  '小程序端': { desc: '微信小程序前端、移动端适配', projects: [] },
};

// 关键词匹配
const scenarioKeywords = {
  '用户权限管理': ['账号', '权限', '角色', '登录', 'RBAC', '菜单'],
  'CRUD台账管理': ['台账', '档案', '维护', '管理'],
  '工作流审批': ['审核', '审批', '驳回', '流程', '申诉', '整改'],
  '数据可视化': ['统计', '图表', '看板', 'ECharts', '可视化', '分析'],
  '文件上传下载': ['文件', '上传', '下载', '附件', '图片', '导入', '导出'],
  '消息通知': ['通知', '消息', '推送', '公告', '站内信'],
  '预约排期': ['预约', '排期', '预订', '挂号', '订票'],
  '订单交易': ['订单', '交易', '支付', '购物', '结算', '购买'],
  '地图定位': ['地图', '定位', '位置', '路径', '导航'],
  '导入导出': ['导入', '导出', 'Excel', '批量'],
  'JWT认证': ['JWT', 'Token', 'Redis', '鉴权', '拦截'],
  '小程序端': ['小程序', 'miniprogram', 'miniapp'],
};

for (const project of guideData) {
  const sd = sourceData[project.number] || {};
  const text = [
    project.title,
    project.modules?.join(' ') || '',
    project.backendTech?.join(' ') || '',
    project.frontendTech?.join(' ') || '',
  ].join(' ');

  // 检查源码特征
  const hasFileUpload = sd.controllers?.some(c => c.apis.some(a => a.path.includes('upload') || a.path.includes('file'))) || false;
  const hasImport = sd.controllers?.some(c => c.apis.some(a => a.path.includes('import') || a.path.includes('export'))) || false;

  for (const [scenario, keywords] of Object.entries(scenarioKeywords)) {
    let matched = keywords.some(kw => text.includes(kw));
    if (scenario === '文件上传下载' && hasFileUpload) matched = true;
    if (scenario === '导入导出' && hasImport) matched = true;
    if (scenario === '小程序端' && project.projectType === 'miniprogram') matched = true;
    if (matched) scenarios[scenario].projects.push(project.number);
  }
}

// === 保存数据 ===
const outputData = {
  projects: guideData,
  techStats,
  complexityStats,
  scenarios: Object.fromEntries(
    Object.entries(scenarios).map(([key, val]) => [key, { desc: val.desc, count: val.projects.length, projects: val.projects }])
  ),
  categoryNames,
  generatedAt: new Date().toISOString()
};

// 保存到 .vitepress 目录（供构建时使用）
fs.writeFileSync(
  path.join(DOCS_SITE_DIR, '.vitepress', 'guide-data.json'),
  JSON.stringify(outputData, null, 2),
  'utf-8'
);

// 同时保存到 public 目录（供前端 fetch 使用）
const PUBLIC_DIR = path.join(DOCS_SITE_DIR, 'public');
if (!fs.existsSync(PUBLIC_DIR)) fs.mkdirSync(PUBLIC_DIR, { recursive: true });
fs.writeFileSync(
  path.join(PUBLIC_DIR, 'guide-data.json'),
  JSON.stringify(outputData),
  'utf-8'  // 不带缩进，减小文件大小
);

console.log('对比选择指南数据已生成');
console.log(`  项目总数: ${guideData.length}`);
console.log(`  复杂度分布: 简单 ${complexityStats['简单']}, 中等 ${complexityStats['中等']}, 复杂 ${complexityStats['复杂']}`);
console.log(`  功能场景: ${Object.entries(scenarios).map(([k, v]) => `${k}(${v.projects.length})`).join(', ')}`);
