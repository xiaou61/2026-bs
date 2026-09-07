<template>
  <div class="host-layout">
    <el-container>
      <el-aside width="200px" class="aside">
        <div class="logo">
          <el-icon><House /></el-icon>
          <span>房东中心</span>
        </div>
        <el-menu :default-active="route.path" router>
          <el-menu-item index="/host/homestay">
            <el-icon><HomeFilled /></el-icon>
            <span>民宿管理</span>
          </el-menu-item>
          <el-menu-item index="/host/booking">
            <el-icon><List /></el-icon>
            <span>订单管理</span>
          </el-menu-item>
          <el-menu-item index="/">
            <el-icon><Back /></el-icon>
            <span>返回首页</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-container>
        <el-header class="header">
          <span>欢迎回来，{{ userStore.userInfo?.nickname }}</span>
          <el-button type="text" @click="handleLogout">退出登录</el-button>
        </el-header>
        <el-main class="main">
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const handleLogout = () => {
  userStore.logout()
  router.push('/')
}
</script>

<style scoped lang="scss">
.host-layout {
  min-height: 100vh;
}
.aside {
  background: #304156;
  .logo {
    height: 60px;
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 8px;
    color: #fff;
    font-size: 18px;
    font-weight: bold;
  }
  .el-menu {
    border-right: none;
    background: #304156;
    .el-menu-item {
      color: #bfcbd9;
      &:hover, &.is-active {
        background: #263445;
        color: #409eff;
      }
    }
  }
}
.header {
  background: #fff;
  display: flex;
  align-items: center;
  justify-content: space-between;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1);
}
.main {
  background: #f5f5f5;
  padding: 20px;
}
</style>
