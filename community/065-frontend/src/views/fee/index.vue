<template>
  <el-card>
    <div class="bar">
      <el-input v-model="query.orderNo" placeholder="账单号" style="width: 200px" clearable />
      <el-input v-model="query.feeMonth" placeholder="缴费月份(如2026-02)" style="width: 180px" clearable />
      <el-select v-if="isStaffRole" v-model="query.houseId" placeholder="房屋" style="width: 220px" clearable>
        <el-option v-for="item in houses" :key="item.id" :label="houseText(item)" :value="item.id" />
      </el-select>
      <el-select v-if="isStaffRole" v-model="query.ownerId" placeholder="业主" style="width: 160px" clearable>
        <el-option v-for="item in owners" :key="item.id" :label="item.nickname || item.username" :value="item.id" />
      </el-select>
      <el-select v-model="query.status" placeholder="状态" style="width: 120px" clearable>
        <el-option label="待缴费" :value="0" />
        <el-option label="已缴费" :value="1" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
      <el-button v-if="isStaffRole" type="success" @click="openAdd">新增账单</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading">
      <el-table-column prop="orderNo" label="账单号" min-width="190" />
      <el-table-column prop="houseName" label="房屋" min-width="180" />
      <el-table-column prop="ownerName" label="业主" width="120" />
      <el-table-column prop="amount" label="金额" width="100" />
      <el-table-column prop="feeMonth" label="月份" width="110" />
      <el-table-column label="状态" width="100">
        <template #default="{ row }"><el-tag :type="row.status === 1 ? 'success' : 'warning'">{{ row.status === 1 ? '已缴费' : '待缴费' }}</el-tag></template>
      </el-table-column>
      <el-table-column prop="payTime" label="缴费时间" width="180" />
      <el-table-column prop="remark" label="备注" min-width="140" show-overflow-tooltip />
      <el-table-column v-if="isStaffRole" prop="creatorName" label="创建人" width="120" />
      <el-table-column label="操作" width="260">
        <template #default="{ row }">
          <el-button v-if="row.status === 0" link type="primary" @click="handlePay(row.id)">缴费</el-button>
          <el-button v-if="isStaffRole" link :disabled="row.status === 1" @click="openEdit(row)">编辑</el-button>
          <el-popconfirm v-if="isStaffRole" title="确认删除？" @confirm="handleDelete(row.id)">
            <template #reference><el-button link type="danger" :disabled="row.status === 1">删除</el-button></template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 12px" @current-change="loadData" />
  </el-card>

  <el-dialog v-model="dialogVisible" :title="form.id ? '编辑账单' : '新增账单'" width="580px">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="95px">
      <el-form-item label="房屋" prop="houseId">
        <el-select v-model="form.houseId" style="width: 100%">
          <el-option v-for="item in houses" :key="item.id" :label="houseText(item)" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="业主">
        <el-select v-model="form.ownerId" clearable style="width: 100%">
          <el-option v-for="item in owners" :key="item.id" :label="item.nickname || item.username" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="金额" prop="amount"><el-input-number v-model="form.amount" :min="0.01" :precision="2" style="width: 100%" /></el-form-item>
      <el-form-item label="缴费月份" prop="feeMonth"><el-input v-model="form.feeMonth" maxlength="20" placeholder="例如 2026-02" /></el-form-item>
      <el-form-item label="备注"><el-input v-model="form.remark" maxlength="255" /></el-form-item>
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
import { addFee, deleteFee, getFeePage, getHouseList, getMyFeePage, getOwnerList, payFee, updateFee } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isAdmin = computed(() => userStore.user?.role === 'ADMIN')
const isStaff = computed(() => userStore.user?.role === 'STAFF')
const isStaffRole = computed(() => isAdmin.value || isStaff.value)
const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const houses = ref([])
const owners = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const query = reactive({ pageNum: 1, pageSize: 10, orderNo: '', feeMonth: '', houseId: null, ownerId: null, status: null })
const form = reactive({})

const rules = {
  houseId: [{ required: true, message: '请选择房屋', trigger: 'change' }],
  amount: [{ required: true, message: '请输入金额', trigger: 'blur' }],
  feeMonth: [{ required: true, message: '请输入缴费月份', trigger: 'blur' }]
}

const houseText = (h) => `${h.buildingName ? `${h.buildingName}-` : ''}${h.unitNo}-${h.roomNo}`

const loadBase = async () => {
  const tasks = [getHouseList()]
  if (isStaffRole.value) {
    tasks.push(getOwnerList())
  }
  const res = await Promise.all(tasks)
  houses.value = res[0].data || []
  if (isStaffRole.value) {
    owners.value = res[1].data || []
  }
}

const loadData = async () => {
  loading.value = true
  try {
    const res = isStaffRole.value ? await getFeePage(query) : await getMyFeePage(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const openAdd = async () => {
  await loadBase()
  Object.assign(form, { id: null, houseId: null, ownerId: null, amount: 1, feeMonth: '', remark: '' })
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
    await updateFee(form)
  } else {
    await addFee(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handlePay = async (id) => {
  await payFee(id)
  ElMessage.success('缴费成功')
  loadData()
}

const handleDelete = async (id) => {
  await deleteFee(id)
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
  flex-wrap: wrap;
}
</style>
