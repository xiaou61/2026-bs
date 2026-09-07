<template>
  <el-container class="layout">
    <el-aside width="200px">
      <div class="logo">剧本杀后台</div>
      <el-menu :default-active="route.path" router background-color="#304156" text-color="#bfcbd9" active-text-color="#409eff">
        <el-menu-item index="/admin/dashboard"><el-icon><DataLine /></el-icon>数据统计</el-menu-item>
        <el-menu-item index="/admin/user"><el-icon><User /></el-icon>用户管理</el-menu-item>
        <el-menu-item index="/admin/shop"><el-icon><Shop /></el-icon>店铺管理</el-menu-item>
        <el-menu-item index="/admin/script"><el-icon><Document /></el-icon>剧本管理</el-menu-item>
        <el-menu-item index="/admin/category"><el-icon><Grid /></el-icon>分类管理</el-menu-item>
        <el-menu-item index="/admin/notice"><el-icon><Bell /></el-icon>公告管理</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header><span>管理员: {{ userStore.userInfo.realName }}</span><el-button type="danger" link @click="handleLogout">退出</el-button></el-header>
      <el-main><router-view /></el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
const route = useRoute()
const router = useRouter()
const userStore = useUserStore()
const handleLogout = () => { userStore.logout(); router.push('/login') }
</script>

<style scoped>
.layout { min-height: 100vh; }
.el-aside { background: #304156; }
.logo { height: 60px; line-height: 60px; text-align: center; color: #fff; font-size: 18px; font-weight: bold; }
.el-header { display: flex; justify-content: space-between; align-items: center; background: #fff; box-shadow: 0 1px 4px rgba(0,0,0,0.1); }
</style>
