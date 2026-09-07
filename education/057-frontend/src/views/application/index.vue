<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.studentName" placeholder="考生姓名" clearable style="width: 150px" />
        <el-select v-model="query.status" placeholder="状态" clearable style="width: 120px">
          <el-option label="待审核" :value="0" /><el-option label="已通过" :value="1" /><el-option label="已拒绝" :value="2" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="examNo" label="准考证号" width="120" />
        <el-table-column prop="studentName" label="考生姓名" width="100" />
        <el-table-column prop="planYear" label="年份" width="80" />
        <el-table-column prop="firstMajorName" label="第一志愿" />
        <el-table-column prop="secondMajorName" label="第二志愿" />
        <el-table-column prop="adjust" label="服从调剂" width="80">
          <template #default="{ row }"><el-tag :type="row.adjust === 1 ? 'success' : 'info'">{{ row.adjust === 1 ? '是' : '否' }}</el-tag></template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="['warning', 'success', 'danger'][row.status]">{{ ['待审核', '已通过', '已拒绝'][row.status] }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button link type="success" v-if="row.status === 0" @click="handleAudit(row.id, 1)">通过</el-button>
            <el-button link type="danger" v-if="row.status === 0" @click="handleAudit(row.id, 2)">拒绝</el-button>
            <el-popconfirm title="确定删除?" @confirm="handleDelete(row.id)">
              <template #reference><el-button link type="danger">删除</el-button></template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" @current-change="loadData" style="margin-top: 15px" />
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getApplicationPage, deleteApplication, auditApplication } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const query = reactive({ pageNum: 1, pageSize: 10, studentName: '', status: null })

const loadData = async () => {
  loading.value = true
  try {
    const res = await getApplicationPage(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const handleDelete = async (id) => {
  await deleteApplication(id)
  ElMessage.success('删除成功')
  loadData()
}

const handleAudit = async (id, status) => {
  await auditApplication(id, { status, remark: '' })
  ElMessage.success('审核成功')
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 15px; }
</style>
