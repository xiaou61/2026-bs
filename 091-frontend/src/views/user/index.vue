<template>
  <div>
    <el-card>
      <div class="toolbar">
        <el-input v-model="query.username" placeholder="用户名/会员昵称" clearable style="width: 180px" />
        <el-input v-model="query.phone" placeholder="手机号" clearable style="width: 180px" />
        <el-select v-model="query.role" placeholder="角色" clearable style="width: 140px">
          <el-option label="管理员" value="ADMIN" />
          <el-option label="门店员工" value="STAFF" />
          <el-option label="会员" value="MEMBER" />
        </el-select>
        <el-select v-model="query.status" placeholder="状态" clearable style="width: 120px">
          <el-option :value="1" label="启用" />
          <el-option :value="0" label="禁用" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增账号</el-button>
      </div>
      <el-table :data="tableData" border v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="memberNo" label="会员编号" width="170" />
        <el-table-column prop="username" label="用户名" />
        <el-table-column prop="nickname" label="昵称" />
        <el-table-column prop="phone" label="手机号" />
        <el-table-column label="角色" width="110">
          <template #default="{ row }">
            <el-tag>{{ roleText(row.role) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="memberLevel" label="等级" width="110" />
        <el-table-column prop="points" label="积分" width="100" />
        <el-table-column prop="totalRecharge" label="累计储值" width="120" />
        <el-table-column prop="totalConsume" label="累计消费" width="120" />
        <el-table-column prop="balance" label="余额" width="120" />
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '启用' : '禁用' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="260">
          <template #default="{ row }">
            <el-button link @click="handleEdit(row)">编辑</el-button>
            <el-button link type="warning" @click="toggleStatus(row)">{{ row.status === 1 ? '禁用' : '启用' }}</el-button>
            <el-popconfirm title="确认删除？" @confirm="handleDelete(row.id)">
              <template #reference>
                <el-button link type="danger">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination class="mt16" v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" @current-change="loadData" />
    </el-card>

    <el-dialog v-model="dialogVisible" title="账号信息" width="620px">
      <el-form :model="form" label-width="90px">
        <el-form-item label="用户名"><el-input v-model="form.username" /></el-form-item>
        <el-form-item label="密码"><el-input v-model="form.password" type="password" show-password /></el-form-item>
        <el-form-item label="昵称"><el-input v-model="form.nickname" /></el-form-item>
        <el-form-item label="手机号"><el-input v-model="form.phone" /></el-form-item>
        <el-form-item label="邮箱"><el-input v-model="form.email" /></el-form-item>
        <el-form-item label="角色">
          <el-select v-model="form.role" style="width: 100%" @change="handleRoleChange">
            <el-option label="管理员" value="ADMIN" />
            <el-option label="门店员工" value="STAFF" />
            <el-option label="会员" value="MEMBER" />
          </el-select>
        </el-form-item>
        <el-form-item label="会员等级">
          <el-select v-model="form.memberLevel" style="width: 100%">
            <el-option label="SILVER" value="SILVER" />
            <el-option label="GOLD" value="GOLD" />
            <el-option label="DIAMOND" value="DIAMOND" />
            <el-option label="STAFF" value="STAFF" />
            <el-option label="ADMIN" value="ADMIN" />
          </el-select>
        </el-form-item>
        <el-form-item label="余额"><el-input-number v-model="form.balance" :min="0" :precision="2" style="width: 100%" /></el-form-item>
        <el-form-item label="积分"><el-input-number v-model="form.points" :min="0" style="width: 100%" /></el-form-item>
        <el-form-item label="累计储值"><el-input-number v-model="form.totalRecharge" :min="0" :precision="2" style="width: 100%" /></el-form-item>
        <el-form-item label="累计消费"><el-input-number v-model="form.totalConsume" :min="0" :precision="2" style="width: 100%" /></el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { reactive, ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { deleteUser, getUserList, saveUser, updateUserStatus } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)

const query = reactive({
  pageNum: 1,
  pageSize: 10,
  username: '',
  phone: '',
  role: '',
  status: null
})

const form = reactive({
  id: null,
  memberNo: '',
  username: '',
  password: '',
  nickname: '',
  phone: '',
  email: '',
  role: 'MEMBER',
  memberLevel: 'SILVER',
  balance: 0,
  points: 0,
  totalRecharge: 0,
  totalConsume: 0,
  status: 1
})

const loadData = async () => {
  loading.value = true
  try {
    const res = await getUserList(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  Object.assign(form, { id: null, memberNo: '', username: '', password: '', nickname: '', phone: '', email: '', role: 'MEMBER', memberLevel: 'SILVER', balance: 0, points: 0, totalRecharge: 0, totalConsume: 0, status: 1 })
  dialogVisible.value = true
}

const handleEdit = row => {
  Object.assign(form, { ...row, password: '' })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await saveUser(form)
  ElMessage.success('保存成功')
  dialogVisible.value = false
  loadData()
}

const toggleStatus = async row => {
  await updateUserStatus(row.id, row.status === 1 ? 0 : 1)
  ElMessage.success('状态已更新')
  loadData()
}

const handleDelete = async id => {
  await deleteUser(id)
  ElMessage.success('删除成功')
  loadData()
}

const handleRoleChange = value => {
  form.memberLevel = value === 'MEMBER' ? 'SILVER' : value
}

const roleText = value => ({
  ADMIN: '管理员',
  STAFF: '门店员工',
  MEMBER: '会员'
}[value] || value)

onMounted(loadData)
</script>

<style scoped>
.toolbar {
  display: flex;
  gap: 10px;
  margin-bottom: 16px;
}

.mt16 {
  margin-top: 16px;
}
</style>
