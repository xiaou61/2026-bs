import { defineStore } from 'pinia'
import { ref } from 'vue'
import { useRouter } from 'vue-router'

export const useUserStore = defineStore('user', () => {
  const router = useRouter()
  const token = ref(localStorage.getItem('token') || '')
  const userId = ref(Number(localStorage.getItem('userId')) || 0)
  const username = ref(localStorage.getItem('username') || '')
  const role = ref(Number(localStorage.getItem('role')) || 0)

  const setUser = (data: any) => {
    token.value = data.token
    userId.value = data.userId
    username.value = data.username
    role.value = data.role
    localStorage.setItem('token', data.token)
    localStorage.setItem('userId', data.userId)
    localStorage.setItem('username', data.username)
    localStorage.setItem('role', data.role)
  }

  const logout = () => {
    token.value = ''
    userId.value = 0
    username.value = ''
    role.value = 0
    localStorage.removeItem('token')
    localStorage.removeItem('userId')
    localStorage.removeItem('username')
    localStorage.removeItem('role')
  }

  const redirectByRole = () => {
    // 0-游客,1-讲解员,2-研究员,3-管理员
    switch (role.value) {
      case 3: router.push('/admin'); break
      case 2: router.push('/researcher'); break
      case 1: router.push('/guide'); break
      default: router.push('/user')
    }
  }

  return { token, userId, username, role, setUser, logout, redirectByRole }
})
