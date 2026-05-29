<template>
  <div class="login-container">
    <!-- 装饰背景 -->
    <div class="bg-decoration">
      <div class="deco deco-1">🏠</div>
      <div class="deco deco-2">❤️</div>
      <div class="deco deco-3">🌿</div>
      <div class="deco deco-4">☀️</div>
    </div>

    <el-card class="login-card" shadow="always">
      <div class="logo-section">
        <div class="logo-icon">🏠</div>
        <h2 class="title">养老院管理系统</h2>
        <p class="subtitle">关爱老人 · 温暖相伴</p>
      </div>

      <el-form :model="form" :rules="rules" ref="formRef" class="login-form">
        <el-form-item prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名" prefix-icon="User" size="large" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" type="password" placeholder="请输入密码" prefix-icon="Lock" show-password size="large" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleLogin" :loading="loading" size="large" class="submit-btn">
            <template v-if="!loading">
              <el-icon><Key /></el-icon> 温暖登录
            </template>
            <template v-else>登录中...</template>
          </el-button>
        </el-form-item>
      </el-form>

      <div class="register-section">
        <el-divider>其他入口</el-divider>
        <router-link to="/register" class="register-link">
          <el-icon><UserFilled /></el-icon> 家属注册
        </router-link>
      </div>

      <div class="care-tips">
        <span>🌅 夕阳无限好 · 人间重晚晴 🌅</span>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useUserStore } from '@/stores/user'
import type { FormInstance } from 'element-plus'

const userStore = useUserStore()
const formRef = ref<FormInstance>()
const loading = ref(false)
const form = reactive({ username: '', password: '' })
const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const handleLogin = async () => {
  await formRef.value?.validate()
  loading.value = true
  try {
    await userStore.login(form.username, form.password)
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
  background: linear-gradient(135deg, #EC407A 0%, #E91E63 30%, #D81B60 60%, #C2185B 100%);
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
  opacity: 0.2;
  animation: floatDeco 8s ease-in-out infinite;
}

.deco-1 { top: 15%; left: 10%; animation-delay: 0s; }
.deco-2 { top: 20%; right: 15%; animation-delay: 2s; font-size: 36px; }
.deco-3 { bottom: 25%; left: 20%; animation-delay: 4s; font-size: 40px; }
.deco-4 { top: 50%; right: 10%; animation-delay: 1s; font-size: 56px; }

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
  background: linear-gradient(90deg, #EC407A, #E91E63, #D81B60, #C2185B, #EC407A);
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
  animation: pulse 2s ease-in-out infinite;
  display: inline-block;
}

@keyframes pulse {
  0%, 100% { transform: scale(1); }
  50% { transform: scale(1.1); }
}

.title {
  font-size: 28px;
  font-weight: 800;
  background: linear-gradient(135deg, #880E4F, #AD1457, #C2185B);
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
  background: linear-gradient(135deg, #880E4F 0%, #C2185B 50%, #E91E63 100%) !important;
  border: none !important;
  box-shadow: 0 8px 24px rgba(233, 30, 99, 0.3) !important;
  transition: all 0.3s ease !important;
}

.submit-btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 12px 32px rgba(233, 30, 99, 0.4) !important;
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
  color: #E91E63;
  text-decoration: none;
  font-weight: 600;
  font-size: 15px;
  transition: all 0.3s ease;
}

.register-link:hover {
  color: #880E4F;
  transform: translateX(4px);
}

.care-tips {
  text-align: center;
  margin-top: 24px;
  padding-top: 16px;
  border-top: 1px solid #FCE4EC;
}

.care-tips span {
  color: #E91E63;
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
