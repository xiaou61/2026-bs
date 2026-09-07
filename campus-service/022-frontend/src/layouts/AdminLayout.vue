<template>
  <el-container class="admin-layout">
    <el-header class="header">
      <div class="logo">
        <School style="width: 24px; height: 24px; margin-right: 8px;" />
        <span>校园自习室管理系统</span>
      </div>
      
      <div class="header-right">
        <el-dropdown>
          <div class="user-info">
            <Avatar style="width: 32px; height: 32px; margin-right: 8px;" />
            <span>{{ userStore.userInfo?.realName || '管理员' }}</span>
            <CaretBottom style="width: 12px; height: 12px; margin-left: 4px;" />
          </div>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="handleLogout">
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
          <el-menu-item index="/admin/dashboard">
            <DataAnalysis /> <span>仪表盘</span>
          </el-menu-item>
          <el-menu-item index="/admin/rooms">
            <House /> <span>自习室管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/seats">
            <Position /> <span>座位管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/reservations">
            <Calendar /> <span>预约管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/users">
            <UserFilled /> <span>用户管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/statistics">
            <DataLine /> <span>数据统计</span>
          </el-menu-item>
          <el-menu-item index="/admin/system">
            <Setting /> <span>系统配置</span>
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
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { ElMessage, ElMessageBox } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()

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
.admin-layout {
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