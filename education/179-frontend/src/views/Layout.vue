<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">课堂考勤 179</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>高校考勤异常申诉与课堂巡查管理系统</strong><span>考勤记录、异常申诉、课堂巡查、整改反馈一体化协同</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'ACADEMIC', 'TEACHER', 'INSPECTOR', 'COUNSELOR', 'STUDENT'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/teaching', label: '教学班级', roles: ['ADMIN', 'ACADEMIC', 'TEACHER', 'COUNSELOR'] },
  { index: '/student', label: '学生档案', roles: ['ADMIN', 'ACADEMIC', 'COUNSELOR', 'TEACHER', 'STUDENT'] },
  { index: '/teacher', label: '教师档案', roles: ['ADMIN', 'ACADEMIC', 'TEACHER'] },
  { index: '/schedule', label: '课程排课', roles: ['ADMIN', 'ACADEMIC', 'TEACHER', 'STUDENT'] },
  { index: '/attendance', label: '考勤记录', roles: ['ADMIN', 'ACADEMIC', 'TEACHER', 'COUNSELOR', 'STUDENT'] },
  { index: '/appeal', label: '异常申诉', roles: ['ADMIN', 'ACADEMIC', 'TEACHER', 'COUNSELOR', 'STUDENT'] },
  { index: '/review', label: '申诉审核', roles: ['ADMIN', 'ACADEMIC', 'TEACHER', 'COUNSELOR'] },
  { index: '/inspection', label: '课堂巡查', roles: ['ADMIN', 'ACADEMIC', 'INSPECTOR', 'TEACHER'] },
  { index: '/issue', label: '巡查问题', roles: ['ADMIN', 'ACADEMIC', 'INSPECTOR', 'COUNSELOR', 'TEACHER'] },
  { index: '/rectification', label: '整改任务', roles: ['ADMIN', 'ACADEMIC', 'INSPECTOR', 'COUNSELOR', 'TEACHER'] },
  { index: '/feedback', label: '整改反馈', roles: ['ADMIN', 'ACADEMIC', 'INSPECTOR', 'COUNSELOR', 'TEACHER', 'STUDENT'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
