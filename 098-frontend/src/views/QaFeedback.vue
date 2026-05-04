<template>
  <div>
    <DataPage
      ref="pageRef"
      title="问答反馈"
      description="提交问答质量反馈，并由编辑员处理回复。"
      :api="api"
      :filters="filters"
      :columns="columns"
      :form-fields="formFields"
      :defaults="{ status: 0, score: 5 }"
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
import { addFeedback, getFeedbackPage, getRecordPage, replyFeedback, updateFeedback } from '../api'

const pageRef = ref()
const records = ref([])
const dialogVisible = ref(false)
const replyForm = reactive({ id: undefined, status: 1, replyContent: '' })
const statusOptions = [
  { label: '待处理', value: 0 },
  { label: '处理中', value: 1 },
  { label: '已关闭', value: 2 }
]
const scoreOptions = [
  { label: '1分', value: 1 },
  { label: '2分', value: 2 },
  { label: '3分', value: 3 },
  { label: '4分', value: 4 },
  { label: '5分', value: 5 }
]
const statusMap = {
  0: { label: '待处理', type: 'info' },
  1: { label: '处理中', type: 'warning' },
  2: { label: '已关闭', type: 'success' }
}
const api = { page: getFeedbackPage, add: addFeedback, update: updateFeedback }
const recordOptions = computed(() => records.value.map((item) => ({ label: `${item.id} - ${item.question}`, value: item.id })))
const filters = computed(() => [
  { prop: 'keyword', label: '关键词', type: 'input', placeholder: '反馈或回复' },
  { prop: 'recordId', label: '记录', type: 'select', options: recordOptions.value },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions },
  { prop: 'score', label: '评分', type: 'select', options: scoreOptions }
])
const columns = [
  { prop: 'id', label: 'ID', width: 80 },
  { prop: 'recordId', label: '记录ID' },
  { prop: 'userId', label: '用户ID' },
  { prop: 'content', label: '反馈内容', minWidth: 260, long: true },
  { prop: 'score', label: '评分' },
  { prop: 'status', label: '状态', map: statusMap },
  { prop: 'replyContent', label: '回复', minWidth: 220, long: true }
]
const formFields = computed(() => [
  { prop: 'recordId', label: '问答记录', type: 'select', required: true, options: recordOptions.value },
  { prop: 'content', label: '反馈内容', type: 'textarea', rows: 5, required: true },
  { prop: 'score', label: '评分', type: 'select', options: scoreOptions }
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
  const res = await getRecordPage({ pageNum: 1, pageSize: 100, status: 1 })
  records.value = res.data.list || []
}

onMounted(loadOptions)
</script>
