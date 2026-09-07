<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">慢病随访 174</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>社区慢病档案随访与服药依从性管理系统</strong><span>慢病建档、随访计划、服药提醒、风险分层一体化协同</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'CENTER', 'DOCTOR', 'NURSE', 'PHARMACIST', 'RESIDENT'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/clinic', label: '社区站点', roles: ['ADMIN', 'CENTER', 'DOCTOR', 'NURSE'] },
  { index: '/patient', label: '患者档案', roles: ['ADMIN', 'CENTER', 'DOCTOR', 'NURSE', 'RESIDENT'] },
  { index: '/team', label: '医护团队', roles: ['ADMIN', 'CENTER', 'DOCTOR', 'NURSE'] },
  { index: '/disease', label: '慢病档案', roles: ['ADMIN', 'CENTER', 'DOCTOR', 'NURSE', 'RESIDENT'] },
  { index: '/plan', label: '随访计划', roles: ['ADMIN', 'CENTER', 'DOCTOR', 'NURSE', 'RESIDENT'] },
  { index: '/followup', label: '随访记录', roles: ['ADMIN', 'CENTER', 'DOCTOR', 'NURSE', 'RESIDENT'] },
  { index: '/medication', label: '用药方案', roles: ['ADMIN', 'CENTER', 'DOCTOR', 'PHARMACIST', 'RESIDENT'] },
  { index: '/adherence', label: '服药打卡', roles: ['ADMIN', 'CENTER', 'NURSE', 'PHARMACIST', 'RESIDENT'] },
  { index: '/indicator', label: '健康指标', roles: ['ADMIN', 'CENTER', 'DOCTOR', 'NURSE', 'RESIDENT'] },
  { index: '/risk', label: '风险分层', roles: ['ADMIN', 'CENTER', 'DOCTOR', 'NURSE'] },
  { index: '/notice', label: '提醒通知', roles: ['ADMIN', 'CENTER', 'DOCTOR', 'NURSE', 'PHARMACIST', 'RESIDENT'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
