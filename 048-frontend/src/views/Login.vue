<template>
  <div class="login-container">
    <!-- 装饰背景 -->
    <div class="bg-decoration">
      <div class="deco deco-1">🏛️</div>
      <div class="deco deco-2">🏺</div>
      <div class="deco deco-3">📜</div>
      <div class="deco deco-4">🎭</div>
    </div>

    <div class="login-box">
      <div class="logo-section">
        <div class="logo-icon">🏛️</div>
        <h2 class="title">博物馆文物数字化管理</h2>
        <p class="subtitle">传承文明 · 守护历史</p>
      </div>

      <el-form :model="form" label-width="0" class="login-form">
        <el-form-item>
          <el-input v-model="form.username" placeholder="请输入用户名" prefix-icon="User" size="large" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.password" type="password" placeholder="请输入密码" prefix-icon="Lock" size="large" show-password />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="large" class="submit-btn" @click="handleLogin">
            <el-icon><Key /></el-icon> 进入博物馆
          </el-button>
        </el-form-item>
      </el-form>

      <div class="register-section">
        <el-divider>其他操作</el-divider>
        <el-link type="primary" @click="router.push('/register')" class="register-link">
          <el-icon><UserFilled /></el-icon> 没有账号？去注册
        </el-link>
      </div>

      <div class="culture-tips">
        <span>📜 千年文明 · 数字永存 📜</span>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { login } from '@/api'
import { ElMessage } from 'element-plus'
const router = useRouter()
const userStore = useUserStore()
const form = reactive({ username: '', password: '' })
const handleLogin = async () => {
  const res: any = await login(form)
  if (res.code === 200) { userStore.setUser(res.data); userStore.redirectByRole() }
  else ElMessage.error(res.message)
}
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #3E2723 0%, #4E342E 30%, #5D4037 60%, #6D4C41 100%);
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
.deco-2 { top: 25%; right: 15%; animation-delay: 2s; font-size: 48px; }
.deco-3 { bottom: 25%; left: 20%; animation-delay: 4s; font-size: 40px; }
.deco-4 { top: 50%; right: 10%; animation-delay: 1s; font-size: 52px; }

@keyframes floatDeco {
  0%, 100% { transform: translateY(0) rotate(0deg); opacity: 0.15; }
  25% { transform: translateY(-15px) rotate(5deg); opacity: 0.25; }
  50% { transform: translateY(-8px) rotate(-3deg); opacity: 0.2; }
  75% { transform: translateY(-12px) rotate(3deg); opacity: 0.18; }
}

.login-box {
  width: 460px;
  padding: 48px 40px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 28px;
  box-shadow: 0 24px 64px rgba(62, 39, 35, 0.3);
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
  background: linear-gradient(90deg, #8B4513, #A0522D, #CD853F, #DEB887, #8B4513);
  background-size: 300% 100%;
  animation: gradientMove 4s linear infinite;
}

@keyframes gradientMove {
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
  animation: pulse 3s ease-in-out infinite;
  display: inline-block;
}

@keyframes pulse {
  0%, 100% { transform: scale(1); }
  50% { transform: scale(1.05); }
}

.title {
  font-size: 26px;
  font-weight: 800;
  background: linear-gradient(135deg, #3E2723, #5D4037, #8B4513);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  margin: 0 0 8px 0;
  letter-spacing: 3px;
}

.subtitle {
  color: #8B4513;
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
  background: linear-gradient(135deg, #3E2723 0%, #5D4037 50%, #8B4513 100%) !important;
  border: none !important;
  box-shadow: 0 8px 24px rgba(139, 69, 19, 0.3) !important;
  transition: all 0.3s ease !important;
}

.submit-btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 12px 32px rgba(139, 69, 19, 0.4) !important;
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
  font-weight: 600;
  font-size: 15px;
}

.culture-tips {
  text-align: center;
  margin-top: 24px;
  padding-top: 16px;
  border-top: 1px solid #EFEBE9;
}

.culture-tips span {
  color: #8B4513;
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
    font-size: 20px;
  }

  .logo-icon {
    font-size: 56px;
  }
}
</style>
