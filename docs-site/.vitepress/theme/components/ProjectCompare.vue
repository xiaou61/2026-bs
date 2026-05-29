<template>
  <div class="guide-container">
    <!-- 筛选区域 -->
    <div class="filter-section">
      <h3>筛选条件</h3>
      <div class="filter-group">
        <label>项目分类：</label>
        <select v-model="filters.category" @change="applyFilters">
          <option value="">全部</option>
          <option v-for="(name, key) in categoryNames" :key="key" :value="key">{{ name }}</option>
        </select>
      </div>
      <div class="filter-group">
        <label>复杂度：</label>
        <select v-model="filters.complexity" @change="applyFilters">
          <option value="">全部</option>
          <option value="简单">简单</option>
          <option value="中等">中等</option>
          <option value="复杂">复杂</option>
        </select>
      </div>
      <div class="filter-group">
        <label>Spring Boot 版本：</label>
        <select v-model="filters.springBootVersion" @change="applyFilters">
          <option value="">全部</option>
          <option value="2.x">2.x 系列</option>
          <option value="3.x">3.x 系列</option>
        </select>
      </div>
      <div class="filter-group">
        <label>项目类型：</label>
        <select v-model="filters.projectType" @change="applyFilters">
          <option value="">全部</option>
          <option value="web">Web 应用</option>
          <option value="miniprogram">微信小程序</option>
        </select>
      </div>
      <div class="filter-group">
        <label>关键词搜索：</label>
        <input type="text" v-model="filters.search" @input="applyFilters" placeholder="输入项目名称或模块关键词..." class="search-input" />
      </div>
      <div class="filter-actions">
        <button @click="resetFilters" class="reset-btn">重置筛选</button>
        <span class="result-count">共 {{ filteredProjects.length }} 个项目</span>
      </div>
    </div>

    <!-- 对比区域 -->
    <div class="compare-section" v-if="compareList.length > 0">
      <h3>对比项目 (最多4个)</h3>
      <div class="compare-cards">
        <div v-for="p in compareList" :key="p.number" class="compare-card">
          <button @click="removeCompare(p.number)" class="remove-btn">&times;</button>
          <h4>{{ p.number }} - {{ p.title }}</h4>
          <table class="compare-table">
            <tr><td>分类</td><td>{{ p.categoryName }}</td></tr>
            <tr><td>复杂度</td><td><span :class="'complexity-' + p.complexity">{{ p.complexity }}</span></td></tr>
            <tr><td>Spring Boot</td><td>{{ p.backendVersion || '未知' }}</td></tr>
            <tr><td>API 接口</td><td>{{ p.totalApiCount }} 个</td></tr>
            <tr><td>Controller</td><td>{{ p.totalControllerCount }} 个</td></tr>
            <tr><td>数据库表</td><td>{{ p.totalSqlTableCount }} 张</td></tr>
            <tr><td>前端页面</td><td>{{ p.totalViewCount }} 个</td></tr>
            <tr><td>功能模块</td><td>{{ p.modules.join('、') }}</td></tr>
            <tr><td>后端技术</td><td>{{ p.backendTech.join('、') }}</td></tr>
            <tr><td>前端技术</td><td>{{ p.frontendTech.join('、') }}</td></tr>
          </table>
          <a :href="'/projects/' + p.number" class="detail-link">查看详情 &rarr;</a>
        </div>
      </div>
    </div>

    <!-- 项目列表 -->
    <div class="projects-grid">
      <div v-for="p in filteredProjects" :key="p.number" class="project-card" :class="{ 'in-compare': isInCompare(p.number) }">
        <div class="card-header">
          <span class="project-number">{{ p.number }}</span>
          <span :class="'complexity-badge complexity-' + p.complexity">{{ p.complexity }}</span>
        </div>
        <h4 class="card-title">
          <a :href="'/projects/' + p.number">{{ p.title }}</a>
        </h4>
        <div class="card-meta">
          <span class="category-tag">{{ p.categoryName }}</span>
          <span v-if="p.projectType === 'miniprogram'" class="type-tag">小程序</span>
          <span v-else class="type-tag">Web</span>
        </div>
        <div class="card-stats">
          <div class="stat"><span class="stat-value">{{ p.totalApiCount }}</span><span class="stat-label">API</span></div>
          <div class="stat"><span class="stat-value">{{ p.totalSqlTableCount }}</span><span class="stat-label">表</span></div>
          <div class="stat"><span class="stat-value">{{ p.totalViewCount }}</span><span class="stat-label">页面</span></div>
          <div class="stat"><span class="stat-value">{{ p.modules.length }}</span><span class="stat-label">模块</span></div>
        </div>
        <div class="card-modules">{{ p.modules.slice(0, 4).join('、') }}<span v-if="p.modules.length > 4">...</span></div>
        <div class="card-tech">
          <span v-for="t in p.backendTech.slice(0, 2)" class="tech-tag backend">{{ t }}</span>
          <span v-for="t in p.frontendTech.slice(0, 2)" class="tech-tag frontend">{{ t }}</span>
        </div>
        <div class="card-actions">
          <a :href="'/projects/' + p.number" class="view-btn">查看详情</a>
          <button @click="addCompare(p)" :disabled="isInCompare(p.number) || compareList.length >= 4" class="compare-btn">
            {{ isInCompare(p.number) ? '已加入' : '加入对比' }}
          </button>
        </div>
      </div>
    </div>

    <div v-if="filteredProjects.length === 0" class="no-results">
      <p>没有找到匹配的项目，请调整筛选条件。</p>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'

const guideData = ref({ projects: [], categoryNames: {}, techStats: {}, complexityStats: {}, scenarios: {} })
const filters = ref({ category: '', complexity: '', springBootVersion: '', projectType: '', search: '' })
const compareList = ref([])

onMounted(async () => {
  try {
    const res = await fetch('/guide-data.json')
    if (res.ok) {
      guideData.value = await res.json()
    }
  } catch (e) {
    console.error('Failed to load guide data:', e)
  }
})

const categoryNames = computed(() => guideData.value.categoryNames || {})

const filteredProjects = computed(() => {
  let list = guideData.value.projects || []
  const f = filters.value

  if (f.category) list = list.filter(p => p.category === f.category)
  if (f.complexity) list = list.filter(p => p.complexity === f.complexity)
  if (f.springBootVersion) {
    if (f.springBootVersion === '2.x') list = list.filter(p => p.backendVersion && p.backendVersion.startsWith('2'))
    if (f.springBootVersion === '3.x') list = list.filter(p => p.backendVersion && p.backendVersion.startsWith('3'))
  }
  if (f.projectType) list = list.filter(p => p.projectType === f.projectType)
  if (f.search) {
    const kw = f.search.toLowerCase()
    list = list.filter(p =>
      p.title.toLowerCase().includes(kw) ||
      p.modules.some(m => m.toLowerCase().includes(kw)) ||
      p.backendTech.some(t => t.toLowerCase().includes(kw)) ||
      p.frontendTech.some(t => t.toLowerCase().includes(kw))
    )
  }
  return list
})

function applyFilters() {}

function resetFilters() {
  filters.value = { category: '', complexity: '', springBootVersion: '', projectType: '', search: '' }
}

function addCompare(project) {
  if (compareList.value.length >= 4) return
  if (isInCompare(project.number)) return
  compareList.value.push(project)
}

function removeCompare(number) {
  compareList.value = compareList.value.filter(p => p.number !== number)
}

function isInCompare(number) {
  return compareList.value.some(p => p.number === number)
}
</script>

<style scoped>
.guide-container {
  max-width: 1200px;
}

.filter-section {
  background: var(--vp-c-bg-soft);
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 24px;
}

.filter-section h3 {
  margin-top: 0;
  margin-bottom: 16px;
  font-size: 16px;
  color: var(--vp-c-text-1);
}

.filter-group {
  display: inline-flex;
  align-items: center;
  margin-right: 16px;
  margin-bottom: 12px;
  gap: 8px;
}

.filter-group label {
  font-size: 14px;
  color: var(--vp-c-text-2);
  white-space: nowrap;
}

.filter-group select,
.search-input {
  padding: 6px 12px;
  border: 1px solid var(--vp-c-divider);
  border-radius: 4px;
  background: var(--vp-c-bg);
  color: var(--vp-c-text-1);
  font-size: 14px;
}

.search-input {
  width: 220px;
}

.filter-actions {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-top: 8px;
}

.reset-btn {
  padding: 6px 16px;
  border: 1px solid var(--vp-c-divider);
  border-radius: 4px;
  background: var(--vp-c-bg);
  color: var(--vp-c-text-2);
  cursor: pointer;
  font-size: 14px;
}

.result-count {
  font-size: 14px;
  color: var(--vp-c-text-2);
}

.compare-section {
  background: var(--vp-c-bg-soft);
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 24px;
}

.compare-section h3 {
  margin-top: 0;
  margin-bottom: 16px;
  font-size: 16px;
  color: var(--vp-c-text-1);
}

.compare-cards {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 16px;
}

.compare-card {
  background: var(--vp-c-bg);
  border: 1px solid var(--vp-c-brand-1);
  border-radius: 8px;
  padding: 16px;
  position: relative;
}

.remove-btn {
  position: absolute;
  top: 8px;
  right: 8px;
  background: none;
  border: none;
  color: var(--vp-c-text-3);
  font-size: 18px;
  cursor: pointer;
}

.compare-card h4 {
  margin-top: 0;
  margin-bottom: 12px;
  font-size: 15px;
  color: var(--vp-c-text-1);
}

.compare-table {
  width: 100%;
  border-collapse: collapse;
  font-size: 13px;
}

.compare-table td {
  padding: 4px 8px;
  border-bottom: 1px solid var(--vp-c-divider);
}

.compare-table td:first-child {
  color: var(--vp-c-text-2);
  width: 40%;
}

.compare-table td:last-child {
  color: var(--vp-c-text-1);
}

.detail-link {
  display: inline-block;
  margin-top: 8px;
  color: var(--vp-c-brand-1);
  font-size: 13px;
}

.projects-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 16px;
}

.project-card {
  background: var(--vp-c-bg-soft);
  border: 1px solid var(--vp-c-divider);
  border-radius: 8px;
  padding: 16px;
  transition: border-color 0.2s;
}

.project-card.in-compare {
  border-color: var(--vp-c-brand-1);
}

.project-card:hover {
  border-color: var(--vp-c-brand-2);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
}

.project-number {
  font-size: 13px;
  color: var(--vp-c-text-3);
  font-weight: 600;
}

.complexity-badge {
  font-size: 12px;
  padding: 2px 8px;
  border-radius: 4px;
}

.complexity-简单 {
  background: #e8f5e9;
  color: #2e7d32;
}

.complexity-中等 {
  background: #fff3e0;
  color: #ef6c00;
}

.complexity-复杂 {
  background: #fce4ec;
  color: #c62828;
}

.card-title {
  margin: 0 0 8px 0;
  font-size: 15px;
  line-height: 1.3;
}

.card-title a {
  color: var(--vp-c-text-1);
  text-decoration: none;
}

.card-title a:hover {
  color: var(--vp-c-brand-1);
}

.card-meta {
  display: flex;
  gap: 8px;
  margin-bottom: 12px;
}

.category-tag,
.type-tag {
  font-size: 12px;
  padding: 2px 8px;
  border-radius: 4px;
  background: var(--vp-c-bg);
  color: var(--vp-c-text-2);
  border: 1px solid var(--vp-c-divider);
}

.card-stats {
  display: flex;
  gap: 12px;
  margin-bottom: 12px;
}

.stat {
  text-align: center;
}

.stat-value {
  display: block;
  font-size: 18px;
  font-weight: 600;
  color: var(--vp-c-text-1);
}

.stat-label {
  display: block;
  font-size: 11px;
  color: var(--vp-c-text-3);
}

.card-modules {
  font-size: 13px;
  color: var(--vp-c-text-2);
  margin-bottom: 8px;
  line-height: 1.4;
}

.card-tech {
  display: flex;
  flex-wrap: wrap;
  gap: 4px;
  margin-bottom: 12px;
}

.tech-tag {
  font-size: 11px;
  padding: 2px 6px;
  border-radius: 3px;
}

.tech-tag.backend {
  background: #e3f2fd;
  color: #1565c0;
}

.tech-tag.frontend {
  background: #f3e5f5;
  color: #7b1fa2;
}

.card-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.view-btn {
  font-size: 13px;
  color: var(--vp-c-brand-1);
  text-decoration: none;
}

.compare-btn {
  font-size: 13px;
  padding: 4px 12px;
  border: 1px solid var(--vp-c-divider);
  border-radius: 4px;
  background: var(--vp-c-bg);
  color: var(--vp-c-text-2);
  cursor: pointer;
}

.compare-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.compare-btn:not(:disabled):hover {
  border-color: var(--vp-c-brand-1);
  color: var(--vp-c-brand-1);
}

.no-results {
  text-align: center;
  padding: 40px;
  color: var(--vp-c-text-2);
}
</style>