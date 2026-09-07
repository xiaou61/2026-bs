<template>
  <div class="host-homestay">
    <div class="header">
      <h2>我的民宿</h2>
      <el-button type="primary" @click="$router.push('/host/homestay/edit')">
        <el-icon><Plus /></el-icon>添加民宿
      </el-button>
    </div>
    <el-table :data="homestayList" v-loading="loading" stripe>
      <el-table-column prop="name" label="民宿名称" min-width="150" />
      <el-table-column prop="city" label="城市" width="100" />
      <el-table-column prop="minPrice" label="最低价" width="100">
        <template #default="{ row }">¥{{ row.minPrice }}</template>
      </el-table-column>
      <el-table-column prop="rating" label="评分" width="80" />
      <el-table-column prop="status" label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : row.status === 2 ? 'warning' : 'info'">
            {{ row.status === 1 ? '已上架' : row.status === 2 ? '审核中' : '已下架' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200" fixed="right">
        <template #default="{ row }">
          <el-button type="primary" text @click="$router.push(`/host/homestay/edit/${row.id}`)">编辑</el-button>
          <el-button type="danger" text @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination">
      <el-pagination v-model:current-page="current" :total="total" :page-size="10" layout="total, prev, pager, next" @change="loadData" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getHostHomestays, deleteHomestay } from '@/api/host'

const loading = ref(false)
const homestayList = ref<any[]>([])
const current = ref(1)
const total = ref(0)

const loadData = async () => {
  loading.value = true
  try {
    const res: any = await getHostHomestays({ current: current.value, size: 10 })
    homestayList.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const handleDelete = async (row: any) => {
  try {
    await ElMessageBox.confirm('确定要删除此民宿吗？', '提示', { type: 'warning' })
    await deleteHomestay(row.id)
    ElMessage.success('删除成功')
    loadData()
  } catch (e) {}
}

onMounted(() => {
  loadData()
})
</script>

<style scoped lang="scss">
.host-homestay {
  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
    h2 { margin: 0; }
  }
  .pagination { margin-top: 20px; text-align: right; }
}
</style>
