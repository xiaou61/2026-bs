<template>
  <div class="complaints-container">
    <el-card>
      <el-form :inline="true" :model="filters" class="search-form">
        <el-form-item label="状态">
          <el-select v-model="filters.status" placeholder="全部" clearable>
            <el-option label="待处理" :value="0" />
            <el-option label="已处理" :value="1" />
            <el-option label="已驳回" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadComplaints">查询</el-button>
          <el-button @click="resetFilters">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="complaints" border style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="orderNo" label="订单编号" width="180" />
        <el-table-column prop="complainantName" label="投诉人" width="120" />
        <el-table-column prop="respondentName" label="被投诉人" width="120" />
        <el-table-column prop="type" label="投诉类型" width="120" />
        <el-table-column prop="reason" label="投诉原因" min-width="200" show-overflow-tooltip />
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">{{ getStatusText(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" width="160">
          <template #default="{ row }">{{ formatTime(row.createTime) }}</template>
        </el-table-column>
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button
              v-if="row.status === 0"
              type="primary"
              size="small"
              @click="handleComplaint(row)"
            >
              处理
            </el-button>
            <el-button type="info" size="small" @click="viewDetail(row)">详情</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-if="total > 0"
        v-model:current-page="currentPage"
        :page-size="pageSize"
        :total="total"
        layout="total, prev, pager, next"
        @current-change="loadComplaints"
        class="pagination"
      />
    </el-card>

    <el-dialog v-model="showHandleDialog" title="处理投诉" width="600px">
      <el-form v-if="currentComplaint" :model="handleForm" label-width="100px">
        <el-form-item label="投诉人">
          <span>{{ currentComplaint.complainantName }}</span>
        </el-form-item>
        <el-form-item label="被投诉人">
          <span>{{ currentComplaint.respondentName }}</span>
        </el-form-item>
        <el-form-item label="投诉类型">
          <span>{{ currentComplaint.type }}</span>
        </el-form-item>
        <el-form-item label="投诉原因">
          <span>{{ currentComplaint.reason }}</span>
        </el-form-item>
        <el-form-item label="处理结果">
          <el-radio-group v-model="handleForm.action">
            <el-radio label="ACCEPT">接受投诉</el-radio>
            <el-radio label="REJECT">驳回投诉</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="处理说明">
          <el-input v-model="handleForm.result" type="textarea" :rows="4" placeholder="请输入处理说明" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showHandleDialog = false">取消</el-button>
        <el-button type="primary" :loading="loading" @click="submitHandle">确认</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="showDetailDialog" title="投诉详情" width="600px">
      <el-descriptions v-if="currentComplaint" :column="1" border>
        <el-descriptions-item label="投诉人">{{ currentComplaint.complainantName }}</el-descriptions-item>
        <el-descriptions-item label="被投诉人">{{ currentComplaint.respondentName }}</el-descriptions-item>
        <el-descriptions-item label="订单编号">{{ currentComplaint.orderNo }}</el-descriptions-item>
        <el-descriptions-item label="投诉类型">{{ currentComplaint.type }}</el-descriptions-item>
        <el-descriptions-item label="投诉原因">{{ currentComplaint.reason }}</el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag :type="getStatusType(currentComplaint.status)">{{ getStatusText(currentComplaint.status) }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item v-if="currentComplaint.handleResult" label="处理结果">
          {{ currentComplaint.handleResult }}
        </el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ formatTime(currentComplaint.createTime) }}</el-descriptions-item>
        <el-descriptions-item v-if="currentComplaint.handleTime" label="处理时间">
          {{ formatTime(currentComplaint.handleTime) }}
        </el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getComplaintList, handleComplaint as handleComplaintApi } from '../../api/admin'
import { ElMessage } from 'element-plus'

const complaints = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const showHandleDialog = ref(false)
const showDetailDialog = ref(false)
const currentComplaint = ref(null)
const loading = ref(false)

const filters = reactive({
  status: null
})

const handleForm = reactive({
  action: 'ACCEPT',
  result: ''
})

const loadComplaints = async () => {
  try {
    const data = await getComplaintList({
      pageNum: currentPage.value,
      pageSize: pageSize.value,
      ...filters
    })
    complaints.value = data.records
    total.value = data.total
  } catch (error) {
    console.error('加载投诉列表失败', error)
  }
}

const resetFilters = () => {
  filters.status = null
  loadComplaints()
}

const handleComplaint = (complaint) => {
  currentComplaint.value = complaint
  handleForm.action = 'ACCEPT'
  handleForm.result = ''
  showHandleDialog.value = true
}

const viewDetail = (complaint) => {
  currentComplaint.value = complaint
  showDetailDialog.value = true
}

const submitHandle = async () => {
  if (!handleForm.result) {
    ElMessage.warning('请输入处理说明')
    return
  }
  loading.value = true
  try {
    await handleComplaintApi(currentComplaint.value.id, handleForm)
    ElMessage.success('处理成功')
    showHandleDialog.value = false
    loadComplaints()
  } catch (error) {
    console.error('处理失败', error)
  } finally {
    loading.value = false
  }
}

const getStatusType = (status) => {
  const types = { 0: 'warning', 1: 'success', 2: 'info' }
  return types[status] || ''
}

const getStatusText = (status) => {
  const texts = { 0: '待处理', 1: '已处理', 2: '已驳回' }
  return texts[status] || '未知'
}

const formatTime = (time) => {
  if (!time) return ''
  return new Date(time).toLocaleString('zh-CN')
}

onMounted(() => {
  loadComplaints()
})
</script>

<style scoped>
.complaints-container {
  max-width: 100%;
}

.search-form {
  margin-bottom: 20px;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}
</style>

