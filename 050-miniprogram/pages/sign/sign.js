const { get, post } = require('../../utils/request')

Page({
  data: {
    taskId: null,
    task: {},
    signCode: '',
    location: null,
    signSuccess: false,
    signResult: null,
    signTypeText: {
      1: '普通签到',
      2: '定位签到',
      3: '二维码签到',
      4: '数字签到'
    }
  },

  onLoad(options) {
    if (options.taskId) {
      this.setData({ taskId: options.taskId })
      this.loadTask()
    }
  },

  async loadTask() {
    try {
      const res = await get(`/attendance/task/${this.data.taskId}`)
      this.setData({ task: res.data || {} })
    } catch (err) {
      console.error('获取任务失败', err)
    }
  },

  onCodeInput(e) {
    this.setData({ signCode: e.detail.value })
  },

  async doSign() {
    const { taskId, signCode, location, task } = this.data
    
    wx.showLoading({ title: '签到中...' })
    
    try {
      const params = { taskId }
      
      // 数字签到需要带上签到码
      if (task.signType == 4) {
        params.signCode = signCode
      }
      
      // 定位签到需要带上位置信息
      if (task.signType == 2 && location) {
        params.latitude = location.latitude
        params.longitude = location.longitude
        params.address = location.address
      }
      
      const res = await post('/attendance/sign', params)
      
      this.setData({
        signSuccess: true,
        signResult: res.data
      })
      
      wx.showToast({
        title: '签到成功',
        icon: 'success'
      })
    } catch (err) {
      console.error('签到失败', err)
    } finally {
      wx.hideLoading()
    }
  },

  getLocationAndSign() {
    wx.showLoading({ title: '获取位置...' })
    
    wx.getLocation({
      type: 'gcj02',
      success: (res) => {
        this.setData({
          location: {
            latitude: res.latitude,
            longitude: res.longitude,
            address: `${res.latitude.toFixed(6)}, ${res.longitude.toFixed(6)}`
          }
        })
        wx.hideLoading()
        this.doSign()
      },
      fail: (err) => {
        wx.hideLoading()
        wx.showToast({
          title: '获取位置失败，请检查定位权限',
          icon: 'none'
        })
      }
    })
  },

  scanQrCode() {
    wx.scanCode({
      onlyFromCamera: true,
      success: (res) => {
        // 二维码内容作为签到码
        this.setData({ signCode: res.result })
        this.doSign()
      },
      fail: () => {
        wx.showToast({
          title: '扫码取消',
          icon: 'none'
        })
      }
    })
  },

  goBack() {
    wx.switchTab({
      url: '/pages/index/index'
    })
  }
})
