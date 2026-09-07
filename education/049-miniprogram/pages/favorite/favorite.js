const { get } = require('../../utils/request')

Page({
  data: { favorites: [], current: 1, hasMore: true, loading: false },
  onLoad() { this.loadFavorites() },
  async loadFavorites(refresh = true) {
    if (this.data.loading) return
    this.setData({ loading: true })
    const current = refresh ? 1 : this.data.current
    try {
      const res = await get('/favorite/list', { current, size: 10 })
      const records = res.data.records || []
      this.setData({
        favorites: refresh ? records : [...this.data.favorites, ...records],
        current: current + 1,
        hasMore: records.length === 10
      })
    } catch (e) {}
    this.setData({ loading: false })
  },
  onReachBottom() { this.loadFavorites(false) }
})
