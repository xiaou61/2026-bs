<template>
  <div class="users-page">
    <h2>用户管理</h2>
    <div class="filter-bar">
      <el-select v-model="filters.role" placeholder="角色" clearable style="width:120px" @change="loadData">
        <el-option label="房东" value="landlord" />
        <el-option label="租客" value="tenant" />
      </el-select>
      <el-input v-model="filters.keyword" placeholder="搜索用户名/姓名/手机" clearable style="width:200px" @keyup.enter="loadData" />
      <el-button type="primary" @click="loadData">搜索</el-button>
    </div>
    <el-table v-loading="loading" :data="list" stripe>
      <el-table-column label="ID" width="80" prop="id" />
      <el-table-column label="用户名" width="120" prop="username" />
      <el-table-column label="真实姓名" width="100" prop="realName" />
      <el-table-column label="手机号" width="130" prop="phone" />
      <el-table-column label="角色" width="80">
        <template #default="{ row }">
          <el-tag :type="row.role === 'landlord' ? 'success' : 'warning'">{{ row.role === 'landlord' ? '房东' : '租客' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="余额" width="100">
        <template #default="{ row }">¥{{ row.balance }}</template>
      </el-table-column>
      <el-table-column label="状态" width="80">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '正常' : '禁用' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="注册时间" width="170" prop="createTime" />
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button v-if="row.status === 1" size="small" type="danger" @click="handleStatus(row, 0)">禁用</el-button>
          <el-button v-else size="small" type="success" @click="handleStatus(row, 1)">启用</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination v-if="total > 0" v-model:current-page="page" :total="total" :page-size="10" layout="total, prev, pager, next" style="margin-top: 20px; justify-content: center" @current-change="loadData" />
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { adminApi } from '@/api'

const loading = ref(false)
const list = ref([])
const page = ref(1)
const total = ref(0)
const filters = reactive({ role: '', keyword: '' })

onMounted(() => loadData())

const loadData = async () => {
  loading.value = true
  try {
    const res = await adminApi.getUsers({ page: page.value, size: 10, ...filters })
    list.value = res.data?.records || []
    total.value = res.data?.total || 0
  } finally { loading.value = false }
}

const handleStatus = async (row, status) => {
  await adminApi.updateUserStatus(row.id, status)
  ElMessage.success('操作成功')
  loadData()
}
</script>

<style scoped>
.users-page h2 { margin-bottom: 20px; }
.filter-bar { display: flex; gap: 12px; margin-bottom: 20px; }
</style>
