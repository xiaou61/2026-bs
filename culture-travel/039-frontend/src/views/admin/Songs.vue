<template>
  <div class="admin-songs">
    <el-card>
      <template #header><span class="card-title">民歌管理</span></template>
      <el-table :data="songs" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="title" label="标题" />
        <el-table-column prop="userName" label="发布者" width="100" />
        <el-table-column prop="categoryName" label="分类" width="100" />
        <el-table-column prop="viewCount" label="浏览" width="80" />
        <el-table-column label="审核状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.auditStatus === 1 ? 'success' : row.auditStatus === 2 ? 'danger' : 'warning'">
              {{ row.auditStatus === 1 ? '通过' : row.auditStatus === 2 ? '拒绝' : '待审' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button v-if="row.auditStatus !== 1" type="success" link @click="handleAudit(row.id, 1)">通过</el-button>
            <el-button v-if="row.auditStatus !== 2" type="warning" link @click="handleAudit(row.id, 2)">拒绝</el-button>
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
import { ElMessage } from 'element-plus'

const songs = ref([])
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)

const fetchData = async () => {
  const res = await adminApi.getSongs({ pageNum: pageNum.value, pageSize: pageSize.value })
  songs.value = res.data.records
  total.value = res.data.total
}

onMounted(fetchData)

const handleAudit = async (id, status) => {
  await adminApi.auditSong(id, status)
  ElMessage.success('操作成功')
  fetchData()
}
</script>
