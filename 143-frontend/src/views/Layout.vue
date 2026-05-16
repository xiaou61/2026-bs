<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 143</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>社区公益时间银行互助服务平台</strong><span>面向社区互助服务管理的服务预约、时长存取、公益活动和互助兑换一体化管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'RESIDENT', 'VOLUNTEER', 'MANAGER'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/project', label: '服务项目', roles: ['ADMIN', 'RESIDENT', 'VOLUNTEER', 'MANAGER'] },
  { index: '/category', label: '服务分类', roles: ['ADMIN', 'RESIDENT', 'VOLUNTEER', 'MANAGER'] },
  { index: '/resident', label: '居民档案', roles: ['ADMIN', 'MANAGER'] },
  { index: '/volunteer', label: '志愿者档案', roles: ['ADMIN', 'VOLUNTEER', 'MANAGER'] },
  { index: '/booking', label: '服务预约', roles: ['ADMIN', 'RESIDENT', 'MANAGER'] },
  { index: '/checkin', label: '服务签到', roles: ['ADMIN', 'VOLUNTEER', 'MANAGER'] },
  { index: '/account', label: '时长账户', roles: ['ADMIN', 'MANAGER'] },
  { index: '/exchange', label: '互助兑换', roles: ['ADMIN', 'RESIDENT', 'VOLUNTEER', 'MANAGER'] },
  { index: '/feedback', label: '评价反馈', roles: ['ADMIN', 'RESIDENT', 'VOLUNTEER', 'MANAGER'] },
  { index: '/activity', label: '公益活动', roles: ['ADMIN', 'RESIDENT', 'VOLUNTEER', 'MANAGER'] },
  { index: '/rule', label: '积分规则', roles: ['ADMIN', 'RESIDENT', 'VOLUNTEER', 'MANAGER'] },
  { index: '/notice', label: '站内通知', roles: ['ADMIN', 'RESIDENT', 'VOLUNTEER', 'MANAGER'] },
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
