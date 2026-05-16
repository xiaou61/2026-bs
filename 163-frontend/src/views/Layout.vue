<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">轮转考核 163</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>医学实习轮转考核与病例学习管理系统</strong><span>轮转安排、病例学习、带教评分、出科考核一体化协同</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'SECRETARY', 'TEACHER', 'STUDENT', 'EXAMINER', 'DIRECTOR'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/department', label: '轮转科室', roles: ['ADMIN', 'SECRETARY', 'DIRECTOR'] },
  { index: '/student', label: '实习学生', roles: ['ADMIN', 'SECRETARY', 'TEACHER', 'STUDENT'] },
  { index: '/teacher', label: '带教老师', roles: ['ADMIN', 'SECRETARY', 'DIRECTOR', 'TEACHER'] },
  { index: '/rotation', label: '轮转安排', roles: ['ADMIN', 'SECRETARY', 'DIRECTOR', 'TEACHER', 'STUDENT'] },
  { index: '/case', label: '病例学习', roles: ['ADMIN', 'SECRETARY', 'TEACHER', 'STUDENT'] },
  { index: '/study', label: '学习记录', roles: ['ADMIN', 'TEACHER', 'STUDENT'] },
  { index: '/round', label: '教学查房', roles: ['ADMIN', 'SECRETARY', 'DIRECTOR', 'TEACHER', 'STUDENT'] },
  { index: '/skill', label: '技能训练', roles: ['ADMIN', 'TEACHER', 'STUDENT', 'EXAMINER'] },
  { index: '/score', label: '带教评分', roles: ['ADMIN', 'SECRETARY', 'TEACHER', 'STUDENT', 'DIRECTOR'] },
  { index: '/exam', label: '出科考核', roles: ['ADMIN', 'SECRETARY', 'EXAMINER', 'TEACHER', 'STUDENT', 'DIRECTOR'] },
  { index: '/feedback', label: '反馈整改', roles: ['ADMIN', 'SECRETARY', 'DIRECTOR', 'TEACHER', 'STUDENT'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN', 'SECRETARY'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
