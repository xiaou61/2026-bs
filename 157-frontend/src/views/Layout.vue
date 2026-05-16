<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">园区调度 157</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>物流园区车辆入场预约与道口调度平台</strong><span>入场预约、门岗核验、排队叫号、道口调度一体化管理</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'DISPATCHER', 'GATEKEEPER', 'YARDMASTER', 'CARRIER'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/carrier', label: '承运商档案', roles: ['ADMIN', 'DISPATCHER', 'CARRIER'] },
  { index: '/vehicle', label: '车辆档案', roles: ['ADMIN', 'DISPATCHER', 'GATEKEEPER', 'CARRIER'] },
  { index: '/driver', label: '司机档案', roles: ['ADMIN', 'DISPATCHER', 'GATEKEEPER', 'CARRIER'] },
  { index: '/appointment', label: '入场预约', roles: ['ADMIN', 'DISPATCHER', 'GATEKEEPER', 'CARRIER'] },
  { index: '/slot', label: '时段计划', roles: ['ADMIN', 'DISPATCHER', 'YARDMASTER'] },
  { index: '/checkin', label: '门岗核验', roles: ['ADMIN', 'DISPATCHER', 'GATEKEEPER'] },
  { index: '/queue', label: '排队叫号', roles: ['ADMIN', 'DISPATCHER', 'GATEKEEPER', 'YARDMASTER', 'CARRIER'] },
  { index: '/dock', label: '道口资源', roles: ['ADMIN', 'DISPATCHER', 'YARDMASTER'] },
  { index: '/assignment', label: '道口分配', roles: ['ADMIN', 'DISPATCHER', 'YARDMASTER', 'CARRIER'] },
  { index: '/loading', label: '装卸任务', roles: ['ADMIN', 'DISPATCHER', 'YARDMASTER', 'CARRIER'] },
  { index: '/turnaround', label: '周转统计', roles: ['ADMIN', 'DISPATCHER', 'YARDMASTER'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN', 'DISPATCHER'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
