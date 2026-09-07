const api = require('../../api/index')
Page({
  data: { questions: [], page: 1 },
  onLoad() { this.loadData() },
  async loadData() {
    const res = await api.getWrongList({ page: this.data.page, size: 20 })
    this.setData({ questions: res.records || [] })
  }
})
