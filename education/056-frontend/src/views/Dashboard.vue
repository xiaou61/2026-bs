<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-icon" style="background: #409EFF"><el-icon><User /></el-icon></div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.userCount || 0 }}</div>
              <div class="stat-label">参赛用户</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-icon" style="background: #67C23A"><el-icon><Trophy /></el-icon></div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.competitionCount || 0 }}</div>
              <div class="stat-label">竞赛总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-icon" style="background: #E6A23C"><el-icon><Document /></el-icon></div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.workCount || 0 }}</div>
              <div class="stat-label">作品总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-icon" style="background: #F56C6C"><el-icon><Avatar /></el-icon></div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.judgeCount || 0 }}</div>
              <div class="stat-label">评委人数</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="16">
        <el-card>
          <template #header>最近竞赛</template>
          <el-table :data="stats.recentCompetitions" style="width: 100%">
            <el-table-column prop="title" label="竞赛名称" />
            <el-table-column prop="status" label="状态" width="100">
              <template #default="{ row }">
                <el-tag :type="statusType[row.status]">{{ statusText[row.status] }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间" width="180" />
          </el-table>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card>
          <template #header>快捷操作</template>
          <div class="quick-actions">
            <el-button type="primary" @click="$router.push('/competition/add')" v-if="userStore.userInfo?.role === 0">新增竞赛</el-button>
            <el-button type="success" @click="$router.push('/work')" v-if="userStore.userInfo?.role === 0">作品审核</el-button>
            <el-button type="warning" @click="$router.push('/score')" v-if="userStore.userInfo?.role <= 1">去评分</el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getDashboard } from '../api'
import { useUserStore } from '../store/user'

const userStore = useUserStore()
const stats = ref({})
const statusText = { 0: '草稿', 1: '进行中', 2: '已结束', 3: '已下架' }
const statusType = { 0: 'info', 1: 'success', 2: 'warning', 3: 'danger' }

onMounted(async () => {
  const res = await getDashboard()
  stats.value = res.data
})
</script>

<style scoped>
.stat-card {
  display: flex;
  align-items: center;
  gap: 20px;
}
.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 8px;
  display: flex;
  justify-content: center;
  align-items: center;
  color: #fff;
  font-size: 28px;
}
.stat-value {
  font-size: 28px;
  font-weight: bold;
  color: #333;
}
.stat-label {
  color: #999;
  margin-top: 5px;
}
.quick-actions {
  display: flex;
  flex-direction: column;
  gap: 15px;
}
.quick-actions .el-button {
  width: 100%;
}
</style>
