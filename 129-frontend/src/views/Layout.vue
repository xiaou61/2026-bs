<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 129</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>水产养殖环境监测与投喂预警系统</strong><span>水质监测、投喂计划、鱼苗批次、生长采样、病害预警和产量统计一体化管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'BREEDER', 'TECHNICIAN', 'MANAGER'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/pond', label: '养殖池塘', roles: ['ADMIN', 'BREEDER', 'TECHNICIAN', 'MANAGER'] },
  { index: '/sensor', label: '传感设备', roles: ['ADMIN', 'TECHNICIAN', 'MANAGER'] },
  { index: '/reading', label: '水质读数', roles: ['ADMIN', 'BREEDER', 'TECHNICIAN', 'MANAGER'] },
  { index: '/plan', label: '投喂计划', roles: ['ADMIN', 'BREEDER', 'MANAGER'] },
  { index: '/feeding', label: '投喂记录', roles: ['ADMIN', 'BREEDER', 'MANAGER'] },
  { index: '/batch', label: '鱼苗批次', roles: ['ADMIN', 'BREEDER', 'MANAGER'] },
  { index: '/sampling', label: '生长采样', roles: ['ADMIN', 'BREEDER', 'TECHNICIAN', 'MANAGER'] },
  { index: '/disease', label: '病害预警', roles: ['ADMIN', 'BREEDER', 'TECHNICIAN', 'MANAGER'] },
  { index: '/medication', label: '用药记录', roles: ['ADMIN', 'BREEDER', 'TECHNICIAN', 'MANAGER'] },
  { index: '/equipment', label: '养殖设备', roles: ['ADMIN', 'TECHNICIAN', 'MANAGER'] },
  { index: '/rule', label: '水质规则', roles: ['ADMIN', 'TECHNICIAN', 'MANAGER'] },
  { index: '/statistic', label: '产量统计', roles: ['ADMIN', 'BREEDER', 'MANAGER'] },
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
