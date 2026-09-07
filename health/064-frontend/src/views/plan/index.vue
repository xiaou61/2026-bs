<template>
  <el-card>
    <div class="bar">
      <el-input v-model="query.name" placeholder="方案名称" style="width: 200px" clearable />
      <el-input v-model="query.suitableConstitution" placeholder="适用体质" style="width: 180px" clearable />
      <el-select v-model="query.status" placeholder="状态" style="width: 120px" clearable>
        <el-option label="启用" :value="1" />
        <el-option label="停用" :value="0" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
      <el-button v-if="isAdmin" type="success" @click="openAdd">新增方案</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading">
      <el-table-column prop="name" label="方案名称" min-width="160" />
      <el-table-column prop="formulaName" label="关联经方" width="160" />
      <el-table-column prop="suitableConstitution" label="适用体质" width="140" />
      <el-table-column prop="mealTime" label="推荐时段" width="120" />
      <el-table-column prop="ingredientSummary" label="核心食材" min-width="220" show-overflow-tooltip />
      <el-table-column prop="steps" label="方案步骤" min-width="220" show-overflow-tooltip />
      <el-table-column label="状态" width="90">
        <template #default="{ row }"><el-tag :type="row.status === 1 ? 'success' : 'info'">{{ row.status === 1 ? '启用' : '停用' }}</el-tag></template>
      </el-table-column>
      <el-table-column label="操作" width="240">
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

  <el-dialog v-model="dialogVisible" :title="form.id ? '编辑方案' : '新增方案'" width="720px">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="95px">
      <el-form-item label="方案名称" prop="name"><el-input v-model="form.name" maxlength="120" /></el-form-item>
      <el-form-item label="关联经方">
        <el-select v-model="form.formulaId" style="width: 100%" clearable>
          <el-option v-for="item in formulas" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="核心食材"><el-input v-model="form.ingredientSummary" maxlength="500" /></el-form-item>
      <el-form-item label="适用体质"><el-input v-model="form.suitableConstitution" maxlength="120" /></el-form-item>
      <el-form-item label="推荐时段"><el-input v-model="form.mealTime" maxlength="60" /></el-form-item>
      <el-form-item label="方案步骤"><el-input v-model="form.steps" type="textarea" :rows="4" maxlength="2000" /></el-form-item>
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
import { addPlan, deletePlan, getFormulaList, getPlanPage, toggleFavorite, updatePlan } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isAdmin = computed(() => userStore.user?.role === 'ADMIN')
const isUser = computed(() => userStore.user?.role === 'USER')
const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const formulas = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const query = reactive({ pageNum: 1, pageSize: 10, name: '', suitableConstitution: '', status: null })
const form = reactive({})

const rules = {
  name: [{ required: true, message: '请输入方案名称', trigger: 'blur' }]
}

const loadFormula = async () => {
  const res = await getFormulaList()
  formulas.value = res.data || []
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getPlanPage(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const openAdd = async () => {
  await loadFormula()
  Object.assign(form, { id: null, name: '', formulaId: null, ingredientSummary: '', suitableConstitution: '', mealTime: '', steps: '', status: 1 })
  dialogVisible.value = true
}

const openEdit = async (row) => {
  await loadFormula()
  Object.assign(form, row)
  dialogVisible.value = true
}

const submitForm = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updatePlan(form)
  } else {
    await addPlan(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deletePlan(id)
  ElMessage.success('删除成功')
  loadData()
}

const handleFavorite = async (targetId) => {
  await toggleFavorite({ targetType: 'PLAN', targetId })
  ElMessage.success('收藏状态已更新')
}

onMounted(async () => {
  await loadFormula()
  await loadData()
})
</script>

<style scoped>
.bar {
  display: flex;
  gap: 10px;
  margin-bottom: 12px;
}
</style>
