import request from '@/utils/request'

// 认证
export const login = (data: { username: string; password: string }) => request.post('/auth/login', data)
export const register = (data: any) => request.post('/auth/register', data)

// 老人
export const getElderList = (params: any) => request.get('/elder/list', { params })
export const getElderDetail = (id: number) => request.get(`/elder/${id}`)
export const elderCheckIn = (data: any) => request.post('/elder/checkin', data)
export const elderCheckOut = (id: number) => request.post(`/elder/${id}/checkout`)
export const updateElder = (id: number, data: any) => request.put(`/elder/${id}`, data)

// 床位
export const getAvailableBeds = () => request.get('/bed/available')
export const getBedStatistics = () => request.get('/bed/statistics')
export const getBuildingList = () => request.get('/bed/building/list')
export const getRoomList = (buildingId?: number) => request.get('/bed/room/list', { params: { buildingId } })
export const getRoomBeds = (roomId: number) => request.get(`/bed/room/${roomId}/beds`)

// 健康
export const getHealthRecords = (elderId: number, params: any) => request.get(`/health/${elderId}`, { params })
export const addHealthRecord = (data: any) => request.post('/health', data)

// 护理
export const getCarePlans = (elderId: number) => request.get(`/care/plan/${elderId}`)
export const addCarePlan = (data: any) => request.post('/care/plan', data)
export const getCareRecords = (params: any) => request.get('/care/record', { params })
export const addCareRecord = (data: any) => request.post('/care/record', data)

// 账单
export const getBillList = (params: any) => request.get('/bill/list', { params })
export const getBillDetail = (id: number) => request.get(`/bill/${id}`)
export const payBill = (billId: number, amount: number, payMethod?: number) => 
  request.post('/bill/pay', null, { params: { billId, amount, payMethod } })

// 探访
export const applyVisit = (data: any) => request.post('/visit/apply', data)
export const getMyVisits = (params: any) => request.get('/visit/my', { params })
export const getVisitList = (params: any) => request.get('/visit/list', { params })
export const approveVisit = (id: number, status: number, remark?: string) =>
  request.post(`/visit/approve/${id}`, null, { params: { status, remark } })

// 公告
export const getNoticeList = (params: any) => request.get('/notice/list', { params })
export const getNoticeDetail = (id: number) => request.get(`/notice/${id}`)
export const publishNotice = (data: any) => request.post('/notice', data)

// 管理员
export const getStatistics = () => request.get('/admin/statistics')
export const getUserList = (params: any) => request.get('/admin/user/list', { params })
export const addUser = (data: any) => request.post('/admin/user', data)
export const updateUser = (id: number, data: any) => request.put(`/admin/user/${id}`, data)
export const getFeeList = (params: any) => request.get('/admin/fee/list', { params })
export const addFeeItem = (data: any) => request.post('/admin/fee', data)
