<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">护理培训 188</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>医疗护理培训签到考核与技能档案系统</strong><span>培训签到、技能考核、证书档案、续训提醒闭环协同</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'EDU', 'INSTRUCTOR', 'ASSESSOR', 'NURSE', 'HR'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/program', label: '培训项目', roles: ['ADMIN', 'EDU', 'INSTRUCTOR'] },
  { index: '/nurse', label: '护理人员档案', roles: ['ADMIN', 'EDU', 'HR'] },
  { index: '/class', label: '培训班次', roles: ['ADMIN', 'EDU', 'INSTRUCTOR', 'NURSE'] },
  { index: '/checkin', label: '培训签到', roles: ['ADMIN', 'EDU', 'INSTRUCTOR', 'NURSE'] },
  { index: '/skill', label: '技能项目', roles: ['ADMIN', 'EDU', 'INSTRUCTOR', 'ASSESSOR'] },
  { index: '/assessment', label: '技能考核', roles: ['ADMIN', 'EDU', 'ASSESSOR', 'NURSE'] },
  { index: '/certificate', label: '证书档案', roles: ['ADMIN', 'EDU', 'HR', 'NURSE'] },
  { index: '/retraining', label: '续训提醒', roles: ['ADMIN', 'EDU', 'HR', 'NURSE'] },
  { index: '/practice', label: '实操记录', roles: ['ADMIN', 'INSTRUCTOR', 'ASSESSOR', 'NURSE'] },
  { index: '/appeal', label: '考核申诉', roles: ['ADMIN', 'EDU', 'ASSESSOR', 'NURSE'] },
  { index: '/notice', label: '培训通知', roles: ['ADMIN', 'EDU', 'INSTRUCTOR', 'ASSESSOR', 'NURSE', 'HR'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
