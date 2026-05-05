<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 133</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/catalog'>耗材目录</el-menu-item>
        <el-menu-item index='/supplier'>供应商档案</el-menu-item>
        <el-menu-item index='/lab'>实验室</el-menu-item>
        <el-menu-item index='/stock'>库存台账</el-menu-item>
        <el-menu-item index='/request'>采购申请</el-menu-item>
        <el-menu-item index='/approval'>采购审批</el-menu-item>
        <el-menu-item index='/order'>采购订单</el-menu-item>
        <el-menu-item index='/inbound'>入库记录</el-menu-item>
        <el-menu-item index='/outbound'>领用记录</el-menu-item>
        <el-menu-item index='/check'>库存盘点</el-menu-item>
        <el-menu-item index='/rule'>预警规则</el-menu-item>
        <el-menu-item index='/warning'>库存预警</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>实验室耗材采购审批与库存预警系统</strong><span>耗材目录、采购申请、审批订单、出入库台账、库存盘点和预警提醒一体化管理</span></div>
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
