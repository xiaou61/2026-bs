<template>
  <div class="page-container">
    <el-card>
      <template #header>待评分作品</template>
      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="title" label="作品名称" min-width="200" />
        <el-table-column prop="competitionTitle" label="所属竞赛" min-width="180" />
        <el-table-column prop="authorName" label="作者" width="120" />
        <el-table-column prop="wordCount" label="字数" width="80" />
        <el-table-column prop="submitTime" label="提交时间" width="160" />
        <el-table-column prop="scoredCount" label="评分状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.scoredCount > 0 ? 'success' : 'warning'">{{ row.scoredCount > 0 ? '已评' : '待评' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="120">
          <template #default="{ row }">
            <el-button link type="primary" @click="$router.push(`/score/${row.id}`)">{{ row.scoredCount > 0 ? '查看/修改' : '去评分' }}</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" @current-change="loadData" style="margin-top: 15px" />
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getPendingWorks } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const query = reactive({ pageNum: 1, pageSize: 10, competitionId: null })

const loadData = async () => {
  loading.value = true
  try {
    const res = await getPendingWorks(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

onMounted(loadData)
</script>

<style scoped>
.page-container { padding: 10px; }
</style>
