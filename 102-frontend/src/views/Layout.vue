<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">LEGAL 102</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-if="role === 'ADMIN'" index="/dashboard">数据看板</el-menu-item>
        <el-menu-item v-if="role === 'ADMIN'" index="/user">账号权限</el-menu-item>
        <el-menu-item v-if="role === 'ASSISTANT'" index="/client">委托人档案</el-menu-item>
        <el-menu-item v-if="role === 'ADMIN'" index="/lawyer">律师档案</el-menu-item>
        <el-menu-item v-if="role === 'LAWYER' || role === 'ASSISTANT' || role === 'CLIENT'" index="/case">案件台账</el-menu-item>
        <el-menu-item v-if="role === 'LAWYER' || role === 'ASSISTANT'" index="/stage">进度节点</el-menu-item>
        <el-menu-item v-if="role === 'LAWYER'" index="/consultation">咨询记录</el-menu-item>
        <el-menu-item v-if="role === 'ADMIN'" index="/template">文书模板</el-menu-item>
        <el-menu-item v-if="role === 'LAWYER'" index="/document">法律文书</el-menu-item>
        <el-menu-item v-if="role === 'LAWYER'" index="/version">文书版本</el-menu-item>
        <el-menu-item v-if="role === 'LAWYER' || role === 'ASSISTANT' || role === 'CLIENT'" index="/appointment">咨询预约</el-menu-item>
        <el-menu-item v-if="role === 'ASSISTANT' || role === 'CLIENT'" index="/evidence">证据材料</el-menu-item>
        <el-menu-item v-if="role === 'ASSISTANT'" index="/fee">费用记录</el-menu-item>
        <el-menu-item v-if="role === 'ADMIN'" index="/log">操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div>
          <strong>法律咨询案件进度与智能文书管理系统</strong>
          <span>案件、文书、预约、费用全流程协同</span>
        </div>
        <div class="user-box">
          <el-tag>{{ userStore.user?.role }}</el-tag>
          <span>{{ userStore.user?.nickname }}</span>
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
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { logout } from '../api'
import { useUserStore } from '../store/user'

const router = useRouter()
const userStore = useUserStore()
const role = computed(() => userStore.user?.role)

const handleLogout = async () => {
  await logout().catch(() => null)
  userStore.clear()
  router.push('/login')
}
</script>
