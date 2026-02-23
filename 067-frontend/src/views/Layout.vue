<template>
  <el-container style="height: 100vh">
    <el-aside :width="collapse ? '64px' : '240px'" class="aside">
      <div class="logo">{{ collapse ? '一体化' : '大学生一体化平台' }}</div>
      <el-menu :default-active="$route.path" :collapse="collapse" router background-color="#0f172a" text-color="#cbd5e1" active-text-color="#22d3ee">
        <el-menu-item v-if="isStaff" index="/dashboard">运营看板</el-menu-item>
        <el-menu-item v-if="isAdmin" index="/user">用户管理</el-menu-item>
        <el-menu-item index="/course">课程中心</el-menu-item>
        <el-menu-item index="/enroll">选课记录</el-menu-item>
        <el-menu-item index="/activity">活动中心</el-menu-item>
        <el-menu-item index="/signup">活动报名</el-menu-item>
        <el-menu-item index="/job">实习岗位</el-menu-item>
        <el-menu-item index="/apply">岗位投递</el-menu-item>
        <el-menu-item index="/lost">失物招领</el-menu-item>
        <el-menu-item index="/notice">公告中心</el-menu-item>
        <el-menu-item index="/profile">个人中心</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <el-button text @click="collapse = !collapse">{{ collapse ? '展开' : '收起' }}</el-button>
        <div class="right">
          <el-tag>{{ roleText }}</el-tag>
          <span>{{ userStore.user?.nickname || userStore.user?.username }}</span>
          <el-button link type="danger" @click="handleLogout">退出</el-button>
        </div>
      </el-header>
      <el-main class="main">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed, ref } from 'vue'
import { useRouter } from 'vue-router'
import { logout } from '../api'
import { useUserStore } from '../store/user'

const router = useRouter()
const userStore = useUserStore()
const collapse = ref(false)
const isAdmin = computed(() => userStore.user?.role === 'ADMIN')
const isTeacher = computed(() => userStore.user?.role === 'TEACHER')
const isStaff = computed(() => isAdmin.value || isTeacher.value)
const roleText = computed(() => {
  if (isAdmin.value) return '管理员'
  if (isTeacher.value) return '教师'
  return '学生'
})

const handleLogout = async () => {
  try {
    await logout()
  } catch (e) {
  }
  userStore.logout()
  router.push('/login')
}
</script>

<style scoped>
.aside {
  background: #0f172a;
}

.logo {
  height: 60px;
  color: #f8fafc;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 18px;
  font-weight: 700;
  border-bottom: 1px solid rgba(255, 255, 255, 0.08);
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #e2e8f0;
  background: #fff;
}

.right {
  display: flex;
  align-items: center;
  gap: 12px;
}

.main {
  background: #f1f5f9;
  padding: 14px;
}
</style>
