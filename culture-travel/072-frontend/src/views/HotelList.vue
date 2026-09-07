<template>
  <div class="hotel-list">
    <el-card class="search-card">
      <el-form :inline="true" :model="query">
        <el-form-item label="酒店名称"><el-input v-model="query.name" placeholder="请输入酒店名称" clearable /></el-form-item>
        <el-form-item label="价格区间">
          <el-select v-model="query.priceRange" placeholder="全部" clearable>
            <el-option label="100以下" value="0-100" />
            <el-option label="100-300" value="100-300" />
            <el-option label="300-500" value="300-500" />
            <el-option label="500以上" value="500-" />
          </el-select>
        </el-form-item>
        <el-form-item><el-button type="primary" @click="loadData">搜索</el-button></el-form-item>
      </el-form>
    </el-card>
    <el-row :gutter="20">
      <el-col :span="8" v-for="hotel in list" :key="hotel.id">
        <el-card class="hotel-card" shadow="hover">
          <el-image :src="hotel.coverImage" class="hotel-img" fit="cover" />
          <div class="hotel-info">
            <h3>{{ hotel.name }}</h3>
            <div class="rating"><el-rate v-model="hotel.rating" disabled show-score /></div>
            <p class="address"><el-icon><Location /></el-icon>{{ hotel.address }}</p>
            <p class="desc">{{ hotel.description }}</p>
            <div class="footer">
              <span class="price">¥{{ hotel.minPrice }}<small>起</small></span>
              <el-button type="primary" size="small" @click="openContact(hotel)">联系酒店</el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-pagination v-if="total > 0" background layout="prev, pager, next" :total="total" :page-size="query.pageSize" v-model:current-page="query.pageNum" @current-change="loadData" class="pagination" />
    <el-dialog v-model="contactDialog" title="联系酒店" width="400px">
      <el-descriptions :column="1" border>
        <el-descriptions-item label="酒店名称">{{ currentHotel?.name }}</el-descriptions-item>
        <el-descriptions-item label="联系电话">{{ currentHotel?.phone }}</el-descriptions-item>
        <el-descriptions-item label="酒店地址">{{ currentHotel?.address }}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getHotelList } from '../api'

const query = ref({ name: '', priceRange: '', pageNum: 1, pageSize: 6 })
const list = ref([])
const total = ref(0)
const contactDialog = ref(false)
const currentHotel = ref(null)

const loadData = async () => {
  const res = await getHotelList(query.value)
  list.value = res.data.records || []
  total.value = res.data.total || 0
}

const openContact = (hotel) => {
  currentHotel.value = hotel
  contactDialog.value = true
}

onMounted(() => loadData())
</script>

<style scoped>
.search-card { margin-bottom: 20px; }
.hotel-card { margin-bottom: 20px; }
.hotel-img { width: 100%; height: 200px; }
.hotel-info { padding: 15px 0; }
.hotel-info h3 { margin: 0 0 10px; font-size: 18px; }
.rating { margin-bottom: 10px; }
.address { color: #999; font-size: 13px; display: flex; align-items: center; gap: 4px; margin-bottom: 8px; }
.desc { color: #666; font-size: 14px; margin-bottom: 15px; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical; overflow: hidden; }
.footer { display: flex; justify-content: space-between; align-items: center; }
.price { color: #f56c6c; font-size: 24px; font-weight: bold; }
.price small { font-size: 14px; font-weight: normal; }
.pagination { margin-top: 20px; justify-content: center; }
</style>
