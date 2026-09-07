import request from './request'

const asPage = (res) => {
  if (Array.isArray(res.data)) {
    res.data = { records: res.data, total: res.data.length }
  }
  return res
}

const asList = (res) => {
  if (res.data && Array.isArray(res.data.records)) {
    res.data = res.data.records
  }
  return res
}

const withDisasterAliases = (item) => ({
  ...item,
  name: item.name || item.title,
  location: item.location || item.address,
  happenTime: item.happenTime || item.reportTime || item.createTime
})

const mapDisasterPage = (res) => {
  if (res.data?.records) {
    res.data.records = res.data.records.map(withDisasterAliases)
  }
  return res
}

const mapDisasterList = (res) => {
  if (Array.isArray(res.data)) {
    res.data = res.data.map(withDisasterAliases)
  }
  return res
}

const mapDisasterToBackend = (data) => ({
  ...data,
  title: data.title || data.name,
  address: data.address || data.location,
  reportTime: data.reportTime || data.happenTime
})

const mapTaskToBackend = (data) => ({
  ...data,
  content: data.content || data.description
})

const mapDispatchToBackend = (data) => ({
  disasterId: data.disasterId,
  fromWarehouseId: data.fromWarehouseId || data.warehouseId,
  toAddress: data.toAddress || '待确认灾区',
  priority: data.priority || 1,
  remark: data.remark || '',
  items: data.items && data.items.length ? data.items : [{ materialId: 1, quantity: 1 }]
})

const stockPayload = (data) => ({
  warehouseId: data.warehouseId,
  materialId: data.materialId,
  quantity: data.quantity,
  source: data.source || '前端入库',
  remark: data.remark || ''
})

export const login = data => request.post('/user/login', data)
export const getUserInfo = () => request.get('/user/info')
export const updatePassword = data => request.put('/user/password', data)
export const getUserList = params => request.get('/user/list', { params: { ...params, keyword: params.username } })
export const addUser = data => request.post('/user/add', data)
export const updateUser = data => request.put('/user/update', data)
export const deleteUser = id => request.delete(`/user/${id}`)

export const getDisasterList = params => request.get('/disaster/list', { params: { ...params, keyword: params.name } }).then(mapDisasterPage)
export const addDisaster = data => request.post('/disaster/report', mapDisasterToBackend(data))
export const updateDisaster = data => request.put(`/disaster/${data.id}`, mapDisasterToBackend(data))
export const deleteDisaster = id => request.delete(`/disaster/${id}`)
export const getDisasterSelect = () => request.get('/disaster/all').then(mapDisasterList)

export const getCategoryList = () => request.get('/category/all').then(asPage)
export const addCategory = data => request.post('/category/add', data)
export const updateCategory = data => request.put('/category/update', data)
export const deleteCategory = id => request.delete(`/category/${id}`)
export const getCategorySelect = () => request.get('/category/list')

export const getMaterialList = params => request.get('/material/list', { params: { ...params, keyword: params.name } })
export const addMaterial = data => request.post('/material/add', data)
export const updateMaterial = data => request.put('/material/update', data)
export const deleteMaterial = id => request.delete(`/material/${id}`)
export const getMaterialSelect = () => request.get('/material/all')

export const getWarehouseList = params => request.get('/warehouse/list', { params: { ...params, keyword: params.name } })
export const addWarehouse = data => request.post('/warehouse/add', data)
export const updateWarehouse = data => request.put('/warehouse/update', data)
export const deleteWarehouse = id => request.delete(`/warehouse/${id}`)
export const getWarehouseSelect = () => request.get('/warehouse/all')

export const getStockList = params => request.get('/stock/list', { params })
export const addStock = data => request.post('/stock/in', stockPayload(data))
export const updateStock = data => request.post('/stock/in', stockPayload(data))
export const getStockRecords = params => request.get('/stock/record', { params })

export const getDispatchList = params => request.get('/dispatch/list', { params })
export const addDispatch = data => request.post('/dispatch/create', mapDispatchToBackend(data))
export const updateDispatch = data => request.put(`/dispatch/${data.id}/status`, { status: data.status || 0 })
export const deleteDispatch = id => request.delete(`/dispatch/${id}`)
export const updateDispatchStatus = (id, status) => request.put(`/dispatch/${id}/status`, { status })

export const getTaskList = params => request.get('/task/list', { params })
export const addTask = data => request.post('/task/create', mapTaskToBackend(data))
export const updateTask = data => request.put('/task/update', mapTaskToBackend(data))
export const deleteTask = id => request.delete(`/task/${id}`)
export const updateTaskStatus = (id, status) => request.put(`/task/${id}/status`, { status })
export const getMyTasks = params => request.get('/task/my', { params })

export const getNoticeList = params => request.get('/notice/list', { params })
export const addNotice = data => request.post('/notice/add', data)
export const updateNotice = data => request.put('/notice/update', data)
export const deleteNotice = id => request.delete(`/notice/${id}`)
export const getPublishedNotices = () => request.get('/notice/published').then(asList)

export const getStats = () => request.get('/stats/overview')
