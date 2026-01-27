<template>
  <el-card>
    <template #header><span>评论管理</span></template>
    <el-table :data="comments" stripe>
      <el-table-column prop="id" label="ID" width="60" />
      <el-table-column prop="username" label="用户" width="120" />
      <el-table-column prop="courseTitle" label="课程" min-width="150" />
      <el-table-column prop="content" label="评论内容" min-width="200" />
      <el-table-column prop="score" label="评分" width="80">
        <template #default="{ row }"><el-rate v-model="row.score" disabled size="small" /></template>
      </el-table-column>
      <el-table-column prop="status" label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : row.status === 2 ? 'danger' : 'warning'">
            {{ row.status === 1 ? '已通过' : row.status === 2 ? '已拒绝' : '待审核' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="时间" width="160" />
      <el-table-column label="操作" width="180">
        <template #default="{ row }">
          <el-button v-if="row.status === 0" link type="success" @click="updateStatus(row, 1)">通过</el-button>
          <el-button v-if="row.status === 0" link type="warning" @click="updateStatus(row, 2)">拒绝</el-button>
          <el-button link type="danger" @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination v-model:current-page="pageNum" :page-size="10" :total="total" layout="total, prev, pager, next" @current-change="loadData" style="margin-top: 20px" />
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import api from '../api'

const comments = ref([])
const total = ref(0)
const pageNum = ref(1)

onMounted(() => loadData())

const loadData = async () => {
  const res = await api.getCommentList({ pageNum: pageNum.value, pageSize: 10 })
  comments.value = res.records
  total.value = res.total
}

const updateStatus = async (row, status) => {
  await api.updateCommentStatus({ id: row.id, status })
  ElMessage.success('操作成功')
  loadData()
}

const handleDelete = async row => {
  await ElMessageBox.confirm('确定删除该评论？', '提示')
  await api.deleteComment(row.id)
  ElMessage.success('删除成功')
  loadData()
}
</script>
