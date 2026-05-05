<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">LIVE COMMERCE 116</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/channel'>直播渠道</el-menu-item>
        <el-menu-item index='/anchor'>主播档案</el-menu-item>
        <el-menu-item index='/supplier'>供应商品牌</el-menu-item>
        <el-menu-item index='/selection'>选品池</el-menu-item>
        <el-menu-item index='/session'>直播场次</el-menu-item>
        <el-menu-item index='/schedule'>排期计划</el-menu-item>
        <el-menu-item index='/sample'>样品申请</el-menu-item>
        <el-menu-item index='/script'>话术脚本</el-menu-item>
        <el-menu-item index='/order'>直播订单</el-menu-item>
        <el-menu-item index='/ticket'>售后工单</el-menu-item>
        <el-menu-item index='/refund'>退款记录</el-menu-item>
        <el-menu-item index='/performance'>主播绩效</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>直播电商选品排期与售后工单系统</strong><span>选品排期、直播订单、售后工单、退款绩效一体化管理</span></div>
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
