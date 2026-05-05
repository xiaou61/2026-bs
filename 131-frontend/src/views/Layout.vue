<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 131</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/patient'>患者档案</el-menu-item>
        <el-menu-item index='/drug'>药品目录</el-menu-item>
        <el-menu-item index='/reporter'>上报人档案</el-menu-item>
        <el-menu-item index='/report'>不良反应上报</el-menu-item>
        <el-menu-item index='/symptom'>反应症状</el-menu-item>
        <el-menu-item index='/risk'>风险评估</el-menu-item>
        <el-menu-item index='/plan'>随访计划</el-menu-item>
        <el-menu-item index='/followup'>随访记录</el-menu-item>
        <el-menu-item index='/review'>病例复核</el-menu-item>
        <el-menu-item index='/advice'>处置建议</el-menu-item>
        <el-menu-item index='/department'>科室信息</el-menu-item>
        <el-menu-item index='/statistic'>统计报表</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>药品不良反应上报与随访管理系统</strong><span>患者档案、药品目录、不良反应上报、风险评估、随访记录和病例复核一体化管理</span></div>
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
