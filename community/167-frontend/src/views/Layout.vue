<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">分类积分 167</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>社区垃圾分类督导与积分兑换平台</strong><span>分类打卡、督导记录、整改任务、积分记录、积分兑换一体化协同</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'COMMUNITY', 'SUPERVISOR', 'RESIDENT', 'VOLUNTEER', 'EXCHANGE'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/area', label: '社区区域', roles: ['ADMIN', 'COMMUNITY', 'SUPERVISOR'] },
  { index: '/building', label: '楼栋档案', roles: ['ADMIN', 'COMMUNITY', 'SUPERVISOR', 'VOLUNTEER'] },
  { index: '/resident', label: '居民档案', roles: ['ADMIN', 'COMMUNITY', 'SUPERVISOR', 'RESIDENT'] },
  { index: '/category', label: '分类规则', roles: ['ADMIN', 'COMMUNITY', 'SUPERVISOR', 'VOLUNTEER', 'RESIDENT'] },
  { index: '/checkin', label: '分类打卡', roles: ['ADMIN', 'COMMUNITY', 'SUPERVISOR', 'RESIDENT', 'VOLUNTEER'] },
  { index: '/supervision', label: '督导记录', roles: ['ADMIN', 'COMMUNITY', 'SUPERVISOR', 'VOLUNTEER'] },
  { index: '/correction', label: '整改任务', roles: ['ADMIN', 'COMMUNITY', 'SUPERVISOR', 'VOLUNTEER', 'RESIDENT'] },
  { index: '/points', label: '积分记录', roles: ['ADMIN', 'COMMUNITY', 'SUPERVISOR', 'RESIDENT', 'EXCHANGE'] },
  { index: '/reward', label: '兑换商品', roles: ['ADMIN', 'COMMUNITY', 'EXCHANGE', 'RESIDENT'] },
  { index: '/exchange', label: '积分兑换', roles: ['ADMIN', 'COMMUNITY', 'EXCHANGE', 'RESIDENT'] },
  { index: '/ranking', label: '楼栋排名', roles: ['ADMIN', 'COMMUNITY', 'SUPERVISOR', 'VOLUNTEER', 'RESIDENT'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN', 'COMMUNITY'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
