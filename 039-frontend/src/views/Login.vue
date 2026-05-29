<template>
  <div class="login-page">
    <!-- 装饰背景 -->
    <div class="bg-notes">
      <div class="note note-1">♪</div>
      <div class="note note-2">♫</div>
      <div class="note note-3">♩</div>
      <div class="note note-4">♬</div>
      <div class="note note-5">🎵</div>
      <div class="note note-6">🎶</div>
    </div>

    <div class="login-box">
      <div class="logo-section">
        <div class="logo-icon">🎵</div>
        <h2 class="title">音乐分享平台</h2>
        <p class="subtitle">聆听世界 · 分享感动</p>
      </div>

      <el-form ref="formRef" :model="form" :rules="rules" label-width="0" class="login-form">
        <el-form-item prop="username">
          <el-input
            v-model="form.username"
            placeholder="请输入用户名"
            prefix-icon="User"
            size="large"
          />
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="form.password"
            type="password"
            placeholder="请输入密码"
            prefix-icon="Lock"
            size="large"
            show-password
          />
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            size="large"
            class="submit-btn"
            @click="handleLogin"
            :loading="loading"
          >
            <template v-if="!loading">
              <el-icon><Headset /></el-icon> 开启音乐之旅
            </template>
            <template v-else>登录中...</template>
          </el-button>
        </el-form-item>
      </el-form>

      <div class="login-footer">
        <el-divider>其他操作</el-divider>
        <div class="footer-links">
          <span class="hint-text">还没有账号？</span>
          <el-button type="primary" link @click="router.push('/register')" class="register-link">
            <el-icon><UserFilled /></el-icon> 立即注册
          </el-button>
        </div>
      </div>

      <div class="music-quote">
        <span>🎶 音乐是灵魂的语言 🎶</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store/user'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()
const formRef = ref()
const loading = ref(false)

const form = reactive({
  username: '',
  password: ''
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const handleLogin = async () => {
  await formRef.value.validate()
  loading.value = true
  try {
    await userStore.login(form)
    ElMessage.success('登录成功')
    router.push('/')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped lang="scss">
.login-page {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #1a1a2e 0%, #16213e 30%, #0f3460 60%, #533483 100%);
  position: relative;
  overflow: hidden;
}

.bg-notes {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  pointer-events: none;
}

.note {
  position: absolute;
  font-size: 48px;
  opacity: 0.15;
  color: #E91E63;
  animation: floatNote 10s ease-in-out infinite;
}

.note-1 { top: 15%; left: 10%; animation-delay: 0s; }
.note-2 { top: 25%; right: 15%; animation-delay: 1.5s; font-size: 36px; }
.note-3 { bottom: 30%; left: 20%; animation-delay: 3s; }
.note-4 { top: 50%; right: 10%; animation-delay: 4.5s; font-size: 40px; }
.note-5 { bottom: 15%; left: 15%; animation-delay: 2s; font-size: 56px; }
.note-6 { top: 10%; left: 50%; animation-delay: 5s; font-size: 44px; }

@keyframes floatNote {
  0%, 100% { transform: translateY(0) rotate(0deg); opacity: 0.15; }
  25% { transform: translateY(-30px) rotate(10deg); opacity: 0.25; }
  50% { transform: translateY(-15px) rotate(-5deg); opacity: 0.2; }
  75% { transform: translateY(-25px) rotate(5deg); opacity: 0.18; }
}

.login-box {
  width: 440px;
  padding: 48px 40px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 28px;
  box-shadow: 0 24px 64px rgba(0, 0, 0, 0.3);
  position: relative;
  z-index: 10;
  backdrop-filter: blur(20px);
  overflow: hidden;

  &::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    height: 6px;
    background: linear-gradient(90deg, #E91E63, #9C27B0, #673AB7, #3F51B5, #2196F3, #E91E63);
    background-size: 400% 100%;
    animation: musicGradient 6s linear infinite;
  }
}

@keyframes musicGradient {
  0% { background-position: 0% 0%; }
  100% { background-position: 400% 0%; }
}

.logo-section {
  text-align: center;
  margin-bottom: 36px;
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
  background: linear-gradient(135deg, #E91E63, #9C27B0, #673AB7);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  margin: 0 0 8px 0;
  letter-spacing: 3px;
}

.subtitle {
  color: #9C27B0;
  font-size: 14px;
  margin: 0;
  letter-spacing: 6px;
  font-weight: 500;
}

.login-form {
  margin-top: 24px;

  :deep(.el-input__wrapper) {
    border-radius: 12px !important;
    box-shadow: 0 2px 12px rgba(156, 39, 176, 0.08) !important;
    padding: 4px 12px;
    transition: all 0.3s ease;

    &:hover {
      box-shadow: 0 4px 16px rgba(156, 39, 176, 0.15) !important;
    }

    &.is-focus {
      box-shadow: 0 4px 20px rgba(156, 39, 176, 0.25) !important;
    }
  }

  :deep(.el-input__prefix) {
    color: #E91E63;
  }
}

.submit-btn {
  width: 100%;
  height: 50px;
  font-size: 16px;
  font-weight: 600;
  letter-spacing: 4px;
  border-radius: 14px !important;
  background: linear-gradient(135deg, #E91E63 0%, #9C27B0 50%, #673AB7 100%) !important;
  border: none !important;
  box-shadow: 0 8px 24px rgba(233, 30, 99, 0.3) !important;
  transition: all 0.3s ease !important;

  &:hover {
    transform: translateY(-3px);
    box-shadow: 0 12px 32px rgba(233, 30, 99, 0.4) !important;
  }

  &:active {
    transform: translateY(0);
  }
}

.login-footer {
  margin-top: 24px;

  :deep(.el-divider__text) {
    background: rgba(255, 255, 255, 0.95);
    color: #999;
    font-size: 12px;
  }
}

.footer-links {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
}

.hint-text {
  color: #666;
  font-size: 14px;
}

.register-link {
  font-weight: 600;
  font-size: 14px;
  display: inline-flex;
  align-items: center;
  gap: 4px;
}

.music-quote {
  text-align: center;
  margin-top: 24px;
  padding-top: 16px;
  border-top: 1px solid #f0f0f0;

  span {
    color: #E91E63;
    font-size: 12px;
    letter-spacing: 2px;
    opacity: 0.6;
  }
}

/* 响应式 */
@media (max-width: 480px) {
  .login-box {
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
