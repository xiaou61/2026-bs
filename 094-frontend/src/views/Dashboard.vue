<template>
  <div>
    <el-card>
      <template #header>
        <span>{{ isManager ? '宠物咖啡馆经营概览' : '顾客欢迎页' }}</span>
      </template>
      <div v-if="isManager" class="card-grid">
        <el-statistic title="今日销售额" :value="Number(stats.todaySales || 0)" :precision="2" />
        <el-statistic title="今日订单数" :value="Number(stats.todayOrders || 0)" />
        <el-statistic title="门店总数" :value="Number(stats.totalShops || 0)" />
        <el-statistic title="待回复评价" :value="Number(stats.pendingReviews || 0)" />
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
          <div class="label">欢迎回来</div>
          <div class="value">{{ user?.nickname || user?.username }}</div>
        </div>
      </div>
    </el-card>

    <el-row v-if="!isManager" :gutter="16" class="mt16">
      <el-col :span="14">
        <el-card>
          <template #header>
            <span>热门门店推荐</span>
          </template>
          <el-row :gutter="16">
            <el-col v-for="item in shopList" :key="item.id" :span="12">
              <div class="shop-card">
                <h4>{{ item.name }}</h4>
                <p>区域：{{ item.areaName || '-' }}</p>
                <p>主题：{{ item.theme || '-' }}</p>
                <p>营业：{{ item.openTime || '--' }} - {{ item.closeTime || '--' }}</p>
              </div>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
      <el-col :span="10">
        <el-card>
          <template #header>
            <span>人气店宠</span>
          </template>
          <div v-for="item in petList" :key="item.id" class="pet-item">
            <div>
              <div class="pet-name">{{ item.name }}</div>
              <div class="pet-desc">{{ item.shopName }} · {{ item.petType }} · {{ item.personality || '亲人互动' }}</div>
            </div>
            <el-tag type="success">星级 {{ item.starLevel || 0 }}</el-tag>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row v-if="isManager" :gutter="16" class="mt16">
      <el-col :span="12">
        <el-card>
          <template #header>近7天销售趋势</template>
          <div ref="salesRef" style="height: 320px"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>热门菜单排行</template>
          <div ref="menuRef" style="height: 320px"></div>
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
import { getDashboardStats, getHotMenus, getPetPublicList, getPublicNoticeList, getSalesTrend, getShopPublicList } from '../api'

const userStore = useUserStore()
const user = computed(() => userStore.user || {})
const role = computed(() => (user.value?.role || '').toUpperCase())
const isManager = computed(() => ['ADMIN', 'STAFF'].includes(role.value))
const roleMap = {
  ADMIN: '管理员',
  STAFF: '店长',
  CUSTOMER: '顾客'
}
const roleLabel = computed(() => roleMap[role.value] || '访客')

const stats = ref({})
const shopList = ref([])
const petList = ref([])
const noticeList = ref([])
const salesRef = ref()
const menuRef = ref()

const loadAdmin = async () => {
  const [a, b, c] = await Promise.all([getDashboardStats(), getSalesTrend({ days: 7 }), getHotMenus()])
  stats.value = a.data || {}
  await nextTick()
  const salesChart = echarts.init(salesRef.value)
  salesChart.setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: (b.data || []).map(i => i.date) },
    yAxis: { type: 'value' },
    series: [{ type: 'line', smooth: true, data: (b.data || []).map(i => Number(i.sales || 0)) }]
  })
  const menuChart = echarts.init(menuRef.value)
  menuChart.setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: (c.data || []).map(i => i.menuName) },
    yAxis: { type: 'value' },
    series: [{ type: 'bar', data: (c.data || []).map(i => Number(i.salesAmount || 0)) }]
  })
}

const loadCustomer = async () => {
  const [shopRes, petRes] = await Promise.all([getShopPublicList(), getPetPublicList()])
  shopList.value = (shopRes.data || []).slice(0, 4)
  petList.value = (petRes.data || []).slice(0, 5)
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
.shop-card {
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
  color: #294234;
}

.shop-card h4 {
  margin: 0 0 8px;
  color: #294234;
}

.shop-card p {
  margin: 6px 0 0;
  color: #667085;
}

.pet-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px 0;
  border-bottom: 1px solid #eef2f6;
}

.pet-item:last-child {
  border-bottom: none;
}

.pet-name {
  font-weight: 600;
  color: #294234;
}

.pet-desc {
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
