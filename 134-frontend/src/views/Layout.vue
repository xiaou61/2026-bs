<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 134</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>科研项目经费报销与成果管理系统</strong><span>科研项目、预算科目、报销审批、发票支付、成果登记和绩效统计一体化管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'RESEARCHER', 'FINANCE', 'LEADER'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/project', label: '科研项目', roles: ['ADMIN', 'RESEARCHER', 'FINANCE', 'LEADER'] },
  { index: '/category', label: '预算科目', roles: ['ADMIN', 'FINANCE', 'RESEARCHER', 'LEADER'] },
  { index: '/budget', label: '经费预算', roles: ['ADMIN', 'FINANCE'] },
  { index: '/claim', label: '报销申请', roles: ['ADMIN', 'RESEARCHER', 'LEADER'] },
  { index: '/invoice', label: '发票记录', roles: ['ADMIN', 'RESEARCHER', 'FINANCE'] },
  { index: '/approval', label: '审批任务', roles: ['ADMIN', 'LEADER'] },
  { index: '/payment', label: '支付记录', roles: ['ADMIN', 'FINANCE'] },
  { index: '/achievement', label: '科研成果', roles: ['ADMIN', 'RESEARCHER', 'FINANCE', 'LEADER'] },
  { index: '/paper', label: '论文成果', roles: ['ADMIN', 'RESEARCHER', 'FINANCE', 'LEADER'] },
  { index: '/patent', label: '专利成果', roles: ['ADMIN', 'RESEARCHER', 'FINANCE', 'LEADER'] },
  { index: '/statistic', label: '绩效统计', roles: ['ADMIN', 'FINANCE', 'LEADER'] },
  { index: '/risk', label: '风险预警', roles: ['ADMIN', 'RESEARCHER', 'FINANCE', 'LEADER'] },
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
