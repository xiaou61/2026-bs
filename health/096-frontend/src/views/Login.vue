<template>
  <div class="login-wrap">
    <!-- 装饰背景 -->
    <div class="bg-decoration">
      <div class="deco deco-1">🏥</div>
      <div class="deco deco-2">👨‍⚕️</div>
      <div class="deco deco-3">💊</div>
      <div class="deco deco-4">🩺</div>
    </div>

    <el-card class="login-card" shadow="always">
      <div class="logo-section">
        <div class="logo-icon">🏥</div>
        <h2 class="title">线上医院挂号系统</h2>
        <p class="subtitle">管理员、医生、患者三角色统一入口</p>
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
                <el-icon><Key /></el-icon> 健康登录
              </template>
              <template v-else>登录中...</template>
            </el-button>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="患者注册" name="register">
          <el-form ref="regRef" :model="regForm" :rules="regRules" class="login-form register-form">
            <el-form-item prop="username">
              <el-input v-model="regForm.username" placeholder="请输入登录账号" prefix-icon="User" size="large" maxlength="50" />
            </el-form-item>
            <el-form-item prop="password">
              <el-input v-model="regForm.password" type="password" show-password placeholder="请输入登录密码" prefix-icon="Lock" size="large" maxlength="100" />
            </el-form-item>
            <el-form-item prop="nickname">
              <el-input v-model="regForm.nickname" placeholder="请输入姓名或昵称" prefix-icon="UserFilled" size="large" maxlength="50" />
            </el-form-item>
            <el-form-item prop="phone">
              <el-input v-model="regForm.phone" placeholder="请输入手机号" prefix-icon="Phone" size="large" maxlength="20" />
            </el-form-item>
            <el-button type="success" size="large" class="submit-btn register-btn" :loading="loading" @click="handleRegister">
              <template v-if="!loading">
                <el-icon><CirclePlus /></el-icon> 注册患者账号
              </template>
              <template v-else>注册中...</template>
            </el-button>
          </el-form>
        </el-tab-pane>
      </el-tabs>

      <div class="demo-accounts">
        <el-divider>测试账号</el-divider>
        <div class="account-tags">
          <el-tag effect="plain" round>admin / 123456</el-tag>
          <el-tag type="success" effect="plain" round>doctor01 / 123456</el-tag>
          <el-tag type="warning" effect="plain" round>patient01 / 123456</el-tag>
        </div>
      </div>

      <div class="hospital-tips">
        <span>🏥 关爱健康 · 呵护生命 🏥</span>
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
const regForm = reactive({ username: '', password: '', nickname: '', phone: '' })

const loginRules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const regRules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  nickname: [{ required: true, message: '请输入姓名或昵称', trigger: 'blur' }],
  phone: [{ required: true, message: '请输入手机号', trigger: 'blur' }]
}

const handleLogin = async () => {
  await loginRef.value.validate()
  loading.value = true
  try {
    const res = await login(loginForm)
    userStore.setUser(res.data.user, res.data.token)
    ElMessage.success('登录成功')
    router.push('/dashboard')
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
    Object.assign(regForm, { username: '', password: '', nickname: '', phone: '' })
    tab.value = 'login'
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
  background: linear-gradient(135deg, #0077B6 0%, #00B4D8 30%, #48CAE4 60%, #90E0EF 100%);
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
.deco-3 { bottom: 25%; left: 20%; animation-delay: 4s; }
.deco-4 { top: 50%; right: 10%; animation-delay: 1s; font-size: 40px; }

@keyframes floatDeco {
  0%, 100% { transform: translateY(0) rotate(0deg); opacity: 0.15; }
  25% { transform: translateY(-15px) rotate(5deg); opacity: 0.25; }
  50% { transform: translateY(-8px) rotate(-3deg); opacity: 0.2; }
  75% { transform: translateY(-12px) rotate(3deg); opacity: 0.18; }
}

.login-card {
  width: 480px;
  padding: 48px 40px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 28px;
  box-shadow: 0 24px 64px rgba(0, 119, 182, 0.3);
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
  background: linear-gradient(90deg, #0077B6, #00B4D8, #48CAE4, #90E0EF, #0077B6);
  background-size: 300% 100%;
  animation: hospitalGradient 4s linear infinite;
}

@keyframes hospitalGradient {
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
  animation: pulse 2s ease-in-out infinite;
  display: inline-block;
}

@keyframes pulse {
  0%, 100% { transform: scale(1); }
  50% { transform: scale(1.1); }
}

.title {
  font-size: 26px;
  font-weight: 800;
  background: linear-gradient(135deg, #0077B6, #00B4D8, #48CAE4);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  margin: 0 0 8px 0;
  letter-spacing: 3px;
}

.subtitle {
  color: #00B4D8;
  font-size: 14px;
  margin: 0;
  letter-spacing: 2px;
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
  background: linear-gradient(90deg, #0077B6, #48CAE4);
  height: 3px;
  border-radius: 2px;
}

.custom-tabs :deep(.el-tabs__item) {
  font-size: 16px;
  font-weight: 600;
  color: #666;
}

.custom-tabs :deep(.el-tabs__item.is-active) {
  color: #0077B6;
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
  box-shadow: 0 2px 12px rgba(0, 119, 182, 0.08) !important;
  padding: 4px 12px;
  transition: all 0.3s ease;
}

.login-form :deep(.el-input__wrapper:hover) {
  box-shadow: 0 4px 16px rgba(0, 119, 182, 0.15) !important;
}

.login-form :deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 4px 20px rgba(0, 119, 182, 0.25) !important;
}

.login-form :deep(.el-input__prefix) {
  color: #0077B6;
}

.submit-btn {
  width: 100%;
  height: 50px;
  font-size: 16px;
  font-weight: 600;
  letter-spacing: 4px;
  border-radius: 14px !important;
  background: linear-gradient(135deg, #0077B6 0%, #00B4D8 50%, #48CAE4 100%) !important;
  border: none !important;
  box-shadow: 0 8px 24px rgba(0, 119, 182, 0.3) !important;
  transition: all 0.3s ease !important;
  margin-top: 8px;
}

.submit-btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 12px 32px rgba(0, 119, 182, 0.4) !important;
}

.register-btn {
  background: linear-gradient(135deg, #2E7D32 0%, #43A047 100%) !important;
  box-shadow: 0 8px 24px rgba(46, 125, 50, 0.3) !important;
}

.register-btn:hover {
  box-shadow: 0 12px 32px rgba(46, 125, 50, 0.4) !important;
}

.demo-accounts {
  margin-top: 24px;
}

.demo-accounts :deep(.el-divider__text) {
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

.hospital-tips {
  text-align: center;
  margin-top: 24px;
  padding-top: 16px;
  border-top: 1px solid #E0F7FA;
}

.hospital-tips span {
  color: #0077B6;
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

  .register-form {
    max-height: 300px;
  }
}
</style>
