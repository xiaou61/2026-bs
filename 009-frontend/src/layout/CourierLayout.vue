<template>
  <el-container class="layout-container">
    <el-header class="header">
      <div class="header-left">
        <el-icon :size="24"><Van /></el-icon>
        <span class="title">快递员工作台</span>
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
          <el-menu-item index="/courier/express-in">
            <el-icon><Upload /></el-icon>
            <span>快递入库</span>
          </el-menu-item>
          <el-menu-item index="/courier/batch-import">
            <el-icon><FolderAdd /></el-icon>
            <span>批量导入</span>
          </el-menu-item>
          <el-menu-item index="/courier/pickup">
            <el-icon><Select /></el-icon>
            <span>取件核销</span>
          </el-menu-item>
          <el-menu-item index="/courier/express-list">
            <el-icon><List /></el-icon>
            <span>快递管理</span>
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
import { Van, Upload, Select, List, FolderAdd } from '@element-plus/icons-vue'

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
  background: #67C23A;
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

