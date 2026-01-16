<template>
  <div class="home-container">
    <el-container>
      <el-header>
        <div class="header-content">
          <h2>社区车辆违停处理系统</h2>
          <div class="user-info">
            <span>欢迎，{{ userStore.userInfo.realName || userStore.userInfo.username }}</span>
            <el-button @click="handleLogout" size="small" style="margin-left: 20px">退出</el-button>
          </div>
        </div>
      </el-header>

      <el-container>
        <el-aside width="200px">
          <el-menu :default-active="activeMenu" router>
            <el-menu-item index="/home">
              <el-icon><HomeFilled /></el-icon>
              <span>首页</span>
            </el-menu-item>
            <el-menu-item index="/report/submit">
              <el-icon><Edit /></el-icon>
              <span>提交举报</span>
            </el-menu-item>
            <el-menu-item index="/reports/my">
              <el-icon><List /></el-icon>
              <span>我的举报</span>
            </el-menu-item>
          </el-menu>
        </el-aside>

        <el-main>
          <el-row :gutter="20">
            <el-col :span="8">
              <el-card shadow="hover" class="stat-card">
                <div class="stat-content">
                  <div class="stat-icon" style="background: #409eff">
                    <el-icon size="40"><DocumentChecked /></el-icon>
                  </div>
                  <div class="stat-info">
                    <div class="stat-value">{{ stats.totalReports }}</div>
                    <div class="stat-label">我的举报总数</div>
                  </div>
                </div>
              </el-card>
            </el-col>
            <el-col :span="8">
              <el-card shadow="hover" class="stat-card">
                <div class="stat-content">
                  <div class="stat-icon" style="background: #67c23a">
                    <el-icon size="40"><SuccessFilled /></el-icon>
                  </div>
                  <div class="stat-info">
                    <div class="stat-value">{{ stats.approvedReports }}</div>
                    <div class="stat-label">审核通过</div>
                  </div>
                </div>
              </el-card>
            </el-col>
            <el-col :span="8">
              <el-card shadow="hover" class="stat-card">
                <div class="stat-content">
                  <div class="stat-icon" style="background: #e6a23c">
                    <el-icon size="40"><Clock /></el-icon>
                  </div>
                  <div class="stat-info">
                    <div class="stat-value">{{ stats.pendingReports }}</div>
                    <div class="stat-label">待审核</div>
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
              <el-button type="primary" size="large" @click="$router.push('/report/submit')">
                <el-icon><Edit /></el-icon>
                提交违停举报
              </el-button>
              <el-button size="large" @click="$router.push('/reports/my')">
                <el-icon><List /></el-icon>
                查看我的举报
              </el-button>
            </div>
          </el-card>

          <el-card style="margin-top: 20px">
            <template #header>
              <span>举报说明</span>
            </template>
            <el-steps :active="4" align-center>
              <el-step title="拍照取证" description="拍摄违停车辆照片" />
              <el-step title="填写信息" description="填写车牌号和违停类型" />
              <el-step title="提交举报" description="提交举报等待审核" />
              <el-step title="处理完成" description="管理员审核处理" />
            </el-steps>
          </el-card>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { HomeFilled, Edit, List, DocumentChecked, SuccessFilled, Clock } from '@element-plus/icons-vue'
import { useUserStore } from '@/stores/user'
import { getMyReports } from '@/api/report'

const router = useRouter()
const userStore = useUserStore()
const activeMenu = ref('/home')

const stats = ref({
  totalReports: 0,
  approvedReports: 0,
  pendingReports: 0
})

const loadStats = async () => {
  try {
    const res = await getMyReports()
    const reports = res.data || []
    stats.value.totalReports = reports.length
    stats.value.approvedReports = reports.filter(r => r.status === 'APPROVED').length
    stats.value.pendingReports = reports.filter(r => r.status === 'PENDING').length
  } catch (error) {
    console.error('加载统计数据失败', error)
  }
}

const handleLogout = () => {
  userStore.logout()
  router.push('/login')
}

onMounted(() => {
  loadStats()
})
</script>

<style scoped>
.home-container {
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
  transition: transform 0.3s;
}

.stat-card:hover {
  transform: translateY(-5px);
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
  margin-right: 20px;
}

.stat-value {
  font-size: 28px;
  font-weight: bold;
  color: #303133;
}

.stat-label {
  color: #909399;
  margin-top: 5px;
}

.quick-actions {
  display: flex;
  gap: 20px;
}

.el-steps {
  margin: 20px 0;
}
</style>
