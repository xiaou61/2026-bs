<template>
  <el-container class="layout-container">
    <el-header class="header">
      <div class="logo">
        <el-icon><Bicycle /></el-icon>
        <span>校园共享自行车</span>
      </div>
      <el-menu
        :default-active="$route.path"
        mode="horizontal"
        router
        class="nav-menu"
      >
        <el-menu-item index="/home">首页</el-menu-item>
        <el-menu-item index="/stations">停车点</el-menu-item>
        <el-menu-item index="/orders">我的订单</el-menu-item>
        <el-menu-item index="/wallet">我的钱包</el-menu-item>
        <el-menu-item index="/profile">个人中心</el-menu-item>
      </el-menu>
      <div class="user-info">
        <span>{{ userStore.userInfo.realName || userStore.userInfo.username }}</span>
        <el-button type="danger" link @click="handleLogout">退出</el-button>
      </div>
    </el-header>
    <el-main class="main">
      <router-view />
    </el-main>
  </el-container>
</template>

<script setup>
import { useUserStore } from '@/stores/user'
import { useRouter } from 'vue-router'
import { ElMessageBox } from 'element-plus'

const userStore = useUserStore()
const router = useRouter()

const handleLogout = () => {
  ElMessageBox.confirm('确定要退出登录吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    userStore.logout()
    router.push('/login')
  })
}
</script>

<style scoped lang="scss">
.layout-container {
  height: 100%;
}

.header {
  display: flex;
  align-items: center;
  background: #fff;
  border-bottom: 1px solid #e8e8e8;
  padding: 0 20px;
  
  .logo {
    display: flex;
    align-items: center;
    font-size: 18px;
    font-weight: bold;
    color: #409eff;
    margin-right: 40px;
    
    .el-icon {
      font-size: 24px;
      margin-right: 8px;
    }
  }
  
  .nav-menu {
    flex: 1;
    border-bottom: none;
  }
  
  .user-info {
    display: flex;
    align-items: center;
    gap: 10px;
  }
}

.main {
  background: #f5f7fa;
  padding: 20px;
}
</style>
