const { get } = require('../../utils/request')

Page({
  data: { notices: [], noticeDetail: null },
  onLoad(options) {
    if (options.id) {
      this.loadDetail(options.id)
    } else {
      this.loadNotices()
    }
  },
  async loadNotices() {
    try {
      const res = await get('/notice/list', { size: 20 })
      this.setData({ notices: res.data.records || [] })
    } catch (e) {}
  },
  async loadDetail(id) {
    try {
      const res = await get(`/notice/${id}`)
      this.setData({ noticeDetail: res.data })
      wx.setNavigationBarTitle({ title: res.data.title })
    } catch (e) {}
  },
  goToDetail(e) {
    wx.navigateTo({ url: `/pages/notice/notice?id=${e.currentTarget.dataset.id}` })
  }
})
