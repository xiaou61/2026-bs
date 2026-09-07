<template>
  <div>
    <el-card>
      <template #header>
        <span>{{ isManager ? '会员运营概览' : '会员中心' }}</span>
      </template>
      <div v-if="isManager" class="card-grid">
        <el-statistic title="今日消费额" :value="Number(stats.todaySales || 0)" :precision="2" />
        <el-statistic title="今日储值额" :value="Number(stats.todayRecharge || 0)" :precision="2" />
        <el-statistic title="会员总数" :value="Number(stats.totalMembers || 0)" />
        <el-statistic title="上映影片数" :value="Number(stats.totalMovies || 0)" />
      </div>
      <div v-else class="member-wrap">
        <div class="member-grid">
          <div class="member-panel">
            <div class="label">会员等级</div>
            <div class="value">{{ user?.memberLevel || 'SILVER' }}</div>
          </div>
          <div class="member-panel">
            <div class="label">当前余额</div>
            <div class="value">¥{{ Number(user?.balance || 0).toFixed(2) }}</div>
          </div>
          <div class="member-panel">
            <div class="label">会员积分</div>
            <div class="value">{{ Number(user?.points || 0) }}</div>
          </div>
          <div class="member-panel">
            <div class="label">累计消费</div>
            <div class="value">¥{{ Number(user?.totalConsume || 0).toFixed(2) }}</div>
          </div>
        </div>
      </div>
    </el-card>

    <el-card v-if="!isManager" class="mt16">
      <template #header>
        <span>会员推荐影片</span>
      </template>
      <div class="recommend-wrap">
        <el-row :gutter="16">
          <el-col :span="6" v-for="item in recommendList" :key="item.id">
            <el-card shadow="hover" class="movie-card">
              <img :src="item.poster" class="poster" />
              <h4>{{ item.title }}</h4>
              <p>类型：{{ item.category }}</p>
              <p>评分：{{ item.rating }}</p>
            </el-card>
          </el-col>
        </el-row>
      </div>
    </el-card>

    <el-row :gutter="16" v-if="isManager" class="mt16">
      <el-col :span="12">
        <el-card>
          <template #header>近7天会员消费趋势</template>
          <div ref="salesRef" style="height: 320px"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>影片消费热度</template>
          <div ref="boxRef" style="height: 320px"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { computed, nextTick, onMounted, ref } from 'vue'
import * as echarts from 'echarts'
import { useUserStore } from '../store/user'
import { getBoxOffice, getDashboardStats, getRecommendMovieList, getSalesTrend } from '../api'

const userStore = useUserStore()
const user = computed(() => userStore.user || {})
const isManager = computed(() => ['ADMIN', 'STAFF'].includes((user.value?.role || '').toUpperCase()))

const stats = ref({})
const recommendList = ref([])
const salesRef = ref()
const boxRef = ref()

const loadAdmin = async () => {
  const [a, b, c] = await Promise.all([getDashboardStats(), getSalesTrend({ days: 7 }), getBoxOffice()])
  stats.value = a.data || {}
  await nextTick()
  const salesChart = echarts.init(salesRef.value)
  salesChart.setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: (b.data || []).map(i => i.date) },
    yAxis: { type: 'value' },
    series: [{ type: 'line', smooth: true, data: (b.data || []).map(i => Number(i.sales || 0)) }]
  })
  const boxChart = echarts.init(boxRef.value)
  boxChart.setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: (c.data || []).map(i => i.movieTitle) },
    yAxis: { type: 'value' },
    series: [{ type: 'bar', data: (c.data || []).map(i => Number(i.boxOffice || 0)) }]
  })
}

const loadUser = async () => {
  const res = await getRecommendMovieList({ limit: 8 })
  recommendList.value = res.data || []
}

onMounted(async () => {
  if (isManager.value) {
    await loadAdmin()
  } else {
    await loadUser()
  }
})
</script>

<style scoped>
.card-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 16px;
}

.member-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 16px;
}

.member-panel {
  background: #f7f9fc;
  border-radius: 10px;
  padding: 18px;
}

.label {
  font-size: 13px;
  color: #667085;
  margin-bottom: 8px;
}

.value {
  font-size: 24px;
  font-weight: 600;
  color: #1d2939;
}

.mt16 {
  margin-top: 16px;
}

.poster {
  width: 100%;
  height: 220px;
  object-fit: cover;
  border-radius: 6px;
}

.movie-card h4 {
  margin: 10px 0 6px;
}

.movie-card p {
  margin: 4px 0;
  color: #666;
  font-size: 13px;
}
</style>
