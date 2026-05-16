<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">访客门禁 165</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>企业访客预约登记与门禁联动管理系统</strong><span>访客预约、访问审批、二维码通行、门禁联动、轨迹留痕一体化协同</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'RECEPTION', 'EMPLOYEE', 'SECURITY', 'VISITOR', 'MANAGER'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/zone', label: '楼宇区域', roles: ['ADMIN', 'RECEPTION', 'SECURITY', 'MANAGER'] },
  { index: '/host', label: '被访员工', roles: ['ADMIN', 'RECEPTION', 'EMPLOYEE', 'MANAGER'] },
  { index: '/visitor', label: '访客档案', roles: ['ADMIN', 'RECEPTION', 'SECURITY', 'EMPLOYEE', 'VISITOR'] },
  { index: '/appointment', label: '访客预约', roles: ['ADMIN', 'RECEPTION', 'EMPLOYEE', 'VISITOR', 'MANAGER'] },
  { index: '/approval', label: '访问审批', roles: ['ADMIN', 'RECEPTION', 'EMPLOYEE', 'MANAGER'] },
  { index: '/qrcode', label: '二维码通行', roles: ['ADMIN', 'RECEPTION', 'SECURITY', 'VISITOR'] },
  { index: '/gate', label: '门禁设备', roles: ['ADMIN', 'SECURITY', 'MANAGER'] },
  { index: '/linkage', label: '门禁联动', roles: ['ADMIN', 'RECEPTION', 'SECURITY', 'MANAGER'] },
  { index: '/entry', label: '入园登记', roles: ['ADMIN', 'RECEPTION', 'SECURITY', 'VISITOR', 'EMPLOYEE'] },
  { index: '/trail', label: '轨迹留痕', roles: ['ADMIN', 'SECURITY', 'EMPLOYEE', 'MANAGER'] },
  { index: '/alert', label: '异常告警', roles: ['ADMIN', 'RECEPTION', 'SECURITY', 'MANAGER'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN', 'MANAGER'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
