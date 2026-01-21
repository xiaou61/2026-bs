<template>
  <el-container style="height:100vh">
    <el-aside width="200px" style="background:#8B4513">
      <div style="color:#fff;text-align:center;padding:20px;font-size:16px;font-weight:bold">博物馆管理系统</div>
      <el-menu :default-active="route.path" router background-color="#8B4513" text-color="#fff" active-text-color="#ffd04b">
        <el-menu-item index="/admin/dashboard"><el-icon><DataAnalysis /></el-icon>数据统计</el-menu-item>
        <el-menu-item index="/admin/user"><el-icon><User /></el-icon>用户管理</el-menu-item>
        <el-menu-item index="/admin/relic"><el-icon><Present /></el-icon>文物管理</el-menu-item>
        <el-menu-item index="/admin/category"><el-icon><Files /></el-icon>分类管理</el-menu-item>
        <el-menu-item index="/admin/dynasty"><el-icon><Calendar /></el-icon>朝代管理</el-menu-item>
        <el-menu-item index="/admin/hall"><el-icon><OfficeBuilding /></el-icon>展厅管理</el-menu-item>
        <el-menu-item index="/admin/exhibition"><el-icon><Picture /></el-icon>展览管理</el-menu-item>
        <el-menu-item index="/admin/research"><el-icon><Document /></el-icon>研究成果</el-menu-item>
        <el-menu-item index="/admin/reservation"><el-icon><Tickets /></el-icon>预约管理</el-menu-item>
        <el-menu-item index="/admin/notice"><el-icon><Bell /></el-icon>公告管理</el-menu-item>
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
import { DataAnalysis, User, Present, Files, Calendar, OfficeBuilding, Picture, Document, Tickets, Bell } from '@element-plus/icons-vue'
const route = useRoute()
const router = useRouter()
const userStore = useUserStore()
const handleLogout = () => { userStore.logout(); router.push('/login') }
</script>
