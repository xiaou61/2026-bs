import { defineStore } from 'pinia'
import { getUserInfo } from '../api'

export const useUserStore = defineStore('user', {
  state: () => ({
    token: localStorage.getItem('token') || '',
    userInfo: JSON.parse(localStorage.getItem('userInfo') || 'null')
  }),
  getters: {
    role: (state) => state.userInfo?.role || '',
    displayName: (state) => state.userInfo?.realName || state.userInfo?.username || '未登录'
  },
  actions: {
    setAuth(token, userInfo) {
      this.setToken(token)
      this.setUserInfo(userInfo)
    },
    setToken(token) {
      this.token = token || ''
      localStorage.setItem('token', this.token)
    },
    setUserInfo(userInfo) {
      this.userInfo = userInfo || null
      localStorage.setItem('userInfo', JSON.stringify(this.userInfo))
    },
    async fetchUserInfo() {
      const res = await getUserInfo()
      this.setUserInfo(res.data)
      return res.data
    },
    logout() {
      this.token = ''
      this.userInfo = null
      localStorage.removeItem('token')
      localStorage.removeItem('userInfo')
    }
  }
})


