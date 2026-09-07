import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import request from '@/utils/request'

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('token') || '')
  const userInfo = ref(JSON.parse(localStorage.getItem('userInfo') || 'null'))

  const isLoggedIn = computed(() => !!token.value)
  const role = computed(() => userInfo.value?.role || '')
  const isAdmin = computed(() => role.value === 'admin')
  const isLandlord = computed(() => role.value === 'landlord')
  const isTenant = computed(() => role.value === 'tenant')

  async function login(data) {
    const res = await request.post('/auth/login', data)
    token.value = res.data.token
    userInfo.value = res.data.user
    localStorage.setItem('token', res.data.token)
    localStorage.setItem('userInfo', JSON.stringify(res.data.user))
    return res
  }

  async function register(data) {
    return await request.post('/auth/register', data)
  }

  async function getUserInfo() {
    const res = await request.get('/auth/info')
    userInfo.value = res.data
    localStorage.setItem('userInfo', JSON.stringify(res.data))
    return res
  }

  function logout() {
    token.value = ''
    userInfo.value = null
    localStorage.removeItem('token')
    localStorage.removeItem('userInfo')
  }

  async function updateUserInfo(data) {
    const res = await request.put('/auth/update', data)
    await getUserInfo()
    return res
  }

  async function changePassword(data) {
    return await request.put('/auth/password', data)
  }

  return {
    token,
    userInfo,
    isLoggedIn,
    role,
    isAdmin,
    isLandlord,
    isTenant,
    login,
    register,
    getUserInfo,
    logout,
    updateUserInfo,
    changePassword
  }
})
