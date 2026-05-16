<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">市场追溯 166</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>农贸市场摊位巡检与食品追溯台账系统</strong><span>摊位巡检、问题整改、商品溯源、抽检记录、风险预警一体化协同</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'MARKET', 'INSPECTOR', 'VENDOR', 'SAMPLER', 'REGULATOR'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/area', label: '市场区域', roles: ['ADMIN', 'MARKET', 'INSPECTOR', 'REGULATOR'] },
  { index: '/stall', label: '摊位档案', roles: ['ADMIN', 'MARKET', 'INSPECTOR', 'VENDOR', 'REGULATOR'] },
  { index: '/vendor', label: '商户档案', roles: ['ADMIN', 'MARKET', 'INSPECTOR', 'VENDOR'] },
  { index: '/product', label: '商品溯源', roles: ['ADMIN', 'MARKET', 'INSPECTOR', 'VENDOR', 'REGULATOR'] },
  { index: '/inspection', label: '摊位巡检', roles: ['ADMIN', 'MARKET', 'INSPECTOR', 'REGULATOR'] },
  { index: '/rectification', label: '问题整改', roles: ['ADMIN', 'MARKET', 'INSPECTOR', 'VENDOR', 'REGULATOR'] },
  { index: '/sample', label: '抽检记录', roles: ['ADMIN', 'MARKET', 'SAMPLER', 'REGULATOR'] },
  { index: '/test', label: '检测结果', roles: ['ADMIN', 'MARKET', 'SAMPLER', 'REGULATOR', 'VENDOR'] },
  { index: '/source', label: '进货台账', roles: ['ADMIN', 'MARKET', 'VENDOR', 'INSPECTOR'] },
  { index: '/disposal', label: '处置记录', roles: ['ADMIN', 'MARKET', 'INSPECTOR', 'VENDOR', 'REGULATOR'] },
  { index: '/alert', label: '风险预警', roles: ['ADMIN', 'MARKET', 'INSPECTOR', 'SAMPLER', 'REGULATOR'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN', 'REGULATOR'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
