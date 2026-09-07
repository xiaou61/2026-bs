<template>
  <div class="house-list-page">
    <!-- 搜索筛选 -->
    <div class="filter-section">
      <el-input v-model="filters.keyword" placeholder="搜索地址、小区名称..." clearable style="width: 300px" @keyup.enter="loadData" />
      <el-select v-model="filters.rentType" placeholder="租赁方式" clearable style="width: 120px" @change="loadData">
        <el-option label="整租" value="整租" />
        <el-option label="合租" value="合租" />
      </el-select>
      <el-select v-model="filters.roomCount" placeholder="户型" clearable style="width: 120px" @change="loadData">
        <el-option label="1室" :value="1" />
        <el-option label="2室" :value="2" />
        <el-option label="3室" :value="3" />
        <el-option label="4室+" :value="4" />
      </el-select>
      <el-select v-model="priceRange" placeholder="价格区间" clearable style="width: 140px" @change="handlePriceChange">
        <el-option label="1000以下" value="0-1000" />
        <el-option label="1000-2000" value="1000-2000" />
        <el-option label="2000-3000" value="2000-3000" />
        <el-option label="3000-5000" value="3000-5000" />
        <el-option label="5000以上" value="5000-99999" />
      </el-select>
      <el-button type="primary" @click="loadData">搜索</el-button>
      <el-button @click="resetFilters">重置</el-button>
    </div>

    <!-- 房源列表 -->
    <div v-loading="loading" class="house-list">
      <div v-if="houses.length" class="house-grid">
        <div v-for="house in houses" :key="house.id" class="house-card" @click="$router.push(`/house/${house.id}`)">
          <div class="house-image">
            <img :src="house.images?.split(',')[0] || '/placeholder.jpg'" :alt="house.title" />
            <el-tag v-if="house.status === 1" class="status-tag" type="success">可租</el-tag>
            <el-tag v-else class="status-tag" type="info">已租</el-tag>
          </div>
          <div class="house-info">
            <h3>{{ house.title }}</h3>
            <p class="address"><el-icon><Location /></el-icon>{{ house.address }}</p>
            <div class="tags">
              <el-tag size="small">{{ house.area }}㎡</el-tag>
              <el-tag size="small" type="info">{{ house.roomCount }}室{{ house.hallCount }}厅</el-tag>
              <el-tag size="small" type="warning">{{ house.rentType }}</el-tag>
            </div>
            <div class="bottom-row">
              <div class="price">
                <span class="amount">¥{{ house.price }}</span>
                <span class="unit">/月</span>
              </div>
            </div>
          </div>
        </div>
      </div>
      <el-empty v-else description="暂无房源" />
    </div>

    <!-- 分页 -->
    <div v-if="total > 0" class="pagination">
      <el-pagination
        v-model:current-page="page"
        v-model:page-size="size"
        :total="total"
        :page-sizes="[9, 18, 36]"
        layout="total, sizes, prev, pager, next"
        @size-change="loadData"
        @current-change="loadData"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { houseApi } from '@/api'

const route = useRoute()
const loading = ref(false)
const houses = ref([])
const page = ref(1)
const size = ref(9)
const total = ref(0)
const priceRange = ref('')

const filters = reactive({
  keyword: '',
  rentType: '',
  roomCount: '',
  minPrice: '',
  maxPrice: ''
})

onMounted(() => {
  if (route.query.keyword) {
    filters.keyword = route.query.keyword
  }
  loadData()
})

const handlePriceChange = (val) => {
  if (val) {
    const [min, max] = val.split('-')
    filters.minPrice = min
    filters.maxPrice = max
  } else {
    filters.minPrice = ''
    filters.maxPrice = ''
  }
  loadData()
}

const loadData = async () => {
  loading.value = true
  try {
    const params = {
      page: page.value,
      size: size.value,
      status: 1,
      ...filters
    }
    Object.keys(params).forEach(k => !params[k] && delete params[k])
    const res = await houseApi.getList(params)
    houses.value = res.data?.records || []
    total.value = res.data?.total || 0
  } catch (e) {
    console.error(e)
  } finally {
    loading.value = false
  }
}

const resetFilters = () => {
  Object.keys(filters).forEach(k => filters[k] = '')
  priceRange.value = ''
  page.value = 1
  loadData()
}
</script>

<style scoped>
.house-list-page {
  max-width: 1200px;
  margin: 0 auto;
}

.filter-section {
  background: #fff;
  padding: 20px;
  border-radius: 8px;
  margin-bottom: 20px;
  display: flex;
  gap: 12px;
  flex-wrap: wrap;
  align-items: center;
}

.house-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
}

.house-card {
  background: #fff;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0,0,0,0.06);
  cursor: pointer;
  transition: transform 0.3s, box-shadow 0.3s;
}

.house-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 24px rgba(0,0,0,0.1);
}

.house-image {
  position: relative;
  height: 180px;
}

.house-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.status-tag {
  position: absolute;
  top: 12px;
  right: 12px;
}

.house-info {
  padding: 16px;
}

.house-info h3 {
  font-size: 16px;
  margin-bottom: 8px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.address {
  font-size: 13px;
  color: #999;
  margin-bottom: 10px;
  display: flex;
  align-items: center;
  gap: 4px;
}

.tags {
  display: flex;
  gap: 6px;
  margin-bottom: 12px;
  flex-wrap: wrap;
}

.bottom-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.price {
  color: #f56c6c;
}

.price .amount {
  font-size: 20px;
  font-weight: bold;
}

.price .unit {
  font-size: 13px;
}

.pagination {
  margin-top: 30px;
  display: flex;
  justify-content: center;
}
</style>
