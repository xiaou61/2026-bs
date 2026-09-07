import { defineStore } from 'pinia'

export const useUserStore = defineStore('aigc-copyright-user', {
  state: () => ({
    token: localStorage.getItem('aigc_copyright_token') || '',
    user: JSON.parse(localStorage.getItem('aigc_copyright_user') || 'null')
  }),
  actions: {
    setLogin(token, user) {
      this.token = token
      this.user = user
      localStorage.setItem('aigc_copyright_token', token)
      localStorage.setItem('aigc_copyright_user', JSON.stringify(user))
    },
    setUser(user) {
      this.user = user
      localStorage.setItem('aigc_copyright_user', JSON.stringify(user))
    },
    logout() {
      this.token = ''
      this.user = null
      localStorage.removeItem('aigc_copyright_token')
      localStorage.removeItem('aigc_copyright_user')
    }
  }
})
