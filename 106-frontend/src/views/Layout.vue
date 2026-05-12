<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">DEVOPS 106</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>DevOps 发布审批与回滚管理系统</strong><span>发布计划、审批、部署、回滚闭环管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'RELEASE', 'AUDITOR'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/environment', label: '发布环境', roles: ['ADMIN', 'RELEASE', 'OPS', 'AUDITOR'] },
  { index: '/service', label: '应用服务', roles: ['ADMIN', 'RELEASE', 'OPS', 'AUDITOR'] },
  { index: '/pipeline', label: '流水线', roles: ['ADMIN', 'RELEASE', 'OPS', 'AUDITOR'] },
  { index: '/release-plan', label: '发布计划', roles: ['ADMIN', 'RELEASE', 'OPS', 'AUDITOR'] },
  { index: '/release-order', label: '发布单', roles: ['ADMIN', 'RELEASE', 'OPS', 'AUDITOR'] },
  { index: '/approval-flow', label: '审批流程', roles: ['ADMIN', 'RELEASE', 'AUDITOR'] },
  { index: '/approval-record', label: '审批记录', roles: ['ADMIN', 'RELEASE', 'AUDITOR'] },
  { index: '/artifact', label: '版本制品', roles: ['ADMIN', 'RELEASE', 'OPS', 'AUDITOR'] },
  { index: '/deploy-task', label: '部署任务', roles: ['ADMIN', 'RELEASE', 'OPS', 'AUDITOR'] },
  { index: '/rollback-plan', label: '回滚预案', roles: ['ADMIN', 'RELEASE', 'OPS', 'AUDITOR'] },
  { index: '/rollback-record', label: '回滚记录', roles: ['ADMIN', 'RELEASE', 'OPS', 'AUDITOR'] },
  { index: '/checklist', label: '变更检查', roles: ['ADMIN', 'RELEASE', 'OPS', 'AUDITOR'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN', 'RELEASE', 'AUDITOR'] }
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
