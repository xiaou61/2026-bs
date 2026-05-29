<template>
  <div class="scenario-container">
    <!-- 场景概览 -->
    <div class="scenario-overview">
      <h3>功能场景概览</h3>
      <p class="overview-desc">以下是项目中常见的功能场景分类，点击场景查看包含该功能的项目列表。</p>
      <div class="scenario-grid">
        <div v-for="(scenario, key) in scenarios" :key="key"
             class="scenario-card"
             :class="{ active: selectedScenario === key }"
             @click="selectScenario(key)">
          <div class="scenario-icon">{{ getIcon(key) }}</div>
          <h4>{{ key }}</h4>
          <p class="scenario-desc">{{ scenario.desc }}</p>
          <span class="scenario-count">{{ scenario.count }} 个项目</span>
        </div>
      </div>
    </div>

    <!-- 场景详情 -->
    <div v-if="selectedScenario" class="scenario-detail">
      <h3>{{ selectedScenario }} — 包含的项目</h3>
      <p>{{ scenarios[selectedScenario]?.desc }}</p>
      <div class="scenario-project-list">
        <div v-for="p in scenarioProjects" :key="p.number" class="scenario-project-item">
          <div class="item-left">
            <span class="item-number">{{ p.number }}</span>
            <a :href="'/projects/' + p.number" class="item-title">{{ p.title }}</a>
            <span class="category-badge">{{ p.categoryName }}</span>
            <span :class="'complexity-badge complexity-' + p.complexity">{{ p.complexity }}</span>
          </div>
          <div class="item-right">
            <span class="stat">API {{ p.totalApiCount }}</span>
            <span class="stat">表 {{ p.totalSqlTableCount }}</span>
            <span class="stat">页面 {{ p.totalViewCount }}</span>
          </div>
        </div>
      </div>
    </div>

    <!-- 多场景组合筛选 -->
    <div class="scenario-combine">
      <h3>多场景组合筛选</h3>
      <p class="combine-desc">选择多个功能场景，查找同时包含所有所选场景的项目。</p>
      <div class="combine-options">
        <label v-for="(scenario, key) in scenarios" :key="key" class="combine-label">
          <input type="checkbox" v-model="selectedScenarios" :value="key" @change="combineFilter" />
          <span>{{ key }} ({{ scenario.count }})</span>
        </label>
      </div>
      <div v-if="selectedScenarios.length > 0" class="combine-result">
        <p class="result-summary">同时包含 {{ selectedScenarios.join(' + ') }} 的项目：{{ combinedProjects.length }} 个</p>
        <div v-if="combinedProjects.length > 0" class="combine-project-list">
          <div v-for="p in combinedProjects" :key="p.number" class="combine-project-item">
            <span class="item-number">{{ p.number }}</span>
            <a :href="'/projects/' + p.number" class="item-title">{{ p.title }}</a>
            <span class="category-badge">{{ p.categoryName }}</span>
            <span :class="'complexity-badge complexity-' + p.complexity">{{ p.complexity }}</span>
          </div>
        </div>
        <div v-else class="no-results">没有找到同时包含所有所选场景的项目。</div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'

const guideData = ref({ projects: [], categoryNames: {}, techStats: {}, complexityStats: {}, scenarios: {} })
const selectedScenario = ref('')
const selectedScenarios = ref([])

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

const scenarios = computed(() => guideData.value.scenarios || {})

const scenarioProjects = computed(() => {
  if (!selectedScenario.value) return []
  const scenario = scenarios.value[selectedScenario.value]
  if (!scenario) return []
  const projectNumbers = scenario.projects || []
  return (guideData.value.projects || []).filter(p => projectNumbers.includes(p.number))
})

const combinedProjects = computed(() => {
  if (selectedScenarios.value.length === 0) return []
  const allProjects = guideData.value.projects || []
  return allProjects.filter(p => {
    return selectedScenarios.value.every(scKey => {
      const scenario = scenarios.value[scKey]
      return scenario && scenario.projects.includes(p.number)
    })
  })
})

function selectScenario(key) {
  selectedScenario.value = selectedScenario.value === key ? '' : key
}

function combineFilter() {}

const icons = {
  '用户权限管理': '🔐',
  'CRUD台账管理': '📋',
  '工作流审批': '✅',
  '数据可视化': '📊',
  '文件上传下载': '📁',
  '消息通知': '🔔',
  '预约排期': '📅',
  '订单交易': '💰',
  '地图定位': '📍',
  '导入导出': '📥',
  'JWT认证': '🔑',
  '小程序端': '📱'
}

function getIcon(key) {
  return icons[key] || '🔹'
}
</script>

<style scoped>
.scenario-container {
  max-width: 1200px;
}

.scenario-overview,
.scenario-detail,
.scenario-combine {
  background: var(--vp-c-bg-soft);
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 24px;
}

.scenario-overview h3,
.scenario-detail h3,
.scenario-combine h3 {
  margin-top: 0;
  margin-bottom: 12px;
  font-size: 16px;
  color: var(--vp-c-text-1);
}

.overview-desc,
.combine-desc {
  font-size: 14px;
  color: var(--vp-c-text-2);
  margin-bottom: 16px;
}

.scenario-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
  gap: 12px;
}

.scenario-card {
  background: var(--vp-c-bg);
  border: 1px solid var(--vp-c-divider);
  border-radius: 8px;
  padding: 12px;
  cursor: pointer;
  transition: border-color 0.2s, background 0.2s;
  text-align: center;
}

.scenario-card:hover {
  border-color: var(--vp-c-brand-2);
}

.scenario-card.active {
  border-color: var(--vp-c-brand-1);
  background: var(--vp-c-bg-alt);
}

.scenario-icon {
  font-size: 24px;
  margin-bottom: 4px;
}

.scenario-card h4 {
  margin: 4px 0;
  font-size: 14px;
  color: var(--vp-c-text-1);
}

.scenario-desc {
  font-size: 12px;
  color: var(--vp-c-text-3);
  margin-bottom: 4px;
  line-height: 1.3;
}

.scenario-count {
  font-size: 12px;
  color: var(--vp-c-brand-1);
  font-weight: 600;
}

.scenario-project-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px 0;
  border-bottom: 1px solid var(--vp-c-divider);
}

.scenario-project-item:last-child {
  border-bottom: none;
}

.item-left {
  display: flex;
  align-items: center;
  gap: 8px;
}

.item-number {
  font-size: 13px;
  color: var(--vp-c-text-3);
  font-weight: 600;
}

.item-title {
  font-size: 14px;
  color: var(--vp-c-text-1);
  text-decoration: none;
}

.item-title:hover {
  color: var(--vp-c-brand-1);
}

.category-badge {
  font-size: 12px;
  padding: 2px 8px;
  border-radius: 4px;
  background: var(--vp-c-bg);
  color: var(--vp-c-text-2);
  border: 1px solid var(--vp-c-divider);
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

.item-right {
  display: flex;
  gap: 12px;
}

.stat {
  font-size: 13px;
  color: var(--vp-c-text-3);
}

.combine-options {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-bottom: 16px;
}

.combine-label {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  font-size: 14px;
  color: var(--vp-c-text-1);
  cursor: pointer;
  padding: 4px 8px;
  border: 1px solid var(--vp-c-divider);
  border-radius: 4px;
  background: var(--vp-c-bg);
}

.combine-label:hover {
  border-color: var(--vp-c-brand-2);
}

.combine-label input {
  accent-color: var(--vp-c-brand-1);
}

.result-summary {
  font-size: 14px;
  color: var(--vp-c-text-2);
  margin-bottom: 12px;
}

.combine-project-item {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 6px 0;
  border-bottom: 1px solid var(--vp-c-divider);
}

.combine-project-item:last-child {
  border-bottom: none;
}

.no-results {
  text-align: center;
  padding: 20px;
  color: var(--vp-c-text-2);
}
</style>