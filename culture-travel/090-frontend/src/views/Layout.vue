<template>
  <div class="shell">
    <aside class="sidebar">
      <div class="brand">
        <div class="brand-mark">090</div>
        <div>
          <div class="brand-title">戏曲文化苑系统</div>
          <div class="brand-subtitle">Opera Culture Garden</div>
        </div>
      </div>
      <el-menu class="menu" :default-active="activeMenu" router>
        <el-menu-item v-for="item in menus" :key="item.path" :index="item.path">
          <span>{{ item.label }}</span>
        </el-menu-item>
      </el-menu>
      <div class="sidebar-card">
        <div class="sidebar-label">当前身份</div>
        <div class="sidebar-role">{{ roleText }}</div>
        <div class="sidebar-note">依据角色自动切换菜单与操作权限</div>
      </div>
    </aside>
    <main class="main">
      <header class="topbar">
        <div>
          <div class="topbar-title">{{ currentTitle }}</div>
          <div class="topbar-subtitle">统一管理剧目、排期、预约、传承资源与过程数据</div>
        </div>
        <div class="topbar-user">
          <div class="user-name">{{ userStore.displayName }}</div>
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
import { computed, onMounted } from 'vue'
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
    { path: '/dashboard', label: '文化看板' },
    { path: '/category', label: '剧种分类管理' },
    { path: '/artist', label: '名家档案管理' },
    { path: '/venue', label: '场馆管理' },
    { path: '/season', label: '文化专题管理' },
    { path: '/repertoire', label: '剧目管理' },
    { path: '/performance', label: '排期与行程' },
    { path: '/booking', label: '预约记录' },
    { path: '/resource', label: '数字资源' },
    { path: '/checkin', label: '签到管理' },
    { path: '/review', label: '研学评分管理' },
    { path: '/comment', label: '剧目赏析互动' },
    { path: '/notice', label: '公告中心' }
  ],
  artist: [
    { path: '/dashboard', label: '文化看板' },
    { path: '/repertoire', label: '剧目列表' },
    { path: '/performance', label: '我的主讲' },
    { path: '/booking', label: '预约记录' },
    { path: '/resource', label: '数字资源' },
    { path: '/checkin', label: '签到管理' },
    { path: '/review', label: '研学评分管理' },
    { path: '/comment', label: '赏析互动结果' },
    { path: '/notice', label: '公告中心' }
  ],
  member: [
    { path: '/dashboard', label: '我的主页' },
    { path: '/repertoire', label: '剧目列表' },
    { path: '/performance', label: '我的行程' },
    { path: '/booking', label: '预约中心' },
    { path: '/resource', label: '数字资源' },
    { path: '/checkin', label: '签到记录' },
    { path: '/review', label: '研学评分查询' },
    { path: '/comment', label: '剧目赏析互动' },
    { path: '/notice', label: '公告中心' }
  ]
}

const activeMenu = computed(() => route.path)
const menus = computed(() => menuMap[userStore.role] || [])
const currentTitle = computed(() => menus.value.find((item) => item.path === route.path)?.label || '戏曲文化苑系统')
const roleText = computed(() => roleLabelMap[userStore.role] || '访客')

onMounted(async () => {
  if (!userStore.userInfo && userStore.token) {
    try {
      await userStore.fetchUserInfo()
    } catch (error) {
      userStore.logout()
      router.push('/login')
    }
  }
})

const handleLogout = () => {
  ElMessageBox.confirm('确定要退出登录吗？', '提示', {
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
  border-right: 1px solid var(--line);
  background: linear-gradient(180deg, rgba(255, 248, 235, 0.95), rgba(253, 245, 230, 0.82));
  position: sticky;
  top: 0;
  height: 100vh;
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
  border-radius: 18px;
  display: grid;
  place-items: center;
  background: linear-gradient(135deg, #7c2d12, #b45309);
  color: #fff;
  font-family: "STZhongsong", "Noto Serif SC", serif;
  font-size: 20px;
  letter-spacing: 1px;
}

.brand-title {
  font-size: 24px;
  line-height: 1.2;
  color: var(--brand-strong);
  font-family: "STZhongsong", "Noto Serif SC", serif;
}

.brand-subtitle {
  margin-top: 4px;
  color: var(--subtle);
  font-size: 12px;
  letter-spacing: 0.2em;
  text-transform: uppercase;
}

.menu {
  border: none;
  background: transparent;
}

.menu :deep(.el-menu-item) {
  margin-bottom: 8px;
  height: 48px;
  border-radius: 14px;
  color: #5b4a3a;
  font-weight: 600;
  background: transparent;
}

.menu :deep(.el-menu-item.is-active) {
  color: #fff;
  background: linear-gradient(135deg, #9a3412, #0f766e);
  box-shadow: 0 12px 24px rgba(15, 118, 110, 0.16);
}

.menu :deep(.el-menu-item:hover) {
  color: var(--brand);
  background: rgba(154, 52, 18, 0.08);
}

.sidebar-card {
  margin-top: auto;
  padding: 18px;
  border-radius: 20px;
  background: rgba(255, 255, 255, 0.78);
  box-shadow: var(--shadow);
}

.sidebar-label {
  color: var(--subtle);
  font-size: 13px;
}

.sidebar-role {
  margin-top: 8px;
  font-size: 24px;
  font-family: "STZhongsong", "Noto Serif SC", serif;
  color: var(--brand-strong);
}

.sidebar-note {
  margin-top: 10px;
  font-size: 13px;
  line-height: 1.7;
  color: #7c6f64;
}

.main {
  padding: 28px;
}

.topbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 22px;
}

.topbar-title {
  font-size: 30px;
  font-family: "STZhongsong", "Noto Serif SC", serif;
  color: #3f2f1f;
}

.topbar-subtitle {
  margin-top: 8px;
  color: var(--subtle);
}

.topbar-user {
  display: flex;
  align-items: center;
  gap: 16px;
}

.user-name {
  padding: 10px 14px;
  border-radius: 999px;
  background: rgba(255, 255, 255, 0.75);
  box-shadow: var(--shadow);
  color: #4b5563;
}

.logout-button {
  border: none;
  background: linear-gradient(135deg, #9a3412, #7c2d12);
  color: #fff;
}

.content {
  padding: 22px;
  min-height: calc(100vh - 130px);
  border-radius: 32px;
  background: var(--paper);
  backdrop-filter: blur(14px);
  box-shadow: var(--shadow);
}

@media (max-width: 960px) {
  .shell {
    grid-template-columns: 1fr;
  }

  .sidebar {
    position: static;
    height: auto;
  }

  .topbar {
    flex-direction: column;
    align-items: flex-start;
    gap: 16px;
  }

  .topbar-user {
    width: 100%;
    justify-content: space-between;
  }
}
</style>


