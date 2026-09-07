<template>
  <div class="layout">
    <el-container>
      <el-aside width="240px" class="aside">
        <div class="logo">铁路订票平台 089</div>
        <el-menu :default-active="route.path" router>
          <el-menu-item index="/dashboard">首页概览</el-menu-item>
          <el-menu-item v-if="userStore.isAdmin" index="/user">用户管理</el-menu-item>
          <el-menu-item v-if="!userStore.isUser" index="/train">列车管理</el-menu-item>
          <el-menu-item v-if="userStore.isAdmin" index="/station">车站管理</el-menu-item>
          <el-menu-item v-if="userStore.isAdmin" index="/carriage">车厢模板</el-menu-item>
          <el-menu-item index="/schedule">班次中心</el-menu-item>
          <el-menu-item v-if="userStore.isUser" index="/seat">余票选座</el-menu-item>
          <el-menu-item v-if="!userStore.isUser" index="/order">订单管理</el-menu-item>
          <el-menu-item v-if="userStore.isUser" index="/my-order">我的订单</el-menu-item>
          <el-menu-item v-if="!userStore.isUser" index="/payment">支付记录</el-menu-item>
          <el-menu-item v-if="!userStore.isUser" index="/ticket">电子票核验</el-menu-item>
          <el-menu-item v-if="userStore.isUser" index="/my-ticket">我的车票</el-menu-item>
          <el-menu-item v-if="userStore.isUser" index="/passenger">常用乘车人</el-menu-item>
          <el-menu-item index="/after-sale">退改签中心</el-menu-item>
          <el-menu-item index="/notice">公告中心</el-menu-item>
          <el-menu-item v-if="!userStore.isUser" index="/statistics">统计看板</el-menu-item>
          <el-menu-item index="/profile">个人中心</el-menu-item>
        </el-menu>
      </el-aside>
      <el-container>
        <el-header class="header">
          <div>当前角色：{{ roleText }}</div>
          <div class="right">
            <span>{{ userStore.user?.nickname || userStore.user?.username }}</span>
            <el-button type="danger" link @click="handleLogout">退出</el-button>
          </div>
        </el-header>
        <el-main>
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { logout } from '../api'
import { useUserStore } from '../store/user'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const roleText = computed(() => {
  if (userStore.isAdmin) return '管理员'
  if (userStore.isDispatcher) return '调度员'
  return '乘客'
})

const handleLogout = async () => {
  try {
    await logout()
  } catch (error) {
  }
  userStore.clearLogin()
  router.push('/login')
}
</script>

<style scoped>
.layout {
  min-height: 100vh;
}

.aside {
  background: #102136;
  color: #fff;
}

.logo {
  height: 56px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 18px;
  font-weight: 600;
  border-bottom: 1px solid rgba(255, 255, 255, 0.12);
}

:deep(.el-menu) {
  border-right: none;
  background: #102136;
}

:deep(.el-menu-item) {
  color: #c7d7e7;
}

:deep(.el-menu-item.is-active) {
  background: #1f3f67;
  color: #fff;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #fff;
  border-bottom: 1px solid #ebeef5;
}

.right {
  display: flex;
  align-items: center;
  gap: 10px;
}
</style>
