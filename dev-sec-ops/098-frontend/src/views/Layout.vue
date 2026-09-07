<template>
  <el-container class="layout">
    <el-aside :width="collapsed ? '72px' : '248px'" class="aside">
      <div class="logo">{{ collapsed ? 'K98' : 'KnowledgeQA 管理台' }}</div>
      <el-menu :default-active="$route.path" :collapse="collapsed" router background-color="#10231f" text-color="#cbd5e1" active-text-color="#5eead4">
        <el-menu-item index="/dashboard"><el-icon><DataAnalysis /></el-icon><span>首页看板</span></el-menu-item>
        <el-menu-item v-if="role === 'ADMIN'" index="/user"><el-icon><User /></el-icon><span>用户管理</span></el-menu-item>
        <el-menu-item index="/space"><el-icon><FolderOpened /></el-icon><span>知识空间</span></el-menu-item>
        <el-menu-item index="/category"><el-icon><CollectionTag /></el-icon><span>文档分类</span></el-menu-item>
        <el-menu-item index="/document"><el-icon><Document /></el-icon><span>知识文档</span></el-menu-item>
        <el-menu-item index="/chunk"><el-icon><Tickets /></el-icon><span>文档分段</span></el-menu-item>
        <el-menu-item v-if="role === 'ADMIN'" index="/group"><el-icon><UserFilled /></el-icon><span>权限组</span></el-menu-item>
        <el-menu-item v-if="role === 'ADMIN'" index="/member"><el-icon><Avatar /></el-icon><span>组成员</span></el-menu-item>
        <el-menu-item v-if="role === 'ADMIN'" index="/permission"><el-icon><Key /></el-icon><span>文档授权</span></el-menu-item>
        <el-menu-item index="/session"><el-icon><ChatLineRound /></el-icon><span>问答会话</span></el-menu-item>
        <el-menu-item index="/record"><el-icon><ChatDotRound /></el-icon><span>问答记录</span></el-menu-item>
        <el-menu-item index="/feedback"><el-icon><Service /></el-icon><span>问答反馈</span></el-menu-item>
        <el-menu-item v-if="role === 'ADMIN'" index="/log"><el-icon><Notebook /></el-icon><span>访问日志</span></el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <div class="header-left">
          <el-button text :icon="collapsed ? Expand : Fold" @click="collapsed = !collapsed" />
          <span>知识入库、权限授权、智能问答和来源追踪</span>
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
import { Avatar, ChatDotRound, ChatLineRound, CollectionTag, DataAnalysis, Document, Expand, FolderOpened, Fold, Key, Notebook, Service, Tickets, User, UserFilled } from '@element-plus/icons-vue'
import { logout } from '../api'
import { useUserStore } from '../store/user'

const router = useRouter()
const userStore = useUserStore()
const collapsed = ref(false)
const role = computed(() => userStore.user?.role)
const roleLabel = computed(() => {
  if (role.value === 'ADMIN') return '管理员'
  if (role.value === 'EDITOR') return '编辑员'
  if (role.value === 'STAFF') return '员工'
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
  background: #10231f;
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
