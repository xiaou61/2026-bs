import axios from 'axios'
import { ElMessage } from 'element-plus'

const request = axios.create({ baseURL: '', timeout: 15000 })

request.interceptors.request.use((config) => {
  const token = localStorage.getItem('service_token')
  if (token) config.headers.Authorization = token
  return config
})

request.interceptors.response.use(
  (response) => {
    const res = response.data
    if (res.code !== 200) {
      ElMessage.error(res.message || '请求失败')
      if (res.code === 401) {
        localStorage.removeItem('service_token')
        localStorage.removeItem('service_user')
        location.href = '/login'
      }
      return Promise.reject(res)
    }
    return res
  },
  (error) => {
    ElMessage.error(error.message || '网络异常')
    return Promise.reject(error)
  }
)

export default request
