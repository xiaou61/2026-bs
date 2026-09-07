<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">CLOUD COST 108</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>云原生成本分析与资源优化平台</strong><span>账单、预算、分摊、优化和异常闭环管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'FINOPS', 'MANAGER'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/account', label: '云账号', roles: ['ADMIN', 'FINOPS', 'DEVOPS', 'MANAGER'] },
  { index: '/namespace', label: '资源命名空间', roles: ['ADMIN', 'FINOPS', 'DEVOPS', 'MANAGER'] },
  { index: '/bill', label: '成本账单', roles: ['ADMIN', 'FINOPS', 'MANAGER'] },
  { index: '/cost-item', label: '成本明细', roles: ['ADMIN', 'FINOPS', 'MANAGER'] },
  { index: '/budget', label: '预算策略', roles: ['ADMIN', 'FINOPS', 'MANAGER'] },
  { index: '/allocation', label: '成本分摊', roles: ['ADMIN', 'FINOPS', 'MANAGER'] },
  { index: '/idle-resource', label: '闲置资源', roles: ['ADMIN', 'DEVOPS', 'MANAGER'] },
  { index: '/optimization-rule', label: '优化规则', roles: ['ADMIN', 'DEVOPS', 'MANAGER'] },
  { index: '/advice', label: '优化建议', roles: ['ADMIN', 'DEVOPS', 'MANAGER'] },
  { index: '/saving-plan', label: '节省计划', roles: ['ADMIN', 'FINOPS', 'MANAGER'] },
  { index: '/anomaly', label: '成本异常', roles: ['ADMIN', 'DEVOPS', 'MANAGER'] },
  { index: '/report', label: '报告快照', roles: ['ADMIN', 'FINOPS', 'MANAGER'] },
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
