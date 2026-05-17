<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">药店慢病管理 196</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>药店处方审核与慢病续方提醒管理系统</strong><span>处方审核、购药记录、续方提醒与风险复核协同</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'PHARMACY', 'PHARMACIST', 'CLERK', 'FOLLOWUP', 'CUSTOMER'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/store', label: '药店门店', roles: ['ADMIN', 'PHARMACY'] },
  { index: '/customer', label: '顾客档案', roles: ['ADMIN', 'PHARMACY', 'PHARMACIST', 'FOLLOWUP', 'CUSTOMER'] },
  { index: '/medicine', label: '药品目录', roles: ['ADMIN', 'PHARMACY', 'PHARMACIST', 'CLERK'] },
  { index: '/prescription', label: '处方登记', roles: ['ADMIN', 'PHARMACY', 'PHARMACIST', 'CLERK', 'CUSTOMER'] },
  { index: '/review', label: '处方审核', roles: ['ADMIN', 'PHARMACY', 'PHARMACIST'] },
  { index: '/risk', label: '风险复核', roles: ['ADMIN', 'PHARMACY', 'PHARMACIST'] },
  { index: '/purchase', label: '购药记录', roles: ['ADMIN', 'PHARMACY', 'CLERK', 'CUSTOMER'] },
  { index: '/guide', label: '用药指导', roles: ['ADMIN', 'PHARMACY', 'PHARMACIST', 'CLERK', 'CUSTOMER'] },
  { index: '/plan', label: '慢病方案', roles: ['ADMIN', 'PHARMACY', 'PHARMACIST', 'FOLLOWUP', 'CUSTOMER'] },
  { index: '/reminder', label: '续方提醒', roles: ['ADMIN', 'PHARMACY', 'FOLLOWUP', 'CUSTOMER'] },
  { index: '/followup', label: '随访记录', roles: ['ADMIN', 'PHARMACY', 'FOLLOWUP', 'CUSTOMER'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
