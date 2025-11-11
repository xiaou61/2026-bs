<template>
  <el-container class="layout-container">
    <el-header class="header">
      <div class="header-left">
        <h2>校园学习资源共享平台</h2>
      </div>
      <el-menu
        :default-active="activeMenu"
        class="header-menu"
        mode="horizontal"
        @select="handleMenuSelect"
      >
        <el-menu-item index="/home">首页</el-menu-item>
        <el-menu-item index="/resource">资源库</el-menu-item>
        <el-menu-item index="/group">学习小组</el-menu-item>
        <el-menu-item index="/question">题库</el-menu-item>
        <el-menu-item index="/qa">答疑</el-menu-item>
        <el-menu-item index="/note">笔记</el-menu-item>
      </el-menu>
      <div class="header-right">
        <el-dropdown @command="handleCommand">
          <span class="user-info">
            <el-icon><User /></el-icon>
            {{ userStore.userInfo?.nickname || '用户' }}
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="profile">个人中心</el-dropdown-item>
              <el-dropdown-item command="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </el-header>
    <el-main class="main-content">
      <router-view />
    </el-main>
  </el-container>
</template>

<script setup>
import { computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { ElMessage } from 'element-plus'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

const activeMenu = computed(() => {
  const path = route.path
  if (path.startsWith('/resource')) return '/resource'
  if (path.startsWith('/group')) return '/group'
  if (path.startsWith('/question')) return '/question'
  if (path.startsWith('/qa')) return '/qa'
  if (path.startsWith('/note')) return '/note'
  return path
})

const handleMenuSelect = (index) => {
  router.push(index)
}

const handleCommand = (command) => {
  if (command === 'profile') {
    router.push('/profile')
  } else if (command === 'logout') {
    userStore.logout()
    ElMessage.success('已退出登录')
    router.push('/login')
  }
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
}

.header {
  display: flex;
  align-items: center;
  background: #fff;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  padding: 0 20px;
}

.header-left h2 {
  color: #409EFF;
  margin: 0;
  white-space: nowrap;
}

.header-menu {
  flex: 1;
  margin: 0 40px;
  border: none;
}

.header-right {
  display: flex;
  align-items: center;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
  padding: 8px 16px;
  border-radius: 4px;
  transition: background-color 0.3s;
}

.user-info:hover {
  background-color: #f5f7fa;
}

.main-content {
  padding: 20px;
  background: #f5f7fa;
  overflow-y: auto;
}
</style>

