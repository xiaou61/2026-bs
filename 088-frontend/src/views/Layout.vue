<template>
  <div class="shell">
    <aside class="sidebar">
      <div class="brand">
        <div class="brand-mark">088</div>
        <div>
          <div class="brand-title">收养管理后台</div>
          <div class="brand-subtitle">Adoption Operation Console</div>
        </div>
      </div>
      <el-menu class="menu" :default-active="route.path" router>
        <el-menu-item v-for="item in menus" :key="item.path" :index="item.path">
          {{ item.label }}
        </el-menu-item>
      </el-menu>
      <div class="sidebar-card">
        <div class="sidebar-label">当前身份</div>
        <div class="sidebar-role">{{ roleLabelMap[userStore.role] || '访客' }}</div>
      </div>
    </aside>
    <main class="main">
      <header class="topbar">
        <div>
          <div class="topbar-title">{{ currentTitle }}</div>
          <div class="topbar-subtitle">围绕申请审核、匹配签约和回访跟踪的完整业务闭环</div>
        </div>
        <div class="topbar-user">
          <span class="user-chip">{{ userStore.displayName }}</span>
          <el-button class="logout-button" @click="handleLogout">退出登录</el-button>
        </div>
      </header>
      <section class="content">
        <router-view />
      </section>
    </main>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessageBox } from 'element-plus'
import { logout } from '../api'
import { useUserStore } from '../store/user'
import { roleLabelMap } from '../utils'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const menuMap = {
  admin: [
    { path: '/admin/dashboard', label: '数据看板' },
    { path: '/admin/child', label: '儿童档案' },
    { path: '/admin/adopter', label: '申请人档案' },
    { path: '/admin/application', label: '收养申请' },
    { path: '/admin/review', label: '家访评估' },
    { path: '/admin/match', label: '匹配审批' },
    { path: '/admin/agreement', label: '协议管理' },
    { path: '/admin/follow', label: '回访记录' },
    { path: '/admin/notice', label: '公告中心' }
  ],
  reviewer: [
    { path: '/admin/dashboard', label: '工作看板' },
    { path: '/admin/application', label: '收养申请' },
    { path: '/admin/review', label: '家访评估' },
    { path: '/admin/match', label: '匹配审批' },
    { path: '/admin/agreement', label: '协议管理' },
    { path: '/admin/follow', label: '回访记录' },
    { path: '/admin/notice', label: '公告中心' }
  ]
}

const menus = computed(() => menuMap[userStore.role] || [])
const currentTitle = computed(() => menus.value.find((item) => item.path === route.path)?.label || '收养管理后台')

const handleLogout = () => {
  ElMessageBox.confirm('确定退出登录吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await logout()
    } catch (error) {}
    userStore.logout()
    router.push('/login')
  }).catch(() => {})
}
</script>

<style scoped>
.shell {
  min-height: 100vh;
  display: grid;
  grid-template-columns: 280px 1fr;
}

.sidebar {
  padding: 28px 22px;
  background: linear-gradient(180deg, rgba(255, 247, 237, 0.96), rgba(236, 253, 245, 0.84));
  border-right: 1px solid var(--line);
  display: flex;
  flex-direction: column;
  gap: 22px;
}

.brand {
  display: flex;
  align-items: center;
  gap: 14px;
}

.brand-mark {
  width: 56px;
  height: 56px;
  border-radius: 20px;
  display: grid;
  place-items: center;
  background: linear-gradient(135deg, #ea580c, #0f766e);
  color: #fff;
  font-family: "STZhongsong", "Noto Serif SC", serif;
}

.brand-title {
  font-size: 24px;
  color: var(--brand-strong);
  font-family: "STZhongsong", "Noto Serif SC", serif;
}

.brand-subtitle,
.sidebar-label,
.topbar-subtitle {
  color: var(--subtle);
}

.menu {
  border: none;
  background: transparent;
}

.menu :deep(.el-menu-item) {
  margin-bottom: 8px;
  border-radius: 14px;
  color: #5b4a3a;
  font-weight: 600;
}

.menu :deep(.el-menu-item.is-active) {
  color: #fff;
  background: linear-gradient(135deg, #ea580c, #0f766e);
}

.sidebar-card {
  margin-top: auto;
  padding: 18px;
  border-radius: 20px;
  background: rgba(255, 255, 255, 0.78);
  box-shadow: var(--shadow);
}

.sidebar-role {
  margin-top: 8px;
  font-size: 24px;
  font-family: "STZhongsong", "Noto Serif SC", serif;
  color: var(--brand-strong);
}

.main {
  padding: 28px;
}

.topbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.topbar-title {
  font-size: 30px;
  font-family: "STZhongsong", "Noto Serif SC", serif;
}

.topbar-user {
  display: flex;
  align-items: center;
  gap: 14px;
}

.user-chip {
  padding: 10px 14px;
  border-radius: 999px;
  background: rgba(255, 255, 255, 0.85);
}

.logout-button {
  border: none;
  color: #fff;
  background: linear-gradient(135deg, #ea580c, #c2410c);
}

.content {
  min-height: calc(100vh - 130px);
  padding: 22px;
  border-radius: 32px;
  background: var(--paper);
  box-shadow: var(--shadow);
}

@media (max-width: 960px) {
  .shell {
    grid-template-columns: 1fr;
  }

  .topbar {
    flex-direction: column;
    align-items: flex-start;
    gap: 16px;
  }
}
</style>
