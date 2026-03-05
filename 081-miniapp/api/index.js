import request from '../utils/request'

const BASE_URL = 'http://localhost:8080'

export const login = (data) => request({ url: '/api/user/login', method: 'POST', data })
export const register = (data) => request({ url: '/api/user/register', method: 'POST', data })
export const getUserInfo = () => request({ url: '/api/user/info' })

export const getDashboard = () => request({ url: '/api/statistics/dashboard' })

export const getCategoryList = () => request({ url: '/api/category/public/list' })

export const createOrder = (data) => request({ url: '/api/order/user/create', method: 'POST', data })
export const getMyOrders = (params) => request({ url: '/api/order/user/list', data: params })
export const getOrderDetail = (id) => request({ url: `/api/order/user/detail/${id}` })
export const cancelOrder = (id) => request({ url: `/api/order/user/cancel/${id}`, method: 'PUT' })
export const payOrder = (id, data) => request({ url: `/api/order/user/pay/${id}`, method: 'PUT', data })
export const getOrderProcess = (orderId) => request({ url: `/api/process/list/${orderId}` })
export const getTechnicianOrders = (params) => request({ url: '/api/order/technician/list', data: params })
export const technicianUpdateOrderStatus = (data) => request({ url: '/api/order/technician/status', method: 'PUT', data })
export const technicianAddProcess = (data) => request({ url: '/api/process/technician/add', method: 'POST', data })

export const getNoticeList = (params) => request({ url: '/api/notice/public/list', data: params })
export const getNoticeDetail = (id) => request({ url: `/api/notice/public/detail/${id}` })

export const submitEvaluate = (data) => request({ url: '/api/evaluate/user/submit', method: 'POST', data })
export const getMyEvaluate = (params) => request({ url: '/api/evaluate/user/my', data: params })

export const uploadImage = (filePath) => {
  return new Promise((resolve, reject) => {
    const token = uni.getStorageSync('token')
    uni.uploadFile({
      url: `${BASE_URL}/api/common/upload`,
      filePath,
      name: 'file',
      header: {
        Authorization: token || ''
      },
      success: (res) => {
        try {
          const data = JSON.parse(res.data)
          if (data.code === 200) {
            resolve(data.data.url)
          } else {
            uni.showToast({ title: data.message || '上传失败', icon: 'none' })
            reject(data)
          }
        } catch (error) {
          uni.showToast({ title: '上传失败', icon: 'none' })
          reject(error)
        }
      },
      fail: (err) => {
        uni.showToast({ title: '上传失败', icon: 'none' })
        reject(err)
      }
    })
  })
}
