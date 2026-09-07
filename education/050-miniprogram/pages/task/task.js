const { get, post, put } = require('../../utils/request')

Page({
  data: {
    mode: 'create',
    courses: [],
    courseIndex: 0,
    title: '',
    signTypes: [
      { type: 1, name: '普通签到' },
      { type: 2, name: '定位签到' },
      { type: 3, name: '二维码签到' },
      { type: 4, name: '数字签到' }
    ],
    signTypeIndex: 0,
    duration: 10,
    lateMinutes: 5,
    distance: 100,
    tasks: [],
    signTypeText: {
      1: '普通签到',
      2: '定位签到',
      3: '二维码签到',
      4: '数字签到'
    }
  },

  onLoad(options) {
    const mode = options.mode || 'create'
    this.setData({ mode })
    
    if (mode == 'create') {
      this.loadCourses()
    } else {
      this.loadTasks()
    }
  },

  async loadCourses() {
    try {
      const res = await get('/course/teacher')
      this.setData({ courses: res.data || [] })
    } catch (err) {
      console.error('获取课程失败', err)
    }
  },

  async loadTasks() {
    try {
      if (this.data.courses.length == 0) {
        await this.loadCourses()
      }
      // 获取第一个课程的任务
      if (this.data.courses.length > 0) {
        const res = await get('/attendance/task/page', { 
          courseId: this.data.courses[0].id,
          page: 1,
          size: 20
        })
        this.setData({ tasks: res.data.records || [] })
      }
    } catch (err) {
      console.error('获取任务失败', err)
    }
  },

  onCourseChange(e) {
    this.setData({ courseIndex: e.detail.value })
  },

  onTitleInput(e) {
    this.setData({ title: e.detail.value })
  },

  onTypeChange(e) {
    this.setData({ signTypeIndex: e.detail.value })
  },

  onDurationInput(e) {
    this.setData({ duration: e.detail.value })
  },

  onLateInput(e) {
    this.setData({ lateMinutes: e.detail.value })
  },

  onDistanceInput(e) {
    this.setData({ distance: e.detail.value })
  },

  async createTask() {
    const { courses, courseIndex, title, signTypes, signTypeIndex, duration, lateMinutes, distance } = this.data
    
    if (courses.length == 0) {
      wx.showToast({ title: '请先选择课程', icon: 'none' })
      return
    }
    if (!title) {
      wx.showToast({ title: '请输入签到标题', icon: 'none' })
      return
    }

    const params = {
      courseId: courses[courseIndex].id,
      title,
      signType: signTypes[signTypeIndex].type,
      duration: parseInt(duration),
      lateMinutes: parseInt(lateMinutes)
    }

    if (signTypes[signTypeIndex].type == 2) {
      // 定位签到需要获取当前位置
      wx.getLocation({
        type: 'gcj02',
        success: async (res) => {
          params.latitude = res.latitude
          params.longitude = res.longitude
          params.distance = parseInt(distance)
          await this.doCreateTask(params)
        },
        fail: () => {
          wx.showToast({ title: '获取位置失败', icon: 'none' })
        }
      })
    } else {
      await this.doCreateTask(params)
    }
  },

  async doCreateTask(params) {
    wx.showLoading({ title: '创建中...' })
    try {
      const res = await post('/attendance/task/create', params)
      wx.showToast({ title: '创建成功', icon: 'success' })
      
      // 显示签到码
      if (res.data.signType == 4) {
        wx.showModal({
          title: '签到码',
          content: res.data.signCode,
          showCancel: false
        })
      }
      
      setTimeout(() => {
        wx.navigateBack()
      }, 1500)
    } catch (err) {
      console.error('创建失败', err)
    } finally {
      wx.hideLoading()
    }
  },

  async endTask(e) {
    const taskId = e.currentTarget.dataset.id
    wx.showModal({
      title: '提示',
      content: '确定要结束这个签到吗？',
      success: async (res) => {
        if (res.confirm) {
          try {
            await put(`/attendance/task/end/${taskId}`)
            wx.showToast({ title: '已结束', icon: 'success' })
            this.loadTasks()
          } catch (err) {
            console.error('结束失败', err)
          }
        }
      }
    })
  },

  viewStats(e) {
    const taskId = e.currentTarget.dataset.id
    wx.showToast({ title: '功能开发中', icon: 'none' })
  }
})
