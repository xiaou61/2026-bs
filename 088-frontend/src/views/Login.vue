<template>
  <div class="auth-shell">
    <!-- 装饰背景 -->
    <div class="bg-decoration">
      <div class="deco deco-1">👶</div>
      <div class="deco deco-2">❤️</div>
      <div class="deco deco-3">🏠</div>
      <div class="deco deco-4">🌟</div>
    </div>

    <div class="auth-card">
      <div class="logo-section">
        <div class="logo-icon">👶</div>
        <div class="auth-eyebrow">Welcome Back</div>
        <h1>进入孩童收养信息平台</h1>
        <p class="auth-desc">用爱守护 · 温暖成长</p>
      </div>

      <el-form :model="form" @submit.prevent class="login-form">
        <el-form-item>
          <el-input v-model="form.username" placeholder="请输入用户名" prefix-icon="User" size="large" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.password" type="password" show-password placeholder="请输入密码" prefix-icon="Lock" size="large" />
        </el-form-item>
        <el-button class="auth-button" type="primary" size="large" @click="handleLogin">
          <el-icon><Key /></el-icon> 爱心登录
        </el-button>
      </el-form>

      <div class="auth-tip">
        <el-divider>其他操作</el-divider>
        <span class="hint-text">还没有账号？</span>
        <router-link to="/register" class="register-link">
          <el-icon><UserFilled /></el-icon> 立即注册
        </router-link>
      </div>

      <div class="love-tips">
        <span>❤️ 每个孩子都值得被爱 ❤️</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { login } from '../api'
import { useUserStore } from '../store/user'

const router = useRouter()
const userStore = useUserStore()
const form = reactive({
  username: '',
  password: ''
})

const handleLogin = async () => {
  const res = await login(form)
  userStore.setAuth(res.data.token, res.data.userInfo)
  ElMessage.success('登录成功')
  router.push(['admin', 'reviewer'].includes(res.data.userInfo.role) ? '/admin/dashboard' : '/')
}
</script>

<style scoped>
.auth-shell {
  min-height: 100vh;
  display: grid;
  place-items: center;
  padding: 24px;
  background: linear-gradient(135deg, #E91E63 0%, #F06292 30%, #F48FB1 60%, #FCE4EC 100%);
  position: relative;
  overflow: hidden;
}

.bg-decoration {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  pointer-events: none;
}

.deco {
  position: absolute;
  font-size: 48px;
  opacity: 0.15;
  animation: floatDeco 8s ease-in-out infinite;
}

.deco-1 { top: 15%; left: 10%; animation-delay: 0s; font-size: 56px; }
.deco-2 { top: 25%; right: 15%; animation-delay: 2s; }
.deco-3 { bottom: 25%; left: 20%; animation-delay: 4s; font-size: 56px; }
.deco-4 { top: 50%; right: 10%; animation-delay: 1s; font-size: 40px; }

@keyframes floatDeco {
  0%, 100% { transform: translateY(0) rotate(0deg); opacity: 0.15; }
  25% { transform: translateY(-15px) rotate(5deg); opacity: 0.25; }
  50% { transform: translateY(-8px) rotate(-3deg); opacity: 0.2; }
  75% { transform: translateY(-12px) rotate(3deg); opacity: 0.18; }
}

.auth-card {
  width: min(460px, 100%);
  padding: 48px 40px;
  border-radius: 28px;
  background: rgba(255, 255, 255, 0.95);
  box-shadow: 0 24px 64px rgba(233, 30, 99, 0.2);
  position: relative;
  z-index: 10;
  backdrop-filter: blur(20px);
  overflow: hidden;
}

.auth-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 6px;
  background: linear-gradient(90deg, #E91E63, #F06292, #F48FB1, #E91E63);
  background-size: 300% 100%;
  animation: loveGradient 4s linear infinite;
}

@keyframes loveGradient {
  0% { background-position: 0% 0%; }
  100% { background-position: 300% 0%; }
}

.logo-section {
  text-align: center;
  margin-bottom: 32px;
}

.logo-icon {
  font-size: 64px;
  margin-bottom: 12px;
  animation: heartbeat 1.5s ease-in-out infinite;
  display: inline-block;
}

@keyframes heartbeat {
  0%, 100% { transform: scale(1); }
  25% { transform: scale(1.1); }
  50% { transform: scale(1); }
  75% { transform: scale(1.05); }
}

.auth-eyebrow {
  font-size: 12px;
  letter-spacing: 0.22em;
  text-transform: uppercase;
  color: #E91E63;
  font-weight: 700;
}

.auth-card h1 {
  margin: 14px 0 8px;
  font-size: 28px;
  line-height: 1.2;
  font-family: "STZhongsong", "Noto Serif SC", serif;
  background: linear-gradient(135deg, #AD1457, #C2185B, #E91E63);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  font-weight: 800;
}

.auth-desc {
  color: #E91E63;
  font-size: 14px;
  letter-spacing: 4px;
  margin: 0;
}

.login-form {
  margin-top: 24px;
}

.login-form :deep(.el-input__wrapper) {
  border-radius: 12px !important;
  box-shadow: 0 2px 12px rgba(233, 30, 99, 0.08) !important;
  padding: 4px 12px;
  transition: all 0.3s ease;
}

.login-form :deep(.el-input__wrapper:hover) {
  box-shadow: 0 4px 16px rgba(233, 30, 99, 0.15) !important;
}

.login-form :deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 4px 20px rgba(233, 30, 99, 0.25) !important;
}

.login-form :deep(.el-input__prefix) {
  color: #E91E63;
}

.auth-button {
  width: 100%;
  height: 50px;
  font-size: 16px;
  font-weight: 600;
  letter-spacing: 4px;
  border-radius: 14px !important;
  background: linear-gradient(135deg, #AD1457 0%, #C2185B 50%, #E91E63 100%) !important;
  border: none !important;
  box-shadow: 0 8px 24px rgba(233, 30, 99, 0.3) !important;
  transition: all 0.3s ease !important;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
}

.auth-button:hover {
  transform: translateY(-3px);
  box-shadow: 0 12px 32px rgba(233, 30, 99, 0.4) !important;
}

.auth-tip {
  margin-top: 24px;
  text-align: center;
}

.auth-tip :deep(.el-divider__text) {
  background: rgba(255, 255, 255, 0.95);
  color: #999;
  font-size: 12px;
}

.hint-text {
  color: #666;
  font-size: 14px;
}

.register-link {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  color: #E91E63;
  text-decoration: none;
  font-weight: 600;
  font-size: 14px;
  margin-left: 8px;
  transition: all 0.3s ease;
}

.register-link:hover {
  color: #AD1457;
  transform: translateX(4px);
}

.love-tips {
  text-align: center;
  margin-top: 24px;
  padding-top: 16px;
  border-top: 1px solid #FCE4EC;
}

.love-tips span {
  color: #E91E63;
  font-size: 14px;
  letter-spacing: 2px;
  opacity: 0.6;
}

@media (max-width: 480px) {
  .auth-card {
    padding: 32px 24px;
  }

  .auth-card h1 {
    font-size: 24px;
  }

  .logo-icon {
    font-size: 48px;
  }
}
</style>
