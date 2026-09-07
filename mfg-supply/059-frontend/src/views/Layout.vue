<template>
  <el-container style="height:100vh">
    <el-aside :width="isCollapse ? '64px' : '220px'" style="background:#1a237e;transition:width 0.3s">
      <div class="logo">
        <span v-show="!isCollapse">MFG-ERP</span>
        <span v-show="isCollapse">M</span>
      </div>
      <el-menu :default-active="$route.path" router background-color="#1a237e" text-color="#fff" active-text-color="#ffd54f" :collapse="isCollapse">
        <el-menu-item index="/dashboard">
          <el-icon><DataAnalysis /></el-icon><span>数据看板</span>
        </el-menu-item>
        <el-menu-item index="/user" v-if="hasRole(['admin'])">
          <el-icon><UserFilled /></el-icon><span>用户管理</span>
        </el-menu-item>
        <el-sub-menu index="equipment-menu" v-if="hasRole(['admin','operator'])">
          <template #title><el-icon><SetUp /></el-icon><span>设备管理</span></template>
          <el-menu-item index="/equipment/category">设备分类</el-menu-item>
          <el-menu-item index="/equipment">设备台账</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="iot-menu" v-if="hasRole(['admin','operator'])">
          <template #title><el-icon><Monitor /></el-icon><span>物联监控</span></template>
          <el-menu-item index="/iot/sensor">传感器数据</el-menu-item>
          <el-menu-item index="/iot/alert">告警记录</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="production-menu" v-if="hasRole(['admin','manager','inspector'])">
          <template #title><el-icon><Box /></el-icon><span>生产管理</span></template>
          <el-menu-item index="/production/product" v-if="hasRole(['admin','manager'])">产品管理</el-menu-item>
          <el-menu-item index="/production/order">生产工单</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="material-menu" v-if="hasRole(['admin','manager'])">
          <template #title><el-icon><Files /></el-icon><span>物料管理</span></template>
          <el-menu-item index="/material">物料档案</el-menu-item>
          <el-menu-item index="/material/stock">出入库记录</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/quality" v-if="hasRole(['admin','manager','inspector'])">
          <el-icon><Checked /></el-icon><span>质量检测</span>
        </el-menu-item>
        <el-sub-menu index="maintenance-menu" v-if="hasRole(['admin','operator'])">
          <template #title><el-icon><Tools /></el-icon><span>维保管理</span></template>
          <el-menu-item index="/maintenance/plan">维保计划</el-menu-item>
          <el-menu-item index="/maintenance/record">维保记录</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header style="display:flex;align-items:center;justify-content:space-between;border-bottom:1px solid #e0e0e0;background:#fff">
        <div style="display:flex;align-items:center">
          <el-icon style="cursor:pointer;font-size:20px" @click="isCollapse=!isCollapse"><Fold v-if="!isCollapse" /><Expand v-else /></el-icon>
          <span style="margin-left:15px;font-size:16px;color:#333">{{ $route.meta.title }}</span>
        </div>
        <div style="display:flex;align-items:center;gap:15px">
          <span style="color:#666">{{ userStore.user?.realName }}</span>
          <el-tag size="small">{{ roleMap[userStore.user?.role] }}</el-tag>
          <el-button type="danger" link @click="handleLogout">退出</el-button>
        </div>
      </el-header>
      <el-main style="background:#f5f5f5;padding:15px">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { logout } from '../api'
import { useUserStore } from '../store/user'

const router = useRouter()
const userStore = useUserStore()
const isCollapse = ref(false)
const roleMap = { admin: '管理员', manager: '生产经理', operator: '操作员', inspector: '质检员' }

const hasRole = (roles) => {
  return roles.includes(userStore.user?.role)
}

const handleLogout = async () => {
  await logout()
  userStore.clear()
  ElMessage.success('已退出')
  router.push('/login')
}
</script>

<style scoped>
.logo { height: 60px; display: flex; align-items: center; justify-content: center; color: #ffd54f; font-size: 22px; font-weight: bold; border-bottom: 1px solid rgba(255,255,255,0.1); }
.el-menu { border-right: none; }
</style>
