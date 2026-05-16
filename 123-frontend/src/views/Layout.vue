<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 123</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>城市内涝监测与应急调度平台</strong><span>水位雨量监测、预警规则、应急调度、物资队伍和避险点一体化管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'MONITOR', 'DISPATCHER', 'MANAGER'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/point', label: '水位点位', roles: ['ADMIN', 'MONITOR', 'MANAGER'] },
  { index: '/rainstation', label: '雨量站点', roles: ['ADMIN', 'MONITOR', 'MANAGER'] },
  { index: '/pump', label: '排涝泵站', roles: ['ADMIN', 'MONITOR', 'DISPATCHER', 'MANAGER'] },
  { index: '/waterdata', label: '水位数据', roles: ['ADMIN', 'MONITOR', 'MANAGER'] },
  { index: '/raindata', label: '雨量数据', roles: ['ADMIN', 'MONITOR', 'MANAGER'] },
  { index: '/rule', label: '预警规则', roles: ['ADMIN', 'MONITOR', 'MANAGER'] },
  { index: '/warning', label: '内涝预警', roles: ['ADMIN', 'MONITOR', 'DISPATCHER', 'MANAGER'] },
  { index: '/plan', label: '应急预案', roles: ['ADMIN', 'DISPATCHER', 'MANAGER'] },
  { index: '/task', label: '调度任务', roles: ['ADMIN', 'DISPATCHER', 'MANAGER'] },
  { index: '/team', label: '救援队伍', roles: ['ADMIN', 'DISPATCHER', 'MANAGER'] },
  { index: '/material', label: '物资储备', roles: ['ADMIN', 'DISPATCHER', 'MANAGER'] },
  { index: '/shelter', label: '避险点位', roles: ['ADMIN', 'DISPATCHER', 'MANAGER'] },
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
