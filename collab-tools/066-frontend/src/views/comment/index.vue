<template>
  <el-card>
    <div class="bar">
      <el-input v-model="query.keyword" placeholder="评论内容" style="width: 220px" clearable />
      <el-select v-model="query.status" placeholder="状态" style="width: 140px" clearable>
        <el-option label="待审核" :value="0" />
        <el-option label="已通过" :value="1" />
        <el-option label="已驳回" :value="2" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading">
      <el-table-column prop="postTitle" label="文章" min-width="180" />
      <el-table-column prop="userName" label="评论人" width="120" />
      <el-table-column prop="content" label="评论内容" min-width="220" show-overflow-tooltip />
      <el-table-column label="状态" width="100">
        <template #default="{ row }"><el-tag :type="statusType(row.status)">{{ statusText(row.status) }}</el-tag></template>
      </el-table-column>
      <el-table-column prop="replyContent" label="回复" min-width="200" show-overflow-tooltip />
      <el-table-column prop="createTime" label="评论时间" width="180" />
      <el-table-column label="操作" width="220">
        <template #default="{ row }">
          <el-button link type="primary" @click="openReview(row)">审核</el-button>
          <el-popconfirm title="确认删除？" @confirm="handleDelete(row.id)">
            <template #reference><el-button link type="danger">删除</el-button></template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 12px" @current-change="loadData" />
  </el-card>

  <el-dialog v-model="dialogVisible" title="审核评论" width="500px">
    <el-form :model="reviewForm" label-width="90px">
      <el-form-item label="审核结果">
        <el-radio-group v-model="reviewForm.status">
          <el-radio :label="1">通过</el-radio>
          <el-radio :label="2">驳回</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="回复内容"><el-input v-model="reviewForm.replyContent" type="textarea" :rows="4" maxlength="500" /></el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitReview">保存</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { deleteComment, getCommentPage, reviewComment } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const query = reactive({ pageNum: 1, pageSize: 10, keyword: '', status: null })
const reviewForm = reactive({ id: null, status: 1, replyContent: '' })

const statusText = (status) => {
  if (Number(status) === 0) return '待审核'
  if (Number(status) === 1) return '已通过'
  if (Number(status) === 2) return '已驳回'
  return '未知'
}

const statusType = (status) => {
  if (Number(status) === 0) return 'warning'
  if (Number(status) === 1) return 'success'
  if (Number(status) === 2) return 'info'
  return ''
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getCommentPage(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const openReview = (row) => {
  Object.assign(reviewForm, { id: row.id, status: row.status === 2 ? 2 : 1, replyContent: row.replyContent || '' })
  dialogVisible.value = true
}

const submitReview = async () => {
  await reviewComment(reviewForm)
  ElMessage.success('审核成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteComment(id)
  ElMessage.success('删除成功')
  loadData()
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
