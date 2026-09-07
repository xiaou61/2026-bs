<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">直播基地 177</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>直播基地主播排班与选品样品管理系统</strong><span>主播排班、选品评测、样品借还、直播复盘一体化协同</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'BASE', 'ANCHOR', 'SELECTOR', 'SAMPLE', 'MERCHANT'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/studio', label: '直播间档案', roles: ['ADMIN', 'BASE', 'ANCHOR'] },
  { index: '/anchor', label: '主播档案', roles: ['ADMIN', 'BASE', 'ANCHOR'] },
  { index: '/merchant', label: '商家档案', roles: ['ADMIN', 'BASE', 'SELECTOR', 'MERCHANT'] },
  { index: '/product', label: '商品选品', roles: ['ADMIN', 'BASE', 'SELECTOR', 'MERCHANT', 'ANCHOR'] },
  { index: '/sample', label: '样品台账', roles: ['ADMIN', 'BASE', 'SELECTOR', 'SAMPLE', 'MERCHANT'] },
  { index: '/loan', label: '样品借还', roles: ['ADMIN', 'BASE', 'ANCHOR', 'SELECTOR', 'SAMPLE', 'MERCHANT'] },
  { index: '/schedule', label: '主播排班', roles: ['ADMIN', 'BASE', 'ANCHOR'] },
  { index: '/review', label: '选品评测', roles: ['ADMIN', 'BASE', 'SELECTOR', 'ANCHOR', 'MERCHANT'] },
  { index: '/plan', label: '直播计划', roles: ['ADMIN', 'BASE', 'ANCHOR', 'SELECTOR', 'MERCHANT'] },
  { index: '/session', label: '直播场次', roles: ['ADMIN', 'BASE', 'ANCHOR', 'SELECTOR'] },
  { index: '/replay', label: '直播复盘', roles: ['ADMIN', 'BASE', 'ANCHOR', 'SELECTOR', 'MERCHANT'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
