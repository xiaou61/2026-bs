import { defineStore } from 'pinia'
import { ref } from 'vue'
import { getUserInfo } from '../api/auth'

export const useUserStore = defineStore('user', () => {
  const userInfo = ref(null)
  const token = ref(localStorage.getItem('token') || '')

  const setToken = (newToken) => {
    token.value = newToken
    localStorage.setItem('token', newToken)
  }

  const setUserInfo = (info) => {
    userInfo.value = info
    localStorage.setItem('userInfo', JSON.stringify(info))
  }

  const loadUserInfo = async () => {
    try {
      const info = await getUserInfo()
      setUserInfo(info)
      return info
    } catch (error) {
      console.error('获取用户信息失败', error)
      throw error
    }
  }

  const logout = () => {
    token.value = ''
    userInfo.value = null
    localStorage.removeItem('token')
    localStorage.removeItem('userInfo')
  }

  const init = () => {
    const savedUserInfo = localStorage.getItem('userInfo')
    if (savedUserInfo) {
      userInfo.value = JSON.parse(savedUserInfo)
    }
  }

  return {
    userInfo,
    token,
    setToken,
    setUserInfo,
    loadUserInfo,
    logout,
    init
  }
})

