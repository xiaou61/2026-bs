// app.js
App({
  globalData: {
    baseUrl: 'http://localhost:8037/api',
    token: '',
    userInfo: null,
    systemInfo: null
  },

  onLaunch() {
    // 获取系统信息
    this.globalData.systemInfo = wx.getSystemInfoSync();
    
    // 检查登录状态
    this.checkLoginStatus();
    
    // 打印启动信息
    console.log('编程学习交流平台启动成功');
    console.log('系统信息:', this.globalData.systemInfo);
  },

  /**
   * 检查登录状态
   */
  checkLoginStatus() {
    const token = wx.getStorageSync('token');
    const userInfo = wx.getStorageSync('userInfo');
    
    if (token && userInfo) {
      this.globalData.token = token;
      this.globalData.userInfo = userInfo;
      console.log('用户已登录:', userInfo);
    } else {
      console.log('用户未登录');
    }
  },

  /**
   * 设置登录信息
   */
  setLoginInfo(token, userInfo) {
    this.globalData.token = token;
    this.globalData.userInfo = userInfo;
    
    wx.setStorageSync('token', token);
    wx.setStorageSync('userInfo', userInfo);
  },

  /**
   * 清除登录信息
   */
  clearLoginInfo() {
    this.globalData.token = '';
    this.globalData.userInfo = null;
    
    wx.removeStorageSync('token');
    wx.removeStorageSync('userInfo');
  },

  /**
   * 检查是否已登录
   */
  isLogin() {
    return !!this.globalData.token;
  },

  /**
   * 跳转到登录页
   */
  navigateToLogin() {
    wx.navigateTo({
      url: '/pages/login/login'
    });
  }
});
