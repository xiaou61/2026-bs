<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>我的订阅</span>
          <el-button type="success" @click="handleAdd">新增订阅</el-button>
        </div>
      </template>
      <el-empty v-if="list.length === 0" description="暂无订阅" />
      <el-table :data="list" v-loading="loading" stripe v-else>
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="productId" label="产品ID" width="80" />
        <el-table-column prop="type" label="类型" width="80">
          <template #default="{ row }">{{ { DAILY: '日订', WEEKLY: '周订', MONTHLY: '月订' }[row.type] }}</template>
        </el-table-column>
        <el-table-column prop="quantity" label="数量" width="60" />
        <el-table-column prop="deliveryTime" label="时段" width="80">
          <template #default="{ row }">{{ row.deliveryTime === 'MORNING' ? '上午' : '下午' }}</template>
        </el-table-column>
        <el-table-column prop="startDate" label="开始" width="110" />
        <el-table-column prop="endDate" label="结束" width="110" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : row.status === 0 ? 'warning' : 'info'">{{ { 0: '暂停', 1: '生效', 2: '已结束' }[row.status] }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button link type="warning" @click="handlePause(row.id)" v-if="row.status === 1">暂停</el-button>
            <el-button link type="success" @click="handleResume(row.id)" v-if="row.status === 0">恢复</el-button>
            <el-popconfirm title="确认取消？" @confirm="handleCancel(row.id)" v-if="row.status !== 2">
              <template #reference><el-button link type="danger">取消</el-button></template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <el-dialog v-model="dialogVisible" title="新增订阅" width="500px">
      <el-form :model="form" ref="formRef" label-width="80px" :rules="rules">
        <el-form-item label="产品" prop="productId">
          <el-select v-model="form.productId" style="width: 100%;">
            <el-option v-for="p in products" :key="p.id" :label="p.name + ' ¥' + p.price" :value="p.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="地址" prop="addressId">
          <el-select v-model="form.addressId" style="width: 100%;">
            <el-option v-for="a in addresses" :key="a.id" :label="a.province + a.city + a.district + a.detail" :value="a.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select v-model="form.type" style="width: 100%;">
            <el-option label="日订" value="DAILY" />
            <el-option label="周订" value="WEEKLY" />
            <el-option label="月订" value="MONTHLY" />
          </el-select>
        </el-form-item>
        <el-form-item label="数量"><el-input-number v-model="form.quantity" :min="1" /></el-form-item>
        <el-form-item label="时段">
          <el-select v-model="form.deliveryTime" style="width: 100%;">
            <el-option label="上午" value="MORNING" />
            <el-option label="下午" value="AFTERNOON" />
          </el-select>
        </el-form-item>
        <el-form-item label="配送日" v-if="form.type === 'WEEKLY'"><el-input v-model="form.weekDays" placeholder="如: 1,3,5" /></el-form-item>
        <el-form-item label="开始日期" prop="startDate"><el-date-picker v-model="form.startDate" type="date" value-format="YYYY-MM-DD" style="width: 100%;" /></el-form-item>
        <el-form-item label="结束日期" prop="endDate"><el-date-picker v-model="form.endDate" type="date" value-format="YYYY-MM-DD" style="width: 100%;" /></el-form-item>
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
import { getMySubscriptions, addSubscription, pauseSubscription, resumeSubscription, deleteSubscription, getProductList, getAddressList } from '../../api'

const loading = ref(false)
const list = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const products = ref([])
const addresses = ref([])
const form = reactive({ productId: null, addressId: null, type: 'DAILY', quantity: 1, deliveryTime: 'MORNING', weekDays: '', startDate: '', endDate: '' })
const rules = {
  productId: [{ required: true, message: '请选择产品', trigger: 'change' }],
  addressId: [{ required: true, message: '请选择地址', trigger: 'change' }],
  type: [{ required: true, message: '请选择类型', trigger: 'change' }],
  startDate: [{ required: true, message: '请选择开始日期', trigger: 'change' }],
  endDate: [{ required: true, message: '请选择结束日期', trigger: 'change' }]
}

const loadData = async () => {
  loading.value = true
  try { const res = await getMySubscriptions(); list.value = res.data } finally { loading.value = false }
}

const handleAdd = () => {
  Object.assign(form, { productId: null, addressId: null, type: 'DAILY', quantity: 1, deliveryTime: 'MORNING', weekDays: '', startDate: '', endDate: '' })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  await addSubscription(form)
  ElMessage.success('订阅成功')
  dialogVisible.value = false
  loadData()
}

const handlePause = async (id) => { await pauseSubscription(id); ElMessage.success('已暂停'); loadData() }
const handleResume = async (id) => { await resumeSubscription(id); ElMessage.success('已恢复'); loadData() }
const handleCancel = async (id) => { await deleteSubscription(id); ElMessage.success('已取消'); loadData() }

onMounted(async () => {
  loadData()
  const pRes = await getProductList({})
  products.value = pRes.data
  const aRes = await getAddressList()
  addresses.value = aRes.data
})
</script>

<style scoped>
.page-container { padding: 10px; }
</style>
