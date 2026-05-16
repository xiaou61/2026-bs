<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">文旅场馆 151</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>文旅场馆讲解预约与票务核销管理平台</strong><span>票务预约、讲解排期、扫码核销、客流统计一体化管理</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'MANAGER', 'GUIDE', 'CHECKER', 'VISITOR'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/venue', label: '场馆档案', roles: ['ADMIN', 'MANAGER', 'GUIDE', 'CHECKER', 'VISITOR'] },
  { index: '/ticket', label: '票种产品', roles: ['ADMIN', 'MANAGER', 'GUIDE', 'CHECKER', 'VISITOR'] },
  { index: '/ticket-order', label: '票务预约', roles: ['ADMIN', 'MANAGER', 'GUIDE', 'CHECKER', 'VISITOR'] },
  { index: '/guide', label: '讲解员档案', roles: ['ADMIN', 'MANAGER', 'GUIDE', 'CHECKER', 'VISITOR'] },
  { index: '/schedule', label: '讲解排期', roles: ['ADMIN', 'MANAGER', 'GUIDE', 'CHECKER', 'VISITOR'] },
  { index: '/booking', label: '讲解预约', roles: ['ADMIN', 'MANAGER', 'GUIDE', 'CHECKER', 'VISITOR'] },
  { index: '/verification', label: '扫码核销', roles: ['ADMIN', 'MANAGER', 'GUIDE', 'CHECKER', 'VISITOR'] },
  { index: '/crowd-flow', label: '客流统计', roles: ['ADMIN', 'MANAGER', 'GUIDE', 'CHECKER', 'VISITOR'] },
  { index: '/feedback', label: '游客评价', roles: ['ADMIN', 'MANAGER', 'GUIDE', 'CHECKER', 'VISITOR'] },
  { index: '/activity', label: '文旅活动', roles: ['ADMIN', 'MANAGER', 'GUIDE', 'CHECKER', 'VISITOR'] },
  { index: '/notice', label: '场馆公告', roles: ['ADMIN', 'MANAGER', 'GUIDE', 'CHECKER', 'VISITOR'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN', 'MANAGER', 'GUIDE', 'CHECKER', 'VISITOR'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
