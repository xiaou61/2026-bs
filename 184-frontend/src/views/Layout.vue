<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">月租停车 184</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>停车场月租合同续费与异常占位管理系统</strong><span>月租合同、续费缴费、占位巡检、异常处理闭环协同</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'PARKING', 'FINANCE', 'PATROL', 'TENANT', 'SERVICE'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/lot', label: '停车场档案', roles: ['ADMIN', 'PARKING', 'PATROL'] },
  { index: '/space', label: '车位档案', roles: ['ADMIN', 'PARKING', 'PATROL'] },
  { index: '/tenant', label: '月租车主', roles: ['ADMIN', 'PARKING', 'TENANT', 'SERVICE'] },
  { index: '/contract', label: '月租合同', roles: ['ADMIN', 'PARKING', 'FINANCE', 'TENANT', 'SERVICE'] },
  { index: '/reminder', label: '续费提醒', roles: ['ADMIN', 'PARKING', 'FINANCE', 'TENANT', 'SERVICE'] },
  { index: '/payment', label: '续费缴费', roles: ['ADMIN', 'PARKING', 'FINANCE', 'TENANT'] },
  { index: '/vehicle', label: '车辆绑定', roles: ['ADMIN', 'PARKING', 'PATROL', 'TENANT'] },
  { index: '/check', label: '占位巡检', roles: ['ADMIN', 'PARKING', 'PATROL'] },
  { index: '/abnormal', label: '异常占位', roles: ['ADMIN', 'PARKING', 'PATROL', 'TENANT', 'SERVICE'] },
  { index: '/handling', label: '违规处理', roles: ['ADMIN', 'PARKING', 'PATROL', 'TENANT', 'SERVICE'] },
  { index: '/ticket', label: '客服工单', roles: ['ADMIN', 'PARKING', 'TENANT', 'SERVICE'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
