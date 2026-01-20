<template>
  <div class="home">
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card class="stat-card">
          <el-statistic title="我的宠物" :value="petCount">
            <template #prefix><el-icon style="color: #409eff"><Cat /></el-icon></template>
          </el-statistic>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="stat-card">
          <el-statistic title="进行中的预约" :value="activeBookings">
            <template #prefix><el-icon style="color: #67c23a"><Calendar /></el-icon></template>
          </el-statistic>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="stat-card">
          <el-statistic title="历史预约" :value="totalBookings">
            <template #prefix><el-icon style="color: #909399"><Tickets /></el-icon></template>
          </el-statistic>
        </el-card>
      </el-col>
    </el-row>

    <el-card style="margin-top: 20px">
      <template #header>
        <span>快捷操作</span>
      </template>
      <el-row :gutter="20">
        <el-col :span="6">
          <el-button type="primary" size="large" style="width: 100%" @click="router.push('/pets')">
            <el-icon><Plus /></el-icon>添加宠物
          </el-button>
        </el-col>
        <el-col :span="6">
          <el-button type="success" size="large" style="width: 100%" @click="router.push('/providers')">
            <el-icon><Search /></el-icon>查找寄养
          </el-button>
        </el-col>
        <el-col :span="6">
          <el-button type="warning" size="large" style="width: 100%" @click="router.push('/bookings')">
            <el-icon><List /></el-icon>我的预约
          </el-button>
        </el-col>
        <el-col :span="6">
          <el-button type="info" size="large" style="width: 100%" @click="router.push('/profile')">
            <el-icon><User /></el-icon>个人中心
          </el-button>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getPetList, getBookingPage } from '../api'
import { Calendar, Tickets, Plus, Search, List, User } from '@element-plus/icons-vue'

const router = useRouter()
const petCount = ref(0)
const activeBookings = ref(0)
const totalBookings = ref(0)

onMounted(async () => {
  try {
    const petRes: any = await getPetList()
    petCount.value = petRes.data?.length || 0

    const bookingRes: any = await getBookingPage({ current: 1, size: 100 })
    const bookings = bookingRes.data?.records || []
    totalBookings.value = bookings.length
    activeBookings.value = bookings.filter((b: any) => [0, 1, 2].includes(b.status)).length
  } catch (e) {
    console.error(e)
  }
})
</script>

<style scoped>
.home {
  padding: 20px;
}
.stat-card {
  text-align: center;
}
</style>
