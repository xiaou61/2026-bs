import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore('user', () =&gt; {
  const token = ref(localStorage.getItem('token') || '')
  const userInfo = ref(null)

  const setToken = (newToken) =&gt; {
    token.value = newToken
    localStorage.setItem('token', newToken)
  }

  const setUserInfo = (info) =&gt; {
    userInfo.value = info
  }

  const logout = () =&gt; {
    token.value = ''
    userInfo.value = null
    localStorage.removeItem('token')
  }

  return {
    token,
    userInfo,
    setToken,
    setUserInfo,
    logout
  }
})
