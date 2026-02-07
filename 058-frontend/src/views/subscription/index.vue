<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.type" placeholder="订阅类型" clearable style="width: 150px;">
          <el-option label="日订" value="DAILY" />
          <el-option label="周订" value="WEEKLY" />
          <el-option label="月订" value="MONTHLY" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="userId" label="用户ID" width="80" />
        <el-table-column prop="productId" label="产品ID" width="80" />
        <el-table-column prop="type" label="类型" width="80">
          <template #default="{ row }">{{ { DAILY: '日订', WEEKLY: '周订', MONTHLY: '月订' }[row.type] }}</template>
        </el-table-column>
        <el-table-column prop="quantity" label="数量" width="60" />
        <el-table-column prop="deliveryTime" label="时段" width="80">
          <template #default="{ row }">{{ row.deliveryTime === 'MORNING' ? '上午' : '下午' }}</template>
        </el-table-column>
        <el-table-column prop="startDate" label="开始日期" width="110" />
        <el-table-column prop="endDate" label="结束日期" width="110" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : row.status === 0 ? 'warning' : 'info'">{{ { 0: '暂停', 1: '生效', 2: '已结束' }[row.status] }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="160" />
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 15px;" @current-change="loadData" />
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getSubscriptionPage } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const query = reactive({ pageNum: 1, pageSize: 10, type: '' })

const loadData = async () => {
  loading.value = true
  try { const res = await getSubscriptionPage(query); tableData.value = res.data.records; total.value = res.data.total } finally { loading.value = false }
}
onMounted(loadData)
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 15px; }
</style>
