<template>
  <div class="my-favorite container">
    <h2>我的收藏</h2>
    <div v-loading="loading">
      <el-empty v-if="!loading && homestayList.length === 0" description="暂无收藏" />
      <el-row :gutter="20">
        <el-col :span="6" v-for="item in homestayList" :key="item.id">
          <el-card class="homestay-card card-hover">
            <el-image :src="item.coverImage || 'https://via.placeholder.com/300x200'" fit="cover" class="cover" @click="$router.push(`/homestay/${item.id}`)" />
            <div class="info">
              <h3 @click="$router.push(`/homestay/${item.id}`)">{{ item.name }}</h3>
              <p class="location"><el-icon><Location /></el-icon>{{ item.city }} {{ item.district }}</p>
              <div class="bottom">
                <span class="price">¥{{ item.minPrice }}<small>/晚起</small></span>
                <el-button type="danger" text size="small" @click="handleRemove(item.id)">取消收藏</el-button>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <div class="pagination">
        <el-pagination v-model:current-page="current" :total="total" :page-size="8" layout="prev, pager, next" @change="loadData" />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getMyFavorites, removeFavorite } from '@/api/favorite'

const loading = ref(false)
const homestayList = ref<any[]>([])
const current = ref(1)
const total = ref(0)

const loadData = async () => {
  loading.value = true
  try {
    const res: any = await getMyFavorites({ current: current.value, size: 8 })
    homestayList.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const handleRemove = async (id: number) => {
  try {
    await removeFavorite(id)
    ElMessage.success('已取消收藏')
    loadData()
  } catch (e) {}
}

onMounted(() => {
  loadData()
})
</script>

<style scoped lang="scss">
.my-favorite {
  padding-top: 20px;
  h2 { margin-bottom: 20px; }
}
.homestay-card {
  margin-bottom: 20px;
  :deep(.el-card__body) { padding: 0; }
  .cover { width: 100%; height: 180px; cursor: pointer; }
  .info {
    padding: 12px;
    h3 { font-size: 16px; margin-bottom: 8px; cursor: pointer; &:hover { color: #409eff; } }
    .location { color: #999; font-size: 13px; display: flex; align-items: center; gap: 4px; margin-bottom: 8px; }
    .bottom { display: flex; justify-content: space-between; align-items: center; }
    .price { color: #ff6600; font-size: 18px; font-weight: bold; small { font-size: 12px; font-weight: normal; } }
  }
}
.pagination { text-align: center; margin-top: 20px; }
</style>
