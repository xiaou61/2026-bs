<template>
  <el-container class="layout">
    <el-aside :width="collapsed ? '72px' : '248px'" class="aside">
      <div class="logo">{{ collapsed ? 'A99' : 'AIGC 版权存证台' }}</div>
      <el-menu :default-active="$route.path" :collapse="collapsed" router background-color="#111827" text-color="#cbd5e1" active-text-color="#93c5fd">
        <el-menu-item index="/dashboard"><el-icon><DataAnalysis /></el-icon><span>首页看板</span></el-menu-item>
        <el-menu-item v-if="role === 'ADMIN'" index="/user"><el-icon><User /></el-icon><span>用户管理</span></el-menu-item>
        <el-menu-item v-if="canCreate" index="/asset"><el-icon><Picture /></el-icon><span>图片作品</span></el-menu-item>
        <el-menu-item v-if="role === 'ADMIN'" index="/rule"><el-icon><Operation /></el-icon><span>审核规则</span></el-menu-item>
        <el-menu-item v-if="canAudit" index="/task"><el-icon><Tickets /></el-icon><span>审核任务</span></el-menu-item>
        <el-menu-item v-if="canAudit" index="/result"><el-icon><Checked /></el-icon><span>审核结果</span></el-menu-item>
        <el-menu-item v-if="role === 'ADMIN'" index="/tag"><el-icon><CollectionTag /></el-icon><span>风险标签</span></el-menu-item>
        <el-menu-item v-if="canCreate" index="/register"><el-icon><DocumentChecked /></el-icon><span>版权登记</span></el-menu-item>
        <el-menu-item v-if="canCreate" index="/evidence"><el-icon><Files /></el-icon><span>电子存证</span></el-menu-item>
        <el-menu-item v-if="canCreate" index="/license"><el-icon><Key /></el-icon><span>授权记录</span></el-menu-item>
        <el-menu-item v-if="canCreate" index="/clue"><el-icon><Warning /></el-icon><span>侵权线索</span></el-menu-item>
        <el-menu-item v-if="canCreate" index="/appeal"><el-icon><ChatDotRound /></el-icon><span>申诉处理</span></el-menu-item>
        <el-menu-item v-if="role === 'ADMIN'" index="/log"><el-icon><Notebook /></el-icon><span>操作日志</span></el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <div class="header-left">
          <el-button text :icon="collapsed ? Expand : Fold" @click="collapsed = !collapsed" />
          <span>生成图片审核、登记、存证、授权与侵权申诉闭环</span>
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
import { ChatDotRound, Checked, CollectionTag, DataAnalysis, DocumentChecked, Expand, Files, Fold, Key, Notebook, Operation, Picture, Tickets, User, Warning } from '@element-plus/icons-vue'
import { logout } from '../api'
import { useUserStore } from '../store/user'

const router = useRouter()
const userStore = useUserStore()
const collapsed = ref(false)
const role = computed(() => userStore.user?.role)
const canAudit = computed(() => role.value === 'ADMIN' || role.value === 'AUDITOR')
const canCreate = computed(() => role.value === 'ADMIN' || role.value === 'AUDITOR' || role.value === 'CREATOR')
const roleLabel = computed(() => {
  if (role.value === 'ADMIN') return '管理员'
  if (role.value === 'AUDITOR') return '审核员'
  if (role.value === 'CREATOR') return '创作者'
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
