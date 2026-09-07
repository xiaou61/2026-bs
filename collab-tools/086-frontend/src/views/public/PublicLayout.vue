<template>
  <div class="public-layout">
    <header class="site-header">
      <div class="page-shell header-inner">
        <router-link class="brand" to="/">
          <span class="brand-mark"></span>
          <div>
            <div class="brand-title display-title">AURORA WALLPAPER</div>
            <div class="brand-sub">高清壁纸社区</div>
          </div>
        </router-link>
        <nav class="nav-links">
          <router-link to="/">首页</router-link>
          <router-link to="/discover">发现</router-link>
          <router-link v-if="userStore.token" to="/favorites">我的收藏</router-link>
          <router-link v-if="userStore.token" to="/my-uploads">我的投稿</router-link>
          <router-link v-if="userStore.userInfo?.role === 'admin'" to="/admin/dashboard">后台管理</router-link>
        </nav>
        <div class="header-actions">
          <template v-if="userStore.token">
            <span class="user-name">{{ userStore.userInfo?.realName || userStore.userInfo?.username }}</span>
            <el-button text @click="handleLogout">退出</el-button>
          </template>
          <template v-else>
            <router-link to="/login"><el-button text>登录</el-button></router-link>
            <router-link to="/register"><el-button type="primary" round>注册</el-button></router-link>
          </template>
        </div>
      </div>
    </header>

    <main>
      <router-view />
    </main>

    <footer class="site-footer">
      <div class="page-shell footer-inner">
        <div>
          <div class="display-title footer-brand">AURORA WALLPAPER</div>
          <div class="footer-text">提供桌面与手机端高清壁纸浏览、收藏、投稿与管理能力</div>
        </div>
        <div class="footer-text">086 项目 壁纸网站设计与实现</div>
      </div>
    </footer>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { ElMessageBox } from 'element-plus'
import { logout } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const router = useRouter()

const handleLogout = () => {
  ElMessageBox.confirm('确定退出当前账号吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await logout()
    } catch (e) {}
    userStore.logout()
    router.push('/')
  }).catch(() => {})
}
</script>

<style scoped>
.public-layout {
  min-height: 100vh;
}

.site-header {
  position: sticky;
  top: 0;
  z-index: 20;
  border-bottom: 1px solid rgba(255, 255, 255, 0.08);
  background: rgba(4, 10, 20, 0.8);
  backdrop-filter: blur(18px);
}

.header-inner {
  height: 80px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 24px;
}

.brand {
  display: flex;
  align-items: center;
  gap: 14px;
}

.brand-mark {
  width: 18px;
  height: 18px;
  border-radius: 50%;
  background: linear-gradient(135deg, #7dd3fc, #f59e0b);
  box-shadow: 0 0 24px rgba(125, 211, 252, 0.6);
}

.brand-title {
  font-size: 20px;
}

.brand-sub,
.footer-text,
.user-name {
  color: var(--text-sub);
  font-size: 13px;
}

.nav-links {
  display: flex;
  align-items: center;
  gap: 26px;
  font-size: 15px;
}

.nav-links a.router-link-active {
  color: var(--accent);
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 12px;
}

.site-footer {
  margin-top: 64px;
  padding: 32px 0 48px;
  border-top: 1px solid rgba(255, 255, 255, 0.08);
}

.footer-inner {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.footer-brand {
  margin-bottom: 8px;
}

@media (max-width: 1400px) {
  .nav-links {
    gap: 16px;
  }
}

@media (max-width: 900px) {
  .header-inner {
    height: auto;
    padding: 16px 0;
    flex-direction: column;
    align-items: flex-start;
  }

  .nav-links,
  .header-actions,
  .footer-inner {
    flex-wrap: wrap;
  }

  .footer-inner {
    gap: 16px;
    align-items: flex-start;
  }
}
</style>
