<template>
  <div class="route-list">
    <el-card class="search-card">
      <el-form :inline="true" :model="query">
        <el-form-item label="路线名称"><el-input v-model="query.name" placeholder="请输入路线名称" clearable /></el-form-item>
        <el-form-item label="游玩天数">
          <el-select v-model="query.days" placeholder="全部" clearable>
            <el-option label="1天" :value="1" />
            <el-option label="2天" :value="2" />
            <el-option label="3天" :value="3" />
            <el-option label="3天以上" :value="4" />
          </el-select>
        </el-form-item>
        <el-form-item><el-button type="primary" @click="loadData">搜索</el-button></el-form-item>
      </el-form>
    </el-card>
    <el-row :gutter="20">
      <el-col :span="8" v-for="route in list" :key="route.id">
        <el-card class="route-card" shadow="hover" @click="$router.push(`/routes/${route.id}`)">
          <el-image :src="route.coverImage" class="route-img" fit="cover" />
          <div class="route-info">
            <h3>{{ route.name }}</h3>
            <p class="desc">{{ route.description }}</p>
            <div class="footer">
              <span class="days"><el-icon><Clock /></el-icon>{{ route.days }}天</span>
              <span class="distance"><el-icon><Odometer /></el-icon>{{ route.totalDistance }}km</span>
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
import { getRouteList } from '../api'

const query = ref({ name: '', days: null, pageNum: 1, pageSize: 6 })
const list = ref([])
const total = ref(0)

const loadData = async () => {
  const res = await getRouteList(query.value)
  list.value = res.data.records || []
  total.value = res.data.total || 0
}

onMounted(() => loadData())
</script>

<style scoped>
.search-card { margin-bottom: 20px; }
.route-card { cursor: pointer; margin-bottom: 20px; }
.route-img { width: 100%; height: 200px; }
.route-info { padding: 15px 0; }
.route-info h3 { margin: 0 0 10px; font-size: 18px; }
.route-info .desc { color: #666; font-size: 14px; margin-bottom: 15px; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical; overflow: hidden; }
.footer { display: flex; gap: 20px; color: #999; font-size: 14px; }
.footer span { display: flex; align-items: center; gap: 4px; }
.pagination { margin-top: 20px; justify-content: center; }
</style>
