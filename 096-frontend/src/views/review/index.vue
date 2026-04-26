<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.keyword" placeholder="搜索医生/患者/内容" clearable />
        <el-button type="primary" @click="loadData">查询</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="doctorName" label="医生" />
        <el-table-column prop="patientName" label="患者" />
        <el-table-column prop="rating" label="评分" />
        <el-table-column prop="content" label="评价内容" min-width="260" show-overflow-tooltip />
        <el-table-column prop="createTime" label="评价时间" />
        <el-table-column label="操作" width="120">
          <template #default="{ row }">
            <el-popconfirm title="确认删除该评价吗？" @confirm="handleDelete(row.id)">
              <template #reference><el-button link type="danger">删除</el-button></template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="query.pageNum"
        v-model:page-size="query.pageSize"
        :total="total"
        layout="total, prev, pager, next"
        style="margin-top: 16px"
        @current-change="loadData"
      />
    </el-card>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { deleteReview, getReviewPage } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const query = reactive({ pageNum: 1, pageSize: 10, keyword: '' })

const loadData = async () => {
  loading.value = true
  try {
    const res = await getReviewPage(query)
    tableData.value = res.data.list || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const handleDelete = async (id) => {
  await deleteReview(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.page-container { padding: 4px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 16px; }
</style>
