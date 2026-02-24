<template>
  <div>
    <el-card>
      <template #header>
        <span>系统概览</span>
      </template>
      <div v-if="isAdmin" class="card-grid">
        <el-statistic title="今日销售额" :value="Number(stats.todaySales || 0)" :precision="2" />
        <el-statistic title="今日订单数" :value="Number(stats.todayOrders || 0)" />
        <el-statistic title="用户总数" :value="Number(stats.totalUsers || 0)" />
        <el-statistic title="影片总数" :value="Number(stats.totalMovies || 0)" />
      </div>
      <div v-else class="recommend-wrap">
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

    <el-row :gutter="16" v-if="isAdmin" class="mt16">
      <el-col :span="12">
        <el-card>
          <template #header>近7天销售趋势</template>
          <div ref="salesRef" style="height: 320px"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>影片票房排行</template>
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
const isAdmin = computed(() => (userStore.user?.role || '').toUpperCase() === 'ADMIN')

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
  if (isAdmin.value) {
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
