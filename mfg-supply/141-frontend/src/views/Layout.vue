<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 141</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>固定资产 RFID 盘点与借用归还系统</strong><span>面向固定资产管理的RFID盘点、借用归还、维修折旧和预警通知一体化管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'ASSET_ADMIN', 'BORROWER', 'AUDITOR'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/asset', label: '资产档案', roles: ['ADMIN', 'ASSET_ADMIN'] },
  { index: '/category', label: '资产分类', roles: ['ADMIN', 'ASSET_ADMIN'] },
  { index: '/tag', label: 'RFID 标签', roles: ['ADMIN', 'ASSET_ADMIN'] },
  { index: '/location', label: '存放位置', roles: ['ADMIN', 'ASSET_ADMIN'] },
  { index: '/inventory-task', label: '盘点任务', roles: ['ADMIN', 'ASSET_ADMIN', 'AUDITOR'] },
  { index: '/inventory-record', label: '盘点明细', roles: ['ADMIN', 'ASSET_ADMIN', 'AUDITOR'] },
  { index: '/borrow-apply', label: '借用申请', roles: ['ADMIN', 'ASSET_ADMIN', 'BORROWER', 'AUDITOR'] },
  { index: '/return-record', label: '归还记录', roles: ['ADMIN', 'ASSET_ADMIN', 'BORROWER', 'AUDITOR'] },
  { index: '/repair', label: '维修记录', roles: ['ADMIN', 'ASSET_ADMIN', 'BORROWER', 'AUDITOR'] },
  { index: '/depreciation', label: '折旧记录', roles: ['ADMIN', 'ASSET_ADMIN', 'AUDITOR'] },
  { index: '/disposal', label: '闲置处置', roles: ['ADMIN', 'ASSET_ADMIN', 'AUDITOR'] },
  { index: '/notice', label: '预警通知', roles: ['ADMIN', 'ASSET_ADMIN', 'BORROWER', 'AUDITOR'] },
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







