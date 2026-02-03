<template>
  <view class="container">
    <view class="form-item">
      <text class="label">昵称</text>
      <input v-model="form.nickname" placeholder="请输入昵称" />
    </view>
    <view class="form-item">
      <text class="label">手机号</text>
      <input v-model="form.phone" placeholder="请输入手机号" />
    </view>
    <view class="form-item">
      <text class="label">邮箱</text>
      <input v-model="form.email" placeholder="请输入邮箱" />
    </view>
    <button class="btn-save" @click="handleSave">保存</button>
  </view>
</template>

<script setup>
import { reactive, onMounted } from 'vue'
import { updateUserInfo, getUserInfo } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const form = reactive({ nickname: '', phone: '', email: '' })

const handleSave = async () => {
  try {
    await updateUserInfo(form)
    const res = await getUserInfo()
    userStore.setUser(res.data, uni.getStorageSync('token'))
    uni.showToast({ title: '保存成功', icon: 'success' })
  } catch (e) {}
}

onMounted(() => {
  if (userStore.userInfo) {
    form.nickname = userStore.userInfo.nickname || ''
    form.phone = userStore.userInfo.phone || ''
    form.email = userStore.userInfo.email || ''
  }
})
</script>

<style scoped>
.form-item { background: #fff; padding: 24rpx; margin-bottom: 2rpx; display: flex; align-items: center; }
.form-item .label { font-size: 28rpx; width: 150rpx; }
.form-item input { flex: 1; font-size: 28rpx; text-align: right; }
.btn-save { margin: 50rpx 20rpx; background: #409EFF; color: #fff; border: none; height: 90rpx; line-height: 90rpx; border-radius: 12rpx; font-size: 32rpx; }
</style>
