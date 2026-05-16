<template>
  <DataPage title="消息通知" description="维护通知编号、通知类型、通知内容和接收人，支撑理赔补件提醒、赔付通知和案件进度触达" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getClaimNoticePage, addClaimNotice, updateClaimNotice, deleteClaimNotice, processClaimNotice, finishClaimNotice } from '../api'

const api = { page: getClaimNoticePage, add: addClaimNotice, update: updateClaimNotice, delete: deleteClaimNotice }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'LEGAL'].includes(role.value))
const canWorkflow = computed(() => ['ADMIN', 'LEGAL', 'APPROVER'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [
  { prop: 'noticeNo', label: '通知编号' },
  { prop: 'reportNo', label: '报案编号' },
  { prop: 'noticeType', label: '通知类型' },
  { prop: 'noticeContent', label: '通知内容', width: 220 },
  { prop: 'receiverName', label: '接收人' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'noticeNo', label: '通知编号' },
  { prop: 'reportNo', label: '报案编号' },
  { prop: 'noticeType', label: '通知类型' },
  { prop: 'noticeContent', label: '通知内容', type: 'textarea' },
  { prop: 'receiverName', label: '接收人' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '草稿', value: 'DRAFT' }, { label: '处理中', value: 'PROCESSING' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canWorkflow.value ? [{ command: 'process', label: '发送中', type: 'warning' }, { command: 'finish', label: '通知完成', type: 'success' }] : [])
const defaults = { status: 'DRAFT' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') {
    await processClaimNotice(row.id)
  }
  if (command === 'finish') {
    await finishClaimNotice(row.id)
  }
  ElMessage.success('操作成功')
  refresh()
}
</script>
