<template>
  <div class="login-page">
    <!-- 装饰背景 -->
    <div class="bg-decoration">
      <div class="deco deco-1">🌈</div>
      <div class="deco deco-2">⭐</div>
      <div class="deco deco-3">🎈</div>
      <div class="deco deco-4">🌻</div>
    </div>

    <div class="login-copy">
      <div class="eyebrow">Blue Sky Kindergarten</div>
      <div class="logo-icon">🌈</div>
      <h1>把园区管理、幼儿成长与家园协同收拢进同一套工作台。</h1>
      <p>
        092 项目聚焦蓝天幼儿园日常管理场景，支持园长、教师、家长三角色切换。你可以直接使用下面的默认账号体验不同视角。
      </p>
      <div class="demo-grid">
        <button v-for="item in accounts" :key="item.username" class="demo-card" @click="fillAccount(item)">
          <div class="demo-role">{{ item.label }}</div>
          <div class="demo-user">{{ item.username }} / {{ item.password }}</div>
          <div class="demo-note">{{ item.note }}</div>
        </button>
      </div>
    </div>
    <div class="login-panel">
      <div class="panel-head">
        <div class="panel-icon">🌈</div>
        <div class="panel-title">系统登录</div>
        <div class="panel-subtitle">输入账号后进入蓝天幼儿园管理工作台</div>
      </div>
      <el-form ref="formRef" :model="form" :rules="rules" label-position="top" @keyup.enter="handleLogin" class="login-form">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名" size="large" prefix-icon="User" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" type="password" show-password placeholder="请输入密码" size="large" prefix-icon="Lock" />
        </el-form-item>
        <el-button class="login-button" :loading="loading" @click="handleLogin">
          <template v-if="!loading">
            <el-icon><Key /></el-icon> 进入幼儿园
          </template>
          <template v-else>登录中...</template>
        </el-button>
      </el-form>
      <div class="panel-footer">
        <span>🌻 快乐成长 · 健康每一天 🌻</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { login } from '../api'
import { useUserStore } from '../store/user'

const router = useRouter()
const userStore = useUserStore()
const formRef = ref()
const loading = ref(false)

const form = reactive({
  username: 'admin',
  password: '123456'
})

const accounts = [
  { label: '园长', username: 'admin', password: '123456', note: '维护园区、班级、活动课程、公告与统计看板' },
  { label: '教师', username: 'teacher', password: '123456', note: '维护幼儿档案、活动安排、食谱、考勤和晨检记录' },
  { label: '家长', username: 'parent', password: '123456', note: '查看孩子档案、活动安排、食谱并提交家园反馈' }
]

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const fillAccount = (item) => {
  form.username = item.username
  form.password = item.password
}

const handleLogin = async () => {
  await formRef.value.validate()
  loading.value = true
  try {
    const res = await login(form)
    userStore.setAuth(res.data.token, res.data.userInfo)
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
  grid-template-columns: 1.1fr 0.9fr;
  gap: 48px;
  padding: 48px 6vw;
  align-items: center;
  background: linear-gradient(135deg, #FF9A9E 0%, #FAD0C4 30%, #FFD1FF 60%, #A8EDEA 100%);
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

.deco-1 { top: 10%; left: 5%; animation-delay: 0s; font-size: 56px; }
.deco-2 { top: 20%; right: 10%; animation-delay: 2s; font-size: 40px; }
.deco-3 { bottom: 20%; left: 15%; animation-delay: 4s; }
.deco-4 { bottom: 10%; right: 20%; animation-delay: 6s; font-size: 52px; }

@keyframes floatDeco {
  0%, 100% { transform: translateY(0) rotate(0deg); opacity: 0.2; }
  25% { transform: translateY(-20px) rotate(10deg); opacity: 0.3; }
  50% { transform: translateY(-10px) rotate(-5deg); opacity: 0.25; }
  75% { transform: translateY(-15px) rotate(5deg); opacity: 0.22; }
}

.eyebrow {
  color: #FF6B6B;
  letter-spacing: 0.28em;
  text-transform: uppercase;
  font-size: 13px;
  font-weight: 700;
}

.logo-icon {
  font-size: 72px;
  margin: 16px 0;
  animation: bounce 2s ease-in-out infinite;
  display: inline-block;
}

@keyframes bounce {
  0%, 100% { transform: translateY(0) scale(1); }
  50% { transform: translateY(-10px) scale(1.1); }
}

.login-copy h1 {
  margin: 18px 0 18px;
  font-size: clamp(36px, 3.5vw, 56px);
  line-height: 1.15;
  background: linear-gradient(135deg, #FF6B6B, #FF8E53, #FF6B6B);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  font-family: "STZhongsong", "Noto Serif SC", serif;
  font-weight: 800;
}

.login-copy p {
  max-width: 680px;
  line-height: 1.9;
  color: #666;
  font-size: 16px;
}

.demo-grid {
  margin-top: 28px;
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 16px;
}

.demo-card {
  border: none;
  padding: 20px;
  border-radius: 20px;
  text-align: left;
  cursor: pointer;
  background: rgba(255, 255, 255, 0.8);
  box-shadow: 0 8px 24px rgba(255, 107, 107, 0.1);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  backdrop-filter: blur(10px);
}

.demo-card:hover {
  transform: translateY(-6px);
  box-shadow: 0 16px 40px rgba(255, 107, 107, 0.2);
}

.demo-role {
  font-size: 18px;
  font-family: "STZhongsong", "Noto Serif SC", serif;
  background: linear-gradient(135deg, #FF6B6B, #FF8E53);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  font-weight: 700;
}

.demo-user {
  margin-top: 12px;
  color: #374151;
  font-weight: 700;
  font-size: 14px;
}

.demo-note {
  margin-top: 10px;
  line-height: 1.7;
  color: #6b7280;
  font-size: 13px;
}

.login-panel {
  padding: 48px;
  border-radius: 32px;
  background: rgba(255, 255, 255, 0.95);
  box-shadow: 0 24px 64px rgba(255, 107, 107, 0.15);
  backdrop-filter: blur(20px);
  position: relative;
  overflow: hidden;
}

.login-panel::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 6px;
  background: linear-gradient(90deg, #FF9A9E, #FAD0C4, #FFD1FF, #A8EDEA, #FF9A9E);
  background-size: 300% 100%;
  animation: kidGradient 4s linear infinite;
}

@keyframes kidGradient {
  0% { background-position: 0% 0%; }
  100% { background-position: 300% 0%; }
}

.panel-head {
  margin-bottom: 32px;
  text-align: center;
}

.panel-icon {
  font-size: 48px;
  margin-bottom: 12px;
}

.panel-title {
  font-size: 28px;
  background: linear-gradient(135deg, #FF6B6B, #FF8E53);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  font-family: "STZhongsong", "Noto Serif SC", serif;
  font-weight: 800;
}

.panel-subtitle {
  margin-top: 8px;
  color: #9ca3af;
  font-size: 14px;
}

.login-form :deep(.el-input__wrapper) {
  border-radius: 12px !important;
  box-shadow: 0 2px 12px rgba(255, 107, 107, 0.08) !important;
  padding: 4px 12px;
  transition: all 0.3s ease;
}

.login-form :deep(.el-input__wrapper:hover) {
  box-shadow: 0 4px 16px rgba(255, 107, 107, 0.15) !important;
}

.login-form :deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 4px 20px rgba(255, 107, 107, 0.25) !important;
}

.login-form :deep(.el-input__prefix) {
  color: #FF6B6B;
}

.login-form :deep(.el-form-item__label) {
  font-weight: 600;
  color: #374151;
}

.login-button {
  width: 100%;
  height: 50px;
  border: none;
  border-radius: 14px;
  margin-top: 16px;
  background: linear-gradient(135deg, #FF6B6B 0%, #FF8E53 50%, #FFD93D 100%);
  color: #fff;
  font-size: 16px;
  font-weight: 700;
  letter-spacing: 4px;
  box-shadow: 0 8px 24px rgba(255, 107, 107, 0.3);
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
}

.login-button:hover {
  transform: translateY(-3px);
  box-shadow: 0 12px 32px rgba(255, 107, 107, 0.4);
}

.panel-footer {
  text-align: center;
  margin-top: 24px;
  padding-top: 16px;
  border-top: 1px solid #f0f0f0;
}

.panel-footer span {
  color: #FF6B6B;
  font-size: 12px;
  letter-spacing: 2px;
  opacity: 0.6;
}

@media (max-width: 960px) {
  .login-page {
    grid-template-columns: 1fr;
    padding: 32px 20px;
  }

  .login-copy {
    text-align: center;
  }

  .demo-grid {
    grid-template-columns: 1fr;
  }

  .login-panel {
    padding: 32px;
  }
}
</style>
