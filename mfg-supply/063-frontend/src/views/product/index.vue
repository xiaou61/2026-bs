<template>
  <el-card>
    <div class="bar">
      <el-input v-model="query.name" placeholder="商品名称" style="width: 180px" clearable />
      <el-input v-model="query.productNo" placeholder="商品编码" style="width: 200px" clearable />
      <el-select v-model="query.categoryId" placeholder="分类" style="width: 160px" clearable>
        <el-option v-for="item in categories" :key="item.id" :label="item.name" :value="item.id" />
      </el-select>
      <el-select v-model="query.status" placeholder="状态" style="width: 120px" clearable>
        <el-option label="上架" :value="1" />
        <el-option label="下架" :value="0" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
      <el-button type="success" @click="openAdd">新增</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading">
      <el-table-column prop="productNo" label="编码" width="180" />
      <el-table-column prop="name" label="名称" min-width="160" />
      <el-table-column prop="categoryName" label="分类" width="130" />
      <el-table-column prop="spec" label="规格" width="140" />
      <el-table-column prop="unit" label="单位" width="80" />
      <el-table-column prop="costPrice" label="采购价" width="100" />
      <el-table-column prop="salePrice" label="销售价" width="100" />
      <el-table-column prop="stock" label="库存" width="80" />
      <el-table-column prop="stockWarn" label="预警值" width="90" />
      <el-table-column label="状态" width="90">
        <template #default="{ row }"><el-tag :type="row.status === 1 ? 'success' : 'info'">{{ row.status === 1 ? '上架' : '下架' }}</el-tag></template>
      </el-table-column>
      <el-table-column label="操作" width="160">
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

  <el-dialog v-model="dialogVisible" :title="form.id ? '编辑商品' : '新增商品'" width="620px">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
      <el-form-item label="名称" prop="name"><el-input v-model="form.name" maxlength="100" /></el-form-item>
      <el-form-item label="分类" prop="categoryId">
        <el-select v-model="form.categoryId" style="width: 100%">
          <el-option v-for="item in categories" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="规格"><el-input v-model="form.spec" maxlength="100" /></el-form-item>
      <el-form-item label="单位"><el-input v-model="form.unit" maxlength="20" /></el-form-item>
      <el-form-item label="采购价"><el-input-number v-model="form.costPrice" :min="0" :precision="2" style="width: 100%" /></el-form-item>
      <el-form-item label="销售价"><el-input-number v-model="form.salePrice" :min="0" :precision="2" style="width: 100%" /></el-form-item>
      <el-form-item label="库存"><el-input-number v-model="form.stock" :min="0" style="width: 100%" /></el-form-item>
      <el-form-item label="预警值"><el-input-number v-model="form.stockWarn" :min="0" style="width: 100%" /></el-form-item>
      <el-form-item label="状态"><el-switch v-model="form.status" :active-value="1" :inactive-value="0" /></el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitForm">保存</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { addProduct, deleteProduct, getCategoryList, getProductPage, updateProduct } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const categories = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const query = reactive({ pageNum: 1, pageSize: 10, name: '', productNo: '', categoryId: null, status: null })
const form = reactive({})

const rules = {
  name: [{ required: true, message: '请输入商品名称', trigger: 'blur' }],
  categoryId: [{ required: true, message: '请选择分类', trigger: 'change' }]
}

const loadCategory = async () => {
  const res = await getCategoryList()
  categories.value = res.data || []
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getProductPage(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const openAdd = async () => {
  await loadCategory()
  Object.assign(form, { id: null, name: '', categoryId: null, spec: '', unit: '', costPrice: 0, salePrice: 0, stock: 0, stockWarn: 10, status: 1 })
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
    await updateProduct(form)
  } else {
    await addProduct(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteProduct(id)
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
