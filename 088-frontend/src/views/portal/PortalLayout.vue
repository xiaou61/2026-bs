<template>
  <div class="portal-shell">
    <header class="portal-header">
      <div class="portal-container header-inner">
        <router-link class="brand" to="/">
          <div class="brand-badge">088</div>
          <div>
            <div class="brand-title">孩童收养信息管理系统</div>
            <div class="brand-sub">Child Adoption Portal</div>
          </div>
        </router-link>
        <nav class="nav-links">
          <router-link to="/">首页</router-link>
          <router-link to="/children">孩童信息</router-link>
          <router-link to="/notices">公告资讯</router-link>
          <router-link v-if="userStore.token" to="/profile">我的资料</router-link>
          <router-link v-if="userStore.token" to="/application">收养申请</router-link>
          <router-link v-if="userStore.token" to="/progress">申请进度</router-link>
          <router-link v-if="isStaff" to="/admin/dashboard">后台管理</router-link>
        </nav>
        <div class="header-actions">
          <template v-if="userStore.token">
            <span class="user-text">{{ userStore.displayName }}</span>
            <el-button text @click="handleLogout">退出</el-button>
          </template>
          <template v-else>
            <router-link to="/login"><el-button text>登录</el-button></router-link>
            <router-link to="/register"><el-button type="primary">注册</el-button></router-link>
          </template>
        </div>
      </div>
    </header>
    <main>
      <router-view />
    </main>
    <footer class="portal-footer">
      <div class="portal-container footer-inner">
        <div>
          <div class="footer-title">为孩子寻找更稳定的成长归宿</div>
          <div class="footer-text">覆盖孩童信息展示、申请审核、匹配审批、签约与回访跟踪</div>
        </div>
        <div class="footer-text">088 项目 完整版毕设演示</div>
      </div>
    </footer>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessageBox } from 'element-plus'
import { logout } from '../../api'
import { useUserStore } from '../../store/user'

const router = useRouter()
const userStore = useUserStore()
const isStaff = computed(() => ['admin', 'reviewer'].includes(userStore.role))

const handleLogout = () => {
  ElMessageBox.confirm('确定退出当前账号吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await logout()
    } catch (error) {}
    userStore.logout()
    router.push('/')
  }).catch(() => {})
}
</script>

<style scoped>
.portal-header {
  position: sticky;
  top: 0;
  z-index: 20;
  backdrop-filter: blur(16px);
  background: rgba(249, 246, 239, 0.92);
  border-bottom: 1px solid rgba(146, 64, 14, 0.12);
}

.portal-container {
  width: min(1180px, calc(100vw - 32px));
  margin: 0 auto;
}

.header-inner,
.footer-inner {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 18px;
}

.header-inner {
  min-height: 76px;
}

.brand {
  display: flex;
  align-items: center;
  gap: 14px;
}

.brand-badge {
  width: 54px;
  height: 54px;
  border-radius: 18px;
  display: grid;
  place-items: center;
  background: linear-gradient(135deg, #f59e0b, #0f766e);
  color: #fff;
  font-family: "STZhongsong", "Noto Serif SC", serif;
}

.brand-title,
.footer-title {
  font-size: 22px;
  color: var(--brand-strong);
  font-family: "STZhongsong", "Noto Serif SC", serif;
}

.brand-sub,
.footer-text,
.user-text {
  font-size: 13px;
  color: var(--subtle);
}

.nav-links,
.header-actions {
  display: flex;
  align-items: center;
  gap: 18px;
}

.portal-footer {
  margin-top: 56px;
  border-top: 1px solid rgba(146, 64, 14, 0.12);
  padding: 28px 0 40px;
}

@media (max-width: 900px) {
  .header-inner,
  .footer-inner {
    flex-direction: column;
    align-items: flex-start;
    padding: 16px 0;
  }

  .nav-links,
  .header-actions {
    flex-wrap: wrap;
  }
}
</style>
