import request from './request'

export const login = (data) => request.post('/api/login', data)
export const logout = () => request.post('/api/logout')
export const getUserInfo = () => request.get('/api/user/info')

export const getUserPage = (params) => request.get('/api/user/page', { params })
export const addUser = (data) => request.post('/api/user', data)
export const updateUser = (data) => request.put('/api/user', data)
export const deleteUser = (id) => request.delete(`/api/user/${id}`)

export const getEquipmentCategoryPage = (params) => request.get('/api/equipmentCategory/page', { params })
export const getEquipmentCategoryList = () => request.get('/api/equipmentCategory/list')
export const addEquipmentCategory = (data) => request.post('/api/equipmentCategory', data)
export const updateEquipmentCategory = (data) => request.put('/api/equipmentCategory', data)
export const deleteEquipmentCategory = (id) => request.delete(`/api/equipmentCategory/${id}`)

export const getEquipmentPage = (params) => request.get('/api/equipment/page', { params })
export const addEquipment = (data) => request.post('/api/equipment', data)
export const updateEquipment = (data) => request.put('/api/equipment', data)
export const deleteEquipment = (id) => request.delete(`/api/equipment/${id}`)
export const updateEquipmentStatus = (data) => request.put('/api/equipment/status', data)

export const getSensorDataPage = (params) => request.get('/api/sensorData/page', { params })
export const addSensorData = (data) => request.post('/api/sensorData', data)
export const getSensorLatest = (equipmentId) => request.get(`/api/sensorData/latest/${equipmentId}`)
export const getSensorTrend = (equipmentId, params) => request.get(`/api/sensorData/trend/${equipmentId}`, { params })

export const getAlertPage = (params) => request.get('/api/alert/page', { params })
export const handleAlert = (id) => request.put(`/api/alert/handle/${id}`)
export const getAlertStats = () => request.get('/api/alert/stats')

export const getProductPage = (params) => request.get('/api/product/page', { params })
export const getProductList = () => request.get('/api/product/list')
export const addProduct = (data) => request.post('/api/product', data)
export const updateProduct = (data) => request.put('/api/product', data)
export const deleteProduct = (id) => request.delete(`/api/product/${id}`)

export const getProductionOrderPage = (params) => request.get('/api/productionOrder/page', { params })
export const addProductionOrder = (data) => request.post('/api/productionOrder', data)
export const updateProductionOrder = (data) => request.put('/api/productionOrder', data)
export const deleteProductionOrder = (id) => request.delete(`/api/productionOrder/${id}`)
export const updateProductionOrderStatus = (data) => request.put('/api/productionOrder/status', data)

export const getMaterialPage = (params) => request.get('/api/material/page', { params })
export const addMaterial = (data) => request.post('/api/material', data)
export const updateMaterial = (data) => request.put('/api/material', data)
export const deleteMaterial = (id) => request.delete(`/api/material/${id}`)

export const getMaterialStockPage = (params) => request.get('/api/materialStock/page', { params })
export const materialStockIn = (data) => request.post('/api/materialStock/in', data)
export const materialStockOut = (data) => request.post('/api/materialStock/out', data)

export const getQualityInspectionPage = (params) => request.get('/api/qualityInspection/page', { params })
export const addQualityInspection = (data) => request.post('/api/qualityInspection', data)
export const updateQualityInspection = (data) => request.put('/api/qualityInspection', data)
export const deleteQualityInspection = (id) => request.delete(`/api/qualityInspection/${id}`)

export const getMaintenancePlanPage = (params) => request.get('/api/maintenancePlan/page', { params })
export const addMaintenancePlan = (data) => request.post('/api/maintenancePlan', data)
export const updateMaintenancePlan = (data) => request.put('/api/maintenancePlan', data)
export const deleteMaintenancePlan = (id) => request.delete(`/api/maintenancePlan/${id}`)

export const getMaintenanceRecordPage = (params) => request.get('/api/maintenanceRecord/page', { params })
export const addMaintenanceRecord = (data) => request.post('/api/maintenanceRecord', data)
export const updateMaintenanceRecordStatus = (data) => request.put('/api/maintenanceRecord/status', data)

export const getDashboardEquipmentStatus = () => request.get('/api/dashboard/equipmentStatus')
export const getDashboardOrderStats = () => request.get('/api/dashboard/orderStats')
export const getDashboardAlertStats = () => request.get('/api/dashboard/alertStats')
export const getDashboardQualityTrend = () => request.get('/api/dashboard/qualityTrend')
export const getDashboardOverview = () => request.get('/api/dashboard/overview')
