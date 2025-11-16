import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { userApi } from '@/api/user'

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('token') || '')
  const userInfo = ref(JSON.parse(localStorage.getItem('userInfo') || '{}'))

  const isLoggedIn = computed(() => !!token.value)

  // 登录
  const login = async (loginForm) => {
    try {
      const response = await userApi.login(loginForm)
      token.value = response.data.token
      userInfo.value = response.data.userInfo

      localStorage.setItem('token', token.value)
      localStorage.setItem('userInfo', JSON.stringify(userInfo.value))

      return response
    } catch (error) {
      throw error
    }
  }

  // 注册
  const register = async (registerForm) => {
    try {
      const response = await userApi.register(registerForm)
      return response
    } catch (error) {
      throw error
    }
  }

  // 退出登录
  const logout = () => {
    token.value = ''
    userInfo.value = {}
    localStorage.removeItem('token')
    localStorage.removeItem('userInfo')
  }

  // 获取用户信息
  const getUserInfo = async () => {
    try {
      const response = await userApi.getUserInfo()
      userInfo.value = response.data
      localStorage.setItem('userInfo', JSON.stringify(userInfo.value))
      return response
    } catch (error) {
      throw error
    }
  }

  // 更新用户信息
  const updateUserInfo = async (updateForm) => {
    try {
      const response = await userApi.updateUserInfo(updateForm)
      await getUserInfo() // 重新获取用户信息
      return response
    } catch (error) {
      throw error
    }
  }

  return {
    token,
    userInfo,
    isLoggedIn,
    login,
    register,
    logout,
    getUserInfo,
    updateUserInfo
  }
})