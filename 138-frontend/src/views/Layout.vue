<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 138</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/plan'>考试计划</el-menu-item>
        <el-menu-item index='/invigilator'>监考档案</el-menu-item>
        <el-menu-item index='/candidate'>考生档案</el-menu-item>
        <el-menu-item index='/session'>考试场次</el-menu-item>
        <el-menu-item index='/behavior'>异常行为</el-menu-item>
        <el-menu-item index='/evidence'>证据记录</el-menu-item>
        <el-menu-item index='/task'>复核任务</el-menu-item>
        <el-menu-item index='/decision'>复核结论</el-menu-item>
        <el-menu-item index='/rule'>预警规则</el-menu-item>
        <el-menu-item index='/device'>设备监测</el-menu-item>
        <el-menu-item index='/appeal'>违规申诉</el-menu-item>
        <el-menu-item index='/alert'>告警通知</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>在线考试反作弊行为分析与证据管理系统</strong><span>面向在线考试反作弊的计划编排、行为识别、证据留存、复核处理和告警通知一体化管理</span></div>
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




