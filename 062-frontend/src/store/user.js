import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore('user', () => {
  const user = ref(JSON.parse(localStorage.getItem('user') || 'null'))
  const token = ref(localStorage.getItem('token') || '')

  const setUser = (u, t) => {
    user.value = u
    token.value = t
    localStorage.setItem('user', JSON.stringify(u))
    localStorage.setItem('token', t)
  }

  const logout = () => {
    user.value = null
    token.value = ''
    localStorage.removeItem('user')
    localStorage.removeItem('token')
  }

  const isRiskRole = () => ['ADMIN', 'ANALYST'].includes(user.value?.role)
  const isAdmin = () => user.value?.role === 'ADMIN'

  return { user, token, setUser, logout, isRiskRole, isAdmin }
})
