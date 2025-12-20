import axios from 'axios'
import { useUserStore } from '../stores/user'

const instance = axios.create({
  baseURL: '/api'
})

instance.interceptors.request.use((config) => {
  const store = useUserStore()
  if (store.token) {
    config.headers.Authorization = `Bearer ${store.token}`
  }
  return config
})

instance.interceptors.response.use(
  (resp) => resp,
  (error) => {
    if (error.response && error.response.status === 401) {
      const store = useUserStore()
      store.clear()
      window.location.href = '/login'
    }
    return Promise.reject(error)
  }
)

export default instance
