<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.status" placeholder="工单状态" style="width: 160px" clearable>
          <el-option label="待受理" :value="0" />
          <el-option label="已派单" :value="1" />
          <el-option label="上门中" :value="2" />
          <el-option label="待支付" :value="3" />
          <el-option label="已完成" :value="4" />
          <el-option label="已取消" :value="5" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column prop="orderNo" label="工单号" width="180" />
        <el-table-column prop="contactName" label="联系人" width="100" />
        <el-table-column prop="contactPhone" label="电话" width="130" />
        <el-table-column prop="address" label="地址" min-width="220" show-overflow-tooltip />
        <el-table-column prop="faultDesc" label="故障描述" min-width="220" show-overflow-tooltip />
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="statusType(row.status)">{{ statusText(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="240" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="openStatus(row)">改状态</el-button>
            <el-button link type="success" @click="openProcess(row)">更新进度</el-button>
            <el-button link @click="viewProcess(row)">查看进度</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="query.pageNum"
        v-model:page-size="query.pageSize"
        :total="total"
        @current-change="loadData"
        layout="total, prev, pager, next"
        style="margin-top: 20px; justify-content: flex-end"
      />
    </el-card>

    <el-dialog v-model="statusVisible" title="更新工单状态" width="460px">
      <el-form label-width="90px">
        <el-form-item label="工单号"><el-input :model-value="statusForm.orderNo" disabled /></el-form-item>
        <el-form-item label="状态">
          <el-select v-model="statusForm.status" style="width: 100%">
            <el-option label="已派单" :value="1" />
            <el-option label="上门中" :value="2" />
            <el-option label="待支付" :value="3" />
            <el-option label="已完成" :value="4" />
          </el-select>
        </el-form-item>
        <el-form-item label="说明"><el-input v-model="statusForm.content" type="textarea" :rows="3" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="statusVisible = false">取消</el-button>
        <el-button type="primary" @click="submitStatus">确定</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="processVisible" title="新增进度" width="520px">
      <el-form label-width="90px">
        <el-form-item label="工单号"><el-input :model-value="processForm.orderNo" disabled /></el-form-item>
        <el-form-item label="进度内容"><el-input v-model="processForm.content" type="textarea" :rows="4" /></el-form-item>
        <el-form-item label="进度图片">
          <el-upload
            action="/api/common/upload"
            :headers="uploadHeaders"
            list-type="picture-card"
            :show-file-list="true"
            :on-success="handleProcessUploadSuccess"
            :on-remove="handleProcessUploadRemove"
          >
            <el-icon><Plus /></el-icon>
          </el-upload>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="processVisible = false">取消</el-button>
        <el-button type="primary" @click="submitProcess">确定</el-button>
      </template>
    </el-dialog>

    <el-drawer v-model="timelineVisible" title="工单进度" size="40%">
      <el-timeline>
        <el-timeline-item v-for="item in processList" :key="item.id" :timestamp="item.createTime">
          <div><b>{{ item.nodeType }}</b> - {{ item.content }}</div>
          <div class="process-meta">操作人：{{ item.operatorRole || '-' }} #{{ item.operatorId || '-' }}</div>
          <div v-if="item.images" class="img-list">
            <el-image
              v-for="img in splitImages(item.images)"
              :key="img"
              :src="normalizeImage(img)"
              :preview-src-list="splitImages(item.images).map(normalizeImage)"
              style="width: 64px; height: 64px; margin-right: 8px;"
              fit="cover"
            />
          </div>
        </el-timeline-item>
      </el-timeline>
    </el-drawer>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import { getTechnicianOrderList, technicianUpdateOrderStatus, technicianAddProcess, getProcessList } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const statusVisible = ref(false)
const processVisible = ref(false)
const timelineVisible = ref(false)
const processList = ref([])

const query = reactive({
  pageNum: 1,
  pageSize: 10,
  status: null
})

const statusForm = reactive({
  orderId: null,
  orderNo: '',
  status: 2,
  content: ''
})

const processForm = reactive({
  orderId: null,
  orderNo: '',
  content: '',
  images: ''
})

const processImages = ref([])

const uploadHeaders = computed(() => ({
  Authorization: localStorage.getItem('token') || ''
}))

const statusText = (status) => ({ 0: '待受理', 1: '已派单', 2: '上门中', 3: '待支付', 4: '已完成', 5: '已取消' }[status] || '未知')
const statusType = (status) => ({ 0: 'info', 1: 'warning', 2: 'primary', 3: 'warning', 4: 'success', 5: 'danger' }[status] || 'info')

const loadData = async () => {
  loading.value = true
  try {
    const res = await getTechnicianOrderList(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const openStatus = (row) => {
  statusForm.orderId = row.id
  statusForm.orderNo = row.orderNo
  statusForm.status = row.status || 2
  statusForm.content = ''
  statusVisible.value = true
}

const submitStatus = async () => {
  await technicianUpdateOrderStatus({
    orderId: statusForm.orderId,
    status: statusForm.status,
    content: statusForm.content
  })
  ElMessage.success('状态更新成功')
  statusVisible.value = false
  loadData()
}

const openProcess = (row) => {
  processForm.orderId = row.id
  processForm.orderNo = row.orderNo
  processForm.content = ''
  processImages.value = []
  processForm.images = ''
  processVisible.value = true
}

const handleProcessUploadSuccess = (res) => {
  if (res.code === 200 && res.data?.url) {
    processImages.value.push(res.data.url)
    processForm.images = processImages.value.join(',')
  }
}

const handleProcessUploadRemove = (file) => {
  const url = file?.response?.data?.url || file?.url
  processImages.value = processImages.value.filter(item => item !== url)
  processForm.images = processImages.value.join(',')
}

const submitProcess = async () => {
  await technicianAddProcess({
    orderId: processForm.orderId,
    content: processForm.content,
    images: processForm.images
  })
  ElMessage.success('进度更新成功')
  processVisible.value = false
}

const viewProcess = async (row) => {
  const res = await getProcessList(row.id)
  processList.value = res.data || []
  timelineVisible.value = true
}

const splitImages = (images) => {
  if (!images) return []
  return images.split(',').filter(Boolean)
}

const normalizeImage = (url) => {
  if (!url) return ''
  if (url.startsWith('http://') || url.startsWith('https://')) return url
  return `http://localhost:8080${url}`
}

onMounted(loadData)
</script>

<style scoped>
.page-container {
  padding: 10px;
}

.search-bar {
  display: flex;
  gap: 10px;
  margin-bottom: 15px;
}

.process-meta {
  color: #909399;
  margin-top: 4px;
  font-size: 12px;
}

.img-list {
  margin-top: 8px;
}
</style>
