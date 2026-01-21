<template>
  <el-container class="layout">
    <el-aside width="200px">
      <div class="logo">作者工作台</div>
      <el-menu :default-active="route.path" router background-color="#1a1a2e" text-color="#bfcbd9" active-text-color="#e94560">
        <el-menu-item index="/author/script"><el-icon><Document /></el-icon>我的剧本</el-menu-item>
        <el-menu-item index="/author/create"><el-icon><Edit /></el-icon>创作剧本</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header><span>作者: {{ userStore.userInfo.realName }}</span><el-button type="danger" link @click="handleLogout">退出</el-button></el-header>
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
.el-aside { background: #1a1a2e; }
.logo { height: 60px; line-height: 60px; text-align: center; color: #e94560; font-size: 18px; font-weight: bold; }
.el-header { display: flex; justify-content: space-between; align-items: center; background: #fff; box-shadow: 0 1px 4px rgba(0,0,0,0.1); }
</style>
