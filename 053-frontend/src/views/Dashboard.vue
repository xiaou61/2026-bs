<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-item">
            <el-icon :size="40" color="#409EFF"><User /></el-icon>
            <div class="stat-info"><div class="stat-value">{{ stats.userCount || 0 }}</div><div class="stat-label">用户数量</div></div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-item">
            <el-icon :size="40" color="#E6A23C"><Warning /></el-icon>
            <div class="stat-info"><div class="stat-value">{{ stats.disasterCount || 0 }}</div><div class="stat-label">灾情数量</div></div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-item">
            <el-icon :size="40" color="#67C23A"><House /></el-icon>
            <div class="stat-info"><div class="stat-value">{{ stats.warehouseCount || 0 }}</div><div class="stat-label">仓库数量</div></div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-item">
            <el-icon :size="40" color="#F56C6C"><Van /></el-icon>
            <div class="stat-info"><div class="stat-value">{{ stats.dispatchCount || 0 }}</div><div class="stat-label">调度数量</div></div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="12">
        <el-card>
          <template #header>最新公告</template>
          <el-empty v-if="!notices.length" description="暂无公告" />
          <div v-else class="notice-list">
            <div v-for="item in notices" :key="item.id" class="notice-item">
              <span class="notice-title">{{ item.title }}</span>
              <span class="notice-time">{{ item.createTime }}</span>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>救援任务统计</template>
          <div class="stat-info-row">
            <div class="stat-box"><div class="stat-num">{{ stats.taskCount || 0 }}</div><div>任务总数</div></div>
            <div class="stat-box"><div class="stat-num">{{ stats.materialCount || 0 }}</div><div>物资种类</div></div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getStats, getPublishedNotices } from '../api'

const stats = ref({})
const notices = ref([])

onMounted(async () => {
  const res = await getStats()
  stats.value = res.data
  const noticeRes = await getPublishedNotices()
  notices.value = noticeRes.data?.slice(0, 5) || []
})
</script>

<style scoped>
.dashboard { padding: 10px; }
.stat-item { display: flex; align-items: center; gap: 20px; }
.stat-info { flex: 1; }
.stat-value { font-size: 28px; font-weight: bold; color: #333; }
.stat-label { color: #999; margin-top: 5px; }
.notice-list { max-height: 200px; overflow-y: auto; }
.notice-item { display: flex; justify-content: space-between; padding: 10px 0; border-bottom: 1px solid #eee; }
.notice-title { color: #333; }
.notice-time { color: #999; font-size: 12px; }
.stat-info-row { display: flex; justify-content: space-around; padding: 20px 0; }
.stat-box { text-align: center; }
.stat-num { font-size: 36px; font-weight: bold; color: #409EFF; }
</style>
