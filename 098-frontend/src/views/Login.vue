<template>
  <div class="login-page">
    <div class="login-visual">
      <div class="brand-block">
        <div class="brand-mark">K98</div>
        <h1>企业知识库智能问答与文档权限管理系统</h1>
        <p>统一管理知识空间、文档分段、访问授权、问答来源和反馈审计，让企业知识可查、可控、可追踪。</p>
      </div>
    </div>
    <div class="login-panel">
      <el-form ref="formRef" :model="form" :rules="rules" class="login-form">
        <h2>系统登录</h2>
        <el-form-item prop="username">
          <el-input v-model="form.username" size="large" placeholder="用户名" :prefix-icon="User" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" size="large" type="password" show-password placeholder="密码" :prefix-icon="Lock" @keyup.enter="handleLogin" />
        </el-form-item>
        <el-button type="primary" size="large" :loading="loading" class="login-btn" @click="handleLogin">登录</el-button>
        <div class="accounts">
          <el-tag>admin / 123456</el-tag>
          <el-tag type="success">editor / 123456</el-tag>
          <el-tag type="warning">staff / 123456</el-tag>
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
  grid-template-columns: minmax(0, 1.2fr) 440px;
  background: #10231f;
}

.login-visual {
  display: flex;
  align-items: center;
  padding: 72px;
  color: #f8fafc;
  background:
    linear-gradient(135deg, rgba(16, 35, 31, 0.94), rgba(15, 118, 110, 0.72)),
    url("https://images.unsplash.com/photo-1497366754035-f200968a6e72?auto=format&fit=crop&w=1600&q=80") center/cover;
}

.brand-block {
  max-width: 650px;
}

.brand-mark {
  width: 68px;
  height: 68px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 8px;
  background: #14b8a6;
  font-size: 24px;
  font-weight: 800;
  margin-bottom: 26px;
}

.brand-block h1 {
  margin: 0;
  font-size: 44px;
  line-height: 1.16;
  letter-spacing: 0;
}

.brand-block p {
  margin: 22px 0 0;
  color: #d1fae5;
  font-size: 17px;
  line-height: 1.8;
}

.login-panel {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 36px;
  background: #f8fafc;
}

.login-form {
  width: 100%;
  background: #fff;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  padding: 34px;
  box-shadow: 0 16px 40px rgba(15, 23, 42, 0.1);
}

.login-form h2 {
  margin: 0 0 24px;
  font-size: 24px;
}

.login-btn {
  width: 100%;
}

.accounts {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-top: 18px;
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
    font-size: 32px;
  }
}
</style>
