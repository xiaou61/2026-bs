<template>
  <div class="admin-users">
    <el-card>
      <template #header>
        <span>用户管理</span>
      </template>

      <el-table :data="users" border>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="username" label="用户名" width="120" />
        <el-table-column prop="nickname" label="昵称" width="120" />
        <el-table-column prop="phone" label="手机号" width="130" />
        <el-table-column prop="school" label="学校" width="150" />
        <el-table-column label="认证状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getAuthStatusType(row.authStatus)">
              {{ getAuthStatusText(row.authStatus) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="creditScore" label="信用分" width="100" />
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">
              {{ row.status === 1 ? '正常' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="注册时间" width="180" />
        <el-table-column label="操作" fixed="right" width="150">
          <template #default="{ row }">
            <el-button v-if="row.status === 1" type="danger" size="small" @click="handleUpdateStatus(row.id, 0)">
              禁用
            </el-button>
            <el-button v-else type="success" size="small" @click="handleUpdateStatus(row.id, 1)">
              启用
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="page"
        v-model:page-size="size"
        :total="total"
        layout="total, prev, pager, next"
        @current-change="loadUsers"
        style="margin-top: 20px; justify-content: center;"
      />
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getUserList, updateUserStatus } from '@/api/admin'
import { ElMessage } from 'element-plus'

const page = ref(1)
const size = ref(10)
const total = ref(0)
const users = ref([])

const getAuthStatusText = (status) => {
  const map = {
    0: '未认证',
    1: '审核中',
    2: '已认证',
    3: '未通过'
  }
  return map[status] || '未知'
}

const getAuthStatusType = (status) => {
  const map = {
    0: 'info',
    1: 'warning',
    2: 'success',
    3: 'danger'
  }
  return map[status] || ''
}

const loadUsers = async () => {
  try {
    const res = await getUserList({ page: page.value, size: size.value })
    users.value = res.data.records
    total.value = res.data.total
  } catch (error) {
    console.error(error)
  }
}

const handleUpdateStatus = async (id, status) => {
  try {
    await updateUserStatus(id, { status })
    ElMessage.success('操作成功')
    loadUsers()
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadUsers()
})
</script>

<style scoped>
</style>

