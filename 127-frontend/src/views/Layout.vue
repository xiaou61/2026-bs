<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 127</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>企业碳排放核算与减排任务管理系统</strong><span>排放因子、能源消耗、碳核算、减排任务、核查报告和预警处置一体化管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'ACCOUNTANT', 'AUDITOR', 'MANAGER'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/company', label: '企业档案', roles: ['ADMIN', 'ACCOUNTANT', 'AUDITOR', 'MANAGER'] },
  { index: '/factor', label: '排放因子', roles: ['ADMIN', 'ACCOUNTANT', 'AUDITOR'] },
  { index: '/period', label: '核算周期', roles: ['ADMIN', 'ACCOUNTANT', 'AUDITOR', 'MANAGER'] },
  { index: '/consumption', label: '能源消耗', roles: ['ADMIN', 'ACCOUNTANT', 'AUDITOR'] },
  { index: '/emission', label: '排放记录', roles: ['ADMIN', 'ACCOUNTANT', 'AUDITOR', 'MANAGER'] },
  { index: '/task', label: '减排任务', roles: ['ADMIN', 'ACCOUNTANT', 'AUDITOR', 'MANAGER'] },
  { index: '/measure', label: '减排措施', roles: ['ADMIN', 'ACCOUNTANT', 'AUDITOR', 'MANAGER'] },
  { index: '/quota', label: '碳配额', roles: ['ADMIN', 'ACCOUNTANT', 'AUDITOR', 'MANAGER'] },
  { index: '/report', label: '核查报告', roles: ['ADMIN', 'ACCOUNTANT', 'AUDITOR', 'MANAGER'] },
  { index: '/attachment', label: '佐证附件', roles: ['ADMIN', 'ACCOUNTANT', 'AUDITOR'] },
  { index: '/rule', label: '预警规则', roles: ['ADMIN', 'ACCOUNTANT', 'MANAGER'] },
  { index: '/warning', label: '碳排预警', roles: ['ADMIN', 'ACCOUNTANT', 'AUDITOR', 'MANAGER'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN'] }
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
