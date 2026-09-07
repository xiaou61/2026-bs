<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 145</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>城市噪声投诉监测与执法协同平台</strong><span>面向城市噪声治理管理的投诉受理、点位监测、执法联动和整改复测一体化管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'CITIZEN', 'OFFICER', 'SUPERVISOR'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/complaint', label: '投诉工单', roles: ['ADMIN', 'CITIZEN', 'OFFICER', 'SUPERVISOR'] },
  { index: '/site', label: '监测点位', roles: ['ADMIN', 'OFFICER', 'SUPERVISOR'] },
  { index: '/source', label: '噪声源档案', roles: ['ADMIN', 'OFFICER', 'SUPERVISOR'] },
  { index: '/officer', label: '执法人员档案', roles: ['ADMIN', 'OFFICER', 'SUPERVISOR'] },
  { index: '/task', label: '处置任务', roles: ['ADMIN', 'OFFICER', 'SUPERVISOR'] },
  { index: '/patrol', label: '现场巡查', roles: ['ADMIN', 'OFFICER', 'SUPERVISOR'] },
  { index: '/rectify', label: '整改通知', roles: ['ADMIN', 'OFFICER', 'SUPERVISOR'] },
  { index: '/retest', label: '复测记录', roles: ['ADMIN', 'OFFICER', 'SUPERVISOR'] },
  { index: '/penalty', label: '处罚决定', roles: ['ADMIN', 'OFFICER', 'SUPERVISOR'] },
  { index: '/feedback', label: '公众回访', roles: ['ADMIN', 'CITIZEN', 'SUPERVISOR'] },
  { index: '/rule', label: '预警规则', roles: ['ADMIN', 'SUPERVISOR'] },
  { index: '/notice', label: '公告公示', roles: ['ADMIN', 'CITIZEN', 'OFFICER', 'SUPERVISOR'] },
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









