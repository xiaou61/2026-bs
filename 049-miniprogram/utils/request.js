const app = getApp()

const request = (url, method = 'GET', data = {}) => {
  return new Promise((resolve, reject) => {
    wx.request({
      url: app.globalData.baseUrl + url,
      method,
      data,
      header: {
        'Content-Type': 'application/json',
        'Authorization': app.globalData.token ? `Bearer ${app.globalData.token}` : ''
      },
      success: res => {
        const body = res.data || {}
        if (res.statusCode === 401 || res.statusCode === 403 || body.code === 401) {
          app.logout()
          wx.navigateTo({ url: '/pages/login/login' })
          reject(body)
        } else if (body.code === 200) {
          resolve(body)
        } else {
          wx.showToast({ title: body.message || '请求失败', icon: 'none' })
          reject(body)
        }
      },
      fail: err => {
        wx.showToast({ title: '网络错误', icon: 'none' })
        reject(err)
      }
    })
  })
}

module.exports = {
  get: (url, data) => request(url, 'GET', data),
  post: (url, data) => request(url, 'POST', data),
  put: (url, data) => request(url, 'PUT', data),
  del: (url, data) => request(url, 'DELETE', data)
}
