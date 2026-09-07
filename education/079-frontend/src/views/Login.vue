<template>
  <div class="login-container">
    <!-- 装饰背景 -->
    <div class="bg-decoration">
      <div class="deco deco-1">🎓</div>
      <div class="deco deco-2">🏫</div>
      <div class="deco deco-3">👨‍🎓</div>
      <div class="deco deco-4">🌟</div>
    </div>

    <div class="login-box">
      <div class="logo-section">
        <div class="logo-icon">🎓</div>
        <h2 class="title">计算机学院校友网</h2>
        <p class="subtitle">桃李满天下 · 校友情谊长</p>
      </div>

      <el-tabs v-model="activeTab" class="custom-tabs">
        <el-tab-pane label="登录" name="login">
          <el-form :model="loginForm" :rules="loginRules" ref="loginFormRef" class="login-form">
            <el-form-item prop="username">
              <el-input v-model="loginForm.username" prefix-icon="User" placeholder="请输入用户名" size="large" />
            </el-form-item>
            <el-form-item prop="password">
              <el-input v-model="loginForm.password" prefix-icon="Lock" type="password" placeholder="请输入密码" show-password size="large" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" size="large" class="submit-btn" @click="handleLogin" :loading="loading">
                <template v-if="!loading">
                  <el-icon><Key /></el-icon> 校友登录
                </template>
                <template v-else>登录中...</template>
              </el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="注册" name="register">
          <el-form :model="registerForm" :rules="registerRules" ref="registerFormRef" class="login-form register-form">
            <el-form-item prop="username">
              <el-input v-model="registerForm.username" prefix-icon="User" placeholder="请输入用户名" size="large" />
            </el-form-item>
            <el-form-item prop="password">
              <el-input v-model="registerForm.password" prefix-icon="Lock" type="password" placeholder="请输入密码" show-password size="large" />
            </el-form-item>
            <el-form-item prop="name">
              <el-input v-model="registerForm.name" prefix-icon="UserFilled" placeholder="请输入姓名" size="large" />
            </el-form-item>
            <el-form-item prop="phone">
              <el-input v-model="registerForm.phone" prefix-icon="Phone" placeholder="请输入手机号" size="large" />
            </el-form-item>
            <el-form-item prop="email">
              <el-input v-model="registerForm.email" prefix-icon="Message" placeholder="请输入邮箱" size="large" />
            </el-form-item>
            <el-form-item>
              <el-button type="success" size="large" class="submit-btn register-btn" @click="handleRegister" :loading="loading">
                <template v-if="!loading">
                  <el-icon><CirclePlus /></el-icon> 校友注册
                </template>
                <template v-else>注册中...</template>
              </el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>

      <div class="alumni-tips">
        <span>🌟 同窗情谊 · 永远珍惜 🌟</span>
      </div>
    </div>
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
const activeTab = ref('login')
const loading = ref(false)
const loginFormRef = ref()
const registerFormRef = ref()

const loginForm = reactive({ username: '', password: '' })
const registerForm = reactive({ username: '', password: '', name: '', phone: '', email: '' })

const loginRules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const registerRules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }]
}

const handleLogin = async () => {
  await loginFormRef.value.validate()
  loading.value = true
  try {
    const res = await login(loginForm)
    userStore.setToken(res.data.token)
    userStore.setUser(res.data.user)
    ElMessage.success('登录成功')
    router.push('/')
  } finally {
    loading.value = false
  }
}

const handleRegister = async () => {
  await registerFormRef.value.validate()
  loading.value = true
  try {
    await register(registerForm)
    ElMessage.success('注册成功，请等待审核')
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
  background: linear-gradient(135deg, #4A148C 0%, #6A1B9A 30%, #8E24AA 60%, #AB47BC 100%);
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

.login-box {
  width: 480px;
  padding: 48px 40px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 28px;
  box-shadow: 0 24px 64px rgba(74, 20, 140, 0.3);
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
  background: linear-gradient(90deg, #4A148C, #6A1B9A, #8E24AA, #AB47BC, #4A148C);
  background-size: 300% 100%;
  animation: alumniGradient 4s linear infinite;
}

@keyframes alumniGradient {
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
  0%, 100% { transform: translateY(0) scale(1); }
  50% { transform: translateY(-10px) scale(1.1); }
}

.title {
  font-size: 26px;
  font-weight: 800;
  background: linear-gradient(135deg, #4A148C, #6A1B9A, #8E24AA);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  margin: 0 0 8px 0;
  letter-spacing: 2px;
}

.subtitle {
  color: #8E24AA;
  font-size: 14px;
  margin: 0;
  letter-spacing: 4px;
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
  background: linear-gradient(90deg, #4A148C, #AB47BC);
  height: 3px;
  border-radius: 2px;
}

.custom-tabs :deep(.el-tabs__item) {
  font-size: 16px;
  font-weight: 600;
  color: #666;
}

.custom-tabs :deep(.el-tabs__item.is-active) {
  color: #8E24AA;
}

.login-form {
  margin-top: 16px;
}

.register-form {
  max-height: 380px;
  overflow-y: auto;
  padding-right: 8px;
}

.register-form::-webkit-scrollbar {
  width: 4px;
}

.register-form::-webkit-scrollbar-thumb {
  background: #ddd;
  border-radius: 2px;
}

.login-form :deep(.el-input__wrapper) {
  border-radius: 12px !important;
  box-shadow: 0 2px 12px rgba(142, 36, 170, 0.08) !important;
  padding: 4px 12px;
  transition: all 0.3s ease;
}

.login-form :deep(.el-input__wrapper:hover) {
  box-shadow: 0 4px 16px rgba(142, 36, 170, 0.15) !important;
}

.login-form :deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 4px 20px rgba(142, 36, 170, 0.25) !important;
}

.login-form :deep(.el-input__prefix) {
  color: #8E24AA;
}

.submit-btn {
  width: 100%;
  height: 50px;
  font-size: 16px;
  font-weight: 600;
  letter-spacing: 4px;
  border-radius: 14px !important;
  background: linear-gradient(135deg, #4A148C 0%, #6A1B9A 50%, #8E24AA 100%) !important;
  border: none !important;
  box-shadow: 0 8px 24px rgba(142, 36, 170, 0.3) !important;
  transition: all 0.3s ease !important;
  margin-top: 8px;
}

.submit-btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 12px 32px rgba(142, 36, 170, 0.4) !important;
}

.register-btn {
  background: linear-gradient(135deg, #2E7D32 0%, #43A047 100%) !important;
  box-shadow: 0 8px 24px rgba(46, 125, 50, 0.3) !important;
}

.register-btn:hover {
  box-shadow: 0 12px 32px rgba(46, 125, 50, 0.4) !important;
}

.alumni-tips {
  text-align: center;
  margin-top: 24px;
  padding-top: 16px;
  border-top: 1px solid #F3E5F5;
}

.alumni-tips span {
  color: #8E24AA;
  font-size: 14px;
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

  .register-form {
    max-height: 300px;
  }
}
</style>
