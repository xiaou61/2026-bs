<template>
  <div class="login-page">
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
        <h2 class="title">电影院会员管理</h2>
        <p class="subtitle">光影世界 · 精彩无限</p>
      </div>

      <el-form :model="form" label-width="0" class="login-form">
        <el-form-item>
          <el-input v-model="form.username" placeholder="请输入用户名" prefix-icon="User" size="large" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.password" type="password" placeholder="请输入密码" prefix-icon="Lock" show-password size="large" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="large" class="submit-btn" @click="handleLogin" :loading="loading">
            <template v-if="!loading">
              <el-icon><Key /></el-icon> 会员登录
            </template>
            <template v-else>登录中...</template>
          </el-button>
        </el-form-item>
        <el-form-item>
          <el-button text type="primary" class="register-link" @click="registerVisible = true">
            <el-icon><UserFilled /></el-icon> 没有账号？去注册
          </el-button>
        </el-form-item>
      </el-form>

      <div class="demo-accounts">
        <el-divider>测试账号</el-divider>
        <div class="account-tags">
          <el-tag effect="plain" round>admin / 123456</el-tag>
          <el-tag type="warning" effect="plain" round>staff / 123456</el-tag>
          <el-tag type="success" effect="plain" round>member / 123456</el-tag>
        </div>
      </div>

      <div class="cinema-tips">
        <span>🎬 每一部好电影都值得被看见 🎬</span>
      </div>
    </el-card>

    <el-dialog v-model="registerVisible" title="注册会员" width="460px" class="register-dialog">
      <el-form :model="registerForm" label-width="80px" class="register-form">
        <el-form-item label="用户名">
          <el-input v-model="registerForm.username" placeholder="请输入用户名" prefix-icon="User" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="registerForm.password" type="password" placeholder="请输入密码" prefix-icon="Lock" show-password />
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="registerForm.nickname" placeholder="请输入昵称" prefix-icon="UserFilled" />
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="registerForm.phone" placeholder="请输入手机号" prefix-icon="Phone" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="registerVisible = false">取消</el-button>
        <el-button type="primary" @click="handleRegister" class="dialog-submit-btn">
          <el-icon><CirclePlus /></el-icon> 注册
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { useUserStore } from '../store/user'
import { login, register } from '../api'

const router = useRouter()
const userStore = useUserStore()
const loading = ref(false)
const registerVisible = ref(false)

const form = reactive({
  username: 'admin',
  password: '123456'
})

const registerForm = reactive({
  username: '',
  password: '',
  nickname: '',
  phone: ''
})

const handleLogin = async () => {
  loading.value = true
  try {
    const res = await login(form)
    userStore.setLogin(res.data)
    ElMessage.success('登录成功')
    router.push('/dashboard')
  } finally {
    loading.value = false
  }
}

const handleRegister = async () => {
  await register(registerForm)
  ElMessage.success('注册成功，请登录')
  registerVisible.value = false
  form.username = registerForm.username
  form.password = registerForm.password
}
</script>

<style scoped>
.login-page {
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
  font-size: 48px;
  opacity: 0.15;
  animation: floatDeco 10s ease-in-out infinite;
}

.deco-1 { top: 15%; left: 10%; animation-delay: 0s; font-size: 56px; }
.deco-2 { top: 25%; right: 15%; animation-delay: 2s; }
.deco-3 { bottom: 25%; left: 20%; animation-delay: 4s; font-size: 40px; }
.deco-4 { top: 50%; right: 10%; animation-delay: 6s; font-size: 52px; }

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

.login-form {
  margin-top: 24px;
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
}

.submit-btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 12px 32px rgba(233, 69, 96, 0.4) !important;
}

.register-link {
  font-weight: 600;
  font-size: 14px;
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

.cinema-tips {
  text-align: center;
  margin-top: 24px;
  padding-top: 16px;
  border-top: 1px solid #f0f0f0;
}

.cinema-tips span {
  color: #e94560;
  font-size: 12px;
  letter-spacing: 2px;
  opacity: 0.6;
}

/* 注册对话框样式 */
.register-dialog :deep(.el-dialog__header) {
  background: linear-gradient(135deg, #e94560, #ff6b6b);
  padding: 20px 24px;
  margin: 0;
}

.register-dialog :deep(.el-dialog__title) {
  color: white;
  font-weight: 600;
}

.register-dialog :deep(.el-dialog__headerbtn .el-dialog__close) {
  color: white;
}

.register-form :deep(.el-input__wrapper) {
  border-radius: 10px !important;
}

.dialog-submit-btn {
  background: linear-gradient(135deg, #e94560, #ff6b6b) !important;
  border: none !important;
}

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
