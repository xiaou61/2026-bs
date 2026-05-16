<template><div class="login"><el-card><h2>校园图书漂流借阅与读书打卡平台</h2><el-form :model="form"><el-form-item><el-input v-model="form.username" placeholder="账号" /></el-form-item><el-form-item><el-input v-model="form.password" placeholder="密码" type="password" /></el-form-item><el-button type="primary" style="width:100%" @click="handleLogin">登录</el-button></el-form><div class="accounts"><span>admin</span><span>library</span><span>curator</span><span>student</span><span>club</span><span>teacher</span></div></el-card></div></template>
<script setup>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { login } from '../api'
import { useUserStore } from '../store/user'
const router = useRouter()
const userStore = useUserStore()
const form = reactive({ username: 'admin', password: '123456' })
const home = { ADMIN: '/dashboard', LIBRARY: '/book', CURATOR: '/donation', STUDENT: '/checkin', CLUB: '/activity', TEACHER: '/note' }
const handleLogin = async () => { const res = await login(form); userStore.setLogin(res.data); router.push(home[res.data.user.role] || '/dashboard') }
</script>
<style scoped>.login{height:100vh;display:flex;align-items:center;justify-content:center;background:#edf4f2}.el-card{width:430px}h2{font-size:20px;margin:0 0 22px;text-align:center}.accounts{display:flex;flex-wrap:wrap;gap:8px;margin-top:14px;color:#64748b;font-size:13px}.accounts span{background:#f1f5f9;border-radius:6px;padding:4px 8px}</style>
