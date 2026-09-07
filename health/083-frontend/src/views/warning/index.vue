<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.elderId" placeholder="老人" clearable style="width: 180px">
          <el-option v-for="item in elderOptions" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
        <el-select v-model="query.warningLevel" placeholder="等级" clearable style="width: 140px">
          <el-option label="low" value="low" />
          <el-option label="medium" value="medium" />
          <el-option label="high" value="high" />
        </el-select>
        <el-select v-model="query.status" placeholder="状态" clearable style="width: 140px">
          <el-option label="待处理" :value="0" />
          <el-option label="处理中" :value="1" />
          <el-option label="已处理" :value="2" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="id" label="ID" width="70" />
        <el-table-column prop="elderId" label="老人">
          <template #default="{ row }">{{ elderName(row.elderId) }}</template>
        </el-table-column>
        <el-table-column prop="warningLevel" label="等级" width="100" />
        <el-table-column prop="warningContent" label="预警内容" min-width="240" />
        <el-table-column prop="status" label="状态" width="110">
          <template #default="{ row }">
            <el-tag :type="statusType(row.status)">{{ statusText(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="220">
          <template #default="{ row }">
            <el-button link type="warning" @click="setStatus(row.id, 1)">处理中</el-button>
            <el-button link type="success" @click="setStatus(row.id, 2)">已处理</el-button>
            <el-popconfirm title="确认删除？" @confirm="handleDelete(row.id)">
              <template #reference>
                <el-button link type="danger">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="query.pageNum"
        v-model:page-size="query.pageSize"
        :total="total"
        layout="total, prev, pager, next"
        @current-change="loadData"
      />
    </el-card>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { deleteWarning, getElderAll, getWarningList, updateWarningStatus } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const elderOptions = ref([])

const query = reactive({
  pageNum: 1,
  pageSize: 10,
  elderId: null,
  warningLevel: '',
  status: null
})

const elderName = (id) => {
  const target = elderOptions.value.find(item => item.id === id)
  return target ? target.name : '-'
}

const statusText = (status) => {
  if (status === 2) return '已处理'
  if (status === 1) return '处理中'
  return '待处理'
}

const statusType = (status) => {
  if (status === 2) return 'success'
  if (status === 1) return 'warning'
  return 'danger'
}

const loadElders = async () => {
  const res = await getElderAll()
  elderOptions.value = res.data || []
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getWarningList(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const setStatus = async (id, status) => {
  await updateWarningStatus(id, status)
  ElMessage.success('状态已更新')
  loadData()
}

const handleDelete = async (id) => {
  await deleteWarning(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(async () => {
  await loadElders()
  await loadData()
})
</script>

<style scoped>
.page-container {
  padding: 10px;
}

.search-bar {
  display: flex;
  gap: 10px;
  margin-bottom: 15px;
  flex-wrap: wrap;
}
</style>
