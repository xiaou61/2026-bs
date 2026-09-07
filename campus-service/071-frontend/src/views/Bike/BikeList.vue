<template>
  <el-card>
    <div style="display:flex;gap:10px;margin-bottom:15px">
      <el-input v-model="query.bikeNo" placeholder="车辆编号" style="width:180px" clearable />
      <el-select v-model="query.type" placeholder="类型" style="width:130px" clearable><el-option label="普通单车" :value="1" /><el-option label="电动单车" :value="2" /></el-select>
      <el-select v-model="query.status" placeholder="状态" style="width:130px" clearable><el-option label="可用" :value="1" /><el-option label="使用中" :value="2" /><el-option label="维修中" :value="3" /><el-option label="报废" :value="4" /></el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
      <el-button type="success" @click="handleAdd">新增</el-button>
    </div>
    <el-table :data="tableData" v-loading="loading" stripe>
      <el-table-column prop="id" label="ID" width="70" />
      <el-table-column prop="bikeNo" label="车辆编号" />
      <el-table-column label="类型" width="100"><template #default="{ row }"><el-tag :type="row.type === 1 ? '' : 'success'">{{ row.type === 1 ? '普通单车' : '电动单车' }}</el-tag></template></el-table-column>
      <el-table-column label="状态" width="90"><template #default="{ row }"><el-tag :type="statusType[row.status]" size="small">{{ statusMap[row.status] }}</el-tag></template></el-table-column>
      <el-table-column prop="stationName" label="所在站点" />
      <el-table-column prop="batteryLevel" label="电量" width="80"><template #default="{ row }"><span v-if="row.type === 2">{{ row.batteryLevel }}%</span><span v-else>-</span></template></el-table-column>
      <el-table-column label="操作" width="200">
        <template #default="{ row }">
          <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
          <el-button link type="warning" @click="handleDispatch(row)">调度</el-button>
          <el-popconfirm title="确认删除？" @confirm="handleDelete(row.id)"><template #reference><el-button link type="danger">删除</el-button></template></el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination style="margin-top:15px;justify-content:end" v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" @current-change="loadData" />
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑单车' : '新增单车'" width="500px">
      <el-form :model="form" label-width="90px">
        <el-form-item label="车辆编号"><el-input v-model="form.bikeNo" /></el-form-item>
        <el-form-item label="类型"><el-select v-model="form.type" style="width:100%"><el-option label="普通单车" :value="1" /><el-option label="电动单车" :value="2" /></el-select></el-form-item>
        <el-form-item label="所在站点"><el-select v-model="form.stationId" style="width:100%" clearable filterable><el-option v-for="s in stations" :key="s.id" :label="s.name" :value="s.id" /></el-select></el-form-item>
        <el-form-item v-if="form.type === 2" label="电量"><el-input-number v-model="form.batteryLevel" :min="0" :max="100" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="handleSubmit">确定</el-button></template>
    </el-dialog>
    <el-dialog v-model="dispatchVisible" title="调度单车" width="400px">
      <el-form label-width="90px">
        <el-form-item label="目标站点"><el-select v-model="dispatchTarget" style="width:100%" filterable><el-option v-for="s in stations" :key="s.id" :label="s.name" :value="s.id" /></el-select></el-form-item>
      </el-form>
      <template #footer><el-button @click="dispatchVisible = false">取消</el-button><el-button type="primary" @click="submitDispatch">确定</el-button></template>
    </el-dialog>
  </el-card>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getBikeList, addBike, updateBike, deleteBike, dispatchBike, getAllStations } from '../../api'

const statusMap = { 1: '可用', 2: '使用中', 3: '维修中', 4: '报废' }
const statusType = { 1: 'success', 2: 'primary', 3: 'warning', 4: 'danger' }
const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const stations = ref([])
const dialogVisible = ref(false)
const dispatchVisible = ref(false)
const dispatchBikeId = ref(null)
const dispatchTarget = ref(null)
const query = reactive({ pageNum: 1, pageSize: 10, bikeNo: '', type: null, status: null })
const form = reactive({ id: null, bikeNo: '', type: 1, stationId: null, batteryLevel: 100 })

const loadData = async () => {
  loading.value = true
  try { const res = await getBikeList(query); tableData.value = res.data.list; total.value = res.data.total } finally { loading.value = false }
}

const handleAdd = () => { Object.assign(form, { id: null, bikeNo: '', type: 1, stationId: null, batteryLevel: 100 }); dialogVisible.value = true }
const handleEdit = (row) => { Object.assign(form, row); dialogVisible.value = true }

const handleSubmit = async () => {
  if (form.id) { await updateBike(form.id, form) } else { await addBike(form) }
  ElMessage.success('操作成功'); dialogVisible.value = false; loadData()
}

const handleDelete = async (id) => { await deleteBike(id); ElMessage.success('删除成功'); loadData() }
const handleDispatch = (row) => { dispatchBikeId.value = row.id; dispatchTarget.value = null; dispatchVisible.value = true }
const submitDispatch = async () => { await dispatchBike({ bikeId: dispatchBikeId.value, targetStationId: dispatchTarget.value }); ElMessage.success('调度成功'); dispatchVisible.value = false; loadData() }

onMounted(async () => { loadData(); const res = await getAllStations(); stations.value = res.data })
</script>
