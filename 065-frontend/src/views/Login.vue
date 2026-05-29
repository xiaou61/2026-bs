<template>
  <div class="login-wrap">
    <!-- 装饰背景 -->
    <div class="bg-decoration">
      <div class="deco deco-1">🏠</div>
      <div class="deco deco-2">🏘️</div>
      <div class="deco deco-3">🌳</div>
      <div class="deco deco-4">🅿️</div>
    </div>

    <el-card class="login-card" shadow="always">
      <div class="logo-section">
        <div class="logo-icon">🏘️</div>
        <h2 class="title">名城小区物业管理</h2>
        <p class="subtitle">贴心服务 · 美好家园</p>
      </div>

      <el-tabs v-model="tab" class="custom-tabs">
        <el-tab-pane label="登录" name="login">
          <el-form ref="loginRef" :model="loginForm" :rules="loginRules" class="login-form">
            <el-form-item prop="username">
              <el-input v-model="loginForm.username" placeholder="请输入用户名" prefix-icon="User" size="large" maxlength="50" />
            </el-form-item>
            <el-form-item prop="password">
              <el-input v-model="loginForm.password" type="password" show-password placeholder="请输入密码" prefix-icon="Lock" size="large" maxlength="100" />
            </el-form-item>
            <el-button type="primary" size="large" class="submit-btn" :loading="loading" @click="handleLogin">
              <template v-if="!loading">
                <el-icon><Key /></el-icon> 进入小区
              </template>
              <template v-else>登录中...</template>
            </el-button>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="注册业主账号" name="register">
          <el-form ref="regRef" :model="regForm" :rules="regRules" class="login-form">
            <el-form-item prop="username">
              <el-input v-model="regForm.username" placeholder="请输入用户名" prefix-icon="User" size="large" maxlength="50" />
            </el-form-item>
            <el-form-item prop="password">
              <el-input v-model="regForm.password" type="password" show-password placeholder="请输入密码" prefix-icon="Lock" size="large" maxlength="100" />
            </el-form-item>
            <el-form-item prop="nickname">
              <el-input v-model="regForm.nickname" placeholder="请输入昵称" prefix-icon="UserFilled" size="large" maxlength="50" />
            </el-form-item>
            <el-button type="success" size="large" class="submit-btn register-btn" :loading="loading" @click="handleRegister">
              <template v-if="!loading">
                <el-icon><CirclePlus /></el-icon> 业主注册
              </template>
              <template v-else>注册中...</template>
            </el-button>
          </el-form>
        </el-tab-pane>
      </el-tabs>

      <div class="property-tips">
        <span>🏡 家的美好 · 从这里开始 🏡</span>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { login, register } from '../api'
import { useUserStore } from '../store/user'

const router = useRouter()
const userStore = useUserStore()
const loading = ref(false)
const tab = ref('login')
const loginRef = ref()
const regRef = ref()

const loginForm = reactive({ username: 'admin', password: '123456' })
const regForm = reactive({ username: '', password: '', nickname: '' })

const loginRules = {
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
    if (res.data.user?.role === 'OWNER') {
      router.push('/fee')
    } else {
      router.push('/dashboard')
    }
  } finally {
    loading.value = false
  }
}

const handleRegister = async () => {
  await regRef.value.validate()
  loading.value = true
  try {
    await register(regForm)
    ElMessage.success('注册成功，请登录')
    tab.value = 'login'
    Object.assign(regForm, { username: '', password: '', nickname: '' })
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-wrap {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #1e3a5f 0%, #2563eb 30%, #3b82f6 60%, #60a5fa 100%);
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

.deco-1 { top: 15%; left: 10%; animation-delay: 0s; }
.deco-2 { top: 25%; right: 15%; animation-delay: 2s; font-size: 56px; }
.deco-3 { bottom: 25%; left: 20%; animation-delay: 4s; }
.deco-4 { top: 50%; right: 10%; animation-delay: 1s; font-size: 40px; }

@keyframes floatDeco {
  0%, 100% { transform: translateY(0) rotate(0deg); }
  25% { transform: translateY(-15px) rotate(5deg); }
  50% { transform: translateY(-8px) rotate(-3deg); }
  75% { transform: translateY(-12px) rotate(3deg); }
}

.login-card {
  width: 460px;
  padding: 48px 40px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 28px;
  box-shadow: 0 24px 64px rgba(30, 58, 95, 0.3);
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
  background: linear-gradient(90deg, #1e3a5f, #2563eb, #3b82f6, #60a5fa, #1e3a5f);
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
  animation: bounce 2s ease-in-out infinite;
  display: inline-block;
}

@keyframes bounce {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-10px); }
}

.title {
  font-size: 26px;
  font-weight: 800;
  background: linear-gradient(135deg, #1e3a5f, #2563eb, #3b82f6);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  margin: 0 0 8px 0;
  letter-spacing: 3px;
}

.subtitle {
  color: #2563eb;
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
  background: linear-gradient(90deg, #1e3a5f, #3b82f6);
  height: 3px;
  border-radius: 2px;
}

.custom-tabs :deep(.el-tabs__item) {
  font-size: 15px;
  font-weight: 600;
  color: #666;
}

.custom-tabs :deep(.el-tabs__item.is-active) {
  color: #2563eb;
}

.login-form {
  margin-top: 16px;
}

.login-form :deep(.el-input__wrapper) {
  border-radius: 12px !important;
  box-shadow: 0 2px 12px rgba(37, 99, 235, 0.08) !important;
  padding: 4px 12px;
  transition: all 0.3s ease;
}

.login-form :deep(.el-input__wrapper:hover) {
  box-shadow: 0 4px 16px rgba(37, 99, 235, 0.15) !important;
}

.login-form :deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 4px 20px rgba(37, 99, 235, 0.25) !important;
}

.login-form :deep(.el-input__prefix) {
  color: #2563eb;
}

.submit-btn {
  width: 100%;
  height: 50px;
  font-size: 16px;
  font-weight: 600;
  letter-spacing: 4px;
  border-radius: 14px !important;
  background: linear-gradient(135deg, #1e3a5f 0%, #2563eb 50%, #3b82f6 100%) !important;
  border: none !important;
  box-shadow: 0 8px 24px rgba(37, 99, 235, 0.3) !important;
  transition: all 0.3s ease !important;
  margin-top: 8px;
}

.submit-btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 12px 32px rgba(37, 99, 235, 0.4) !important;
}

.register-btn {
  background: linear-gradient(135deg, #059669 0%, #10b981 100%) !important;
  box-shadow: 0 8px 24px rgba(5, 150, 105, 0.3) !important;
}

.register-btn:hover {
  box-shadow: 0 12px 32px rgba(5, 150, 105, 0.4) !important;
}

.property-tips {
  text-align: center;
  margin-top: 24px;
  padding-top: 16px;
  border-top: 1px solid #EFF6FF;
}

.property-tips span {
  color: #2563eb;
  font-size: 14px;
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
    font-size: 20px;
  }

  .logo-icon {
    font-size: 56px;
  }
}
</style>
