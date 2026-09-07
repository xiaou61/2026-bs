import request from '@/utils/request'

// 认证
export const login = (data: { username: string; password: string }) => request.post('/auth/login', data)
export const register = (data: any) => request.post('/auth/register', data)

// 垃圾分类
export const getCategoryList = () => request.get('/category/list')
export const getCategoryDetail = (id: number) => request.get(`/category/${id}`)

// 回收订单
export const createOrder = (data: any) => request.post('/order', data)
export const getMyOrders = (params: any) => request.get('/order/my', { params })
export const getOrderDetail = (id: number) => request.get(`/order/${id}`)
export const cancelOrder = (id: number, reason?: string) => request.post(`/order/${id}/cancel`, null, { params: { reason } })

// 回收员
export const getCollectorOrders = (params: any) => request.get('/collector/orders', { params })
export const acceptOrder = (orderId: number) => request.post(`/collector/accept/${orderId}`)
export const completeOrder = (orderId: number, details: any[]) => request.post(`/collector/complete/${orderId}`, details)

// 积分
export const getMyPoints = () => request.get('/points/my')
export const getPointsRecords = (params: any) => request.get('/points/records', { params })
export const getPointsProducts = (params: any) => request.get('/points/products', { params })
export const exchangeProduct = (productId: number, quantity?: number) => 
  request.post('/points/exchange', null, { params: { productId, quantity } })
export const getExchangeRecords = (params: any) => request.get('/points/exchange/records', { params })

// 环保知识
export const getKnowledgeList = (params: any) => request.get('/knowledge/list', { params })
export const getKnowledgeDetail = (id: number) => request.get(`/knowledge/${id}`)

// 公告
export const getNoticeList = (params: any) => request.get('/notice/list', { params })
export const getNoticeDetail = (id: number) => request.get(`/notice/${id}`)

// 管理员
export const getStatistics = () => request.get('/admin/statistics')
export const getAdminOrders = (params: any) => request.get('/admin/orders', { params })
export const getAdminUsers = (params: any) => request.get('/admin/users', { params })
export const addUser = (data: any) => request.post('/admin/user', data)
export const getCommunities = (params: any) => request.get('/admin/communities', { params })
export const addCommunity = (data: any) => request.post('/admin/community', data)
export const getAdminCategories = (params: any) => request.get('/admin/categories', { params })
export const saveCategory = (data: any) => request.post('/admin/category', data)
export const getAdminProducts = (params: any) => request.get('/admin/products', { params })
export const saveProduct = (data: any) => request.post('/admin/product', data)
export const getAdminExchanges = (params: any) => request.get('/admin/exchanges', { params })
export const deliverExchange = (id: number) => request.post(`/admin/exchange/${id}/deliver`)
export const publishKnowledge = (data: any) => request.post('/admin/knowledge', data)
export const publishNotice = (data: any) => request.post('/admin/notice', data)
