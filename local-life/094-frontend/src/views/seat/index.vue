<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.shopId" placeholder="门店" clearable>
          <el-option v-for="item in shopOptions" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
        <el-select v-model="query.reservationStatus" placeholder="预约状态" clearable>
          <el-option label="AVAILABLE" value="AVAILABLE" />
          <el-option label="BOOKED" value="BOOKED" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增座位</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="shopName" label="门店名称" min-width="160" />
        <el-table-column prop="seatNo" label="座位编号" min-width="120" />
        <el-table-column prop="zoneName" label="分区" min-width="120" />
        <el-table-column prop="capacity" label="容量" min-width="90" />
        <el-table-column prop="minConsume" label="最低消费" min-width="100" />
        <el-table-column prop="seatStatus" label="座位状态" min-width="100" />
        <el-table-column prop="reservationStatus" label="预约状态" min-width="100" />
        <el-table-column label="操作" width="180">
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

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑座位' : '新增座位'" width="620px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="90px">
        <el-form-item label="门店" prop="shopId">
          <el-select v-model="form.shopId" style="width: 100%">
            <el-option v-for="item in shopOptions" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="座位编号" prop="seatNo">
          <el-input v-model="form.seatNo" />
        </el-form-item>
        <el-form-item label="分区">
          <el-input v-model="form.zoneName" />
        </el-form-item>
        <el-form-item label="容量">
          <el-input-number v-model="form.capacity" :min="1" style="width: 100%" />
        </el-form-item>
        <el-form-item label="最低消费">
          <el-input-number v-model="form.minConsume" :min="0" :precision="2" style="width: 100%" />
        </el-form-item>
        <el-form-item label="座位状态">
          <el-select v-model="form.seatStatus" style="width: 100%">
            <el-option label="NORMAL" value="NORMAL" />
            <el-option label="DISABLED" value="DISABLED" />
          </el-select>
        </el-form-item>
        <el-form-item label="预约状态">
          <el-select v-model="form.reservationStatus" style="width: 100%">
            <el-option label="AVAILABLE" value="AVAILABLE" />
            <el-option label="BOOKED" value="BOOKED" />
          </el-select>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" type="textarea" />
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
import { deleteSeat, getSeatList, getShopOptions, saveSeat } from '../../api'

const loading = ref(false)
const tableData = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const shopOptions = ref([])
const query = reactive({
  pageNum: 1,
  pageSize: 20,
  shopId: null,
  reservationStatus: ''
})
const form = reactive({
  id: null,
  shopId: null,
  seatNo: '',
  zoneName: '',
  capacity: 2,
  minConsume: 68,
  seatStatus: 'NORMAL',
  reservationStatus: 'AVAILABLE',
  remark: ''
})
const rules = {
  shopId: [{ required: true, message: '请选择门店', trigger: 'change' }],
  seatNo: [{ required: true, message: '请输入座位编号', trigger: 'blur' }]
}

const loadOptions = async () => {
  const res = await getShopOptions()
  shopOptions.value = res.data || []
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getSeatList(query)
    tableData.value = res.data.records || []
  } finally {
    loading.value = false
  }
}

const resetForm = () => {
  Object.assign(form, {
    id: null,
    shopId: null,
    seatNo: '',
    zoneName: '',
    capacity: 2,
    minConsume: 68,
    seatStatus: 'NORMAL',
    reservationStatus: 'AVAILABLE',
    remark: ''
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
  await saveSeat(form)
  ElMessage.success('保存成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async id => {
  await deleteSeat(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(async () => {
  await loadOptions()
  loadData()
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
</style>
