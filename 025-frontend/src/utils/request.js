import axios from 'axios'
import { ElMessage } from 'element-plus'

const request = axios.create({
    baseURL: 'http://localhost:8081',
    timeout: 5000
})

// Request interceptor
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';
    // Add token if available
    // const user = localStorage.getItem("user");
    // if (user) {
    //     config.headers['token'] = JSON.parse(user).token;
    // }
    return config
}, error => {
    return Promise.reject(error)
})

// Response interceptor
request.interceptors.response.use(response => {
    let res = response.data;
    // If response is string, try to parse JSON
    if (typeof res === 'string') {
        res = res ? JSON.parse(res) : res
    }
    if (res.code === '200') {
        return res;
    } else {
        ElMessage.error(res.msg || 'Error');
        return Promise.reject(res.msg || 'Error');
    }
}, error => {
    console.log('err' + error)
    ElMessage.error(error.message)
    return Promise.reject(error)
})

export default request
