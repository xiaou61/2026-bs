<template>
  <div class="login-page">
    <!-- 装饰背景 -->
    <div class="bg-decoration">
      <div class="deco deco-1">⚽</div>
      <div class="deco deco-2">🏆</div>
      <div class="deco deco-3">🥅</div>
      <div class="deco deco-4">⭐</div>
    </div>

    <div class="login-card">
      <div class="intro">
        <div class="tag">095 PROJECT</div>
        <div class="logo-icon">⚽</div>
        <h2>足球联赛管理系统</h2>
        <p>覆盖联赛赛季、俱乐部球队、教练球员、赛程赛果、积分榜、球队关注与统计看板。</p>
        <div class="features">
          <div class="feature-item">
            <span class="feature-icon">🏆</span>
            <span>联赛管理</span>
          </div>
          <div class="feature-item">
            <span class="feature-icon">📊</span>
            <span>积分榜单</span>
          </div>
          <div class="feature-item">
            <span class="feature-icon">⚽</span>
            <span>赛程赛果</span>
          </div>
        </div>
      </div>
      <el-form :model="form" :rules="rules" ref="formRef" class="form">
        <div class="form-header">
          <div class="form-icon">⚽</div>
          <h3 class="form-title">系统登录</h3>
          <p class="form-subtitle">输入账号进入足球联赛管理</p>
        </div>
        <el-form-item prop="username">
          <el-input v-model="form.username" placeholder="请输入账号" prefix-icon="User" size="large" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" type="password" show-password placeholder="请输入密码" prefix-icon="Lock" size="large" />
        </el-form-item>
        <el-button type="primary" class="submit" size="large" @click="handleLogin">
          <el-icon><Key /></el-icon> 登录系统
        </el-button>
        <div class="accounts">
          <el-divider>测试账号</el-divider>
          <div class="account-tags">
            <el-tag effect="plain" round>admin / 123456</el-tag>
            <el-tag type="warning" effect="plain" round>manager / 123456</el-tag>
            <el-tag type="success" effect="plain" round>fan / 123456</el-tag>
          </div>
        </div>
        <div class="football-tips">
          <span>⚽ 绿茵场上 · 谁与争锋 ⚽</span>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { login } from '../api'
import { useUserStore } from '../store/user'

const router = useRouter()
const userStore = useUserStore()
const formRef = ref()
const form = reactive({
  username: 'admin',
  password: '123456'
})
const rules = {
  username: [{ required: true, message: '请输入账号', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const handleLogin = async () => {
  await formRef.value.validate()
  const res = await login(form)
  userStore.setLogin(res.data)
  ElMessage.success('登录成功')
  router.push('/dashboard')
}
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #1B5E20 0%, #2E7D32 30%, #43A047 60%, #66BB6A 100%);
  padding: 24px;
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
.deco-3 { bottom: 25%; left: 20%; animation-delay: 4s; font-size: 40px; }
.deco-4 { top: 50%; right: 10%; animation-delay: 1s; }

@keyframes floatDeco {
  0%, 100% { transform: translateY(0) rotate(0deg); opacity: 0.15; }
  25% { transform: translateY(-15px) rotate(5deg); opacity: 0.25; }
  50% { transform: translateY(-8px) rotate(-3deg); opacity: 0.2; }
  75% { transform: translateY(-12px) rotate(3deg); opacity: 0.18; }
}

.login-card {
  width: 980px;
  display: grid;
  grid-template-columns: 1.1fr 0.9fr;
  background: rgba(255, 255, 255, 0.96);
  border-radius: 28px;
  overflow: hidden;
  box-shadow: 0 30px 60px rgba(27, 94, 32, 0.3);
  position: relative;
  z-index: 10;
}

.intro {
  padding: 56px 48px;
  color: #fff;
  background: linear-gradient(135deg, #1B5E20 0%, #2E7D32 50%, #43A047 100%);
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.tag {
  display: inline-flex;
  padding: 6px 16px;
  border-radius: 999px;
  background: rgba(255, 255, 255, 0.2);
  color: #fff;
  font-size: 12px;
  margin-bottom: 18px;
  font-weight: 600;
  letter-spacing: 2px;
  width: fit-content;
}

.logo-icon {
  font-size: 64px;
  margin-bottom: 16px;
  animation: bounce 2s ease-in-out infinite;
  display: inline-block;
  width: fit-content;
}

@keyframes bounce {
  0%, 100% { transform: translateY(0) scale(1); }
  50% { transform: translateY(-10px) scale(1.1); }
}

.intro h2 {
  margin: 0 0 14px;
  font-size: 32px;
  font-weight: 800;
  letter-spacing: 2px;
}

.intro p {
  margin: 0;
  line-height: 1.8;
  color: rgba(255, 255, 255, 0.8);
  font-size: 15px;
}

.features {
  margin-top: 32px;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.feature-item {
  display: flex;
  align-items: center;
  gap: 12px;
  color: rgba(255, 255, 255, 0.9);
  font-size: 14px;
}

.feature-icon {
  font-size: 20px;
}

.form {
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
  background: linear-gradient(135deg, #1B5E20, #43A047);
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

.form :deep(.el-input__wrapper) {
  border-radius: 12px !important;
  box-shadow: 0 2px 12px rgba(27, 94, 32, 0.08) !important;
  padding: 4px 12px;
  transition: all 0.3s ease;
}

.form :deep(.el-input__wrapper:hover) {
  box-shadow: 0 4px 16px rgba(27, 94, 32, 0.15) !important;
}

.form :deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 4px 20px rgba(27, 94, 32, 0.25) !important;
}

.form :deep(.el-input__prefix) {
  color: #43A047;
}

.submit {
  width: 100%;
  height: 50px;
  margin-top: 8px;
  font-size: 16px;
  font-weight: 600;
  letter-spacing: 4px;
  border-radius: 14px !important;
  background: linear-gradient(135deg, #1B5E20 0%, #2E7D32 50%, #43A047 100%) !important;
  border: none !important;
  box-shadow: 0 8px 24px rgba(27, 94, 32, 0.3) !important;
  transition: all 0.3s ease !important;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
}

.submit:hover {
  transform: translateY(-3px);
  box-shadow: 0 12px 32px rgba(27, 94, 32, 0.4) !important;
}

.accounts {
  margin-top: 24px;
}

.accounts :deep(.el-divider__text) {
  background: rgba(255, 255, 255, 0.96);
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

.football-tips {
  text-align: center;
  margin-top: 24px;
  padding-top: 16px;
  border-top: 1px solid #E8F5E9;
}

.football-tips span {
  color: #43A047;
  font-size: 14px;
  letter-spacing: 2px;
  opacity: 0.6;
}

@media (max-width: 768px) {
  .login-card {
    grid-template-columns: 1fr;
    width: 100%;
  }

  .intro {
    padding: 32px 24px;
  }

  .form {
    padding: 32px 24px;
  }

  .intro h2 {
    font-size: 24px;
  }
}
</style>
