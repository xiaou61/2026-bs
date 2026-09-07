<template>
  <el-card>
    <div style="display:flex;gap:10px;margin-bottom:15px">
      <el-input v-model="query.username" placeholder="搜索用户名" style="width:200px" clearable />
      <el-select v-model="query.role" placeholder="角色" style="width:140px" clearable>
        <el-option label="管理员" value="admin" /><el-option label="运维人员" value="operator" /><el-option label="普通用户" value="user" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
    </div>
    <el-table :data="tableData" v-loading="loading" stripe>
      <el-table-column prop="id" label="ID" width="70" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="realName" label="真实姓名" />
      <el-table-column prop="phone" label="手机号" />
      <el-table-column prop="role" label="角色"><template #default="{ row }"><el-tag>{{ roleMap[row.role] }}</el-tag></template></el-table-column>
      <el-table-column prop="creditScore" label="信用分" width="80" />
      <el-table-column prop="balance" label="余额" width="90" />
      <el-table-column prop="depositPaid" label="押金" width="80"><template #default="{ row }"><el-tag :type="row.depositPaid ? 'success' : 'info'" size="small">{{ row.depositPaid ? '已缴' : '未缴' }}</el-tag></template></el-table-column>
      <el-table-column label="状态" width="80"><template #default="{ row }"><el-tag :type="row.status === 1 ? 'success' : 'danger'" size="small">{{ row.status === 1 ? '启用' : '禁用' }}</el-tag></template></el-table-column>
      <el-table-column label="操作" width="100"><template #default="{ row }"><el-switch :model-value="row.status === 1" @change="val => handleStatus(row.id, val ? 1 : 0)" /></template></el-table-column>
    </el-table>
    <el-pagination style="margin-top:15px;justify-content:end" v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" @current-change="loadData" />
  </el-card>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getUserList, updateUserStatus } from '../../api'

const roleMap = { admin: '管理员', operator: '运维人员', user: '普通用户' }
const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const query = reactive({ pageNum: 1, pageSize: 10, username: '', role: '' })

const loadData = async () => {
  loading.value = true
  try {
    const res = await getUserList(query)
    tableData.value = res.data.list
    total.value = res.data.total
  } finally { loading.value = false }
}

const handleStatus = async (id, status) => {
  await updateUserStatus(id, { status })
  ElMessage.success('操作成功')
  loadData()
}

onMounted(loadData)
</script>
