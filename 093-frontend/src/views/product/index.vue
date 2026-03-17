<template>
  <div class="page-container">
    <el-card v-if="isManager">
      <div class="search-bar">
        <el-input v-model="query.name" placeholder="商品名称" clearable />
        <el-select v-model="query.categoryId" placeholder="分类" clearable>
          <el-option v-for="item in categoryOptions" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
        <el-select v-model="query.status" placeholder="状态" clearable>
          <el-option label="启用" :value="1" />
          <el-option label="禁用" :value="0" />
        </el-select>
        <el-button type="primary" @click="loadManagerData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增商品</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="productNo" label="商品编号" min-width="150" />
        <el-table-column prop="name" label="商品名称" min-width="160" />
        <el-table-column prop="categoryName" label="分类" min-width="120" />
        <el-table-column prop="brand" label="品牌" min-width="120" />
        <el-table-column prop="spec" label="规格" min-width="100" />
        <el-table-column prop="salePrice" label="售价" min-width="90" />
        <el-table-column prop="stockWarn" label="预警值" min-width="90" />
        <el-table-column prop="status" label="状态" min-width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '启用' : '禁用' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确认删除吗？" @confirm="handleDelete(row.id)">
              <template #reference>
                <el-button link type="danger">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-row v-else :gutter="16">
      <el-col v-for="item in publicList" :key="item.id" :span="8">
        <el-card class="product-card" shadow="hover">
          <h3>{{ item.name }}</h3>
          <p>分类：{{ item.categoryName || '-' }}</p>
          <p>规格：{{ item.spec || '-' }}</p>
          <p>售价：¥{{ Number(item.salePrice || 0).toFixed(2) }}</p>
        </el-card>
      </el-col>
    </el-row>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑商品' : '新增商品'" width="640px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="90px">
        <el-form-item label="商品编号" prop="productNo">
          <el-input v-model="form.productNo" />
        </el-form-item>
        <el-form-item label="商品名称" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="所属分类" prop="categoryId">
          <el-select v-model="form.categoryId" style="width: 100%">
            <el-option v-for="item in categoryOptions" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="品牌">
          <el-input v-model="form.brand" />
        </el-form-item>
        <el-form-item label="规格">
          <el-input v-model="form.spec" />
        </el-form-item>
        <el-form-item label="成本价">
          <el-input-number v-model="form.costPrice" :min="0" :precision="2" style="width: 100%" />
        </el-form-item>
        <el-form-item label="售价">
          <el-input-number v-model="form.salePrice" :min="0" :precision="2" style="width: 100%" />
        </el-form-item>
        <el-form-item label="预警值">
          <el-input-number v-model="form.stockWarn" :min="0" style="width: 100%" />
        </el-form-item>
        <el-form-item label="状态">
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
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { deleteProduct, getCategoryOptions, getProductList, getProductPublicList, saveProduct } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isManager = computed(() => ['ADMIN', 'STAFF'].includes((userStore.user?.role || '').toUpperCase()))

const loading = ref(false)
const tableData = ref([])
const publicList = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const categoryOptions = ref([])
const query = reactive({
  pageNum: 1,
  pageSize: 10,
  name: '',
  categoryId: null,
  status: null
})
const form = reactive({
  id: null,
  productNo: '',
  name: '',
  categoryId: null,
  brand: '',
  spec: '',
  costPrice: 0,
  salePrice: 0,
  stockWarn: 5,
  status: 1
})
const rules = {
  productNo: [{ required: true, message: '请输入商品编号', trigger: 'blur' }],
  name: [{ required: true, message: '请输入商品名称', trigger: 'blur' }],
  categoryId: [{ required: true, message: '请选择分类', trigger: 'change' }]
}

const loadOptions = async () => {
  const res = await getCategoryOptions()
  categoryOptions.value = res.data || []
}

const loadManagerData = async () => {
  loading.value = true
  try {
    const res = await getProductList(query)
    tableData.value = res.data.records || []
  } finally {
    loading.value = false
  }
}

const loadPublicData = async () => {
  const res = await getProductPublicList()
  publicList.value = res.data || []
}

const resetForm = () => {
  Object.assign(form, {
    id: null,
    productNo: '',
    name: '',
    categoryId: null,
    brand: '',
    spec: '',
    costPrice: 0,
    salePrice: 0,
    stockWarn: 5,
    status: 1
  })
}

const handleAdd = () => {
  resetForm()
  dialogVisible.value = true
}

const handleEdit = row => {
  Object.assign(form, { ...row })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  await saveProduct(form)
  ElMessage.success('保存成功')
  dialogVisible.value = false
  loadManagerData()
}

const handleDelete = async id => {
  await deleteProduct(id)
  ElMessage.success('删除成功')
  loadManagerData()
}

onMounted(async () => {
  await loadOptions()
  if (isManager.value) {
    await loadManagerData()
  } else {
    await loadPublicData()
  }
})
</script>

<style scoped>
.page-container {
  padding: 8px;
}

.search-bar {
  display: flex;
  gap: 12px;
  margin-bottom: 16px;
}

.product-card h3 {
  margin: 0 0 10px;
  color: #17324d;
}

.product-card p {
  margin: 6px 0;
  color: #667085;
}
</style>
