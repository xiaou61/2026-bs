<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">COLD CHAIN 114</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/warehouse'>冷链仓点</el-menu-item>
        <el-menu-item index='/carrier'>承运企业</el-menu-item>
        <el-menu-item index='/device'>温控设备</el-menu-item>
        <el-menu-item index='/cargo'>冷链货品</el-menu-item>
        <el-menu-item index='/order'>运输订单</el-menu-item>
        <el-menu-item index='/temperature'>温控记录</el-menu-item>
        <el-menu-item index='/track'>运输轨迹</el-menu-item>
        <el-menu-item index='/rule'>告警规则</el-menu-item>
        <el-menu-item index='/alert'>异常告警</el-menu-item>
        <el-menu-item index='/task'>处置任务</el-menu-item>
        <el-menu-item index='/responsibility'>责任追溯</el-menu-item>
        <el-menu-item index='/maintenance'>设备维护</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>供应链冷链温控追踪与异常预警平台</strong><span>温控记录、运输轨迹、异常告警、责任追溯闭环管理</span></div>
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
