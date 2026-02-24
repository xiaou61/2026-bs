<template>
  <div>
    <el-card>
      <div class="toolbar">
        <el-input-number v-model="query.movieId" :min="1" placeholder="影片ID" style="width: 180px" />
        <el-select v-if="isAdmin" v-model="query.status" placeholder="状态" clearable style="width: 160px">
          <el-option label="待审核" value="PENDING" />
          <el-option label="通过" value="APPROVED" />
          <el-option label="拒绝" value="REJECTED" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button v-if="!isAdmin" type="success" @click="showAdd = true">发布评论</el-button>
      </div>

      <el-table :data="tableData" border v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="movieId" label="影片ID" width="90" />
        <el-table-column prop="userId" label="用户ID" width="90" />
        <el-table-column prop="rating" label="评分" width="90" />
        <el-table-column prop="content" label="内容" min-width="260" />
        <el-table-column prop="status" label="状态" width="100" />
        <el-table-column label="操作" width="220">
          <template #default="{ row }">
            <template v-if="isAdmin && row.status === 'PENDING'">
              <el-button link type="success" @click="handleAudit(row.id, 'APPROVED')">通过</el-button>
              <el-button link type="warning" @click="handleAudit(row.id, 'REJECTED')">拒绝</el-button>
            </template>
            <el-button link type="danger" @click="handleDelete(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination class="mt16" v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" @current-change="loadData" />
    </el-card>

    <el-dialog v-model="showAdd" title="发布评论" width="500px">
      <el-form :model="form" label-width="90px">
        <el-form-item label="影片ID"><el-input-number v-model="form.movieId" :min="1" /></el-form-item>
        <el-form-item label="订单ID"><el-input-number v-model="form.orderId" :min="1" /></el-form-item>
        <el-form-item label="评分"><el-input-number v-model="form.rating" :min="1" :max="10" /></el-form-item>
        <el-form-item label="内容"><el-input v-model="form.content" type="textarea" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showAdd = false">取消</el-button>
        <el-button type="primary" @click="handleAdd">提交</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { useUserStore } from '../../store/user'
import { addComment, auditComment, deleteComment, getCommentList, getPublicCommentList } from '../../api'

const userStore = useUserStore()
const isAdmin = computed(() => (userStore.user?.role || '').toUpperCase() === 'ADMIN')

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const showAdd = ref(false)

const query = reactive({ pageNum: 1, pageSize: 10, movieId: 1, status: '' })
const form = reactive({ movieId: 1, orderId: null, rating: 8, content: '' })

const loadData = async () => {
  loading.value = true
  try {
    if (isAdmin.value) {
      const res = await getCommentList(query)
      tableData.value = res.data.records || []
      total.value = res.data.total || 0
    } else {
      const res = await getPublicCommentList(query.movieId, { pageNum: query.pageNum, pageSize: query.pageSize })
      tableData.value = res.data.records || []
      total.value = res.data.total || 0
    }
  } finally {
    loading.value = false
  }
}

const handleAdd = async () => {
  await addComment(form)
  ElMessage.success('评论已提交，等待审核')
  showAdd.value = false
  form.content = ''
  loadData()
}

const handleAudit = async (id, status) => {
  await auditComment(id, status)
  ElMessage.success('审核完成')
  loadData()
}

const handleDelete = async id => {
  await deleteComment(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.toolbar {
  display: flex;
  gap: 10px;
  margin-bottom: 16px;
}

.mt16 {
  margin-top: 16px;
}
</style>
