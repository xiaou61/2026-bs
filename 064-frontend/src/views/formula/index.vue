<template>
  <el-card>
    <div class="bar">
      <el-input v-model="query.name" placeholder="经方名称" style="width: 220px" clearable />
      <el-select v-model="query.status" placeholder="状态" style="width: 120px" clearable>
        <el-option label="启用" :value="1" />
        <el-option label="停用" :value="0" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
      <el-button v-if="isAdmin" type="success" @click="openAdd">新增经方</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading">
      <el-table-column prop="name" label="经方名称" min-width="160" />
      <el-table-column prop="source" label="出处" min-width="160" show-overflow-tooltip />
      <el-table-column prop="indication" label="主治" min-width="220" show-overflow-tooltip />
      <el-table-column prop="composition" label="组成" min-width="220" show-overflow-tooltip />
      <el-table-column prop="usageMethod" label="用法" min-width="180" show-overflow-tooltip />
      <el-table-column label="状态" width="90">
        <template #default="{ row }"><el-tag :type="row.status === 1 ? 'success' : 'info'">{{ row.status === 1 ? '启用' : '停用' }}</el-tag></template>
      </el-table-column>
      <el-table-column label="操作" width="220">
        <template #default="{ row }">
          <el-button v-if="isAdmin" link @click="openEdit(row)">编辑</el-button>
          <el-popconfirm v-if="isAdmin" title="确认删除？" @confirm="handleDelete(row.id)">
            <template #reference><el-button link type="danger">删除</el-button></template>
          </el-popconfirm>
          <el-button v-if="isUser" link type="primary" @click="handleFavorite(row.id)">收藏</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 12px" @current-change="loadData" />
  </el-card>

  <el-dialog v-model="dialogVisible" :title="form.id ? '编辑经方' : '新增经方'" width="700px">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
      <el-form-item label="名称" prop="name"><el-input v-model="form.name" maxlength="120" /></el-form-item>
      <el-form-item label="出处"><el-input v-model="form.source" maxlength="255" /></el-form-item>
      <el-form-item label="主治"><el-input v-model="form.indication" maxlength="500" /></el-form-item>
      <el-form-item label="组成"><el-input v-model="form.composition" type="textarea" :rows="3" maxlength="2000" /></el-form-item>
      <el-form-item label="用法"><el-input v-model="form.usageMethod" type="textarea" :rows="3" maxlength="2000" /></el-form-item>
      <el-form-item label="状态"><el-switch v-model="form.status" :active-value="1" :inactive-value="0" /></el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitForm">保存</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { addFormula, deleteFormula, getFormulaPage, toggleFavorite, updateFormula } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isAdmin = computed(() => userStore.user?.role === 'ADMIN')
const isUser = computed(() => userStore.user?.role === 'USER')
const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const query = reactive({ pageNum: 1, pageSize: 10, name: '', status: null })
const form = reactive({})

const rules = {
  name: [{ required: true, message: '请输入经方名称', trigger: 'blur' }]
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getFormulaPage(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const openAdd = () => {
  Object.assign(form, { id: null, name: '', source: '', indication: '', composition: '', usageMethod: '', status: 1 })
  dialogVisible.value = true
}

const openEdit = (row) => {
  Object.assign(form, row)
  dialogVisible.value = true
}

const submitForm = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateFormula(form)
  } else {
    await addFormula(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteFormula(id)
  ElMessage.success('删除成功')
  loadData()
}

const handleFavorite = async (targetId) => {
  await toggleFavorite({ targetType: 'FORMULA', targetId })
  ElMessage.success('收藏状态已更新')
}

onMounted(loadData)
</script>

<style scoped>
.bar {
  display: flex;
  gap: 10px;
  margin-bottom: 12px;
}
</style>
