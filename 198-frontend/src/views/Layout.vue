<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">充电宝运营 198</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>城市共享充电宝投放巡检与收益结算系统</strong><span>点位投放、设备巡检、异常回收与收益结算协同</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'OPERATOR', 'SITE', 'INSPECTOR', 'FINANCE', 'MERCHANT'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/site', label: '投放点位', roles: ['ADMIN', 'OPERATOR', 'SITE', 'MERCHANT'] },
  { index: '/cabinet', label: '设备柜档案', roles: ['ADMIN', 'OPERATOR', 'SITE', 'INSPECTOR'] },
  { index: '/device', label: '充电宝档案', roles: ['ADMIN', 'OPERATOR', 'SITE', 'INSPECTOR'] },
  { index: '/plan', label: '点位投放', roles: ['ADMIN', 'OPERATOR', 'SITE'] },
  { index: '/inspection', label: '设备巡检', roles: ['ADMIN', 'OPERATOR', 'INSPECTOR', 'SITE'] },
  { index: '/repair', label: '故障维修', roles: ['ADMIN', 'OPERATOR', 'INSPECTOR', 'SITE'] },
  { index: '/recycle', label: '异常回收', roles: ['ADMIN', 'OPERATOR', 'INSPECTOR', 'SITE'] },
  { index: '/order', label: '租借订单', roles: ['ADMIN', 'OPERATOR', 'FINANCE', 'MERCHANT'] },
  { index: '/income', label: '商户收益', roles: ['ADMIN', 'OPERATOR', 'FINANCE', 'MERCHANT'] },
  { index: '/settlement', label: '收益结算', roles: ['ADMIN', 'OPERATOR', 'FINANCE', 'MERCHANT'] },
  { index: '/transfer', label: '库存调拨', roles: ['ADMIN', 'OPERATOR', 'INSPECTOR'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
