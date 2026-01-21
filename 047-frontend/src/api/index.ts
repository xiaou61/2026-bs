import axios from 'axios'

const request = axios.create({
  baseURL: 'http://localhost:8080/api',
  timeout: 10000
})

request.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) config.headers.Authorization = `Bearer ${token}`
  return config
})

request.interceptors.response.use(
  res => res.data,
  err => {
    if (err.response?.status === 401) {
      localStorage.removeItem('token')
      window.location.href = '/login'
    }
    return Promise.reject(err)
  }
)

// Auth
export const login = (data: any) => request.post('/auth/login', data)
export const register = (data: any) => request.post('/auth/register', data)

// Script
export const getScripts = (params: any) => request.get('/script/list', { params })
export const getScriptDetail = (id: number) => request.get(`/script/${id}`)
export const getScriptContent = (id: number) => request.get(`/script/${id}/content`)
export const getCategories = () => request.get('/script/categories')
export const likeScript = (id: number) => request.post(`/script/${id}/like`)

// Shop
export const getShops = (params: any) => request.get('/shop/list', { params })
export const getShopDetail = (id: number) => request.get(`/shop/${id}`)
export const getShopRooms = (id: number) => request.get(`/shop/${id}/rooms`)
export const getShopScripts = (id: number) => request.get(`/shop/${id}/scripts`)

// Reservation
export const createReservation = (data: any) => request.post('/reservation', data)
export const getMyReservations = (params: any) => request.get('/reservation/my', { params })
export const getReservationDetail = (id: number) => request.get(`/reservation/${id}`)
export const cancelReservation = (id: number) => request.put(`/reservation/${id}/cancel`)

// User
export const getUserInfo = () => request.get('/user/info')
export const getFavorites = (type?: number) => request.get('/user/favorites', { params: { type } })
export const addFavorite = (data: any) => request.post('/user/favorite', data)
export const removeFavorite = (id: number) => request.delete(`/user/favorite/${id}`)
export const addReview = (data: any) => request.post('/user/review', data)
export const getMyReviews = () => request.get('/user/reviews')

// Author
export const getMyScripts = (params: any) => request.get('/author/scripts', { params })
export const createScript = (data: any) => request.post('/author/script', data)
export const updateScript = (data: any) => request.put('/author/script', data)
export const getAuthorStats = () => request.get('/author/statistics')

// Shop Owner
export const getMyShop = () => request.get('/owner/shop')
export const saveMyShop = (data: any) => request.post('/owner/shop', data)
export const getMyRooms = () => request.get('/owner/rooms')
export const saveRoom = (data: any) => request.post('/owner/room', data)
export const getOwnerScripts = () => request.get('/owner/scripts')
export const addShopScript = (data: any) => request.post('/owner/script', data)
export const getShopReservations = (params: any) => request.get('/owner/reservations', { params })
export const confirmReservation = (id: number) => request.put(`/owner/reservation/${id}/confirm`)
export const completeReservation = (id: number) => request.put(`/owner/reservation/${id}/complete`)

// Review
export const getShopReviews = (shopId: number, params: any) => request.get(`/review/shop/${shopId}`, { params })
export const getScriptReviews = (scriptId: number, params: any) => request.get(`/review/script/${scriptId}`, { params })

// Notice
export const getNotices = (params: any) => request.get('/notice/list', { params })
export const getNoticeDetail = (id: number) => request.get(`/notice/${id}`)

// Admin
export const getStatistics = () => request.get('/admin/statistics')
export const getAdminUsers = (params: any) => request.get('/admin/users', { params })
export const updateUserStatus = (id: number, status: number) => request.put(`/admin/user/${id}/status`, null, { params: { status } })
export const getAdminShops = (params: any) => request.get('/admin/shops', { params })
export const updateShopStatus = (id: number, status: number) => request.put(`/admin/shop/${id}/status`, null, { params: { status } })
export const getAdminScripts = (params: any) => request.get('/admin/scripts', { params })
export const auditScript = (id: number, status: number, remark?: string) => request.put(`/admin/script/${id}/audit`, null, { params: { status, remark } })
export const getAdminCategories = () => request.get('/admin/categories')
export const saveCategory = (data: any) => request.post('/admin/category', data)
export const getAdminNotices = (params: any) => request.get('/admin/notices', { params })
export const saveNotice = (data: any) => request.post('/admin/notice', data)
export const deleteNotice = (id: number) => request.delete(`/admin/notice/${id}`)
