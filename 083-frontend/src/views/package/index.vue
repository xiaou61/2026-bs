<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.packageName" placeholder="套餐名称" clearable style="width: 220px" />
        <el-select v-model="query.status" placeholder="状态" clearable style="width: 140px">
          <el-option label="启用" :value="1" />
          <el-option label="禁用" :value="0" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="id" label="ID" width="70" />
        <el-table-column prop="packageCode" label="套餐编码" width="120" />
        <el-table-column prop="packageName" label="套餐名称" />
        <el-table-column prop="price" label="价格" width="90" />
        <el-table-column prop="suitableCrowd" label="适用人群" />
        <el-table-column prop="status" label="状态" width="90">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '启用' : '禁用' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="170">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确认删除？" @confirm="handleDelete(row.id)">
              <template #reference>
                <el-button link type="danger">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="query.pageNum"
        v-model:page-size="query.pageSize"
        :total="total"
        layout="total, prev, pager, next"
        @current-change="loadData"
      />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑套餐' : '新增套餐'" width="660px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="套餐编码" prop="packageCode">
          <el-input v-model="form.packageCode" />
        </el-form-item>
        <el-form-item label="套餐名称" prop="packageName">
          <el-input v-model="form.packageName" />
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input-number v-model="form.price" :min="0" :precision="2" />
        </el-form-item>
        <el-form-item label="适用人群" prop="suitableCrowd">
          <el-input v-model="form.suitableCrowd" />
        </el-form-item>
        <el-form-item label="套餐项目" prop="itemIds">
          <el-select v-model="form.itemIds" multiple style="width: 100%">
            <el-option v-for="item in itemOptions" :key="item.id" :label="item.itemName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="说明" prop="description">
          <el-input v-model="form.description" type="textarea" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
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
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { addPackage, deletePackage, getItemAll, getPackageList, updatePackage } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const itemOptions = ref([])

const query = reactive({
  pageNum: 1,
  pageSize: 10,
  packageName: '',
  status: null
})

const form = reactive({
  id: null,
  packageCode: '',
  packageName: '',
  price: 0,
  description: '',
  suitableCrowd: '',
  itemIds: [],
  status: 1
})

const rules = {
  packageCode: [{ required: true, message: '请输入编码', trigger: 'blur' }],
  packageName: [{ required: true, message: '请输入名称', trigger: 'blur' }]
}

const resetForm = () => {
  form.id = null
  form.packageCode = ''
  form.packageName = ''
  form.price = 0
  form.description = ''
  form.suitableCrowd = ''
  form.itemIds = []
  form.status = 1
}

const loadItems = async () => {
  const res = await getItemAll()
  itemOptions.value = res.data || []
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getPackageList(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
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
  form.itemIds = row.itemIds || []
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updatePackage(form)
  } else {
    await addPackage(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deletePackage(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(async () => {
  await loadItems()
  await loadData()
})
</script>

<style scoped>
.page-container {
  padding: 10px;
}

.search-bar {
  display: flex;
  gap: 10px;
  margin-bottom: 15px;
  flex-wrap: wrap;
}
</style>
