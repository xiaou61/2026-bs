<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">运动康复 199</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>运动康复训练计划与体测评估管理系统</strong><span>体测评估、风险提醒、训练计划与康复反馈协同</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
  </el-container>
</template>
<script setup>
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { logout } from '../api'
import { useUserStore } from '../store/user'
const router = useRouter()
const userStore = useUserStore()
const menus = [
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'CENTER', 'ASSESSOR', 'COACH', 'THERAPIST', 'MEMBER'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/center', label: '康复中心', roles: ['ADMIN', 'CENTER'] },
  { index: '/member', label: '会员档案', roles: ['ADMIN', 'CENTER', 'ASSESSOR', 'COACH', 'THERAPIST', 'MEMBER'] },
  { index: '/coach', label: '教练档案', roles: ['ADMIN', 'CENTER', 'COACH'] },
  { index: '/item', label: '体测项目', roles: ['ADMIN', 'CENTER', 'ASSESSOR', 'THERAPIST'] },
  { index: '/assessment', label: '体测评估', roles: ['ADMIN', 'CENTER', 'ASSESSOR', 'THERAPIST', 'MEMBER'] },
  { index: '/risk', label: '风险提醒', roles: ['ADMIN', 'CENTER', 'ASSESSOR', 'THERAPIST', 'COACH'] },
  { index: '/plan', label: '训练计划', roles: ['ADMIN', 'CENTER', 'COACH', 'THERAPIST', 'MEMBER'] },
  { index: '/session', label: '训练安排', roles: ['ADMIN', 'CENTER', 'COACH', 'THERAPIST', 'MEMBER'] },
  { index: '/checkin', label: '训练打卡', roles: ['ADMIN', 'CENTER', 'COACH', 'MEMBER'] },
  { index: '/feedback', label: '康复反馈', roles: ['ADMIN', 'CENTER', 'THERAPIST', 'COACH', 'MEMBER'] },
  { index: '/reassessment', label: '复评记录', roles: ['ADMIN', 'CENTER', 'ASSESSOR', 'THERAPIST', 'MEMBER'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
