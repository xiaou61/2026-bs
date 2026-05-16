<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">后厨留样 186</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>校园餐厅后厨留样与卫生巡检台账系统</strong><span>留样登记、卫生巡检、食材验收、问题整改闭环协同</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'CANTEEN', 'CHEF', 'INSPECTOR', 'WAREHOUSE', 'SCHOOL'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/canteen', label: '餐厅档案', roles: ['ADMIN', 'CANTEEN', 'SCHOOL'] },
  { index: '/area', label: '后厨区域', roles: ['ADMIN', 'CANTEEN', 'CHEF', 'INSPECTOR'] },
  { index: '/dish', label: '菜品档案', roles: ['ADMIN', 'CANTEEN', 'CHEF', 'SCHOOL'] },
  { index: '/sample', label: '留样登记', roles: ['ADMIN', 'CANTEEN', 'CHEF', 'INSPECTOR', 'SCHOOL'] },
  { index: '/storage', label: '留样存储', roles: ['ADMIN', 'CANTEEN', 'CHEF', 'INSPECTOR'] },
  { index: '/ingredient', label: '食材验收', roles: ['ADMIN', 'CANTEEN', 'WAREHOUSE', 'CHEF', 'SCHOOL'] },
  { index: '/disinfection', label: '消毒记录', roles: ['ADMIN', 'CANTEEN', 'CHEF', 'INSPECTOR'] },
  { index: '/inspection', label: '卫生巡检', roles: ['ADMIN', 'CANTEEN', 'INSPECTOR', 'SCHOOL'] },
  { index: '/rectification', label: '问题整改', roles: ['ADMIN', 'CANTEEN', 'CHEF', 'INSPECTOR', 'SCHOOL'] },
  { index: '/risk', label: '风险提醒', roles: ['ADMIN', 'CANTEEN', 'INSPECTOR', 'SCHOOL'] },
  { index: '/waste', label: '厨余处置', roles: ['ADMIN', 'CANTEEN', 'CHEF', 'WAREHOUSE', 'INSPECTOR'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
