<template>
  <div class="dashboard">
    <el-row :gutter="15">
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <el-icon class="stat-icon" style="background: #409eff"><User /></el-icon>
            <div class="stat-info">
              <div class="stat-num">{{ stats.alumniCount || 0 }}</div>
              <div class="stat-label">校友总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <el-icon class="stat-icon" style="background: #67c23a"><Calendar /></el-icon>
            <div class="stat-info">
              <div class="stat-num">{{ stats.activityCount || 0 }}</div>
              <div class="stat-label">活动数量</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <el-icon class="stat-icon" style="background: #e6a23c"><Document /></el-icon>
            <div class="stat-info">
              <div class="stat-num">{{ stats.newsCount || 0 }}</div>
              <div class="stat-label">新闻数量</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <el-icon class="stat-icon" style="background: #f56c6c"><Present /></el-icon>
            <div class="stat-info">
              <div class="stat-num">¥{{ stats.donationTotal || 0 }}</div>
              <div class="stat-label">捐赠总额</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="15" style="margin-top: 15px">
      <el-col :span="12">
        <el-card>
          <template #header>最新新闻</template>
          <el-table :data="newsList" :show-header="false">
            <el-table-column prop="title">
              <template #default="{ row }">
                <el-link @click="$router.push(`/news/${row.id}`)">{{ row.title }}</el-link>
              </template>
            </el-table-column>
            <el-table-column prop="createTime" width="180" />
          </el-table>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>最新活动</template>
          <el-table :data="activityList" :show-header="false">
            <el-table-column prop="title">
              <template #default="{ row }">
                <el-link @click="$router.push(`/activity/${row.id}`)">{{ row.title }}</el-link>
              </template>
            </el-table-column>
            <el-table-column prop="startTime" width="180" />
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getStats, getNewsList, getActivityList } from '../api'

const stats = ref({})
const newsList = ref([])
const activityList = ref([])

onMounted(async () => {
  const [statsRes, newsRes, activityRes] = await Promise.all([
    getStats(),
    getNewsList({ pageNum: 1, pageSize: 5 }),
    getActivityList({ pageNum: 1, pageSize: 5 })
  ])
  stats.value = statsRes.data
  newsList.value = newsRes.data.records
  activityList.value = activityRes.data.records
})
</script>

<style scoped>
.stat-card {
  cursor: pointer;
}
.stat-content {
  display: flex;
  align-items: center;
  gap: 15px;
}
.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 28px;
  color: #fff;
}
.stat-num {
  font-size: 28px;
  font-weight: bold;
  color: #333;
}
.stat-label {
  color: #999;
  margin-top: 5px;
}
</style>
