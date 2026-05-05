<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">LOCAL VOUCHER 117</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/merchant'>商户档案</el-menu-item>
        <el-menu-item index='/store'>门店网点</el-menu-item>
        <el-menu-item index='/consumer'>用户档案</el-menu-item>
        <el-menu-item index='/template'>券模板</el-menu-item>
        <el-menu-item index='/activity'>营销活动</el-menu-item>
        <el-menu-item index='/coupon'>用户领券</el-menu-item>
        <el-menu-item index='/verification'>核销记录</el-menu-item>
        <el-menu-item index='/settlement'>商户结算</el-menu-item>
        <el-menu-item index='/detail'>结算明细</el-menu-item>
        <el-menu-item index='/transfer'>打款记录</el-menu-item>
        <el-menu-item index='/complaint'>申诉工单</el-menu-item>
        <el-menu-item index='/statRecord'>活动统计</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>本地生活服务券核销与商户结算系统</strong><span>服务券、核销、结算、打款和申诉统计一体化管理</span></div>
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
