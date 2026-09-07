import request from '@/utils/request'

export const favoriteApi = {
  addFavorite: (productId) => {
    return request({
      url: '/favorite/add',
      method: 'post',
      data: { productId }
    })
  },

  removeFavorite: (productId) => {
    return request({
      url: `/favorite/${productId}`,
      method: 'delete'
    })
  },

  getMyFavorites: (params) => {
    return request({
      url: '/favorite/my',
      method: 'get',
      params
    })
  },

  checkFavorite: (productId) => {
    return request({
      url: `/favorite/check/${productId}`,
      method: 'get'
    })
  }
}
