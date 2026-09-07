<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card shadow="hover">
          <div class="stat-card">
            <el-icon size="48" color="#409eff"><User /></el-icon>
            <div class="stat-info">
              <div class="stat-value">{{ stats.elderCount || 0 }}</div>
              <div class="stat-label">在住老人</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover">
          <div class="stat-card">
            <el-icon size="48" color="#67c23a"><House /></el-icon>
            <div class="stat-info">
              <div class="stat-value">{{ stats.bedStats?.available || 0 }}</div>
              <div class="stat-label">空闲床位</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover">
          <div class="stat-card">
            <el-icon size="48" color="#e6a23c"><UserFilled /></el-icon>
            <div class="stat-info">
              <div class="stat-value">{{ stats.userCount || 0 }}</div>
              <div class="stat-label">系统用户</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-card style="margin-top: 20px">
      <template #header>床位使用情况</template>
      <el-progress :percentage="bedUsagePercent" :stroke-width="20" :format="() => `已使用 ${stats.bedStats?.occupied || 0} / 总计 ${stats.bedStats?.total || 0}`" />
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { getStatistics } from '@/api'

const stats = ref<any>({})

const bedUsagePercent = computed(() => {
  if (!stats.value.bedStats?.total) return 0
  return Math.round((stats.value.bedStats.occupied / stats.value.bedStats.total) * 100)
})

onMounted(async () => {
  const res: any = await getStatistics()
  stats.value = res.data
})
</script>

<style scoped>
.stat-card {
  display: flex;
  align-items: center;
  gap: 20px;
}
.stat-value {
  font-size: 32px;
  font-weight: bold;
}
.stat-label {
  color: #909399;
}
</style>
