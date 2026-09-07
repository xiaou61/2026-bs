<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-button type="primary" @click="handleAdd(0)">新增一级分类</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" row-key="id" :tree-props="{ children: 'children' }">
        <el-table-column prop="name" label="分类名称" />
        <el-table-column prop="icon" label="图标" />
        <el-table-column prop="sort" label="排序" width="100" />
        <el-table-column label="操作" width="240">
          <template #default="{ row }">
            <el-button v-if="row.parentId === 0" link type="primary" @click="handleAdd(row.id)">新增子分类</el-button>
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确定删除吗？" @confirm="handleDelete(row.id)">
              <template #reference><el-button link type="danger">删除</el-button></template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑分类' : '新增分类'" width="400px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="分类名称" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="图标">
          <el-input v-model="form.icon" />
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="form.sort" :min="0" />
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
import { ElMessage } from 'element-plus'
import { getCategoryTree, addCategory, updateCategory, deleteCategory } from '../../api'

const loading = ref(false)
const tableData = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const form = reactive({ id: null, parentId: 0, name: '', icon: '', sort: 0 })
const rules = { name: [{ required: true, message: '请输入分类名称', trigger: 'blur' }] }

const loadData = async () => {
  loading.value = true
  try {
    const res = await getCategoryTree()
    tableData.value = res.data
  } finally {
    loading.value = false
  }
}

const handleAdd = (parentId) => {
  Object.assign(form, { id: null, parentId, name: '', icon: '', sort: 0 })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateCategory(form)
  } else {
    await addCategory(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteCategory(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { margin-bottom: 15px; }
</style>
