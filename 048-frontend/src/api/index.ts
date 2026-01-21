import axios from 'axios'
import { useUserStore } from '@/stores/user'

const api = axios.create({ baseURL: 'http://localhost:8080/api' })

api.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) config.headers.Authorization = `Bearer ${token}`
  return config
})

api.interceptors.response.use(
  res => res.data,
  err => {
    if (err.response?.status === 401) {
      const userStore = useUserStore()
      userStore.logout()
      window.location.href = '/login'
    }
    return Promise.reject(err)
  }
)

// 认证
export const login = (data: any) => api.post('/auth/login', data)
export const register = (data: any) => api.post('/auth/register', data)

// 基础数据
export const getCategories = () => api.get('/base/categories')
export const getDynasties = () => api.get('/base/dynasties')
export const getHalls = () => api.get('/base/halls')
export const getGuides = () => api.get('/base/guides')

// 文物
export const getRelics = (params: any) => api.get('/relic/list', { params })
export const getRelicDetail = (id: number) => api.get(`/relic/${id}`)
export const getRelicImages = (id: number) => api.get(`/relic/${id}/images`)
export const likeRelic = (id: number) => api.post(`/relic/${id}/like`)

// 展览
export const getExhibitions = (params: any) => api.get('/exhibition/list', { params })
export const getExhibitionDetail = (id: number) => api.get(`/exhibition/${id}`)

// 研究成果
export const getResearches = (params: any) => api.get('/research/list', { params })
export const getResearchDetail = (id: number) => api.get(`/research/${id}`)

// 预约
export const createReservation = (data: any) => api.post('/reservation', data)
export const getMyReservations = (params: any) => api.get('/reservation/my', { params })
export const cancelReservation = (id: number) => api.post(`/reservation/${id}/cancel`)

// 讲解预约
export const createGuideBooking = (data: any) => api.post('/guide-booking', data)
export const getMyGuideBookings = (params: any) => api.get('/guide-booking/my', { params })
export const cancelGuideBooking = (id: number) => api.post(`/guide-booking/${id}/cancel`)

// 用户
export const getUserInfo = () => api.get('/user/info')
export const getFavorites = () => api.get('/user/favorites')
export const addFavorite = (data: any) => api.post('/user/favorite', data)
export const removeFavorite = (relicId: number) => api.delete(`/user/favorite/${relicId}`)

// 公告
export const getNotices = (params: any) => api.get('/notice/list', { params })
export const getNoticeDetail = (id: number) => api.get(`/notice/${id}`)

// 讲解员
export const getGuideBookings = (params: any) => api.get('/guide/bookings', { params })
export const confirmGuideBooking = (id: number) => api.post(`/guide/booking/${id}/confirm`)
export const completeGuideBooking = (id: number) => api.post(`/guide/booking/${id}/complete`)

// 研究员
export const getResearcherResearches = (params: any) => api.get('/researcher/researches', { params })
export const saveResearch = (data: any) => api.post('/researcher/research', data)
export const updateResearch = (data: any) => api.put('/researcher/research', data)

// 管理员
export const getStatistics = () => api.get('/admin/statistics')
export const getUsers = (params: any) => api.get('/admin/users', { params })
export const updateUserStatus = (id: number, status: number) => api.put(`/admin/user/${id}/status?status=${status}`)

export const getAdminRelics = (params: any) => api.get('/admin/relics', { params })
export const saveRelic = (data: any) => api.post('/admin/relic', data)
export const updateRelic = (data: any) => api.put('/admin/relic', data)
export const updateRelicStatus = (id: number, status: number) => api.put(`/admin/relic/${id}/status?status=${status}`)

export const getAdminCategories = () => api.get('/admin/categories')
export const saveCategory = (data: any) => api.post('/admin/category', data)
export const updateCategory = (data: any) => api.put('/admin/category', data)
export const deleteCategory = (id: number) => api.delete(`/admin/category/${id}`)

export const getAdminDynasties = () => api.get('/admin/dynasties')
export const saveDynasty = (data: any) => api.post('/admin/dynasty', data)
export const updateDynasty = (data: any) => api.put('/admin/dynasty', data)

export const getAdminHalls = () => api.get('/admin/halls')
export const saveHall = (data: any) => api.post('/admin/hall', data)
export const updateHall = (data: any) => api.put('/admin/hall', data)

export const getAdminExhibitions = (params: any) => api.get('/admin/exhibitions', { params })
export const saveExhibition = (data: any) => api.post('/admin/exhibition', data)
export const updateExhibition = (data: any) => api.put('/admin/exhibition', data)
export const deleteExhibition = (id: number) => api.delete(`/admin/exhibition/${id}`)

export const getAdminResearches = (params: any) => api.get('/admin/researches', { params })
export const auditResearch = (id: number, status: number) => api.put(`/admin/research/${id}/audit?status=${status}`)

export const getAdminReservations = (params: any) => api.get('/admin/reservations', { params })
export const confirmReservation = (id: number) => api.post(`/admin/reservation/${id}/confirm`)
export const completeReservation = (id: number) => api.post(`/admin/reservation/${id}/complete`)

export const getAdminGuideBookings = (params: any) => api.get('/admin/guide-bookings', { params })

export const getAdminNotices = (params: any) => api.get('/admin/notices', { params })
export const saveNotice = (data: any) => api.post('/admin/notice', data)
export const updateNotice = (data: any) => api.put('/admin/notice', data)
export const deleteNotice = (id: number) => api.delete(`/admin/notice/${id}`)

export default api
