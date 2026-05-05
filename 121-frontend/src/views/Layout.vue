<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 121</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/drone'>无人机设备</el-menu-item>
        <el-menu-item index='/pilot'>飞手档案</el-menu-item>
        <el-menu-item index='/zone'>巡检区域</el-menu-item>
        <el-menu-item index='/route'>航线规划</el-menu-item>
        <el-menu-item index='/task'>巡检任务</el-menu-item>
        <el-menu-item index='/flight'>飞行记录</el-menu-item>
        <el-menu-item index='/defect'>缺陷报告</el-menu-item>
        <el-menu-item index='/image'>缺陷图片</el-menu-item>
        <el-menu-item index='/rectify'>整改工单</el-menu-item>
        <el-menu-item index='/station'>电池站点</el-menu-item>
        <el-menu-item index='/maintenance'>维保记录</el-menu-item>
        <el-menu-item index='/warning'>风险预警</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>无人机巡检任务调度与缺陷上报平台</strong><span>航线规划、任务派发、飞行记录、缺陷整改和风险看板一体化管理</span></div>
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
