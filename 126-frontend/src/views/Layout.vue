<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 126</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/home'>家庭档案</el-menu-item>
        <el-menu-item index='/member'>家庭成员</el-menu-item>
        <el-menu-item index='/meter'>智能电表</el-menu-item>
        <el-menu-item index='/device'>用电设备</el-menu-item>
        <el-menu-item index='/reading'>用电读数</el-menu-item>
        <el-menu-item index='/bill'>电费账单</el-menu-item>
        <el-menu-item index='/usage'>设备用电</el-menu-item>
        <el-menu-item index='/budget'>能耗预算</el-menu-item>
        <el-menu-item index='/suggestion'>节能建议</el-menu-item>
        <el-menu-item index='/alert'>异常预警</el-menu-item>
        <el-menu-item index='/carbon'>碳排统计</el-menu-item>
        <el-menu-item index='/ticket'>维修工单</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>家庭能源用电分析与节能建议平台</strong><span>家庭档案、智能电表、设备用电、账单预算、异常预警和节能建议一体化管理</span></div>
        <div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div>
      </el-header>
      <el-main><router-view /></el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { logout } from '../api'
import { useUserStore } from '../store/user'
const router = useRouter()
const userStore = useUserStore()
const handleLogout = async () => {
  await logout().catch(() => null)
  userStore.clear()
  router.push('/login')
}
</script>
