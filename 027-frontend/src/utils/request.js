import axios from 'axios'
import { ElMessage } from 'element-plus'

// 创建axios实例
const request = axios.create({
    baseURL: '/api',
    timeout: 10000
})

// 请求拦截器
request.interceptors.request.use(
    config => {
        // 添加token
        const token = localStorage.getItem('token')
        if (token) {
            config.headers['Authorization'] = `Bearer ${token}`
        }
        return config
    },
    error => {
        return Promise.reject(error)
    }
)

// 响应拦截器
request.interceptors.response.use(
    response => {
        const res = response.data

        // 如果返回的code不是200，视为错误
        if (res.code !== 200) {
            ElMessage.error(res.message || '请求失败')

            // 401: 未登录或token过期
            if (res.code === 401) {
                localStorage.removeItem('token')
                localStorage.removeItem('userInfo')
                window.location.href = '/login'
            }

            return Promise.reject(new Error(res.message || '请求失败'))
        } else {
            return res
        }
    },
    error => {
        console.error('请求错误：', error)

        if (error.response) {
            switch (error.response.status) {
                case 401:
                    ElMessage.error('未登录或登录已过期')
                    localStorage.removeItem('token')
                    localStorage.removeItem('userInfo')
                    window.location.href = '/login'
                    break
                case 403:
                    ElMessage.error('没有权限访问')
                    break
                case 404:
                    ElMessage.error('请求的资源不存在')
                    break
                case 500:
                    ElMessage.error('服务器错误')
                    break
                default:
                    ElMessage.error(error.response.data.message || '请求失败')
            }
        } else {
            ElMessage.error('网络错误，请检查网络连接')
        }

        return Promise.reject(error)
    }
)

export default request
