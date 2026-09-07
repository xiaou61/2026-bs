<template>
  <div>
    <el-row :gutter="12">
      <el-col v-for="item in cards" :key="item.label" :span="6">
        <el-card>
          <div class="card">
            <span>{{ item.label }}</span>
            <b>{{ item.value }}</b>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <template v-if="isAdmin">
      <el-row :gutter="12" style="margin-top: 12px">
        <el-col :span="12"><el-card><div ref="departmentRef" class="chart" /></el-card></el-col>
        <el-col :span="12"><el-card><div ref="trendRef" class="chart" /></el-card></el-col>
      </el-row>
    </template>

    <template v-else-if="isPatient">
      <el-card style="margin-top: 12px">
        <template #header>门诊推荐</template>
        <el-carousel height="240px" indicator-position="outside">
          <el-carousel-item v-for="item in banners" :key="item.id">
            <div class="banner" :style="{ backgroundImage: `linear-gradient(rgba(15,23,42,.35), rgba(15,23,42,.35)), url(${item.imageUrl})` }">
              <div class="banner-content">
                <h3>{{ item.title }}</h3>
                <p>{{ item.description }}</p>
              </div>
            </div>
          </el-carousel-item>
        </el-carousel>
      </el-card>

      <el-card style="margin-top: 12px">
        <template #header>热门医生</template>
        <el-table :data="hotDoctors">
          <el-table-column prop="name" label="医生姓名" />
          <el-table-column prop="departmentName" label="所属科室" />
          <el-table-column prop="appointmentCount" label="预约量" />
        </el-table>
      </el-card>
    </template>

    <el-card style="margin-top: 12px">
      <template #header>最新公告</template>
      <el-empty v-if="!notices.length" description="暂无公告" />
      <el-timeline v-else>
        <el-timeline-item v-for="item in notices" :key="item.id" :timestamp="item.createTime">
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
import { getAppointmentTrend, getDashboardStats, getDepartmentRank, getHotDoctors, getPublicBanners, getPublicNotices } from '../api'
import { useUserStore } from '../store/user'

const userStore = useUserStore()
const isAdmin = computed(() => userStore.user?.role === 'ADMIN')
const isPatient = computed(() => userStore.user?.role === 'PATIENT')

const stats = ref({})
const notices = ref([])
const banners = ref([])
const hotDoctors = ref([])
const departmentRef = ref()
const trendRef = ref()

const cards = computed(() => {
  if (isAdmin.value) {
    return [
      { label: '用户总数', value: stats.value.userCount || 0 },
      { label: '科室总数', value: stats.value.departmentCount || 0 },
      { label: '医生总数', value: stats.value.doctorCount || 0 },
      { label: '预约总数', value: stats.value.appointmentCount || 0 }
    ]
  }
  if (userStore.user?.role === 'DOCTOR') {
    return [
      { label: '我的排班', value: stats.value.scheduleCount || 0 },
      { label: '预约患者', value: stats.value.appointmentCount || 0 },
      { label: '已完成', value: stats.value.finishCount || 0 },
      { label: '评价数量', value: stats.value.reviewCount || 0 }
    ]
  }
  return [
    { label: '我的挂号', value: stats.value.appointmentCount || 0 },
    { label: '已支付', value: stats.value.paidCount || 0 },
    { label: '就诊卡', value: stats.value.cardCount || 0 },
    { label: '我的评价', value: stats.value.reviewCount || 0 }
  ]
})

const loadAdminCharts = async () => {
  const [rankRes, trendRes] = await Promise.all([getDepartmentRank(), getAppointmentTrend()])
  await nextTick()
  const departmentChart = echarts.init(departmentRef.value)
  departmentChart.setOption({
    tooltip: { trigger: 'item' },
    series: [{ type: 'pie', radius: ['45%', '70%'], data: rankRes.data || [] }]
  })
  const trendChart = echarts.init(trendRef.value)
  trendChart.setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: (trendRes.data || []).map(item => item.date) },
    yAxis: { type: 'value' },
    series: [{ type: 'line', smooth: true, data: (trendRes.data || []).map(item => item.count) }]
  })
}

onMounted(async () => {
  const [statsRes, noticeRes] = await Promise.all([getDashboardStats(), getPublicNotices()])
  stats.value = statsRes.data || {}
  notices.value = noticeRes.data || []
  if (isAdmin.value) {
    await loadAdminCharts()
  }
  if (isPatient.value) {
    const [bannerRes, hotRes] = await Promise.all([getPublicBanners(), getHotDoctors()])
    banners.value = bannerRes.data || []
    hotDoctors.value = hotRes.data || []
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
  font-size: 26px;
  color: #0f172a;
}

.chart {
  height: 320px;
}

.banner {
  height: 240px;
  background-size: cover;
  background-position: center;
  display: flex;
  align-items: flex-end;
}

.banner-content {
  padding: 24px;
  color: #fff;
}

.banner-content h3 {
  margin: 0 0 8px;
}

.banner-content p {
  margin: 0;
}
</style>
