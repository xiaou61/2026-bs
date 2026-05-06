<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 140</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/template'>合同模板</el-menu-item>
        <el-menu-item index='/counterparty'>对方单位</el-menu-item>
        <el-menu-item index='/signer'>签署方档案</el-menu-item>
        <el-menu-item index='/draft'>合同草稿</el-menu-item>
        <el-menu-item index='/seal-apply'>用印申请</el-menu-item>
        <el-menu-item index='/approval'>审批流程</el-menu-item>
        <el-menu-item index='/signing'>合同签署</el-menu-item>
        <el-menu-item index='/seal-record'>用印记录</el-menu-item>
        <el-menu-item index='/archive'>归档记录</el-menu-item>
        <el-menu-item index='/reminder'>到期提醒</el-menu-item>
        <el-menu-item index='/risk'>风险条款</el-menu-item>
        <el-menu-item index='/notice'>合同通知</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>电子合同签署与印章审批管理系统</strong><span>面向电子合同签署管理的模板维护、用印审批、签署归档、到期提醒和风险条款一体化管理</span></div>
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






