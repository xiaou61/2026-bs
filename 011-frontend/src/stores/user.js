import { defineStore } from 'pinia'
import { getUserInfo as getUserInfoApi } from '@/api/auth'

export const useUserStore = defineStore('user', {
  state: () => ({
    userInfo: null,
    token: localStorage.getItem('token') || ''
  }),
  
  getters: {
    isLogin: (state) => !!state.token,
    userId: (state) => state.userInfo?.id
  },
  
  actions: {
    setToken(token) {
      this.token = token
      localStorage.setItem('token', token)
    },
    
    removeToken() {
      this.token = ''
      this.userInfo = null
      localStorage.removeItem('token')
    },
    
    async getUserInfo() {
      try {
        const res = await getUserInfoApi()
        this.userInfo = res.data
        return res.data
      } catch (error) {
        console.error('获取用户信息失败:', error)
        throw error
      }
    },
    
    updateUserInfo(info) {
      this.userInfo = { ...this.userInfo, ...info }
    }
  }
})

