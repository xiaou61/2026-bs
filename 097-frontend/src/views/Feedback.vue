<template>
  <div>
    <DataPage
      ref="pageRef"
      title="反馈管理"
      description="提交 Prompt 使用反馈，并由评审员维护处理状态和回复内容。"
      :api="api"
      :filters="filters"
      :columns="columns"
      :form-fields="formFields"
      :defaults="{ priority: '中', status: 0 }"
      :row-actions="rowActions"
      dialog-width="720px"
      @row-action="openReply"
    />
    <el-dialog v-model="dialogVisible" title="处理反馈" width="620px">
      <el-form :model="replyForm" label-width="100px">
        <el-form-item label="处理状态">
          <el-select v-model="replyForm.status" style="width: 100%">
            <el-option label="待处理" :value="0" />
            <el-option label="处理中" :value="1" />
            <el-option label="已关闭" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item label="回复内容">
          <el-input v-model="replyForm.replyContent" type="textarea" :rows="5" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitReply">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { addFeedback, getAssetPage, getFeedbackPage, replyFeedback, updateFeedback } from '../api'

const pageRef = ref()
const assets = ref([])
const dialogVisible = ref(false)
const replyForm = reactive({ id: undefined, status: 1, replyContent: '' })
const priorityOptions = [
  { label: '低', value: '低' },
  { label: '中', value: '中' },
  { label: '高', value: '高' }
]
const statusOptions = [
  { label: '待处理', value: 0 },
  { label: '处理中', value: 1 },
  { label: '已关闭', value: 2 }
]
const priorityMap = {
  低: { label: '低', type: 'info' },
  中: { label: '中', type: 'warning' },
  高: { label: '高', type: 'danger' }
}
const statusMap = {
  0: { label: '待处理', type: 'info' },
  1: { label: '处理中', type: 'warning' },
  2: { label: '已关闭', type: 'success' }
}
const api = { page: getFeedbackPage, add: addFeedback, update: updateFeedback }
const assetOptions = computed(() => assets.value.map((item) => ({ label: item.title, value: item.id })))
const filters = computed(() => [
  { prop: 'assetId', label: '资产', type: 'select', options: assetOptions.value },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions },
  { prop: 'priority', label: '优先级', type: 'select', options: priorityOptions }
])
const columns = [
  { prop: 'id', label: 'ID', width: 80 },
  { prop: 'assetId', label: '资产ID' },
  { prop: 'userId', label: '用户ID' },
  { prop: 'content', label: '反馈内容', minWidth: 260, long: true },
  { prop: 'priority', label: '优先级', map: priorityMap },
  { prop: 'status', label: '状态', map: statusMap },
  { prop: 'replyContent', label: '回复', minWidth: 220, long: true }
]
const formFields = computed(() => [
  { prop: 'assetId', label: 'Prompt资产', type: 'select', required: true, options: assetOptions.value },
  { prop: 'content', label: '反馈内容', type: 'textarea', rows: 5, required: true },
  { prop: 'priority', label: '优先级', type: 'select', options: priorityOptions }
])
const rowActions = [{ name: 'reply', label: '处理', type: 'success' }]

const openReply = (name, row) => {
  replyForm.id = row.id
  replyForm.status = row.status ?? 1
  replyForm.replyContent = row.replyContent || ''
  dialogVisible.value = true
}

const submitReply = async () => {
  await replyFeedback(replyForm)
  ElMessage.success('处理成功')
  dialogVisible.value = false
  pageRef.value.loadData()
}

const loadOptions = async () => {
  const res = await getAssetPage({ pageNum: 1, pageSize: 100, status: 1 })
  assets.value = res.data.records || []
}

onMounted(loadOptions)
</script>
