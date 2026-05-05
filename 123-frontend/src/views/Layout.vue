<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 123</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/point'>水位点位</el-menu-item>
        <el-menu-item index='/rainstation'>雨量站点</el-menu-item>
        <el-menu-item index='/pump'>排涝泵站</el-menu-item>
        <el-menu-item index='/waterdata'>水位数据</el-menu-item>
        <el-menu-item index='/raindata'>雨量数据</el-menu-item>
        <el-menu-item index='/rule'>预警规则</el-menu-item>
        <el-menu-item index='/warning'>内涝预警</el-menu-item>
        <el-menu-item index='/plan'>应急预案</el-menu-item>
        <el-menu-item index='/task'>调度任务</el-menu-item>
        <el-menu-item index='/team'>救援队伍</el-menu-item>
        <el-menu-item index='/material'>物资储备</el-menu-item>
        <el-menu-item index='/shelter'>避险点位</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>城市内涝监测与应急调度平台</strong><span>水位雨量监测、预警规则、应急调度、物资队伍和避险点一体化管理</span></div>
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
