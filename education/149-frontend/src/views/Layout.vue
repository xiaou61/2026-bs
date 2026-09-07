<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 149</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>高校实验设备共享预约与违规使用追踪系统</strong><span>面向高校实验设备管理的共享预约、借用审批、违规追踪和维护复核一体化管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'TEACHER', 'STUDENT', 'MANAGER'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/asset', label: '设备档案', roles: ['ADMIN', 'TEACHER', 'MANAGER'] },
  { index: '/lab', label: '实验室档案', roles: ['ADMIN', 'TEACHER', 'MANAGER'] },
  { index: '/category', label: '设备分类', roles: ['ADMIN', 'TEACHER', 'MANAGER'] },
  { index: '/borrow-user', label: '设备借用人', roles: ['ADMIN', 'TEACHER', 'MANAGER'] },
  { index: '/reservation', label: '预约申请', roles: ['ADMIN', 'TEACHER', 'STUDENT', 'MANAGER'] },
  { index: '/borrow-record', label: '借用记录', roles: ['ADMIN', 'TEACHER', 'MANAGER'] },
  { index: '/usage', label: '使用登记', roles: ['ADMIN', 'TEACHER', 'MANAGER'] },
  { index: '/violation', label: '违规记录', roles: ['ADMIN', 'TEACHER', 'MANAGER'] },
  { index: '/maintenance', label: '维修工单', roles: ['ADMIN', 'MANAGER'] },
  { index: '/return-confirm', label: '归还确认', roles: ['ADMIN', 'TEACHER', 'MANAGER'] },
  { index: '/inspection', label: '巡检计划', roles: ['ADMIN', 'TEACHER', 'MANAGER'] },
  { index: '/notice', label: '通知公告', roles: ['ADMIN', 'TEACHER', 'STUDENT', 'MANAGER'] },
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











