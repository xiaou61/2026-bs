import request from './request'

export const login = (data: { username: string; password: string }) => request.post('/auth/login', data)
export const register = (data: { username: string; password: string; realName: string; phone: string; email?: string }) => request.post('/auth/register', data)
export const fetchDashboard = (date?: string) => request.get('/stats/dashboard', { params: { date } })
export const fetchList = (path: string, params?: any) => request.get(path, { params })
export const postData = (path: string, data: any) => request.post(path, data)
export const putData = (path: string, data: any) => request.put(path, data)
export const deleteData = (path: string) => request.delete(path)
