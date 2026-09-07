<template>
  <div class="restaurant-list">
    <el-card class="search-card">
      <el-form :inline="true" :model="query">
        <el-form-item label="餐厅名称"><el-input v-model="query.name" placeholder="请输入餐厅名称" clearable /></el-form-item>
        <el-form-item label="菜系分类">
          <el-select v-model="query.category" placeholder="全部" clearable>
            <el-option label="东北菜" value="东北菜" />
            <el-option label="俄式西餐" value="俄式西餐" />
            <el-option label="火锅烧烤" value="火锅烧烤" />
            <el-option label="小吃快餐" value="小吃快餐" />
          </el-select>
        </el-form-item>
        <el-form-item><el-button type="primary" @click="loadData">搜索</el-button></el-form-item>
      </el-form>
    </el-card>
    <el-row :gutter="20">
      <el-col :span="8" v-for="restaurant in list" :key="restaurant.id">
        <el-card class="restaurant-card" shadow="hover">
          <el-image :src="restaurant.coverImage" class="restaurant-img" fit="cover" />
          <div class="restaurant-info">
            <h3>{{ restaurant.name }}</h3>
            <div class="tags">
              <el-tag size="small">{{ restaurant.category }}</el-tag>
              <el-rate v-model="restaurant.rating" disabled show-score />
            </div>
            <p class="address"><el-icon><Location /></el-icon>{{ restaurant.address }}</p>
            <p class="recommend"><span>推荐菜品：</span>{{ restaurant.recommendDishes }}</p>
            <div class="footer">
              <span class="price">人均 ¥{{ restaurant.avgPrice }}</span>
              <el-button type="primary" size="small" @click="openContact(restaurant)">联系餐厅</el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-pagination v-if="total > 0" background layout="prev, pager, next" :total="total" :page-size="query.pageSize" v-model:current-page="query.pageNum" @current-change="loadData" class="pagination" />
    <el-dialog v-model="contactDialog" title="联系餐厅" width="400px">
      <el-descriptions :column="1" border>
        <el-descriptions-item label="餐厅名称">{{ currentRestaurant?.name }}</el-descriptions-item>
        <el-descriptions-item label="联系电话">{{ currentRestaurant?.phone }}</el-descriptions-item>
        <el-descriptions-item label="营业时间">{{ currentRestaurant?.openTime }}</el-descriptions-item>
        <el-descriptions-item label="餐厅地址">{{ currentRestaurant?.address }}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getRestaurantList } from '../api'

const query = ref({ name: '', category: '', pageNum: 1, pageSize: 6 })
const list = ref([])
const total = ref(0)
const contactDialog = ref(false)
const currentRestaurant = ref(null)

const loadData = async () => {
  const res = await getRestaurantList(query.value)
  list.value = res.data.records || []
  total.value = res.data.total || 0
}

const openContact = (restaurant) => {
  currentRestaurant.value = restaurant
  contactDialog.value = true
}

onMounted(() => loadData())
</script>

<style scoped>
.search-card { margin-bottom: 20px; }
.restaurant-card { margin-bottom: 20px; }
.restaurant-img { width: 100%; height: 200px; }
.restaurant-info { padding: 15px 0; }
.restaurant-info h3 { margin: 0 0 10px; font-size: 18px; }
.tags { display: flex; align-items: center; gap: 10px; margin-bottom: 10px; }
.address { color: #999; font-size: 13px; display: flex; align-items: center; gap: 4px; margin-bottom: 8px; }
.recommend { color: #666; font-size: 14px; margin-bottom: 15px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; }
.recommend span { color: #409eff; }
.footer { display: flex; justify-content: space-between; align-items: center; }
.price { color: #f56c6c; font-size: 16px; font-weight: bold; }
.pagination { margin-top: 20px; justify-content: center; }
</style>
