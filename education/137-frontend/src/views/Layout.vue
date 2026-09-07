<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 137</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>大学生创新创业项目孵化管理平台</strong><span>面向创新创业孵化的项目申报、导师辅导、路演评分、经费跟踪和成果展示一体化管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'MENTOR', 'STUDENT', 'JUDGE'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/project', label: '孵化项目', roles: ['ADMIN', 'MENTOR', 'STUDENT', 'JUDGE'] },
  { index: '/mentor', label: '导师档案', roles: ['ADMIN', 'MENTOR', 'STUDENT', 'JUDGE'] },
  { index: '/team', label: '团队档案', roles: ['ADMIN', 'MENTOR', 'STUDENT', 'JUDGE'] },
  { index: '/application', label: '项目申报', roles: ['ADMIN', 'MENTOR', 'STUDENT', 'JUDGE'] },
  { index: '/plan', label: '孵化计划', roles: ['ADMIN', 'MENTOR', 'STUDENT', 'JUDGE'] },
  { index: '/coaching', label: '导师辅导', roles: ['ADMIN', 'MENTOR', 'STUDENT', 'JUDGE'] },
  { index: '/roadshow', label: '路演活动', roles: ['ADMIN', 'MENTOR', 'STUDENT', 'JUDGE'] },
  { index: '/score', label: '路演评分', roles: ['ADMIN', 'MENTOR', 'STUDENT', 'JUDGE'] },
  { index: '/funding', label: '经费记录', roles: ['ADMIN', 'MENTOR', 'STUDENT', 'JUDGE'] },
  { index: '/milestone', label: '里程碑任务', roles: ['ADMIN', 'MENTOR', 'STUDENT', 'JUDGE'] },
  { index: '/achievement', label: '成果展示', roles: ['ADMIN', 'MENTOR', 'STUDENT', 'JUDGE'] },
  { index: '/notice', label: '孵化通知', roles: ['ADMIN', 'MENTOR', 'STUDENT', 'JUDGE'] },
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




