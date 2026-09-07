<template>
  <div class="shell">
    <header class="topbar">
      <div class="brand" @click="router.push('/')">
        <div class="brand-mark">M</div>
        <div>
          <h1>校园二手交易平台</h1>
          <p>让闲置在校园里继续发光</p>
        </div>
      </div>

      <nav class="nav">
        <button
          v-for="item in navItems"
          :key="item.path"
          class="nav-item"
          :class="{ active: route.path === item.path }"
          type="button"
          @click="router.push(item.path)"
        >
          {{ item.label }}
        </button>
      </nav>

      <div class="actions">
        <template v-if="userStore.isLoggedIn">
          <div class="credit-card">
            <span>信用分</span>
            <strong>{{ userStore.userInfo.creditScore ?? 100 }}</strong>
          </div>

          <el-dropdown trigger="click">
            <div class="user-entry">
              <el-avatar :size="36" :src="userStore.userInfo.avatar || ''">
                {{ displayName.slice(0, 1) }}
              </el-avatar>
              <div class="user-meta">
                <strong>{{ displayName }}</strong>
                <span>{{ userStore.userInfo.college || '校园用户' }}</span>
              </div>
              <el-icon><ArrowDown /></el-icon>
            </div>

            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="router.push('/publish')">发布商品</el-dropdown-item>
                <el-dropdown-item @click="router.push('/my-products')">我的发布</el-dropdown-item>
                <el-dropdown-item @click="router.push('/favorites')">我的收藏</el-dropdown-item>
                <el-dropdown-item @click="router.push('/chat')">消息中心</el-dropdown-item>
                <el-dropdown-item divided @click="handleLogout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </template>

        <template v-else>
          <el-button plain @click="router.push('/login')">登录</el-button>
          <el-button type="primary" @click="router.push('/register')">注册</el-button>
        </template>
      </div>
    </header>

    <section class="hero">
      <div>
        <span class="hero-tag">Campus Loop</span>
        <h2>更轻松的校内交易，更可信的同学连接。</h2>
        <p>
          支持商品发布、收藏、议价、订单跟踪和消息沟通，把校园里的二手流转做成一条顺手的完整链路。
        </p>
      </div>
      <div class="hero-metrics">
        <div>
          <strong>6</strong>
          <span>预置分类</span>
        </div>
        <div>
          <strong>3</strong>
          <span>订单状态</span>
        </div>
        <div>
          <strong>24h</strong>
          <span>登录有效期</span>
        </div>
      </div>
    </section>

    <main class="page-main">
      <router-view />
    </main>
  </div>
</template>

<script setup>
import { computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const navItems = [
  { path: '/', label: '首页' },
  { path: '/publish', label: '发布商品' },
  { path: '/my-products', label: '我的发布' },
  { path: '/favorites', label: '我的收藏' },
  { path: '/chat', label: '消息中心' },
  { path: '/profile', label: '个人中心' }
]

const displayName = computed(() => {
  return userStore.userInfo.realName || userStore.userInfo.username || '校园用户'
})

const handleLogout = () => {
  userStore.logout()
  ElMessage.success('已退出登录')
  router.push('/login')
}

onMounted(async () => {
  if (userStore.isLoggedIn && !userStore.userInfo.id) {
    try {
      await userStore.getUserInfo()
    } catch (error) {
      userStore.logout()
    }
  }
})
</script>

<style scoped>
.shell {
  min-height: 100vh;
  background:
    radial-gradient(circle at top left, rgba(254, 233, 196, 0.9), transparent 28%),
    radial-gradient(circle at top right, rgba(192, 233, 252, 0.7), transparent 24%),
    linear-gradient(180deg, #fffaf3 0%, #f4f7fb 50%, #eef2f7 100%);
  color: #1f2a37;
}

.topbar {
  position: sticky;
  top: 0;
  z-index: 20;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 24px;
  padding: 18px 32px;
  backdrop-filter: blur(16px);
  background: rgba(255, 255, 255, 0.86);
  border-bottom: 1px solid rgba(15, 23, 42, 0.08);
}

.brand {
  display: flex;
  align-items: center;
  gap: 14px;
  cursor: pointer;
}

.brand-mark {
  width: 46px;
  height: 46px;
  border-radius: 16px;
  display: grid;
  place-items: center;
  font-size: 22px;
  font-weight: 700;
  color: #fff;
  background: linear-gradient(135deg, #ff8f5a 0%, #ff5b6b 100%);
  box-shadow: 0 14px 24px rgba(255, 91, 107, 0.22);
}

.brand h1 {
  font-size: 20px;
  margin-bottom: 4px;
}

.brand p {
  font-size: 13px;
  color: #6b7280;
}

.nav {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.nav-item {
  border: 0;
  background: transparent;
  color: #4b5563;
  padding: 10px 14px;
  border-radius: 999px;
  cursor: pointer;
  transition: all 0.2s ease;
}

.nav-item:hover,
.nav-item.active {
  background: #111827;
  color: #fff;
}

.actions {
  display: flex;
  align-items: center;
  gap: 12px;
}

.credit-card {
  padding: 10px 14px;
  border-radius: 16px;
  background: rgba(17, 24, 39, 0.06);
  display: flex;
  align-items: baseline;
  gap: 8px;
}

.credit-card span {
  font-size: 12px;
  color: #6b7280;
}

.credit-card strong {
  font-size: 20px;
}

.user-entry {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 6px 10px 6px 6px;
  border-radius: 999px;
  background: rgba(255, 255, 255, 0.8);
  border: 1px solid rgba(15, 23, 42, 0.08);
  cursor: pointer;
}

.user-meta {
  display: flex;
  flex-direction: column;
}

.user-meta strong {
  font-size: 14px;
}

.user-meta span {
  font-size: 12px;
  color: #6b7280;
}

.hero {
  max-width: 1280px;
  margin: 0 auto;
  padding: 36px 32px 24px;
  display: grid;
  grid-template-columns: minmax(0, 1.5fr) minmax(300px, 0.9fr);
  gap: 24px;
}

.hero-tag {
  display: inline-flex;
  align-items: center;
  padding: 6px 12px;
  border-radius: 999px;
  background: rgba(255, 140, 90, 0.14);
  color: #c2410c;
  font-size: 13px;
  margin-bottom: 16px;
}

.hero h2 {
  font-size: 36px;
  line-height: 1.2;
  margin-bottom: 14px;
}

.hero p {
  max-width: 760px;
  color: #4b5563;
  line-height: 1.8;
}

.hero-metrics {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 14px;
}

.hero-metrics div {
  background: rgba(255, 255, 255, 0.72);
  border: 1px solid rgba(148, 163, 184, 0.18);
  border-radius: 22px;
  padding: 20px;
  min-height: 120px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.hero-metrics strong {
  font-size: 34px;
}

.hero-metrics span {
  font-size: 13px;
  color: #6b7280;
}

.page-main {
  max-width: 1280px;
  margin: 0 auto;
  padding: 0 32px 40px;
}

@media (max-width: 1080px) {
  .topbar,
  .hero,
  .page-main {
    padding-left: 20px;
    padding-right: 20px;
  }

  .topbar {
    flex-wrap: wrap;
  }

  .hero {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 720px) {
  .topbar {
    gap: 16px;
  }

  .nav {
    width: 100%;
    overflow-x: auto;
    padding-bottom: 4px;
  }

  .actions {
    width: 100%;
    justify-content: space-between;
  }

  .hero h2 {
    font-size: 28px;
  }

  .hero-metrics {
    grid-template-columns: 1fr;
  }
}
</style>
