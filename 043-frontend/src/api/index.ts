import request from './request'

// 认证相关
export const login = (data: { username: string; password: string }) => 
  request.post('/auth/login', data)

export const register = (data: any) => 
  request.post('/auth/register', data)

// 宠物相关
export const getPetList = () => request.get('/pet/list')
export const getPetPage = (params: { current: number; size: number }) => 
  request.get('/pet/page', { params })
export const addPet = (data: any) => request.post('/pet', data)
export const updatePet = (data: any) => request.put('/pet', data)
export const deletePet = (id: number) => request.delete(`/pet/${id}`)

// 服务商相关
export const getProviderPage = (params: { current: number; size: number; keyword?: string }) =>
  request.get('/provider/page', { params })
export const getProviderDetail = (id: number) => request.get(`/provider/${id}`)
export const getProviderServices = (id: number) => request.get(`/provider/${id}/services`)

// 预约相关
export const createBooking = (data: any) => request.post('/booking', data)
export const cancelBooking = (id: number) => request.post(`/booking/${id}/cancel`)
export const getBookingPage = (params: { current: number; size: number; status?: number }) =>
  request.get('/booking/page', { params })
export const getBookingDetail = (id: number) => request.get(`/booking/${id}`)
