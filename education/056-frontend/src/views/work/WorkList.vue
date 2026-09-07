<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.keyword" placeholder="搜索作品名称" style="width: 200px" clearable />
        <el-select v-model="query.status" placeholder="状态" clearable style="width: 120px">
          <el-option label="待审核" :value="0" />
          <el-option label="已通过" :value="1" />
          <el-option label="已退回" :value="2" />
          <el-option label="已撤回" :value="3" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="title" label="作品名称" min-width="200" />
        <el-table-column prop="competitionTitle" label="所属竞赛" min-width="180" />
        <el-table-column prop="authorName" label="作者" width="120" />
        <el-table-column prop="wordCount" label="字数" width="80" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="statusType[row.status]">{{ statusText[row.status] }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="finalScore" label="得分" width="80" />
        <el-table-column prop="submitTime" label="提交时间" width="160" />
        <el-table-column label="操作" width="180">
          <template #default="{ row }">
            <el-button link type="primary" @click="$router.push(`/work/${row.id}`)">查看</el-button>
            <el-button link type="success" v-if="row.status === 0" @click="handleAudit(row.id, 1)">通过</el-button>
            <el-button link type="danger" v-if="row.status === 0" @click="showRejectDialog(row.id)">退回</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" @current-change="loadData" style="margin-top: 15px" />
    </el-card>
    <el-dialog v-model="rejectDialogVisible" title="退回原因" width="400px">
      <el-input v-model="rejectReason" type="textarea" :rows="4" placeholder="请输入退回原因" />
      <template #footer>
        <el-button @click="rejectDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleReject">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getWorkList, auditWork } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const query = reactive({ pageNum: 1, pageSize: 10, keyword: '', status: null, competitionId: null })
const statusText = { 0: '待审核', 1: '已通过', 2: '已退回', 3: '已撤回' }
const statusType = { 0: 'warning', 1: 'success', 2: 'danger', 3: 'info' }
const rejectDialogVisible = ref(false)
const rejectReason = ref('')
const currentWorkId = ref(null)

const loadData = async () => {
  loading.value = true
  try {
    const res = await getWorkList(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const handleAudit = async (id, status) => {
  await auditWork({ id, status })
  ElMessage.success('操作成功')
  loadData()
}

const showRejectDialog = (id) => {
  currentWorkId.value = id
  rejectReason.value = ''
  rejectDialogVisible.value = true
}

const handleReject = async () => {
  await auditWork({ id: currentWorkId.value, status: 2, rejectReason: rejectReason.value })
  ElMessage.success('操作成功')
  rejectDialogVisible.value = false
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 15px; }
</style>
