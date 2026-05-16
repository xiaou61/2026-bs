<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 132</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>医疗器械借用消毒与追踪管理系统</strong><span>器械档案、借用申请、归还记录、消毒批次、二维码追踪和风险预警一体化管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'NURSE', 'STERILIZER', 'MANAGER'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/device', label: '器械档案', roles: ['ADMIN', 'NURSE', 'STERILIZER', 'MANAGER'] },
  { index: '/category', label: '器械分类', roles: ['ADMIN', 'NURSE', 'STERILIZER', 'MANAGER'] },
  { index: '/department', label: '科室信息', roles: ['ADMIN', 'NURSE', 'STERILIZER', 'MANAGER'] },
  { index: '/request', label: '借用申请', roles: ['ADMIN', 'NURSE', 'MANAGER'] },
  { index: '/borrow', label: '借用记录', roles: ['ADMIN', 'NURSE', 'MANAGER'] },
  { index: '/return', label: '归还记录', roles: ['ADMIN', 'NURSE', 'STERILIZER', 'MANAGER'] },
  { index: '/batch', label: '消毒批次', roles: ['ADMIN', 'STERILIZER', 'MANAGER'] },
  { index: '/sterilization', label: '消毒记录', roles: ['ADMIN', 'NURSE', 'STERILIZER', 'MANAGER'] },
  { index: '/trace', label: '二维码追踪', roles: ['ADMIN', 'NURSE', 'STERILIZER', 'MANAGER'] },
  { index: '/maintenance', label: '维护记录', roles: ['ADMIN', 'MANAGER'] },
  { index: '/inspection', label: '巡检任务', roles: ['ADMIN', 'MANAGER'] },
  { index: '/alert', label: '风险预警', roles: ['ADMIN', 'NURSE', 'STERILIZER', 'MANAGER'] },
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
