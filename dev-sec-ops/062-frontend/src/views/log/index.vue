<template>
  <el-card>
    <div class="bar">
      <el-select v-model="query.module" placeholder="模块" style="width: 160px" clearable>
        <el-option label="用户" value="USER" />
        <el-option label="黑名单" value="BLACKLIST" />
        <el-option label="规则" value="RULE" />
        <el-option label="事件" value="EVENT" />
        <el-option label="预警" value="ALERT" />
        <el-option label="案件" value="CASE" />
        <el-option label="申诉" value="APPEAL" />
        <el-option label="公告" value="ANNOUNCEMENT" />
      </el-select>
      <el-input v-model="query.action" placeholder="动作" style="width: 140px" clearable />
      <el-input v-model="query.keyword" placeholder="业务号/内容关键字" style="width: 240px" clearable />
      <el-button type="primary" @click="loadData">查询</el-button>
      <el-button type="success" @click="exportData">导出CSV</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="module" label="模块" width="130" />
      <el-table-column prop="action" label="动作" width="120" />
      <el-table-column prop="operatorName" label="操作人" width="140" />
      <el-table-column prop="operatorRole" label="角色" width="110" />
      <el-table-column prop="bizNo" label="业务号" min-width="180" />
      <el-table-column prop="content" label="内容" min-width="260" show-overflow-tooltip />
      <el-table-column prop="createTime" label="时间" width="180" />
    </el-table>

    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 12px" @current-change="loadData" />
  </el-card>
</template>

<script setup>
import axios from 'axios'
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { getLogPage } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)

const query = reactive({ pageNum: 1, pageSize: 10, module: '', action: '', keyword: '' })

const loadData = async () => {
  loading.value = true
  try {
    const res = await getLogPage(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const exportData = async () => {
  try {
    const token = localStorage.getItem('token') || ''
    const res = await axios.get('/api/log/export', {
      params: {
        module: query.module || undefined,
        action: query.action || undefined,
        keyword: query.keyword || undefined
      },
      responseType: 'blob',
      headers: { Authorization: token }
    })
    const blob = new Blob([res.data], { type: 'text/csv;charset=utf-8' })
    const url = window.URL.createObjectURL(blob)
    const a = document.createElement('a')
    a.href = url
    a.download = 'operation_log.csv'
    document.body.appendChild(a)
    a.click()
    document.body.removeChild(a)
    window.URL.revokeObjectURL(url)
    ElMessage.success('导出成功')
  } catch (e) {
    ElMessage.error('导出失败')
  }
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
