<template>
  <div>
    <el-row :gutter="15" v-if="userRole === 'ADMIN'">
      <el-col :span="6" v-for="item in cards" :key="item.label">
        <el-card shadow="hover">
          <div style="display: flex; align-items: center; justify-content: space-between;">
            <div>
              <div style="font-size: 14px; color: #999;">{{ item.label }}</div>
              <div style="font-size: 28px; font-weight: bold; margin-top: 8px;">{{ item.value }}</div>
            </div>
            <el-icon :style="{ fontSize: '40px', color: item.color }"><component :is="item.icon" /></el-icon>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="15" style="margin-top: 15px;" v-if="userRole === 'ADMIN'">
      <el-col :span="12">
        <el-card>
          <template #header>订单统计</template>
          <div ref="orderChartRef" style="height: 300px;"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>配送统计</template>
          <div ref="deliveryChartRef" style="height: 300px;"></div>
        </el-card>
      </el-col>
    </el-row>
    <el-card v-if="userRole !== 'ADMIN'">
      <div style="text-align: center; padding: 50px;">
        <div style="font-size: 60px;">🥛</div>
        <h2 style="margin: 20px 0;">欢迎使用鲜牛奶订购系统</h2>
        <p style="color: #999;">{{ userRole === 'DELIVERY' ? '请查看今日配送任务' : '请浏览产品并创建订阅' }}</p>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted, computed, nextTick } from 'vue'
import * as echarts from 'echarts'
import { getDashboardStats, getOrderStats, getDeliveryStats } from '../api'
import { useUserStore } from '../store/user'

const userStore = useUserStore()
const userRole = computed(() => userStore.user?.role || '')
const orderChartRef = ref()
const deliveryChartRef = ref()
const cards = ref([])

const loadData = async () => {
  if (userRole.value !== 'ADMIN') return
  const res = await getDashboardStats()
  const d = res.data
  cards.value = [
    { label: '用户总数', value: d.userCount, icon: 'User', color: '#409EFF' },
    { label: '产品数量', value: d.productCount, icon: 'GoodsFilled', color: '#67C23A' },
    { label: '订单总数', value: d.orderCount, icon: 'List', color: '#E6A23C' },
    { label: '营收总额', value: '¥' + d.totalAmount, icon: 'Money', color: '#F56C6C' }
  ]
  await nextTick()
  const orderRes = await getOrderStats()
  const od = orderRes.data
  const orderChart = echarts.init(orderChartRef.value)
  orderChart.setOption({
    tooltip: { trigger: 'item' },
    legend: { bottom: 0 },
    series: [{
      type: 'pie', radius: ['40%', '70%'],
      data: [
        { value: od.pending, name: '待配送' },
        { value: od.delivering, name: '配送中' },
        { value: od.completed, name: '已完成' },
        { value: od.cancelled, name: '已取消' }
      ]
    }]
  })
  const deliveryRes = await getDeliveryStats()
  const dd = deliveryRes.data
  const deliveryChart = echarts.init(deliveryChartRef.value)
  deliveryChart.setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: ['待配送', '已完成', '异常'] },
    yAxis: { type: 'value' },
    series: [{
      type: 'bar',
      data: [
        { value: dd.pending, itemStyle: { color: '#E6A23C' } },
        { value: dd.completed, itemStyle: { color: '#67C23A' } },
        { value: dd.exception, itemStyle: { color: '#F56C6C' } }
      ]
    }]
  })
}

onMounted(loadData)
</script>
