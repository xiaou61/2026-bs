import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import * as userApi from '../api/user'

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('token') || '')
  const userInfo = ref(readStoredUserInfo())
  const isLoggedIn = computed(() => !!token.value)

  // 登录
  const login = async (credentials) => {
    try {
      const response = await userApi.login(credentials)
      const { token: authToken, ...user } = response.data
      token.value = authToken
      localStorage.setItem('token', authToken)
      localStorage.setItem('userInfo', JSON.stringify(user))
      userInfo.value = user
      
      return response
    } catch (error) {
      throw error
    }
  }

  // 注册
  const register = async (formData) => {
    try {
      const response = await userApi.register(formData)
      return response
    } catch (error) {
      throw error
    }
  }

  // 获取用户信息
  const fetchUserInfo = async () => {
    try {
      const response = await userApi.getUserInfo()
      userInfo.value = response.data
      localStorage.setItem('userInfo', JSON.stringify(response.data))
      return response
    } catch (error) {
      throw error
    }
  }

  // 更新用户信息
  const updateUserInfo = async (data) => {
    try {
      const response = await userApi.updateUserInfo(data)
      userInfo.value = { ...userInfo.value, ...data }
      localStorage.setItem('userInfo', JSON.stringify(userInfo.value))
      return response
    } catch (error) {
      throw error
    }
  }

  // 登出
  const logout = () => {
    token.value = ''
    userInfo.value = null
    localStorage.removeItem('token')
    localStorage.removeItem('userInfo')
  }

  // 从 localStorage 恢复状态
  const restoreState = () => {
    const savedToken = localStorage.getItem('token')
      const savedUserInfo = localStorage.getItem('userInfo')
    
    if (savedToken) {
      token.value = savedToken
    }
    
    if (savedUserInfo) {
      try {
        userInfo.value = JSON.parse(savedUserInfo)
      } catch (e) {
        console.error('Failed to parse saved user info:', e)
      }
    }
  }

  return {
    token,
    userInfo,
    isLoggedIn,
    login,
    register,
    fetchUserInfo,
    updateUserInfo,
    logout,
    restoreState
  }
})

function readStoredUserInfo() {
  const savedUserInfo = localStorage.getItem('userInfo')
  if (!savedUserInfo) {
    return null
  }

  try {
    return JSON.parse(savedUserInfo)
  } catch (error) {
    console.error('Failed to parse saved user info:', error)
    localStorage.removeItem('userInfo')
    return null
  }
}
