import axios from 'axios'

const hostname = typeof window !== 'undefined' && window.location?.hostname
  ? window.location.hostname
  : '127.0.0.1'

const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || `http://${hostname}:8032`
})

api.interceptors.request.use((cfg) => {
  const token = localStorage.getItem('token')
  if (token) {
    cfg.headers.Authorization = `Bearer ${token}`
  }
  return cfg
})

export default api
