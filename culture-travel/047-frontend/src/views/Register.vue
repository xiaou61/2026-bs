<template>
  <div class="login-page">
    <div class="login-box">
      <h2>用户注册</h2>
      <el-form :model="form" @submit.prevent="handleRegister">
        <el-form-item><el-input v-model="form.username" placeholder="用户名" /></el-form-item>
        <el-form-item><el-input v-model="form.password" type="password" placeholder="密码" /></el-form-item>
        <el-form-item><el-input v-model="form.realName" placeholder="真实姓名" /></el-form-item>
        <el-form-item><el-input v-model="form.phone" placeholder="手机号" /></el-form-item>
        <el-form-item>
          <el-select v-model="form.role" placeholder="注册身份" style="width: 100%">
            <el-option label="玩家" :value="0" />
            <el-option label="店家" :value="1" />
            <el-option label="作者" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item><el-button type="primary" native-type="submit" style="width: 100%">注册</el-button></el-form-item>
      </el-form>
      <div style="text-align: center;"><router-link to="/login">已有账号？返回登录</router-link></div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { register } from '@/api'
import { ElMessage } from 'element-plus'

const router = useRouter()
const form = reactive({ username: '', password: '', realName: '', phone: '', role: 0 })

const handleRegister = async () => {
  const res: any = await register(form)
  if (res.code === 200) {
    ElMessage.success('注册成功')
    router.push('/login')
  } else {
    ElMessage.error(res.msg || '注册失败')
  }
}
</script>

<style scoped>
.login-page { min-height: 100vh; display: flex; justify-content: center; align-items: center; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); }
.login-box { width: 400px; padding: 40px; background: #fff; border-radius: 8px; box-shadow: 0 4px 20px rgba(0,0,0,0.2); }
.login-box h2 { text-align: center; margin-bottom: 30px; color: #333; }
</style>
