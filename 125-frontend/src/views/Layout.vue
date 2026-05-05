<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 125</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/lot'>停车场</el-menu-item>
        <el-menu-item index='/area'>停车区域</el-menu-item>
        <el-menu-item index='/space'>车位档案</el-menu-item>
        <el-menu-item index='/sensor'>车位传感器</el-menu-item>
        <el-menu-item index='/vehicle'>车主车辆</el-menu-item>
        <el-menu-item index='/reservation'>预约订单</el-menu-item>
        <el-menu-item index='/record'>停车记录</el-menu-item>
        <el-menu-item index='/payment'>支付记录</el-menu-item>
        <el-menu-item index='/prediction'>空位预测</el-menu-item>
        <el-menu-item index='/screen'>诱导屏幕</el-menu-item>
        <el-menu-item index='/route'>导航路线</el-menu-item>
        <el-menu-item index='/fault'>故障报修</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>智慧停车诱导与空位预测平台</strong><span>停车场、车位传感、预约导航、空位预测、故障报修和收费对账一体化管理</span></div>
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
