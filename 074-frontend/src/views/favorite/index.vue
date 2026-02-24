<template>
  <el-card>
    <template #header>我的收藏</template>
    <el-empty v-if="!tableData.length" description="暂无收藏" />
    <el-table v-else :data="tableData" v-loading="loading">
      <el-table-column prop="title" label="商品" min-width="220" />
      <el-table-column prop="craftName" label="工艺名" width="120" />
      <el-table-column prop="craftType" label="类型" width="100" />
      <el-table-column prop="price" label="价格" width="100" />
      <el-table-column prop="stock" label="库存" width="80" />
      <el-table-column label="操作" width="120">
        <template #default="{ row }">
          <el-button link type="danger" @click="remove(row)">取消收藏</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { getMyFavorite, toggleFavorite } from '../../api'

const loading = ref(false)
const tableData = ref([])

const loadData = async () => {
  loading.value = true
  try {
    const res = await getMyFavorite()
    tableData.value = res.data || []
  } finally {
    loading.value = false
  }
}

const remove = async (row) => {
  await toggleFavorite(row.id)
  ElMessage.success('已取消收藏')
  loadData()
}

onMounted(loadData)
</script>


