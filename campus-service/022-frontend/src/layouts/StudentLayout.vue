<template>
  <el-container class="student-layout">
    <el-header class="header">
      <div class="logo">
        <School style="width: 24px; height: 24px; margin-right: 8px;" />
        <span>校园自习室预约系统</span>
      </div>
      
      <div class="header-right">
        <div class="credit-info">
          <span>信用分：</span>
          <el-tag :type="creditType">{{ userStore.creditScore }}</el-tag>
        </div>
        
        <el-dropdown>
          <div class="user-info">
            <Avatar style="width: 32px; height: 32px; margin-right: 8px;" />
            <span>{{ userStore.userInfo?.realName || '学生' }}</span>
            <CaretBottom style="width: 12px; height: 12px; margin-left: 4px;" />
          </div>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="router.push('/student/profile')">
                <UserFilled /> 个人信息
              </el-dropdown-item>
              <el-dropdown-item @click="router.push('/student/credit')">
                <Trophy /> 信用记录
              </el-dropdown-item>
              <el-dropdown-item divided @click="handleLogout">
                <SwitchButton /> 退出登录
              </el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </el-header>
    
    <el-container>
      <el-aside width="200px" class="sidebar">
        <el-menu 
          :default-active="$route.path" 
          router
          class="sidebar-menu"
        >
          <el-menu-item index="/student/home">
            <HomeFilled /> <span>首页</span>
          </el-menu-item>
          <el-menu-item index="/student/rooms">
            <Reading /> <span>自习室列表</span>
          </el-menu-item>
          <el-menu-item index="/student/reservations">
            <Calendar /> <span>我的预约</span>
          </el-menu-item>
          <el-menu-item index="/student/profile">
            <UserFilled /> <span>个人中心</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      
      <el-main class="main">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { ElMessage, ElMessageBox } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()

const creditType = computed(() => {
  const score = userStore.creditScore
  if (score >= 90) return 'success'
  if (score >= 70) return 'warning'
  if (score >= 60) return 'danger'
  return 'info'
})

const handleLogout = async () => {
  await ElMessageBox.confirm('确定要退出登录吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
  
  userStore.logout()
  ElMessage.success('已退出登录')
  router.push('/login')
}
</script>

<style lang="scss" scoped>
.student-layout {
  height: 100vh;
  
  .header {
    background: #fff;
    border-bottom: 1px solid #e6e6e6;
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0 20px;
    
    .logo {
      display: flex;
      align-items: center;
      font-size: 18px;
      font-weight: bold;
      color: #333;
    }
    
    .header-right {
      display: flex;
      align-items: center;
      gap: 20px;
      
      .credit-info {
        display: flex;
        align-items: center;
        gap: 8px;
        color: #666;
      }
      
      .user-info {
        display: flex;
        align-items: center;
        cursor: pointer;
        color: #333;
        
        &:hover {
          color: #409eff;
        }
      }
    }
  }
  
  .sidebar {
    background: #fff;
    border-right: 1px solid #e6e6e6;
    
    .sidebar-menu {
      border-right: none;
      height: 100%;
    }
  }
  
  .main {
    background: #f5f7fa;
    padding: 20px;
  }
}
</style>