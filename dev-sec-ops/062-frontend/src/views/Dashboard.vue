<template>
  <div>
    <el-row :gutter="12" v-if="isRiskRole">
      <el-col :span="6"><el-card><div class="card"><span>用户总数</span><b>{{ stats.userCount || 0 }}</b></div></el-card></el-col>
      <el-col :span="6"><el-card><div class="card"><span>事件总数</span><b>{{ stats.eventCount || 0 }}</b></div></el-card></el-col>
      <el-col :span="6"><el-card><div class="card"><span>待处理预警</span><b>{{ stats.pendingAlertCount || 0 }}</b></div></el-card></el-col>
      <el-col :span="6"><el-card><div class="card"><span>高风险事件</span><b>{{ stats.highRiskCount || 0 }}</b></div></el-card></el-col>
    </el-row>
    <el-row :gutter="12" v-else>
      <el-col :span="8"><el-card><div class="card"><span>我的事件</span><b>{{ userStats.eventCount || 0 }}</b></div></el-card></el-col>
      <el-col :span="8"><el-card><div class="card"><span>我的预警</span><b>{{ userStats.alertCount || 0 }}</b></div></el-card></el-col>
      <el-col :span="8"><el-card><div class="card"><span>我的申诉</span><b>{{ userStats.appealCount || 0 }}</b></div></el-card></el-col>
    </el-row>

    <el-row :gutter="12" style="margin-top: 12px" v-if="isRiskRole">
      <el-col :span="12"><el-card><div ref="trendRef" class="chart"></div></el-card></el-col>
      <el-col :span="12"><el-card><div ref="levelRef" class="chart"></div></el-card></el-col>
    </el-row>

    <el-card style="margin-top: 12px" v-if="isRiskRole">
      <template #header>规则命中排行</template>
      <el-table :data="topRules" size="small">
        <el-table-column prop="ruleName" label="规则" min-width="220" />
        <el-table-column prop="ruleCode" label="编码" width="180" />
        <el-table-column prop="hitCount" label="命中次数" width="120" />
      </el-table>
    </el-card>

    <el-card style="margin-top: 12px" v-if="isRiskRole">
      <template #header>高风险用户排行</template>
      <el-table :data="topUsers" size="small">
        <el-table-column prop="userName" label="用户" min-width="180" />
        <el-table-column prop="highRiskCount" label="高风险次数" width="130" />
        <el-table-column prop="eventCount" label="事件总数" width="120" />
        <el-table-column label="平均风险分" width="140">
          <template #default="{ row }">{{ Number(row.avgScore || 0).toFixed(2) }}</template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-card style="margin-top: 12px">
      <template #header>最新公告</template>
      <el-empty v-if="!announcements.length" description="暂无公告" />
      <el-timeline v-else>
        <el-timeline-item v-for="item in announcements" :key="item.id" :timestamp="item.createTime">
          <b>{{ item.title }}</b>
          <div style="margin-top: 4px">{{ item.content }}</div>
        </el-timeline-item>
      </el-timeline>
    </el-card>
  </div>
</template>

<script setup>
import { computed, nextTick, onMounted, ref } from 'vue'
import * as echarts from 'echarts'
import { getAnnouncementList, getDashboardStats, getMyAlertPage, getMyAppealPage, getMyEventPage, getRiskLevelDist, getRiskTrend, getTopRules, getTopUsers } from '../api'
import { useUserStore } from '../store/user'

const userStore = useUserStore()
const isRiskRole = computed(() => ['ADMIN', 'ANALYST'].includes(userStore.user?.role))

const stats = ref({})
const userStats = ref({})
const announcements = ref([])
const topRules = ref([])
const topUsers = ref([])
const trendRef = ref()
const levelRef = ref()

const loadAnnouncements = async () => {
  const res = await getAnnouncementList()
  announcements.value = res.data || []
}

const loadRiskData = async () => {
  const [sRes, tRes, lRes, rRes, uRes] = await Promise.all([
    getDashboardStats(),
    getRiskTrend(),
    getRiskLevelDist(),
    getTopRules(),
    getTopUsers()
  ])
  stats.value = sRes.data || {}
  topRules.value = rRes.data || []
  topUsers.value = uRes.data || []
  await nextTick()
  const trendChart = echarts.init(trendRef.value)
  trendChart.setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: (tRes.data || []).map(i => i.date) },
    yAxis: { type: 'value' },
    series: [{ type: 'line', smooth: true, data: (tRes.data || []).map(i => i.count) }]
  })
  const levelChart = echarts.init(levelRef.value)
  levelChart.setOption({
    tooltip: { trigger: 'item' },
    series: [{ type: 'pie', radius: ['40%', '70%'], data: lRes.data || [] }]
  })
}

const loadUserData = async () => {
  const [eventRes, alertRes, appealRes] = await Promise.all([
    getMyEventPage({ pageNum: 1, pageSize: 1 }),
    getMyAlertPage({ pageNum: 1, pageSize: 1 }),
    getMyAppealPage({ pageNum: 1, pageSize: 1 })
  ])
  userStats.value = {
    eventCount: eventRes.data.total || 0,
    alertCount: alertRes.data.total || 0,
    appealCount: appealRes.data.total || 0
  }
}

onMounted(async () => {
  await loadAnnouncements()
  if (isRiskRole.value) {
    await loadRiskData()
  } else {
    await loadUserData()
  }
})
</script>

<style scoped>
.card {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.card b {
  font-size: 24px;
  color: #0f172a;
}

.chart {
  height: 320px;
}
</style>
