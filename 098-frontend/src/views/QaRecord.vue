<template>
  <div class="page-shell">
    <div class="page-title">
      <div>
        <h2>问答记录</h2>
        <p>提交知识库问题，查看模拟答案、命中来源、置信度和解决状态。</p>
      </div>
      <el-button type="primary" @click="openAsk">提问</el-button>
    </div>
    <DataPage
      ref="pageRef"
      title="记录列表"
      description="按会话、关键词和解决状态筛选问答记录。"
      :api="{ page: getRecordPage }"
      :filters="filters"
      :columns="columns"
      :row-actions="rowActions"
      readonly
      @row-action="handleAction"
    />
    <el-dialog v-model="dialogVisible" title="提交问题" width="660px">
      <el-form :model="askForm" label-width="96px">
        <el-form-item label="会话">
          <el-select v-model="askForm.sessionId" style="width: 100%">
            <el-option v-for="item in sessionOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="问题">
          <el-input v-model="askForm.question" type="textarea" :rows="5" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitAsk">提交</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { askQuestion, getRecordPage, getSessionPage, resolveRecord } from '../api'

const pageRef = ref()
const sessions = ref([])
const dialogVisible = ref(false)
const askForm = reactive({ sessionId: undefined, question: '' })
const resolvedOptions = [
  { label: '未解决', value: 0 },
  { label: '已解决', value: 1 }
]
const statusOptions = [
  { label: '有效', value: 1 },
  { label: '无效', value: 0 }
]
const resolvedMap = {
  0: { label: '未解决', type: 'warning' },
  1: { label: '已解决', type: 'success' }
}
const statusMap = {
  1: { label: '有效', type: 'success' },
  0: { label: '无效', type: 'danger' }
}
const sessionOptions = computed(() => sessions.value.map((item) => ({ label: item.title, value: item.id })))
const filters = computed(() => [
  { prop: 'keyword', label: '关键词', type: 'input', placeholder: '问题或答案' },
  { prop: 'sessionId', label: '会话', type: 'select', options: sessionOptions.value },
  { prop: 'resolved', label: '解决状态', type: 'select', options: resolvedOptions },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions }
])
const columns = [
  { prop: 'id', label: 'ID', width: 80 },
  { prop: 'sessionId', label: '会话ID' },
  { prop: 'question', label: '问题', minWidth: 230, long: true },
  { prop: 'answer', label: '答案', minWidth: 300, long: true },
  { prop: 'sourceChunkIds', label: '来源分段' },
  { prop: 'confidence', label: '置信度' },
  { prop: 'resolved', label: '解决', map: resolvedMap },
  { prop: 'status', label: '状态', map: statusMap }
]
const rowActions = [
  { name: 'resolved', label: '已解决', type: 'success' },
  { name: 'unresolved', label: '未解决', type: 'warning' }
]

const openAsk = () => {
  askForm.sessionId = sessions.value[0]?.id
  askForm.question = ''
  dialogVisible.value = true
}

const submitAsk = async () => {
  await askQuestion(askForm)
  ElMessage.success('提问成功')
  dialogVisible.value = false
  pageRef.value.loadData()
}

const handleAction = async (name, row) => {
  await resolveRecord(row.id, name === 'resolved' ? 1 : 0)
  ElMessage.success('状态已更新')
  pageRef.value.loadData()
}

const loadOptions = async () => {
  const res = await getSessionPage({ pageNum: 1, pageSize: 100, status: 0 })
  sessions.value = res.data.list || []
}

onMounted(loadOptions)
</script>
