<template>
  <div>
    <el-row :gutter="12" v-if="isAdmin">
      <el-col :span="6"><el-card><div class="card"><span>用户总数</span><b>{{ stats.userCount || 0 }}</b></div></el-card></el-col>
      <el-col :span="6"><el-card><div class="card"><span>商品总数</span><b>{{ stats.itemCount || 0 }}</b></div></el-card></el-col>
      <el-col :span="6"><el-card><div class="card"><span>订单总数</span><b>{{ stats.orderCount || 0 }}</b></div></el-card></el-col>
      <el-col :span="6"><el-card><div class="card"><span>成交总额</span><b>¥{{ Number(stats.totalAmount || 0).toFixed(2) }}</b></div></el-card></el-col>
    </el-row>
    <el-row :gutter="12" v-else>
      <el-col :span="6"><el-card><div class="card"><span>我的作品</span><b>{{ userStats.itemCount || 0 }}</b></div></el-card></el-col>
      <el-col :span="6"><el-card><div class="card"><span>我的订单</span><b>{{ userStats.orderCount || 0 }}</b></div></el-card></el-col>
      <el-col :span="6"><el-card><div class="card"><span>我的收藏</span><b>{{ userStats.favoriteCount || 0 }}</b></div></el-card></el-col>
      <el-col :span="6"><el-card><div class="card"><span>待处理申诉</span><b>{{ userStats.pendingComplaint || 0 }}</b></div></el-card></el-col>
    </el-row>

    <el-row :gutter="12" style="margin-top: 12px" v-if="isAdmin">
      <el-col :span="12"><el-card><div ref="categoryRef" class="chart"></div></el-card></el-col>
      <el-col :span="12"><el-card><div ref="orderRef" class="chart"></div></el-card></el-col>
    </el-row>

    <el-card style="margin-top: 12px" v-if="isAdmin">
      <template #header>热销商品</template>
      <el-table :data="hotItems" size="small">
        <el-table-column prop="title" label="商品" min-width="220" />
        <el-table-column prop="soldCount" label="销量" width="120" />
        <el-table-column prop="price" label="价格" width="120" />
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
import { getAnnouncementList, getCategoryStats, getDashboardStats, getHotItems, getItemPage, getMyComplaint, getMyFavorite, getMyOrders, getOrderTrend } from '../api'
import { useUserStore } from '../store/user'

const userStore = useUserStore()
const isAdmin = computed(() => userStore.user?.role === 'ADMIN')

const stats = ref({})
const userStats = ref({})
const hotItems = ref([])
const announcements = ref([])
const categoryRef = ref()
const orderRef = ref()

const loadAnnouncements = async () => {
  const res = await getAnnouncementList()
  announcements.value = res.data || []
}

const loadAdminData = async () => {
  const [sRes, cRes, oRes, hRes] = await Promise.all([
    getDashboardStats(),
    getCategoryStats(),
    getOrderTrend(),
    getHotItems()
  ])
  stats.value = sRes.data || {}
  hotItems.value = hRes.data || []
  await nextTick()
  const cChart = echarts.init(categoryRef.value)
  cChart.setOption({
    tooltip: { trigger: 'item' },
    series: [{ type: 'pie', radius: ['40%', '70%'], data: cRes.data || [] }]
  })
  const oChart = echarts.init(orderRef.value)
  oChart.setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: (oRes.data || []).map(i => i.date) },
    yAxis: { type: 'value' },
    series: [{ type: 'line', smooth: true, data: (oRes.data || []).map(i => i.count) }]
  })
}

const loadUserData = async () => {
  const [itemRes, orderRes, favRes, complaintRes] = await Promise.all([
    getItemPage({ pageNum: 1, pageSize: 1 }),
    getMyOrders({ pageNum: 1, pageSize: 1 }),
    getMyFavorite(),
    getMyComplaint({ pageNum: 1, pageSize: 1, status: 0 })
  ])
  userStats.value = {
    itemCount: itemRes.data.total || 0,
    orderCount: orderRes.data.total || 0,
    favoriteCount: (favRes.data || []).length,
    pendingComplaint: complaintRes.data.total || 0
  }
}

onMounted(async () => {
  await loadAnnouncements()
  if (isAdmin.value) {
    await loadAdminData()
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

