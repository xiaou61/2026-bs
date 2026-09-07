<template>
  <div class="log-container">
    <el-page-header title="返回" @back="$router.back()">
      <template #content>
        <span class="page-title">操作日志</span>
      </template>
    </el-page-header>

    <el-card class="content-card">
      <el-form :inline="true" :model="queryForm">
        <el-form-item label="操作类型">
          <el-select v-model="queryForm.operationType" placeholder="全部" clearable style="width: 150px">
            <el-option label="入库" value="IN" />
            <el-option label="取件" value="OUT" />
            <el-option label="更新" value="UPDATE" />
            <el-option label="删除" value="DELETE" />
          </el-select>
        </el-form-item>
        <el-form-item label="关键词">
          <el-input v-model="queryForm.keyword" placeholder="操作人/描述" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadData">查询</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="tableData" v-loading="loading" style="margin-top: 20px">
        <el-table-column prop="operatorName" label="操作人" width="120" />
        <el-table-column label="操作类型" width="100">
          <template #default="{ row }">
            <el-tag :type="getTypeTag(row.operationType)">{{ getTypeName(row.operationType) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="operationDesc" label="操作描述" />
        <el-table-column prop="ipAddress" label="IP地址" width="150" />
        <el-table-column prop="createTime" label="操作时间" width="180" />
      </el-table>

      <el-pagination
        v-model:current-page="pagination.page"
        v-model:page-size="pagination.size"
        :total="pagination.total"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="loadData"
        @current-change="loadData"
        style="margin-top: 20px; justify-content: center"
      />
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getLogList } from '@/api/log'
import { ElMessage } from 'element-plus'

const loading = ref(false)
const tableData = ref([])

const queryForm = reactive({
  operationType: '',
  keyword: ''
})

const pagination = reactive({
  page: 1,
  size: 20,
  total: 0
})

const getTypeName = (type) => {
  const map = {
    'IN': '入库',
    'OUT': '取件',
    'UPDATE': '更新',
    'DELETE': '删除'
  }
  return map[type] || type
}

const getTypeTag = (type) => {
  const map = {
    'IN': 'success',
    'OUT': 'primary',
    'UPDATE': 'warning',
    'DELETE': 'danger'
  }
  return map[type] || 'info'
}

const handleReset = () => {
  queryForm.operationType = ''
  queryForm.keyword = ''
  pagination.page = 1
  loadData()
}

const loadData = async () => {
  loading.value = true
  try {
    const params = {
      page: pagination.page,
      size: pagination.size,
      ...queryForm
    }
    const res = await getLogList(params)
    tableData.value = res.data.records
    pagination.total = res.data.total
  } catch (error) {
    console.error(error)
    ElMessage.error('加载失败')
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.log-container {
  padding: 20px;
}

.page-title {
  font-size: 18px;
  font-weight: bold;
}

.content-card {
  margin-top: 20px;
}
</style>

