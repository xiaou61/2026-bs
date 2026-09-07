<template>
  <view class="login-container">
    <view class="logo">
      <text class="title">短文写作竞赛</text>
    </view>
    <view class="form-box">
      <view class="form-item">
        <input v-model="form.username" placeholder="请输入用户名" />
      </view>
      <view class="form-item">
        <input v-model="form.password" type="password" placeholder="请输入密码" />
      </view>
      <button class="btn-login" @click="handleLogin">登录</button>
      <view class="register-link" @click="showRegister = true">没有账号？点击注册</view>
    </view>
    <view class="modal" v-if="showRegister">
      <view class="modal-content">
        <text class="modal-title">注册账号</text>
        <view class="form-item">
          <input v-model="registerForm.username" placeholder="用户名" />
        </view>
        <view class="form-item">
          <input v-model="registerForm.password" type="password" placeholder="密码" />
        </view>
        <view class="form-item">
          <input v-model="registerForm.nickname" placeholder="昵称" />
        </view>
        <view class="modal-btns">
          <button class="btn-cancel" @click="showRegister = false">取消</button>
          <button class="btn-confirm" @click="handleRegister">注册</button>
        </view>
      </view>
    </view>
  </view>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { login, register } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const form = reactive({ username: '', password: '' })
const registerForm = reactive({ username: '', password: '', nickname: '' })
const showRegister = ref(false)

const handleLogin = async () => {
  if (!form.username || !form.password) {
    return uni.showToast({ title: '请填写完整', icon: 'none' })
  }
  try {
    const res = await login(form)
    userStore.setUser(res.data.userInfo, res.data.token)
    uni.showToast({ title: '登录成功', icon: 'success' })
    setTimeout(() => uni.switchTab({ url: '/pages/index/index' }), 1000)
  } catch (e) {}
}

const handleRegister = async () => {
  if (!registerForm.username || !registerForm.password) {
    return uni.showToast({ title: '请填写完整', icon: 'none' })
  }
  try {
    await register(registerForm)
    uni.showToast({ title: '注册成功', icon: 'success' })
    showRegister.value = false
    form.username = registerForm.username
  } catch (e) {}
}
</script>

<style scoped>
.login-container { min-height: 100vh; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); padding: 100rpx 40rpx; }
.logo { text-align: center; margin-bottom: 80rpx; }
.logo .title { color: #fff; font-size: 48rpx; font-weight: bold; }
.form-box { background: #fff; border-radius: 20rpx; padding: 50rpx 40rpx; }
.form-item { margin-bottom: 30rpx; }
.form-item input { height: 90rpx; background: #f5f5f5; border-radius: 12rpx; padding: 0 30rpx; font-size: 28rpx; }
.btn-login { background: #409EFF; color: #fff; border: none; height: 90rpx; line-height: 90rpx; border-radius: 12rpx; font-size: 32rpx; margin-top: 20rpx; }
.register-link { text-align: center; color: #409EFF; font-size: 26rpx; margin-top: 30rpx; }
.modal { position: fixed; top: 0; left: 0; right: 0; bottom: 0; background: rgba(0,0,0,0.5); display: flex; justify-content: center; align-items: center; }
.modal-content { background: #fff; width: 80%; border-radius: 20rpx; padding: 40rpx; }
.modal-title { font-size: 32rpx; font-weight: bold; text-align: center; margin-bottom: 30rpx; display: block; }
.modal-btns { display: flex; gap: 20rpx; margin-top: 30rpx; }
.modal-btns button { flex: 1; height: 80rpx; line-height: 80rpx; border-radius: 12rpx; font-size: 28rpx; }
.btn-cancel { background: #f5f5f5; color: #666; }
.btn-confirm { background: #409EFF; color: #fff; }
</style>
