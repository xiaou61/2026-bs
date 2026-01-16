<template>
  <div class="audit-container">
    <el-container>
      <el-header>
        <div class="header-content">
          <h2>管理后台 - 举报审核</h2>
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
          <el-card>
            <template #header>
              <div class="card-header">
                <span>待审核举报列表</span>
                <el-button type="primary" size="small" @click="loadReports" :icon="RefreshRight">
                  刷新
                </el-button>
              </div>
            </template>

            <el-table :data="reports" v-loading="loading" border>
              <el-table-column prop="reportNo" label="举报编号" width="150" />
              <el-table-column prop="plateNumber" label="车牌号" width="120" />
              <el-table-column label="违停类型" width="150">
                <template #default="{ row }">
                  {{ row.violationType?.name || '-' }}
                </template>
              </el-table-column>
              <el-table-column prop="location" label="违停位置" show-overflow-tooltip min-width="200" />
              <el-table-column label="举报人" width="120">
                <template #default="{ row }">
                  {{ row.reporter?.username || '-' }}
                </template>
              </el-table-column>
              <el-table-column prop="status" label="状态" width="100">
                <template #default="{ row }">
                  <el-tag v-if="row.status === 'PENDING'" type="warning">待审核</el-tag>
                  <el-tag v-else-if="row.status === 'APPROVED'" type="success">已通过</el-tag>
                  <el-tag v-else type="danger">已拒绝</el-tag>
                </template>
              </el-table-column>
              <el-table-column prop="createdAt" label="提交时间" width="180">
                <template #default="{ row }">
                  {{ formatTime(row.createdAt) }}
                </template>
              </el-table-column>
              <el-table-column label="操作" width="120" fixed="right">
                <template #default="{ row }">
                  <el-button 
                    type="primary" 
                    size="small" 
                    @click="goToDetail(row.id)"
                    v-if="row.status === 'PENDING'"
                  >
                    审核
                  </el-button>
                  <el-button 
                    type="info" 
                    size="small" 
                    @click="goToDetail(row.id)"
                    v-else
                  >
                    查看
                  </el-button>
                </template>
              </el-table-column>
            </el-table>

            <el-empty v-if="!loading && reports.length === 0" description="暂无待审核举报" />
          </el-card>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { DataAnalysis, DocumentChecked, RefreshRight } from '@element-plus/icons-vue'
import { useUserStore } from '@/stores/user'
import { getPendingReports } from '@/api/report'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()
const activeMenu = ref('/admin/audit')
const loading = ref(false)
const reports = ref([])

const loadReports = async () => {
  loading.value = true
  try {
    const res = await getPendingReports()
    reports.value = res.data || []
  } catch (error) {
    console.error('加载举报列表失败', error)
    ElMessage.error('加载举报列表失败')
  } finally {
    loading.value = false
  }
}

const formatTime = (time) => {
  if (!time) return '-'
  return new Date(time).toLocaleString('zh-CN')
}

const goToDetail = (id) => {
  router.push(`/admin/audit/${id}`)
}

const handleLogout = () => {
  userStore.logout()
  router.push('/admin/login')
}

onMounted(() => {
  loadReports()
})
</script>

<style scoped>
.audit-container {
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

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
