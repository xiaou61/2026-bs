<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 146</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>食品安全抽检任务与结果公示平台</strong><span>面向食品安全监管场景的计划抽检、样品检测、风险预警、处置联动和结果公示一体化管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'INSPECTOR', 'MERCHANT', 'REVIEWER'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/plan', label: '抽检计划', roles: ['ADMIN', 'INSPECTOR', 'REVIEWER'] },
  { index: '/food', label: '食品档案', roles: ['ADMIN', 'INSPECTOR', 'MERCHANT', 'REVIEWER'] },
  { index: '/merchant', label: '商户档案', roles: ['ADMIN', 'INSPECTOR', 'MERCHANT', 'REVIEWER'] },
  { index: '/task', label: '抽样任务', roles: ['ADMIN', 'INSPECTOR', 'REVIEWER'] },
  { index: '/agency', label: '检测机构', roles: ['ADMIN', 'INSPECTOR', 'REVIEWER'] },
  { index: '/sample', label: '样品信息', roles: ['ADMIN', 'INSPECTOR', 'REVIEWER'] },
  { index: '/result', label: '检测结果', roles: ['ADMIN', 'INSPECTOR', 'REVIEWER'] },
  { index: '/recheck', label: '复检申请', roles: ['ADMIN', 'MERCHANT', 'REVIEWER'] },
  { index: '/disposal', label: '处置记录', roles: ['ADMIN', 'REVIEWER'] },
  { index: '/report', label: '结果公示', roles: ['ADMIN', 'INSPECTOR', 'MERCHANT', 'REVIEWER'] },
  { index: '/warning', label: '风险预警', roles: ['ADMIN', 'REVIEWER'] },
  { index: '/notice', label: '通知公告', roles: ['ADMIN', 'INSPECTOR', 'MERCHANT', 'REVIEWER'] },
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
