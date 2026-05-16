<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">助残服务 191</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>社区助残器具借用与康复随访平台</strong><span>器具借用、康复计划、随访记录与回收提醒协同</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'COMMUNITY', 'AIDSTAFF', 'THERAPIST', 'VOLUNTEER', 'RESIDENT'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/center', label: '服务站点', roles: ['ADMIN', 'COMMUNITY'] },
  { index: '/resident', label: '居民档案', roles: ['ADMIN', 'COMMUNITY', 'THERAPIST', 'VOLUNTEER'] },
  { index: '/device', label: '助残器具', roles: ['ADMIN', 'COMMUNITY', 'AIDSTAFF'] },
  { index: '/borrow', label: '器具借用', roles: ['ADMIN', 'COMMUNITY', 'AIDSTAFF', 'RESIDENT'] },
  { index: '/approval', label: '借用审核', roles: ['ADMIN', 'COMMUNITY', 'AIDSTAFF'] },
  { index: '/delivery', label: '器具交付', roles: ['ADMIN', 'COMMUNITY', 'AIDSTAFF', 'VOLUNTEER'] },
  { index: '/plan', label: '康复计划', roles: ['ADMIN', 'COMMUNITY', 'THERAPIST', 'RESIDENT'] },
  { index: '/training', label: '康复训练', roles: ['ADMIN', 'COMMUNITY', 'THERAPIST'] },
  { index: '/followup', label: '随访记录', roles: ['ADMIN', 'COMMUNITY', 'THERAPIST', 'VOLUNTEER', 'RESIDENT'] },
  { index: '/reminder', label: '回收提醒', roles: ['ADMIN', 'COMMUNITY', 'AIDSTAFF', 'VOLUNTEER'] },
  { index: '/maintenance', label: '器具维护', roles: ['ADMIN', 'COMMUNITY', 'AIDSTAFF'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
