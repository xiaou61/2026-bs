<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">校车核验 169</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>校车乘车预约与学生上下车核验系统</strong><span>乘车预约、上下车核验、异常告警、家长通知一体化协同</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'SCHOOL', 'DISPATCH', 'DRIVER', 'TEACHER', 'GUARDIAN'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/route', label: '校车线路', roles: ['ADMIN', 'SCHOOL', 'DISPATCH', 'DRIVER', 'GUARDIAN'] },
  { index: '/stop', label: '站点档案', roles: ['ADMIN', 'SCHOOL', 'DISPATCH', 'DRIVER', 'GUARDIAN'] },
  { index: '/vehicle', label: '车辆档案', roles: ['ADMIN', 'SCHOOL', 'DISPATCH', 'DRIVER'] },
  { index: '/driver', label: '司机档案', roles: ['ADMIN', 'SCHOOL', 'DISPATCH', 'DRIVER'] },
  { index: '/student', label: '学生档案', roles: ['ADMIN', 'SCHOOL', 'TEACHER', 'GUARDIAN'] },
  { index: '/guardian', label: '家长档案', roles: ['ADMIN', 'SCHOOL', 'TEACHER', 'GUARDIAN'] },
  { index: '/reservation', label: '乘车预约', roles: ['ADMIN', 'SCHOOL', 'DISPATCH', 'TEACHER', 'GUARDIAN'] },
  { index: '/boarding', label: '上车核验', roles: ['ADMIN', 'SCHOOL', 'DISPATCH', 'DRIVER', 'TEACHER', 'GUARDIAN'] },
  { index: '/dropoff', label: '下车核验', roles: ['ADMIN', 'SCHOOL', 'DISPATCH', 'DRIVER', 'TEACHER', 'GUARDIAN'] },
  { index: '/exception', label: '异常告警', roles: ['ADMIN', 'SCHOOL', 'DISPATCH', 'DRIVER', 'TEACHER', 'GUARDIAN'] },
  { index: '/notice', label: '家长通知', roles: ['ADMIN', 'SCHOOL', 'DISPATCH', 'TEACHER', 'GUARDIAN'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN', 'SCHOOL'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
