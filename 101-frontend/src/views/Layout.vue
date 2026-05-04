<template>
  <el-container class="layout">
    <el-aside :width="collapsed ? '72px' : '248px'" class="aside">
      <div class="logo">{{ collapsed ? 'R101' : '招聘匹配管理台' }}</div>
      <el-menu :default-active="$route.path" :collapse="collapsed" router background-color="#111827" text-color="#cbd5e1" active-text-color="#67e8f9">
        <el-menu-item index="/dashboard"><el-icon><DataAnalysis /></el-icon><span>首页看板</span></el-menu-item>
        <el-menu-item v-if="role === 'ADMIN'" index="/user"><el-icon><User /></el-icon><span>用户管理</span></el-menu-item>
        <el-menu-item v-if="canCandidate" index="/candidate"><el-icon><Avatar /></el-icon><span>候选人档案</span></el-menu-item>
        <el-menu-item v-if="canCandidate" index="/resume"><el-icon><Document /></el-icon><span>简历材料</span></el-menu-item>
        <el-menu-item v-if="canCandidate" index="/certificate"><el-icon><Tickets /></el-icon><span>证书材料</span></el-menu-item>
        <el-menu-item v-if="canHr" index="/job"><el-icon><Briefcase /></el-icon><span>岗位管理</span></el-menu-item>
        <el-menu-item v-if="canHr" index="/requirement"><el-icon><CollectionTag /></el-icon><span>岗位要求</span></el-menu-item>
        <el-menu-item v-if="canHr" index="/parse-task"><el-icon><Files /></el-icon><span>解析任务</span></el-menu-item>
        <el-menu-item v-if="canHr" index="/parse-result"><el-icon><Checked /></el-icon><span>解析结果</span></el-menu-item>
        <el-menu-item v-if="canHr" index="/match-task"><el-icon><Connection /></el-icon><span>匹配任务</span></el-menu-item>
        <el-menu-item v-if="canHr" index="/match-result"><el-icon><TrendCharts /></el-icon><span>匹配结果</span></el-menu-item>
        <el-menu-item v-if="canInterview" index="/interview"><el-icon><Calendar /></el-icon><span>面试排期</span></el-menu-item>
        <el-menu-item v-if="canInterview" index="/feedback"><el-icon><ChatDotRound /></el-icon><span>面试反馈</span></el-menu-item>
        <el-menu-item v-if="role === 'ADMIN'" index="/log"><el-icon><Notebook /></el-icon><span>操作日志</span></el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <div class="header-left">
          <el-button text :icon="collapsed ? Expand : Fold" @click="collapsed = !collapsed" />
          <span>简历解析、证书核验、岗位匹配、面试排期和反馈归档</span>
        </div>
        <div class="header-right">
          <el-tag>{{ roleLabel }}</el-tag>
          <span>{{ userStore.user?.nickname || userStore.user?.username }}</span>
          <el-button link type="danger" @click="handleLogout">退出</el-button>
        </div>
      </el-header>
      <el-main class="main"><router-view /></el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed, ref } from 'vue'
import { useRouter } from 'vue-router'
import { Avatar, Briefcase, Calendar, ChatDotRound, Checked, CollectionTag, Connection, DataAnalysis, Document, Expand, Files, Fold, Notebook, Tickets, TrendCharts, User } from '@element-plus/icons-vue'
import { logout } from '../api'
import { useUserStore } from '../store/user'

const router = useRouter()
const userStore = useUserStore()
const collapsed = ref(false)
const role = computed(() => userStore.user?.role)
const canHr = computed(() => role.value === 'ADMIN' || role.value === 'HR')
const canCandidate = computed(() => role.value === 'ADMIN' || role.value === 'HR' || role.value === 'CANDIDATE')
const canInterview = computed(() => role.value === 'ADMIN' || role.value === 'HR' || role.value === 'INTERVIEWER')
const roleLabel = computed(() => {
  if (role.value === 'ADMIN') return '管理员'
  if (role.value === 'HR') return 'HR'
  if (role.value === 'CANDIDATE') return '候选人'
  if (role.value === 'INTERVIEWER') return '面试官'
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
