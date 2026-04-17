<template>
  <div class="auth-page">
    <div class="intro">
      <span class="badge">Campus Loop</span>
      <h1>登录后继续你的校园交易</h1>
      <p>
        发布闲置、发起聊天、收藏心仪商品、跟进订单状态，都从这里开始。
      </p>
      <ul>
        <li>支持校园实名信息注册</li>
        <li>信用分随交易评价动态变化</li>
        <li>议价和聊天记录统一管理</li>
      </ul>
    </div>

    <el-card class="form-card" shadow="never">
      <template #header>
        <div class="card-header">
          <div>
            <h2>欢迎回来</h2>
            <p>输入账号密码进入平台</p>
          </div>
        </div>
      </template>

      <el-form ref="formRef" :model="form" :rules="rules" label-position="top" @submit.prevent>
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名" size="large" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input
            v-model="form.password"
            type="password"
            show-password
            placeholder="请输入密码"
            size="large"
            @keyup.enter="handleLogin"
          />
        </el-form-item>

        <el-button type="primary" size="large" :loading="submitting" class="submit-btn" @click="handleLogin">
          登录
        </el-button>
      </el-form>

      <div class="footer">
        <span>还没有账号？</span>
        <el-link type="primary" @click="router.push('/register')">去注册</el-link>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()
const formRef = ref()
const submitting = ref(false)

const form = reactive({
  username: '',
  password: ''
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const handleLogin = async () => {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) {
    return
  }

  submitting.value = true
  try {
    await userStore.login(form)
    await userStore.getUserInfo()
    ElMessage.success('登录成功')
    router.push('/')
  } finally {
    submitting.value = false
  }
}
</script>

<style scoped>
.auth-page {
  min-height: 100vh;
  display: grid;
  grid-template-columns: minmax(0, 1.1fr) minmax(420px, 480px);
  gap: 32px;
  align-items: center;
  padding: 48px;
  background:
    radial-gradient(circle at left top, rgba(255, 200, 122, 0.28), transparent 30%),
    radial-gradient(circle at right center, rgba(96, 165, 250, 0.24), transparent 24%),
    #f8fafc;
}

.intro {
  padding: 0 28px;
}

.badge {
  display: inline-block;
  padding: 8px 14px;
  border-radius: 999px;
  background: rgba(255, 138, 76, 0.12);
  color: #c2410c;
  margin-bottom: 18px;
}

.intro h1 {
  font-size: 48px;
  line-height: 1.15;
  margin-bottom: 16px;
  color: #0f172a;
}

.intro p {
  color: #475569;
  line-height: 1.9;
  max-width: 560px;
}

.intro ul {
  margin-top: 24px;
  padding-left: 20px;
  color: #334155;
  line-height: 2;
}

.form-card {
  border-radius: 28px;
  border: 1px solid rgba(148, 163, 184, 0.18);
  background: rgba(255, 255, 255, 0.92);
}

.card-header h2 {
  font-size: 28px;
  margin-bottom: 6px;
}

.card-header p {
  color: #64748b;
}

.submit-btn {
  width: 100%;
  margin-top: 12px;
  height: 48px;
}

.footer {
  margin-top: 24px;
  display: flex;
  justify-content: center;
  gap: 6px;
  color: #64748b;
}

@media (max-width: 960px) {
  .auth-page {
    grid-template-columns: 1fr;
    padding: 24px;
  }

  .intro {
    padding: 0;
  }

  .intro h1 {
    font-size: 36px;
  }
}
</style>
