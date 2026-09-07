<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">水务巡线 176</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>水务巡线工单与阀门检修协同管理系统</strong><span>巡线路线、阀门台账、故障派工、检修回执一体化协同</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'DISPATCH', 'PATROL', 'REPAIR', 'WAREHOUSE', 'SUPERVISOR'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/station', label: '水务站点', roles: ['ADMIN', 'DISPATCH', 'SUPERVISOR'] },
  { index: '/section', label: '管线区段', roles: ['ADMIN', 'DISPATCH', 'PATROL', 'SUPERVISOR'] },
  { index: '/route', label: '巡线路线', roles: ['ADMIN', 'DISPATCH', 'PATROL', 'SUPERVISOR'] },
  { index: '/valve', label: '阀门台账', roles: ['ADMIN', 'DISPATCH', 'PATROL', 'REPAIR', 'SUPERVISOR'] },
  { index: '/task', label: '巡线任务', roles: ['ADMIN', 'DISPATCH', 'PATROL', 'SUPERVISOR'] },
  { index: '/record', label: '巡线记录', roles: ['ADMIN', 'DISPATCH', 'PATROL', 'SUPERVISOR'] },
  { index: '/fault', label: '故障上报', roles: ['ADMIN', 'DISPATCH', 'PATROL', 'REPAIR', 'SUPERVISOR'] },
  { index: '/dispatch', label: '故障派工', roles: ['ADMIN', 'DISPATCH', 'REPAIR', 'SUPERVISOR'] },
  { index: '/receipt', label: '检修回执', roles: ['ADMIN', 'DISPATCH', 'REPAIR', 'SUPERVISOR'] },
  { index: '/spare', label: '备件台账', roles: ['ADMIN', 'DISPATCH', 'REPAIR', 'WAREHOUSE'] },
  { index: '/warning', label: '风险预警', roles: ['ADMIN', 'DISPATCH', 'PATROL', 'REPAIR', 'SUPERVISOR'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
