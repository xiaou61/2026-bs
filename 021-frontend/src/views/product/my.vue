<template>
  <div class="page">
    <section class="panel">
      <div class="header">
        <div>
          <h2>我的发布</h2>
          <p>管理上架中的商品，随时编辑、下架或删除。</p>
        </div>
        <el-button type="primary" @click="router.push('/publish')">继续发布</el-button>
      </div>

      <el-table :data="products" v-loading="loading" class="table" empty-text="暂时还没有发布商品">
        <el-table-column label="商品" min-width="280">
          <template #default="{ row }">
            <div class="product-cell">
              <img :src="resolveImage(row)" :alt="row.title" />
              <div>
                <strong>{{ row.title }}</strong>
                <p>{{ row.categoryName || '未分类' }} · {{ row.condition }}</p>
                <span>{{ formatTime(row.createTime) }}</span>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="售价" width="120">
          <template #default="{ row }">{{ formatPrice(row.price) }}</template>
        </el-table-column>
        <el-table-column label="状态" width="120">
          <template #default="{ row }">
            <el-tag :type="row.status === 'on_sale' ? 'success' : row.status === 'sold' ? 'danger' : 'info'">
              {{ getProductStatusLabel(row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="热度" width="160">
          <template #default="{ row }">
            浏览 {{ row.viewCount ?? 0 }} / 收藏 {{ row.favoriteCount ?? 0 }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="320" fixed="right">
          <template #default="{ row }">
            <div class="row-actions">
              <el-button link type="primary" @click="router.push(`/product/${row.id}`)">查看</el-button>
              <el-button link type="primary" :disabled="row.status === 'sold'" @click="router.push(`/publish?edit=${row.id}`)">
                编辑
              </el-button>
              <el-button
                link
                type="warning"
                :disabled="row.status === 'sold'"
                @click="toggleStatus(row)"
              >
                {{ row.status === 'off_shelf' ? '重新上架' : '下架商品' }}
              </el-button>
              <el-button link type="danger" :disabled="row.status === 'sold'" @click="removeProduct(row.id)">
                删除
              </el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>

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
    </section>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useRouter } from 'vue-router'
import { productApi } from '@/api/product'
import { fallbackImage, formatPrice, formatTime, getProductStatusLabel, normalizeImageList } from '@/utils/market'

const router = useRouter()
const loading = ref(false)
const products = ref([])

const pagination = reactive({
  total: 0,
  current: 1,
  size: 10
})

const loadProducts = async () => {
  loading.value = true
  try {
    const response = await productApi.getMyProducts({
      current: pagination.current,
      size: pagination.size
    })
    const page = response.data || {}
    products.value = page.records || []
    pagination.total = page.total || 0
    pagination.current = Number(page.current || 1)
    pagination.size = Number(page.size || 10)
  } finally {
    loading.value = false
  }
}

const handlePageChange = (page) => {
  pagination.current = page
  loadProducts()
}

const resolveImage = (product) => {
  const list = normalizeImageList(product.imageList || product.images)
  return list[0] || fallbackImage(product.title)
}

const toggleStatus = async (product) => {
  const nextStatus = product.status === 'off_shelf' ? 'on_sale' : 'off_shelf'
  await productApi.changeProductStatus(product.id, nextStatus)
  ElMessage.success(nextStatus === 'on_sale' ? '商品已重新上架' : '商品已下架')
  loadProducts()
}

const removeProduct = async (id) => {
  await ElMessageBox.confirm('删除后该商品将不再显示，确认继续吗？', '删除商品', {
    type: 'warning'
  })
  await productApi.deleteProduct(id)
  ElMessage.success('商品已删除')
  loadProducts()
}

onMounted(() => {
  loadProducts()
})
</script>

<style scoped>
.panel {
  background: rgba(255, 255, 255, 0.9);
  border-radius: 28px;
  border: 1px solid rgba(148, 163, 184, 0.16);
  padding: 24px;
}

.header {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  align-items: center;
  margin-bottom: 20px;
}

.header h2 {
  font-size: 28px;
  margin-bottom: 8px;
}

.header p {
  color: #64748b;
}

.table {
  border-radius: 20px;
  overflow: hidden;
}

.product-cell {
  display: flex;
  align-items: center;
  gap: 14px;
}

.product-cell img {
  width: 72px;
  height: 72px;
  border-radius: 16px;
  object-fit: cover;
}

.product-cell strong {
  display: block;
  margin-bottom: 6px;
}

.product-cell p,
.product-cell span {
  color: #64748b;
  font-size: 13px;
}

.row-actions {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.pagination {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
}

@media (max-width: 720px) {
  .header {
    flex-direction: column;
    align-items: flex-start;
  }

  .pagination {
    justify-content: center;
  }
}
</style>
