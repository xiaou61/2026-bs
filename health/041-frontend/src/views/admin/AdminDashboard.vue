<template>
  <div class="dashboard-container">
    <el-container>
      <el-header>
        <div class="header-content">
          <h2>管理后台</h2>
          <div>
            <span>管理员：{{ userStore.userInfo.username }}</span>
            <el-button @click="handleLogout" size="small" style="margin-left: 20px">退出</el-button>
          </div>
        </div>
      </el-header>
      <el-main>
        <el-row :gutter="20">
          <el-col :span="6">
            <el-card>
              <h3>用户管理</h3>
              <p>管理系统用户</p>
            </el-card>
          </el-col>
          <el-col :span="6">
            <el-card>
              <h3>咨询师管理</h3>
              <p>审核咨询师资质</p>
            </el-card>
          </el-col>
          <el-col :span="6">
            <el-card>
              <h3>量表管理</h3>
              <p>管理测评量表</p>
            </el-card>
          </el-col>
          <el-col :span="6">
            <el-card>
              <h3>文章管理</h3>
              <p>管理心理文章</p>
            </el-card>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()

const handleLogout = () => {
  userStore.logout()
  router.push('/admin/login')
}
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

.el-card {
  text-align: center;
  cursor: pointer;
}

.el-card:hover {
  box-shadow: 0 4px 12px rgba(0,0,0,0.15);
}

.el-card h3 {
  margin-bottom: 10px;
}

.el-card p {
  color: #666;
  font-size: 14px;
}
</style>
