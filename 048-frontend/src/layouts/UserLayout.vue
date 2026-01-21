<template>
  <el-container style="height:100vh">
    <el-aside width="200px" style="background:#CD853F">
      <div style="color:#fff;text-align:center;padding:20px;font-size:16px;font-weight:bold">博物馆参观</div>
      <el-menu :default-active="route.path" router background-color="#CD853F" text-color="#fff" active-text-color="#ffd04b">
        <el-menu-item index="/user/relic"><el-icon><Present /></el-icon>文物展览</el-menu-item>
        <el-menu-item index="/user/exhibition"><el-icon><Picture /></el-icon>展览活动</el-menu-item>
        <el-menu-item index="/user/research"><el-icon><Document /></el-icon>研究成果</el-menu-item>
        <el-menu-item index="/user/guide-booking"><el-icon><Service /></el-icon>预约讲解</el-menu-item>
        <el-menu-item index="/user/reservation"><el-icon><Tickets /></el-icon>我的预约</el-menu-item>
        <el-menu-item index="/user/favorite"><el-icon><Star /></el-icon>我的收藏</el-menu-item>
        <el-menu-item index="/user/notice"><el-icon><Bell /></el-icon>系统公告</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header style="display:flex;justify-content:flex-end;align-items:center;background:#fff;box-shadow:0 1px 4px rgba(0,0,0,.1)">
        <span style="margin-right:20px">{{ userStore.username }}</span>
        <el-button @click="handleLogout">退出</el-button>
      </el-header>
      <el-main style="background:#f5f5f5"><router-view /></el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { Present, Picture, Document, Service, Tickets, Star, Bell } from '@element-plus/icons-vue'
const route = useRoute()
const router = useRouter()
const userStore = useUserStore()
const handleLogout = () => { userStore.logout(); router.push('/login') }
</script>
