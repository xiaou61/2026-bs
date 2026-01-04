import axios from 'axios'
import { ElMessage } from 'element-plus'

const request = axios.create({
  baseURL: '/api',
  timeout: 10000
})

request.interceptors.request.use(
  config =&gt; {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers['Authorization'] = token
    }
    return config
  },
  error =&gt; {
    return Promise.reject(error)
  }
)

request.interceptors.response.use(
  response =&gt; {
    const res = response.data
    if (res.code !== 200) {
      ElMessage.error(res.message || '请求失败')
      return Promise.reject(new Error(res.message || '请求失败'))
    }
    return res
  },
  error =&gt; {
    ElMessage.error(error.message || '网络错误')
    return Promise.reject(error)
  }
)

export default request
