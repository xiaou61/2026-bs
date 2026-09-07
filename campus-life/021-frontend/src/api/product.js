import request from '@/utils/request'

export const productApi = {
  // 获取商品列表
  getProductList: (params) => {
    return request({
      url: '/product/list',
      method: 'get',
      params
    })
  },

  // 获取商品详情
  getProductDetail: (id) => {
    return request({
      url: `/product/${id}`,
      method: 'get'
    })
  },

  // 发布商品
  publishProduct: (data) => {
    return request({
      url: '/product/publish',
      method: 'post',
      data
    })
  },

  // 更新商品
  updateProduct: (productId, data) => {
    return request({
      url: '/product/update',
      method: 'put',
      params: { productId },
      data
    })
  },

  // 删除商品
  deleteProduct: (id) => {
    return request({
      url: `/product/${id}`,
      method: 'delete'
    })
  },

  // 商品上下架
  changeProductStatus: (id, status) => {
    return request({
      url: `/product/${id}/shelf`,
      method: 'put',
      params: { status }
    })
  },

  // 获取我的发布
  getMyProducts: (params) => {
    return request({
      url: '/product/my',
      method: 'get',
      params
    })
  },

  // 获取分类列表
  getCategories: () => {
    return request({
      url: '/category/list',
      method: 'get'
    })
  }
}