<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 120</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/building'>园区楼宇</el-menu-item>
        <el-menu-item index='/device'>孪生设备</el-menu-item>
        <el-menu-item index='/route'>巡检路线</el-menu-item>
        <el-menu-item index='/point'>巡检点位</el-menu-item>
        <el-menu-item index='/task'>巡检任务</el-menu-item>
        <el-menu-item index='/record'>巡检记录</el-menu-item>
        <el-menu-item index='/defect'>缺陷报告</el-menu-item>
        <el-menu-item index='/workOrder'>维修工单</el-menu-item>
        <el-menu-item index='/sensor'>传感数据</el-menu-item>
        <el-menu-item index='/model'>孪生模型</el-menu-item>
        <el-menu-item index='/energy'>能耗监测</el-menu-item>
        <el-menu-item index='/schedule'>保养计划</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>数字孪生园区设备巡检管理系统</strong><span>基础档案、任务流转、异常处理、统计看板和审计日志一体化管理</span></div>
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
