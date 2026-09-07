const request = require('../../utils/request');

Page({
  data: {
    courses: [],
    pageNum: 1,
    pageSize: 10
  },

  onLoad() {
    this.loadCourses();
  },

  loadCourses() {
    request.get('/courses/list', {
      pageNum: this.data.pageNum,
      pageSize: this.data.pageSize
    }).then(page => {
      this.setData({ courses: page.list || [] });
    }).catch(() => {
      this.setData({ courses: [] });
    });
  },

  goDetail(e) {
    wx.navigateTo({ url: `/pages/course-detail/course-detail?id=${e.currentTarget.dataset.id}` });
  }
});
