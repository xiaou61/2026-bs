<template>
  <el-card>
    <template #header>
      <div class="header">
        <span>用户管理</span>
        <el-input v-model="keyword" placeholder="搜索用户" style="width: 200px" @keyup.enter="loadData" clearable />
      </div>
    </template>
    <el-table :data="users" stripe>
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="nickname" label="昵称" />
      <el-table-column prop="email" label="邮箱" />
      <el-table-column prop="phone" label="手机号" />
      <el-table-column prop="role" label="角色" width="100">
        <template #default="{ row }">
          <el-tag :type="row.role === 2 ? 'danger' : row.role === 1 ? 'warning' : 'info'">
            {{ row.role === 2 ? '管理员' : row.role === 1 ? '教师' : '学生' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态" width="100">
        <template #default="{ row }">
          <el-switch v-model="row.status" :active-value="1" :inactive-value="0" @change="updateStatus(row)" />
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="注册时间" width="180" />
    </el-table>
    <el-pagination v-model:current-page="pageNum" :page-size="10" :total="total" layout="total, prev, pager, next" @current-change="loadData" style="margin-top: 20px" />
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import api from '../api'

const users = ref([])
const total = ref(0)
const pageNum = ref(1)
const keyword = ref('')

onMounted(() => loadData())

const loadData = async () => {
  const res = await api.getUserList({ pageNum: pageNum.value, pageSize: 10, keyword: keyword.value })
  users.value = res.records
  total.value = res.total
}

const updateStatus = async row => {
  await api.updateUserStatus({ id: row.id, status: row.status })
  ElMessage.success('更新成功')
}
</script>

<style scoped>
.header { display: flex; justify-content: space-between; align-items: center; }
</style>
