<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">社区党建 155</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>社区党建活动报名与积分激励平台</strong><span>活动报名、志愿积分、组织关系、榜单统计一体化管理</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'SECRETARY', 'ORGANIZER', 'VOLUNTEER', 'RESIDENT'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/branch', label: '党组织维护', roles: ['ADMIN', 'SECRETARY'] },
  { index: '/member', label: '党员档案', roles: ['ADMIN', 'SECRETARY', 'RESIDENT'] },
  { index: '/activity', label: '党建活动', roles: ['ADMIN', 'SECRETARY', 'ORGANIZER'] },
  { index: '/registration', label: '活动报名', roles: ['ADMIN', 'ORGANIZER', 'VOLUNTEER', 'RESIDENT'] },
  { index: '/attendance', label: '签到记录', roles: ['ADMIN', 'ORGANIZER', 'VOLUNTEER'] },
  { index: '/task', label: '志愿任务', roles: ['ADMIN', 'ORGANIZER', 'VOLUNTEER'] },
  { index: '/points', label: '积分记录', roles: ['ADMIN', 'SECRETARY', 'ORGANIZER', 'VOLUNTEER', 'RESIDENT'] },
  { index: '/exchange', label: '积分兑换', roles: ['ADMIN', 'ORGANIZER', 'VOLUNTEER', 'RESIDENT'] },
  { index: '/transfer', label: '组织关系', roles: ['ADMIN', 'SECRETARY', 'RESIDENT'] },
  { index: '/ranking', label: '榜单统计', roles: ['ADMIN', 'SECRETARY', 'ORGANIZER', 'VOLUNTEER', 'RESIDENT'] },
  { index: '/notice', label: '通知公告', roles: ['ADMIN', 'SECRETARY', 'ORGANIZER'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN', 'SECRETARY'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
