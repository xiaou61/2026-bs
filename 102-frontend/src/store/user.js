import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', {
  state: () => ({
    token: localStorage.getItem('legal_token') || '',
    user: JSON.parse(localStorage.getItem('legal_user') || 'null')
  }),
  actions: {
    setLogin(token, user) {
      this.token = token
      this.user = user
      localStorage.setItem('legal_token', token)
      localStorage.setItem('legal_user', JSON.stringify(user))
    },
    clear() {
      this.token = ''
      this.user = null
      localStorage.removeItem('legal_token')
      localStorage.removeItem('legal_user')
    }
  }
})
