<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">CLOUD 107</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>云服务器资产监控与告警平台</strong><span>主机资产、指标采集、告警规则、处置工单闭环管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'SRE', 'MANAGER'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/region', label: '云区域', roles: ['ADMIN', 'OPS', 'SRE', 'MANAGER'] },
  { index: '/asset', label: '主机资产', roles: ['ADMIN', 'OPS', 'SRE', 'MANAGER'] },
  { index: '/group', label: '资源分组', roles: ['ADMIN', 'OPS', 'SRE', 'MANAGER'] },
  { index: '/metric', label: '指标定义', roles: ['ADMIN', 'OPS', 'SRE', 'MANAGER'] },
  { index: '/sample', label: '指标采样', roles: ['ADMIN', 'OPS', 'SRE', 'MANAGER'] },
  { index: '/rule', label: '告警规则', roles: ['ADMIN', 'OPS', 'SRE', 'MANAGER'] },
  { index: '/event', label: '告警事件', roles: ['ADMIN', 'OPS', 'SRE', 'MANAGER'] },
  { index: '/notify', label: '通知记录', roles: ['ADMIN', 'OPS', 'SRE', 'MANAGER'] },
  { index: '/ticket', label: '处置工单', roles: ['ADMIN', 'OPS', 'SRE', 'MANAGER'] },
  { index: '/maintenance', label: '维护窗口', roles: ['ADMIN', 'OPS', 'SRE', 'MANAGER'] },
  { index: '/capacity', label: '容量规划', roles: ['ADMIN', 'OPS', 'SRE', 'MANAGER'] },
  { index: '/widget', label: '看板组件', roles: ['ADMIN', 'SRE', 'MANAGER'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN', 'SRE', 'MANAGER'] }
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
