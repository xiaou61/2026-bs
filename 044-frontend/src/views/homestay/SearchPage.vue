<template>
  <div class="search-page container">
    <el-card class="filter-card">
      <el-form :inline="true">
        <el-form-item label="关键词">
          <el-input v-model="keyword" placeholder="搜索民宿名称/地址" clearable />
        </el-form-item>
        <el-form-item label="城市">
          <el-input v-model="city" placeholder="城市" clearable />
        </el-form-item>
        <el-form-item label="价格范围">
          <el-input-number v-model="minPrice" :min="0" placeholder="最低价" controls-position="right" />
          <span style="margin: 0 8px;">-</span>
          <el-input-number v-model="maxPrice" :min="0" placeholder="最高价" controls-position="right" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
          <el-button @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <div class="results" v-loading="loading">
      <el-empty v-if="!loading && homestayList.length === 0" description="暂无符合条件的民宿" />
      <el-row :gutter="20">
        <el-col :span="6" v-for="item in homestayList" :key="item.id">
          <el-card class="homestay-card card-hover" @click="$router.push(`/homestay/${item.id}`)">
            <el-image :src="item.coverImage || 'https://via.placeholder.com/300x200'" fit="cover" class="cover" />
            <div class="info">
              <h3>{{ item.name }}</h3>
              <p class="location"><el-icon><Location /></el-icon>{{ item.city }} {{ item.district }}</p>
              <div class="bottom">
                <span class="price">¥{{ item.minPrice }}<small>/晚起</small></span>
                <span class="rating"><el-icon><Star /></el-icon>{{ item.rating }}</span>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <div class="pagination">
        <el-pagination
          v-model:current-page="current"
          v-model:page-size="size"
          :total="total"
          :page-sizes="[8, 12, 20]"
          layout="total, sizes, prev, pager, next"
          @change="loadData"
        />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import { useRoute } from 'vue-router'
import { getHomestayList } from '@/api/homestay'

const route = useRoute()
const loading = ref(false)
const homestayList = ref<any[]>([])
const current = ref(1)
const size = ref(8)
const total = ref(0)
const keyword = ref('')
const city = ref('')
const minPrice = ref<number | undefined>()
const maxPrice = ref<number | undefined>()

const loadData = async () => {
  loading.value = true
  try {
    const res: any = await getHomestayList({
      current: current.value,
      size: size.value,
      keyword: keyword.value || undefined,
      city: city.value || undefined,
      minPrice: minPrice.value,
      maxPrice: maxPrice.value
    })
    homestayList.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  current.value = 1
  loadData()
}

const resetSearch = () => {
  keyword.value = ''
  city.value = ''
  minPrice.value = undefined
  maxPrice.value = undefined
  handleSearch()
}

onMounted(() => {
  if (route.query.keyword) {
    keyword.value = route.query.keyword as string
  }
  loadData()
})

watch(() => route.query.keyword, (val) => {
  if (val) {
    keyword.value = val as string
    handleSearch()
  }
})
</script>

<style scoped lang="scss">
.search-page { padding-top: 20px; }
.filter-card { margin-bottom: 20px; }
.homestay-card {
  cursor: pointer;
  margin-bottom: 20px;
  :deep(.el-card__body) { padding: 0; }
  .cover { width: 100%; height: 180px; }
  .info {
    padding: 12px;
    h3 { font-size: 16px; margin-bottom: 8px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; }
    .location { color: #999; font-size: 13px; display: flex; align-items: center; gap: 4px; margin-bottom: 8px; }
    .bottom { display: flex; justify-content: space-between; align-items: center; }
    .price { color: #ff6600; font-size: 18px; font-weight: bold; small { font-size: 12px; font-weight: normal; } }
    .rating { color: #ff9900; display: flex; align-items: center; gap: 4px; }
  }
}
.pagination { margin-top: 20px; text-align: center; }
</style>
