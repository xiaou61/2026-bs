<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">DATA MASKING 109</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/source'>数据源配置</el-menu-item>
        <el-menu-item index='/dataset'>数据集目录</el-menu-item>
        <el-menu-item index='/rule'>敏感规则</el-menu-item>
        <el-menu-item index='/recognition-task'>识别任务</el-menu-item>
        <el-menu-item index='/recognition-result'>识别结果</el-menu-item>
        <el-menu-item index='/strategy'>脱敏策略</el-menu-item>
        <el-menu-item index='/masking-task'>脱敏任务</el-menu-item>
        <el-menu-item index='/masking-record'>脱敏记录</el-menu-item>
        <el-menu-item index='/lineage'>字段血缘</el-menu-item>
        <el-menu-item index='/access-request'>访问申请</el-menu-item>
        <el-menu-item index='/export-approval'>导出审批</el-menu-item>
        <el-menu-item index='/risk-alert'>风险告警</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>数据脱敏与敏感信息识别平台</strong><span>识别、脱敏、审批、告警闭环管理</span></div>
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
