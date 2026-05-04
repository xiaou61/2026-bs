<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">CLOUD COST 108</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/account'>云账号</el-menu-item>
        <el-menu-item index='/namespace'>资源命名空间</el-menu-item>
        <el-menu-item index='/bill'>成本账单</el-menu-item>
        <el-menu-item index='/cost-item'>成本明细</el-menu-item>
        <el-menu-item index='/budget'>预算策略</el-menu-item>
        <el-menu-item index='/allocation'>成本分摊</el-menu-item>
        <el-menu-item index='/idle-resource'>闲置资源</el-menu-item>
        <el-menu-item index='/optimization-rule'>优化规则</el-menu-item>
        <el-menu-item index='/advice'>优化建议</el-menu-item>
        <el-menu-item index='/saving-plan'>节省计划</el-menu-item>
        <el-menu-item index='/anomaly'>成本异常</el-menu-item>
        <el-menu-item index='/report'>报告快照</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>云原生成本分析与资源优化平台</strong><span>账单、预算、分摊、优化和异常闭环管理</span></div>
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
