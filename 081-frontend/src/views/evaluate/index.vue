<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.orderId" placeholder="工单ID" style="width: 140px" />
        <el-input v-model="query.technicianId" placeholder="技师ID" style="width: 140px" />
        <el-select v-model="query.score" placeholder="评分" style="width: 120px" clearable>
          <el-option label="5分" :value="5" />
          <el-option label="4分" :value="4" />
          <el-option label="3分" :value="3" />
          <el-option label="2分" :value="2" />
          <el-option label="1分" :value="1" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="orderId" label="工单ID" width="100" />
        <el-table-column prop="userId" label="用户ID" width="100" />
        <el-table-column prop="technicianId" label="技师ID" width="100" />
        <el-table-column prop="score" label="总分" width="80" />
        <el-table-column prop="attitudeScore" label="态度" width="80" />
        <el-table-column prop="qualityScore" label="质量" width="80" />
        <el-table-column prop="speedScore" label="时效" width="80" />
        <el-table-column prop="content" label="评价内容" min-width="260" show-overflow-tooltip />
        <el-table-column prop="createTime" label="评价时间" width="180" />
      </el-table>
      <el-pagination
        v-model:current-page="query.pageNum"
        v-model:page-size="query.pageSize"
        :total="total"
        @current-change="loadData"
        layout="total, prev, pager, next"
        style="margin-top: 20px; justify-content: flex-end"
      />
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getEvaluateList } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)

const query = reactive({
  pageNum: 1,
  pageSize: 10,
  orderId: '',
  technicianId: '',
  score: null
})

const loadData = async () => {
  loading.value = true
  try {
    const params = {
      ...query,
      orderId: query.orderId ? Number(query.orderId) : undefined,
      technicianId: query.technicianId ? Number(query.technicianId) : undefined
    }
    const res = await getEvaluateList(params)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

onMounted(loadData)
</script>

<style scoped>
.page-container {
  padding: 10px;
}

.search-bar {
  display: flex;
  gap: 10px;
  margin-bottom: 15px;
}
</style>
