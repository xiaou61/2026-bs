<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 130</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/greenhouse'>温室档案</el-menu-item>
        <el-menu-item index='/crop'>作物批次</el-menu-item>
        <el-menu-item index='/sensor'>环境传感器</el-menu-item>
        <el-menu-item index='/reading'>环境读数</el-menu-item>
        <el-menu-item index='/irrigation'>灌溉任务</el-menu-item>
        <el-menu-item index='/fertilizer'>施肥计划</el-menu-item>
        <el-menu-item index='/pest'>虫害预警</el-menu-item>
        <el-menu-item index='/diagnosis'>病害诊断</el-menu-item>
        <el-menu-item index='/device'>控制设备</el-menu-item>
        <el-menu-item index='/command'>远程指令</el-menu-item>
        <el-menu-item index='/harvest'>采收记录</el-menu-item>
        <el-menu-item index='/ticket'>维护工单</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>温室大棚物联网控制与病害预警系统</strong><span>温室档案、环境传感、远程控制、灌溉施肥、病虫害预警和采收维护一体化管理</span></div>
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
