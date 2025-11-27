<template>
  <div>
    <el-button type="primary" @click="$router.push('/demand/create')">发布需求</el-button>
    
    <el-table :data="demands" style="width: 100%; margin-top: 20px">
      <el-table-column prop="title" label="标题" />
      <el-table-column prop="type" label="类型" />
      <el-table-column prop="budgetMin" label="预算下限" />
      <el-table-column prop="budgetMax" label="预算上限" />
      <el-table-column prop="status" label="状态">
        <template #default="{ row }">
          <el-tag>{{ getStatusText(row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="发布时间" />
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import request from '../../utils/request'

const user = JSON.parse(localStorage.getItem('user') || '{}')
const demands = ref([])

const getStatusText = (status) => {
  const map = { 'OPEN': '公开中', 'BIDDING': '竞标中', 'ACCEPTED': '已接受', 'CLOSED': '已关闭' }
  return map[status] || status
}

const loadDemands = async () => {
  try {
    if (user.role === 'ARTIST') {
      demands.value = await request.get('/demand/open')
    } else {
      demands.value = await request.get(`/demand/user/${user.id}`)
    }
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadDemands()
})
</script>
