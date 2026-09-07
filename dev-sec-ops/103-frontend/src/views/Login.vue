<template>
  <div class="login-page">
    <!-- 装饰背景 -->
    <div class="bg-decoration">
      <div class="deco deco-1">💬</div>
      <div class="deco deco-2">📋</div>
      <div class="deco deco-3">🎯</div>
      <div class="deco deco-4">⭐</div>
    </div>

    <div class="login-panel">
      <div class="brand">
        <div class="logo-icon">💬</div>
        <div class="brand-tag">SMART SERVICE</div>
        <h1>智能客服工单质检与知识推荐系统</h1>
        <p>工单处理、质检评分、知识推荐和坐席绩效一体化运营</p>
        <div class="features">
          <div class="feature-item">
            <span class="feature-icon">📋</span>
            <span>工单管理</span>
          </div>
          <div class="feature-item">
            <span class="feature-icon">🔍</span>
            <span>质检评分</span>
          </div>
          <div class="feature-item">
            <span class="feature-icon">📚</span>
            <span>知识推荐</span>
          </div>
        </div>
      </div>
      <el-form :model="form" class="login-form" @keyup.enter="handleLogin">
        <div class="form-header">
          <div class="form-icon">🔐</div>
          <h3 class="form-title">系统登录</h3>
          <p class="form-subtitle">输入账号进入客服系统</p>
        </div>
        <el-form-item>
          <el-input v-model="form.username" size="large" placeholder="请输入用户名" prefix-icon="User" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.password" size="large" type="password" show-password placeholder="请输入密码" prefix-icon="Lock" />
        </el-form-item>
        <el-button type="primary" size="large" :loading="loading" @click="handleLogin" class="submit-btn">
          <template v-if="!loading">
            <el-icon><Key /></el-icon> 登录系统
          </template>
          <template v-else>登录中...</template>
        </el-button>
        <div class="accounts">
          <el-divider>测试账号</el-divider>
          <div class="account-tags">
            <el-tag effect="plain" round>admin / 123456</el-tag>
            <el-tag type="success" effect="plain" round>supervisor / 123456</el-tag>
            <el-tag type="warning" effect="plain" round>agent / 123456</el-tag>
            <el-tag type="danger" effect="plain" round>qa / 123456</el-tag>
          </div>
        </div>
        <div class="service-tips">
          <span>💬 用心服务 · 客户至上 💬</span>
        </div>
      </el-form>
    </div>
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
const loading = ref(false)
const form = reactive({ username: 'admin', password: '123456' })

const handleLogin = async () => {
  loading.value = true
  try {
    const res = await login(form)
    userStore.setLogin(res.data.token, res.data.user)
    ElMessage.success('登录成功')
    router.push('/dashboard')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #FF6B35 0%, #F7931E 30%, #FFB347 60%, #FFD700 100%);
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
.deco-2 { top: 25%; right: 15%; animation-delay: 2s; }
.deco-3 { bottom: 25%; left: 20%; animation-delay: 4s; }
.deco-4 { top: 50%; right: 10%; animation-delay: 1s; font-size: 40px; }

@keyframes floatDeco {
  0%, 100% { transform: translateY(0) rotate(0deg); opacity: 0.15; }
  25% { transform: translateY(-15px) rotate(5deg); opacity: 0.25; }
  50% { transform: translateY(-8px) rotate(-3deg); opacity: 0.2; }
  75% { transform: translateY(-12px) rotate(3deg); opacity: 0.18; }
}

.login-panel {
  display: flex;
  align-items: stretch;
  max-width: 1000px;
  width: 100%;
  margin: 24px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 28px;
  box-shadow: 0 24px 64px rgba(255, 107, 53, 0.3);
  overflow: hidden;
  position: relative;
  z-index: 10;
}

.login-panel::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 6px;
  background: linear-gradient(90deg, #FF6B35, #F7931E, #FFB347, #FFD700, #FF6B35);
  background-size: 300% 100%;
  animation: serviceGradient 4s linear infinite;
}

@keyframes serviceGradient {
  0% { background-position: 0% 0%; }
  100% { background-position: 300% 0%; }
}

.brand {
  flex: 1;
  padding: 56px 48px;
  background: linear-gradient(135deg, #FF6B35 0%, #F7931E 50%, #FFB347 100%);
  color: white;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.logo-icon {
  font-size: 64px;
  margin-bottom: 16px;
  animation: pulse 2s ease-in-out infinite;
  display: inline-block;
  width: fit-content;
}

@keyframes pulse {
  0%, 100% { transform: scale(1); }
  50% { transform: scale(1.1); }
}

.brand-tag {
  display: inline-flex;
  padding: 6px 16px;
  border-radius: 999px;
  background: rgba(255, 255, 255, 0.2);
  color: #fff;
  font-size: 12px;
  font-weight: 700;
  letter-spacing: 3px;
  margin-bottom: 20px;
  width: fit-content;
}

.brand h1 {
  margin: 0 0 16px;
  font-size: 30px;
  line-height: 1.2;
  font-weight: 800;
}

.brand p {
  margin: 0;
  color: rgba(255, 255, 255, 0.8);
  font-size: 15px;
  line-height: 1.8;
}

.features {
  margin-top: 32px;
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.feature-item {
  display: flex;
  align-items: center;
  gap: 10px;
  color: rgba(255, 255, 255, 0.9);
  font-size: 14px;
}

.feature-icon {
  font-size: 18px;
}

.login-form {
  flex: 1;
  padding: 56px 48px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.form-header {
  text-align: center;
  margin-bottom: 32px;
}

.form-icon {
  font-size: 48px;
  margin-bottom: 12px;
}

.form-title {
  font-size: 24px;
  font-weight: 800;
  background: linear-gradient(135deg, #FF6B35, #F7931E);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  margin: 0 0 8px 0;
}

.form-subtitle {
  color: #9ca3af;
  font-size: 14px;
  margin: 0;
}

.login-form :deep(.el-input__wrapper) {
  border-radius: 12px !important;
  box-shadow: 0 2px 12px rgba(255, 107, 53, 0.08) !important;
  padding: 4px 12px;
  transition: all 0.3s ease;
}

.login-form :deep(.el-input__wrapper:hover) {
  box-shadow: 0 4px 16px rgba(255, 107, 53, 0.15) !important;
}

.login-form :deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 4px 20px rgba(255, 107, 53, 0.25) !important;
}

.login-form :deep(.el-input__prefix) {
  color: #FF6B35;
}

.submit-btn {
  width: 100%;
  height: 50px;
  font-size: 16px;
  font-weight: 600;
  letter-spacing: 4px;
  border-radius: 14px !important;
  background: linear-gradient(135deg, #FF6B35 0%, #F7931E 50%, #FFB347 100%) !important;
  border: none !important;
  box-shadow: 0 8px 24px rgba(255, 107, 53, 0.3) !important;
  transition: all 0.3s ease !important;
  margin-top: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
}

.submit-btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 12px 32px rgba(255, 107, 53, 0.4) !important;
}

.accounts {
  margin-top: 24px;
}

.accounts :deep(.el-divider__text) {
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

.service-tips {
  text-align: center;
  margin-top: 24px;
  padding-top: 16px;
  border-top: 1px solid #FFF3E0;
}

.service-tips span {
  color: #FF6B35;
  font-size: 12px;
  letter-spacing: 2px;
  opacity: 0.6;
}

@media (max-width: 768px) {
  .login-panel {
    flex-direction: column;
    margin: 16px;
  }

  .brand {
    padding: 32px 24px;
  }

  .brand h1 {
    font-size: 24px;
  }

  .login-form {
    padding: 32px 24px;
  }
}
</style>
