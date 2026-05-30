<template>
  <div class="login-page">
    <!-- 装饰背景 -->
    <div class="bg-decoration">
      <div class="deco deco-1">📚</div>
      <div class="deco deco-2">💡</div>
      <div class="deco deco-3">📖</div>
      <div class="deco deco-4">🔍</div>
    </div>

    <div class="login-visual">
      <div class="brand-block">
        <div class="brand-mark">📚</div>
        <div class="brand-tag">KNOWLEDGE BASE</div>
        <h1>企业知识库智能问答与文档权限管理系统</h1>
        <p>统一管理知识空间、文档分段、访问授权、问答来源和反馈审计，让企业知识可查、可控、可追踪。</p>
        <div class="features">
          <div class="feature-item">
            <span class="feature-icon">📖</span>
            <span>知识管理</span>
          </div>
          <div class="feature-item">
            <span class="feature-icon">🤖</span>
            <span>智能问答</span>
          </div>
          <div class="feature-item">
            <span class="feature-icon">🔐</span>
            <span>权限控制</span>
          </div>
        </div>
      </div>
    </div>
    <div class="login-panel">
      <el-form ref="formRef" :model="form" :rules="rules" class="login-form">
        <div class="form-header">
          <div class="form-icon">🔐</div>
          <h2>系统登录</h2>
          <p class="form-subtitle">输入账号进入知识库系统</p>
        </div>
        <el-form-item prop="username">
          <el-input v-model="form.username" size="large" placeholder="请输入用户名" prefix-icon="User" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" size="large" type="password" show-password placeholder="请输入密码" prefix-icon="Lock" @keyup.enter="handleLogin" />
        </el-form-item>
        <el-button type="primary" size="large" :loading="loading" class="login-btn" @click="handleLogin">
          <template v-if="!loading">
            <el-icon><Key /></el-icon> 进入知识库
          </template>
          <template v-else>登录中...</template>
        </el-button>
        <div class="accounts">
          <el-divider>测试账号</el-divider>
          <div class="account-tags">
            <el-tag effect="plain" round>admin / 123456</el-tag>
            <el-tag type="success" effect="plain" round>editor / 123456</el-tag>
            <el-tag type="warning" effect="plain" round>staff / 123456</el-tag>
          </div>
        </div>
        <div class="knowledge-tips">
          <span>📚 知识就是力量 · 学习成就未来 📚</span>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { Lock, User } from '@element-plus/icons-vue'
import { login } from '../api'
import { useUserStore } from '../store/user'

const router = useRouter()
const userStore = useUserStore()
const loading = ref(false)
const formRef = ref()
const form = reactive({ username: 'admin', password: '123456' })
const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const handleLogin = async () => {
  await formRef.value.validate()
  loading.value = true
  try {
    const res = await login(form)
    userStore.setLogin(res.data.token, res.data.user)
    router.push('/dashboard')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  display: grid;
  grid-template-columns: minmax(0, 1.2fr) 480px;
  background: linear-gradient(135deg, #0f766e 0%, #14b8a6 50%, #2dd4bf 100%);
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
  opacity: 0.08;
  animation: floatDeco 10s ease-in-out infinite;
}

.deco-1 { top: 15%; left: 10%; animation-delay: 0s; font-size: 64px; }
.deco-2 { top: 25%; right: 15%; animation-delay: 2s; }
.deco-3 { bottom: 25%; left: 20%; animation-delay: 4s; font-size: 40px; }
.deco-4 { top: 50%; right: 10%; animation-delay: 6s; font-size: 56px; }

@keyframes floatDeco {
  0%, 100% { transform: translateY(0) rotate(0deg); }
  25% { transform: translateY(-15px) rotate(5deg); }
  50% { transform: translateY(-8px) rotate(-3deg); }
  75% { transform: translateY(-12px) rotate(3deg); }
}

.login-visual {
  display: flex;
  align-items: center;
  padding: 72px;
  color: #f8fafc;
  background: linear-gradient(135deg, #0f766e 0%, #14b8a6 50%, #0f766e 100%);
  position: relative;
  overflow: hidden;
}

.login-visual::before {
  content: '';
  position: absolute;
  top: -50%;
  right: -50%;
  width: 100%;
  height: 100%;
  background: radial-gradient(circle, rgba(20, 184, 166, 0.2) 0%, transparent 70%);
  animation: glow 8s ease-in-out infinite;
}

@keyframes glow {
  0%, 100% { transform: scale(1); opacity: 0.5; }
  50% { transform: scale(1.2); opacity: 0.8; }
}

.brand-block {
  max-width: 650px;
  position: relative;
  z-index: 2;
}

.brand-mark {
  width: 80px;
  height: 80px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 20px;
  background: linear-gradient(135deg, #14b8a6, #2dd4bf);
  font-size: 40px;
  margin-bottom: 20px;
  box-shadow: 0 12px 32px rgba(20, 184, 166, 0.3);
  animation: pulse 2s ease-in-out infinite;
}

@keyframes pulse {
  0%, 100% { transform: scale(1); }
  50% { transform: scale(1.05); }
}

.brand-tag {
  display: inline-flex;
  padding: 6px 16px;
  border-radius: 999px;
  background: rgba(20, 184, 166, 0.2);
  color: #2dd4bf;
  font-size: 12px;
  font-weight: 700;
  letter-spacing: 3px;
  margin-bottom: 20px;
}

.brand-block h1 {
  margin: 0;
  font-size: 40px;
  line-height: 1.2;
  letter-spacing: 2px;
  font-weight: 800;
}

.brand-block p {
  margin: 22px 0 0;
  color: rgba(255, 255, 255, 0.7);
  font-size: 16px;
  line-height: 1.8;
}

.features {
  margin-top: 40px;
  display: flex;
  gap: 24px;
}

.feature-item {
  display: flex;
  align-items: center;
  gap: 8px;
  color: rgba(255, 255, 255, 0.8);
  font-size: 14px;
  padding: 12px 20px;
  background: rgba(255, 255, 255, 0.1);
  border-radius: 12px;
  border: 1px solid rgba(255, 255, 255, 0.15);
}

.feature-icon {
  font-size: 20px;
}

.login-panel {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 48px;
  background: rgba(248, 250, 252, 0.95);
  backdrop-filter: blur(20px);
}

.login-form {
  width: 100%;
  background: #fff;
  border: none;
  border-radius: 24px;
  padding: 48px 40px;
  box-shadow: 0 24px 64px rgba(15, 118, 110, 0.1);
  position: relative;
  overflow: hidden;
}

.login-form::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 6px;
  background: linear-gradient(90deg, #0f766e, #14b8a6, #2dd4bf, #0f766e);
  background-size: 300% 100%;
  animation: knowledgeGradient 4s linear infinite;
}

@keyframes knowledgeGradient {
  0% { background-position: 0% 0%; }
  100% { background-position: 300% 0%; }
}

.form-header {
  text-align: center;
  margin-bottom: 32px;
}

.form-icon {
  font-size: 48px;
  margin-bottom: 12px;
}

.login-form h2 {
  margin: 0 0 8px;
  font-size: 24px;
  font-weight: 800;
  background: linear-gradient(135deg, #0f766e, #14b8a6);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.form-subtitle {
  color: #9ca3af;
  font-size: 14px;
  margin: 0;
}

.login-form :deep(.el-input__wrapper) {
  border-radius: 12px !important;
  box-shadow: 0 2px 12px rgba(15, 118, 110, 0.08) !important;
  padding: 4px 12px;
  transition: all 0.3s ease;
}

.login-form :deep(.el-input__wrapper:hover) {
  box-shadow: 0 4px 16px rgba(15, 118, 110, 0.15) !important;
}

.login-form :deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 4px 20px rgba(15, 118, 110, 0.25) !important;
}

.login-form :deep(.el-input__prefix) {
  color: #14b8a6;
}

.login-btn {
  width: 100%;
  height: 50px;
  font-size: 16px;
  font-weight: 600;
  letter-spacing: 4px;
  border-radius: 14px !important;
  background: linear-gradient(135deg, #0f766e 0%, #14b8a6 50%, #2dd4bf 100%) !important;
  border: none !important;
  box-shadow: 0 8px 24px rgba(15, 118, 110, 0.3) !important;
  transition: all 0.3s ease !important;
  margin-top: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
}

.login-btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 12px 32px rgba(15, 118, 110, 0.4) !important;
}

.accounts {
  margin-top: 24px;
}

.accounts :deep(.el-divider__text) {
  background: #fff;
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

.knowledge-tips {
  text-align: center;
  margin-top: 24px;
  padding-top: 16px;
  border-top: 1px solid #f0f0f0;
}

.knowledge-tips span {
  color: #14b8a6;
  font-size: 12px;
  letter-spacing: 2px;
  opacity: 0.6;
}

@media (max-width: 900px) {
  .login-page {
    grid-template-columns: 1fr;
  }

  .login-visual {
    min-height: 320px;
    padding: 36px;
  }

  .brand-block h1 {
    font-size: 28px;
  }

  .features {
    flex-wrap: wrap;
    gap: 12px;
  }

  .login-panel {
    padding: 24px;
  }

  .login-form {
    padding: 32px 24px;
  }
}
</style>
