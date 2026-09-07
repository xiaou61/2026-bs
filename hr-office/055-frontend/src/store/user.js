import { defineStore } from 'pinia'
import { getUserInfo } from '../api'

export const useUserStore = defineStore('user', {
  state: () => ({
    userInfo: JSON.parse(localStorage.getItem('userInfo') || '{}')
  }),
  getters: {
    isAdmin: state => state.userInfo.role === 'admin',
    isManager: state => state.userInfo.role === 'manager',
    isEmployee: state => state.userInfo.role === 'employee'
  },
  actions: {
    async fetchUserInfo() {
      const res = await getUserInfo()
      this.userInfo = res.data
      localStorage.setItem('userInfo', JSON.stringify(res.data))
    },
    setUserInfo(info) {
      this.userInfo = info
      localStorage.setItem('userInfo', JSON.stringify(info))
    },
    logout() {
      this.userInfo = {}
      localStorage.removeItem('token')
      localStorage.removeItem('userInfo')
    }
  }
})
