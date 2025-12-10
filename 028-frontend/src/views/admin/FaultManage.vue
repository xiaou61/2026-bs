<template>
  <div class="fault-manage">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>故障管理</span>
          <el-select v-model="filterStatus" placeholder="处理状态" clearable style="width: 120px" @change="loadFaults">
            <el-option label="待处理" :value="0" />
            <el-option label="处理中" :value="1" />
            <el-option label="已处理" :value="2" />
          </el-select>
        </div>
      </template>
      <el-table :data="faultList" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="bikeNo" label="车辆编号" width="120" />
        <el-table-column prop="username" label="上报用户" width="120" />
        <el-table-column prop="faultType" label="故障类型" width="100">
          <template #default="{ row }">{{ faultTypeText[row.faultType] }}</template>
        </el-table-column>
        <el-table-column prop="description" label="故障描述" show-overflow-tooltip />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="statusType[row.status]">{{ statusText[row.status] }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="上报时间" width="160" />
        <el-table-column label="操作" width="180">
          <template #default="{ row }">
            <el-button size="small" @click="handleProcess(row)" v-if="row.status !== 2">处理</el-button>
            <el-button size="small" @click="handleDetail(row)">详情</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination background layout="prev, pager, next" :total="total" v-model:current-page="currentPage" @current-change="loadFaults" class="mt-20" />
    </el-card>
    
    <el-dialog v-model="processVisible" title="处理故障">
      <el-form :model="processForm" label-width="100px">
        <el-form-item label="车辆编号">{{ processForm.bikeNo }}</el-form-item>
        <el-form-item label="故障描述">{{ processForm.description }}</el-form-item>
        <el-form-item label="处理状态">
          <el-select v-model="processForm.status">
            <el-option label="处理中" :value="1" />
            <el-option label="已处理" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item label="处理结果">
          <el-input v-model="processForm.result" type="textarea" rows="3" placeholder="请输入处理结果" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="processVisible = false">取消</el-button>
        <el-button type="primary" @click="submitProcess">确定</el-button>
      </template>
    </el-dialog>
    
    <el-dialog v-model="detailVisible" title="故障详情" width="500px">
      <el-descriptions :column="1" border>
        <el-descriptions-item label="车辆编号">{{ faultDetail.bikeNo }}</el-descriptions-item>
        <el-descriptions-item label="上报用户">{{ faultDetail.username }}</el-descriptions-item>
        <el-descriptions-item label="故障类型">{{ faultTypeText[faultDetail.faultType] }}</el-descriptions-item>
        <el-descriptions-item label="故障描述">{{ faultDetail.description }}</el-descriptions-item>
        <el-descriptions-item label="上报时间">{{ faultDetail.createTime }}</el-descriptions-item>
        <el-descriptions-item label="处理状态">
          <el-tag :type="statusType[faultDetail.status]">{{ statusText[faultDetail.status] }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="处理结果" v-if="faultDetail.result">{{ faultDetail.result }}</el-descriptions-item>
        <el-descriptions-item label="处理时间" v-if="faultDetail.handleTime">{{ faultDetail.handleTime }}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { adminApi } from '@/api'

const faultList = ref([])
const loading = ref(false)
const total = ref(0)
const currentPage = ref(1)
const filterStatus = ref('')
const processVisible = ref(false)
const detailVisible = ref(false)
const processForm = reactive({ id: null, bikeNo: '', description: '', status: 1, result: '' })
const faultDetail = reactive({})

const statusText = { 0: '待处理', 1: '处理中', 2: '已处理' }
const statusType = { 0: 'danger', 1: 'warning', 2: 'success' }
const faultTypeText = { 1: '车锁故障', 2: '轮胎问题', 3: '刹车故障', 4: '车身损坏', 5: '其他' }

const loadFaults = async () => {
  loading.value = true
  try {
    const res = await adminApi.getFaultList({ page: currentPage.value, status: filterStatus.value })
    faultList.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const handleProcess = (row) => {
  Object.assign(processForm, { id: row.id, bikeNo: row.bikeNo, description: row.description, status: 1, result: '' })
  processVisible.value = true
}

const handleDetail = (row) => {
  Object.assign(faultDetail, row)
  detailVisible.value = true
}

const submitProcess = async () => {
  await adminApi.handleFault(processForm.id, { status: processForm.status, result: processForm.result })
  ElMessage.success('处理成功')
  processVisible.value = false
  loadFaults()
}

onMounted(() => loadFaults())
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
