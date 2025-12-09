import request from '@/utils/request'

/**
 * 用户注册
 */
export function register(data) {
    return request({
        url: '/auth/register',
        method: 'post',
        data
    })
}

/**
 * 用户登录
 */
export function login(data) {
    return request({
        url: '/auth/login',
        method: 'post',
        data
    })
}

/**
 * 获取当前用户信息
 */
export function getCurrentUserInfo() {
    return request({
        url: '/auth/info',
        method: 'get'
    })
}

/**
 * 更新个人信息
 */
export function updateProfile(data) {
    return request({
        url: '/auth/profile',
        method: 'put',
        data
    })
}

/**
 * 余额充值
 */
export function rechargeBalance(amount) {
    return request({
        url: '/user/balance/recharge',
        method: 'post',
        params: { amount }
    })
}
