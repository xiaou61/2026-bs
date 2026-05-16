<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">儿童托管 181</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>社区儿童托管签到接送与安全告警系统</strong><span>托管签到、接送授权、安全告警、家长通知闭环协同</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'CENTER', 'TEACHER', 'PARENT', 'SECURITY', 'NURSE'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/classroom', label: '托管班级', roles: ['ADMIN', 'CENTER', 'TEACHER'] },
  { index: '/child', label: '儿童档案', roles: ['ADMIN', 'CENTER', 'TEACHER', 'PARENT', 'NURSE'] },
  { index: '/guardian', label: '监护人档案', roles: ['ADMIN', 'CENTER', 'PARENT', 'SECURITY'] },
  { index: '/checkin', label: '签到记录', roles: ['ADMIN', 'CENTER', 'TEACHER', 'PARENT'] },
  { index: '/authorization', label: '接送授权', roles: ['ADMIN', 'CENTER', 'PARENT', 'SECURITY'] },
  { index: '/pickup', label: '接送记录', roles: ['ADMIN', 'CENTER', 'TEACHER', 'PARENT', 'SECURITY'] },
  { index: '/alert', label: '安全告警', roles: ['ADMIN', 'CENTER', 'SECURITY', 'TEACHER', 'PARENT'] },
  { index: '/health', label: '健康晨检', roles: ['ADMIN', 'CENTER', 'NURSE', 'TEACHER', 'PARENT'] },
  { index: '/activity', label: '托管活动', roles: ['ADMIN', 'CENTER', 'TEACHER', 'PARENT'] },
  { index: '/notice', label: '家长通知', roles: ['ADMIN', 'CENTER', 'TEACHER', 'PARENT', 'SECURITY', 'NURSE'] },
  { index: '/incident', label: '事件跟进', roles: ['ADMIN', 'CENTER', 'SECURITY', 'TEACHER', 'PARENT'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
