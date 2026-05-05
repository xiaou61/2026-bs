<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 122</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/project'>工地项目</el-menu-item>
        <el-menu-item index='/team'>施工班组</el-menu-item>
        <el-menu-item index='/inspector'>安全员档案</el-menu-item>
        <el-menu-item index='/plan'>巡检计划</el-menu-item>
        <el-menu-item index='/task'>巡检任务</el-menu-item>
        <el-menu-item index='/hazard'>隐患上报</el-menu-item>
        <el-menu-item index='/rectify'>整改工单</el-menu-item>
        <el-menu-item index='/acceptance'>验收记录</el-menu-item>
        <el-menu-item index='/training'>安全培训</el-menu-item>
        <el-menu-item index='/equipment'>设备检查</el-menu-item>
        <el-menu-item index='/supply'>防护用品</el-menu-item>
        <el-menu-item index='/warning'>风险预警</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>智慧工地安全巡检与隐患整改系统</strong><span>工地项目、巡检计划、隐患上报、整改验收和安全培训一体化管理</span></div>
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
