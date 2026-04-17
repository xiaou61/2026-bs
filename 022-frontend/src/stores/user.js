import { defineStore } from 'pinia'
import { login, register, getUserInfo } from '@/api/user'
import { ElMessage } from 'element-plus'

const normalizeUser = (user) => {
  if (!user) return null
  return {
    ...user,
    role: user.role || (user.username === 'admin' ? 'admin' : 'student')
  }
}

export const useUserStore = defineStore('user', {
  state: () => ({
    token: localStorage.getItem('token') || '',
    userInfo: normalizeUser(JSON.parse(localStorage.getItem('userInfo') || 'null')),
    creditScore: JSON.parse(localStorage.getItem('userInfo') || 'null')?.creditScore || 100
  }),

  getters: {
    isLoggedIn: (state) => !!state.token,
    isAdmin: (state) => state.userInfo?.role === 'admin',
    isStudent: (state) => state.userInfo?.role === 'student'
  },

  actions: {
    async login(loginForm) {
      const res = await login(loginForm)
      this.token = res.data.token
      this.userInfo = normalizeUser(res.data.user)
      this.creditScore = this.userInfo?.creditScore || 100
      localStorage.setItem('token', this.token)
      localStorage.setItem('userInfo', JSON.stringify(this.userInfo))
      return res
    },

    async register(registerForm) {
      const res = await register(registerForm)
      ElMessage.success('注册成功，请登录')
      return res
    },

    async fetchUserInfo() {
      const res = await getUserInfo()
      this.userInfo = normalizeUser(res.data)
      this.creditScore = this.userInfo?.creditScore || 100
      localStorage.setItem('userInfo', JSON.stringify(this.userInfo))
      return res
    },

    initUserInfo() {
      const userInfo = localStorage.getItem('userInfo')
      if (userInfo) {
        this.userInfo = normalizeUser(JSON.parse(userInfo))
        this.creditScore = this.userInfo?.creditScore || 100
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
