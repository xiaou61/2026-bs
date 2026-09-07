<template>
  <div class="dashboard-container">
    <el-container>
      <el-header>
        <div class="header-content">
          <h2>管理后台 - 数据看板</h2>
          <div class="user-info">
            <span>管理员：{{ userStore.userInfo.realName || userStore.userInfo.username }}</span>
            <el-button @click="handleLogout" size="small" style="margin-left: 20px">退出</el-button>
          </div>
        </div>
      </el-header>

      <el-container>
        <el-aside width="200px">
          <el-menu :default-active="activeMenu" router>
            <el-menu-item index="/admin/dashboard">
              <el-icon><DataAnalysis /></el-icon>
              <span>数据看板</span>
            </el-menu-item>
            <el-menu-item index="/admin/audit">
              <el-icon><DocumentChecked /></el-icon>
              <span>举报审核</span>
            </el-menu-item>
          </el-menu>
        </el-aside>

        <el-main>
          <el-row :gutter="20">
            <el-col :span="6">
              <el-card shadow="hover" class="stat-card">
                <div class="stat-content">
                  <div class="stat-icon" style="background: #409eff">
                    <el-icon size="32"><Document /></el-icon>
                  </div>
                  <div class="stat-info">
                    <div class="stat-value">{{ stats.totalReports }}</div>
                    <div class="stat-label">总举报数</div>
                  </div>
                </div>
              </el-card>
            </el-col>
            <el-col :span="6">
              <el-card shadow="hover" class="stat-card">
                <div class="stat-content">
                  <div class="stat-icon" style="background: #e6a23c">
                    <el-icon size="32"><Clock /></el-icon>
                  </div>
                  <div class="stat-info">
                    <div class="stat-value">{{ stats.pendingReports }}</div>
                    <div class="stat-label">待审核</div>
                  </div>
                </div>
              </el-card>
            </el-col>
            <el-col :span="6">
              <el-card shadow="hover" class="stat-card">
                <div class="stat-content">
                  <div class="stat-icon" style="background: #67c23a">
                    <el-icon size="32"><Select /></el-icon>
                  </div>
                  <div class="stat-info">
                    <div class="stat-value">{{ stats.approvedReports }}</div>
                    <div class="stat-label">已通过</div>
                  </div>
                </div>
              </el-card>
            </el-col>
            <el-col :span="6">
              <el-card shadow="hover" class="stat-card">
                <div class="stat-content">
                  <div class="stat-icon" style="background: #f56c6c">
                    <el-icon size="32"><CloseBold /></el-icon>
                  </div>
                  <div class="stat-info">
                    <div class="stat-value">{{ stats.rejectedReports }}</div>
                    <div class="stat-label">已拒绝</div>
                  </div>
                </div>
              </el-card>
            </el-col>
          </el-row>

          <el-card style="margin-top: 20px">
            <template #header>
              <div class="card-header">
                <span>快捷操作</span>
              </div>
            </template>
            <div class="quick-actions">
              <el-button type="primary" size="large" @click="$router.push('/admin/audit')">
                <el-icon><DocumentChecked /></el-icon>
                处理待审核举报 ({{ stats.pendingReports }})
              </el-button>
            </div>
          </el-card>

          <el-card style="margin-top: 20px">
            <template #header>
              <span>最近举报</span>
            </template>
            <el-table :data="recentReports" v-loading="loading">
              <el-table-column prop="reportNo" label="举报编号" width="150" />
              <el-table-column prop="plateNumber" label="车牌号" width="120" />
              <el-table-column prop="location" label="位置" show-overflow-tooltip />
              <el-table-column prop="status" label="状态" width="100">
                <template #default="{ row }">
                  <el-tag v-if="row.status === 'PENDING'" type="warning">待审核</el-tag>
                  <el-tag v-else-if="row.status === 'APPROVED'" type="success">已通过</el-tag>
                  <el-tag v-else type="danger">已拒绝</el-tag>
                </template>
              </el-table-column>
              <el-table-column prop="createdAt" label="时间" width="180">
                <template #default="{ row }">
                  {{ formatTime(row.createdAt) }}
                </template>
              </el-table-column>
            </el-table>
          </el-card>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { DataAnalysis, DocumentChecked, Document, Clock, Select, CloseBold } from '@element-plus/icons-vue'
import { useUserStore } from '@/stores/user'
import { getPendingReports } from '@/api/report'

const router = useRouter()
const userStore = useUserStore()
const activeMenu = ref('/admin/dashboard')
const loading = ref(false)

const stats = ref({
  totalReports: 0,
  pendingReports: 0,
  approvedReports: 0,
  rejectedReports: 0
})

const recentReports = ref([])

const loadStats = async () => {
  loading.value = true
  try {
    const res = await getPendingReports()
    const reports = res.data || []
    stats.value.pendingReports = reports.filter(r => r.status === 'PENDING').length
    stats.value.totalReports = reports.length
    recentReports.value = reports.slice(0, 5)
  } catch (error) {
    console.error('加载统计数据失败', error)
  } finally {
    loading.value = false
  }
}

const formatTime = (time) => {
  if (!time) return '-'
  return new Date(time).toLocaleString('zh-CN')
}

const handleLogout = () => {
  userStore.logout()
  router.push('/admin/login')
}

onMounted(() => {
  loadStats()
})
</script>

<style scoped>
.dashboard-container {
  min-height: 100vh;
  background: #f0f2f5;
}

.el-header {
  background: white;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100%;
}

.user-info {
  display: flex;
  align-items: center;
}

.el-aside {
  background: white;
  box-shadow: 2px 0 8px rgba(0,0,0,0.1);
}

.el-menu {
  border: none;
}

.stat-card {
  cursor: pointer;
}

.stat-content {
  display: flex;
  align-items: center;
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  margin-right: 15px;
}

.stat-value {
  font-size: 24px;
  font-weight: bold;
  color: #303133;
}

.stat-label {
  color: #909399;
  font-size: 14px;
  margin-top: 5px;
}

.quick-actions {
  display: flex;
  gap: 20px;
}
</style>
