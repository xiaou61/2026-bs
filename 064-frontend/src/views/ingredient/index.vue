<template>
  <el-card>
    <div class="bar">
      <el-input v-model="query.name" placeholder="食材名称" style="width: 180px" clearable />
      <el-select v-model="query.categoryId" placeholder="分类" style="width: 160px" clearable>
        <el-option v-for="item in categories" :key="item.id" :label="item.name" :value="item.id" />
      </el-select>
      <el-select v-model="query.status" placeholder="状态" style="width: 120px" clearable>
        <el-option label="启用" :value="1" />
        <el-option label="停用" :value="0" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
      <el-button v-if="isAdmin" type="success" @click="openAdd">新增</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading">
      <el-table-column prop="name" label="食材名称" min-width="140" />
      <el-table-column prop="categoryName" label="分类" width="120" />
      <el-table-column prop="natureTaste" label="性味" width="130" />
      <el-table-column prop="meridian" label="归经" width="130" />
      <el-table-column prop="efficacy" label="功效" min-width="220" show-overflow-tooltip />
      <el-table-column prop="suitablePeople" label="适宜人群" min-width="160" show-overflow-tooltip />
      <el-table-column prop="tabooPeople" label="禁忌人群" min-width="160" show-overflow-tooltip />
      <el-table-column label="状态" width="90">
        <template #default="{ row }"><el-tag :type="row.status === 1 ? 'success' : 'info'">{{ row.status === 1 ? '启用' : '停用' }}</el-tag></template>
      </el-table-column>
      <el-table-column v-if="isAdmin" label="操作" width="160">
        <template #default="{ row }">
          <el-button link @click="openEdit(row)">编辑</el-button>
          <el-popconfirm title="确认删除？" @confirm="handleDelete(row.id)">
            <template #reference><el-button link type="danger">删除</el-button></template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 12px" @current-change="loadData" />
  </el-card>

  <el-dialog v-model="dialogVisible" :title="form.id ? '编辑食材' : '新增食材'" width="680px">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="95px">
      <el-form-item label="名称" prop="name"><el-input v-model="form.name" maxlength="100" /></el-form-item>
      <el-form-item label="分类" prop="categoryId">
        <el-select v-model="form.categoryId" style="width: 100%">
          <el-option v-for="item in categories" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="性味"><el-input v-model="form.natureTaste" maxlength="120" /></el-form-item>
      <el-form-item label="归经"><el-input v-model="form.meridian" maxlength="120" /></el-form-item>
      <el-form-item label="功效"><el-input v-model="form.efficacy" maxlength="500" /></el-form-item>
      <el-form-item label="适宜人群"><el-input v-model="form.suitablePeople" maxlength="255" /></el-form-item>
      <el-form-item label="禁忌人群"><el-input v-model="form.tabooPeople" maxlength="255" /></el-form-item>
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
import { addIngredient, deleteIngredient, getCategoryList, getIngredientPage, updateIngredient } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isAdmin = computed(() => userStore.user?.role === 'ADMIN')
const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const categories = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const query = reactive({ pageNum: 1, pageSize: 10, name: '', categoryId: null, status: null })
const form = reactive({})

const rules = {
  name: [{ required: true, message: '请输入食材名称', trigger: 'blur' }],
  categoryId: [{ required: true, message: '请选择分类', trigger: 'change' }]
}

const loadCategory = async () => {
  const res = await getCategoryList()
  categories.value = res.data || []
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getIngredientPage(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const openAdd = async () => {
  await loadCategory()
  Object.assign(form, { id: null, name: '', categoryId: null, natureTaste: '', meridian: '', efficacy: '', suitablePeople: '', tabooPeople: '', status: 1 })
  dialogVisible.value = true
}

const openEdit = async (row) => {
  await loadCategory()
  Object.assign(form, row)
  dialogVisible.value = true
}

const submitForm = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateIngredient(form)
  } else {
    await addIngredient(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteIngredient(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(async () => {
  await loadCategory()
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
