<template>
  <div class="tech-filter-container">
    <!-- 技术栈统计概览 -->
    <div class="tech-overview">
      <h3>技术栈分布概览</h3>
      <div class="tech-categories">
        <div class="tech-category">
          <h4>后端框架版本</h4>
          <div class="tech-bars">
            <div v-for="(count, version) in sortedVersions" :key="version" class="tech-bar-row">
              <span class="tech-name">Spring Boot {{ version }}</span>
              <div class="bar-container">
                <div class="bar-fill" :style="{ width: (count / maxVersionCount * 100) + '%' }"></div>
              </div>
              <span class="tech-count">{{ count }} 个项目</span>
            </div>
          </div>
        </div>
        <div class="tech-category">
          <h4>后端技术 (Top 10)</h4>
          <div class="tech-bars">
            <div v-for="item in topBackendTech" :key="item.name" class="tech-bar-row">
              <span class="tech-name">{{ item.name }}</span>
              <div class="bar-container">
                <div class="bar-fill backend" :style="{ width: (item.count / maxBackendCount * 100) + '%' }"></div>
              </div>
              <span class="tech-count">{{ item.count }} 个</span>
            </div>
          </div>
        </div>
        <div class="tech-category">
          <h4>前端技术 (Top 10)</h4>
          <div class="tech-bars">
            <div v-for="item in topFrontendTech" :key="item.name" class="tech-bar-row">
              <span class="tech-name">{{ item.name }}</span>
              <div class="bar-container">
                <div class="bar-fill frontend" :style="{ width: (item.count / maxFrontendCount * 100) + '%' }"></div>
              </div>
              <span class="tech-count">{{ item.count }} 个</span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 技术栈筛选 -->
    <div class="tech-filter-section">
      <h3>按技术栈筛选项目</h3>
      <div class="filter-controls">
        <div class="filter-group">
          <label>选择后端技术：</label>
          <select v-model="selectedBackend" @change="applyFilters">
            <option value="">不限</option>
            <option v-for="item in allBackendTech" :key="item" :value="item">{{ item }}</option>
          </select>
        </div>
        <div class="filter-group">
          <label>选择前端技术：</label>
          <select v-model="selectedFrontend" @change="applyFilters">
            <option value="">不限</option>
            <option v-for="item in allFrontendTech" :key="item" :value="item">{{ item }}</option>
          </select>
        </div>
        <div class="filter-group">
          <label>Spring Boot 版本：</label>
          <select v-model="selectedVersion" @change="applyFilters">
            <option value="">不限</option>
            <option v-for="(count, version) in sortedVersions" :key="version" :value="version">{{ version }} ({{ count }}个)</option>
          </select>
        </div>
        <button @click="resetFilters" class="reset-btn">重置</button>
      </div>

      <!-- 筛选结果 -->
      <div class="tech-results">
        <p class="result-summary">找到 {{ filteredProjects.length }} 个匹配项目</p>
        <div v-if="filteredProjects.length > 0" class="tech-project-list">
          <div v-for="p in filteredProjects" :key="p.number" class="tech-project-item">
            <div class="item-header">
              <span class="item-number">{{ p.number }}</span>
              <a :href="'/projects/' + p.number" class="item-title">{{ p.title }}</a>
              <span :class="'complexity-badge complexity-' + p.complexity">{{ p.complexity }}</span>
            </div>
            <div class="item-tech">
              <span v-for="t in p.backendTech" class="tech-tag backend">{{ t }}</span>
              <span class="tech-separator">|</span>
              <span v-for="t in p.frontendTech" class="tech-tag frontend">{{ t }}</span>
            </div>
            <div class="item-stats">
              API {{ p.totalApiCount }} | 表 {{ p.totalSqlTableCount }} | 页面 {{ p.totalViewCount }} | 模块 {{ p.modules.length }}个
            </div>
          </div>
        </div>
        <div v-else class="no-results">没有找到匹配的项目。</div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'

const guideData = ref({ projects: [], categoryNames: {}, techStats: {}, complexityStats: {}, scenarios: {} })
const selectedBackend = ref('')
const selectedFrontend = ref('')
const selectedVersion = ref('')

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

const sortedVersions = computed(() => {
  const versions = guideData.value.techStats?.springBootVersions || {}
  const sorted = Object.entries(versions).sort((a, b) => b[1] - a[1])
  return Object.fromEntries(sorted)
})

const maxVersionCount = computed(() => {
  const versions = sortedVersions.value
  return Math.max(...Object.values(versions), 1)
})

const topBackendTech = computed(() => {
  const tech = guideData.value.techStats?.backend || {}
  return Object.entries(tech)
    .sort((a, b) => b[1] - a[1])
    .slice(0, 10)
    .map(([name, count]) => ({ name, count }))
})

const maxBackendCount = computed(() => Math.max(...topBackendTech.value.map(t => t.count), 1))

const topFrontendTech = computed(() => {
  const tech = guideData.value.techStats?.frontend || {}
  return Object.entries(tech)
    .sort((a, b) => b[1] - a[1])
    .slice(0, 10)
    .map(([name, count]) => ({ name, count }))
})

const maxFrontendCount = computed(() => Math.max(...topFrontendTech.value.map(t => t.count), 1))

const allBackendTech = computed(() => {
  const tech = guideData.value.techStats?.backend || {}
  return Object.entries(tech).sort((a, b) => b[1] - a[1]).map(([name]) => name)
})

const allFrontendTech = computed(() => {
  const tech = guideData.value.techStats?.frontend || {}
  return Object.entries(tech).sort((a, b) => b[1] - a[1]).map(([name]) => name)
})

const filteredProjects = computed(() => {
  let list = guideData.value.projects || []
  if (selectedBackend.value) {
    list = list.filter(p => p.backendTech.some(t => t === selectedBackend.value))
  }
  if (selectedFrontend.value) {
    list = list.filter(p => p.frontendTech.some(t => t === selectedFrontend.value))
  }
  if (selectedVersion.value) {
    list = list.filter(p => p.backendVersion === selectedVersion.value)
  }
  return list
})

function applyFilters() {}

function resetFilters() {
  selectedBackend.value = ''
  selectedFrontend.value = ''
  selectedVersion.value = ''
}
</script>

<style scoped>
.tech-filter-container {
  max-width: 1200px;
}

.tech-overview {
  background: var(--vp-c-bg-soft);
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 24px;
}

.tech-overview h3 {
  margin-top: 0;
  margin-bottom: 16px;
  font-size: 16px;
  color: var(--vp-c-text-1);
}

.tech-categories {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
  gap: 24px;
}

.tech-category h4 {
  margin: 0 0 12px 0;
  font-size: 14px;
  color: var(--vp-c-text-2);
}

.tech-bar-row {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 6px;
}

.tech-name {
  width: 180px;
  font-size: 13px;
  color: var(--vp-c-text-1);
  text-align: right;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.bar-container {
  flex: 1;
  height: 18px;
  background: var(--vp-c-divider);
  border-radius: 3px;
  position: relative;
}

.bar-fill {
  height: 100%;
  border-radius: 3px;
  background: var(--vp-c-brand-1);
  min-width: 2px;
}

.bar-fill.backend {
  background: #1565c0;
}

.bar-fill.frontend {
  background: #7b1fa2;
}

.tech-count {
  width: 80px;
  font-size: 12px;
  color: var(--vp-c-text-3);
}

.tech-filter-section {
  background: var(--vp-c-bg-soft);
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 24px;
}

.tech-filter-section h3 {
  margin-top: 0;
  margin-bottom: 16px;
  font-size: 16px;
  color: var(--vp-c-text-1);
}

.filter-controls {
  display: flex;
  align-items: center;
  gap: 16px;
  flex-wrap: wrap;
  margin-bottom: 16px;
}

.filter-group {
  display: inline-flex;
  align-items: center;
  gap: 8px;
}

.filter-group label {
  font-size: 14px;
  color: var(--vp-c-text-2);
  white-space: nowrap;
}

.filter-group select {
  padding: 6px 12px;
  border: 1px solid var(--vp-c-divider);
  border-radius: 4px;
  background: var(--vp-c-bg);
  color: var(--vp-c-text-1);
  font-size: 14px;
  max-width: 200px;
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

.result-summary {
  font-size: 14px;
  color: var(--vp-c-text-2);
  margin-bottom: 12px;
}

.tech-project-item {
  padding: 12px;
  border-bottom: 1px solid var(--vp-c-divider);
}

.tech-project-item:last-child {
  border-bottom: none;
}

.item-header {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 6px;
}

.item-number {
  font-size: 13px;
  color: var(--vp-c-text-3);
  font-weight: 600;
}

.item-title {
  font-size: 15px;
  color: var(--vp-c-text-1);
  text-decoration: none;
}

.item-title:hover {
  color: var(--vp-c-brand-1);
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

.item-tech {
  display: flex;
  align-items: center;
  gap: 4px;
  margin-bottom: 6px;
  flex-wrap: wrap;
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

.tech-separator {
  color: var(--vp-c-text-3);
  font-size: 12px;
}

.item-stats {
  font-size: 13px;
  color: var(--vp-c-text-3);
}

.no-results {
  text-align: center;
  padding: 40px;
  color: var(--vp-c-text-2);
}
</style>