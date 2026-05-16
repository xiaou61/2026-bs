<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PHISHING 111</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>网络钓鱼邮件演练与安全意识培训平台</strong><span>邮件演练、点击追踪、课程考试、风险评分闭环管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'SECURITY', 'TRAINER', 'AUDITOR'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/employee', label: '员工档案', roles: ['ADMIN', 'SECURITY', 'TRAINER', 'AUDITOR'] },
  { index: '/department', label: '部门分组', roles: ['ADMIN', 'SECURITY', 'TRAINER', 'AUDITOR'] },
  { index: '/template', label: '邮件模板', roles: ['ADMIN', 'SECURITY', 'AUDITOR'] },
  { index: '/campaign', label: '演练活动', roles: ['ADMIN', 'SECURITY', 'AUDITOR'] },
  { index: '/target', label: '目标名单', roles: ['ADMIN', 'SECURITY', 'AUDITOR'] },
  { index: '/send-record', label: '发送记录', roles: ['ADMIN', 'SECURITY', 'TRAINER', 'AUDITOR'] },
  { index: '/click', label: '点击追踪', roles: ['ADMIN', 'SECURITY', 'TRAINER', 'AUDITOR'] },
  { index: '/course', label: '培训课程', roles: ['ADMIN', 'TRAINER', 'AUDITOR'] },
  { index: '/exam', label: '培训考试', roles: ['ADMIN', 'TRAINER', 'AUDITOR'] },
  { index: '/question', label: '考试题目', roles: ['ADMIN', 'TRAINER', 'AUDITOR'] },
  { index: '/attempt', label: '考试记录', roles: ['ADMIN', 'SECURITY', 'TRAINER', 'AUDITOR'] },
  { index: '/risk-score', label: '风险评分', roles: ['ADMIN', 'SECURITY', 'TRAINER', 'AUDITOR'] },
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
