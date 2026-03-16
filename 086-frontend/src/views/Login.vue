<template>
  <div class="auth-page">
    <div class="auth-card glass-card">
      <div class="auth-side">
        <div class="section-eyebrow">欢迎回来</div>
        <h1 class="display-title auth-title">登录你的壁纸空间</h1>
        <p class="auth-text">登录后可收藏喜欢的作品、管理自己的投稿，并进入后台进行运营审核。</p>
      </div>
      <div class="auth-form">
        <h2>账号登录</h2>
        <el-form ref="formRef" :model="form" :rules="rules" label-position="top">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="form.username" placeholder="请输入用户名" />
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="form.password" type="password" show-password placeholder="请输入密码" @keyup.enter="handleSubmit" />
          </el-form-item>
          <el-button type="primary" class="submit-btn" @click="handleSubmit">登录</el-button>
        </el-form>
        <div class="auth-foot">
          <span>还没有账号？</span>
          <router-link to="/register">立即注册</router-link>
        </div>
      </div>
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
const formRef = ref()

const form = reactive({
  username: 'admin',
  password: '123456'
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const handleSubmit = async () => {
  await formRef.value.validate()
  const res = await login(form)
  userStore.setToken(res.data.token)
  userStore.setUserInfo(res.data.userInfo)
  ElMessage.success('登录成功')
  router.push(res.data.userInfo?.role === 'admin' ? '/admin/dashboard' : '/')
}
</script>

<style scoped>
.auth-page {
  min-height: 100vh;
  display: grid;
  place-items: center;
  padding: 24px;
}

.auth-card {
  width: min(1080px, 100%);
  display: grid;
  grid-template-columns: 1fr 0.9fr;
  border-radius: 34px;
  overflow: hidden;
}

.auth-side {
  padding: 54px;
  background:
    radial-gradient(circle at top left, rgba(125, 211, 252, 0.35), transparent 35%),
    linear-gradient(160deg, rgba(8, 17, 31, 0.7), rgba(9, 16, 29, 0.95)),
    url('https://picsum.photos/id/1003/900/1200') center/cover;
}

.auth-title {
  margin: 18px 0 16px;
  font-size: 52px;
  line-height: 1.08;
}

.auth-text,
.auth-foot {
  color: var(--text-sub);
  line-height: 1.8;
}

.auth-form {
  padding: 54px;
}

.auth-form h2 {
  margin: 0 0 24px;
  font-size: 28px;
}

.submit-btn {
  width: 100%;
  margin-top: 10px;
}

.auth-foot {
  margin-top: 20px;
}

@media (max-width: 900px) {
  .auth-card {
    grid-template-columns: 1fr;
  }
}
</style>
