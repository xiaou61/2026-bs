<template>
  <div class="dashboard-container">
    <el-row :gutter="20" class="stats-row">
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-icon" style="background: #409eff">
            <el-icon><user /></el-icon>
          </div>
          <div class="stat-content">
            <div class="stat-value">{{ statistics?.totalUsers || 0 }}</div>
            <div class="stat-label">用户总数</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-icon" style="background: #67c23a">
            <el-icon><document-copy /></el-icon>
          </div>
          <div class="stat-content">
            <div class="stat-value">{{ statistics?.totalOrders || 0 }}</div>
            <div class="stat-label">订单总数</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-icon" style="background: #e6a23c">
            <el-icon><coin /></el-icon>
          </div>
          <div class="stat-content">
            <div class="stat-value">¥{{ statistics?.totalAmount || 0 }}</div>
            <div class="stat-label">交易金额</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-icon" style="background: #f56c6c">
            <el-icon><warning /></el-icon>
          </div>
          <div class="stat-content">
            <div class="stat-value">{{ statistics?.pendingComplaints || 0 }}</div>
            <div class="stat-label">待处理投诉</div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>今日订单统计</span>
          </template>
          <el-descriptions :column="1" border>
            <el-descriptions-item label="今日订单">{{ statistics?.todayOrders || 0 }}</el-descriptions-item>
            <el-descriptions-item label="待接单">{{ statistics?.pendingOrders || 0 }}</el-descriptions-item>
            <el-descriptions-item label="进行中">{{ statistics?.processingOrders || 0 }}</el-descriptions-item>
            <el-descriptions-item label="已完成">{{ statistics?.completedOrders || 0 }}</el-descriptions-item>
            <el-descriptions-item label="已取消">{{ statistics?.cancelledOrders || 0 }}</el-descriptions-item>
          </el-descriptions>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>用户活跃度</span>
          </template>
          <el-descriptions :column="1" border>
            <el-descriptions-item label="今日活跃用户">{{ statistics?.activeUsersToday || 0 }}</el-descriptions-item>
            <el-descriptions-item label="本周活跃用户">{{ statistics?.activeUsersWeek || 0 }}</el-descriptions-item>
            <el-descriptions-item label="本月活跃用户">{{ statistics?.activeUsersMonth || 0 }}</el-descriptions-item>
            <el-descriptions-item label="订单完成率">{{ statistics?.completionRate || 0 }}%</el-descriptions-item>
          </el-descriptions>
        </el-card>
      </el-col>
    </el-row>

    <el-card class="chart-card">
      <template #header>
        <span>快速操作</span>
      </template>
      <div class="quick-actions">
        <el-button type="primary" @click="$router.push('/admin/users')">
          <el-icon><user /></el-icon>
          <span>用户管理</span>
        </el-button>
        <el-button type="success" @click="$router.push('/admin/orders')">
          <el-icon><document-copy /></el-icon>
          <span>订单管理</span>
        </el-button>
        <el-button type="warning" @click="$router.push('/admin/complaints')">
          <el-icon><warning /></el-icon>
          <span>投诉管理</span>
        </el-button>
        <el-button type="info" @click="$router.push('/admin/transactions')">
          <el-icon><coin /></el-icon>
          <span>交易流水</span>
        </el-button>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getStatistics } from '../../api/admin'
import { User, DocumentCopy, Coin, Warning } from '@element-plus/icons-vue'

const statistics = ref(null)

const loadStatistics = async () => {
  try {
    statistics.value = await getStatistics()
  } catch (error) {
    console.error('加载统计数据失败', error)
  }
}

onMounted(() => {
  loadStatistics()
})
</script>

<style scoped>
.dashboard-container {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.stats-row {
  margin-bottom: 20px;
}

.stat-card {
  display: flex;
  align-items: center;
}

.stat-card :deep(.el-card__body) {
  display: flex;
  align-items: center;
  gap: 20px;
  width: 100%;
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-size: 30px;
}

.stat-content {
  flex: 1;
}

.stat-value {
  font-size: 28px;
  font-weight: bold;
  color: #303133;
  margin-bottom: 5px;
}

.stat-label {
  font-size: 14px;
  color: #909399;
}

.chart-card {
  margin-top: 20px;
}

.quick-actions {
  display: flex;
  gap: 20px;
  flex-wrap: wrap;
}

.quick-actions .el-button {
  flex: 1;
  min-width: 150px;
  height: 60px;
  font-size: 16px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 8px;
}
</style>

