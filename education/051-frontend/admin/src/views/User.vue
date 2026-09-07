<template>
  <div>
    <el-card>
      <template #header><span>用户管理</span></template>
      <el-input v-model="keyword" placeholder="搜索用户" style="width: 200px; margin-bottom: 20px" @input="loadData" />
      <el-table :data="list" border>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="nickname" label="昵称" />
        <el-table-column label="头像" width="80"><template #default="{ row }"><el-avatar :src="row.avatar" /></template></el-table-column>
        <el-table-column prop="points" label="积分" width="100" />
        <el-table-column prop="level" label="等级" width="100" />
        <el-table-column prop="createTime" label="注册时间" width="180" />
      </el-table>
      <el-pagination v-model:current-page="page" :page-size="10" :total="total" @current-change="loadData" style="margin-top: 20px" />
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import api from '../api'

const list = ref([])
const total = ref(0)
const page = ref(1)
const keyword = ref('')

const loadData = async () => {
  const res = await api.getUserList({ page: page.value, size: 10, keyword: keyword.value })
  list.value = res.records
  total.value = res.total
}

onMounted(loadData)
</script>
