<template>
  <div>
    <el-card>
      <h3 style="margin-bottom:15px">我的收藏</h3>
      <el-table :data="favorites" v-loading="loading" stripe>
        <el-table-column prop="movieTitle" label="电影名称" />
        <el-table-column prop="director" label="导演" />
        <el-table-column prop="createTime" label="收藏时间" width="160" />
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button link type="primary" @click="$router.push(`/movie/${row.movieId}`)">查看</el-button>
            <el-button link type="danger" @click="handleCancel(row.movieId)">取消收藏</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getMyFavorites, toggleFavorite } from '../../api'

const loading = ref(false)
const favorites = ref([])

const loadData = async () => { loading.value = true; try { const res = await getMyFavorites(); favorites.value = res.data } finally { loading.value = false } }
const handleCancel = async (movieId) => { await toggleFavorite(movieId); ElMessage.success('已取消收藏'); loadData() }
onMounted(loadData)
</script>
