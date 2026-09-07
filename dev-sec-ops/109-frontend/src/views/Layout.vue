<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">DATA MASKING 109</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>数据脱敏与敏感信息识别平台</strong><span>识别、脱敏、审批、告警闭环管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'SECURITY', 'OWNER', 'AUDITOR'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/source', label: '数据源配置', roles: ['ADMIN', 'SECURITY', 'OWNER', 'AUDITOR'] },
  { index: '/dataset', label: '数据集目录', roles: ['ADMIN', 'SECURITY', 'OWNER', 'AUDITOR'] },
  { index: '/rule', label: '敏感规则', roles: ['ADMIN', 'SECURITY', 'AUDITOR'] },
  { index: '/recognition-task', label: '识别任务', roles: ['ADMIN', 'SECURITY', 'AUDITOR'] },
  { index: '/recognition-result', label: '识别结果', roles: ['ADMIN', 'SECURITY', 'OWNER', 'AUDITOR'] },
  { index: '/strategy', label: '脱敏策略', roles: ['ADMIN', 'SECURITY', 'AUDITOR'] },
  { index: '/masking-task', label: '脱敏任务', roles: ['ADMIN', 'SECURITY', 'AUDITOR'] },
  { index: '/masking-record', label: '脱敏记录', roles: ['ADMIN', 'SECURITY', 'OWNER', 'AUDITOR'] },
  { index: '/lineage', label: '字段血缘', roles: ['ADMIN', 'OWNER', 'AUDITOR'] },
  { index: '/access-request', label: '访问申请', roles: ['ADMIN', 'SECURITY', 'OWNER', 'AUDITOR'] },
  { index: '/export-approval', label: '导出审批', roles: ['ADMIN', 'SECURITY', 'OWNER', 'AUDITOR'] },
  { index: '/risk-alert', label: '风险告警', roles: ['ADMIN', 'SECURITY', 'OWNER', 'AUDITOR'] },
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
