<template>
  <div class="my-houses">
    <div class="page-header">
      <h2>我的房源</h2>
      <el-button type="primary" @click="$router.push('/landlord/house/publish')">
        <el-icon><Plus /></el-icon>发布房源
      </el-button>
    </div>

    <el-table v-loading="loading" :data="houses" stripe>
      <el-table-column label="房源" min-width="250">
        <template #default="{ row }">
          <div class="house-info">
            <img :src="row.images?.split(',')[0] || '/placeholder.jpg'" class="house-img" />
            <div>
              <p class="house-title">{{ row.title }}</p>
              <p class="house-address">{{ row.address }}</p>
            </div>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="租金" width="100">
        <template #default="{ row }">¥{{ row.price }}/月</template>
      </el-table-column>
      <el-table-column label="户型" width="100">
        <template #default="{ row }">{{ row.roomCount }}室{{ row.hallCount }}厅</template>
      </el-table-column>
      <el-table-column label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="statusType(row.status)">{{ statusText(row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="发布时间" width="170" prop="createTime" />
      <el-table-column label="操作" width="200" fixed="right">
        <template #default="{ row }">
          <el-button size="small" @click="$router.push(`/landlord/house/edit/${row.id}`)">编辑</el-button>
          <el-button v-if="row.status === 1" size="small" type="warning" @click="handleStatus(row, 0)">下架</el-button>
          <el-button v-if="row.status === 0" size="small" type="success" @click="handleStatus(row, 1)">上架</el-button>
          <el-popconfirm title="确定删除？" @confirm="handleDelete(row)">
            <template #reference>
              <el-button size="small" type="danger">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      v-if="total > 0"
      v-model:current-page="page"
      :total="total"
      :page-size="10"
      layout="total, prev, pager, next"
      style="margin-top: 20px; justify-content: center"
      @current-change="loadData"
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { houseApi } from '@/api'

const loading = ref(false)
const houses = ref([])
const page = ref(1)
const total = ref(0)

const statusText = (s) => ({ 0: '已下架', 1: '可租', 2: '已出租' }[s])
const statusType = (s) => ({ 0: 'info', 1: 'success', 2: 'warning' }[s])

onMounted(() => loadData())

const loadData = async () => {
  loading.value = true
  try {
    const res = await houseApi.getMyHouses({ page: page.value, size: 10 })
    houses.value = res.data?.records || []
    total.value = res.data?.total || 0
  } finally {
    loading.value = false
  }
}

const handleStatus = async (row, status) => {
  await houseApi.updateStatus(row.id, status)
  ElMessage.success('操作成功')
  loadData()
}

const handleDelete = async (row) => {
  await houseApi.delete(row.id)
  ElMessage.success('删除成功')
  loadData()
}
</script>

<style scoped>
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.house-info {
  display: flex;
  gap: 12px;
  align-items: center;
}

.house-img {
  width: 80px;
  height: 60px;
  object-fit: cover;
  border-radius: 6px;
}

.house-title {
  font-weight: 500;
  margin-bottom: 4px;
}

.house-address {
  font-size: 12px;
  color: #999;
}
</style>
