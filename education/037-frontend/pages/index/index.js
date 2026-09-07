const request = require('../../utils/request');

Page({
  data: {
    courses: []
  },

  onLoad() {
    this.loadHotCourses();
  },

  loadHotCourses() {
    request.get('/courses/hot', { limit: 3 }, { loading: false })
      .then(courses => {
        this.setData({ courses });
      })
      .catch(() => {
        this.setData({ courses: [] });
      });
  },

  goCourse(e) {
    const id = e.currentTarget.dataset.id;
    wx.navigateTo({ url: `/pages/course-detail/course-detail?id=${id}` });
  }
});
