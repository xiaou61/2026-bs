<template>
  <el-container class="layout-container">
    <el-aside width="200px">
      <div class="logo">资助管理系统</div>
      <el-menu :default-active="activeMenu" router>
        <el-menu-item index="/dashboard">
          <el-icon><DataAnalysis /></el-icon>
          <span>数据统计</span>
        </el-menu-item>
        <el-menu-item index="/child">
          <el-icon><User /></el-icon>
          <span>儿童管理</span>
        </el-menu-item>
        <el-menu-item index="/donor">
          <el-icon><UserFilled /></el-icon>
          <span>捐赠人管理</span>
        </el-menu-item>
        <el-menu-item index="/application">
          <el-icon><Document /></el-icon>
          <span>资助申请</span>
        </el-menu-item>
        <el-menu-item index="/donation">
          <el-icon><Coin /></el-icon>
          <span>捐赠记录</span>
        </el-menu-item>
        <el-menu-item index="/project">
          <el-icon><Files /></el-icon>
          <span>项目管理</span>
        </el-menu-item>
        <el-menu-item index="/fund">
          <el-icon><Money /></el-icon>
          <span>资金管理</span>
        </el-menu-item>
        <el-menu-item index="/feedback">
          <el-icon><ChatDotRound /></el-icon>
          <span>反馈互动</span>
        </el-menu-item>
        <el-menu-item index="/growth">
          <el-icon><TrendCharts /></el-icon>
          <span>成长记录</span>
        </el-menu-item>
        <el-menu-item index="/announcement">
          <el-icon><Bell /></el-icon>
          <span>公告管理</span>
        </el-menu-item>
        <el-menu-item index="/sponsor">
          <el-icon><Link /></el-icon>
          <span>资助关系</span>
        </el-menu-item>
        <el-menu-item index="/user">
          <el-icon><Setting /></el-icon>
          <span>用户管理</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div class="header-content">
          <span>欢迎，{{ userStore.userInfo?.realName || userStore.userInfo?.username }}</span>
          <el-button type="danger" size="small" @click="handleLogout">退出登录</el-button>
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
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '../store/user'
import { ElMessageBox } from 'element-plus'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const activeMenu = computed(() => route.path)

const handleLogout = () => {
  ElMessageBox.confirm('确定要退出登录吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    userStore.logout()
    router.push('/login')
  }).catch(() => {})
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
}

.el-aside {
  background: #304156;
  color: #fff;
}

.logo {
  height: 60px;
  line-height: 60px;
  text-align: center;
  font-size: 18px;
  font-weight: bold;
  background: #263445;
}

.el-menu {
  border: none;
  background: #304156;
}

.el-menu-item {
  color: #bfcbd9;
}

.el-menu-item:hover,
.el-menu-item.is-active {
  background: #263445 !important;
  color: #409eff !important;
}

.el-header {
  background: #fff;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);
  display: flex;
  align-items: center;
  padding: 0 20px;
}

.header-content {
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.el-main {
  background: #f0f2f5;
  padding: 20px;
}
</style>
