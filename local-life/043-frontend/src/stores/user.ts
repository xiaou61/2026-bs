import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('token') || '')
  const user = ref(JSON.parse(localStorage.getItem('user') || '{}'))

  const setLogin = (data: any) => {
    token.value = data.token
    user.value = data
    localStorage.setItem('token', data.token)
    localStorage.setItem('user', JSON.stringify(data))
  }

  const logout = () => {
    token.value = ''
    user.value = {}
    localStorage.removeItem('token')
    localStorage.removeItem('user')
  }

  return { token, user, setLogin, logout }
})
