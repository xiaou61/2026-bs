import request from '@/utils/request'

export const authApi = {
  login: (data) => request.post('/auth/login', data),
  register: (data) => request.post('/auth/register', data)
}

export const userApi = {
  getInfo: () => request.get('/user/info'),
  updateInfo: (data) => request.put('/user/info', data),
  changePassword: (oldPassword, newPassword) => 
    request.put(`/user/password?oldPassword=${oldPassword}&newPassword=${newPassword}`),
  getMySongs: (params) => request.get('/user/songs', { params }),
  getMyFavorites: (params) => request.get('/user/favorites', { params })
}

export const categoryApi = {
  getAll: () => request.get('/categories'),
  getById: (id) => request.get(`/categories/${id}`),
  getByRegion: (region) => request.get(`/categories/region/${region}`)
}

export const songApi = {
  getList: (params) => request.get('/songs', { params }),
  getByCategory: (categoryId, params) => request.get(`/songs/category/${categoryId}`, { params }),
  search: (params) => request.get('/songs/search', { params }),
  getHot: (limit = 10) => request.get('/songs/hot', { params: { limit } }),
  getLatest: (limit = 10) => request.get('/songs/latest', { params: { limit } }),
  getDetail: (id) => request.get(`/songs/${id}`),
  create: (data) => request.post('/songs', data),
  update: (data) => request.put('/songs', data),
  delete: (id) => request.delete(`/songs/${id}`),
  like: (id) => request.post(`/songs/${id}/like`),
  unlike: (id) => request.delete(`/songs/${id}/like`),
  collect: (id) => request.post(`/songs/${id}/collect`),
  uncollect: (id) => request.delete(`/songs/${id}/collect`)
}

export const commentApi = {
  getBySongId: (songId, params) => request.get(`/comments/song/${songId}`, { params }),
  create: (data) => request.post('/comments', data),
  delete: (id) => request.delete(`/comments/${id}`)
}

export const announcementApi = {
  getList: (params) => request.get('/announcements', { params }),
  getLatest: (limit = 5) => request.get('/announcements/latest', { params: { limit } }),
  getById: (id) => request.get(`/announcements/${id}`)
}

export const fileApi = {
  uploadImage: (file) => {
    const formData = new FormData()
    formData.append('file', file)
    return request.post('/file/upload/image', formData, {
      headers: { 'Content-Type': 'multipart/form-data' }
    })
  },
  uploadAudio: (file) => {
    const formData = new FormData()
    formData.append('file', file)
    return request.post('/file/upload/audio', formData, {
      headers: { 'Content-Type': 'multipart/form-data' }
    })
  },
  uploadVideo: (file) => {
    const formData = new FormData()
    formData.append('file', file)
    return request.post('/file/upload/video', formData, {
      headers: { 'Content-Type': 'multipart/form-data' }
    })
  }
}

export const adminApi = {
  getUsers: (params) => request.get('/admin/users', { params }),
  updateUserStatus: (id, status) => request.put(`/admin/users/${id}/status?status=${status}`),
  deleteUser: (id) => request.delete(`/admin/users/${id}`),
  
  getCategories: () => request.get('/admin/categories'),
  createCategory: (data) => request.post('/admin/categories', data),
  updateCategory: (data) => request.put('/admin/categories', data),
  deleteCategory: (id) => request.delete(`/admin/categories/${id}`),
  
  getSongs: (params) => request.get('/admin/songs', { params }),
  getPendingSongs: (params) => request.get('/admin/songs/pending', { params }),
  auditSong: (id, auditStatus) => request.put(`/admin/songs/${id}/audit?auditStatus=${auditStatus}`),
  
  getComments: (params) => request.get('/admin/comments', { params }),
  deleteComment: (id) => request.delete(`/admin/comments/${id}`),
  
  getAnnouncements: (params) => request.get('/admin/announcements', { params }),
  createAnnouncement: (data) => request.post('/admin/announcements', data),
  updateAnnouncement: (data) => request.put('/admin/announcements', data),
  deleteAnnouncement: (id) => request.delete(`/admin/announcements/${id}`)
}
