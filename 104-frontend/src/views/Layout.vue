<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">LICENSE 104</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>开源许可证合规扫描与项目台账系统</strong><span>依赖、许可证、风险、整改、报告闭环治理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'COMPLIANCE', 'AUDITOR'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/team', label: '组织团队', roles: ['ADMIN', 'COMPLIANCE', 'DEVELOPER'] },
  { index: '/repository', label: '项目台账', roles: ['ADMIN', 'COMPLIANCE', 'DEVELOPER', 'AUDITOR'] },
  { index: '/branch', label: '分支版本', roles: ['ADMIN', 'COMPLIANCE', 'DEVELOPER', 'AUDITOR'] },
  { index: '/dependency', label: '依赖台账', roles: ['ADMIN', 'COMPLIANCE', 'DEVELOPER', 'AUDITOR'] },
  { index: '/policy', label: '许可证策略', roles: ['ADMIN', 'COMPLIANCE', 'AUDITOR'] },
  { index: '/baseline', label: '合规基线', roles: ['ADMIN', 'COMPLIANCE', 'DEVELOPER', 'AUDITOR'] },
  { index: '/scan-task', label: '扫描任务', roles: ['ADMIN', 'COMPLIANCE', 'DEVELOPER', 'AUDITOR'] },
  { index: '/scan-result', label: '扫描结果', roles: ['ADMIN', 'COMPLIANCE', 'DEVELOPER', 'AUDITOR'] },
  { index: '/issue', label: '风险问题', roles: ['ADMIN', 'COMPLIANCE', 'DEVELOPER', 'AUDITOR'] },
  { index: '/rectification', label: '整改任务', roles: ['ADMIN', 'COMPLIANCE', 'DEVELOPER', 'AUDITOR'] },
  { index: '/report', label: '审计报告', roles: ['ADMIN', 'COMPLIANCE', 'AUDITOR'] },
  { index: '/approval', label: '审批记录', roles: ['ADMIN', 'COMPLIANCE', 'AUDITOR'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN', 'COMPLIANCE', 'AUDITOR'] }
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
