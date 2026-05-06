<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 148</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/package'>服务套餐</el-menu-item>
        <el-menu-item index='/elder'>老人档案</el-menu-item>
        <el-menu-item index='/caregiver'>护理档案</el-menu-item>
        <el-menu-item index='/order'>服务工单</el-menu-item>
        <el-menu-item index='/team'>服务团队</el-menu-item>
        <el-menu-item index='/checkin'>上门签到</el-menu-item>
        <el-menu-item index='/record'>服务记录</el-menu-item>
        <el-menu-item index='/assessment'>健康评估</el-menu-item>
        <el-menu-item index='/reminder'>用药提醒</el-menu-item>
        <el-menu-item index='/family'>家属回访</el-menu-item>
        <el-menu-item index='/alert'>预警事件</el-menu-item>
        <el-menu-item index='/notice'>通知公告</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>社区养老服务派单与健康随访管理系统</strong><span>面向社区养老服务管理的服务派单、上门照护、健康随访和预警提醒一体化管理</span></div>
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










