<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">临期处置 162</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>生鲜门店临期商品预警与促销处置系统</strong><span>临期预警、促销处置、库存周转、报损复盘一体化协同</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'MANAGER', 'CLERK', 'STOCK', 'MARKETING', 'SUPPLIER'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/store', label: '门店档案', roles: ['ADMIN', 'MANAGER'] },
  { index: '/supplier', label: '供应商档案', roles: ['ADMIN', 'MANAGER', 'SUPPLIER'] },
  { index: '/category', label: '生鲜品类', roles: ['ADMIN', 'MANAGER', 'STOCK'] },
  { index: '/batch', label: '商品批次', roles: ['ADMIN', 'MANAGER', 'CLERK', 'STOCK'] },
  { index: '/rule', label: '保质期规则', roles: ['ADMIN', 'MANAGER', 'STOCK'] },
  { index: '/warning', label: '临期预警', roles: ['ADMIN', 'MANAGER', 'CLERK', 'STOCK'] },
  { index: '/promotion', label: '促销策略', roles: ['ADMIN', 'MANAGER', 'MARKETING'] },
  { index: '/discount', label: '折扣执行', roles: ['ADMIN', 'MANAGER', 'CLERK', 'MARKETING'] },
  { index: '/loss', label: '报损记录', roles: ['ADMIN', 'MANAGER', 'CLERK', 'STOCK'] },
  { index: '/turnover', label: '库存周转', roles: ['ADMIN', 'MANAGER', 'STOCK'] },
  { index: '/analysis', label: '门店分析', roles: ['ADMIN', 'MANAGER', 'MARKETING'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN', 'MANAGER'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
