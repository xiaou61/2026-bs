<template>
  <div class="favorites-page">
    <h2>我的收藏</h2>
    <div v-loading="loading" class="house-grid">
      <div v-for="item in list" :key="item.id" class="house-card" @click="$router.push(`/house/${item.house?.id}`)">
        <div class="house-image">
          <img :src="item.house?.images?.split(',')[0] || '/placeholder.jpg'" />
        </div>
        <div class="house-info">
          <h3>{{ item.house?.title }}</h3>
          <p class="address">{{ item.house?.address }}</p>
          <div class="price">¥{{ item.house?.price }}/月</div>
        </div>
      </div>
    </div>
    <el-empty v-if="!loading && !list.length" description="暂无收藏" />
    <el-pagination v-if="total > 0" v-model:current-page="page" :total="total" :page-size="12" layout="total, prev, pager, next" style="margin-top: 20px; justify-content: center" @current-change="loadData" />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { houseApi } from '@/api'

const loading = ref(false)
const list = ref([])
const page = ref(1)
const total = ref(0)

onMounted(() => loadData())

const loadData = async () => {
  loading.value = true
  try {
    const res = await houseApi.getFavorites({ page: page.value, size: 12 })
    list.value = res.data?.records || []
    total.value = res.data?.total || 0
  } finally { loading.value = false }
}
</script>

<style scoped>
.favorites-page h2 { margin-bottom: 20px; }
.house-grid { display: grid; grid-template-columns: repeat(4, 1fr); gap: 16px; }
.house-card { background: #fff; border-radius: 8px; overflow: hidden; cursor: pointer; transition: transform 0.2s; }
.house-card:hover { transform: translateY(-4px); }
.house-image { height: 140px; }
.house-image img { width: 100%; height: 100%; object-fit: cover; }
.house-info { padding: 12px; }
.house-info h3 { font-size: 14px; margin-bottom: 4px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; }
.address { font-size: 12px; color: #999; margin-bottom: 8px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; }
.price { color: #f56c6c; font-weight: bold; }
</style>
