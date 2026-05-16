<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">校园寄卖 153</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>校园二手物品寄卖与信用评价系统</strong><span>寄卖订单、担保交易、评价体系、违约处理一体化管理</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'OPERATOR', 'SELLER', 'BUYER', 'ARBITER'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/category', label: '物品分类', roles: ['ADMIN', 'OPERATOR', 'SELLER', 'BUYER', 'ARBITER'] },
  { index: '/student', label: '学生档案', roles: ['ADMIN', 'OPERATOR', 'SELLER', 'BUYER', 'ARBITER'] },
  { index: '/consignment', label: '寄卖物品', roles: ['ADMIN', 'OPERATOR', 'SELLER', 'BUYER', 'ARBITER'] },
  { index: '/audit', label: '上架审核', roles: ['ADMIN', 'OPERATOR', 'SELLER', 'BUYER', 'ARBITER'] },
  { index: '/order', label: '担保订单', roles: ['ADMIN', 'OPERATOR', 'SELLER', 'BUYER', 'ARBITER'] },
  { index: '/payment', label: '担保支付', roles: ['ADMIN', 'OPERATOR', 'SELLER', 'BUYER', 'ARBITER'] },
  { index: '/handover', label: '交接确认', roles: ['ADMIN', 'OPERATOR', 'SELLER', 'BUYER', 'ARBITER'] },
  { index: '/credit', label: '信用评价', roles: ['ADMIN', 'OPERATOR', 'SELLER', 'BUYER', 'ARBITER'] },
  { index: '/breach', label: '违约记录', roles: ['ADMIN', 'OPERATOR', 'SELLER', 'BUYER', 'ARBITER'] },
  { index: '/complaint', label: '纠纷申诉', roles: ['ADMIN', 'OPERATOR', 'SELLER', 'BUYER', 'ARBITER'] },
  { index: '/notice', label: '平台公告', roles: ['ADMIN', 'OPERATOR', 'SELLER', 'BUYER', 'ARBITER'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN', 'OPERATOR', 'SELLER', 'BUYER', 'ARBITER'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
