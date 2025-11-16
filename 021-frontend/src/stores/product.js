import { defineStore } from 'pinia'
import { ref } from 'vue'
import { productApi } from '@/api/product'

export const useProductStore = defineStore('product', () => {
  const categories = ref([])
  const currentProduct = ref(null)

  // 获取分类列表
  const getCategories = async () => {
    try {
      const response = await productApi.getCategories()
      categories.value = response.data
      return response
    } catch (error) {
      throw error
    }
  }

  // 获取商品详情
  const getProductDetail = async (productId) => {
    try {
      const response = await productApi.getProductDetail(productId)
      currentProduct.value = response.data
      return response
    } catch (error) {
      throw error
    }
  }

  return {
    categories,
    currentProduct,
    getCategories,
    getProductDetail
  }
})