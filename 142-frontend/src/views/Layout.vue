<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 142</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/policy'>保险保单</el-menu-item>
        <el-menu-item index='/vehicle'>车辆档案</el-menu-item>
        <el-menu-item index='/customer'>客户档案</el-menu-item>
        <el-menu-item index='/claim'>理赔申请</el-menu-item>
        <el-menu-item index='/accident'>事故报案</el-menu-item>
        <el-menu-item index='/material'>材料清单</el-menu-item>
        <el-menu-item index='/review'>材料审核</el-menu-item>
        <el-menu-item index='/assessment'>定损记录</el-menu-item>
        <el-menu-item index='/compensation'>赔付记录</el-menu-item>
        <el-menu-item index='/progress'>进度跟踪</el-menu-item>
        <el-menu-item index='/followup'>回访记录</el-menu-item>
        <el-menu-item index='/notice'>消息通知</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>车辆保险理赔材料审核与进度跟踪系统</strong><span>面向车辆保险理赔管理的报案受理、材料审核、定损赔付和进度跟踪一体化管理</span></div>
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







