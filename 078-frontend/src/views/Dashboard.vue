<template>
  <div class="dashboard">
    <el-row :gutter="20" v-if="user?.role === 0">
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-icon" style="background: #409eff"><el-icon><User /></el-icon></div>
          <div class="stat-info"><div class="stat-value">{{ stats.userCount }}</div><div class="stat-label">用户数</div></div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-icon" style="background: #67c23a"><el-icon><Shop /></el-icon></div>
          <div class="stat-info"><div class="stat-value">{{ stats.merchantCount }}</div><div class="stat-label">商家数</div></div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-icon" style="background: #e6a23c"><el-icon><List /></el-icon></div>
          <div class="stat-info"><div class="stat-value">{{ stats.orderCount }}</div><div class="stat-label">订单数</div></div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-icon" style="background: #f56c6c"><el-icon><Money /></el-icon></div>
          <div class="stat-info"><div class="stat-value">¥{{ stats.totalAmount }}</div><div class="stat-label">销售额</div></div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" style="margin-top: 20px" v-if="user?.role !== 2">
      <el-col :span="16">
        <el-card>
          <template #header>销售趋势</template>
          <div ref="chartRef" style="height: 350px"></div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card>
          <template #header>热销商品</template>
          <div v-for="(item, index) in hotProducts" :key="item.id" class="hot-item">
            <span class="rank" :class="{ top: index < 3 }">{{ index + 1 }}</span>
            <span class="name">{{ item.name }}</span>
            <span class="sales">{{ item.sales }}件</span>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <div v-if="user?.role === 2" class="user-dashboard">
      <el-card>
        <template #header>欢迎您，{{ user?.nickname }}！</template>
        <p>快去发现更多优惠团购商品吧~</p>
        <el-button type="primary" @click="$router.push('/home')">去逛逛</el-button>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import * as echarts from 'echarts'
import { getStatsOverview, getStatsSales, getHotProducts, getMerchantStats } from '../api'
import { useUserStore } from '../store/user'

const userStore = useUserStore()
const user = computed(() => userStore.user)
const chartRef = ref()
const stats = ref({ userCount: 0, merchantCount: 0, orderCount: 0, totalAmount: 0 })
const hotProducts = ref([])

onMounted(async () => {
  if (user.value?.role === 0) {
    const res = await getStatsOverview()
    stats.value = res.data
    const salesRes = await getStatsSales({ days: 7 })
    initChart(salesRes.data)
    const hotRes = await getHotProducts({ limit: 10 })
    hotProducts.value = hotRes.data
  } else if (user.value?.role === 1) {
    const res = await getMerchantStats({ days: 7 })
    stats.value = { orderCount: res.data.orderCount, totalAmount: res.data.totalAmount, productCount: res.data.productCount }
    initChart(res.data.dailyStats)
  }
})

const initChart = (data) => {
  if (!chartRef.value) return
  const chart = echarts.init(chartRef.value)
  chart.setOption({
    tooltip: { trigger: 'axis' },
    legend: { data: ['订单量', '销售额'] },
    xAxis: { type: 'category', data: data.map(d => d.date) },
    yAxis: [{ type: 'value', name: '订单量' }, { type: 'value', name: '销售额' }],
    series: [
      { name: '订单量', type: 'bar', data: data.map(d => d.count) },
      { name: '销售额', type: 'line', yAxisIndex: 1, data: data.map(d => d.amount) }
    ]
  })
}
</script>

<style scoped>
.stat-card {
  display: flex;
  align-items: center;
  padding: 20px;
}
.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 28px;
  color: #fff;
}
.stat-info {
  margin-left: 20px;
}
.stat-value {
  font-size: 28px;
  font-weight: bold;
  color: #333;
}
.stat-label {
  color: #999;
  margin-top: 5px;
}
.hot-item {
  display: flex;
  align-items: center;
  padding: 10px 0;
  border-bottom: 1px solid #eee;
}
.hot-item:last-child {
  border-bottom: none;
}
.rank {
  width: 24px;
  height: 24px;
  border-radius: 50%;
  background: #ddd;
  color: #666;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 12px;
}
.rank.top {
  background: #f56c6c;
  color: #fff;
}
.name {
  flex: 1;
  margin: 0 10px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.sales {
  color: #999;
}
.user-dashboard {
  max-width: 600px;
  margin: 0 auto;
}
</style>
