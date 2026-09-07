<template>
  <div class="login-container">
    <!-- 装饰背景 -->
    <div class="bg-decoration">
      <div class="deco deco-1">🚲</div>
      <div class="deco deco-2">🌿</div>
      <div class="deco deco-3">🌍</div>
      <div class="deco deco-4">♻️</div>
    </div>

    <div class="login-card">
      <div class="logo-section">
        <div class="logo-icon">🚲</div>
        <h2 class="title">{{ isLogin ? '共享单车管理' : '用户注册' }}</h2>
        <p class="subtitle">{{ isLogin ? '绿色出行 · 低碳生活' : '加入我们 · 共享出行' }}</p>
      </div>

      <el-form :model="form" :rules="rules" ref="formRef" label-width="0" class="login-form">
        <el-form-item prop="username">
          <el-input v-model="form.username" prefix-icon="User" placeholder="请输入用户名" size="large" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" prefix-icon="Lock" type="password" placeholder="请输入密码" size="large" show-password />
        </el-form-item>
        <el-form-item v-if="!isLogin" prop="phone">
          <el-input v-model="form.phone" prefix-icon="Phone" placeholder="请输入手机号" size="large" />
        </el-form-item>
        <el-form-item v-if="!isLogin" prop="realName">
          <el-input v-model="form.realName" prefix-icon="UserFilled" placeholder="请输入真实姓名" size="large" />
        </el-form-item>
        <el-form-item>
          <el-button type="success" size="large" class="submit-btn" @click="handleSubmit" :loading="loading">
            <template v-if="!loading">
              <el-icon><Key /></el-icon> {{ isLogin ? '绿色登录' : '立即注册' }}
            </template>
            <template v-else>{{ isLogin ? '登录中...' : '注册中...' }}</template>
          </el-button>
        </el-form-item>
      </el-form>

      <div class="switch-mode">
        <el-link type="success" @click="switchMode" class="switch-link">
          {{ isLogin ? '没有账号？去注册' : '已有账号？去登录' }}
        </el-link>
      </div>

      <div class="eco-tips">
        <span>🚲 骑行改变城市 · 共享美好生活 🚲</span>
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
const formRef = ref()
const loading = ref(false)
const isLogin = ref(true)
const form = reactive({ username: '', password: '', phone: '', realName: '' })
const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const handleSubmit = async () => {
  await formRef.value.validate()
  loading.value = true
  try {
    if (isLogin.value) {
      const res = await login({ username: form.username, password: form.password })
      userStore.setUser(res.data.user, res.data.token)
      ElMessage.success('登录成功')
      router.push('/dashboard')
    } else {
      await register(form)
      ElMessage.success('注册成功，请登录')
      isLogin.value = true
    }
  } finally {
    loading.value = false
  }
}

const switchMode = () => {
  isLogin.value = !isLogin.value
}
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #0f766e 0%, #14b8a6 30%, #2dd4bf 60%, #5eead4 100%);
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
.deco-2 { top: 25%; right: 15%; animation-delay: 2s; font-size: 40px; }
.deco-3 { bottom: 25%; left: 20%; animation-delay: 4s; font-size: 56px; }
.deco-4 { top: 50%; right: 10%; animation-delay: 1s; }

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
  box-shadow: 0 24px 64px rgba(15, 118, 110, 0.2);
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
  background: linear-gradient(90deg, #0f766e, #14b8a6, #2dd4bf, #5eead4, #0f766e);
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
  0%, 100% { transform: translateY(0) scale(1); }
  50% { transform: translateY(-10px) scale(1.1); }
}

.title {
  font-size: 28px;
  font-weight: 800;
  background: linear-gradient(135deg, #0f766e, #14b8a6, #2dd4bf);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  margin: 0 0 8px 0;
  letter-spacing: 3px;
}

.subtitle {
  color: #14b8a6;
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
  box-shadow: 0 2px 12px rgba(20, 184, 166, 0.08) !important;
  padding: 4px 12px;
  transition: all 0.3s ease;
}

.login-form :deep(.el-input__wrapper:hover) {
  box-shadow: 0 4px 16px rgba(20, 184, 166, 0.15) !important;
}

.login-form :deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 4px 20px rgba(20, 184, 166, 0.25) !important;
}

.login-form :deep(.el-input__prefix) {
  color: #14b8a6;
}

.submit-btn {
  width: 100%;
  height: 50px;
  font-size: 16px;
  font-weight: 600;
  letter-spacing: 4px;
  border-radius: 14px !important;
  background: linear-gradient(135deg, #0f766e 0%, #14b8a6 50%, #2dd4bf 100%) !important;
  border: none !important;
  box-shadow: 0 8px 24px rgba(20, 184, 166, 0.3) !important;
  transition: all 0.3s ease !important;
}

.submit-btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 12px 32px rgba(20, 184, 166, 0.4) !important;
}

.switch-mode {
  text-align: center;
  margin-top: 16px;
}

.switch-link {
  font-weight: 600;
  font-size: 14px;
}

.eco-tips {
  text-align: center;
  margin-top: 24px;
  padding-top: 16px;
  border-top: 1px solid #F0FDFA;
}

.eco-tips span {
  color: #14b8a6;
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
    font-size: 22px;
  }

  .logo-icon {
    font-size: 56px;
  }
}
</style>
