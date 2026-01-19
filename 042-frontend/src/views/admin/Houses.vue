<template>
  <div class="houses-page">
    <h2>房源管理</h2>
    <div class="filter-bar">
      <el-select v-model="filters.status" placeholder="状态" clearable style="width:120px" @change="loadData">
        <el-option label="已下架" :value="0" />
        <el-option label="可租" :value="1" />
        <el-option label="已出租" :value="2" />
      </el-select>
      <el-input v-model="filters.keyword" placeholder="搜索标题/地址" clearable style="width:200px" @keyup.enter="loadData" />
      <el-button type="primary" @click="loadData">搜索</el-button>
    </div>
    <el-table v-loading="loading" :data="list" stripe>
      <el-table-column label="ID" width="80" prop="id" />
      <el-table-column label="房源标题" min-width="200" prop="title" />
      <el-table-column label="地址" min-width="200" prop="address" />
      <el-table-column label="租金" width="100">
        <template #default="{ row }">¥{{ row.price }}/月</template>
      </el-table-column>
      <el-table-column label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="statusType(row.status)">{{ statusText(row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="发布时间" width="170" prop="createTime" />
      <el-table-column label="操作" width="150">
        <template #default="{ row }">
          <el-button v-if="row.status === 0" size="small" type="success" @click="handleAudit(row, 1)">上架</el-button>
          <el-button v-if="row.status === 1" size="small" type="warning" @click="handleAudit(row, 0)">下架</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination v-if="total > 0" v-model:current-page="page" :total="total" :page-size="10" layout="total, prev, pager, next" style="margin-top: 20px; justify-content: center" @current-change="loadData" />
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { adminApi } from '@/api'

const loading = ref(false)
const list = ref([])
const page = ref(1)
const total = ref(0)
const filters = reactive({ status: '', keyword: '' })

const statusText = (s) => ({ 0: '已下架', 1: '可租', 2: '已出租' }[s])
const statusType = (s) => ({ 0: 'info', 1: 'success', 2: 'warning' }[s])

onMounted(() => loadData())

const loadData = async () => {
  loading.value = true
  try {
    const res = await adminApi.getHouses({ page: page.value, size: 10, ...filters })
    list.value = res.data?.records || []
    total.value = res.data?.total || 0
  } finally { loading.value = false }
}

const handleAudit = async (row, status) => {
  await adminApi.auditHouse(row.id, status)
  ElMessage.success('操作成功')
  loadData()
}
</script>

<style scoped>
.houses-page h2 { margin-bottom: 20px; }
.filter-bar { display: flex; gap: 12px; margin-bottom: 20px; }
</style>
