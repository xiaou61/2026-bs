<template>
  <div>
    <el-card>
      <div class="toolbar">
        <el-select v-model="query.trainId" placeholder="列车" clearable style="width: 180px">
          <el-option v-for="item in trainOptions" :key="item.id" :label="`${item.trainCode} ${item.trainName}`" :value="item.id" />
        </el-select>
        <el-select v-model="query.departureStationId" placeholder="出发站" clearable style="width: 160px">
          <el-option v-for="item in stationOptions" :key="item.id" :label="item.stationName" :value="item.id" />
        </el-select>
        <el-select v-model="query.arrivalStationId" placeholder="到达站" clearable style="width: 160px">
          <el-option v-for="item in stationOptions" :key="item.id" :label="item.stationName" :value="item.id" />
        </el-select>
        <el-date-picker v-model="query.travelDate" type="date" value-format="YYYY-MM-DD" placeholder="出发日期" />
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button v-if="!userStore.isUser" type="success" @click="handleAdd">新增班次</el-button>
      </div>

      <el-table :data="tableData" border v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column label="列车" min-width="100">
          <template #default="{ row }">{{ trainLabel(row.trainId) }}</template>
        </el-table-column>
        <el-table-column label="出发站" min-width="100">
          <template #default="{ row }">{{ stationLabel(row.departureStationId) }}</template>
        </el-table-column>
        <el-table-column label="到达站" min-width="100">
          <template #default="{ row }">{{ stationLabel(row.arrivalStationId) }}</template>
        </el-table-column>
        <el-table-column prop="travelDate" label="出发日期" width="120" />
        <el-table-column prop="departureTime" label="发车时间" min-width="160" />
        <el-table-column prop="arrivalTime" label="到达时间" min-width="160" />
        <el-table-column prop="basePrice" label="票价" width="100" />
        <el-table-column prop="availableSeats" label="余票" width="90" />
        <el-table-column prop="saleStatus" label="售卖状态" width="110" />
        <el-table-column v-if="!userStore.isUser" label="操作" width="160">
          <template #default="{ row }">
            <el-button link @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确认删除？" @confirm="handleDelete(row.id)">
              <template #reference><el-button link type="danger">删除</el-button></template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination class="mt16" v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" @current-change="loadData" />
    </el-card>

    <el-dialog v-model="dialogVisible" title="班次信息" width="620px">
      <el-form :model="form" label-width="110px">
        <el-form-item label="列车">
          <el-select v-model="form.trainId" style="width: 100%">
            <el-option v-for="item in trainOptions" :key="item.id" :label="`${item.trainCode} ${item.trainName}`" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="车厢模板">
          <el-select v-model="form.carriageId" style="width: 100%">
            <el-option v-for="item in carriageOptions" :key="item.id" :label="item.templateName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="出发站">
          <el-select v-model="form.departureStationId" style="width: 100%">
            <el-option v-for="item in stationOptions" :key="item.id" :label="item.stationName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="到达站">
          <el-select v-model="form.arrivalStationId" style="width: 100%">
            <el-option v-for="item in stationOptions" :key="item.id" :label="item.stationName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="出发日期"><el-date-picker v-model="form.travelDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" /></el-form-item>
        <el-form-item label="发车时间"><el-date-picker v-model="form.departureTime" type="datetime" value-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" /></el-form-item>
        <el-form-item label="到达时间"><el-date-picker v-model="form.arrivalTime" type="datetime" value-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" /></el-form-item>
        <el-form-item label="基础票价"><el-input-number v-model="form.basePrice" :min="1" :precision="2" style="width: 100%" /></el-form-item>
        <el-form-item label="售卖状态">
          <el-select v-model="form.saleStatus" style="width: 100%">
            <el-option label="售卖中" value="ON_SALE" />
            <el-option label="暂停售卖" value="PAUSED" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">停用</el-radio>
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
import { deleteSchedule, getCarriageEnabledList, getScheduleList, getStationEnabledList, getTrainEnabledList, saveSchedule } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const trainOptions = ref([])
const stationOptions = ref([])
const carriageOptions = ref([])
const query = reactive({ pageNum: 1, pageSize: 10, trainId: null, departureStationId: null, arrivalStationId: null, travelDate: '' })
const form = reactive({
  id: null,
  trainId: null,
  carriageId: null,
  departureStationId: null,
  arrivalStationId: null,
  travelDate: '',
  departureTime: '',
  arrivalTime: '',
  basePrice: 120,
  saleStatus: 'ON_SALE',
  status: 1
})

const trainMap = computed(() => Object.fromEntries(trainOptions.value.map(item => [item.id, item])))
const stationMap = computed(() => Object.fromEntries(stationOptions.value.map(item => [item.id, item])))

const trainLabel = id => trainMap.value[id] ? `${trainMap.value[id].trainCode}` : id
const stationLabel = id => stationMap.value[id]?.stationName || id

const loadOptions = async () => {
  const [a, b, c] = await Promise.all([getTrainEnabledList(), getStationEnabledList(), getCarriageEnabledList()])
  trainOptions.value = a.data || []
  stationOptions.value = b.data || []
  carriageOptions.value = c.data || []
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getScheduleList(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  Object.assign(form, {
    id: null,
    trainId: trainOptions.value[0]?.id || null,
    carriageId: carriageOptions.value[0]?.id || null,
    departureStationId: stationOptions.value[0]?.id || null,
    arrivalStationId: stationOptions.value[1]?.id || null,
    travelDate: '',
    departureTime: '',
    arrivalTime: '',
    basePrice: 120,
    saleStatus: 'ON_SALE',
    status: 1
  })
  dialogVisible.value = true
}

const handleEdit = row => {
  Object.assign(form, { ...row })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await saveSchedule(form)
  ElMessage.success('保存成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async id => {
  await deleteSchedule(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(async () => {
  await loadOptions()
  await loadData()
})
</script>

<style scoped>
.toolbar {
  display: flex;
  gap: 10px;
  margin-bottom: 16px;
  flex-wrap: wrap;
}

.mt16 {
  margin-top: 16px;
}
</style>
