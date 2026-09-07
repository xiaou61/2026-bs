<template>
  <el-container class="layout-container">
    <el-header class="header">
      <div class="logo">校园招聘平台 - 企业端</div>
      <el-menu
        mode="horizontal"
        :default-active="activeMenu"
        router
        class="header-menu"
      >
        <el-menu-item index="/company/jobs">岗位管理</el-menu-item>
        <el-menu-item index="/company/applications">简历管理</el-menu-item>
        <el-menu-item index="/company/interviews">面试管理</el-menu-item>
        <el-menu-item index="/company/company-info">企业信息</el-menu-item>
      </el-menu>
      <div class="user-info">
        <el-dropdown @command="handleCommand">
          <span class="user-name">
            {{ userStore.user?.realName || userStore.user?.username }}
            <el-icon><ArrowDown /></el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="profile">个人信息</el-dropdown-item>
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
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { ArrowDown } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const activeMenu = computed(() => {
  return route.path
})

const handleCommand = (command) => {
  if (command === 'logout') {
    userStore.logout()
    router.push('/login')
  } else if (command === 'profile') {
    router.push('/company/profile')
  }
}
</script>

<style scoped>
.layout-container {
  min-height: 100vh;
}

.header {
  display: flex;
  align-items: center;
  background: #fff;
  border-bottom: 1px solid #e8e8e8;
  padding: 0 20px;
}

.logo {
  font-size: 20px;
  font-weight: bold;
  color: #67c23a;
  margin-right: 40px;
  white-space: nowrap;
}

.header-menu {
  flex: 1;
  border: none;
}

.user-info {
  margin-left: 20px;
}

.user-name {
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 5px;
}

.main-content {
  background: #f5f5f5;
  padding: 20px;
}
</style>

