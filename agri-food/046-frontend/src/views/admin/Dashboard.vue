<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <el-icon size="48" color="#409eff"><User /></el-icon>
            <div class="stat-info">
              <div class="stat-value">{{ stats.userCount || 0 }}</div>
              <div class="stat-label">居民用户</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <el-icon size="48" color="#67c23a"><UserFilled /></el-icon>
            <div class="stat-info">
              <div class="stat-value">{{ stats.collectorCount || 0 }}</div>
              <div class="stat-label">回收员</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <el-icon size="48" color="#e6a23c"><Document /></el-icon>
            <div class="stat-info">
              <div class="stat-value">{{ stats.totalOrders || 0 }}</div>
              <div class="stat-label">总订单数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <el-icon size="48" color="#f56c6c"><CircleCheck /></el-icon>
            <div class="stat-info">
              <div class="stat-value">{{ stats.completedOrders || 0 }}</div>
              <div class="stat-label">已完成订单</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getStatistics } from '@/api'

const stats = ref<any>({})

onMounted(async () => {
  const res: any = await getStatistics()
  stats.value = res.data
})
</script>

<style scoped>
.stat-card { display: flex; align-items: center; gap: 20px; }
.stat-value { font-size: 32px; font-weight: bold; }
.stat-label { color: #909399; }
</style>
