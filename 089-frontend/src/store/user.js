import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', {
  state: () => ({
    token: localStorage.getItem('token') || '',
    user: JSON.parse(localStorage.getItem('user') || 'null')
  }),
  getters: {
    role: state => (state.user?.role || '').toUpperCase(),
    isAdmin: state => (state.user?.role || '').toUpperCase() === 'ADMIN',
    isDispatcher: state => (state.user?.role || '').toUpperCase() === 'DISPATCHER',
    isUser: state => (state.user?.role || '').toUpperCase() === 'USER'
  },
  actions: {
    setLogin(data) {
      this.token = data.token
      this.user = data.user
      localStorage.setItem('token', data.token || '')
      localStorage.setItem('user', JSON.stringify(data.user || null))
    },
    clearLogin() {
      this.token = ''
      this.user = null
      localStorage.removeItem('token')
      localStorage.removeItem('user')
    }
  }
})
