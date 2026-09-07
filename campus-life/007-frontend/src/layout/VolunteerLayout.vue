<template>
  <el-container class="layout-container">
    <el-header class="header">
      <div class="logo">志愿活动平台</div>
      <el-menu mode="horizontal" :default-active="activeMenu" router class="header-menu">
        <el-menu-item index="/volunteer/activities">活动大厅</el-menu-item>
        <el-menu-item index="/volunteer/my-activities">我的活动</el-menu-item>
        <el-menu-item index="/volunteer/my-points">我的积分</el-menu-item>
        <el-menu-item index="/volunteer/rewards">积分商城</el-menu-item>
        <el-menu-item index="/volunteer/my-exchanges">我的兑换</el-menu-item>
        <el-menu-item index="/volunteer/ranking">排行榜</el-menu-item>
      </el-menu>
      <el-dropdown @command="handleCommand">
        <span class="user-info">
          <el-avatar :size="32" icon="User" />
          <span style="margin-left: 8px">{{ userStore.userInfo.name }}</span>
          <el-icon><ArrowDown /></el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="profile">个人中心</el-dropdown-item>
            <el-dropdown-item command="logout" divided>退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </el-header>
    <el-main class="main-content">
      <router-view />
    </el-main>
  </el-container>
</template>

<script setup>
import { computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessageBox } from 'element-plus'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

const activeMenu = computed(() => route.path)

const handleCommand = (command) => {
  if (command === 'logout') {
    ElMessageBox.confirm('确定要退出登录吗?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(() => {
      userStore.logout()
      router.push('/login')
    })
  } else if (command === 'profile') {
    router.push('/volunteer/profile')
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
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  padding: 0 20px;
}

.logo {
  font-size: 20px;
  font-weight: bold;
  color: #409eff;
  margin-right: 40px;
  white-space: nowrap;
}

.header-menu {
  flex: 1;
  border: none;
}

.user-info {
  display: flex;
  align-items: center;
  cursor: pointer;
  margin-left: 20px;
}

.main-content {
  background: #f5f7fa;
  padding: 20px;
}
</style>

