import request from './request'

export const login = (data) => request.post('/auth/login', data)
export const register = (data) => request.post('/auth/register', data)
export const logout = () => request.post('/auth/logout')
export const getUserInfo = () => request.get('/auth/info')

export const getCategoryList = (params) => request.get('/category/list', { params })
export const getCategoryEnabled = () => request.get('/category/enabled')
export const addCategory = (data) => request.post('/category/add', data)
export const updateCategory = (data) => request.put('/category/update', data)
export const deleteCategory = (id) => request.delete(`/category/delete/${id}`)

export const getTagList = (params) => request.get('/tag/list', { params })
export const getTagEnabled = () => request.get('/tag/enabled')
export const addTag = (data) => request.post('/tag/add', data)
export const updateTag = (data) => request.put('/tag/update', data)
export const deleteTag = (id) => request.delete(`/tag/delete/${id}`)

export const getWallpaperList = (params) => request.get('/wallpaper/list', { params })
export const getPublicWallpaperList = (params) => request.get('/wallpaper/public/list', { params })
export const getWallpaperDetail = (id) => request.get(`/wallpaper/detail/${id}`)
export const getRecommendWallpapers = (params) => request.get('/wallpaper/recommend', { params })
export const getMyWallpaperList = (params) => request.get('/wallpaper/my/list', { params })
export const addWallpaper = (data) => request.post('/wallpaper/add', data)
export const updateWallpaper = (data) => request.put('/wallpaper/update', data)
export const publishWallpaper = (id, publishStatus) => request.put('/wallpaper/publish', null, { params: { id, publishStatus } })
export const deleteWallpaper = (id) => request.delete(`/wallpaper/delete/${id}`)
export const downloadWallpaper = (id) => request.post(`/wallpaper/download/${id}`)

export const getFavoriteList = () => request.get('/favorite/list')
export const addFavorite = (wallpaperId) => request.post(`/favorite/add/${wallpaperId}`)
export const cancelFavorite = (wallpaperId) => request.delete(`/favorite/cancel/${wallpaperId}`)

export const getAuditList = (params) => request.get('/audit/list', { params })
export const submitAudit = (data) => request.put('/audit/submit', data)
export const getAuditRecord = (wallpaperId) => request.get(`/audit/record/${wallpaperId}`)

export const getBannerList = (params) => request.get('/banner/list', { params })
export const getBannerEnabled = () => request.get('/banner/enabled')
export const addBanner = (data) => request.post('/banner/add', data)
export const updateBanner = (data) => request.put('/banner/update', data)
export const deleteBanner = (id) => request.delete(`/banner/delete/${id}`)

export const getNoticeList = (params) => request.get('/notice/list', { params })
export const getPublicNoticeList = () => request.get('/notice/public/list')
export const addNotice = (data) => request.post('/notice/add', data)
export const updateNotice = (data) => request.put('/notice/update', data)
export const deleteNotice = (id) => request.delete(`/notice/delete/${id}`)

export const getDashboard = () => request.get('/statistics/dashboard')

export const uploadImage = (file) => {
  const formData = new FormData()
  formData.append('file', file)
  return request.post('/upload/image', formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}
