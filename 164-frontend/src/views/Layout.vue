<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">体育赛事 164</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>校园体育赛事报名编排与裁判评分系统</strong><span>报名分组、赛程编排、裁判评分、成绩公示一体化协同</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'ORGANIZER', 'COACH', 'ATHLETE', 'REFEREE', 'COMMITTEE'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/event', label: '体育赛事', roles: ['ADMIN', 'ORGANIZER', 'COMMITTEE'] },
  { index: '/team', label: '参赛队伍', roles: ['ADMIN', 'ORGANIZER', 'COACH'] },
  { index: '/athlete', label: '运动员档案', roles: ['ADMIN', 'ORGANIZER', 'COACH', 'ATHLETE'] },
  { index: '/registration', label: '赛事报名', roles: ['ADMIN', 'ORGANIZER', 'COACH', 'ATHLETE'] },
  { index: '/group', label: '报名分组', roles: ['ADMIN', 'ORGANIZER', 'COACH', 'REFEREE'] },
  { index: '/schedule', label: '赛程编排', roles: ['ADMIN', 'ORGANIZER', 'COACH', 'REFEREE', 'ATHLETE'] },
  { index: '/venue', label: '场地资源', roles: ['ADMIN', 'ORGANIZER', 'REFEREE'] },
  { index: '/referee', label: '裁判指派', roles: ['ADMIN', 'ORGANIZER', 'REFEREE', 'COMMITTEE'] },
  { index: '/score', label: '裁判评分', roles: ['ADMIN', 'ORGANIZER', 'REFEREE', 'COMMITTEE'] },
  { index: '/result', label: '成绩公示', roles: ['ADMIN', 'ORGANIZER', 'COACH', 'ATHLETE', 'REFEREE', 'COMMITTEE'] },
  { index: '/appeal', label: '申诉复核', roles: ['ADMIN', 'ORGANIZER', 'COACH', 'ATHLETE', 'COMMITTEE'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN', 'ORGANIZER'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
