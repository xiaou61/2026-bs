import { defineStore } from 'pinia'

export const useUserStore = defineStore('knowledge-user', {
  state: () => ({
    token: localStorage.getItem('knowledge_token') || '',
    user: JSON.parse(localStorage.getItem('knowledge_user') || 'null')
  }),
  actions: {
    setLogin(token, user) {
      this.token = token
      this.user = user
      localStorage.setItem('knowledge_token', token)
      localStorage.setItem('knowledge_user', JSON.stringify(user))
    },
    setUser(user) {
      this.user = user
      localStorage.setItem('knowledge_user', JSON.stringify(user))
    },
    logout() {
      this.token = ''
      this.user = null
      localStorage.removeItem('knowledge_token')
      localStorage.removeItem('knowledge_user')
    }
  }
})
