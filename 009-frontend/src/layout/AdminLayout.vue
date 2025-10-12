<template>
  <el-container class="layout-container">
    <el-header class="header">
      <div class="header-left">
        <el-icon :size="24"><Setting /></el-icon>
        <span class="title">管理后台</span>
      </div>
      <div class="header-right">
        <el-dropdown @command="handleCommand">
          <span class="user-info">
            <el-avatar :size="32">{{ userStore.userInfo?.realName?.charAt(0) }}</el-avatar>
            <span class="username">{{ userStore.userInfo?.realName }}</span>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </el-header>
    <el-container>
      <el-aside width="200px">
        <el-menu
          :default-active="$route.path"
          router
          class="el-menu-vertical">
          <el-menu-item index="/admin/dashboard">
            <el-icon><DataAnalysis /></el-icon>
            <span>数据统计</span>
          </el-menu-item>
          <el-menu-item index="/admin/pickup">
            <el-icon><Select /></el-icon>
            <span>取件核销</span>
          </el-menu-item>
          <el-menu-item index="/admin/express">
            <el-icon><Box /></el-icon>
            <span>快递管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/station">
            <el-icon><Location /></el-icon>
            <span>代收点管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/company">
            <el-icon><Van /></el-icon>
            <span>快递公司</span>
          </el-menu-item>
          <el-menu-item index="/admin/user">
            <el-icon><User /></el-icon>
            <span>用户管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/batch-import">
            <el-icon><Upload /></el-icon>
            <span>批量导入</span>
          </el-menu-item>
          <el-menu-item index="/admin/operation-log">
            <el-icon><Document /></el-icon>
            <span>操作日志</span>
          </el-menu-item>
          <el-menu-item index="/admin/config">
            <el-icon><Tools /></el-icon>
            <span>系统配置</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { Setting, DataAnalysis, Select, Box, Location, Van, User, Upload, Document, Tools } from '@element-plus/icons-vue'

const router = useRouter()
const userStore = useUserStore()

const handleCommand = (command) => {
  if (command === 'logout') {
    userStore.logout()
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
  justify-content: space-between;
  align-items: center;
  background: #E6A23C;
  color: white;
  padding: 0 20px;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 10px;
}

.title {
  font-size: 20px;
  font-weight: bold;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 20px;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 10px;
  cursor: pointer;
}

.username {
  font-size: 14px;
}

.el-aside {
  background: #f5f7fa;
  border-right: 1px solid #e4e7ed;
}

.el-menu-vertical {
  border-right: none;
}

.el-main {
  background: #fff;
  padding: 20px;
}
</style>

