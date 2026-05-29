<template>
  <div class="login-container">
    <!-- 装饰背景 -->
    <div class="bg-decoration">
      <div class="heart heart-1">💚</div>
      <div class="heart heart-2">🌿</div>
      <div class="heart heart-3">☮️</div>
      <div class="heart heart-4">🧘</div>
      <div class="heart heart-5">💚</div>
    </div>

    <el-card class="login-card" shadow="always">
      <div class="logo-section">
        <div class="logo-icon">🧠</div>
        <h2 class="title">在线心理评测与咨询</h2>
        <p class="subtitle">关爱心灵 · 拥抱阳光</p>
      </div>

      <el-tabs v-model="activeTab" class="custom-tabs">
        <el-tab-pane label="登录" name="login">
          <el-form :model="loginForm" @submit.prevent="handleLogin" class="login-form">
            <el-form-item>
              <el-input v-model="loginForm.username" placeholder="请输入用户名" prefix-icon="User" size="large" />
            </el-form-item>
            <el-form-item>
              <el-input v-model="loginForm.password" type="password" placeholder="请输入密码" prefix-icon="Lock" show-password size="large" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleLogin" size="large" class="submit-btn">
                <el-icon><Key /></el-icon> 温暖登录
              </el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="注册" name="register">
          <el-form :model="registerForm" @submit.prevent="handleRegister" class="login-form">
            <el-form-item>
              <el-input v-model="registerForm.username" placeholder="请输入用户名" prefix-icon="User" />
            </el-form-item>
            <el-form-item>
              <el-input v-model="registerForm.password" type="password" placeholder="请输入密码" prefix-icon="Lock" show-password />
            </el-form-item>
            <el-form-item>
              <el-input v-model="registerForm.nickname" placeholder="请输入昵称" prefix-icon="UserFilled" />
            </el-form-item>
            <el-form-item>
              <el-input v-model="registerForm.phone" placeholder="请输入手机号" prefix-icon="Phone" />
            </el-form-item>
            <el-form-item>
              <el-input v-model="registerForm.email" placeholder="请输入邮箱" prefix-icon="Message" />
            </el-form-item>
            <el-form-item>
              <el-button type="success" @click="handleRegister" size="large" class="submit-btn register-btn">
                <el-icon><CirclePlus /></el-icon> 立即注册
              </el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>

      <div class="mental-tips">
        <span>🌱 每一次倾诉都是治愈的开始 🌱</span>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { login, register } from '@/api/auth'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()
const activeTab = ref('login')

const loginForm = ref({
  username: '',
  password: ''
})

const registerForm = ref({
  username: '',
  password: '',
  nickname: '',
  phone: '',
  email: ''
})

const handleLogin = async () => {
  try {
    const res = await login(loginForm.value)
    userStore.setToken(res.data.token)
    userStore.setUserInfo(res.data.user)
    ElMessage.success('登录成功')
    router.push('/home')
  } catch (error) {
    ElMessage.error('登录失败')
  }
}

const handleRegister = async () => {
  try {
    const res = await register(registerForm.value)
    userStore.setToken(res.data.token)
    userStore.setUserInfo(res.data.user)
    ElMessage.success('注册成功')
    router.push('/home')
  } catch (error) {
    ElMessage.error('注册失败')
  }
}
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #81C784 0%, #66BB6A 30%, #4CAF50 60%, #388E3C 100%);
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

.heart {
  position: absolute;
  font-size: 48px;
  opacity: 0.2;
  animation: floatHeart 8s ease-in-out infinite;
}

.heart-1 { top: 15%; left: 10%; animation-delay: 0s; }
.heart-2 { top: 25%; right: 15%; animation-delay: 1.5s; font-size: 36px; }
.heart-3 { bottom: 30%; left: 20%; animation-delay: 3s; }
.heart-4 { top: 50%; right: 10%; animation-delay: 4.5s; font-size: 40px; }
.heart-5 { bottom: 15%; left: 15%; animation-delay: 2s; font-size: 56px; }

@keyframes floatHeart {
  0%, 100% { transform: translateY(0) rotate(0deg); opacity: 0.2; }
  25% { transform: translateY(-20px) rotate(10deg); opacity: 0.3; }
  50% { transform: translateY(-10px) rotate(-5deg); opacity: 0.25; }
  75% { transform: translateY(-15px) rotate(5deg); opacity: 0.22; }
}

.login-card {
  width: 460px;
  padding: 48px 40px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 28px;
  box-shadow: 0 24px 64px rgba(76, 175, 80, 0.2);
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
  background: linear-gradient(90deg, #4CAF50, #81C784, #A5D6A7, #4CAF50);
  background-size: 200% 100%;
  animation: shimmer 3s linear infinite;
}

@keyframes shimmer {
  0% { background-position: 200% 0; }
  100% { background-position: -200% 0; }
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
  background: linear-gradient(135deg, #2E7D32, #4CAF50, #66BB6A);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  margin: 0 0 8px 0;
  letter-spacing: 3px;
}

.subtitle {
  color: #4CAF50;
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
  background: linear-gradient(90deg, #4CAF50, #81C784);
  height: 3px;
  border-radius: 2px;
}

.custom-tabs :deep(.el-tabs__item) {
  font-size: 16px;
  font-weight: 600;
  color: #666;
}

.custom-tabs :deep(.el-tabs__item.is-active) {
  color: #4CAF50;
}

.login-form {
  margin-top: 16px;
}

.login-form :deep(.el-input__wrapper) {
  border-radius: 12px !important;
  box-shadow: 0 2px 12px rgba(76, 175, 80, 0.08) !important;
  padding: 4px 12px;
  transition: all 0.3s ease;
}

.login-form :deep(.el-input__wrapper:hover) {
  box-shadow: 0 4px 16px rgba(76, 175, 80, 0.15) !important;
}

.login-form :deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 4px 20px rgba(76, 175, 80, 0.25) !important;
}

.login-form :deep(.el-input__prefix) {
  color: #4CAF50;
}

.submit-btn {
  width: 100%;
  height: 50px;
  font-size: 16px;
  font-weight: 600;
  letter-spacing: 4px;
  border-radius: 14px !important;
  background: linear-gradient(135deg, #2E7D32 0%, #4CAF50 50%, #66BB6A 100%) !important;
  border: none !important;
  box-shadow: 0 8px 24px rgba(76, 175, 80, 0.3) !important;
  transition: all 0.3s ease !important;
}

.submit-btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 12px 32px rgba(76, 175, 80, 0.4) !important;
}

.register-btn {
  background: linear-gradient(135deg, #1565C0 0%, #42A5F5 50%, #64B5F6 100%) !important;
  box-shadow: 0 8px 24px rgba(21, 101, 192, 0.3) !important;
}

.register-btn:hover {
  box-shadow: 0 12px 32px rgba(21, 101, 192, 0.4) !important;
}

.mental-tips {
  text-align: center;
  margin-top: 24px;
  padding-top: 16px;
  border-top: 1px solid #e8f5e9;
}

.mental-tips span {
  color: #4CAF50;
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
    font-size: 20px;
  }

  .logo-icon {
    font-size: 56px;
  }
}
</style>
