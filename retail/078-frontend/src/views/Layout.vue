<template>
  <el-container class="layout-container">
    <el-aside width="220px" class="aside">
      <div class="logo">团购系统</div>
      <el-menu :default-active="route.path" router background-color="#304156" text-color="#bfcbd9" active-text-color="#409EFF">
        <template v-if="user?.role === 0">
          <el-menu-item index="/dashboard"><el-icon><DataLine /></el-icon>数据概览</el-menu-item>
          <el-menu-item index="/user"><el-icon><User /></el-icon>用户管理</el-menu-item>
          <el-menu-item index="/merchant"><el-icon><Shop /></el-icon>商家管理</el-menu-item>
          <el-menu-item index="/category"><el-icon><Grid /></el-icon>分类管理</el-menu-item>
          <el-menu-item index="/notice"><el-icon><Bell /></el-icon>公告管理</el-menu-item>
          <el-menu-item index="/review"><el-icon><ChatLineSquare /></el-icon>评价审核</el-menu-item>
        </template>
        <template v-else-if="user?.role === 1">
          <el-menu-item index="/dashboard"><el-icon><DataLine /></el-icon>数据概览</el-menu-item>
          <el-menu-item index="/merchant/info"><el-icon><Shop /></el-icon>店铺信息</el-menu-item>
          <el-menu-item index="/product"><el-icon><Goods /></el-icon>商品管理</el-menu-item>
          <el-menu-item index="/activity"><el-icon><Timer /></el-icon>团购活动</el-menu-item>
          <el-menu-item index="/order"><el-icon><List /></el-icon>订单管理</el-menu-item>
        </template>
        <template v-else>
          <el-menu-item index="/home"><el-icon><HomeFilled /></el-icon>团购首页</el-menu-item>
          <el-menu-item index="/cart"><el-icon><ShoppingCart /></el-icon>购物车</el-menu-item>
          <el-menu-item index="/order"><el-icon><List /></el-icon>我的订单</el-menu-item>
          <el-menu-item index="/group"><el-icon><Connection /></el-icon>我的拼团</el-menu-item>
          <el-menu-item index="/address"><el-icon><Location /></el-icon>收货地址</el-menu-item>
          <el-menu-item index="/profile"><el-icon><User /></el-icon>个人中心</el-menu-item>
          <el-menu-item index="/merchant/apply"><el-icon><Plus /></el-icon>商家入驻</el-menu-item>
        </template>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <div class="header-left"></div>
        <div class="header-right">
          <el-dropdown @command="handleCommand">
            <span class="user-info">
              <el-avatar :size="32">{{ user?.nickname?.charAt(0) }}</el-avatar>
              <span class="username">{{ user?.nickname }}</span>
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
      <el-main class="main">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '../store/user'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()
const user = computed(() => userStore.user)

const handleCommand = (command) => {
  if (command === 'logout') {
    userStore.logout()
    router.push('/login')
  } else if (command === 'profile') {
    router.push('/profile')
  }
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
}
.aside {
  background: #304156;
}
.logo {
  height: 60px;
  line-height: 60px;
  text-align: center;
  color: #fff;
  font-size: 20px;
  font-weight: bold;
  background: #263445;
}
.header {
  background: #fff;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 1px 4px rgba(0,21,41,0.08);
}
.user-info {
  display: flex;
  align-items: center;
  cursor: pointer;
}
.username {
  margin-left: 10px;
  color: #333;
}
.main {
  background: #f0f2f5;
}
</style>
