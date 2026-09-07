import axios from 'axios'
import { ElMessage } from 'element-plus'
import router from '../router'

const request = axios.create({ baseURL: '/api/admin', timeout: 10000 })

request.interceptors.request.use(config => {
  const token = localStorage.getItem('admin_token')
  if (token) config.headers.Authorization = 'Bearer ' + token
  return config
})

request.interceptors.response.use(
  res => {
    if (res.data.code === 200) return res.data.data
    ElMessage.error(res.data.message || '请求失败')
    return Promise.reject(res.data)
  },
  err => {
    if (err.response?.status === 401) {
      localStorage.removeItem('admin_token')
      router.push('/login')
    }
    ElMessage.error('网络错误')
    return Promise.reject(err)
  }
)

export default {
  login: data => request.post('/login', data),
  getStats: () => request.get('/stats'),
  getUserList: params => request.get('/user/list', { params }),
  updateUserStatus: (id, status) => request.put(`/user/status/${id}`, null, { params: { status } }),
  getCategoryList: params => request.get('/category/list', { params }),
  saveCategory: data => request.post('/category/save', data),
  deleteCategory: id => request.delete(`/category/${id}`),
  getArticleList: params => request.get('/article/list', { params }),
  saveArticle: data => request.post('/article/save', data),
  deleteArticle: id => request.delete(`/article/${id}`),
  getQuestionList: params => request.get('/question/list', { params }),
  saveQuestion: data => request.post('/question/save', data),
  deleteQuestion: id => request.delete(`/question/${id}`),
  getNewsList: params => request.get('/news/list', { params }),
  saveNews: data => request.post('/news/save', data),
  deleteNews: id => request.delete(`/news/${id}`),
  getQaList: params => request.get('/qa/list', { params }),
  updateQaStatus: (id, status) => request.put(`/qa/status/${id}`, null, { params: { status } })
}
