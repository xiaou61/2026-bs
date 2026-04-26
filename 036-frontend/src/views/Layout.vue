<template>
  <div class="layout">
    <el-container>
      <el-header class="header">
        <div class="header-content">
          <div class="logo" @click="$router.push('/')">小梦想公益平台</div>
          <el-menu mode="horizontal" :default-active="activeMenu" class="nav-menu">
            <el-menu-item index="/" @click="$router.push('/')">首页</el-menu-item>
            <el-menu-item index="/projects" @click="$router.push('/projects')">公益项目</el-menu-item>
            <el-menu-item index="/my" @click="$router.push('/my')">个人中心</el-menu-item>
          </el-menu>
          <div class="user-info">
            <el-button v-if="!userStore.token" @click="$router.push('/login')">登录</el-button>
            <el-dropdown v-else>
              <span class="el-dropdown-link">
                {{ userStore.userInfo?.username || '用户' }}
                <el-icon class="el-icon--right"><arrow-down /></el-icon>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item @click="handleLogout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </div>
      </el-header>
      <el-main class="main-content">
        <router-view />
      </el-main>
      <el-footer class="footer">
        <div>© 2026 小梦想全球公益捐赠平台</div>
      </el-footer>
    </el-container>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/store/user'
import { ArrowDown } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const activeMenu = computed(() => route.path)

const handleLogout = () => {
  userStore.logout()
  router.push('/login')
}
</script>

<style scoped>
.layout {
  min-height: 100vh;
}

.header {
  background-color: #fff;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
  padding: 0;
}

.header-content {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 100%;
  padding: 0 20px;
}

.logo {
  font-size: 20px;
  font-weight: bold;
  color: #409EFF;
  cursor: pointer;
}

.nav-menu {
  flex: 1;
  margin: 0 40px;
  border-bottom: none;
}

.user-info {
  display: flex;
  align-items: center;
}

.el-dropdown-link {
  cursor: pointer;
  color: #409EFF;
  display: flex;
  align-items: center;
}

.main-content {
  min-height: calc(100vh - 120px);
  padding: 20px;
  background-color: #f5f7fa;
}

.footer {
  background-color: #fff;
  text-align: center;
  padding: 20px;
  color: #909399;
}
</style>
