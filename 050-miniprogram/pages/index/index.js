const { get } = require('../../utils/request')
const app = getApp()

Page({
  data: {
    userInfo: null,
    activeTasks: [],
    todaySchedules: [],
    unreadCount: 0,
    signTypeText: {
      1: '普通签到',
      2: '定位签到',
      3: '二维码签到',
      4: '数字签到'
    }
  },

  onLoad() {
    this.checkLogin()
  },

  onShow() {
    if (app.globalData.token) {
      this.setData({ userInfo: app.globalData.userInfo })
      this.loadData()
    }
  },

  checkLogin() {
    if (!app.globalData.token) {
      wx.navigateTo({
        url: '/pages/login/login'
      })
    }
  },

  async loadData() {
    await Promise.all([
      this.loadActiveTasks(),
      this.loadTodaySchedules(),
      this.loadUnreadCount()
    ])
  },

  async loadActiveTasks() {
    if (this.data.userInfo && this.data.userInfo.role == 0) {
      try {
        const res = await get('/attendance/task/active')
        this.setData({ activeTasks: res.data || [] })
      } catch (err) {
        console.error('获取待签到任务失败', err)
      }
    }
  },

  async loadTodaySchedules() {
    try {
      const res = await get('/course/schedule/today')
      this.setData({ todaySchedules: res.data || [] })
    } catch (err) {
      console.error('获取今日课表失败', err)
    }
  },

  async loadUnreadCount() {
    try {
      const res = await get('/message/unreadCount')
      this.setData({ unreadCount: res.data || 0 })
    } catch (err) {
      console.error('获取未读消息数失败', err)
    }
  },

  goSign(e) {
    const task = e.currentTarget.dataset.task
    wx.navigateTo({
      url: `/pages/sign/sign?taskId=${task.id}`
    })
  },

  goCourse() {
    wx.switchTab({
      url: '/pages/course/course'
    })
  },

  goRecord() {
    wx.switchTab({
      url: '/pages/record/record'
    })
  },

  goLeave() {
    wx.navigateTo({
      url: '/pages/leave/leave'
    })
  },

  goMessage() {
    wx.navigateTo({
      url: '/pages/message/message'
    })
  },

  goCreateTask() {
    wx.navigateTo({
      url: '/pages/task/task?mode=create'
    })
  },

  goTaskList() {
    wx.navigateTo({
      url: '/pages/task/task?mode=list'
    })
  },

  goApprove() {
    wx.navigateTo({
      url: '/pages/leave/leave?mode=approve'
    })
  },

  goStats() {
    wx.navigateTo({
      url: '/pages/record/record?mode=stats'
    })
  }
})
