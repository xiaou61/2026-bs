<template>
  <div class="layout">
    <el-header class="header">
      <div class="logo" @click="$router.push('/')">网课学习平台</div>
      <el-menu mode="horizontal" :ellipsis="false" router>
        <el-menu-item index="/">首页</el-menu-item>
        <el-menu-item index="/courses">全部课程</el-menu-item>
      </el-menu>
      <div class="user-area">
        <template v-if="user">
          <el-dropdown>
            <span class="user-info">
              <el-avatar :size="32" :src="user.avatar">{{ user.nickname?.charAt(0) }}</el-avatar>
              <span>{{ user.nickname }}</span>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="$router.push('/my-courses')">我的课程</el-dropdown-item>
                <el-dropdown-item @click="$router.push('/favorites')">我的收藏</el-dropdown-item>
                <el-dropdown-item @click="$router.push('/profile')">个人中心</el-dropdown-item>
                <el-dropdown-item divided @click="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </template>
        <template v-else>
          <el-button type="primary" @click="$router.push('/login')">登录</el-button>
          <el-button @click="$router.push('/register')">注册</el-button>
        </template>
      </div>
    </el-header>
    <el-main class="main">
      <router-view />
    </el-main>
    <el-footer class="footer">© 2024 网课学习平台 All Rights Reserved</el-footer>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const user = ref(null)

onMounted(() => {
  const userStr = localStorage.getItem('user')
  if (userStr) {
    user.value = JSON.parse(userStr)
  }
})

const logout = () => {
  localStorage.removeItem('token')
  localStorage.removeItem('user')
  user.value = null
  router.push('/')
}
</script>

<style scoped>
.layout { min-height: 100vh; display: flex; flex-direction: column; }
.header { display: flex; align-items: center; background: #fff; box-shadow: 0 2px 8px rgba(0,0,0,0.1); padding: 0 40px; }
.logo { font-size: 20px; font-weight: bold; color: #409eff; cursor: pointer; margin-right: 40px; }
.user-area { margin-left: auto; display: flex; align-items: center; gap: 10px; }
.user-info { display: flex; align-items: center; gap: 8px; cursor: pointer; }
.main { flex: 1; padding: 20px 40px; max-width: 1400px; margin: 0 auto; width: 100%; }
.footer { text-align: center; color: #999; background: #fff; }
</style>
