const api = require('../../api/index')
Page({
  data: { rankList: [] },
  onLoad() { this.loadRank() },
  async loadRank() {
    const rankList = await api.getRankList()
    this.setData({ rankList })
  }
})
