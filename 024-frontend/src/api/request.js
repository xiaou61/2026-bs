import axios from 'axios'
import { ElMessage } from 'element-plus'

const request = axios.create({
    baseURL: 'http://localhost:8080',
    timeout: 5000
})

// Request Interceptor
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';
    // Add token if exists
    // const user = JSON.parse(localStorage.getItem("user") || "{}")
    // if (user.token) {
    //     config.headers['token'] = user.token;
    // }
    return config
}, error => {
    return Promise.reject(error)
})

// Response Interceptor
request.interceptors.response.use(response => {
    let res = response.data;
    if (res.code === 200) {
        return res
    } else {
        ElMessage.error(res.msg || 'Error')
        return Promise.reject(res.msg)
    }
}, error => {
    ElMessage.error(error.message || 'Network Error')
    return Promise.reject(error)
})

export default request
