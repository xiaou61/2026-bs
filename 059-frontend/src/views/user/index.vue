<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.username" placeholder="用户名" style="width:200px" clearable />
        <el-select v-model="query.role" placeholder="角色" clearable style="width:150px">
          <el-option label="管理员" value="admin" /><el-option label="生产经理" value="manager" />
          <el-option label="操作员" value="operator" /><el-option label="质检员" value="inspector" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="username" label="用户名" />
        <el-table-column prop="realName" label="真实姓名" />
        <el-table-column prop="phone" label="手机号" />
        <el-table-column prop="role" label="角色">
          <template #default="{row}">
            <el-tag>{{ {admin:'管理员',manager:'生产经理',operator:'操作员',inspector:'质检员'}[row.role] }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态">
          <template #default="{row}"><el-tag :type="row.status===1?'success':'danger'">{{ row.status===1?'启用':'禁用' }}</el-tag></template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template #default="{row}">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确认删除?" @confirm="handleDelete(row.id)">
              <template #reference><el-button link type="danger">删除</el-button></template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total,prev,pager,next" style="margin-top:15px" @current-change="loadData" />
    </el-card>
    <el-dialog v-model="dialogVisible" :title="form.id?'编辑用户':'新增用户'" width="500px">
      <el-form :model="form" ref="formRef" label-width="80px" :rules="formRules">
        <el-form-item label="用户名" prop="username"><el-input v-model="form.username" /></el-form-item>
        <el-form-item label="密码" prop="password" v-if="!form.id"><el-input v-model="form.password" /></el-form-item>
        <el-form-item label="真实姓名"><el-input v-model="form.realName" /></el-form-item>
        <el-form-item label="手机号"><el-input v-model="form.phone" /></el-form-item>
        <el-form-item label="角色" prop="role">
          <el-select v-model="form.role" style="width:100%">
            <el-option label="管理员" value="admin" /><el-option label="生产经理" value="manager" />
            <el-option label="操作员" value="operator" /><el-option label="质检员" value="inspector" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态"><el-switch v-model="form.status" :active-value="1" :inactive-value="0" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getUserPage, addUser, updateUser, deleteUser } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const query = reactive({ pageNum: 1, pageSize: 10, username: '', role: '' })
const form = reactive({ id: null, username: '', password: '123456', realName: '', phone: '', role: 'operator', status: 1 })
const formRules = { username: [{ required: true, message: '请输入用户名', trigger: 'blur' }], role: [{ required: true, message: '请选择角色', trigger: 'change' }] }

const loadData = async () => {
  loading.value = true
  try { const res = await getUserPage(query); tableData.value = res.data.records; total.value = res.data.total } finally { loading.value = false }
}
const handleAdd = () => { Object.assign(form, { id: null, username: '', password: '123456', realName: '', phone: '', role: 'operator', status: 1 }); dialogVisible.value = true }
const handleEdit = (row) => { Object.assign(form, row); dialogVisible.value = true }
const handleSubmit = async () => {
  await formRef.value.validate()
  if (form.id) { await updateUser(form) } else { await addUser(form) }
  ElMessage.success('操作成功'); dialogVisible.value = false; loadData()
}
const handleDelete = async (id) => { await deleteUser(id); ElMessage.success('删除成功'); loadData() }
onMounted(loadData)
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 15px; }
</style>
