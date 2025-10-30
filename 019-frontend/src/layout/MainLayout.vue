<template>
  <el-container class="main-container">
    <el-aside width="200px" class="sidebar">
      <div class="logo">
        <el-icon :size="30"><Bicycle /></el-icon>
        <span>运动管理</span>
      </div>
      <el-menu
        :default-active="activeMenu"
        router
        background-color="#001529"
        text-color="#fff"
        active-text-color="#1890ff"
      >
        <el-menu-item index="/home">
          <el-icon><HomeFilled /></el-icon>
          <span>首页</span>
        </el-menu-item>
        
        <el-sub-menu index="sport">
          <template #title>
            <el-icon><Trophy /></el-icon>
            <span>运动打卡</span>
          </template>
          <el-menu-item index="/sport/create">创建打卡</el-menu-item>
          <el-menu-item index="/sport/record">我的记录</el-menu-item>
        </el-sub-menu>
        
        <el-sub-menu index="plan">
          <template #title>
            <el-icon><Document /></el-icon>
            <span>健身计划</span>
          </template>
          <el-menu-item index="/plan/create">创建计划</el-menu-item>
          <el-menu-item index="/plan/list">我的计划</el-menu-item>
        </el-sub-menu>
        
        <el-sub-menu index="activity">
          <template #title>
            <el-icon><User /></el-icon>
            <span>约球活动</span>
          </template>
          <el-menu-item index="/activity/create">发起活动</el-menu-item>
          <el-menu-item index="/activity/list">活动列表</el-menu-item>
        </el-sub-menu>
        
        <el-menu-item index="/health/profile">
          <el-icon><DataLine /></el-icon>
          <span>健康档案</span>
        </el-menu-item>
        
        <el-sub-menu index="venue">
          <template #title>
            <el-icon><School /></el-icon>
            <span>场馆预约</span>
          </template>
          <el-menu-item index="/venue/list">场馆列表</el-menu-item>
          <el-menu-item index="/venue/booking">我的预约</el-menu-item>
        </el-sub-menu>
        
        <el-menu-item index="/rank">
          <el-icon><TrophyBase /></el-icon>
          <span>排行榜</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    
    <el-container>
      <el-header class="header">
        <div class="header-left">
          <h2>{{ pageTitle }}</h2>
        </div>
        <div class="header-right">
          <el-dropdown @command="handleCommand">
            <span class="user-info">
              <el-avatar :size="35" :src="userStore.userInfo.avatar">
                {{ userStore.userInfo.nickname?.[0] }}
              </el-avatar>
              <span class="username">{{ userStore.userInfo.nickname }}</span>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="profile">个人中心</el-dropdown-item>
                <el-dropdown-item command="logout" divided>退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      
      <el-main class="main-content">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { ElMessage } from 'element-plus'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const activeMenu = computed(() => route.path)
const pageTitle = computed(() => route.meta.title || '首页')

const handleCommand = (command) => {
  if (command === 'logout') {
    ElMessage.success('退出成功')
    userStore.logout()
    router.push('/login')
  } else if (command === 'profile') {
    router.push('/profile')
  }
}
</script>

<style scoped>
.main-container {
  height: 100vh;
}

.sidebar {
  background-color: #001529;
  overflow-x: hidden;
}

.logo {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 60px;
  color: #fff;
  font-size: 18px;
  font-weight: bold;
  gap: 10px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #fff;
  border-bottom: 1px solid #f0f0f0;
  padding: 0 20px;
}

.header-left h2 {
  margin: 0;
  font-size: 20px;
  color: #333;
}

.header-right {
  display: flex;
  align-items: center;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 10px;
  cursor: pointer;
}

.username {
  font-size: 14px;
  color: #333;
}

.main-content {
  background-color: #f0f2f5;
  padding: 20px;
  overflow-y: auto;
}
</style>

