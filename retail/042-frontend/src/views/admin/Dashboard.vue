<template>
  <div class="admin-dashboard">
    <h2>数据统计</h2>
    <div class="stat-cards">
      <div class="stat-card">
        <div class="stat-icon" style="background: #e6f7ff"><el-icon><User /></el-icon></div>
        <div class="stat-info">
          <p class="stat-num">{{ stats.totalUsers || 0 }}</p>
          <p class="stat-label">总用户数</p>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon" style="background: #fff7e6"><el-icon><House /></el-icon></div>
        <div class="stat-info">
          <p class="stat-num">{{ stats.totalHouses || 0 }}</p>
          <p class="stat-label">房源总数</p>
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
          <p class="stat-label">平台总收入</p>
        </div>
      </div>
    </div>

    <div class="detail-cards">
      <div class="detail-card">
        <h3>用户统计</h3>
        <div class="detail-item"><span class="label">房东数量</span><span class="value">{{ stats.landlordCount || 0 }}</span></div>
        <div class="detail-item"><span class="label">租客数量</span><span class="value">{{ stats.tenantCount || 0 }}</span></div>
        <div class="detail-item"><span class="label">今日新增</span><span class="value highlight">{{ stats.todayUsers || 0 }}</span></div>
      </div>
      <div class="detail-card">
        <h3>房源统计</h3>
        <div class="detail-item"><span class="label">可租房源</span><span class="value">{{ stats.availableHouses || 0 }}</span></div>
        <div class="detail-item"><span class="label">已租房源</span><span class="value">{{ stats.rentedHouses || 0 }}</span></div>
        <div class="detail-item"><span class="label">今日发布</span><span class="value highlight">{{ stats.todayHouses || 0 }}</span></div>
      </div>
      <div class="detail-card">
        <h3>业务统计</h3>
        <div class="detail-item"><span class="label">合同总数</span><span class="value">{{ stats.totalContracts || 0 }}</span></div>
        <div class="detail-item"><span class="label">今日预约</span><span class="value highlight">{{ stats.todayAppointments || 0 }}</span></div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { adminApi } from '@/api'

const stats = ref({})

onMounted(async () => {
  try {
    const res = await adminApi.getStatistics()
    stats.value = res.data || {}
  } catch (e) {}
})
</script>

<style scoped>
.admin-dashboard h2 { margin-bottom: 24px; }
.stat-cards { display: grid; grid-template-columns: repeat(4, 1fr); gap: 20px; margin-bottom: 30px; }
.stat-card { background: #fff; border-radius: 12px; padding: 24px; display: flex; align-items: center; gap: 16px; }
.stat-icon { width: 56px; height: 56px; border-radius: 12px; display: flex; align-items: center; justify-content: center; font-size: 24px; color: #1890ff; }
.stat-num { font-size: 28px; font-weight: bold; color: #333; }
.stat-label { color: #999; font-size: 14px; }
.detail-cards { display: grid; grid-template-columns: repeat(3, 1fr); gap: 20px; }
.detail-card { background: #fff; border-radius: 12px; padding: 24px; }
.detail-card h3 { font-size: 16px; margin-bottom: 16px; padding-bottom: 12px; border-bottom: 1px solid #f0f0f0; }
.detail-item { display: flex; justify-content: space-between; padding: 8px 0; }
.detail-item .label { color: #666; }
.detail-item .value { font-weight: bold; }
.detail-item .value.highlight { color: #409eff; }
</style>
