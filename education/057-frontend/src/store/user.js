import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore('user', () => {
  const admin = ref(JSON.parse(localStorage.getItem('admin') || 'null'))
  const token = ref(localStorage.getItem('token') || '')

  const setAdmin = (data) => {
    admin.value = data
    localStorage.setItem('admin', JSON.stringify(data))
  }

  const setToken = (data) => {
    token.value = data
    localStorage.setItem('token', data)
  }

  const clearUser = () => {
    admin.value = null
    token.value = ''
    localStorage.removeItem('admin')
    localStorage.removeItem('token')
  }

  return { admin, token, setAdmin, setToken, clearUser }
})
