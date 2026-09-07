import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('token') || '')
  const userInfo = ref(JSON.parse(localStorage.getItem('userInfo') || '{}'))
  const userId = ref(localStorage.getItem('userId') || '')

  function setToken(newToken) {
    token.value = newToken
    localStorage.setItem('token', newToken)
  }

  function setUserInfo(info) {
    userInfo.value = info
    userId.value = info.id
    localStorage.setItem('userInfo', JSON.stringify(info))
    localStorage.setItem('userId', info.id)
  }

  function logout() {
    token.value = ''
    userInfo.value = {}
    userId.value = ''
    localStorage.removeItem('token')
    localStorage.removeItem('userInfo')
    localStorage.removeItem('userId')
  }

  return {
    token,
    userInfo,
    userId,
    setToken,
    setUserInfo,
    logout
  }
})
