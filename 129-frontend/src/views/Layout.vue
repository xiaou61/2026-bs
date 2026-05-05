<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 129</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/pond'>养殖池塘</el-menu-item>
        <el-menu-item index='/sensor'>传感设备</el-menu-item>
        <el-menu-item index='/reading'>水质读数</el-menu-item>
        <el-menu-item index='/plan'>投喂计划</el-menu-item>
        <el-menu-item index='/feeding'>投喂记录</el-menu-item>
        <el-menu-item index='/batch'>鱼苗批次</el-menu-item>
        <el-menu-item index='/sampling'>生长采样</el-menu-item>
        <el-menu-item index='/disease'>病害预警</el-menu-item>
        <el-menu-item index='/medication'>用药记录</el-menu-item>
        <el-menu-item index='/equipment'>养殖设备</el-menu-item>
        <el-menu-item index='/rule'>水质规则</el-menu-item>
        <el-menu-item index='/statistic'>产量统计</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
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
