<template>
  <el-container style="height: 100vh">
    <el-aside :width="collapse ? '64px' : '240px'" class="aside">
      <div class="logo">{{ collapse ? '物业' : '名城小区物业' }}</div>
      <el-menu :default-active="$route.path" :collapse="collapse" router background-color="#0f172a" text-color="#cbd5e1" active-text-color="#22d3ee">
        <el-menu-item v-if="isStaffRole" index="/dashboard">运营看板</el-menu-item>
        <el-menu-item v-if="isAdmin" index="/user">用户管理</el-menu-item>
        <el-menu-item v-if="isStaffRole" index="/building">楼栋管理</el-menu-item>
        <el-menu-item v-if="isStaffRole" index="/house">房屋管理</el-menu-item>
        <el-menu-item index="/fee">物业缴费</el-menu-item>
        <el-menu-item index="/repair">报修工单</el-menu-item>
        <el-menu-item index="/complaint">投诉建议</el-menu-item>
        <el-menu-item index="/visitor">访客登记</el-menu-item>
        <el-menu-item v-if="isStaffRole" index="/parking">车位管理</el-menu-item>
        <el-menu-item index="/announcement">公告中心</el-menu-item>
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
const isStaff = computed(() => userStore.user?.role === 'STAFF')
const isStaffRole = computed(() => isAdmin.value || isStaff.value)
const roleText = computed(() => {
  if (isAdmin.value) return '管理员'
  if (isStaff.value) return '物业人员'
  return '业主'
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
