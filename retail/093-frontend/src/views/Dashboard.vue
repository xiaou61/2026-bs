<template>
  <div>
    <el-card>
      <template #header>
        <span>{{ isManager ? '自助售货经营概览' : '顾客服务中心' }}</span>
      </template>
      <div v-if="isManager" class="card-grid">
        <el-statistic title="今日销售额" :value="Number(stats.todaySales || 0)" :precision="2" />
        <el-statistic title="今日订单数" :value="Number(stats.todayOrders || 0)" />
        <el-statistic title="设备总数" :value="Number(stats.totalMachines || 0)" />
        <el-statistic title="待处理故障" :value="Number(stats.pendingFaults || 0)" />
      </div>
      <div v-else class="customer-grid">
        <div class="customer-card">
          <div class="label">钱包余额</div>
          <div class="value">¥{{ Number(user?.balance || 0).toFixed(2) }}</div>
        </div>
        <div class="customer-card">
          <div class="label">累计消费</div>
          <div class="value">¥{{ Number(user?.totalConsume || 0).toFixed(2) }}</div>
        </div>
        <div class="customer-card">
          <div class="label">账号身份</div>
          <div class="value">{{ roleLabel }}</div>
        </div>
        <div class="customer-card">
          <div class="label">欢迎使用</div>
          <div class="value">{{ user?.nickname || user?.username }}</div>
        </div>
      </div>
    </el-card>

    <el-card v-if="!isManager" class="mt16">
      <template #header>
        <span>在线设备推荐</span>
      </template>
      <el-row :gutter="16">
        <el-col v-for="item in machineList" :key="item.id" :span="8">
          <div class="machine-card">
            <h4>{{ item.name }}</h4>
            <p>点位：{{ item.locationName || '-' }}</p>
            <p>温区：{{ item.temperatureType || '-' }}</p>
            <p>状态：{{ item.status }}</p>
          </div>
        </el-col>
      </el-row>
    </el-card>

    <el-row v-if="isManager" :gutter="16" class="mt16">
      <el-col :span="12">
        <el-card>
          <template #header>近7天销售趋势</template>
          <div ref="salesRef" style="height: 320px"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>热销商品排行</template>
          <div ref="productRef" style="height: 320px"></div>
        </el-card>
      </el-col>
    </el-row>

    <el-card class="mt16">
      <template #header>
        <span>最新公告</span>
      </template>
      <el-timeline>
        <el-timeline-item v-for="item in noticeList" :key="item.id" :timestamp="item.createTime">
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
import { getDashboardStats, getHotProducts, getMachinePublicList, getPublicNoticeList, getSalesTrend } from '../api'

const userStore = useUserStore()
const user = computed(() => userStore.user || {})
const role = computed(() => (user.value?.role || '').toUpperCase())
const isManager = computed(() => ['ADMIN', 'STAFF'].includes(role.value))
const roleMap = {
  ADMIN: '管理员',
  STAFF: '补货员',
  CUSTOMER: '顾客'
}
const roleLabel = computed(() => roleMap[role.value] || '访客')

const stats = ref({})
const machineList = ref([])
const noticeList = ref([])
const salesRef = ref()
const productRef = ref()

const loadAdmin = async () => {
  const [a, b, c] = await Promise.all([getDashboardStats(), getSalesTrend({ days: 7 }), getHotProducts()])
  stats.value = a.data || {}
  await nextTick()
  const salesChart = echarts.init(salesRef.value)
  salesChart.setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: (b.data || []).map(i => i.date) },
    yAxis: { type: 'value' },
    series: [{ type: 'line', smooth: true, data: (b.data || []).map(i => Number(i.sales || 0)) }]
  })
  const productChart = echarts.init(productRef.value)
  productChart.setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: (c.data || []).map(i => i.productName) },
    yAxis: { type: 'value' },
    series: [{ type: 'bar', data: (c.data || []).map(i => Number(i.salesAmount || 0)) }]
  })
}

const loadCustomer = async () => {
  const res = await getMachinePublicList()
  machineList.value = (res.data || []).slice(0, 6)
}

const loadNotice = async () => {
  const res = await getPublicNoticeList()
  noticeList.value = (res.data || []).slice(0, 5)
}

onMounted(async () => {
  await loadNotice()
  if (isManager.value) {
    await loadAdmin()
  } else {
    await loadCustomer()
  }
})
</script>

<style scoped>
.card-grid,
.customer-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 16px;
}

.customer-card,
.machine-card {
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
  color: #17324d;
}

.machine-card h4 {
  margin: 0 0 8px;
  color: #17324d;
}

.machine-card p {
  margin: 6px 0 0;
  color: #667085;
}

.notice-content {
  margin-top: 6px;
  color: #475467;
}

.mt16 {
  margin-top: 16px;
}
</style>
