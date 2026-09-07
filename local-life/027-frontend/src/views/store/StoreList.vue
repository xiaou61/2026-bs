<template>
  <div class="store-list-page">
    <!-- 搜索栏 -->
    <div class="search-bar">
      <el-input
        v-model="searchKeyword"
        placeholder="搜索门店名称或地址"
        size="large"
        style="width: 400px"
        @keyup.enter="handleSearch"
      >
        <template #append>
          <el-button :icon="Search" @click="handleSearch" />
        </template>
      </el-input>
      <el-select v-model="sortBy" placeholder="排序方式" style="margin-left: 20px; width: 150px" @change="fetchStores">
        <el-option label="按评分排序" value="rating" />
        <el-option label="最新发布" value="" />
      </el-select>
    </div>

    <!-- 门店列表 -->
    <div class="store-list">
      <el-row :gutter="20" v-loading="loading">
        <el-col :span="8" v-for="store in stores" :key="store.id">
          <el-card shadow="hover" class="store-card" @click="goToDetail(store.id)">
            <div class="store-content">
              <h3>{{ store.name }}</h3>
              <div class="store-rating">
                <el-rate v-model="store.rating" disabled show-score />
              </div>
              <p><el-icon><Location /></el-icon> {{ store.address }}</p>
              <p><el-icon><Phone /></el-icon> {{ store.phone }}</p>
              <p><el-icon><Clock /></el-icon> {{ store.openTime }} - {{ store.closeTime }}</p>
              <el-tag v-if="store.status === 1" type="success">营业中</el-tag>
              <el-tag v-else type="info">已打烊</el-tag>
            </div>
          </el-card>
        </el-col>
      </el-row>

      <!-- 分页 -->
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :total="total"
        layout="total, prev, pager, next"
        @current-change="fetchStores"
        style="margin-top: 30px; text-align: center"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getStoreList } from '@/api/store'
import { Search, Location, Phone, Clock } from '@element-plus/icons-vue'

const router = useRouter()

const loading = ref(false)
const stores = ref([])
const currentPage = ref(1)
const pageSize = ref(9)
const total = ref(0)
const searchKeyword = ref('')
const sortBy = ref('rating')

// 获取门店列表
const fetchStores = async () => {
  loading.value = true
  try {
    const res = await getStoreList({
      pageNum: currentPage.value,
      pageSize: pageSize.value,
      keyword: searchKeyword.value,
      sortBy: sortBy.value
    })
    stores.value = res.data.records
    total.value = res.data.total
  } catch (error) {
    console.error('获取门店列表失败：', error)
  } finally {
    loading.value = false
  }
}

// 搜索
const handleSearch = () => {
  currentPage.value = 1
  fetchStores()
}

// 跳转详情
const goToDetail = (id) => {
  router.push(`/store/${id}`)
}

onMounted(() => {
  fetchStores()
})
</script>

<style scoped>
.store-list-page {
  max-width: 1200px;
  margin: 0 auto;
  padding: 30px 20px;
}

.search-bar {
  margin-bottom: 30px;
  text-align: center;
}

.store-card {
  cursor: pointer;
  margin-bottom: 20px;
  transition: transform 0.3s;
}

.store-card:hover {
  transform: translateY(-5px);
}

.store-content h3 {
  font-size: 20px;
  margin-bottom: 15px;
  color: #333;
}

.store-rating {
  margin-bottom: 15px;
}

.store-content p {
  margin: 8px 0;
  color: #666;
  display: flex;
  align-items: center;
}

.store-content p .el-icon {
  margin-right: 8px;
}
</style>
