import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', {
  state: () => ({
    token: localStorage.getItem('service_token') || '',
    user: JSON.parse(localStorage.getItem('service_user') || 'null')
  }),
  actions: {
    setLogin(token, user) {
      this.token = token
      this.user = user
      localStorage.setItem('service_token', token)
      localStorage.setItem('service_user', JSON.stringify(user))
    },
    clear() {
      this.token = ''
      this.user = null
      localStorage.removeItem('service_token')
      localStorage.removeItem('service_user')
    }
  }
})
