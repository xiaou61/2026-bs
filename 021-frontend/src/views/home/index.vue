<template>
  <div class="page">
    <section class="panel search-panel">
      <div class="panel-header">
        <div>
          <h3>发现正在流转的闲置</h3>
          <p>支持关键字、分类、成色和排序筛选，快速找到目标商品。</p>
        </div>
        <el-button type="primary" @click="router.push('/publish')">发布我的闲置</el-button>
      </div>

      <div class="toolbar">
        <el-input
          v-model="filters.keyword"
          placeholder="搜索商品标题或描述"
          clearable
          @keyup.enter="handleSearch"
          @clear="handleSearch"
        >
          <template #prepend>关键词</template>
        </el-input>

        <el-select v-model="filters.categoryId" clearable placeholder="全部分类" @change="handleSearch">
          <el-option
            v-for="category in categories"
            :key="category.id"
            :label="category.categoryName"
            :value="category.id"
          />
        </el-select>

        <el-select v-model="filters.condition" clearable placeholder="全部成色" @change="handleSearch">
          <el-option v-for="item in conditions" :key="item" :label="item" :value="item" />
        </el-select>

        <el-select v-model="sortValue" placeholder="排序方式" @change="handleSortChange">
          <el-option label="最新发布" value="create_time_desc" />
          <el-option label="价格从低到高" value="price_asc" />
          <el-option label="价格从高到低" value="price_desc" />
          <el-option label="浏览量优先" value="viewCount_desc" />
          <el-option label="收藏量优先" value="favoriteCount_desc" />
        </el-select>
      </div>

      <div class="category-pills">
        <button
          class="pill"
          :class="{ active: !filters.categoryId }"
          type="button"
          @click="selectCategory()"
        >
          全部
        </button>
        <button
          v-for="category in categories"
          :key="category.id"
          class="pill"
          :class="{ active: filters.categoryId === category.id }"
          type="button"
          @click="selectCategory(category.id)"
        >
          {{ category.categoryName }}
        </button>
      </div>
    </section>

    <section class="stats-grid">
      <article class="stat-card">
        <span>当前商品数</span>
        <strong>{{ pagination.total }}</strong>
      </article>
      <article class="stat-card">
        <span>筛选条件</span>
        <strong>{{ activeFilterCount }}</strong>
      </article>
      <article class="stat-card">
        <span>平台特色</span>
        <strong>支持议价</strong>
      </article>
    </section>

    <section class="product-grid" v-loading="loading">
      <article v-for="product in products" :key="product.id" class="product-card">
        <div class="cover" @click="goDetail(product.id)">
          <img :src="resolveImage(product)" :alt="product.title" />
          <span class="status-tag">{{ getProductStatusLabel(product.status) }}</span>
        </div>

        <div class="content">
          <div class="title-row">
            <h4 @click="goDetail(product.id)">{{ product.title }}</h4>
            <span class="price">{{ formatPrice(product.price) }}</span>
          </div>
          <p class="desc">{{ product.description }}</p>

          <div class="meta-row">
            <span>{{ product.categoryName || '未分类' }}</span>
            <span>{{ product.condition }}</span>
            <span>{{ formatTime(product.createTime) }}</span>
          </div>

          <div class="seller-row">
            <div>
              <strong>{{ product.sellerName || '匿名卖家' }}</strong>
              <span>信用分 {{ product.sellerCreditScore ?? 100 }}</span>
            </div>
            <div class="stats">
              <span>浏览 {{ product.viewCount ?? 0 }}</span>
              <span>收藏 {{ product.favoriteCount ?? 0 }}</span>
            </div>
          </div>
        </div>
      </article>

      <el-empty v-if="!loading && !products.length" description="暂时没有符合条件的商品" />
    </section>

    <div class="pagination">
      <el-pagination
        background
        layout="prev, pager, next, total"
        :total="pagination.total"
        :page-size="pagination.size"
        :current-page="pagination.current"
        @current-change="handlePageChange"
      />
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { productApi } from '@/api/product'
import { fallbackImage, formatPrice, formatTime, getProductStatusLabel, normalizeImageList } from '@/utils/market'

const router = useRouter()
const loading = ref(false)
const products = ref([])
const categories = ref([])
const sortValue = ref('create_time_desc')

const conditions = ['全新', '九成新', '八成新', '七成新']

const filters = reactive({
  keyword: '',
  categoryId: null,
  condition: '',
  sortBy: '',
  sortOrder: '',
  current: 1,
  size: 12
})

const pagination = reactive({
  total: 0,
  current: 1,
  size: 12
})

const activeFilterCount = computed(() => {
  return [filters.keyword, filters.categoryId, filters.condition].filter(Boolean).length
})

const loadCategories = async () => {
  const response = await productApi.getCategories()
  categories.value = response.data || []
}

const loadProducts = async () => {
  loading.value = true
  try {
    const response = await productApi.getProductList({ ...filters })
    const page = response.data || {}
    products.value = page.records || []
    pagination.total = page.total || 0
    pagination.current = Number(page.current || filters.current)
    pagination.size = Number(page.size || filters.size)
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  filters.current = 1
  loadProducts()
}

const handleSortChange = (value) => {
  if (value === 'create_time_desc') {
    filters.sortBy = ''
    filters.sortOrder = ''
  } else {
    const separatorIndex = value.lastIndexOf('_')
    filters.sortBy = value.slice(0, separatorIndex)
    filters.sortOrder = value.slice(separatorIndex + 1)
  }
  filters.current = 1
  loadProducts()
}

const handlePageChange = (page) => {
  filters.current = page
  loadProducts()
}

const selectCategory = (categoryId = null) => {
  filters.categoryId = categoryId
  handleSearch()
}

const resolveImage = (product) => {
  const list = normalizeImageList(product.imageList || product.images)
  return list[0] || fallbackImage(product.title)
}

const goDetail = (id) => {
  router.push(`/product/${id}`)
}

onMounted(async () => {
  await Promise.all([loadCategories(), loadProducts()])
})
</script>

<style scoped>
.page {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.panel {
  background: rgba(255, 255, 255, 0.78);
  border: 1px solid rgba(148, 163, 184, 0.16);
  border-radius: 28px;
  padding: 24px;
  box-shadow: 0 16px 40px rgba(15, 23, 42, 0.04);
}

.search-panel {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.panel-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 20px;
}

.panel-header h3 {
  font-size: 28px;
  margin-bottom: 8px;
}

.panel-header p {
  color: #64748b;
}

.toolbar {
  display: grid;
  grid-template-columns: 2fr repeat(3, minmax(0, 1fr));
  gap: 14px;
}

.category-pills {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.pill {
  border: 1px solid rgba(148, 163, 184, 0.25);
  background: rgba(255, 255, 255, 0.95);
  color: #334155;
  padding: 10px 16px;
  border-radius: 999px;
  cursor: pointer;
  transition: all 0.2s ease;
}

.pill.active,
.pill:hover {
  background: #111827;
  border-color: #111827;
  color: #fff;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 16px;
}

.stat-card {
  background: linear-gradient(135deg, rgba(255, 255, 255, 0.96), rgba(255, 247, 237, 0.92));
  border-radius: 24px;
  padding: 22px;
  border: 1px solid rgba(255, 159, 111, 0.12);
}

.stat-card span {
  color: #64748b;
  display: block;
  margin-bottom: 14px;
}

.stat-card strong {
  font-size: 30px;
}

.product-grid {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 18px;
}

.product-card {
  overflow: hidden;
  border-radius: 26px;
  background: rgba(255, 255, 255, 0.9);
  border: 1px solid rgba(148, 163, 184, 0.15);
  display: flex;
  flex-direction: column;
}

.cover {
  position: relative;
  height: 230px;
  overflow: hidden;
  cursor: pointer;
}

.cover img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.25s ease;
}

.cover:hover img {
  transform: scale(1.04);
}

.status-tag {
  position: absolute;
  top: 16px;
  right: 16px;
  background: rgba(15, 23, 42, 0.72);
  color: #fff;
  padding: 6px 10px;
  border-radius: 999px;
  font-size: 12px;
}

.content {
  padding: 18px;
  display: flex;
  flex-direction: column;
  gap: 14px;
}

.title-row {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 14px;
}

.title-row h4 {
  font-size: 20px;
  line-height: 1.4;
  cursor: pointer;
}

.price {
  color: #dc2626;
  font-size: 20px;
  font-weight: 700;
}

.desc {
  color: #64748b;
  line-height: 1.7;
  min-height: 48px;
}

.meta-row,
.seller-row,
.stats {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.meta-row span,
.stats span,
.seller-row span {
  font-size: 13px;
  color: #64748b;
}

.seller-row {
  justify-content: space-between;
  align-items: flex-end;
}

.seller-row strong {
  display: block;
  margin-bottom: 4px;
}

.pagination {
  display: flex;
  justify-content: flex-end;
  padding-bottom: 20px;
}

@media (max-width: 1080px) {
  .toolbar,
  .stats-grid,
  .product-grid {
    grid-template-columns: 1fr 1fr;
  }

  .toolbar :deep(.el-input),
  .toolbar :deep(.el-select) {
    width: 100%;
  }
}

@media (max-width: 720px) {
  .panel-header {
    flex-direction: column;
    align-items: flex-start;
  }

  .toolbar,
  .stats-grid,
  .product-grid {
    grid-template-columns: 1fr;
  }

  .pagination {
    justify-content: center;
  }
}
</style>
