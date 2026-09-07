import { defineStore } from 'pinia'

export const useUserStore = defineStore('academic-integrity-user', {
  state: () => ({
    token: localStorage.getItem('academic_integrity_token') || '',
    user: JSON.parse(localStorage.getItem('academic_integrity_user') || 'null')
  }),
  actions: {
    setLogin(token, user) {
      this.token = token
      this.user = user
      localStorage.setItem('academic_integrity_token', token)
      localStorage.setItem('academic_integrity_user', JSON.stringify(user))
    },
    setUser(user) {
      this.user = user
      localStorage.setItem('academic_integrity_user', JSON.stringify(user))
    },
    logout() {
      this.token = ''
      this.user = null
      localStorage.removeItem('academic_integrity_token')
      localStorage.removeItem('academic_integrity_user')
    }
  }
})
