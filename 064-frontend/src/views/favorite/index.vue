<template>
  <el-card>
    <div class="bar">
      <el-select v-model="query.targetType" placeholder="收藏类型" style="width: 160px" clearable>
        <el-option label="经方" value="FORMULA" />
        <el-option label="方案" value="PLAN" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading">
      <el-table-column label="类型" width="120">
        <template #default="{ row }"><el-tag>{{ row.targetType === 'FORMULA' ? '经方' : '方案' }}</el-tag></template>
      </el-table-column>
      <el-table-column prop="targetName" label="名称" min-width="220" />
      <el-table-column prop="createTime" label="收藏时间" width="180" />
      <el-table-column label="操作" width="140">
        <template #default="{ row }">
          <el-button link type="danger" @click="handleCancel(row)">取消收藏</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 12px" @current-change="loadData" />
  </el-card>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { getMyFavoritePage, toggleFavorite } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const query = reactive({ pageNum: 1, pageSize: 10, targetType: '' })

const loadData = async () => {
  loading.value = true
  try {
    const res = await getMyFavoritePage(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const handleCancel = async (row) => {
  await toggleFavorite({ targetType: row.targetType, targetId: row.targetId })
  ElMessage.success('已取消收藏')
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.bar {
  display: flex;
  gap: 10px;
  margin-bottom: 12px;
}
</style>
