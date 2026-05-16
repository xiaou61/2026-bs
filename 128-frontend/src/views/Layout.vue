<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 128</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>ESG 数据填报与可视化报告系统</strong><span>指标库、企业填报、佐证材料、审核任务、ESG评分和报告导出一体化管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'EDITOR', 'REVIEWER', 'ESG_MANAGER'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/indicator', label: '指标库', roles: ['ADMIN', 'EDITOR', 'REVIEWER', 'ESG_MANAGER'] },
  { index: '/template', label: '披露模板', roles: ['ADMIN', 'EDITOR', 'REVIEWER', 'ESG_MANAGER'] },
  { index: '/period', label: '报告周期', roles: ['ADMIN', 'EDITOR', 'REVIEWER', 'ESG_MANAGER'] },
  { index: '/submission', label: '企业填报', roles: ['ADMIN', 'EDITOR', 'REVIEWER', 'ESG_MANAGER'] },
  { index: '/data', label: '指标数据', roles: ['ADMIN', 'EDITOR', 'REVIEWER', 'ESG_MANAGER'] },
  { index: '/evidence', label: '佐证材料', roles: ['ADMIN', 'EDITOR', 'REVIEWER'] },
  { index: '/review', label: '审核任务', roles: ['ADMIN', 'EDITOR', 'REVIEWER', 'ESG_MANAGER'] },
  { index: '/model', label: '评分模型', roles: ['ADMIN', 'EDITOR', 'REVIEWER', 'ESG_MANAGER'] },
  { index: '/score', label: 'ESG评分', roles: ['ADMIN', 'EDITOR', 'REVIEWER', 'ESG_MANAGER'] },
  { index: '/improvement', label: '改进任务', roles: ['ADMIN', 'EDITOR', 'REVIEWER', 'ESG_MANAGER'] },
  { index: '/feedback', label: '利益相关方反馈', roles: ['ADMIN', 'EDITOR', 'REVIEWER', 'ESG_MANAGER'] },
  { index: '/export', label: '报告导出', roles: ['ADMIN', 'EDITOR', 'REVIEWER', 'ESG_MANAGER'] },
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
