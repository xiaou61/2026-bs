<template>
  <el-container class="layout">
    <el-aside :width="collapsed ? '72px' : '248px'" class="aside">
      <div class="logo">{{ collapsed ? 'P97' : 'PromptOps 评测平台' }}</div>
      <el-menu :default-active="$route.path" :collapse="collapsed" router background-color="#111827" text-color="#cbd5e1" active-text-color="#22d3ee">
        <el-menu-item index="/dashboard"><el-icon><DataAnalysis /></el-icon><span>首页看板</span></el-menu-item>
        <el-menu-item v-if="role === 'ADMIN'" index="/user"><el-icon><User /></el-icon><span>用户管理</span></el-menu-item>
        <el-menu-item v-if="role === 'ADMIN'" index="/team"><el-icon><OfficeBuilding /></el-icon><span>团队空间</span></el-menu-item>
        <el-menu-item v-if="role === 'ADMIN'" index="/category"><el-icon><CollectionTag /></el-icon><span>Prompt 分类</span></el-menu-item>
        <el-menu-item index="/asset"><el-icon><Tickets /></el-icon><span>Prompt 资产</span></el-menu-item>
        <el-menu-item index="/version"><el-icon><DocumentCopy /></el-icon><span>Prompt 版本</span></el-menu-item>
        <el-menu-item index="/case"><el-icon><Finished /></el-icon><span>测试用例</span></el-menu-item>
        <el-menu-item v-if="role === 'ADMIN'" index="/model"><el-icon><Cpu /></el-icon><span>模型配置</span></el-menu-item>
        <el-menu-item v-if="role === 'ADMIN'" index="/rule"><el-icon><ScaleToOriginal /></el-icon><span>评分规则</span></el-menu-item>
        <el-menu-item index="/evaluation"><el-icon><Operation /></el-icon><span>评测任务</span></el-menu-item>
        <el-menu-item index="/result"><el-icon><TrendCharts /></el-icon><span>评测结果</span></el-menu-item>
        <el-menu-item index="/feedback"><el-icon><ChatDotRound /></el-icon><span>反馈管理</span></el-menu-item>
        <el-menu-item v-if="role === 'ADMIN'" index="/log"><el-icon><Notebook /></el-icon><span>操作日志</span></el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <div class="header-left">
          <el-button text :icon="collapsed ? Expand : Fold" @click="collapsed = !collapsed" />
          <span>提示词资产、版本和评测闭环管理</span>
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
import { ChatDotRound, CollectionTag, Cpu, DataAnalysis, DocumentCopy, Expand, Finished, Fold, Notebook, OfficeBuilding, Operation, ScaleToOriginal, Tickets, TrendCharts, User } from '@element-plus/icons-vue'
import { logout } from '../api'
import { useUserStore } from '../store/user'

const router = useRouter()
const userStore = useUserStore()
const collapsed = ref(false)
const role = computed(() => userStore.user?.role)
const roleLabel = computed(() => {
  if (role.value === 'ADMIN') return '管理员'
  if (role.value === 'ENGINEER') return '工程师'
  if (role.value === 'REVIEWER') return '评审员'
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
  background: #f5f7fb;
}
</style>
