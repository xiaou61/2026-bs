Page({
  data: {
    checked: false
  },

  doCheckin() {
    this.setData({ checked: true });
    wx.showToast({ title: '打卡成功', icon: 'success' });
  }
});
