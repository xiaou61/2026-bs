<template>
  <div class="login-container">
    <!-- 装饰背景 -->
    <div class="bg-decoration">
      <div class="deco deco-1">🏥</div>
      <div class="deco deco-2">❤️</div>
      <div class="deco deco-3">🩺</div>
      <div class="deco deco-4">💊</div>
    </div>

    <el-card class="login-card" shadow="always">
      <div class="logo-section">
        <div class="logo-icon">🏥</div>
        <h2 class="title">老年人体检管理</h2>
        <p class="subtitle">关爱健康 · 呵护晚年</p>
      </div>

      <el-form ref="formRef" :model="form" :rules="rules" class="login-form">
        <el-form-item prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名" prefix-icon="User" size="large" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" type="password" placeholder="请输入密码" prefix-icon="Lock" show-password size="large" @keyup.enter="handleLogin" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="large" class="submit-btn" :loading="loading" @click="handleLogin">
            <template v-if="!loading">
              <el-icon><Key /></el-icon> 健康登录
            </template>
            <template v-else>登录中...</template>
          </el-button>
        </el-form-item>
      </el-form>

      <div class="health-tips">
        <span>❤️ 关爱老人健康 · 从体检开始 ❤️</span>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { login } from '../api'
import { useUserStore } from '../store/user'

const router = useRouter()
const userStore = useUserStore()
const formRef = ref()
const loading = ref(false)

const form = reactive({
  username: 'admin',
  password: '123456'
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const handleLogin = async () => {
  await formRef.value.validate()
  loading.value = true
  try {
    const res = await login(form)
    userStore.setToken(res.data.token)
    userStore.setUserInfo(res.data.userInfo)
    ElMessage.success('登录成功')
    router.push('/')
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
.deco-3 { bottom: 25%; left: 20%; animation-delay: 4s; }
.deco-4 { top: 50%; right: 10%; animation-delay: 1s; font-size: 40px; }

@keyframes floatDeco {
  0%, 100% { transform: translateY(0) rotate(0deg); opacity: 0.15; }
  25% { transform: translateY(-15px) rotate(5deg); opacity: 0.25; }
  50% { transform: translateY(-8px) rotate(-3deg); opacity: 0.2; }
  75% { transform: translateY(-12px) rotate(3deg); opacity: 0.18; }
}

.login-card {
  width: 460px;
  padding: 48px 40px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 28px;
  box-shadow: 0 24px 64px rgba(233, 30, 99, 0.2);
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
  background: linear-gradient(90deg, #E91E63, #F06292, #F48FB1, #E91E63);
  background-size: 300% 100%;
  animation: healthGradient 4s linear infinite;
}

@keyframes healthGradient {
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
  background: linear-gradient(135deg, #AD1457, #C2185B, #E91E63);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  margin: 0 0 8px 0;
  letter-spacing: 3px;
}

.subtitle {
  color: #E91E63;
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

.submit-btn {
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
}

.submit-btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 12px 32px rgba(233, 30, 99, 0.4) !important;
}

.health-tips {
  text-align: center;
  margin-top: 32px;
  padding-top: 20px;
  border-top: 1px solid #FCE4EC;
}

.health-tips span {
  color: #E91E63;
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
