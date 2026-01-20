<template>
  <el-container class="layout-container">
    <el-aside width="200px">
      <div class="logo">垃圾回收管理</div>
      <el-menu :default-active="route.path" router background-color="#304156" text-color="#bfcbd9" active-text-color="#409eff">
        <el-menu-item index="/admin/dashboard"><el-icon><DataAnalysis /></el-icon>数据统计</el-menu-item>
        <el-menu-item index="/admin/order"><el-icon><Document /></el-icon>订单管理</el-menu-item>
        <el-menu-item index="/admin/user"><el-icon><User /></el-icon>用户管理</el-menu-item>
        <el-menu-item index="/admin/community"><el-icon><House /></el-icon>小区管理</el-menu-item>
        <el-menu-item index="/admin/category"><el-icon><Grid /></el-icon>分类管理</el-menu-item>
        <el-menu-item index="/admin/product"><el-icon><ShoppingBag /></el-icon>商品管理</el-menu-item>
        <el-menu-item index="/admin/exchange"><el-icon><Ticket /></el-icon>兑换管理</el-menu-item>
        <el-menu-item index="/admin/knowledge"><el-icon><Reading /></el-icon>知识管理</el-menu-item>
        <el-menu-item index="/admin/notice"><el-icon><Bell /></el-icon>公告管理</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <span>欢迎您，{{ userStore.userInfo.realName }}</span>
        <el-button type="text" @click="userStore.logout">退出登录</el-button>
      </el-header>
      <el-main><router-view /></el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import { useRoute } from 'vue-router'
import { useUserStore } from '@/stores/user'

const route = useRoute()
const userStore = useUserStore()
</script>

<style scoped>
.layout-container { height: 100vh; }
.el-aside { background-color: #304156; }
.logo { height: 60px; line-height: 60px; text-align: center; color: #fff; font-size: 18px; font-weight: bold; }
.el-header { display: flex; justify-content: space-between; align-items: center; background: #fff; box-shadow: 0 1px 4px rgba(0,21,41,.08); }
.el-main { background: #f0f2f5; }
</style>
