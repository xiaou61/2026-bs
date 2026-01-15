<template>
  <div class="admin-comments">
    <el-card>
      <template #header><span class="card-title">评论管理</span></template>
      <el-table :data="comments" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="userName" label="用户" width="120" />
        <el-table-column prop="content" label="内容" show-overflow-tooltip />
        <el-table-column prop="createTime" label="时间" width="160">
          <template #default="{ row }">{{ formatTime(row.createTime) }}</template>
        </el-table-column>
        <el-table-column label="操作" width="100">
          <template #default="{ row }">
            <el-button type="danger" link @click="handleDelete(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        v-model:current-page="pageNum"
        :page-size="pageSize"
        :total="total"
        layout="total, prev, pager, next"
        @current-change="fetchData"
      />
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { adminApi } from '@/api'
import { ElMessage, ElMessageBox } from 'element-plus'

const comments = ref([])
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)

const fetchData = async () => {
  const res = await adminApi.getComments({ pageNum: pageNum.value, pageSize: pageSize.value })
  comments.value = res.data.records
  total.value = res.data.total
}

onMounted(fetchData)

const formatTime = (time) => new Date(time).toLocaleString()

const handleDelete = async (id) => {
  await ElMessageBox.confirm('确定要删除该评论吗？', '提示', { type: 'warning' })
  await adminApi.deleteComment(id)
  ElMessage.success('删除成功')
  fetchData()
}
</script>
