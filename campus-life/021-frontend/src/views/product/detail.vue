<template>
  <div class="detail-page" v-loading="loading">
    <section class="gallery panel">
      <div class="main-image">
        <img :src="currentImage" :alt="product.title || '商品图片'" />
      </div>
      <div class="thumbs">
        <button
          v-for="(image, index) in imageList"
          :key="`${image}-${index}`"
          class="thumb"
          :class="{ active: activeImage === image }"
          type="button"
          @click="activeImage = image"
        >
          <img :src="image" :alt="`${product.title}-${index}`" />
        </button>
      </div>
    </section>

    <section class="summary panel">
      <div class="title-line">
        <div>
          <span class="category">{{ product.categoryName || '未分类' }}</span>
          <h2>{{ product.title }}</h2>
        </div>
        <el-tag effect="dark">{{ getProductStatusLabel(product.status) }}</el-tag>
      </div>

      <div class="price-line">
        <strong>{{ formatPrice(product.price) }}</strong>
        <span v-if="product.originalPrice">{{ formatPrice(product.originalPrice) }}</span>
      </div>

      <div class="stats">
        <div><label>成色</label><span>{{ product.condition }}</span></div>
        <div><label>浏览</label><span>{{ product.viewCount ?? 0 }}</span></div>
        <div><label>收藏</label><span>{{ product.favoriteCount ?? 0 }}</span></div>
        <div><label>发布时间</label><span>{{ formatTime(product.createTime) }}</span></div>
      </div>

      <p class="description">{{ product.description }}</p>

      <div class="actions">
        <el-button
          type="primary"
          size="large"
          :disabled="!canTrade"
          @click="handleBuyNow"
        >
          立即购买
        </el-button>
        <el-button
          size="large"
          :disabled="!canChat"
          @click="goChat"
        >
          联系卖家
        </el-button>
        <el-button
          size="large"
          :disabled="!canTrade"
          @click="bargainDialogVisible = true"
        >
          发起议价
        </el-button>
        <el-button
          size="large"
          :type="isFavorite ? 'warning' : 'default'"
          @click="toggleFavorite"
        >
          {{ isFavorite ? '取消收藏' : '加入收藏' }}
        </el-button>
      </div>
    </section>

    <section class="seller panel">
      <div class="seller-top">
        <div class="seller-user">
          <el-avatar :size="56" :src="product.sellerAvatar || ''">
            {{ (product.sellerName || '卖').slice(0, 1) }}
          </el-avatar>
          <div>
            <h3>{{ product.sellerName || '匿名卖家' }}</h3>
            <p>{{ creditMeta.label }} · 信用分 {{ product.sellerCreditScore ?? 100 }}</p>
          </div>
        </div>
        <el-button text @click="router.push(`/user/${product.sellerId}`)">查看资料</el-button>
      </div>

      <div class="seller-grid">
        <div>
          <label>学院</label>
          <span>{{ product.sellerCollege || '--' }}</span>
        </div>
        <div>
          <label>宿舍</label>
          <span>{{ product.sellerDorm || '--' }}</span>
        </div>
        <div>
          <label>电话</label>
          <span>{{ product.sellerPhone || '--' }}</span>
        </div>
      </div>
    </section>

    <el-dialog v-model="bargainDialogVisible" title="发起议价" width="420px">
      <el-form label-position="top">
        <el-form-item label="目标价格">
          <el-input-number
            v-model="bargainForm.bargainPrice"
            :min="0.01"
            :precision="2"
            :step="1"
            class="full-width"
          />
        </el-form-item>
        <el-form-item label="补充说明">
          <el-input
            v-model="bargainForm.content"
            type="textarea"
            :rows="4"
            placeholder="比如：今晚可自提，想问能否再优惠一点"
          />
        </el-form-item>
      </el-form>

      <template #footer>
        <el-button @click="bargainDialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="bargainSubmitting" @click="handleBargain">
          发送议价
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref, watch } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useRoute, useRouter } from 'vue-router'
import { chatApi } from '@/api/chat'
import { favoriteApi } from '@/api/favorite'
import { orderApi } from '@/api/order'
import { productApi } from '@/api/product'
import { useUserStore } from '@/stores/user'
import {
  fallbackImage,
  formatPrice,
  formatTime,
  getCreditMeta,
  getProductStatusLabel,
  normalizeImageList
} from '@/utils/market'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const loading = ref(false)
const bargainSubmitting = ref(false)
const product = ref({})
const activeImage = ref('')
const isFavorite = ref(false)
const bargainDialogVisible = ref(false)

const bargainForm = reactive({
  bargainPrice: 0,
  content: ''
})

const imageList = computed(() => {
  const list = normalizeImageList(product.value.imageList || product.value.images)
  return list.length ? list : [fallbackImage(product.value.title)]
})

const currentImage = computed(() => activeImage.value || imageList.value[0])

const creditMeta = computed(() => getCreditMeta(product.value.sellerCreditScore ?? 100))

const isSelfProduct = computed(() => {
  return !!userStore.userInfo.id && product.value.sellerId === userStore.userInfo.id
})

const canTrade = computed(() => {
  return userStore.isLoggedIn && product.value.status === 'on_sale' && !isSelfProduct.value
})

const canChat = computed(() => {
  return userStore.isLoggedIn && !isSelfProduct.value
})

const ensureLogin = () => {
  if (!userStore.isLoggedIn) {
    ElMessage.warning('请先登录后再操作')
    router.push('/login')
    return false
  }
  return true
}

const loadProduct = async () => {
  loading.value = true
  try {
    const response = await productApi.getProductDetail(route.params.id)
    product.value = response.data || {}
    activeImage.value = imageList.value[0]
    bargainForm.bargainPrice = Number(product.value.price || 0)

    if (userStore.isLoggedIn && !isSelfProduct.value) {
      const favoriteResponse = await favoriteApi.checkFavorite(product.value.id)
      isFavorite.value = !!favoriteResponse.data
    } else {
      isFavorite.value = false
    }
  } finally {
    loading.value = false
  }
}

const toggleFavorite = async () => {
  if (!ensureLogin()) {
    return
  }

  if (isSelfProduct.value) {
    ElMessage.warning('不能收藏自己发布的商品')
    return
  }

  if (isFavorite.value) {
    await favoriteApi.removeFavorite(product.value.id)
    isFavorite.value = false
    ElMessage.success('已取消收藏')
  } else {
    await favoriteApi.addFavorite(product.value.id)
    isFavorite.value = true
    ElMessage.success('收藏成功')
  }

  await loadProduct()
}

const handleBuyNow = async () => {
  if (!ensureLogin()) {
    return
  }

  await ElMessageBox.confirm('确认创建订单并进入线下交易流程吗？', '立即购买', {
    type: 'warning'
  })
  await orderApi.createOrder({ productId: product.value.id })
  ElMessage.success('订单已创建，请到个人中心查看')
  await loadProduct()
  router.push('/profile?tab=buy')
}

const goChat = () => {
  if (!ensureLogin()) {
    return
  }

  router.push(`/chat/${product.value.sellerId}/${product.value.id}`)
}

const handleBargain = async () => {
  if (!ensureLogin()) {
    return
  }

  bargainSubmitting.value = true
  try {
    await chatApi.bargain({
      productId: product.value.id,
      receiverId: product.value.sellerId,
      bargainPrice: bargainForm.bargainPrice,
      content: bargainForm.content
    })
    ElMessage.success('议价消息已发送')
    bargainDialogVisible.value = false
    router.push(`/chat/${product.value.sellerId}/${product.value.id}`)
  } finally {
    bargainSubmitting.value = false
  }
}

watch(
  () => route.params.id,
  () => {
    loadProduct()
  }
)

onMounted(() => {
  loadProduct()
})
</script>

<style scoped>
.detail-page {
  display: grid;
  grid-template-columns: minmax(0, 1.2fr) minmax(380px, 0.9fr);
  gap: 20px;
}

.panel {
  background: rgba(255, 255, 255, 0.88);
  border: 1px solid rgba(148, 163, 184, 0.16);
  border-radius: 28px;
  padding: 24px;
}

.gallery {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.main-image {
  border-radius: 24px;
  overflow: hidden;
  height: 460px;
  background: #f8fafc;
}

.main-image img,
.thumb img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.thumbs {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(90px, 1fr));
  gap: 12px;
}

.thumb {
  border: 2px solid transparent;
  border-radius: 18px;
  overflow: hidden;
  padding: 0;
  height: 88px;
  cursor: pointer;
}

.thumb.active {
  border-color: #fb923c;
}

.summary,
.seller {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.title-line {
  display: flex;
  justify-content: space-between;
  gap: 16px;
}

.category {
  display: inline-flex;
  padding: 6px 12px;
  border-radius: 999px;
  background: rgba(255, 140, 90, 0.14);
  color: #c2410c;
  margin-bottom: 14px;
}

.title-line h2 {
  font-size: 34px;
  line-height: 1.25;
}

.price-line {
  display: flex;
  align-items: baseline;
  gap: 12px;
}

.price-line strong {
  color: #dc2626;
  font-size: 34px;
}

.price-line span {
  color: #94a3b8;
  text-decoration: line-through;
}

.stats {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 14px;
}

.stats div,
.seller-grid div {
  background: #f8fafc;
  border-radius: 18px;
  padding: 14px 16px;
}

.stats label,
.seller-grid label {
  display: block;
  font-size: 12px;
  color: #64748b;
  margin-bottom: 8px;
}

.stats span,
.seller-grid span {
  color: #0f172a;
}

.description {
  color: #475569;
  line-height: 1.9;
  white-space: pre-wrap;
}

.actions {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
}

.seller {
  grid-column: 2;
}

.seller-top {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 12px;
}

.seller-user {
  display: flex;
  align-items: center;
  gap: 14px;
}

.seller-user h3 {
  margin-bottom: 8px;
}

.seller-user p {
  color: #64748b;
}

.seller-grid {
  display: grid;
  gap: 12px;
}

.full-width {
  width: 100%;
}

@media (max-width: 1080px) {
  .detail-page {
    grid-template-columns: 1fr;
  }

  .seller {
    grid-column: auto;
  }
}

@media (max-width: 720px) {
  .main-image {
    height: 280px;
  }

  .title-line h2 {
    font-size: 28px;
  }

  .stats {
    grid-template-columns: 1fr;
  }
}
</style>
