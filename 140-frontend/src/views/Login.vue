<template>
  <div class="login">
    <!-- 装饰背景 -->
    <div class="bg-decoration">
      <div class="deco deco-1">📝</div>
      <div class="deco deco-2">🔏</div>
      <div class="deco deco-3">📋</div>
      <div class="deco deco-4">✅</div>
    </div>

    <el-card class="login-card" shadow="always">
      <div class="logo-section">
        <div class="logo-icon">📝</div>
        <h2 class="title">电子合同签署与印章审批</h2>
        <p class="subtitle">面向企业电子合同场景的模板签署、用印审批、归档提醒和风险控制平台</p>
      </div>

      <el-form :model="form" class="login-form" @keyup.enter="submit">
        <el-form-item>
          <el-input v-model="form.username" placeholder="请输入账号" prefix-icon="User" size="large" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.password" type="password" placeholder="请输入密码" prefix-icon="Lock" show-password size="large" />
        </el-form-item>
        <el-button type="primary" size="large" class="submit-btn" @click="submit">
          <el-icon><Key /></el-icon> 合同登录
        </el-button>
      </el-form>

      <div class="accounts">
        <el-divider>测试账号</el-divider>
        <div class="account-tags">
          <el-tag effect="plain" round>admin / 123456</el-tag>
          <el-tag type="success" effect="plain" round>legal / 123456</el-tag>
          <el-tag type="warning" effect="plain" round>applicant / 123456</el-tag>
          <el-tag type="info" effect="plain" round>approver / 123456</el-tag>
        </div>
      </div>

      <div class="contract-tips">
        <span>📝 电子签约 · 安全可信 📝</span>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { login } from '../api'
import { useUserStore } from '../store/user'
const ROLE_HOME = {
  ADMIN: '/dashboard',
  LEGAL: '/template',
  APPLICANT: '/draft',
  APPROVER: '/approval'
}
const router = useRouter()
const userStore = useUserStore()
const form = reactive({ username: 'admin', password: '123456' })
const submit = async () => {
  const res = await login(form)
  userStore.setAuth(res.data.token, res.data.user)
  ElMessage.success('登录成功')
  router.push(ROLE_HOME[res.data.user?.role] || '/dashboard')
}
</script>

<style scoped>
.login {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #1565C0 0%, #1976D2 30%, #2196F3 60%, #42A5F5 100%);
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
.deco-3 { bottom: 25%; left: 20%; animation-delay: 4s; }
.deco-4 { top: 50%; right: 10%; animation-delay: 1s; font-size: 56px; }

@keyframes floatDeco {
  0%, 100% { transform: translateY(0) rotate(0deg); }
  25% { transform: translateY(-15px) rotate(5deg); }
  50% { transform: translateY(-8px) rotate(-3deg); }
  75% { transform: translateY(-12px) rotate(3deg); }
}

.login-card {
  width: 480px;
  padding: 48px 40px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 28px;
  box-shadow: 0 24px 64px rgba(21, 101, 192, 0.3);
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
  background: linear-gradient(90deg, #1565C0, #1976D2, #2196F3, #42A5F5, #1565C0);
  background-size: 300% 100%;
  animation: contractGradient 4s linear infinite;
}

@keyframes contractGradient {
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
  animation: sign 2s ease-in-out infinite;
  display: inline-block;
}

@keyframes sign {
  0%, 100% { transform: rotate(0deg); }
  25% { transform: rotate(-10deg); }
  75% { transform: rotate(10deg); }
}

.title {
  font-size: 24px;
  font-weight: 800;
  background: linear-gradient(135deg, #0D47A1, #1565C0, #1976D2);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  margin: 0 0 8px 0;
  letter-spacing: 2px;
}

.subtitle {
  color: #1976D2;
  font-size: 13px;
  margin: 0;
  letter-spacing: 1px;
  font-weight: 500;
}

.login-form {
  margin-top: 24px;
}

.login-form :deep(.el-input__wrapper) {
  border-radius: 12px !important;
  box-shadow: 0 2px 12px rgba(21, 101, 192, 0.08) !important;
  padding: 4px 12px;
  transition: all 0.3s ease;
}

.login-form :deep(.el-input__wrapper:hover) {
  box-shadow: 0 4px 16px rgba(21, 101, 192, 0.15) !important;
}

.login-form :deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 4px 20px rgba(21, 101, 192, 0.25) !important;
}

.login-form :deep(.el-input__prefix) {
  color: #1565C0;
}

.submit-btn {
  width: 100%;
  height: 50px;
  font-size: 16px;
  font-weight: 600;
  letter-spacing: 4px;
  border-radius: 14px !important;
  background: linear-gradient(135deg, #0D47A1 0%, #1565C0 50%, #1976D2 100%) !important;
  border: none !important;
  box-shadow: 0 8px 24px rgba(21, 101, 192, 0.3) !important;
  transition: all 0.3s ease !important;
  margin-top: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
}

.submit-btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 12px 32px rgba(21, 101, 192, 0.4) !important;
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

.contract-tips {
  text-align: center;
  margin-top: 24px;
  padding-top: 16px;
  border-top: 1px solid #E3F2FD;
}

.contract-tips span {
  color: #1565C0;
  font-size: 12px;
  letter-spacing: 2px;
  opacity: 0.6;
}

@media (max-width: 480px) {
  .login-card {
    width: 90%;
    margin: 16px;
    padding: 32px 24px;
  }

  .title {
    font-size: 18px;
  }

  .logo-icon {
    font-size: 56px;
  }
}
</style>






