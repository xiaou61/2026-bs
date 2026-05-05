<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">AGV WAREHOUSE 118</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/zone'>仓库区域</el-menu-item>
        <el-menu-item index='/location'>库位档案</el-menu-item>
        <el-menu-item index='/agv'>AGV车辆</el-menu-item>
        <el-menu-item index='/station'>充电站点</el-menu-item>
        <el-menu-item index='/inventory'>库存物料</el-menu-item>
        <el-menu-item index='/inbound'>入库订单</el-menu-item>
        <el-menu-item index='/outbound'>出库订单</el-menu-item>
        <el-menu-item index='/task'>AGV任务</el-menu-item>
        <el-menu-item index='/route'>路径规划</el-menu-item>
        <el-menu-item index='/recommendation'>库位推荐</el-menu-item>
        <el-menu-item index='/maintenance'>设备维保</el-menu-item>
        <el-menu-item index='/alert'>异常告警</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>智能仓储 AGV 任务调度与库位优化系统</strong><span>仓储区域、库位推荐、AGV调度、路径规划、出入库和维保告警一体化管理</span></div>
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
