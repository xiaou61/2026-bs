<template>
  <el-card>
    <div style="display:flex;gap:10px;margin-bottom:15px">
      <el-select v-model="query.status" placeholder="状态" style="width:130px" clearable><el-option label="待处理" :value="0" /><el-option label="处理中" :value="1" /><el-option label="已处理" :value="2" /></el-select>
      <el-select v-model="query.type" placeholder="类型" style="width:130px" clearable><el-option label="车锁故障" :value="1" /><el-option label="轮胎破损" :value="2" /><el-option label="刹车失灵" :value="3" /><el-option label="车身损坏" :value="4" /><el-option label="其他" :value="5" /></el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
    </div>
    <el-table :data="tableData" v-loading="loading" stripe>
      <el-table-column prop="id" label="ID" width="70" />
      <el-table-column prop="username" label="上报人" width="100" />
      <el-table-column prop="bikeNo" label="车辆" width="120" />
      <el-table-column label="类型" width="100"><template #default="{ row }"><el-tag size="small">{{ faultTypeMap[row.type] }}</el-tag></template></el-table-column>
      <el-table-column prop="description" label="描述" />
      <el-table-column label="状态" width="90"><template #default="{ row }"><el-tag :type="{ 0: 'warning', 1: '', 2: 'success' }[row.status]" size="small">{{ { 0: '待处理', 1: '处理中', 2: '已处理' }[row.status] }}</el-tag></template></el-table-column>
      <el-table-column prop="handlerName" label="处理人" width="100" />
      <el-table-column prop="createTime" label="上报时间" width="160" />
      <el-table-column label="操作" width="100"><template #default="{ row }"><el-button v-if="row.status !== 2" link type="primary" @click="openHandle(row)">处理</el-button></template></el-table-column>
    </el-table>
    <el-pagination style="margin-top:15px;justify-content:end" v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" @current-change="loadData" />
    <el-dialog v-model="dialogVisible" title="处理故障" width="500px">
      <el-form label-width="90px">
        <el-form-item label="处理结果"><el-input v-model="handleForm.handleResult" type="textarea" :rows="3" /></el-form-item>
        <el-form-item label="状态"><el-select v-model="handleForm.status" style="width:100%"><el-option label="处理中" :value="1" /><el-option label="已处理" :value="2" /></el-select></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="submitHandle">确定</el-button></template>
    </el-dialog>
  </el-card>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getFaultList, handleFault } from '../../api'

const faultTypeMap = { 1: '车锁故障', 2: '轮胎破损', 3: '刹车失灵', 4: '车身损坏', 5: '其他' }
const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const currentId = ref(null)
const query = reactive({ pageNum: 1, pageSize: 10, status: null, type: null })
const handleForm = reactive({ handleResult: '', status: 2 })

const loadData = async () => { loading.value = true; try { const res = await getFaultList(query); tableData.value = res.data.list; total.value = res.data.total } finally { loading.value = false } }
const openHandle = (row) => { currentId.value = row.id; handleForm.handleResult = ''; handleForm.status = 2; dialogVisible.value = true }
const submitHandle = async () => { await handleFault(currentId.value, handleForm); ElMessage.success('处理成功'); dialogVisible.value = false; loadData() }
onMounted(loadData)
</script>
