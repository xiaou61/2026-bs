<template>
  <div class="login-page">
    <!-- 装饰背景 -->
    <div class="bg-particles">
      <div class="particle" v-for="i in 15" :key="i" :style="getParticleStyle(i)"></div>
    </div>

    <el-card class="login-card" shadow="always">
      <div class="logo-section">
        <div class="logo-icon">❤️</div>
        <h2 class="title">小梦想，大力量</h2>
        <p class="subtitle">让每一份爱心都能点亮希望之光</p>
      </div>

      <el-form :model="form" :rules="rules" ref="formRef" class="login-form">
        <el-form-item prop="username">
          <el-input
            v-model="form.username"
            placeholder="请输入用户名"
            size="large"
            prefix-icon="User"
          />
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="form.password"
            type="password"
            placeholder="请输入密码"
            size="large"
            prefix-icon="Lock"
            show-password
          />
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            @click="handleLogin"
            size="large"
            class="submit-btn"
          >
            <el-icon><Key /></el-icon> 爱心登录
          </el-button>
        </el-form-item>
      </el-form>

      <div class="links">
        <el-divider>其他操作</el-divider>
        <router-link to="/register" class="register-link">
          <el-icon><UserFilled /></el-icon> 还没有账号？立即注册
        </router-link>
      </div>

      <div class="footer-slogan">
        <span>🌟 汇聚爱心 · 温暖世界 🌟</span>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { login } from '@/api/user'
import { useUserStore } from '@/store/user'

const router = useRouter()
const userStore = useUserStore()
const formRef = ref()
const form = ref({
  username: '',
  password: ''
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const getParticleStyle = (i) => ({
  left: `${Math.random() * 100}%`,
  top: `${Math.random() * 100}%`,
  width: `${Math.random() * 12 + 6}px`,
  height: `${Math.random() * 12 + 6}px`,
  animationDelay: `${Math.random() * 5}s`,
  animationDuration: `${Math.random() * 10 + 8}s`,
  opacity: Math.random() * 0.4 + 0.1
})

const handleLogin = async () => {
  await formRef.value.validate()
  try {
    const res = await login(form.value)
    userStore.setToken(res.data.token)
    userStore.setUserInfo(res.data.user)
    ElMessage.success('登录成功')
    router.push('/')
  } catch (error) {
    console.error(error)
  }
}
</script>

<style scoped>
.login-page {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #E91E63 0%, #FF5252 30%, #FF8A80 60%, #FFCDD2 100%);
  position: relative;
  overflow: hidden;
}

.bg-particles {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  pointer-events: none;
}

.particle {
  position: absolute;
  background: rgba(255, 255, 255, 0.3);
  border-radius: 50%;
  animation: floatParticle linear infinite;
}

@keyframes floatParticle {
  0% { transform: translateY(0) rotate(0deg); }
  50% { transform: translateY(-30px) rotate(180deg); }
  100% { transform: translateY(0) rotate(360deg); }
}

.login-card {
  width: 440px;
  border-radius: 28px !important;
  border: none !important;
  background: rgba(255, 255, 255, 0.95) !important;
  backdrop-filter: blur(20px);
  box-shadow: 0 24px 64px rgba(233, 30, 99, 0.2) !important;
  position: relative;
  z-index: 10;
  overflow: hidden;
}

.login-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 6px;
  background: linear-gradient(90deg, #E91E63, #FF5252, #FF8A80, #E91E63);
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
  padding-top: 16px;
}

.logo-icon {
  font-size: 64px;
  margin-bottom: 16px;
  animation: heartbeat 1.5s ease-in-out infinite;
  display: inline-block;
}

@keyframes heartbeat {
  0%, 100% { transform: scale(1); }
  25% { transform: scale(1.1); }
  50% { transform: scale(1); }
  75% { transform: scale(1.05); }
}

.title {
  font-size: 28px;
  font-weight: 800;
  background: linear-gradient(135deg, #C62828, #E91E63, #FF5252);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  margin: 0 0 8px 0;
  letter-spacing: 2px;
}

.subtitle {
  color: #E91E63;
  font-size: 14px;
  margin: 0;
  letter-spacing: 3px;
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
  box-shadow: 0 4px 20px rgba(233, 30, 99, 0.2) !important;
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
  background: linear-gradient(135deg, #C62828 0%, #E91E63 50%, #FF5252 100%) !important;
  border: none !important;
  box-shadow: 0 8px 24px rgba(233, 30, 99, 0.3) !important;
  transition: all 0.3s ease !important;
}

.submit-btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 12px 32px rgba(233, 30, 99, 0.4) !important;
}

.submit-btn:active {
  transform: translateY(0);
}

.links {
  text-align: center;
  margin-top: 24px;
}

.links :deep(.el-divider__text) {
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
  font-weight: 500;
  font-size: 14px;
  transition: all 0.3s ease;
}

.register-link:hover {
  color: #C62828;
  transform: translateX(4px);
}

.footer-slogan {
  text-align: center;
  margin-top: 24px;
  padding-top: 16px;
  border-top: 1px solid #fce4ec;
}

.footer-slogan span {
  color: #FF8A80;
  font-size: 12px;
  letter-spacing: 2px;
}

/* 响应式 */
@media (max-width: 480px) {
  .login-card {
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
