<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.type" placeholder="类型" clearable style="width: 150px;">
          <el-option label="投诉" value="COMPLAINT" />
          <el-option label="反馈" value="FEEDBACK" />
        </el-select>
        <el-select v-model="query.status" placeholder="状态" clearable style="width: 150px;">
          <el-option label="待处理" :value="0" />
          <el-option label="已处理" :value="1" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="userId" label="用户ID" width="80" />
        <el-table-column prop="type" label="类型" width="80">
          <template #default="{ row }">
            <el-tag :type="row.type === 'COMPLAINT' ? 'danger' : 'primary'">{{ row.type === 'COMPLAINT' ? '投诉' : '反馈' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="content" label="内容" show-overflow-tooltip />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'warning'">{{ row.status === 1 ? '已处理' : '待处理' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="reply" label="回复" show-overflow-tooltip />
        <el-table-column prop="createTime" label="创建时间" width="160" />
        <el-table-column label="操作" width="100">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleReply(row)" v-if="row.status === 0">回复</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 15px;" @current-change="loadData" />
    </el-card>
    <el-dialog v-model="dialogVisible" title="回复反馈" width="450px">
      <el-form :model="replyForm" label-width="60px">
        <el-form-item label="内容"><div>{{ replyForm.content }}</div></el-form-item>
        <el-form-item label="回复"><el-input v-model="replyForm.reply" type="textarea" :rows="4" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmitReply">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getComplaintPage, replyComplaint } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const query = reactive({ pageNum: 1, pageSize: 10, type: '', status: null })
const replyForm = reactive({ id: null, content: '', reply: '' })

const loadData = async () => {
  loading.value = true
  try { const res = await getComplaintPage(query); tableData.value = res.data.records; total.value = res.data.total } finally { loading.value = false }
}

const handleReply = (row) => {
  Object.assign(replyForm, { id: row.id, content: row.content, reply: '' })
  dialogVisible.value = true
}

const handleSubmitReply = async () => {
  await replyComplaint({ id: replyForm.id, reply: replyForm.reply })
  ElMessage.success('回复成功')
  dialogVisible.value = false
  loadData()
}
onMounted(loadData)
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 15px; }
</style>
