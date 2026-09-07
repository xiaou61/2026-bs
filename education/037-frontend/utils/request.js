// utils/request.js
const app = getApp();

/**
 * HTTP请求封装
 */
function request(options) {
  return new Promise((resolve, reject) => {
    const { url, method = 'GET', data = {}, header = {} } = options;
    
    // 完整URL
    const fullUrl = app.globalData.baseUrl + url;
    
    // 请求头
    const requestHeader = {
      'Content-Type': 'application/json',
      ...header
    };
    
    // 添加Token
    if (app.globalData.token) {
      requestHeader['Authorization'] = 'Bearer ' + app.globalData.token;
    }
    
    // 显示加载提示
    if (options.loading !== false) {
      wx.showLoading({
        title: '加载中...',
        mask: true
      });
    }
    
    // 发起请求
    wx.request({
      url: fullUrl,
      method: method,
      data: data,
      header: requestHeader,
      success: (res) => {
        wx.hideLoading();
        
        // 请求成功
        if (res.statusCode === 200) {
          const result = res.data;
          
          // 业务成功
          if (result.code === 200) {
            resolve(result.data);
          } else {
            // 业务失败
            wx.showToast({
              title: result.message || '请求失败',
              icon: 'none',
              duration: 2000
            });
            reject(result);
          }
        } else if (res.statusCode === 401) {
          // 未登录
          wx.showToast({
            title: '请先登录',
            icon: 'none',
            duration: 2000
          });
          app.clearLoginInfo();
          app.navigateToLogin();
          reject(res);
        } else {
          // 服务器错误
          wx.showToast({
            title: '服务器错误',
            icon: 'none',
            duration: 2000
          });
          reject(res);
        }
      },
      fail: (err) => {
        wx.hideLoading();
        wx.showToast({
          title: '网络请求失败',
          icon: 'none',
          duration: 2000
        });
        reject(err);
      }
    });
  });
}

/**
 * GET请求
 */
function get(url, data, options = {}) {
  return request({
    url,
    method: 'GET',
    data,
    ...options
  });
}

/**
 * POST请求
 */
function post(url, data, options = {}) {
  return request({
    url,
    method: 'POST',
    data,
    ...options
  });
}

/**
 * PUT请求
 */
function put(url, data, options = {}) {
  return request({
    url,
    method: 'PUT',
    data,
    ...options
  });
}

/**
 * DELETE请求
 */
function del(url, data, options = {}) {
  return request({
    url,
    method: 'DELETE',
    data,
    ...options
  });
}

module.exports = {
  request,
  get,
  post,
  put,
  del
};
