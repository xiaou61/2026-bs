<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">就业帮扶 173</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>高校毕业去向填报与就业帮扶跟踪系统</strong><span>去向填报、材料审核、岗位推荐、帮扶跟踪一体化协同</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'CAREER', 'COLLEGE', 'COUNSELOR', 'STUDENT', 'EMPLOYER'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/major', label: '学院专业', roles: ['ADMIN', 'CAREER', 'COLLEGE', 'COUNSELOR'] },
  { index: '/graduate', label: '毕业生档案', roles: ['ADMIN', 'CAREER', 'COLLEGE', 'COUNSELOR', 'STUDENT'] },
  { index: '/employer', label: '用人单位', roles: ['ADMIN', 'CAREER', 'EMPLOYER'] },
  { index: '/job', label: '招聘岗位', roles: ['ADMIN', 'CAREER', 'COLLEGE', 'COUNSELOR', 'STUDENT', 'EMPLOYER'] },
  { index: '/destination', label: '去向填报', roles: ['ADMIN', 'CAREER', 'COLLEGE', 'COUNSELOR', 'STUDENT'] },
  { index: '/contract', label: '协议归档', roles: ['ADMIN', 'CAREER', 'COLLEGE', 'COUNSELOR', 'STUDENT', 'EMPLOYER'] },
  { index: '/review', label: '材料审核', roles: ['ADMIN', 'CAREER', 'COLLEGE', 'COUNSELOR', 'STUDENT'] },
  { index: '/assistance', label: '帮扶记录', roles: ['ADMIN', 'CAREER', 'COLLEGE', 'COUNSELOR', 'STUDENT'] },
  { index: '/recommendation', label: '岗位推荐', roles: ['ADMIN', 'CAREER', 'COLLEGE', 'COUNSELOR', 'STUDENT', 'EMPLOYER'] },
  { index: '/followup', label: '跟踪回访', roles: ['ADMIN', 'CAREER', 'COLLEGE', 'COUNSELOR', 'STUDENT'] },
  { index: '/statistics', label: '就业统计', roles: ['ADMIN', 'CAREER', 'COLLEGE', 'COUNSELOR'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
