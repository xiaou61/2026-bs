<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">口腔门诊 172</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>口腔门诊治疗预约与耗材计费管理系统</strong><span>预约诊疗、治疗记录、耗材使用、费用结算一体化协同</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'RECEPTION', 'DENTIST', 'NURSE', 'CASHIER', 'PATIENT'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/room', label: '诊室档案', roles: ['ADMIN', 'RECEPTION', 'DENTIST', 'NURSE'] },
  { index: '/dentist', label: '医生档案', roles: ['ADMIN', 'RECEPTION', 'DENTIST'] },
  { index: '/patient', label: '患者档案', roles: ['ADMIN', 'RECEPTION', 'DENTIST', 'NURSE', 'PATIENT'] },
  { index: '/treatment', label: '治疗项目', roles: ['ADMIN', 'RECEPTION', 'DENTIST', 'CASHIER'] },
  { index: '/appointment', label: '预约诊疗', roles: ['ADMIN', 'RECEPTION', 'DENTIST', 'NURSE', 'PATIENT'] },
  { index: '/triage', label: '签到分诊', roles: ['ADMIN', 'RECEPTION', 'DENTIST', 'NURSE'] },
  { index: '/treatment-record', label: '治疗记录', roles: ['ADMIN', 'DENTIST', 'NURSE', 'PATIENT', 'CASHIER'] },
  { index: '/consumable', label: '耗材目录', roles: ['ADMIN', 'NURSE', 'DENTIST', 'CASHIER'] },
  { index: '/stock', label: '耗材库存', roles: ['ADMIN', 'NURSE', 'DENTIST', 'CASHIER'] },
  { index: '/usage', label: '耗材使用', roles: ['ADMIN', 'DENTIST', 'NURSE', 'CASHIER'] },
  { index: '/billing', label: '费用结算', roles: ['ADMIN', 'RECEPTION', 'DENTIST', 'CASHIER', 'PATIENT'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
