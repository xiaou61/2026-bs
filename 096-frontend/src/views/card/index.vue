<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-button type="success" @click="handleAdd">新增就诊卡</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="cardNo" label="卡号" />
        <el-table-column prop="patientName" label="持卡人" />
        <el-table-column prop="phone" label="手机号" />
        <el-table-column prop="idCard" label="身份证号" />
        <el-table-column prop="isDefault" label="默认卡">
          <template #default="{ row }">
            <el-tag v-if="row.isDefault === 1" type="success">默认</el-tag>
            <span v-else>-</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="260">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-button link type="success" @click="handleDefault(row)" v-if="row.isDefault !== 1">设为默认</el-button>
            <el-popconfirm title="确认删除该就诊卡吗？" @confirm="handleDelete(row.id)">
              <template #reference><el-button link type="danger">删除</el-button></template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑就诊卡' : '新增就诊卡'" width="520px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="持卡人" prop="patientName"><el-input v-model="form.patientName" /></el-form-item>
        <el-form-item label="手机号" prop="phone"><el-input v-model="form.phone" /></el-form-item>
        <el-form-item label="身份证号"><el-input v-model="form.idCard" /></el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">可用</el-radio>
            <el-radio :label="0">停用</el-radio>
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
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { addCard, deleteCard, getMyCards, setDefaultCard, updateCard } from '../../api'

const loading = ref(false)
const dialogVisible = ref(false)
const formRef = ref()
const tableData = ref([])
const form = reactive({ id: null, patientName: '', phone: '', idCard: '', status: 1 })

const rules = {
  patientName: [{ required: true, message: '请输入持卡人姓名', trigger: 'blur' }],
  phone: [{ required: true, message: '请输入手机号', trigger: 'blur' }]
}

const resetForm = () => {
  Object.assign(form, { id: null, patientName: '', phone: '', idCard: '', status: 1 })
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getMyCards()
    tableData.value = res.data || []
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  resetForm()
  dialogVisible.value = true
}

const handleEdit = (row) => {
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateCard(form)
  } else {
    await addCard(form)
  }
  ElMessage.success('保存成功')
  dialogVisible.value = false
  loadData()
}

const handleDefault = async (row) => {
  await setDefaultCard(row.id)
  ElMessage.success('默认就诊卡已更新')
  loadData()
}

const handleDelete = async (id) => {
  await deleteCard(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.page-container { padding: 4px; }
.search-bar { display: flex; justify-content: flex-end; margin-bottom: 16px; }
</style>
