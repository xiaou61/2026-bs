<template>
  <div>
    <el-card>
      <template #header>
        <span>{{ isFan ? '球迷欢迎页' : '联赛运营总览' }}</span>
      </template>
      <div v-if="!isFan" class="card-grid">
        <el-statistic title="总比赛数" :value="Number(stats.totalMatches || 0)" />
        <el-statistic title="已完赛数" :value="Number(stats.finishedMatches || 0)" />
        <el-statistic title="总进球数" :value="Number(stats.totalGoals || 0)" />
        <el-statistic title="球队总数" :value="Number(stats.totalTeams || 0)" />
      </div>
      <div v-else class="fan-grid">
        <div class="fan-card">
          <div class="label">账号身份</div>
          <div class="value">{{ roleLabel }}</div>
        </div>
        <div class="fan-card">
          <div class="label">欢迎回来</div>
          <div class="value">{{ user?.nickname || user?.username }}</div>
        </div>
        <div class="fan-card">
          <div class="label">关注球队</div>
          <div class="value">{{ followList.length }}</div>
        </div>
      </div>
    </el-card>

    <el-row v-if="!isFan" :gutter="16" class="mt16">
      <el-col :span="12">
        <el-card>
          <template #header>近 7 天赛程趋势</template>
          <div ref="trendRef" style="height: 320px"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>积分榜前列</template>
          <div ref="standingRef" style="height: 320px"></div>
        </el-card>
      </el-col>
    </el-row>

    <el-row v-if="isFan" :gutter="16" class="mt16">
      <el-col :span="14">
        <el-card>
          <template #header>近期赛程</template>
          <el-table :data="matchList" size="small">
            <el-table-column prop="seasonName" label="赛季" min-width="120" />
            <el-table-column label="对阵" min-width="220">
              <template #default="{ row }">{{ row.homeTeamName }} vs {{ row.awayTeamName }}</template>
            </el-table-column>
            <el-table-column prop="kickOffTime" label="开球时间" min-width="160" />
            <el-table-column prop="status" label="状态" min-width="100" />
          </el-table>
        </el-card>
      </el-col>
      <el-col :span="10">
        <el-card>
          <template #header>热门球员</template>
          <div v-for="item in playerList" :key="item.id" class="player-item">
            <div>
              <div class="player-name">{{ item.name }}</div>
              <div class="player-desc">{{ item.teamName }} · {{ item.position || '位置待定' }}</div>
            </div>
            <el-tag type="success">{{ item.goalCount || 0 }} 球</el-tag>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-card class="mt16">
      <template #header>
        <span>最新资讯</span>
      </template>
      <el-timeline>
        <el-timeline-item v-for="item in newsList" :key="item.id" :timestamp="item.publishTime">
          <strong>{{ item.title }}</strong>
          <div class="notice-content">{{ item.content }}</div>
        </el-timeline-item>
      </el-timeline>
    </el-card>
  </div>
</template>

<script setup>
import { computed, nextTick, onMounted, ref } from 'vue'
import * as echarts from 'echarts'
import { useUserStore } from '../store/user'
import { getDashboardStats, getFollowList, getGoalRank, getMatchPublicList, getMatchTrend, getPlayerPublicList, getPublicNewsList, getStandingRank } from '../api'

const userStore = useUserStore()
const user = computed(() => userStore.user || {})
const role = computed(() => (user.value?.role || '').toUpperCase())
const isFan = computed(() => role.value === 'FAN')
const roleMap = {
  ADMIN: '管理员',
  MANAGER: '俱乐部经理',
  FAN: '球迷'
}
const roleLabel = computed(() => roleMap[role.value] || '访客')

const stats = ref({})
const newsList = ref([])
const matchList = ref([])
const playerList = ref([])
const followList = ref([])
const trendRef = ref()
const standingRef = ref()

const loadManager = async () => {
  const [a, b, c] = await Promise.all([getDashboardStats(), getMatchTrend({ days: 7 }), getStandingRank({})])
  stats.value = a.data || {}
  await nextTick()
  echarts.init(trendRef.value).setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: (b.data || []).map(i => i.date) },
    yAxis: { type: 'value' },
    series: [{ type: 'line', smooth: true, data: (b.data || []).map(i => Number(i.matchCount || 0)) }]
  })
  echarts.init(standingRef.value).setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: (c.data || []).map(i => i.teamName) },
    yAxis: { type: 'value' },
    series: [{ type: 'bar', data: (c.data || []).map(i => Number(i.points || 0)) }]
  })
}

const loadFan = async () => {
  const [matchRes, playerRes, followRes] = await Promise.all([
    getMatchPublicList({}),
    getPlayerPublicList(),
    getFollowList({ pageNum: 1, pageSize: 20 })
  ])
  matchList.value = (matchRes.data || []).slice(0, 6)
  playerList.value = (playerRes.data || []).slice(0, 6)
  followList.value = followRes.data?.records || []
}

const loadNews = async () => {
  const res = await getPublicNewsList()
  newsList.value = (res.data || []).slice(0, 6)
}

onMounted(async () => {
  await loadNews()
  if (isFan.value) {
    await loadFan()
  } else {
    await loadManager()
  }
})
</script>

<style scoped>
.card-grid,
.fan-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 16px;
}

.fan-card {
  background: #f6f8fb;
  border-radius: 12px;
  padding: 18px;
}

.label {
  color: #667085;
  font-size: 13px;
  margin-bottom: 8px;
}

.value {
  font-size: 24px;
  font-weight: 700;
  color: #14365d;
}

.player-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px 0;
  border-bottom: 1px solid #eef2f6;
}

.player-item:last-child {
  border-bottom: none;
}

.player-name {
  font-weight: 600;
  color: #14365d;
}

.player-desc {
  margin-top: 4px;
  color: #667085;
  font-size: 13px;
}

.notice-content {
  margin-top: 6px;
  color: #475467;
}

.mt16 {
  margin-top: 16px;
}
</style>
