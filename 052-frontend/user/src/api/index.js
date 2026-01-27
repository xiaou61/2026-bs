import axios from 'axios'
import { ElMessage } from 'element-plus'
import router from '../router'

const request = axios.create({
  baseURL: '/api',
  timeout: 10000
})

request.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

request.interceptors.response.use(
  response => {
    const res = response.data
    if (res.code !== 200) {
      ElMessage.error(res.message || '请求失败')
      return Promise.reject(res)
    }
    return res.data
  },
  error => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token')
      localStorage.removeItem('user')
      router.push('/login')
    }
    ElMessage.error(error.response?.data?.message || '网络错误')
    return Promise.reject(error)
  }
)

export const userApi = {
  login: data => request.post('/user/login', data),
  register: data => request.post('/user/register', data),
  getInfo: () => request.get('/user/info'),
  updateInfo: data => request.put('/user/info', data),
  updatePassword: data => request.put('/user/password', data)
}

export const courseApi = {
  getBanners: () => request.get('/banner/list'),
  getCategories: () => request.get('/category/list'),
  getList: params => request.get('/course/list', { params }),
  getRecommend: () => request.get('/course/recommend'),
  getHot: () => request.get('/course/hot'),
  getLatest: () => request.get('/course/latest'),
  getDetail: id => request.get(`/course/${id}`),
  getChapters: id => request.get(`/course/${id}/chapters`)
}

export const learnApi = {
  startLearn: courseId => request.post('/learn/start', { courseId }),
  getVideoInfo: id => request.get(`/learn/video/${id}`),
  saveProgress: data => request.post('/learn/progress', data),
  getMyCourses: params => request.get('/learn/my-courses', { params }),
  getRecords: params => request.get('/learn/records', { params })
}

export const commentApi = {
  getList: params => request.get('/comment/list', { params }),
  add: data => request.post('/comment/add', data)
}

export const favoriteApi = {
  add: courseId => request.post('/favorite/add', { courseId }),
  remove: courseId => request.delete(`/favorite/${courseId}`),
  getList: params => request.get('/favorite/list', { params }),
  check: courseId => request.get(`/favorite/check/${courseId}`)
}

export default request
