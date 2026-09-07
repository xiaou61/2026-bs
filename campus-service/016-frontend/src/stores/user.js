import { defineStore } from 'pinia'
import { ref } from 'vue'
import { getUserInfo } from '../api/auth'

export const useUserStore = defineStore('user', () => {
  const userInfo = ref(null)
  const token = ref(localStorage.getItem('token') || '')
  const userType = ref(localStorage.getItem('userType') || '')

  const setToken = (newToken) => {
    token.value = newToken
    localStorage.setItem('token', newToken)
  }

  const setUserType = (type) => {
    userType.value = type
    localStorage.setItem('userType', type)
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
    userType.value = ''
    userInfo.value = null
    localStorage.removeItem('token')
    localStorage.removeItem('userType')
    localStorage.removeItem('userInfo')
  }

  const init = () => {
    token.value = localStorage.getItem('token') || ''
    userType.value = localStorage.getItem('userType') || ''
    const savedUserInfo = localStorage.getItem('userInfo')
    if (savedUserInfo) {
      userInfo.value = JSON.parse(savedUserInfo)
    }
  }

  return {
    userInfo,
    token,
    userType,
    setToken,
    setUserType,
    setUserInfo,
    loadUserInfo,
    logout,
    init
  }
})

