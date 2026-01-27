import axios from 'axios'
import { ElMessage } from 'element-plus'
import router from '../router'

const request = axios.create({
  baseURL: '/api/admin',
  timeout: 10000
})

request.interceptors.request.use(config => {
  const token = localStorage.getItem('adminToken')
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
      localStorage.removeItem('adminToken')
      router.push('/login')
    }
    ElMessage.error(error.response?.data?.message || '网络错误')
    return Promise.reject(error)
  }
)

export default {
  login: data => request.post('/login', data),
  getStats: () => request.get('/stats'),
  getUserList: params => request.get('/user/list', { params }),
  updateUserStatus: data => request.put('/user/status', data),
  getCategoryList: () => request.get('/category/list'),
  addCategory: data => request.post('/category/add', data),
  updateCategory: data => request.put('/category/update', data),
  deleteCategory: id => request.delete(`/category/${id}`),
  getCourseList: params => request.get('/course/list', { params }),
  addCourse: data => request.post('/course/add', data),
  updateCourse: data => request.put('/course/update', data),
  deleteCourse: id => request.delete(`/course/${id}`),
  updateCourseStatus: data => request.put('/course/status', data),
  getCourseChapters: id => request.get(`/course/${id}/chapters`),
  addChapter: data => request.post('/chapter/add', data),
  updateChapter: data => request.put('/chapter/update', data),
  deleteChapter: id => request.delete(`/chapter/${id}`),
  addVideo: data => request.post('/video/add', data),
  updateVideo: data => request.put('/video/update', data),
  deleteVideo: id => request.delete(`/video/${id}`),
  getCommentList: params => request.get('/comment/list', { params }),
  updateCommentStatus: data => request.put('/comment/status', data),
  deleteComment: id => request.delete(`/comment/${id}`),
  getBannerList: () => request.get('/banner/list'),
  addBanner: data => request.post('/banner/add', data),
  updateBanner: data => request.put('/banner/update', data),
  deleteBanner: id => request.delete(`/banner/${id}`)
}
