<template>
  <div class="dashboard">
    <h2>房东控制台</h2>
    <div class="stat-cards">
      <div class="stat-card">
        <div class="stat-icon" style="background: #e6f7ff"><el-icon><House /></el-icon></div>
        <div class="stat-info">
          <p class="stat-num">{{ stats.houseCount || 0 }}</p>
          <p class="stat-label">我的房源</p>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon" style="background: #fff7e6"><el-icon><Calendar /></el-icon></div>
        <div class="stat-info">
          <p class="stat-num">{{ stats.pendingAppointments || 0 }}</p>
          <p class="stat-label">待处理预约</p>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon" style="background: #f6ffed"><el-icon><Document /></el-icon></div>
        <div class="stat-info">
          <p class="stat-num">{{ stats.activeContracts || 0 }}</p>
          <p class="stat-label">生效合同</p>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon" style="background: #fff1f0"><el-icon><Money /></el-icon></div>
        <div class="stat-info">
          <p class="stat-num">¥{{ stats.totalIncome || 0 }}</p>
          <p class="stat-label">累计收入</p>
        </div>
      </div>
    </div>

    <div class="quick-actions">
      <h3>快捷操作</h3>
      <div class="action-grid">
        <el-button type="primary" @click="$router.push('/landlord/house/publish')">
          <el-icon><Plus /></el-icon>发布房源
        </el-button>
        <el-button @click="$router.push('/landlord/appointments')">
          <el-icon><Calendar /></el-icon>预约管理
        </el-button>
        <el-button @click="$router.push('/landlord/contracts')">
          <el-icon><Document /></el-icon>合同管理
        </el-button>
        <el-button @click="$router.push('/landlord/repairs')">
          <el-icon><Tools /></el-icon>报修处理
        </el-button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { houseApi, appointmentApi, contractApi, billApi } from '@/api'

const stats = ref({})

onMounted(async () => {
  try {
    const [houseRes, appointRes, contractRes, billRes] = await Promise.all([
      houseApi.getMyHouses({ page: 1, size: 1 }),
      appointmentApi.getList({ page: 1, size: 100, status: 0 }),
      contractApi.getList({ page: 1, size: 100, status: 1 }),
      billApi.getStatistics()
    ])
    stats.value = {
      houseCount: houseRes.data?.total || 0,
      pendingAppointments: appointRes.data?.total || 0,
      activeContracts: contractRes.data?.total || 0,
      totalIncome: billRes.data?.paidAmount || 0
    }
  } catch (e) {}
})
</script>

<style scoped>
.dashboard h2 {
  margin-bottom: 24px;
}

.stat-cards {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
  margin-bottom: 30px;
}

.stat-card {
  background: #fff;
  border-radius: 12px;
  padding: 24px;
  display: flex;
  align-items: center;
  gap: 16px;
}

.stat-icon {
  width: 56px;
  height: 56px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 24px;
  color: #1890ff;
}

.stat-num {
  font-size: 28px;
  font-weight: bold;
  color: #333;
}

.stat-label {
  color: #999;
  font-size: 14px;
}

.quick-actions {
  background: #fff;
  border-radius: 12px;
  padding: 24px;
}

.quick-actions h3 {
  margin-bottom: 16px;
}

.action-grid {
  display: flex;
  gap: 12px;
}
</style>
