<template>
  <el-card>
    <div class="bar">
      <el-input v-model="query.orderNo" placeholder="销售单号" style="width: 220px" clearable />
      <el-select v-model="query.customerId" placeholder="客户" style="width: 180px" clearable>
        <el-option v-for="item in customers" :key="item.id" :label="item.name" :value="item.id" />
      </el-select>
      <el-select v-model="query.status" placeholder="状态" style="width: 120px" clearable>
        <el-option label="待审核" :value="0" />
        <el-option label="已审核" :value="1" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
      <el-button type="success" @click="openAdd">新增销售</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading">
      <el-table-column prop="orderNo" label="销售单号" min-width="190" />
      <el-table-column prop="customerName" label="客户" width="160" />
      <el-table-column prop="productName" label="商品" width="160" />
      <el-table-column prop="quantity" label="数量" width="80" />
      <el-table-column prop="price" label="单价" width="100" />
      <el-table-column prop="amount" label="金额" width="110" />
      <el-table-column label="状态" width="100">
        <template #default="{ row }"><el-tag :type="row.status === 1 ? 'success' : 'warning'">{{ row.status === 1 ? '已审核' : '待审核' }}</el-tag></template>
      </el-table-column>
      <el-table-column prop="creatorName" label="创建人" width="120" />
      <el-table-column prop="createTime" label="创建时间" width="180" />
      <el-table-column label="操作" width="220">
        <template #default="{ row }">
          <el-button link :disabled="row.status === 1" @click="openEdit(row)">编辑</el-button>
          <el-button link type="primary" :disabled="row.status === 1" @click="handleApprove(row.id)">审核</el-button>
          <el-popconfirm title="确认删除？" @confirm="handleDelete(row)">
            <template #reference><el-button link type="danger" :disabled="row.status === 1">删除</el-button></template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 12px" @current-change="loadData" />
  </el-card>

  <el-dialog v-model="dialogVisible" :title="form.id ? '编辑销售单' : '新增销售单'" width="620px">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
      <el-form-item label="客户" prop="customerId">
        <el-select v-model="form.customerId" style="width: 100%">
          <el-option v-for="item in customers" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="商品" prop="productId">
        <el-select v-model="form.productId" style="width: 100%">
          <el-option v-for="item in products" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="数量" prop="quantity"><el-input-number v-model="form.quantity" :min="1" style="width: 100%" /></el-form-item>
      <el-form-item label="单价" prop="price"><el-input-number v-model="form.price" :min="0.01" :precision="2" style="width: 100%" /></el-form-item>
      <el-form-item label="备注"><el-input v-model="form.remark" maxlength="255" /></el-form-item>
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
import { addSale, approveSale, deleteSale, getCustomerList, getProductList, getSalePage, updateSale } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const customers = ref([])
const products = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const query = reactive({ pageNum: 1, pageSize: 10, orderNo: '', customerId: null, status: null })
const form = reactive({})

const rules = {
  customerId: [{ required: true, message: '请选择客户', trigger: 'change' }],
  productId: [{ required: true, message: '请选择商品', trigger: 'change' }],
  quantity: [{ required: true, message: '请输入数量', trigger: 'blur' }],
  price: [{ required: true, message: '请输入单价', trigger: 'blur' }]
}

const loadBase = async () => {
  const [cRes, pRes] = await Promise.all([getCustomerList(), getProductList()])
  customers.value = cRes.data || []
  products.value = pRes.data || []
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getSalePage(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const openAdd = async () => {
  await loadBase()
  Object.assign(form, { id: null, customerId: null, productId: null, quantity: 1, price: 1, remark: '' })
  dialogVisible.value = true
}

const openEdit = async (row) => {
  await loadBase()
  Object.assign(form, row)
  dialogVisible.value = true
}

const submitForm = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateSale(form)
  } else {
    await addSale(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleApprove = async (id) => {
  await approveSale(id)
  ElMessage.success('审核成功')
  loadData()
}

const handleDelete = async (row) => {
  await deleteSale(row.id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(async () => {
  await loadBase()
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
