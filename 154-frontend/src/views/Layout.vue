<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">宠物医院 154</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>宠物医院接诊排班与疫苗随访管理系统</strong><span>接诊建档、排班预约、疫苗提醒、随访记录一体化管理</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'RECEPTION', 'DOCTOR', 'NURSE', 'OWNER'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/owner', label: '宠主档案', roles: ['ADMIN', 'RECEPTION', 'OWNER'] },
  { index: '/pet', label: '宠物档案', roles: ['ADMIN', 'RECEPTION', 'DOCTOR', 'OWNER'] },
  { index: '/doctor', label: '医生档案', roles: ['ADMIN', 'RECEPTION', 'DOCTOR'] },
  { index: '/schedule', label: '接诊排班', roles: ['ADMIN', 'RECEPTION', 'DOCTOR'] },
  { index: '/appointment', label: '接诊预约', roles: ['ADMIN', 'RECEPTION', 'DOCTOR', 'OWNER'] },
  { index: '/visit', label: '接诊记录', roles: ['ADMIN', 'DOCTOR', 'NURSE'] },
  { index: '/vaccine-plan', label: '疫苗计划', roles: ['ADMIN', 'DOCTOR', 'NURSE', 'OWNER'] },
  { index: '/vaccine-record', label: '疫苗接种', roles: ['ADMIN', 'DOCTOR', 'NURSE'] },
  { index: '/follow-up', label: '随访记录', roles: ['ADMIN', 'DOCTOR', 'NURSE', 'OWNER'] },
  { index: '/medicine', label: '药品库存', roles: ['ADMIN', 'DOCTOR', 'NURSE'] },
  { index: '/billing', label: '费用结算', roles: ['ADMIN', 'RECEPTION', 'OWNER'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN', 'RECEPTION'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
