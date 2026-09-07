<template>
  <div class="home-container">
    <el-container>
      <el-header>
        <div class="header-content">
          <h2>在线心理评测与咨询系统</h2>
          <div>
            <span>欢迎，{{ userStore.userInfo.nickname || userStore.userInfo.username }}</span>
            <el-button @click="handleLogout" size="small" style="margin-left: 20px">退出</el-button>
          </div>
        </div>
      </el-header>
      <el-main>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-card shadow="hover" class="menu-card" @click="$router.push('/scales')">
              <div class="menu-icon">📝</div>
              <h3>心理测评</h3>
              <p>专业的心理量表测评</p>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card shadow="hover" class="menu-card" @click="$router.push('/counselors')">
              <div class="menu-icon">👨‍⚕️</div>
              <h3>咨询预约</h3>
              <p>预约专业心理咨询师</p>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card shadow="hover" class="menu-card" @click="$router.push('/articles')">
              <div class="menu-icon">📚</div>
              <h3>心理文章</h3>
              <p>心理健康知识学习</p>
            </el-card>
          </el-col>
        </el-row>

        <el-row :gutter="20" style="margin-top: 20px">
          <el-col :span="12">
            <el-card shadow="hover" class="menu-card" @click="$router.push('/assessments/my')">
              <div class="menu-icon">📊</div>
              <h3>我的测评</h3>
              <p>查看历史测评记录</p>
            </el-card>
          </el-col>
          <el-col :span="12">
            <el-card shadow="hover" class="menu-card" @click="$router.push('/appointments/my')">
              <div class="menu-icon">📅</div>
              <h3>我的预约</h3>
              <p>查看咨询预约记录</p>
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
  router.push('/login')
}
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

.el-main {
  max-width: 1200px;
  margin: 0 auto;
  padding-top: 40px;
}

.menu-card {
  cursor: pointer;
  text-align: center;
  padding: 20px;
  transition: transform 0.3s;
}

.menu-card:hover {
  transform: translateY(-5px);
}

.menu-icon {
  font-size: 48px;
  margin-bottom: 10px;
}

.menu-card h3 {
  margin: 10px 0;
  color: #333;
}

.menu-card p {
  color: #666;
  font-size: 14px;
}
</style>
