import { defineStore } from 'pinia'
import { userApi } from '../api'

export const useUserStore = defineStore('user', {
  state: () => ({
    token: localStorage.getItem('token') || '',
    userInfo: JSON.parse(localStorage.getItem('userInfo') || '{}')
  }),
  
  actions: {
    setToken(token) {
      this.token = token
      localStorage.setItem('token', token)
    },
    
    setUserInfo(userInfo) {
      this.userInfo = userInfo
      localStorage.setItem('userInfo', JSON.stringify(userInfo))
    },
    
    async login(data) {
      const res = await userApi.login(data)
      this.setToken(res.data.token)
      this.setUserInfo(res.data.userInfo)
      return res
    },
    
    async register(data) {
      const res = await userApi.register(data)
      this.setToken(res.data.token)
      this.setUserInfo(res.data.userInfo)
      return res
    },
    
    logout() {
      this.token = ''
      this.userInfo = {}
      localStorage.removeItem('token')
      localStorage.removeItem('userInfo')
    }
  }
})
