const { get, put } = require('../../utils/request')
const app = getApp()

Page({
  data: {
    type: 'leave', // leave 或 makeup
    mode: 'list', // list 或 approve
    list: [],
    statusText: {
      0: '待审核',
      1: '已批准',
      2: '已拒绝'
    },
    statusClass: {
      0: 'tag-warning',
      1: 'tag-success',
      2: 'tag-error'
    }
  },

  onLoad(options) {
    const type = options.type || 'leave'
    const mode = options.mode || 'list'
    this.setData({ type, mode })
    
    wx.setNavigationBarTitle({
      title: type == 'makeup' ? '补签记录' : (mode == 'approve' ? '审批管理' : '请假记录')
    })
  },

  onShow() {
    this.loadList()
  },

  async loadList() {
    const { type, mode } = this.data
    const userInfo = app.globalData.userInfo

    try {
      let url
      if (type == 'makeup') {
        url = mode == 'approve' ? '/leave/makeup/teacher' : '/leave/makeup/student'
      } else {
        url = mode == 'approve' ? '/leave/teacher' : '/leave/student'
      }
      
      const res = await get(url, { page: 1, size: 20 })
      this.setData({ list: res.data.records || [] })
    } catch (err) {
      console.error('获取列表失败', err)
    }
  },

  async approve(e) {
    const { id, status } = e.currentTarget.dataset
    const { type } = this.data

    const action = status == 1 ? '批准' : '拒绝'
    
    wx.showModal({
      title: '确认',
      content: `确定要${action}这个申请吗？`,
      success: async (res) => {
        if (res.confirm) {
          try {
            const url = type == 'makeup' 
              ? `/leave/makeup/approve/${id}`
              : `/leave/approve/${id}`
            
            await put(url, { status })
            wx.showToast({ title: '操作成功', icon: 'success' })
            this.loadList()
          } catch (err) {
            console.error('审批失败', err)
          }
        }
      }
    })
  }
})
