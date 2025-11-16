import { defineStore } from 'pinia'
import { login, register, getUserInfo } from '@/api/user'
import { ElMessage } from 'element-plus'

export const useUserStore = defineStore('user', {
  state: () => ({
    token: localStorage.getItem('token') || '',
    userInfo: null,
    creditScore: 100
  }),

  getters: {
    isLoggedIn: (state) => !!state.token,
    isAdmin: (state) => state.userInfo?.role === 'admin',
    isStudent: (state) => state.userInfo?.role === 'student'
  },

  actions: {
    async login(loginForm) {
      try {
        const res = await login(loginForm)
        this.token = res.data.token
        this.userInfo = res.data.user
        this.creditScore = res.data.user.creditScore
        localStorage.setItem('token', this.token)
        localStorage.setItem('userInfo', JSON.stringify(this.userInfo))
        return res
      } catch (error) {
        throw error
      }
    },

    async register(registerForm) {
      try {
        const res = await register(registerForm)
        ElMessage.success('注册成功，请登录')
        return res
      } catch (error) {
        throw error
      }
    },

    async fetchUserInfo() {
      try {
        const res = await getUserInfo()
        this.userInfo = res.data
        this.creditScore = res.data.creditScore
        localStorage.setItem('userInfo', JSON.stringify(this.userInfo))
        return res
      } catch (error) {
        throw error
      }
    },

    initUserInfo() {
      const userInfo = localStorage.getItem('userInfo')
      if (userInfo) {
        this.userInfo = JSON.parse(userInfo)
        this.creditScore = this.userInfo.creditScore
      }
    },

    logout() {
      this.token = ''
      this.userInfo = null
      this.creditScore = 100
      localStorage.removeItem('token')
      localStorage.removeItem('userInfo')
    },

    updateCreditScore(score) {
      this.creditScore = score
      if (this.userInfo) {
        this.userInfo.creditScore = score
        localStorage.setItem('userInfo', JSON.stringify(this.userInfo))
      }
    }
  }
})