<template>
  <div class="login-page">
    <div class="login-card">
      <div class="intro">
        <div class="tag">095 PROJECT</div>
        <h2>SpringBoot 足球联赛管理系统</h2>
        <p>覆盖联赛赛季、俱乐部球队、教练球员、赛程赛果、积分榜、球队关注与统计看板。</p>
      </div>
      <el-form :model="form" :rules="rules" ref="formRef" class="form">
        <el-form-item prop="username">
          <el-input v-model="form.username" placeholder="请输入账号" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" type="password" show-password placeholder="请输入密码" />
        </el-form-item>
        <el-button type="primary" class="submit" @click="handleLogin">登录系统</el-button>
        <div class="accounts">
          <div>管理员：admin / 123456</div>
          <div>俱乐部经理：manager / 123456</div>
          <div>球迷：fan / 123456</div>
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
  background:
    radial-gradient(circle at top left, rgba(43, 124, 198, 0.28), transparent 34%),
    radial-gradient(circle at bottom right, rgba(29, 185, 84, 0.24), transparent 28%),
    linear-gradient(135deg, #0f2547, #163c6f 46%, #1c548e 100%);
  padding: 24px;
}

.login-card {
  width: 980px;
  display: grid;
  grid-template-columns: 1.1fr 0.9fr;
  background: rgba(255, 255, 255, 0.96);
  border-radius: 24px;
  overflow: hidden;
  box-shadow: 0 30px 60px rgba(10, 23, 48, 0.3);
}

.intro {
  padding: 56px 48px;
  color: #14365d;
  background: linear-gradient(180deg, #f5faff 0%, #eaf2ff 100%);
}

.tag {
  display: inline-flex;
  padding: 6px 12px;
  border-radius: 999px;
  background: #d8e8ff;
  color: #2c67a7;
  font-size: 12px;
  margin-bottom: 18px;
}

.intro h2 {
  margin: 0 0 14px;
  font-size: 34px;
}

.intro p {
  margin: 0;
  line-height: 1.8;
  color: #52606d;
}

.form {
  padding: 56px 48px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.submit {
  width: 100%;
  height: 44px;
  margin-top: 6px;
}

.accounts {
  margin-top: 18px;
  padding: 14px 16px;
  border-radius: 12px;
  background: #f8fafc;
  color: #667085;
  line-height: 1.9;
}
</style>
