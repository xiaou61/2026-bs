<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 134</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/project'>科研项目</el-menu-item>
        <el-menu-item index='/category'>预算科目</el-menu-item>
        <el-menu-item index='/budget'>经费预算</el-menu-item>
        <el-menu-item index='/claim'>报销申请</el-menu-item>
        <el-menu-item index='/invoice'>发票记录</el-menu-item>
        <el-menu-item index='/approval'>审批任务</el-menu-item>
        <el-menu-item index='/payment'>支付记录</el-menu-item>
        <el-menu-item index='/achievement'>科研成果</el-menu-item>
        <el-menu-item index='/paper'>论文成果</el-menu-item>
        <el-menu-item index='/patent'>专利成果</el-menu-item>
        <el-menu-item index='/statistic'>绩效统计</el-menu-item>
        <el-menu-item index='/risk'>风险预警</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>科研项目经费报销与成果管理系统</strong><span>科研项目、预算科目、报销审批、发票支付、成果登记和绩效统计一体化管理</span></div>
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
