<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 147</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>校园心理咨询预约与危机干预管理系统</strong><span>面向校园心理健康服务的预约接待、个案跟踪、危机响应和家校协同一体化管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'TEACHER', 'STUDENT', 'COUNSELOR'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/case', label: '咨询个案', roles: ['ADMIN', 'TEACHER', 'COUNSELOR'] },
  { index: '/room', label: '咨询室管理', roles: ['ADMIN', 'TEACHER', 'COUNSELOR'] },
  { index: '/student', label: '学生档案', roles: ['ADMIN', 'TEACHER', 'COUNSELOR'] },
  { index: '/duty', label: '值班安排', roles: ['ADMIN', 'TEACHER', 'COUNSELOR'] },
  { index: '/appointment', label: '预约申请', roles: ['ADMIN', 'TEACHER', 'STUDENT', 'COUNSELOR'] },
  { index: '/record', label: '咨询记录', roles: ['ADMIN', 'TEACHER', 'COUNSELOR'] },
  { index: '/questionnaire', label: '测评问卷', roles: ['ADMIN', 'TEACHER', 'STUDENT'] },
  { index: '/risk', label: '风险评估', roles: ['ADMIN', 'TEACHER', 'COUNSELOR'] },
  { index: '/intervention', label: '危机干预', roles: ['ADMIN', 'TEACHER', 'COUNSELOR'] },
  { index: '/family', label: '家校沟通', roles: ['ADMIN', 'TEACHER', 'COUNSELOR'] },
  { index: '/followup', label: '随访计划', roles: ['ADMIN', 'TEACHER', 'COUNSELOR'] },
  { index: '/notice', label: '通知公告', roles: ['ADMIN', 'TEACHER', 'STUDENT', 'COUNSELOR'] },
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










