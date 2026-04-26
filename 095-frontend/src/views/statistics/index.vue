<template>
  <div class="page-container">
    <el-row :gutter="16">
      <el-col :span="6"><el-card><el-statistic title="总比赛数" :value="Number(stats.totalMatches || 0)" /></el-card></el-col>
      <el-col :span="6"><el-card><el-statistic title="已完赛" :value="Number(stats.finishedMatches || 0)" /></el-card></el-col>
      <el-col :span="6"><el-card><el-statistic title="总进球数" :value="Number(stats.totalGoals || 0)" /></el-card></el-col>
      <el-col :span="6"><el-card><el-statistic title="球队总数" :value="Number(stats.totalTeams || 0)" /></el-card></el-col>
    </el-row>

    <el-row :gutter="16" class="mt16">
      <el-col :span="6"><el-card><el-statistic title="俱乐部总数" :value="Number(stats.totalClubs || 0)" /></el-card></el-col>
      <el-col :span="6"><el-card><el-statistic title="球迷人数" :value="Number(stats.totalFans || 0)" /></el-card></el-col>
      <el-col :span="6"><el-card><el-statistic title="活跃赛季" :value="Number(stats.activeSeasons || 0)" /></el-card></el-col>
      <el-col :span="6"><el-card><el-statistic title="完赛率" :value="finishRate" suffix="%" /></el-card></el-col>
    </el-row>

    <el-row :gutter="16" class="mt16">
      <el-col :span="14">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>赛程趋势</span>
              <el-radio-group v-model="days" size="small" @change="loadTrend">
                <el-radio-button :label="7">近 7 天</el-radio-button>
                <el-radio-button :label="15">近 15 天</el-radio-button>
                <el-radio-button :label="30">近 30 天</el-radio-button>
              </el-radio-group>
            </div>
          </template>
          <div ref="trendRef" class="chart-box"></div>
        </el-card>
      </el-col>
      <el-col :span="10">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>积分榜前列</span>
              <el-select v-model="seasonId" placeholder="选择赛季" clearable style="width: 180px" @change="loadStanding">
                <el-option v-for="item in seasonOptions" :key="item.id" :label="item.seasonName" :value="item.id" />
              </el-select>
            </div>
          </template>
          <div ref="standingRef" class="chart-box"></div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="16" class="mt16">
      <el-col :span="12">
        <el-card>
          <template #header>射手榜</template>
          <div ref="goalRef" class="chart-box"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>数据明细</template>
          <el-table :data="goalRank" size="small">
            <el-table-column prop="playerName" label="球员" min-width="120" />
            <el-table-column prop="teamName" label="球队" min-width="140" />
            <el-table-column prop="goals" label="进球" min-width="80" />
            <el-table-column prop="assists" label="助攻" min-width="80" />
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { computed, nextTick, onBeforeUnmount, onMounted, ref } from 'vue'
import * as echarts from 'echarts'
import { getDashboardStats, getGoalRank, getMatchTrend, getSeasonOptions, getStandingRank } from '../../api'

const stats = ref({})
const days = ref(7)
const seasonId = ref(null)
const seasonOptions = ref([])
const goalRank = ref([])

const trendRef = ref()
const standingRef = ref()
const goalRef = ref()

let trendChart
let standingChart
let goalChart

const finishRate = computed(() => {
  const total = Number(stats.value.totalMatches || 0)
  if (!total) {
    return 0
  }
  return Number(((Number(stats.value.finishedMatches || 0) / total) * 100).toFixed(1))
})

const renderTrend = data => {
  if (!trendChart && trendRef.value) {
    trendChart = echarts.init(trendRef.value)
  }
  trendChart?.setOption({
    tooltip: { trigger: 'axis' },
    legend: { data: ['赛程数', '完赛数'] },
    xAxis: { type: 'category', data: data.map(item => item.date) },
    yAxis: { type: 'value' },
    series: [
      { name: '赛程数', type: 'line', smooth: true, data: data.map(item => Number(item.matchCount || 0)) },
      { name: '完赛数', type: 'bar', barMaxWidth: 20, data: data.map(item => Number(item.finishedCount || 0)) }
    ]
  })
}

const renderStanding = data => {
  if (!standingChart && standingRef.value) {
    standingChart = echarts.init(standingRef.value)
  }
  standingChart?.setOption({
    tooltip: { trigger: 'axis' },
    grid: { left: 40, right: 16, top: 16, bottom: 40 },
    xAxis: { type: 'value' },
    yAxis: {
      type: 'category',
      inverse: true,
      data: data.map(item => item.teamName)
    },
    series: [
      {
        type: 'bar',
        barMaxWidth: 24,
        data: data.map(item => Number(item.points || 0)),
        itemStyle: { color: '#1d4f87', borderRadius: [0, 8, 8, 0] }
      }
    ]
  })
}

const renderGoals = data => {
  if (!goalChart && goalRef.value) {
    goalChart = echarts.init(goalRef.value)
  }
  goalChart?.setOption({
    tooltip: { trigger: 'axis' },
    grid: { left: 40, right: 16, top: 16, bottom: 50 },
    xAxis: {
      type: 'category',
      axisLabel: { interval: 0, rotate: 25 },
      data: data.map(item => item.playerName)
    },
    yAxis: { type: 'value' },
    series: [
      {
        name: '进球',
        type: 'bar',
        data: data.map(item => Number(item.goals || 0)),
        itemStyle: { color: '#23854f', borderRadius: [8, 8, 0, 0] }
      },
      {
        name: '助攻',
        type: 'line',
        smooth: true,
        data: data.map(item => Number(item.assists || 0)),
        itemStyle: { color: '#e59b1f' }
      }
    ]
  })
}

const loadBase = async () => {
  const [statsRes, seasonRes, goalRes] = await Promise.all([
    getDashboardStats(),
    getSeasonOptions({}),
    getGoalRank()
  ])
  stats.value = statsRes.data || {}
  seasonOptions.value = seasonRes.data || []
  goalRank.value = goalRes.data || []
  await nextTick()
  renderGoals(goalRank.value)
}

const loadTrend = async () => {
  const res = await getMatchTrend({ days: days.value })
  renderTrend(res.data || [])
}

const loadStanding = async () => {
  const res = await getStandingRank({ seasonId: seasonId.value })
  renderStanding(res.data || [])
}

const handleResize = () => {
  trendChart?.resize()
  standingChart?.resize()
  goalChart?.resize()
}

onMounted(async () => {
  await loadBase()
  await loadTrend()
  await loadStanding()
  window.addEventListener('resize', handleResize)
})

onBeforeUnmount(() => {
  window.removeEventListener('resize', handleResize)
  trendChart?.dispose()
  standingChart?.dispose()
  goalChart?.dispose()
})
</script>

<style scoped>
.page-container {
  padding: 8px;
}

.mt16 {
  margin-top: 16px;
}

.card-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
}

.chart-box {
  height: 320px;
}
</style>
