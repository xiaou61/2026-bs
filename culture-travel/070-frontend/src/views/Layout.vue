<template>
  <div class="layout">
    <el-container>
      <el-aside width="220px" class="aside">
        <div class="logo">购票系统 070</div>
        <el-menu :default-active="route.path" router>
          <el-menu-item index="/dashboard">首页</el-menu-item>
          <el-menu-item v-if="isAdmin" index="/user">用户管理</el-menu-item>
          <el-menu-item index="/movie">影片管理</el-menu-item>
          <el-menu-item index="/cinema">影院管理</el-menu-item>
          <el-menu-item v-if="isAdmin" index="/hall">影厅管理</el-menu-item>
          <el-menu-item index="/showtime">场次管理</el-menu-item>
          <el-menu-item v-if="!isAdmin" index="/seat">选座下单</el-menu-item>
          <el-menu-item v-if="isAdmin" index="/order">订单管理</el-menu-item>
          <el-menu-item v-if="!isAdmin" index="/my-order">我的订单</el-menu-item>
          <el-menu-item v-if="isAdmin" index="/payment">支付记录</el-menu-item>
          <el-menu-item v-if="isAdmin" index="/ticket">票券核销</el-menu-item>
          <el-menu-item v-if="!isAdmin" index="/my-ticket">我的票券</el-menu-item>
          <el-menu-item v-if="isAdmin" index="/coupon">优惠券管理</el-menu-item>
          <el-menu-item v-if="!isAdmin" index="/my-coupon">我的优惠券</el-menu-item>
          <el-menu-item index="/comment">评论管理</el-menu-item>
          <el-menu-item v-if="isAdmin" index="/statistics">统计看板</el-menu-item>
          <el-menu-item index="/profile">个人中心</el-menu-item>
        </el-menu>
      </el-aside>
      <el-container>
        <el-header class="header">
          <div>当前角色：{{ roleText }}</div>
          <div class="right">
            <span>{{ user?.nickname || user?.username }}</span>
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
import { useUserStore } from '../store/user'
import { logout } from '../api'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const user = computed(() => userStore.user)
const isAdmin = computed(() => (user.value?.role || '').toUpperCase() === 'ADMIN')
const roleText = computed(() => (isAdmin.value ? '管理员' : '普通用户'))

const handleLogout = async () => {
  try {
    await logout()
  } catch (e) {
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
  background: #1d2a3a;
  color: #fff;
}

.logo {
  height: 56px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 18px;
  font-weight: 600;
  border-bottom: 1px solid rgba(255, 255, 255, 0.15);
}

:deep(.el-menu) {
  border-right: none;
  background: #1d2a3a;
}

:deep(.el-menu-item) {
  color: #c6d2e0;
}

:deep(.el-menu-item.is-active) {
  background: #2f4259;
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
