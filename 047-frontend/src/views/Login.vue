<template>
  <div class="login-page">
    <!-- 装饰背景 -->
    <div class="bg-decoration">
      <div class="deco deco-1">🎭</div>
      <div class="deco deco-2">🔍</div>
      <div class="deco deco-3">💀</div>
      <div class="deco deco-4">🗡️</div>
    </div>

    <div class="login-box">
      <div class="logo-section">
        <div class="logo-icon">🎭</div>
        <h2 class="title">剧本杀创作与预约</h2>
        <p class="subtitle">悬疑推理 · 沉浸体验</p>
      </div>

      <el-form :model="form" @submit.prevent="handleLogin" class="login-form">
        <el-form-item>
          <el-input v-model="form.username" placeholder="请输入用户名" prefix-icon="User" size="large" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.password" type="password" placeholder="请输入密码" prefix-icon="Lock" size="large" show-password />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" native-type="submit" size="large" class="submit-btn">
            <el-icon><Key /></el-icon> 进入剧场
          </el-button>
        </el-form-item>
      </el-form>

      <div class="register-section">
        <el-divider>其他操作</el-divider>
        <router-link to="/register" class="register-link">
          <el-icon><UserFilled /></el-icon> 没有账号？立即注册
        </router-link>
      </div>

      <div class="mystery-tips">
        <span>🔍 真相只有一个 · 等你来揭晓 🔍</span>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { login } from '@/api'
import { useUserStore } from '@/stores/user'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()
const form = reactive({ username: '', password: '' })

const handleLogin = async () => {
  const res: any = await login(form)
  if (res.code === 200) {
    userStore.setLogin(res.data)
    ElMessage.success('登录成功')
    router.push(userStore.getRedirectPath())
  } else {
    ElMessage.error(res.msg || '登录失败')
  }
}
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #1a1a2e 0%, #16213e 30%, #0f3460 60%, #533483 100%);
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
  font-size: 56px;
  opacity: 0.15;
  animation: floatDeco 10s ease-in-out infinite;
}

.deco-1 { top: 15%; left: 10%; animation-delay: 0s; }
.deco-2 { top: 25%; right: 15%; animation-delay: 2s; font-size: 40px; }
.deco-3 { bottom: 25%; left: 20%; animation-delay: 4s; font-size: 48px; }
.deco-4 { top: 50%; right: 10%; animation-delay: 1s; font-size: 44px; }

@keyframes floatDeco {
  0%, 100% { transform: translateY(0) rotate(0deg); opacity: 0.15; }
  25% { transform: translateY(-20px) rotate(10deg); opacity: 0.25; }
  50% { transform: translateY(-10px) rotate(-5deg); opacity: 0.2; }
  75% { transform: translateY(-15px) rotate(5deg); opacity: 0.18; }
}

.login-box {
  width: 460px;
  padding: 48px 40px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 28px;
  box-shadow: 0 24px 64px rgba(83, 52, 131, 0.3);
  position: relative;
  z-index: 10;
  backdrop-filter: blur(20px);
  overflow: hidden;
}

.login-box::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 6px;
  background: linear-gradient(90deg, #E91E63, #9C27B0, #673AB7, #3F51B5, #E91E63);
  background-size: 400% 100%;
  animation: mysteryGradient 6s linear infinite;
}

@keyframes mysteryGradient {
  0% { background-position: 0% 0%; }
  100% { background-position: 400% 0%; }
}

.logo-section {
  text-align: center;
  margin-bottom: 32px;
}

.logo-icon {
  font-size: 72px;
  margin-bottom: 16px;
  animation: pulse 2s ease-in-out infinite;
  display: inline-block;
}

@keyframes pulse {
  0%, 100% { transform: scale(1) rotate(0deg); }
  50% { transform: scale(1.1) rotate(5deg); }
}

.title {
  font-size: 28px;
  font-weight: 800;
  background: linear-gradient(135deg, #E91E63, #9C27B0, #673AB7);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  margin: 0 0 8px 0;
  letter-spacing: 3px;
}

.subtitle {
  color: #9C27B0;
  font-size: 14px;
  margin: 0;
  letter-spacing: 6px;
  font-weight: 500;
}

.login-form {
  margin-top: 24px;
}

.login-form :deep(.el-input__wrapper) {
  border-radius: 12px !important;
  box-shadow: 0 2px 12px rgba(156, 39, 176, 0.08) !important;
  padding: 4px 12px;
  transition: all 0.3s ease;
}

.login-form :deep(.el-input__wrapper:hover) {
  box-shadow: 0 4px 16px rgba(156, 39, 176, 0.15) !important;
}

.login-form :deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 4px 20px rgba(156, 39, 176, 0.25) !important;
}

.login-form :deep(.el-input__prefix) {
  color: #9C27B0;
}

.submit-btn {
  width: 100%;
  height: 50px;
  font-size: 16px;
  font-weight: 600;
  letter-spacing: 4px;
  border-radius: 14px !important;
  background: linear-gradient(135deg, #E91E63 0%, #9C27B0 50%, #673AB7 100%) !important;
  border: none !important;
  box-shadow: 0 8px 24px rgba(156, 39, 176, 0.3) !important;
  transition: all 0.3s ease !important;
}

.submit-btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 12px 32px rgba(156, 39, 176, 0.4) !important;
}

.register-section {
  margin-top: 24px;
  text-align: center;
}

.register-section :deep(.el-divider__text) {
  background: rgba(255, 255, 255, 0.95);
  color: #999;
  font-size: 12px;
}

.register-link {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  color: #9C27B0;
  text-decoration: none;
  font-weight: 600;
  font-size: 15px;
  transition: all 0.3s ease;
}

.register-link:hover {
  color: #E91E63;
  transform: translateX(4px);
}

.mystery-tips {
  text-align: center;
  margin-top: 24px;
  padding-top: 16px;
  border-top: 1px solid #F3E5F5;
}

.mystery-tips span {
  color: #9C27B0;
  font-size: 12px;
  letter-spacing: 2px;
  opacity: 0.6;
}

/* 响应式 */
@media (max-width: 480px) {
  .login-box {
    width: 90%;
    margin: 16px;
    padding: 32px 24px;
  }

  .title {
    font-size: 22px;
  }

  .logo-icon {
    font-size: 56px;
  }
}
</style>
