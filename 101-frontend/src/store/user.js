import { defineStore } from 'pinia'

export const useUserStore = defineStore('recruit-match-user', {
  state: () => ({
    token: localStorage.getItem('recruit_match_token') || '',
    user: JSON.parse(localStorage.getItem('recruit_match_user') || 'null')
  }),
  actions: {
    setLogin(token, user) {
      this.token = token
      this.user = user
      localStorage.setItem('recruit_match_token', token)
      localStorage.setItem('recruit_match_user', JSON.stringify(user))
    },
    logout() {
      this.token = ''
      this.user = null
      localStorage.removeItem('recruit_match_token')
      localStorage.removeItem('recruit_match_user')
    }
  }
})
