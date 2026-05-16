<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">医院陪护 192</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>医院陪护服务预约与护工评价管理系统</strong><span>陪护预约、护工排班、服务记录与评价结算协同</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'HOSPITAL', 'COORDINATOR', 'CAREGIVER', 'FINANCE', 'FAMILY'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/ward', label: '病区档案', roles: ['ADMIN', 'HOSPITAL'] },
  { index: '/patient', label: '患者档案', roles: ['ADMIN', 'HOSPITAL', 'COORDINATOR', 'FAMILY'] },
  { index: '/caregiver', label: '护工档案', roles: ['ADMIN', 'HOSPITAL', 'COORDINATOR', 'CAREGIVER'] },
  { index: '/appointment', label: '陪护预约', roles: ['ADMIN', 'HOSPITAL', 'COORDINATOR', 'FAMILY'] },
  { index: '/review', label: '预约审核', roles: ['ADMIN', 'HOSPITAL', 'COORDINATOR'] },
  { index: '/schedule', label: '护工排班', roles: ['ADMIN', 'HOSPITAL', 'COORDINATOR', 'CAREGIVER'] },
  { index: '/assignment', label: '服务派单', roles: ['ADMIN', 'COORDINATOR', 'CAREGIVER'] },
  { index: '/service', label: '服务记录', roles: ['ADMIN', 'COORDINATOR', 'CAREGIVER', 'FAMILY'] },
  { index: '/communication', label: '家属沟通', roles: ['ADMIN', 'HOSPITAL', 'COORDINATOR', 'FAMILY'] },
  { index: '/evaluation', label: '护工评价', roles: ['ADMIN', 'COORDINATOR', 'FINANCE', 'FAMILY'] },
  { index: '/settlement', label: '评价结算', roles: ['ADMIN', 'COORDINATOR', 'FINANCE', 'FAMILY'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
