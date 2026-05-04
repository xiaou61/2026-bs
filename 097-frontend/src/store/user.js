import { defineStore } from 'pinia'

export const useUserStore = defineStore('prompt-user', {
  state: () => ({
    token: localStorage.getItem('prompt_token') || '',
    user: JSON.parse(localStorage.getItem('prompt_user') || 'null')
  }),
  actions: {
    setLogin(token, user) {
      this.token = token
      this.user = user
      localStorage.setItem('prompt_token', token)
      localStorage.setItem('prompt_user', JSON.stringify(user))
    },
    setUser(user) {
      this.user = user
      localStorage.setItem('prompt_user', JSON.stringify(user))
    },
    logout() {
      this.token = ''
      this.user = null
      localStorage.removeItem('prompt_token')
      localStorage.removeItem('prompt_user')
    }
  }
})
