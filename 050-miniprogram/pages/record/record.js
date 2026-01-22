const { get } = require('../../utils/request')

Page({
  data: {
    courseId: null,
    stat: null,
    records: [],
    statusText: {
      0: '未签到',
      1: '已签到',
      2: '迟到',
      3: '早退',
      4: '请假',
      5: '补签'
    },
    statusClass: {
      0: 'tag-error',
      1: 'tag-success',
      2: 'tag-warning',
      3: 'tag-warning',
      4: 'tag-info',
      5: 'tag-info'
    }
  },

  onLoad(options) {
    if (options.courseId) {
      this.setData({ courseId: options.courseId })
    }
  },

  onShow() {
    this.loadData()
  },

  async loadData() {
    await Promise.all([
      this.loadStat(),
      this.loadRecords()
    ])
  },

  async loadStat() {
    try {
      let res
      if (this.data.courseId) {
        res = await get('/attendance/stat/student', { courseId: this.data.courseId })
      } else {
        res = await get('/attendance/stat/student/all')
        // 合并统计
        if (res.data && res.data.length > 0) {
          const stat = res.data.reduce((acc, item) => ({
            totalCount: acc.totalCount + item.totalCount,
            normalCount: acc.normalCount + item.normalCount,
            lateCount: acc.lateCount + item.lateCount,
            absentCount: acc.absentCount + item.absentCount,
            leaveCount: acc.leaveCount + item.leaveCount,
            makeupCount: acc.makeupCount + item.makeupCount
          }), { totalCount: 0, normalCount: 0, lateCount: 0, absentCount: 0, leaveCount: 0, makeupCount: 0 })
          
          stat.attendanceRate = stat.totalCount > 0 
            ? ((stat.normalCount + stat.lateCount + stat.makeupCount) * 100 / stat.totalCount).toFixed(1)
            : 0
          res.data = stat
        }
      }
      this.setData({ stat: res.data })
    } catch (err) {
      console.error('获取统计失败', err)
    }
  },

  async loadRecords() {
    try {
      if (this.data.courseId) {
        const res = await get('/attendance/record/student', { courseId: this.data.courseId })
        this.setData({ records: res.data || [] })
      }
    } catch (err) {
      console.error('获取记录失败', err)
    }
  }
})
