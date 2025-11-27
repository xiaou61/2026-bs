import axios from 'axios'
import { ElMessage } from 'element-plus'

const request = axios.create({
    baseURL: 'http://localhost:8082',
    timeout: 10000
})

// 请求拦截器
request.interceptors.request.use(
    config => {
        // 从localStorage获取token
        const user = localStorage.getItem('user')
        if (user) {
            const userObj = JSON.parse(user)
            config.headers['Authorization'] = userObj.token || ''
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
        if (res.code === '200') {
            return res.data
        } else {
            ElMessage.error(res.msg || '请求失败')
            return Promise.reject(new Error(res.msg || '请求失败'))
        }
    },
    error => {
        ElMessage.error(error.message || '网络错误')
        return Promise.reject(error)
    }
)

export default request
