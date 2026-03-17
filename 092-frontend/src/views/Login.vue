<template>
  <div class="login-page">
    <div class="login-copy">
      <div class="eyebrow">Blue Sky Kindergarten</div>
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
        <div class="panel-title">系统登录</div>
        <div class="panel-subtitle">输入账号后进入蓝天幼儿园管理工作台</div>
      </div>
      <el-form ref="formRef" :model="form" :rules="rules" label-position="top" @keyup.enter="handleLogin">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名" size="large" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" type="password" show-password placeholder="请输入密码" size="large" />
        </el-form-item>
        <el-button class="login-button" :loading="loading" @click="handleLogin">进入系统</el-button>
      </el-form>
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
}

.eyebrow {
  color: var(--brand);
  letter-spacing: 0.28em;
  text-transform: uppercase;
  font-size: 12px;
  font-weight: 700;
}

.login-copy h1 {
  margin: 18px 0 18px;
  font-size: clamp(42px, 4vw, 66px);
  line-height: 1.1;
  color: #3f2f1f;
  font-family: "STZhongsong", "Noto Serif SC", serif;
}

.login-copy p {
  max-width: 680px;
  line-height: 1.9;
  color: #66584b;
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
  border-radius: 24px;
  text-align: left;
  cursor: pointer;
  background: rgba(255, 255, 255, 0.76);
  box-shadow: var(--shadow);
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.demo-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 24px 42px rgba(120, 53, 15, 0.18);
}

.demo-role {
  font-size: 20px;
  font-family: "STZhongsong", "Noto Serif SC", serif;
  color: var(--brand-strong);
}

.demo-user {
  margin-top: 12px;
  color: #374151;
  font-weight: 700;
}

.demo-note {
  margin-top: 10px;
  line-height: 1.7;
  color: #6b7280;
  font-size: 13px;
}

.login-panel {
  padding: 34px;
  border-radius: 32px;
  background: rgba(255, 255, 255, 0.9);
  box-shadow: 0 22px 45px rgba(120, 53, 15, 0.16);
}

.panel-head {
  margin-bottom: 24px;
}

.panel-title {
  font-size: 30px;
  color: #1f2937;
  font-family: "STZhongsong", "Noto Serif SC", serif;
}

.panel-subtitle {
  margin-top: 8px;
  color: var(--subtle);
}

.login-button {
  width: 100%;
  height: 46px;
  border: none;
  border-radius: 14px;
  margin-top: 8px;
  background: linear-gradient(135deg, #9a3412, #0f766e);
  color: #fff;
  font-size: 16px;
  font-weight: 700;
}

@media (max-width: 960px) {
  .login-page {
    grid-template-columns: 1fr;
    padding: 32px 20px;
  }

  .demo-grid {
    grid-template-columns: 1fr;
  }
}
</style>
