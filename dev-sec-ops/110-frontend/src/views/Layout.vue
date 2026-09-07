<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PRIVACY 110</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>个人数据隐私授权与访问审计平台</strong><span>授权、访问、撤销、预警、审计闭环管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'PRIVACY', 'DATAUSER', 'AUDITOR'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/subject', label: '数据主体', roles: ['ADMIN', 'PRIVACY', 'DATAUSER', 'AUDITOR'] },
  { index: '/data-item', label: '个人数据项', roles: ['ADMIN', 'PRIVACY', 'DATAUSER', 'AUDITOR'] },
  { index: '/purpose', label: '授权目的', roles: ['ADMIN', 'PRIVACY', 'AUDITOR'] },
  { index: '/policy', label: '授权策略', roles: ['ADMIN', 'PRIVACY', 'AUDITOR'] },
  { index: '/authorization', label: '授权记录', roles: ['ADMIN', 'PRIVACY', 'AUDITOR'] },
  { index: '/scope', label: '授权范围', roles: ['ADMIN', 'PRIVACY', 'AUDITOR'] },
  { index: '/access-application', label: '访问申请', roles: ['ADMIN', 'PRIVACY', 'DATAUSER', 'AUDITOR'] },
  { index: '/grant', label: '访问授权', roles: ['ADMIN', 'PRIVACY', 'DATAUSER', 'AUDITOR'] },
  { index: '/access-log', label: '访问日志', roles: ['ADMIN', 'PRIVACY', 'DATAUSER', 'AUDITOR'] },
  { index: '/revocation', label: '撤销申请', roles: ['ADMIN', 'PRIVACY', 'DATAUSER', 'AUDITOR'] },
  { index: '/risk-warning', label: '风险预警', roles: ['ADMIN', 'PRIVACY', 'AUDITOR'] },
  { index: '/audit-report', label: '审计报告', roles: ['ADMIN', 'PRIVACY', 'AUDITOR'] },
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
