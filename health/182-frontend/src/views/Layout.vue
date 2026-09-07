<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">养老助餐 182</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>养老助餐配送排线与营养套餐管理系统</strong><span>助餐订单、营养套餐、配送排线、签收回执闭环协同</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'CENTER', 'DIETITIAN', 'DISPATCH', 'COURIER', 'ELDER'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/site', label: '助餐站点', roles: ['ADMIN', 'CENTER', 'DISPATCH'] },
  { index: '/elder', label: '老人档案', roles: ['ADMIN', 'CENTER', 'ELDER', 'DIETITIAN'] },
  { index: '/menu', label: '营养套餐', roles: ['ADMIN', 'CENTER', 'DIETITIAN', 'ELDER'] },
  { index: '/order', label: '助餐订单', roles: ['ADMIN', 'CENTER', 'ELDER', 'DISPATCH'] },
  { index: '/route', label: '配送排线', roles: ['ADMIN', 'CENTER', 'DISPATCH', 'COURIER'] },
  { index: '/delivery', label: '配送任务', roles: ['ADMIN', 'CENTER', 'DISPATCH', 'COURIER', 'ELDER'] },
  { index: '/receipt', label: '签收回执', roles: ['ADMIN', 'CENTER', 'COURIER', 'ELDER'] },
  { index: '/restriction', label: '饮食禁忌', roles: ['ADMIN', 'CENTER', 'DIETITIAN', 'ELDER'] },
  { index: '/analysis', label: '营养分析', roles: ['ADMIN', 'CENTER', 'DIETITIAN', 'ELDER'] },
  { index: '/subsidy', label: '补贴记录', roles: ['ADMIN', 'CENTER', 'ELDER'] },
  { index: '/followup', label: '回访关怀', roles: ['ADMIN', 'CENTER', 'DIETITIAN', 'DISPATCH', 'ELDER'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
