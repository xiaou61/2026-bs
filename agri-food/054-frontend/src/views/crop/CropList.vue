<template>
  <div class="page-container">
    <div class="search-form">
      <el-form :inline="true" :model="queryParams">
        <el-form-item label="作物名称">
          <el-input v-model="queryParams.name" placeholder="请输入作物名称" clearable />
        </el-form-item>
        <el-form-item label="分类">
          <el-select v-model="queryParams.categoryId" placeholder="全部" clearable>
            <el-option v-for="c in categories" :key="c.id" :label="c.name" :value="c.id" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadData">查询</el-button>
          <el-button @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="table-container">
      <div style="margin-bottom: 15px">
        <el-button type="primary" @click="handleAdd">新增作物</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="作物名称" />
        <el-table-column prop="categoryName" label="分类" />
        <el-table-column prop="growthCycle" label="生长周期(天)" width="120" />
        <el-table-column prop="suitableTemp" label="适宜温度" />
        <el-table-column prop="suitableHumidity" label="适宜湿度" />
        <el-table-column prop="description" label="描述" show-overflow-tooltip />
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-button link type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination-container">
        <el-pagination
          v-model:current-page="queryParams.pageNum"
          v-model:page-size="queryParams.pageSize"
          :total="total"
          :page-sizes="[10, 20, 50]"
          layout="total, sizes, prev, pager, next"
          @change="loadData"
        />
      </div>
    </div>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="600px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="作物名称" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="分类" prop="categoryId">
          <el-select v-model="form.categoryId" style="width: 100%">
            <el-option v-for="c in categories" :key="c.id" :label="c.name" :value="c.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="生长周期(天)" prop="growthCycle">
          <el-input-number v-model="form.growthCycle" :min="1" />
        </el-form-item>
        <el-form-item label="适宜温度" prop="suitableTemp">
          <el-input v-model="form.suitableTemp" placeholder="如: 20-30℃" />
        </el-form-item>
        <el-form-item label="适宜湿度" prop="suitableHumidity">
          <el-input v-model="form.suitableHumidity" placeholder="如: 60-80%" />
        </el-form-item>
        <el-form-item label="土壤要求" prop="soilRequirement">
          <el-input v-model="form.soilRequirement" type="textarea" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" type="textarea" :rows="3" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getCropPage, addCrop, updateCrop, deleteCrop, getCategoryList } from '@/api/crop'
import { ElMessage, ElMessageBox } from 'element-plus'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const categories = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('')
const formRef = ref()

const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  name: '',
  categoryId: null
})

const form = ref({})

const rules = {
  name: [{ required: true, message: '请输入作物名称', trigger: 'blur' }],
  categoryId: [{ required: true, message: '请选择分类', trigger: 'change' }]
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getCropPage(queryParams)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const loadCategories = async () => {
  const res = await getCategoryList()
  categories.value = res.data
}

const resetQuery = () => {
  queryParams.name = ''
  queryParams.categoryId = null
  queryParams.pageNum = 1
  loadData()
}

const handleAdd = () => {
  dialogTitle.value = '新增作物'
  form.value = {}
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑作物'
  form.value = { ...row }
  dialogVisible.value = true
}

const handleDelete = async (row) => {
  await ElMessageBox.confirm('确定要删除该作物吗?', '提示', { type: 'warning' })
  await deleteCrop(row.id)
  ElMessage.success('删除成功')
  loadData()
}

const submitForm = async () => {
  await formRef.value.validate()
  if (form.value.id) {
    await updateCrop(form.value)
    ElMessage.success('修改成功')
  } else {
    await addCrop(form.value)
    ElMessage.success('新增成功')
  }
  dialogVisible.value = false
  loadData()
}

onMounted(() => {
  loadData()
  loadCategories()
})
</script>
