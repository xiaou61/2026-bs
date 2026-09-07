<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">应急物资 171</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>应急物资储备盘点与调拨审批平台</strong><span>储备台账、库存盘点、调拨审批、出库调度一体化协同</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'WAREHOUSE', 'CHECKER', 'APPLICANT', 'DISPATCH', 'AUDITOR'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/warehouse', label: '储备仓库', roles: ['ADMIN', 'WAREHOUSE', 'DISPATCH', 'AUDITOR'] },
  { index: '/category', label: '物资分类', roles: ['ADMIN', 'WAREHOUSE', 'DISPATCH', 'AUDITOR'] },
  { index: '/material', label: '物资台账', roles: ['ADMIN', 'WAREHOUSE', 'CHECKER', 'DISPATCH', 'APPLICANT', 'AUDITOR'] },
  { index: '/batch', label: '库存批次', roles: ['ADMIN', 'WAREHOUSE', 'CHECKER', 'DISPATCH', 'AUDITOR'] },
  { index: '/check', label: '库存盘点', roles: ['ADMIN', 'WAREHOUSE', 'CHECKER', 'AUDITOR'] },
  { index: '/difference', label: '盘点差异', roles: ['ADMIN', 'WAREHOUSE', 'CHECKER', 'AUDITOR'] },
  { index: '/requisition', label: '申领工单', roles: ['ADMIN', 'APPLICANT', 'DISPATCH', 'AUDITOR', 'WAREHOUSE'] },
  { index: '/approval', label: '调拨审批', roles: ['ADMIN', 'DISPATCH', 'AUDITOR', 'WAREHOUSE', 'APPLICANT'] },
  { index: '/dispatch', label: '调度任务', roles: ['ADMIN', 'DISPATCH', 'WAREHOUSE', 'APPLICANT', 'AUDITOR'] },
  { index: '/outbound', label: '出库记录', roles: ['ADMIN', 'WAREHOUSE', 'DISPATCH', 'AUDITOR'] },
  { index: '/warning', label: '库存预警', roles: ['ADMIN', 'WAREHOUSE', 'CHECKER', 'DISPATCH', 'AUDITOR'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN', 'AUDITOR'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
