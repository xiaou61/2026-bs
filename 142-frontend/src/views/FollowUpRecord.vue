<template>
  <DataPage title="回访记录" description="维护回访编号、回访方式、回访内容和客户反馈，形成理赔完成后的服务跟踪与满意度记录" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getFollowUpRecordPage, addFollowUpRecord, updateFollowUpRecord, deleteFollowUpRecord, processFollowUpRecord, finishFollowUpRecord } from '../api'

const api = { page: getFollowUpRecordPage, add: addFollowUpRecord, update: updateFollowUpRecord, delete: deleteFollowUpRecord }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'LEGAL'].includes(role.value))
const canWorkflow = computed(() => ['ADMIN', 'LEGAL', 'APPROVER'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [
  { prop: 'followupNo', label: '回访编号' },
  { prop: 'reportNo', label: '报案编号' },
  { prop: 'followupMethod', label: '回访方式' },
  { prop: 'followupContent', label: '回访内容', width: 220 },
  { prop: 'customerFeedback', label: '客户反馈', width: 220 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'followupNo', label: '回访编号' },
  { prop: 'reportNo', label: '报案编号' },
  { prop: 'followupMethod', label: '回访方式' },
  { prop: 'followupContent', label: '回访内容', type: 'textarea' },
  { prop: 'customerFeedback', label: '客户反馈', type: 'textarea' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '草稿', value: 'DRAFT' }, { label: '处理中', value: 'PROCESSING' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canWorkflow.value ? [{ command: 'process', label: '回访中', type: 'warning' }, { command: 'finish', label: '回访完成', type: 'success' }] : [])
const defaults = { status: 'DRAFT' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') {
    await processFollowUpRecord(row.id)
  }
  if (command === 'finish') {
    await finishFollowUpRecord(row.id)
  }
  ElMessage.success('操作成功')
  refresh()
}
</script>
