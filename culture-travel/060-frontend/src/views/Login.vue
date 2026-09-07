<template>
  <div class="login-container">
    <!-- 装饰背景 -->
    <div class="bg-decoration">
      <div class="deco deco-1">🎬</div>
      <div class="deco deco-2">🍿</div>
      <div class="deco deco-3">🎥</div>
      <div class="deco deco-4">⭐</div>
    </div>

    <el-card class="login-card" shadow="always">
      <div class="logo-section">
        <div class="logo-icon">🎬</div>
        <h2 class="title">电影订票及评论</h2>
        <p class="subtitle">光影世界 · 精彩无限</p>
      </div>

      <el-tabs v-model="activeTab" class="custom-tabs">
        <el-tab-pane label="登录" name="login">
          <el-form :model="loginForm" :rules="rules" ref="loginRef" class="login-form">
            <el-form-item prop="username">
              <el-input v-model="loginForm.username" placeholder="请输入用户名" prefix-icon="User" size="large" />
            </el-form-item>
            <el-form-item prop="password">
              <el-input v-model="loginForm.password" type="password" placeholder="请输入密码" prefix-icon="Lock" show-password size="large" />
            </el-form-item>
            <el-button type="primary" size="large" class="submit-btn" @click="handleLogin" :loading="loading">
              <template v-if="!loading">
                <el-icon><Key /></el-icon> 购票登录
              </template>
              <template v-else>登录中...</template>
            </el-button>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="注册" name="register">
          <el-form :model="registerForm" :rules="regRules" ref="registerRef" class="login-form">
            <el-form-item prop="username">
              <el-input v-model="registerForm.username" placeholder="请输入用户名" prefix-icon="User" size="large" />
            </el-form-item>
            <el-form-item prop="password">
              <el-input v-model="registerForm.password" type="password" placeholder="请输入密码" prefix-icon="Lock" show-password size="large" />
            </el-form-item>
            <el-form-item prop="nickname">
              <el-input v-model="registerForm.nickname" placeholder="请输入昵称" prefix-icon="UserFilled" size="large" />
            </el-form-item>
            <el-button type="success" size="large" class="submit-btn register-btn" @click="handleRegister" :loading="loading">
              <template v-if="!loading">
                <el-icon><CirclePlus /></el-icon> 立即注册
              </template>
              <template v-else>注册中...</template>
            </el-button>
          </el-form>
        </el-tab-pane>
      </el-tabs>

      <div class="movie-tips">
        <span>🎬 每一部好电影都值得被看见 🎬</span>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { login, register } from '../api'
import { useUserStore } from '../store/user'

const router = useRouter()
const userStore = useUserStore()
const loading = ref(false)
const activeTab = ref('login')
const loginRef = ref()
const registerRef = ref()

const loginForm = reactive({ username: '', password: '' })
const registerForm = reactive({ username: '', password: '', nickname: '' })

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}
const regRules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  nickname: [{ required: true, message: '请输入昵称', trigger: 'blur' }]
}

const handleLogin = async () => {
  await loginRef.value.validate()
  loading.value = true
  try {
    const res = await login(loginForm)
    userStore.setUser(res.data.user, res.data.token)
    ElMessage.success('登录成功')
    router.push(userStore.isAdmin() ? '/dashboard' : '/movie')
  } finally {
    loading.value = false
  }
}

const handleRegister = async () => {
  await registerRef.value.validate()
  loading.value = true
  try {
    await register(registerForm)
    ElMessage.success('注册成功，请登录')
    activeTab.value = 'login'
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #1a1a2e 0%, #16213e 30%, #0f3460 60%, #e94560 100%);
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
.deco-3 { bottom: 25%; left: 20%; animation-delay: 4s; font-size: 44px; }
.deco-4 { top: 50%; right: 10%; animation-delay: 1s; font-size: 40px; }

@keyframes floatDeco {
  0%, 100% { transform: translateY(0) rotate(0deg); opacity: 0.15; }
  25% { transform: translateY(-20px) rotate(10deg); opacity: 0.25; }
  50% { transform: translateY(-10px) rotate(-5deg); opacity: 0.2; }
  75% { transform: translateY(-15px) rotate(5deg); opacity: 0.18; }
}

.login-card {
  width: 460px;
  padding: 48px 40px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 28px;
  box-shadow: 0 24px 64px rgba(233, 69, 96, 0.3);
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
  background: linear-gradient(90deg, #e94560, #ff6b6b, #ffd93d, #6bcb77, #4d96ff, #e94560);
  background-size: 400% 100%;
  animation: cinemaGradient 6s linear infinite;
}

@keyframes cinemaGradient {
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
  background: linear-gradient(135deg, #e94560, #ff6b6b, #ffd93d);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  margin: 0 0 8px 0;
  letter-spacing: 3px;
}

.subtitle {
  color: #e94560;
  font-size: 14px;
  margin: 0;
  letter-spacing: 6px;
  font-weight: 500;
}

.custom-tabs {
  margin-top: 24px;
}

.custom-tabs :deep(.el-tabs__header) {
  margin-bottom: 24px;
}

.custom-tabs :deep(.el-tabs__nav-wrap::after) {
  display: none;
}

.custom-tabs :deep(.el-tabs__active-bar) {
  background: linear-gradient(90deg, #e94560, #ff6b6b);
  height: 3px;
  border-radius: 2px;
}

.custom-tabs :deep(.el-tabs__item) {
  font-size: 16px;
  font-weight: 600;
  color: #666;
}

.custom-tabs :deep(.el-tabs__item.is-active) {
  color: #e94560;
}

.login-form {
  margin-top: 16px;
}

.login-form :deep(.el-input__wrapper) {
  border-radius: 12px !important;
  box-shadow: 0 2px 12px rgba(233, 69, 96, 0.08) !important;
  padding: 4px 12px;
  transition: all 0.3s ease;
}

.login-form :deep(.el-input__wrapper:hover) {
  box-shadow: 0 4px 16px rgba(233, 69, 96, 0.15) !important;
}

.login-form :deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 4px 20px rgba(233, 69, 96, 0.25) !important;
}

.login-form :deep(.el-input__prefix) {
  color: #e94560;
}

.submit-btn {
  width: 100%;
  height: 50px;
  font-size: 16px;
  font-weight: 600;
  letter-spacing: 4px;
  border-radius: 14px !important;
  background: linear-gradient(135deg, #e94560 0%, #ff6b6b 100%) !important;
  border: none !important;
  box-shadow: 0 8px 24px rgba(233, 69, 96, 0.3) !important;
  transition: all 0.3s ease !important;
  margin-top: 8px;
}

.submit-btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 12px 32px rgba(233, 69, 96, 0.4) !important;
}

.register-btn {
  background: linear-gradient(135deg, #6bcb77 0%, #4d96ff 100%) !important;
  box-shadow: 0 8px 24px rgba(107, 203, 119, 0.3) !important;
}

.register-btn:hover {
  box-shadow: 0 12px 32px rgba(107, 203, 119, 0.4) !important;
}

.movie-tips {
  text-align: center;
  margin-top: 24px;
  padding-top: 16px;
  border-top: 1px solid #f0f0f0;
}

.movie-tips span {
  color: #e94560;
  font-size: 12px;
  letter-spacing: 2px;
  opacity: 0.6;
}

/* 响应式 */
@media (max-width: 480px) {
  .login-card {
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
