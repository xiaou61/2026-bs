<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">家政信用服务 197</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>社区家政服务预约与人员信用评价系统</strong><span>服务预约、人员排班、上门记录与信用评价协同</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'AGENCY', 'DISPATCH', 'WORKER', 'QUALITY', 'RESIDENT'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/station', label: '服务站点', roles: ['ADMIN', 'AGENCY'] },
  { index: '/resident', label: '居民档案', roles: ['ADMIN', 'AGENCY', 'DISPATCH', 'RESIDENT'] },
  { index: '/worker', label: '人员档案', roles: ['ADMIN', 'AGENCY', 'DISPATCH', 'WORKER', 'QUALITY'] },
  { index: '/service', label: '服务项目', roles: ['ADMIN', 'AGENCY', 'DISPATCH', 'RESIDENT'] },
  { index: '/booking', label: '服务预约', roles: ['ADMIN', 'AGENCY', 'DISPATCH', 'RESIDENT'] },
  { index: '/review', label: '预约审核', roles: ['ADMIN', 'AGENCY', 'DISPATCH'] },
  { index: '/dispatch', label: '人员排班', roles: ['ADMIN', 'AGENCY', 'DISPATCH', 'WORKER'] },
  { index: '/record', label: '上门记录', roles: ['ADMIN', 'AGENCY', 'DISPATCH', 'WORKER', 'RESIDENT'] },
  { index: '/evaluation', label: '信用评价', roles: ['ADMIN', 'AGENCY', 'QUALITY', 'RESIDENT'] },
  { index: '/complaint', label: '投诉处理', roles: ['ADMIN', 'AGENCY', 'QUALITY', 'RESIDENT'] },
  { index: '/settlement', label: '费用结算', roles: ['ADMIN', 'AGENCY', 'DISPATCH'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
