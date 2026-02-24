<template>
  <div class="spot-list">
    <el-card class="search-card">
      <el-form :inline="true" :model="query">
        <el-form-item label="景点名称"><el-input v-model="query.name" placeholder="请输入景点名称" clearable /></el-form-item>
        <el-form-item label="景点分类">
          <el-select v-model="query.category" placeholder="全部分类" clearable>
            <el-option label="自然风光" value="自然风光" />
            <el-option label="历史文化" value="历史文化" />
            <el-option label="冰雪乐园" value="冰雪乐园" />
            <el-option label="城市地标" value="城市地标" />
            <el-option label="休闲娱乐" value="休闲娱乐" />
          </el-select>
        </el-form-item>
        <el-form-item><el-button type="primary" @click="loadData">搜索</el-button></el-form-item>
      </el-form>
    </el-card>
    <el-row :gutter="20">
      <el-col :span="6" v-for="spot in list" :key="spot.id">
        <el-card class="spot-card" shadow="hover" @click="$router.push(`/spots/${spot.id}`)">
          <el-image :src="spot.coverImage" class="spot-img" fit="cover" />
          <div class="spot-info">
            <h3>{{ spot.name }}</h3>
            <p class="category"><el-tag size="small">{{ spot.category }}</el-tag></p>
            <p class="address"><el-icon><Location /></el-icon>{{ spot.address }}</p>
            <div class="footer">
              <span class="price">¥{{ spot.ticketPrice }}</span>
              <span class="views"><el-icon><View /></el-icon>{{ spot.viewCount }}</span>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-pagination v-if="total > 0" background layout="prev, pager, next" :total="total" :page-size="query.pageSize" v-model:current-page="query.pageNum" @current-change="loadData" class="pagination" />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getSpotList } from '../api'

const query = ref({ name: '', category: '', pageNum: 1, pageSize: 8 })
const list = ref([])
const total = ref(0)

const loadData = async () => {
  const res = await getSpotList(query.value)
  list.value = res.data.records || []
  total.value = res.data.total || 0
}

onMounted(() => loadData())
</script>

<style scoped>
.search-card { margin-bottom: 20px; }
.spot-card { cursor: pointer; margin-bottom: 20px; }
.spot-img { width: 100%; height: 180px; }
.spot-info { padding: 10px 0; }
.spot-info h3 { margin: 0 0 10px; font-size: 16px; }
.spot-info .category { margin-bottom: 8px; }
.spot-info .address { color: #999; font-size: 13px; display: flex; align-items: center; gap: 4px; margin-bottom: 10px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; }
.footer { display: flex; justify-content: space-between; align-items: center; }
.price { color: #f56c6c; font-size: 18px; font-weight: bold; }
.views { color: #999; font-size: 13px; display: flex; align-items: center; gap: 4px; }
.pagination { margin-top: 20px; justify-content: center; }
</style>
