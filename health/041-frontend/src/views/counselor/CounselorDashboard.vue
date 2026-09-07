<template>
  <div class="dashboard-container">
    <el-container>
      <el-header>
        <div class="header-content">
          <h2>咨询师工作台</h2>
          <div>
            <span>{{ userStore.userInfo.nickname }}</span>
            <el-button @click="handleLogout" size="small" style="margin-left: 20px">退出</el-button>
          </div>
        </div>
      </el-header>
      <el-main>
        <el-card>
          <h3>工作台</h3>
          <p>欢迎使用咨询师工作台</p>
          <p>您可以在这里管理您的预约、时段等信息</p>
        </el-card>
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
  router.push('/counselor-login')
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
</style>
