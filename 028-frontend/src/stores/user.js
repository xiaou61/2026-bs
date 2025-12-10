import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('token') || '')
  const userInfo = ref(JSON.parse(localStorage.getItem('userInfo') || '{}'))
  const userType = ref(localStorage.getItem('userType') || '')

  const isLoggedIn = computed(() => !!token.value)

  function setToken(newToken) {
    token.value = newToken
    localStorage.setItem('token', newToken)
  }

  function setUserInfo(info) {
    userInfo.value = info
    localStorage.setItem('userInfo', JSON.stringify(info))
  }

  function setUserType(type) {
    userType.value = type
    localStorage.setItem('userType', type)
  }

  function logout() {
    token.value = ''
    userInfo.value = {}
    userType.value = ''
    localStorage.removeItem('token')
    localStorage.removeItem('userInfo')
    localStorage.removeItem('userType')
  }

  return {
    token,
    userInfo,
    userType,
    isLoggedIn,
    setToken,
    setUserInfo,
    setUserType,
    logout
  }
})
