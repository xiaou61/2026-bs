<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 124</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/station'>充电站点</el-menu-item>
        <el-menu-item index='/pile'>充电桩位</el-menu-item>
        <el-menu-item index='/vehicle'>用户车辆</el-menu-item>
        <el-menu-item index='/appointment'>预约订单</el-menu-item>
        <el-menu-item index='/session'>充电记录</el-menu-item>
        <el-menu-item index='/fault'>故障报修</el-menu-item>
        <el-menu-item index='/repair'>维修工单</el-menu-item>
        <el-menu-item index='/plan'>保养计划</el-menu-item>
        <el-menu-item index='/price'>电价策略</el-menu-item>
        <el-menu-item index='/payment'>支付记录</el-menu-item>
        <el-menu-item index='/revenue'>收益统计</el-menu-item>
        <el-menu-item index='/energy'>能耗监测</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>电动车充电桩预约与运维管理系统</strong><span>站点桩位、预约充电、故障报修、维修工单、收益统计和能耗监测一体化管理</span></div>
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
