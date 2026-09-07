<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 139</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>企业培训学习路径与能力画像系统</strong><span>面向企业培训管理的课程学习、路径规划、能力画像、认证记录和学习提醒一体化管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'TRAINER', 'EMPLOYEE', 'MANAGER'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/program', label: '培训项目', roles: ['ADMIN', 'TRAINER', 'EMPLOYEE', 'MANAGER'] },
  { index: '/course', label: '课程目录', roles: ['ADMIN', 'TRAINER', 'EMPLOYEE', 'MANAGER'] },
  { index: '/learner', label: '学员档案', roles: ['ADMIN', 'TRAINER', 'EMPLOYEE', 'MANAGER'] },
  { index: '/path', label: '学习路径', roles: ['ADMIN', 'TRAINER', 'EMPLOYEE', 'MANAGER'] },
  { index: '/task', label: '学习任务', roles: ['ADMIN', 'TRAINER', 'EMPLOYEE', 'MANAGER'] },
  { index: '/enroll', label: '课程报名', roles: ['ADMIN', 'TRAINER', 'EMPLOYEE', 'MANAGER'] },
  { index: '/exam', label: '考核考试', roles: ['ADMIN', 'TRAINER', 'EMPLOYEE', 'MANAGER'] },
  { index: '/score', label: '考试成绩', roles: ['ADMIN', 'TRAINER', 'EMPLOYEE', 'MANAGER'] },
  { index: '/skill', label: '技能标签', roles: ['ADMIN', 'TRAINER', 'EMPLOYEE', 'MANAGER'] },
  { index: '/profile', label: '能力画像', roles: ['ADMIN', 'TRAINER', 'EMPLOYEE', 'MANAGER'] },
  { index: '/cert', label: '认证记录', roles: ['ADMIN', 'TRAINER', 'EMPLOYEE', 'MANAGER'] },
  { index: '/reminder', label: '学习提醒', roles: ['ADMIN', 'TRAINER', 'EMPLOYEE', 'MANAGER'] },
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






