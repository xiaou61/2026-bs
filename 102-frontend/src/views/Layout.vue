<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">LEGAL 102</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index="/user">账号权限</el-menu-item>
        <el-menu-item index="/client">委托人档案</el-menu-item>
        <el-menu-item index="/lawyer">律师档案</el-menu-item>
        <el-menu-item index="/case">案件台账</el-menu-item>
        <el-menu-item index="/stage">进度节点</el-menu-item>
        <el-menu-item index="/consultation">咨询记录</el-menu-item>
        <el-menu-item index="/template">文书模板</el-menu-item>
        <el-menu-item index="/document">法律文书</el-menu-item>
        <el-menu-item index="/version">文书版本</el-menu-item>
        <el-menu-item index="/appointment">咨询预约</el-menu-item>
        <el-menu-item index="/evidence">证据材料</el-menu-item>
        <el-menu-item index="/fee">费用记录</el-menu-item>
        <el-menu-item index="/log">操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div>
          <strong>法律咨询案件进度与智能文书管理系统</strong>
          <span>案件、文书、预约、费用全流程协同</span>
        </div>
        <div class="user-box">
          <el-tag>{ userStore.user?.role }</el-tag>
          <span>{ userStore.user?.nickname }</span>
          <el-button link type="danger" @click="handleLogout">退出</el-button>
        </div>
      </el-header>
      <el-main>
        <router-view />
      </el-main>
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
