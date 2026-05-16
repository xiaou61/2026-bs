<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">公厕运维 185</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>城市公厕巡检保洁与耗材补给调度系统</strong><span>公厕点位、保洁巡检、耗材补给、投诉整改闭环协同</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'SANITATION', 'CLEANER', 'SUPPLY', 'INSPECTOR', 'CITIZEN'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/site', label: '公厕点位', roles: ['ADMIN', 'SANITATION', 'INSPECTOR'] },
  { index: '/route', label: '保洁路线', roles: ['ADMIN', 'SANITATION', 'CLEANER', 'INSPECTOR'] },
  { index: '/task', label: '保洁任务', roles: ['ADMIN', 'SANITATION', 'CLEANER'] },
  { index: '/record', label: '保洁记录', roles: ['ADMIN', 'SANITATION', 'CLEANER', 'INSPECTOR'] },
  { index: '/plan', label: '巡检计划', roles: ['ADMIN', 'SANITATION', 'INSPECTOR'] },
  { index: '/inspection', label: '巡检记录', roles: ['ADMIN', 'SANITATION', 'INSPECTOR'] },
  { index: '/stock', label: '耗材库存', roles: ['ADMIN', 'SANITATION', 'SUPPLY', 'CLEANER'] },
  { index: '/request', label: '补给申请', roles: ['ADMIN', 'SANITATION', 'SUPPLY', 'CLEANER'] },
  { index: '/dispatch', label: '补给调度', roles: ['ADMIN', 'SANITATION', 'SUPPLY'] },
  { index: '/complaint', label: '投诉反馈', roles: ['ADMIN', 'SANITATION', 'INSPECTOR', 'CITIZEN'] },
  { index: '/rectification', label: '问题整改', roles: ['ADMIN', 'SANITATION', 'CLEANER', 'SUPPLY', 'INSPECTOR'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
