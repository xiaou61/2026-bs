<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">ZERO TRUST 112</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>零信任设备准入与访问控制管理系统</strong><span>设备准入、风险评估、访问策略、审计事件闭环管理</span></div>
        <div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div>
      </el-header>
      <el-main><router-view /></el-main>
    </el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'SECURITY', 'NETWORK', 'AUDITOR'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/device', label: '设备资产', roles: ['ADMIN', 'SECURITY', 'NETWORK', 'AUDITOR'] },
  { index: '/employee', label: '员工账号', roles: ['ADMIN', 'SECURITY', 'NETWORK', 'AUDITOR'] },
  { index: '/idp', label: '身份源', roles: ['ADMIN', 'NETWORK'] },
  { index: '/risk-model', label: '风险模型', roles: ['ADMIN', 'SECURITY', 'AUDITOR'] },
  { index: '/assessment', label: '风险评估', roles: ['ADMIN', 'SECURITY', 'AUDITOR'] },
  { index: '/policy', label: '访问策略', roles: ['ADMIN', 'SECURITY', 'AUDITOR'] },
  { index: '/rule', label: '策略规则', roles: ['ADMIN', 'SECURITY', 'AUDITOR'] },
  { index: '/application', label: '准入申请', roles: ['ADMIN', 'NETWORK', 'AUDITOR'] },
  { index: '/session', label: '访问会话', roles: ['ADMIN', 'NETWORK', 'AUDITOR'] },
  { index: '/segment', label: '网络分区', roles: ['ADMIN', 'NETWORK'] },
  { index: '/certificate', label: '设备证书', roles: ['ADMIN', 'NETWORK'] },
  { index: '/audit-event', label: '审计事件', roles: ['ADMIN', 'SECURITY', 'NETWORK', 'AUDITOR'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN', 'AUDITOR'] }
]
const visibleMenus = computed(() => {
  const role = userStore.user?.role
  return menus.filter(item => item.roles.includes(role))
})
const handleLogout = async () => {
  await logout().catch(() => null)
  userStore.clear()
  router.push('/login')
}
</script>
