<template>
  <el-container class="layout-container">
    <el-aside width="200px">
      <div class="logo">购票系统</div>
      <el-menu :default-active="$route.path" :router="true">
        <el-menu-item index="/dashboard">
          <el-icon><DataAnalysis /></el-icon>
          <span>数据统计</span>
        </el-menu-item>
        <el-sub-menu index="user">
          <template #title>
            <el-icon><User /></el-icon>
            <span>用户管理</span>
          </template>
          <el-menu-item index="/user/profile">个人中心</el-menu-item>
          <el-menu-item index="/user/list" v-if="isAdmin">用户列表</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="movie">
          <template #title>
            <el-icon><Film /></el-icon>
            <span>电影管理</span>
          </template>
          <el-menu-item index="/movie/list">电影列表</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="cinema" v-if="isAdmin">
          <template #title>
            <el-icon><OfficeBuilding /></el-icon>
            <span>影院管理</span>
          </template>
          <el-menu-item index="/cinema/list">影院列表</el-menu-item>
          <el-menu-item index="/hall/list">影厅列表</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/showtime/list">
          <el-icon><Calendar /></el-icon>
          <span>场次管理</span>
        </el-menu-item>
        <el-sub-menu index="order">
          <template #title>
            <el-icon><Tickets /></el-icon>
            <span>订单管理</span>
          </template>
          <el-menu-item index="/order/my">我的订单</el-menu-item>
          <el-menu-item index="/order/list" v-if="isAdmin">所有订单</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/ticket/my">
          <el-icon><Ticket /></el-icon>
          <span>我的票券</span>
        </el-menu-item>
        <el-sub-menu index="coupon">
          <template #title>
            <el-icon><Present /></el-icon>
            <span>优惠券</span>
          </template>
          <el-menu-item index="/coupon/my">我的优惠券</el-menu-item>
          <el-menu-item index="/coupon/list" v-if="isAdmin">优惠券管理</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/comment/list" v-if="isAdmin">
          <el-icon><ChatDotRound /></el-icon>
          <span>评论管理</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div class="header-right">
          <span>{{ userStore.userInfo.nickname || userStore.userInfo.username }}</span>
          <el-dropdown @command="handleCommand">
            <el-avatar :size="40">{{ userStore.userInfo.username?.[0] }}</el-avatar>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="profile">个人中心</el-dropdown-item>
                <el-dropdown-item command="logout" divided>退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useUserStore } from '../store/user'

const router = useRouter()
const userStore = useUserStore()

const isAdmin = computed(() => userStore.userInfo.role === 'admin')

const handleCommand = (command) => {
  if (command === 'logout') {
    userStore.logout()
    ElMessage.success('已退出登录')
    router.push('/login')
  } else if (command === 'profile') {
    router.push('/user/profile')
  }
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
}

.el-aside {
  background-color: #001529;
  color: #fff;
}

.logo {
  height: 60px;
  line-height: 60px;
  text-align: center;
  font-size: 20px;
  font-weight: bold;
  color: #fff;
  background-color: #002140;
}

.el-menu {
  border: none;
  background-color: #001529;
}

.el-header {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  background-color: #fff;
  border-bottom: 1px solid #f0f0f0;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 15px;
}

.el-main {
  background-color: #f0f2f5;
  padding: 20px;
}
</style>
