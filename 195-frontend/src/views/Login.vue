<template><div class="login"><el-card><h2>便民服务中心事项预约与窗口评价平台</h2><el-form :model="form"><el-form-item><el-input v-model="form.username" placeholder="账号" /></el-form-item><el-form-item><el-input v-model="form.password" placeholder="密码" type="password" /></el-form-item><el-button type="primary" style="width:100%" @click="handleLogin">登录</el-button></el-form></el-card></div></template>
<script setup>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { login } from '../api'
import { useUserStore } from '../store/user'
const router = useRouter()
const userStore = useUserStore()
const form = reactive({ username: 'admin', password: '123456' })
const handleLogin = async () => { const res = await login(form); userStore.setLogin(res.data); router.push('/dashboard') }
</script>
<style scoped>.login{height:100vh;display:flex;align-items:center;justify-content:center;background:#eef2f7}.el-card{width:420px}h2{font-size:20px;margin:0 0 22px;text-align:center}</style>
