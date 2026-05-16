<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 130</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>温室大棚物联网控制与病害预警系统</strong><span>温室档案、环境传感、远程控制、灌溉施肥、病虫害预警和采收维护一体化管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'GROWER', 'TECHNICIAN', 'MANAGER'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/greenhouse', label: '温室档案', roles: ['ADMIN', 'GROWER', 'TECHNICIAN', 'MANAGER'] },
  { index: '/crop', label: '作物批次', roles: ['ADMIN', 'GROWER', 'MANAGER'] },
  { index: '/sensor', label: '环境传感器', roles: ['ADMIN', 'TECHNICIAN', 'MANAGER'] },
  { index: '/reading', label: '环境读数', roles: ['ADMIN', 'GROWER', 'TECHNICIAN', 'MANAGER'] },
  { index: '/irrigation', label: '灌溉任务', roles: ['ADMIN', 'GROWER', 'MANAGER'] },
  { index: '/fertilizer', label: '施肥计划', roles: ['ADMIN', 'GROWER', 'MANAGER'] },
  { index: '/pest', label: '虫害预警', roles: ['ADMIN', 'GROWER', 'TECHNICIAN', 'MANAGER'] },
  { index: '/diagnosis', label: '病害诊断', roles: ['ADMIN', 'GROWER', 'TECHNICIAN', 'MANAGER'] },
  { index: '/device', label: '控制设备', roles: ['ADMIN', 'TECHNICIAN', 'MANAGER'] },
  { index: '/command', label: '远程指令', roles: ['ADMIN', 'TECHNICIAN', 'MANAGER'] },
  { index: '/harvest', label: '采收记录', roles: ['ADMIN', 'GROWER', 'MANAGER'] },
  { index: '/ticket', label: '维护工单', roles: ['ADMIN', 'TECHNICIAN', 'MANAGER'] },
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
