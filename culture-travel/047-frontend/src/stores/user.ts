import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('token') || '')
  const userInfo = ref<any>(JSON.parse(localStorage.getItem('userInfo') || '{}'))

  const setLogin = (data: any) => {
    token.value = data.token
    userInfo.value = data
    localStorage.setItem('token', data.token)
    localStorage.setItem('userInfo', JSON.stringify(data))
  }

  const logout = () => {
    token.value = ''
    userInfo.value = {}
    localStorage.removeItem('token')
    localStorage.removeItem('userInfo')
  }

  const getRedirectPath = () => {
    const role = userInfo.value.role
    if (role === 3) return '/admin'
    if (role === 1) return '/owner'
    if (role === 2) return '/author'
    return '/user'
  }

  return { token, userInfo, setLogin, logout, getRedirectPath }
})
