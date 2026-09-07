<template>
  <div class="auth-page">
    <div class="auth-card glass-card register-card">
      <div class="auth-form">
        <h2>创建账号</h2>
        <el-form ref="formRef" :model="form" :rules="rules" label-position="top">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="form.username" placeholder="请输入用户名" />
          </el-form-item>
          <el-form-item label="昵称" prop="realName">
            <el-input v-model="form.realName" placeholder="请输入昵称" />
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="form.password" type="password" show-password placeholder="请输入密码" />
          </el-form-item>
          <el-button type="primary" class="submit-btn" @click="handleSubmit">注册并登录</el-button>
        </el-form>
        <div class="auth-foot">
          <span>已经有账号？</span>
          <router-link to="/login">去登录</router-link>
        </div>
      </div>
      <div class="auth-side">
        <div class="section-eyebrow">加入社区</div>
        <h1 class="display-title auth-title">上传你的作品，让更多人点亮屏幕</h1>
        <p class="auth-text">注册后即可收藏壁纸、发布投稿并追踪审核进度。</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { login, register } from '../api'
import { useUserStore } from '../store/user'

const router = useRouter()
const userStore = useUserStore()
const formRef = ref()

const form = reactive({
  username: '',
  realName: '',
  password: ''
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  realName: [{ required: true, message: '请输入昵称', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const handleSubmit = async () => {
  await formRef.value.validate()
  await register(form)
  const res = await login({ username: form.username, password: form.password })
  userStore.setToken(res.data.token)
  userStore.setUserInfo(res.data.userInfo)
  ElMessage.success('注册成功')
  router.push('/')
}
</script>

<style scoped>
.auth-page {
  min-height: 100vh;
  display: grid;
  place-items: center;
  padding: 24px;
}

.register-card {
  width: min(1080px, 100%);
  display: grid;
  grid-template-columns: 0.9fr 1fr;
  border-radius: 34px;
  overflow: hidden;
}

.auth-form,
.auth-side {
  padding: 54px;
}

.auth-side {
  background:
    radial-gradient(circle at bottom right, rgba(245, 158, 11, 0.28), transparent 35%),
    linear-gradient(160deg, rgba(8, 17, 31, 0.76), rgba(9, 16, 29, 0.95)),
    url('https://picsum.photos/id/1011/900/1200') center/cover;
}

.auth-title {
  margin: 18px 0 16px;
  font-size: 48px;
  line-height: 1.1;
}

.auth-text,
.auth-foot {
  color: var(--text-sub);
}

.submit-btn {
  width: 100%;
  margin-top: 10px;
}

.auth-form h2 {
  margin: 0 0 24px;
  font-size: 28px;
}

.auth-foot {
  margin-top: 20px;
}

@media (max-width: 900px) {
  .register-card {
    grid-template-columns: 1fr;
  }
}
</style>
