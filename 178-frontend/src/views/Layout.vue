<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">手术室器械 178</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>医院手术室器械包追踪与灭菌放行系统</strong><span>器械包追踪、灭菌记录、放行审核、异常召回一体化协同</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'ORNURSE', 'CSSD', 'STERILE', 'QA', 'SURGEON'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/room', label: '手术室档案', roles: ['ADMIN', 'ORNURSE', 'SURGEON'] },
  { index: '/pack', label: '器械包档案', roles: ['ADMIN', 'CSSD', 'ORNURSE', 'SURGEON'] },
  { index: '/item', label: '器械明细', roles: ['ADMIN', 'CSSD', 'STERILE', 'QA'] },
  { index: '/trace', label: '器械包追踪', roles: ['ADMIN', 'ORNURSE', 'CSSD', 'STERILE', 'QA', 'SURGEON'] },
  { index: '/batch', label: '灭菌批次', roles: ['ADMIN', 'CSSD', 'STERILE', 'QA'] },
  { index: '/sterilization', label: '灭菌记录', roles: ['ADMIN', 'CSSD', 'STERILE', 'QA'] },
  { index: '/release', label: '放行审核', roles: ['ADMIN', 'QA', 'CSSD', 'ORNURSE'] },
  { index: '/surgery', label: '手术使用', roles: ['ADMIN', 'ORNURSE', 'SURGEON', 'CSSD'] },
  { index: '/return', label: '回收清点', roles: ['ADMIN', 'ORNURSE', 'CSSD', 'STERILE'] },
  { index: '/defect', label: '缺损上报', roles: ['ADMIN', 'ORNURSE', 'CSSD', 'STERILE', 'QA'] },
  { index: '/recall', label: '异常召回', roles: ['ADMIN', 'QA', 'CSSD', 'ORNURSE', 'SURGEON'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
