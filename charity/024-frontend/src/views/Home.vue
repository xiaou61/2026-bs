<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { Menu as IconMenu, Location } from '@element-plus/icons-vue'

const router = useRouter()
const user = ref({})

onMounted(() => {
  const userStr = localStorage.getItem('user')
  if (userStr) {
    user.value = JSON.parse(userStr)
  } else {
    router.push('/login')
  }
})

const logout = () => {
  localStorage.removeItem('user')
  router.push('/login')
}
</script>

<template>
  <div class="home-container">
    <el-container>
      <el-header class="header">
        <div class="logo">Pet Adoption Platform</div>
        <div class="user-info">
          <span>Welcome, {{ user.nickname || user.username }}</span>
          <el-button type="danger" size="small" @click="logout" style="margin-left: 10px">Logout</el-button>
        </div>
      </el-header>
      <el-container>
        <el-aside width="200px">
          <el-menu router :default-active="$route.path">
            <el-menu-item index="/">
              <el-icon><icon-menu /></el-icon>
              <span>Dashboard</span>
            </el-menu-item>
            <el-sub-menu index="1">
              <template #title>
                <el-icon><location /></el-icon>
                <span>Pet Management</span>
              </template>
              <el-menu-item index="/pet/list">Pet List</el-menu-item>
              <el-menu-item index="/pet/add">Add Pet</el-menu-item>
            </el-sub-menu>
          </el-menu>
        </el-aside>
        <el-main>
          <RouterView />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #409eff;
  color: white;
  padding: 0 20px;
  height: 60px;
}
.logo {
  font-size: 20px;
  font-weight: bold;
}
.home-container {
  height: 100vh;
  display: flex;
  flex-direction: column;
}
</style>
