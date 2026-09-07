<template>
  <div class="dashboard">
    <h2>租客中心</h2>
    <div class="stat-cards">
      <div class="stat-card">
        <div class="stat-icon" style="background: #e6f7ff"><el-icon><Star /></el-icon></div>
        <div class="stat-info">
          <p class="stat-num">{{ stats.favoriteCount || 0 }}</p>
          <p class="stat-label">收藏房源</p>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon" style="background: #fff7e6"><el-icon><Calendar /></el-icon></div>
        <div class="stat-info">
          <p class="stat-num">{{ stats.appointmentCount || 0 }}</p>
          <p class="stat-label">我的预约</p>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon" style="background: #f6ffed"><el-icon><Document /></el-icon></div>
        <div class="stat-info">
          <p class="stat-num">{{ stats.contractCount || 0 }}</p>
          <p class="stat-label">租赁合同</p>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon" style="background: #fff1f0"><el-icon><Money /></el-icon></div>
        <div class="stat-info">
          <p class="stat-num">¥{{ userStore.userInfo?.balance || 0 }}</p>
          <p class="stat-label">账户余额</p>
        </div>
      </div>
    </div>
    <div class="quick-actions">
      <h3>快捷操作</h3>
      <div class="action-grid">
        <el-button type="primary" @click="$router.push('/house')"><el-icon><Search /></el-icon>找房源</el-button>
        <el-button @click="$router.push('/tenant/favorites')"><el-icon><Star /></el-icon>我的收藏</el-button>
        <el-button @click="$router.push('/tenant/contracts')"><el-icon><Document /></el-icon>我的合同</el-button>
        <el-button @click="$router.push('/tenant/bills')"><el-icon><Money /></el-icon>我的账单</el-button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useUserStore } from '@/store/user'
import { houseApi, appointmentApi, contractApi } from '@/api'

const userStore = useUserStore()
const stats = ref({})

onMounted(async () => {
  try {
    const [favRes, appointRes, contractRes] = await Promise.all([
      houseApi.getFavorites({ page: 1, size: 1 }),
      appointmentApi.getList({ page: 1, size: 1 }),
      contractApi.getList({ page: 1, size: 1 })
    ])
    stats.value = {
      favoriteCount: favRes.data?.total || 0,
      appointmentCount: appointRes.data?.total || 0,
      contractCount: contractRes.data?.total || 0
    }
  } catch (e) {}
})
</script>

<style scoped>
.dashboard h2 { margin-bottom: 24px; }
.stat-cards { display: grid; grid-template-columns: repeat(4, 1fr); gap: 20px; margin-bottom: 30px; }
.stat-card { background: #fff; border-radius: 12px; padding: 24px; display: flex; align-items: center; gap: 16px; }
.stat-icon { width: 56px; height: 56px; border-radius: 12px; display: flex; align-items: center; justify-content: center; font-size: 24px; color: #1890ff; }
.stat-num { font-size: 28px; font-weight: bold; color: #333; }
.stat-label { color: #999; font-size: 14px; }
.quick-actions { background: #fff; border-radius: 12px; padding: 24px; }
.quick-actions h3 { margin-bottom: 16px; }
.action-grid { display: flex; gap: 12px; }
</style>
