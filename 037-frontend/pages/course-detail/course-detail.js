const request = require('../../utils/request');

Page({
  data: {
    course: null
  },

  onLoad(options) {
    const id = options.id || 1;
    request.get(`/courses/${id}`).then(course => {
      this.setData({ course });
    });
  }
});
