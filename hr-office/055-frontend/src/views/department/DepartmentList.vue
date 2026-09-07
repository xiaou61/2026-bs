<template>
  <div class="page-container">
    <el-card>
      <div class="toolbar">
        <el-button type="primary" @click="handleAdd">新增部门</el-button>
      </div>
      <el-table :data="list" v-loading="loading" row-key="id" default-expand-all>
        <el-table-column prop="name" label="部门名称" width="200" />
        <el-table-column prop="leaderName" label="负责人" width="150" />
        <el-table-column prop="sort" label="排序" width="100" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '正常' : '禁用' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleAddChild(row)">新增子部门</el-button>
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-button link type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑部门' : '新增部门'" width="500px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="上级部门">
          <el-tree-select v-model="form.parentId" :data="deptTree" :props="{ label: 'name', value: 'id' }" check-strictly clearable placeholder="选择上级部门" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="部门名称" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="负责人">
          <el-select v-model="form.leaderId" placeholder="选择负责人" clearable style="width: 100%;">
            <el-option v-for="u in userList" :key="u.id" :label="u.realName" :value="u.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="form.sort" :min="0" />
        </el-form-item>
        <el-form-item label="状态">
          <el-switch v-model="form.status" :active-value="1" :inactive-value="0" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getDeptTree, addDept, updateDept, deleteDept, getAllUsers } from '../../api'

const loading = ref(false)
const list = ref([])
const deptTree = ref([])
const userList = ref([])
const dialogVisible = ref(false)
const formRef = ref()

const form = reactive({
  id: null,
  name: '',
  parentId: 0,
  leaderId: null,
  sort: 0,
  status: 1
})

const rules = {
  name: [{ required: true, message: '请输入部门名称', trigger: 'blur' }]
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getDeptTree()
    list.value = res.data
    deptTree.value = [{ id: 0, name: '顶级部门', children: res.data }]
  } finally {
    loading.value = false
  }
}

const loadUsers = async () => {
  const res = await getAllUsers()
  userList.value = res.data
}

const resetForm = () => {
  Object.assign(form, {
    id: null,
    name: '',
    parentId: 0,
    leaderId: null,
    sort: 0,
    status: 1
  })
}

const handleAdd = () => {
  resetForm()
  dialogVisible.value = true
}

const handleAddChild = (row) => {
  resetForm()
  form.parentId = row.id
  dialogVisible.value = true
}

const handleEdit = (row) => {
  resetForm()
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateDept(form)
    ElMessage.success('修改成功')
  } else {
    await addDept(form)
    ElMessage.success('添加成功')
  }
  dialogVisible.value = false
  loadData()
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定删除该部门吗？', '提示', { type: 'warning' }).then(async () => {
    await deleteDept(row.id)
    ElMessage.success('删除成功')
    loadData()
  })
}

onMounted(() => {
  loadData()
  loadUsers()
})
</script>

<style scoped>
.page-container {
  padding: 10px;
}
.toolbar {
  margin-bottom: 15px;
}
</style>
