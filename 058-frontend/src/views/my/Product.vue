<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="categoryId" placeholder="全部分类" clearable @change="loadProducts">
          <el-option v-for="c in categories" :key="c.id" :label="c.name" :value="c.id" />
        </el-select>
      </div>
      <el-row :gutter="15">
        <el-col :span="6" v-for="p in products" :key="p.id" style="margin-bottom: 15px;">
          <el-card shadow="hover">
            <div style="text-align: center; padding: 10px 0;">
              <div style="font-size: 40px;">🥛</div>
              <h3 style="margin: 10px 0;">{{ p.name }}</h3>
              <p style="color: #999; font-size: 13px;">{{ p.spec }} / {{ p.unit }}</p>
              <p style="color: #f56c6c; font-size: 20px; font-weight: bold;">¥{{ p.price }}</p>
              <p style="color: #999; font-size: 12px; margin-top: 5px;">{{ p.description }}</p>
              <p style="color: #999; font-size: 12px;">库存: {{ p.stock }}</p>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <el-empty v-if="products.length === 0" description="暂无产品" />
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getProductList, getCategoryList } from '../../api'

const categories = ref([])
const products = ref([])
const categoryId = ref(null)

const loadProducts = async () => {
  const params = {}
  if (categoryId.value) params.categoryId = categoryId.value
  const res = await getProductList(params)
  products.value = res.data
}

onMounted(async () => {
  const res = await getCategoryList()
  categories.value = res.data
  loadProducts()
})
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 15px; }
</style>
