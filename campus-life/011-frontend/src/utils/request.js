import axios from 'axios'
import { ElMessage } from 'element-plus'
import router from '@/router'

const legacyMediaPattern = /^https?:\/\/(?:localhost|127\.0\.0\.1):8080\/api\/upload\/(videos|covers|avatars)\/(.+)$/
const legacyRelativeMediaPattern = /^\/api\/upload\/(videos|covers|avatars)\/(.+)$/
const legacyBlobPattern = /^blob:https?:\/\/(?:localhost|127\.0\.0\.1):3000\//i

const normalizeMediaValue = (value) => {
  if (typeof value !== 'string') {
    return value
  }

  if (legacyBlobPattern.test(value)) {
    return ''
  }

  const legacyMatch = value.match(legacyMediaPattern) || value.match(legacyRelativeMediaPattern)
  if (legacyMatch) {
    return `/${legacyMatch[1]}/${legacyMatch[2]}`
  }

  return value
}

const normalizeResponseData = (data) => {
  if (Array.isArray(data)) {
    return data.map(item => normalizeResponseData(item))
  }

  if (data && typeof data === 'object') {
    const normalized = {}
    Object.keys(data).forEach(key => {
      const value = data[key]
      if (value && typeof value === 'object') {
        normalized[key] = normalizeResponseData(value)
      } else {
        normalized[key] = normalizeMediaValue(value)
      }
    })
    return normalized
  }

  return normalizeMediaValue(data)
}

const request = axios.create({
  baseURL: '/api',
  timeout: 30000
})

request.interceptors.request.use(
  config => {
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

request.interceptors.response.use(
  response => {
    const res = response.data
    if (res.code === 200) {
      res.data = normalizeResponseData(res.data)
      return res
    } else {
      ElMessage.error(res.message || '请求失败')
      return Promise.reject(new Error(res.message || '请求失败'))
    }
  },
  error => {
    if (error.response && error.response.status === 401) {
      ElMessage.error('未登录或登录已过期')
      localStorage.removeItem('token')
      router.push('/login')
    } else {
      ElMessage.error(error.message || '网络错误')
    }
    return Promise.reject(error)
  }
)

export default request

