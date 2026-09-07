<template>
  <div class="login">
    <!-- 装饰背景 -->
    <div class="bg-decoration">
      <div class="deco deco-1">🏛️</div>
      <div class="deco deco-2">🎫</div>
      <div class="deco deco-3">🎤</div>
      <div class="deco deco-4">✨</div>
    </div>

    <el-card class="login-card" shadow="always">
      <div class="logo-section">
        <div class="logo-icon">🏛️</div>
        <h2 class="title">文旅场馆讲解预约与票务</h2>
        <p class="subtitle">覆盖票务销售、讲解预约、扫码核销、客流监测和反馈运营协同平台</p>
      </div>

      <el-form :model="form" class="login-form" @keyup.enter="handleLogin">
        <el-form-item>
          <el-input v-model="form.username" placeholder="请输入账号" prefix-icon="User" size="large" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.password" placeholder="请输入密码" type="password" prefix-icon="Lock" show-password size="large" />
        </el-form-item>
        <el-button type="primary" size="large" class="submit-btn" @click="handleLogin">
          <el-icon><Key /></el-icon> 进入场馆
        </el-button>
      </el-form>

      <div class="accounts">
        <el-divider>测试账号</el-divider>
        <div class="account-tags">
          <el-tag effect="plain" round>admin / 123456</el-tag>
          <el-tag type="success" effect="plain" round>manager / 123456</el-tag>
          <el-tag type="warning" effect="plain" round>guide / 123456</el-tag>
          <el-tag type="info" effect="plain" round>checker / 123456</el-tag>
          <el-tag type="danger" effect="plain" round>visitor / 123456</el-tag>
        </div>
      </div>

      <div class="venue-tips">
        <span>🏛️ 文化传承 · 智慧文旅 🏛️</span>
      </div>
    </el-card>
  </div>
</template>
<script setup>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { login } from '../api'
import { useUserStore } from '../store/user'
const router = useRouter()
const userStore = useUserStore()
const form = reactive({ username: 'admin', password: '123456' })
const home = { ADMIN: '/dashboard', MANAGER: '/dashboard', GUIDE: '/schedule', CHECKER: '/verification', VISITOR: '/ticket-order' }
const handleLogin = async () => { const res = await login(form); userStore.setLogin(res.data); router.push(home[res.data.user.role] || '/dashboard') }
</script>

<style scoped>
.login {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #8B4513 0%, #A0522D 30%, #CD853F 60%, #DEB887 100%);
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

.deco-1 { top: 15%; left: 10%; animation-delay: 0s; font-size: 64px; }
.deco-2 { top: 25%; right: 15%; animation-delay: 2s; }
.deco-3 { bottom: 25%; left: 20%; animation-delay: 4s; }
.deco-4 { top: 50%; right: 10%; animation-delay: 1s; font-size: 40px; }

@keyframes floatDeco {
  0%, 100% { transform: translateY(0) rotate(0deg); }
  25% { transform: translateY(-15px) rotate(5deg); }
  50% { transform: translateY(-8px) rotate(-3deg); }
  75% { transform: translateY(-12px) rotate(3deg); }
}

.login-card {
  width: 480px;
  padding: 48px 40px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 28px;
  box-shadow: 0 24px 64px rgba(139, 69, 19, 0.3);
  position: relative;
  z-index: 10;
  backdrop-filter: blur(20px);
  overflow: hidden;
}

.login-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 6px;
  background: linear-gradient(90deg, #8B4513, #A0522D, #CD853F, #DEB887, #8B4513);
  background-size: 300% 100%;
  animation: venueGradient 4s linear infinite;
}

@keyframes venueGradient {
  0% { background-position: 0% 0%; }
  100% { background-position: 300% 0%; }
}

.logo-section {
  text-align: center;
  margin-bottom: 32px;
}

.logo-icon {
  font-size: 72px;
  margin-bottom: 16px;
  animation: bounce 2s ease-in-out infinite;
  display: inline-block;
}

@keyframes bounce {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-10px); }
}

.title {
  font-size: 24px;
  font-weight: 800;
  background: linear-gradient(135deg, #5D4037, #795548, #8B4513);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  margin: 0 0 8px 0;
  letter-spacing: 2px;
}

.subtitle {
  color: #8B4513;
  font-size: 13px;
  margin: 0;
  letter-spacing: 1px;
  font-weight: 500;
}

.login-form {
  margin-top: 24px;
}

.login-form :deep(.el-input__wrapper) {
  border-radius: 12px !important;
  box-shadow: 0 2px 12px rgba(139, 69, 19, 0.08) !important;
  padding: 4px 12px;
  transition: all 0.3s ease;
}

.login-form :deep(.el-input__wrapper:hover) {
  box-shadow: 0 4px 16px rgba(139, 69, 19, 0.15) !important;
}

.login-form :deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 4px 20px rgba(139, 69, 19, 0.25) !important;
}

.login-form :deep(.el-input__prefix) {
  color: #8B4513;
}

.submit-btn {
  width: 100%;
  height: 50px;
  font-size: 16px;
  font-weight: 600;
  letter-spacing: 4px;
  border-radius: 14px !important;
  background: linear-gradient(135deg, #5D4037 0%, #795548 50%, #8B4513 100%) !important;
  border: none !important;
  box-shadow: 0 8px 24px rgba(139, 69, 19, 0.3) !important;
  transition: all 0.3s ease !important;
  margin-top: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
}

.submit-btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 12px 32px rgba(139, 69, 19, 0.4) !important;
}

.accounts {
  margin-top: 24px;
}

.accounts :deep(.el-divider__text) {
  background: rgba(255, 255, 255, 0.95);
  color: #999;
  font-size: 12px;
}

.account-tags {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 8px;
}

.account-tags .el-tag {
  cursor: pointer;
  transition: all 0.3s ease;
}

.account-tags .el-tag:hover {
  transform: translateY(-2px);
}

.venue-tips {
  text-align: center;
  margin-top: 24px;
  padding-top: 16px;
  border-top: 1px solid #EFEBE9;
}

.venue-tips span {
  color: #8B4513;
  font-size: 12px;
  letter-spacing: 2px;
  opacity: 0.6;
}

@media (max-width: 480px) {
  .login-card {
    width: 90%;
    margin: 16px;
    padding: 32px 24px;
  }

  .title {
    font-size: 18px;
  }

  .logo-icon {
    font-size: 56px;
  }
}
</style>
