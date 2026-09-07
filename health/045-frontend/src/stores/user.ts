import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { login as loginApi } from '@/api'
import router from '@/router'

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('token') || '')
  const userInfo = ref<any>(JSON.parse(localStorage.getItem('userInfo') || '{}'))

  const isLogin = computed(() => !!token.value)
  const role = computed(() => userInfo.value?.role ?? -1)
  const isAdmin = computed(() => role.value === 2)
  const isNurse = computed(() => role.value === 1)
  const isFamily = computed(() => role.value === 0)

  const login = async (username: string, password: string) => {
    const res: any = await loginApi({ username, password })
    token.value = res.data.token
    userInfo.value = res.data
    localStorage.setItem('token', res.data.token)
    localStorage.setItem('userInfo', JSON.stringify(res.data))
    
    // 根据角色跳转
    if (res.data.role === 2) {
      router.push('/admin')
    } else if (res.data.role === 1) {
      router.push('/nurse')
    } else {
      router.push('/family')
    }
  }

  const logout = () => {
    token.value = ''
    userInfo.value = {}
    localStorage.removeItem('token')
    localStorage.removeItem('userInfo')
    router.push('/login')
  }

  return { token, userInfo, isLogin, role, isAdmin, isNurse, isFamily, login, logout }
})
