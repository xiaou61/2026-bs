<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">团建分摊 187</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>旅行社团建行程报名与费用分摊管理平台</strong><span>行程报名、成员确认、费用预算、费用分摊闭环协同</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'AGENCY', 'PLANNER', 'FINANCE', 'LEADER', 'PARTICIPANT'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/agency', label: '旅行社档案', roles: ['ADMIN', 'AGENCY'] },
  { index: '/team', label: '团队档案', roles: ['ADMIN', 'AGENCY', 'LEADER'] },
  { index: '/trip', label: '团建行程', roles: ['ADMIN', 'AGENCY', 'PLANNER', 'LEADER', 'PARTICIPANT'] },
  { index: '/signup', label: '行程报名', roles: ['ADMIN', 'AGENCY', 'PLANNER', 'LEADER', 'PARTICIPANT'] },
  { index: '/confirmation', label: '成员确认', roles: ['ADMIN', 'LEADER', 'PARTICIPANT'] },
  { index: '/budget', label: '费用预算', roles: ['ADMIN', 'AGENCY', 'PLANNER', 'FINANCE', 'LEADER'] },
  { index: '/share', label: '费用分摊', roles: ['ADMIN', 'FINANCE', 'LEADER', 'PARTICIPANT'] },
  { index: '/payment', label: '缴费记录', roles: ['ADMIN', 'FINANCE', 'LEADER', 'PARTICIPANT'] },
  { index: '/notice', label: '通知同步', roles: ['ADMIN', 'AGENCY', 'PLANNER', 'LEADER', 'PARTICIPANT'] },
  { index: '/feedback', label: '出行反馈', roles: ['ADMIN', 'AGENCY', 'LEADER', 'PARTICIPANT'] },
  { index: '/invoice', label: '发票记录', roles: ['ADMIN', 'AGENCY', 'FINANCE', 'LEADER'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
