<template>
  <div>
    <el-card>
      <div style="display:flex;gap:10px;margin-bottom:15px">
        <el-input v-model="query.username" placeholder="搜索用户名" style="width:200px" clearable />
        <el-button type="primary" @click="loadData">查询</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="username" label="用户名" />
        <el-table-column prop="nickname" label="昵称" />
        <el-table-column prop="phone" label="手机号" />
        <el-table-column prop="email" label="邮箱" />
        <el-table-column prop="role" label="角色">
          <template #default="{ row }"><el-tag :type="row.role === 'admin' ? 'danger' : 'success'">{{ row.role === 'admin' ? '管理员' : '用户' }}</el-tag></template>
        </el-table-column>
        <el-table-column prop="status" label="状态">
          <template #default="{ row }"><el-switch :model-value="row.status === 1" @change="val => handleStatus(row, val)" /></template>
        </el-table-column>
        <el-table-column label="操作" width="120">
          <template #default="{ row }">
            <el-popconfirm title="确认删除?" @confirm="handleDelete(row.id)">
              <template #reference><el-button link type="danger">删除</el-button></template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination style="margin-top:15px;justify-content:flex-end" v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total,prev,pager,next" @current-change="loadData" />
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getUserPage, updateUserStatus, deleteUser } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const query = reactive({ pageNum: 1, pageSize: 10, username: '' })

const loadData = async () => {
  loading.value = true
  try {
    const res = await getUserPage(query)
    tableData.value = res.data.list
    total.value = res.data.total
  } finally { loading.value = false }
}

const handleStatus = async (row, val) => {
  await updateUserStatus({ id: row.id, status: val ? 1 : 0 })
  ElMessage.success('操作成功')
  loadData()
}

const handleDelete = async (id) => {
  await deleteUser(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(loadData)
</script>
