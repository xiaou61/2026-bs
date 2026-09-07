import { defineStore } from 'pinia'
import { ref } from 'vue'
import { login as loginApi, getInfo } from '@/api/auth'
import { ElMessage } from 'element-plus'
import router from '@/router'

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('token') || '')
  const userInfo = ref(JSON.parse(localStorage.getItem('user') || 'null'))

  const login = async (username, password) => {
    try {
      const res = await loginApi({ username, password })
      token.value = res.data.token
      userInfo.value = res.data.user
      localStorage.setItem('token', res.data.token)
      localStorage.setItem('user', JSON.stringify(res.data.user))
      ElMessage.success('登录成功')
      
      if (res.data.user.role === 'admin') {
        router.push('/admin')
      } else if (res.data.user.role === 'teacher') {
        router.push('/teacher')
      } else {
        router.push('/student')
      }
    } catch (error) {
      throw error
    }
  }

  const logout = () => {
    token.value = ''
    userInfo.value = null
    localStorage.removeItem('token')
    localStorage.removeItem('user')
    router.push('/login')
    ElMessage.success('退出登录成功')
  }

  const refreshInfo = async () => {
    try {
      const res = await getInfo()
      return res.data
    } catch (error) {
      logout()
    }
  }

  return {
    token,
    userInfo,
    login,
    logout,
    refreshInfo
  }
})

