<template>
  <div class="login-page">
    <!-- 装饰背景 -->
    <div class="bg-decoration">
      <div class="grain grain-1">🌾</div>
      <div class="grain grain-2">🌾</div>
      <div class="grain grain-3">🌾</div>
      <div class="tractor">🚜</div>
      <div class="sun">☀️</div>
    </div>

    <el-card class="card" shadow="always">
      <div class="logo-section">
        <div class="logo-icon">🌾</div>
        <h2 class="title">水稻收割预约系统</h2>
        <p class="subtitle">智慧农业 · 高效收割</p>
      </div>

      <el-form :model="form" :rules="rules" ref="formRef" label-width="0" class="login-form">
        <el-form-item prop="username">
          <el-input
            v-model="form.username"
            autocomplete="username"
            placeholder="请输入用户名"
            size="large"
            prefix-icon="User"
          />
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="form.password"
            type="password"
            autocomplete="current-password"
            placeholder="请输入密码"
            size="large"
            prefix-icon="Lock"
            show-password
          />
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            :loading="loading"
            @click="onSubmit"
            size="large"
            class="submit-btn"
          >
            <template v-if="!loading">
              <el-icon><Key /></el-icon> 登录系统
            </template>
            <template v-else>
              登录中...
            </template>
          </el-button>
        </el-form-item>
      </el-form>

      <div class="footer-info">
        <el-divider>演示账号</el-divider>
        <div class="demo-accounts">
          <el-tag type="success" round effect="plain">admin / admin</el-tag>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useUserStore } from '../stores/user'
import request from '../utils/request'

const router = useRouter()
const route = useRoute()
const formRef = ref()
const loading = ref(false)
const form = ref({ username: '', password: '' })
const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const onSubmit = () => {
  formRef.value.validate(async (valid) => {
    if (!valid) return
    loading.value = true
    try {
      const { data } = await request.post('/auth/login', form.value)
      const store = useUserStore()
      store.setAuth(data.data.token, {
        id: data.data.userId,
        username: data.data.username,
        role: data.data.role
      })
      router.replace(route.query.redirect || '/')
    } catch (e) {
      console.error(e)
    } finally {
      loading.value = false
    }
  })
}
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #2E7D32 0%, #4CAF50 30%, #66BB6A 60%, #81C784 100%);
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

.grain {
  position: absolute;
  font-size: 48px;
  opacity: 0.3;
  animation: sway 3s ease-in-out infinite;
}

.grain-1 { left: 10%; bottom: 20%; animation-delay: 0s; }
.grain-2 { left: 30%; bottom: 25%; animation-delay: 0.5s; font-size: 56px; }
.grain-3 { right: 15%; bottom: 18%; animation-delay: 1s; }

.tractor {
  position: absolute;
  bottom: 10%;
  left: -10%;
  font-size: 64px;
  animation: drive 15s linear infinite;
}

.sun {
  position: absolute;
  top: 10%;
  right: 15%;
  font-size: 72px;
  animation: pulse 3s ease-in-out infinite;
}

@keyframes sway {
  0%, 100% { transform: rotate(-5deg); }
  50% { transform: rotate(5deg); }
}

@keyframes drive {
  0% { transform: translateX(-100px); }
  100% { transform: translateX(calc(100vw + 100px)); }
}

@keyframes pulse {
  0%, 100% { transform: scale(1); opacity: 0.8; }
  50% { transform: scale(1.1); opacity: 1; }
}

.card {
  width: 420px;
  border-radius: 24px !important;
  border: none !important;
  background: rgba(255, 255, 255, 0.95) !important;
  backdrop-filter: blur(20px);
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.15) !important;
  position: relative;
  z-index: 10;
  overflow: visible;
}

.card::before {
  content: '';
  position: absolute;
  top: -4px;
  left: -4px;
  right: -4px;
  bottom: -4px;
  background: linear-gradient(135deg, #4CAF50, #81C784, #A5D6A7, #4CAF50);
  border-radius: 28px;
  z-index: -1;
  animation: borderRotate 3s linear infinite;
}

@keyframes borderRotate {
  0% { filter: hue-rotate(0deg); }
  100% { filter: hue-rotate(360deg); }
}

.logo-section {
  text-align: center;
  margin-bottom: 32px;
}

.logo-icon {
  font-size: 64px;
  margin-bottom: 16px;
  animation: bounce 2s ease-in-out infinite;
  display: inline-block;
}

@keyframes bounce {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-10px); }
}

.title {
  font-size: 28px;
  font-weight: 800;
  background: linear-gradient(135deg, #1B5E20, #2E7D32, #4CAF50);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  margin: 0 0 8px 0;
  letter-spacing: 2px;
}

.subtitle {
  color: #4CAF50;
  font-size: 14px;
  margin: 0;
  letter-spacing: 4px;
  font-weight: 500;
}

.login-form {
  margin-top: 24px;
}

.login-form :deep(.el-input__wrapper) {
  border-radius: 12px !important;
  box-shadow: 0 2px 12px rgba(76, 175, 80, 0.1) !important;
  padding: 4px 12px;
  transition: all 0.3s ease;
}

.login-form :deep(.el-input__wrapper:hover) {
  box-shadow: 0 4px 16px rgba(76, 175, 80, 0.2) !important;
}

.login-form :deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 4px 20px rgba(76, 175, 80, 0.3) !important;
}

.login-form :deep(.el-input__prefix) {
  color: #4CAF50;
}

.submit-btn {
  width: 100%;
  height: 48px;
  font-size: 16px;
  font-weight: 600;
  letter-spacing: 4px;
  border-radius: 12px !important;
  background: linear-gradient(135deg, #2E7D32 0%, #4CAF50 50%, #66BB6A 100%) !important;
  border: none !important;
  box-shadow: 0 8px 24px rgba(76, 175, 80, 0.3) !important;
  transition: all 0.3s ease !important;
}

.submit-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 12px 32px rgba(76, 175, 80, 0.4) !important;
}

.submit-btn:active {
  transform: translateY(0);
}

.footer-info {
  margin-top: 24px;
}

.footer-info :deep(.el-divider__text) {
  background: rgba(255, 255, 255, 0.95);
  color: #666;
  font-size: 12px;
}

.demo-accounts {
  text-align: center;
}

.demo-accounts .el-tag {
  font-size: 13px;
  padding: 8px 16px;
}

/* 响应式 */
@media (max-width: 480px) {
  .card {
    width: 90%;
    margin: 16px;
  }

  .title {
    font-size: 22px;
  }

  .logo-icon {
    font-size: 48px;
  }
}
</style>
