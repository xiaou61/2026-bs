const app = getApp();

Page({
  data: {
    userInfo: null
  },

  onShow() {
    this.setData({ userInfo: app.globalData.userInfo });
  },

  goLogin() {
    wx.navigateTo({ url: '/pages/login/login' });
  },

  logout() {
    app.clearLoginInfo();
    this.setData({ userInfo: null });
    wx.showToast({ title: '已退出', icon: 'success' });
  }
});
