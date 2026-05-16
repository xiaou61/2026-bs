<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 136</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>导师课题双选与开题过程管理系统</strong><span>课题发布、志愿申请、双选确认、任务书下达、开题答辩和节点提醒一体化管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'TEACHER', 'STUDENT', 'AFFAIRS'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/topic', label: '课题发布', roles: ['ADMIN', 'TEACHER', 'AFFAIRS'] },
  { index: '/teacher', label: '导师档案', roles: ['ADMIN', 'TEACHER', 'AFFAIRS'] },
  { index: '/student', label: '学生档案', roles: ['ADMIN', 'STUDENT', 'AFFAIRS'] },
  { index: '/application', label: '课题申请', roles: ['ADMIN', 'TEACHER', 'STUDENT', 'AFFAIRS'] },
  { index: '/review', label: '双选审核', roles: ['ADMIN', 'TEACHER', 'AFFAIRS'] },
  { index: '/selection', label: '双选确认', roles: ['ADMIN', 'TEACHER', 'AFFAIRS'] },
  { index: '/taskbook', label: '任务书下达', roles: ['ADMIN', 'TEACHER', 'AFFAIRS'] },
  { index: '/proposal', label: '开题材料', roles: ['ADMIN', 'TEACHER', 'STUDENT', 'AFFAIRS'] },
  { index: '/defense', label: '开题答辩', roles: ['ADMIN', 'TEACHER', 'AFFAIRS'] },
  { index: '/midterm', label: '中期检查', roles: ['ADMIN', 'TEACHER', 'AFFAIRS'] },
  { index: '/guidance', label: '指导记录', roles: ['ADMIN', 'TEACHER', 'AFFAIRS'] },
  { index: '/notice', label: '节点通知', roles: ['ADMIN', 'TEACHER', 'STUDENT', 'AFFAIRS'] },
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


