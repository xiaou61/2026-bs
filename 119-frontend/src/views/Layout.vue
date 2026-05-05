<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 119</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/asset'>设备资产</el-menu-item>
        <el-menu-item index='/catalog'>备件目录</el-menu-item>
        <el-menu-item index='/stock'>备件库存</el-menu-item>
        <el-menu-item index='/inbound'>入库记录</el-menu-item>
        <el-menu-item index='/outbound'>出库领用</el-menu-item>
        <el-menu-item index='/usage'>使用记录</el-menu-item>
        <el-menu-item index='/metric'>运行指标</el-menu-item>
        <el-menu-item index='/failure'>故障记录</el-menu-item>
        <el-menu-item index='/prediction'>寿命预测</el-menu-item>
        <el-menu-item index='/plan'>维保计划</el-menu-item>
        <el-menu-item index='/purchase'>采购申请</el-menu-item>
        <el-menu-item index='/warning'>风险预警</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>设备备件寿命预测与维保决策系统</strong><span>基础档案、任务流转、异常处理、统计看板和审计日志一体化管理</span></div>
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
