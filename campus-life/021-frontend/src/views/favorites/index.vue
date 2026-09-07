<template>
  <div class="page">
    <section class="panel">
      <div class="header">
        <div>
          <h2>我的收藏</h2>
          <p>把感兴趣的商品先收进清单，价格合适的时候再下单。</p>
        </div>
      </div>

      <div class="grid" v-loading="loading">
        <article v-for="item in favorites" :key="item.productId" class="card">
          <img :src="resolveImage(item)" :alt="item.title" />
          <div class="card-body">
            <div class="title-line">
              <h3>{{ item.title }}</h3>
              <span>{{ formatPrice(item.price) }}</span>
            </div>

            <p>{{ item.description }}</p>

            <div class="meta">
              <span>{{ item.categoryName || '未分类' }}</span>
              <span>{{ item.condition }}</span>
              <span>{{ formatTime(item.favoriteTime) }}</span>
            </div>

            <div class="footer">
              <div>
                <strong>{{ item.sellerName || '未知卖家' }}</strong>
                <span>信用分 {{ item.sellerCreditScore ?? 100 }}</span>
              </div>
              <div class="actions">
                <el-button link type="primary" @click="router.push(`/product/${item.productId}`)">查看详情</el-button>
                <el-button link type="danger" @click="removeFavorite(item.productId)">取消收藏</el-button>
              </div>
            </div>
          </div>
        </article>

        <el-empty v-if="!loading && !favorites.length" description="还没有收藏商品" />
      </div>

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
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { favoriteApi } from '@/api/favorite'
import { fallbackImage, formatPrice, formatTime, normalizeImageList } from '@/utils/market'

const router = useRouter()
const loading = ref(false)
const favorites = ref([])

const pagination = reactive({
  total: 0,
  current: 1,
  size: 12
})

const loadFavorites = async () => {
  loading.value = true
  try {
    const response = await favoriteApi.getMyFavorites({
      current: pagination.current,
      size: pagination.size
    })
    const page = response.data || {}
    favorites.value = page.records || []
    pagination.total = page.total || 0
    pagination.current = Number(page.current || 1)
    pagination.size = Number(page.size || 12)
  } finally {
    loading.value = false
  }
}

const resolveImage = (item) => {
  const list = normalizeImageList(item.imageList || item.images)
  return list[0] || fallbackImage(item.title)
}

const removeFavorite = async (productId) => {
  await favoriteApi.removeFavorite(productId)
  ElMessage.success('已取消收藏')
  loadFavorites()
}

const handlePageChange = (page) => {
  pagination.current = page
  loadFavorites()
}

onMounted(() => {
  loadFavorites()
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
  margin-bottom: 20px;
}

.header h2 {
  font-size: 28px;
  margin-bottom: 8px;
}

.header p {
  color: #64748b;
}

.grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 18px;
}

.card {
  display: grid;
  grid-template-columns: 220px minmax(0, 1fr);
  border: 1px solid rgba(148, 163, 184, 0.14);
  border-radius: 24px;
  overflow: hidden;
  background: #fff;
}

.card > img {
  width: 100%;
  height: 100%;
  min-height: 220px;
  object-fit: cover;
}

.card-body {
  padding: 18px;
  display: flex;
  flex-direction: column;
  gap: 14px;
}

.title-line {
  display: flex;
  justify-content: space-between;
  gap: 12px;
}

.title-line h3 {
  font-size: 20px;
}

.title-line span {
  color: #dc2626;
  font-weight: 700;
}

.card-body p {
  color: #475569;
  line-height: 1.8;
}

.meta,
.footer,
.actions {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.meta span,
.footer span {
  color: #64748b;
  font-size: 13px;
}

.footer {
  justify-content: space-between;
  align-items: flex-end;
  margin-top: auto;
}

.footer strong {
  display: block;
  margin-bottom: 6px;
}

.pagination {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
}

@media (max-width: 1080px) {
  .grid {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 720px) {
  .card {
    grid-template-columns: 1fr;
  }

  .card > img {
    min-height: 220px;
  }

  .pagination {
    justify-content: center;
  }
}
</style>
