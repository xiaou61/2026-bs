<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">危废联动监管 194</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>工业园区危废暂存与转运联动监管平台</strong><span>危废暂存、转运联单、称重记录与台账审计协同</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'PARK', 'WAREHOUSE', 'TRANSPORT', 'INSPECTOR', 'ENTERPRISE'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/enterprise', label: '园区企业', roles: ['ADMIN', 'PARK', 'ENTERPRISE'] },
  { index: '/waste', label: '危废类别', roles: ['ADMIN', 'PARK', 'WAREHOUSE', 'ENTERPRISE'] },
  { index: '/facility', label: '暂存设施', roles: ['ADMIN', 'PARK', 'WAREHOUSE', 'INSPECTOR'] },
  { index: '/inbound', label: '入库登记', roles: ['ADMIN', 'PARK', 'WAREHOUSE', 'ENTERPRISE'] },
  { index: '/check', label: '暂存巡检', roles: ['ADMIN', 'PARK', 'WAREHOUSE', 'INSPECTOR'] },
  { index: '/transfer', label: '转运联单', roles: ['ADMIN', 'PARK', 'WAREHOUSE', 'TRANSPORT', 'ENTERPRISE'] },
  { index: '/dispatch', label: '车辆调度', roles: ['ADMIN', 'PARK', 'TRANSPORT'] },
  { index: '/weighing', label: '称重记录', roles: ['ADMIN', 'PARK', 'WAREHOUSE', 'TRANSPORT'] },
  { index: '/handover', label: '处置交接', roles: ['ADMIN', 'PARK', 'TRANSPORT', 'INSPECTOR'] },
  { index: '/warning', label: '风险预警', roles: ['ADMIN', 'PARK', 'WAREHOUSE', 'INSPECTOR'] },
  { index: '/audit', label: '台账审计', roles: ['ADMIN', 'PARK', 'INSPECTOR'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
