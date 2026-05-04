<template>
  <el-container class="layout">
    <el-aside :width="collapsed ? '72px' : '248px'" class="aside">
      <div class="logo">{{ collapsed ? 'A100' : '学术诚信预警台' }}</div>
      <el-menu :default-active="$route.path" :collapse="collapsed" router background-color="#111827" text-color="#cbd5e1" active-text-color="#6ee7b7">
        <el-menu-item index="/dashboard"><el-icon><DataAnalysis /></el-icon><span>首页看板</span></el-menu-item>
        <el-menu-item v-if="role === 'ADMIN'" index="/user"><el-icon><User /></el-icon><span>用户管理</span></el-menu-item>
        <el-menu-item v-if="canTeach" index="/course"><el-icon><Reading /></el-icon><span>课程管理</span></el-menu-item>
        <el-menu-item v-if="canTeach" index="/class"><el-icon><OfficeBuilding /></el-icon><span>班级管理</span></el-menu-item>
        <el-menu-item v-if="canTeach" index="/student"><el-icon><Avatar /></el-icon><span>学生档案</span></el-menu-item>
        <el-menu-item v-if="canTeach" index="/assignment"><el-icon><Document /></el-icon><span>作业任务</span></el-menu-item>
        <el-menu-item v-if="canStudent" index="/submission"><el-icon><EditPen /></el-icon><span>文本提交</span></el-menu-item>
        <el-menu-item v-if="role === 'ADMIN'" index="/rule"><el-icon><Operation /></el-icon><span>检测规则</span></el-menu-item>
        <el-menu-item v-if="canReview" index="/task"><el-icon><Tickets /></el-icon><span>检测任务</span></el-menu-item>
        <el-menu-item v-if="canReview" index="/result"><el-icon><Checked /></el-icon><span>检测结果</span></el-menu-item>
        <el-menu-item v-if="canReview" index="/warning"><el-icon><Warning /></el-icon><span>诚信预警</span></el-menu-item>
        <el-menu-item v-if="canStudent" index="/rectification"><el-icon><RefreshRight /></el-icon><span>整改跟踪</span></el-menu-item>
        <el-menu-item v-if="canStudent" index="/appeal"><el-icon><ChatDotRound /></el-icon><span>申诉管理</span></el-menu-item>
        <el-menu-item v-if="role === 'ADMIN'" index="/log"><el-icon><Notebook /></el-icon><span>操作日志</span></el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <div class="header-left">
          <el-button text :icon="collapsed ? Expand : Fold" @click="collapsed = !collapsed" />
          <span>文本提交、AI概率检测、人工复核、预警整改和申诉闭环</span>
        </div>
        <div class="header-right">
          <el-tag>{{ roleLabel }}</el-tag>
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
import { Avatar, ChatDotRound, Checked, DataAnalysis, Document, EditPen, Expand, Fold, Notebook, OfficeBuilding, Operation, Reading, RefreshRight, Tickets, User, Warning } from '@element-plus/icons-vue'
import { logout } from '../api'
import { useUserStore } from '../store/user'

const router = useRouter()
const userStore = useUserStore()
const collapsed = ref(false)
const role = computed(() => userStore.user?.role)
const canTeach = computed(() => role.value === 'ADMIN' || role.value === 'TEACHER')
const canReview = computed(() => role.value === 'ADMIN' || role.value === 'TEACHER' || role.value === 'REVIEWER')
const canStudent = computed(() => role.value === 'ADMIN' || role.value === 'TEACHER' || role.value === 'REVIEWER' || role.value === 'STUDENT')
const roleLabel = computed(() => {
  if (role.value === 'ADMIN') return '管理员'
  if (role.value === 'TEACHER') return '教师'
  if (role.value === 'STUDENT') return '学生'
  if (role.value === 'REVIEWER') return '复核员'
  return '访客'
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
.layout {
  height: 100vh;
}

.aside {
  background: #111827;
  transition: width 0.2s;
  overflow: hidden;
}

.logo {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #f8fafc;
  border-bottom: 1px solid rgba(255, 255, 255, 0.08);
  font-weight: 800;
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: #fff;
  border-bottom: 1px solid #e5e7eb;
}

.header-left,
.header-right {
  display: flex;
  align-items: center;
  gap: 12px;
}

.header-left span {
  color: #64748b;
}

.main {
  padding: 16px;
  background: #f6f8fb;
}
</style>
