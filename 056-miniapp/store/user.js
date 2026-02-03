import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore('user', () => {
  const userInfo = ref(uni.getStorageSync('userInfo') || null)
  const token = ref(uni.getStorageSync('token') || '')

  const setUser = (info, t) => {
    userInfo.value = info
    token.value = t
    uni.setStorageSync('userInfo', info)
    uni.setStorageSync('token', t)
  }

  const logout = () => {
    userInfo.value = null
    token.value = ''
    uni.removeStorageSync('userInfo')
    uni.removeStorageSync('token')
  }

  return { userInfo, token, setUser, logout }
})
