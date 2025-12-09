import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { login, getCurrentUserInfo } from '@/api/auth'

export const useUserStore = defineStore('user', () => {
    const token = ref(localStorage.getItem('token') || '')
    const userInfo = ref(JSON.parse(localStorage.getItem('userInfo') || 'null'))

    // 是否已登录
    const isLoggedIn = computed(() => !!token.value)

    // 会员等级折扣
    const memberDiscount = computed(() => {
        if (!userInfo.value) return 1.0
        switch (userInfo.value.level) {
            case '钻石会员': return 0.85
            case '金卡会员': return 0.90
            case '银卡会员': return 0.95
            default: return 1.0
        }
    })

    // 登录
    async function userLogin(loginData) {
        const res = await login(loginData)
        token.value = res.data.token
        userInfo.value = res.data.userInfo

        localStorage.setItem('token', res.data.token)
        localStorage.setItem('userInfo', JSON.stringify(res.data.userInfo))
    }

    // 登出
    function logout() {
        token.value = ''
        userInfo.value = null
        localStorage.removeItem('token')
        localStorage.removeItem('userInfo')
    }

    // 获取用户信息
    async function fetchUserInfo() {
        const res = await getCurrentUserInfo()
        userInfo.value = res.data
        localStorage.setItem('userInfo', JSON.stringify(res.data))
    }

    // 更新用户信息
    function updateUserInfo(newUserInfo) {
        userInfo.value = newUserInfo
        localStorage.setItem('userInfo', JSON.stringify(newUserInfo))
    }

    return {
        token,
        userInfo,
        isLoggedIn,
        memberDiscount,
        userLogin,
        logout,
        fetchUserInfo,
        updateUserInfo
    }
})
