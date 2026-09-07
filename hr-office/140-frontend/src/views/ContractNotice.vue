<template>
  <DataPage title="合同通知" description="维护待签提醒、审批通知、归档通知等消息，支撑合同流程节点通知分发" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getContractNoticePage, addContractNotice, updateContractNotice, deleteContractNotice, processContractNotice, finishContractNotice } from '../api'

const api = { page: getContractNoticePage, add: addContractNotice, update: updateContractNotice, delete: deleteContractNotice }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'LEGAL', 'APPROVER'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [
  { prop: 'noticeNo', label: '通知编号' },
  { prop: 'noticeTitle', label: '通知标题', width: 220 },
  { prop: 'noticeType', label: '通知类型' },
  { prop: 'receiverName', label: '接收人' },
  { prop: 'publishTime', label: '发布时间', width: 180 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'noticeNo', label: '通知编号' },
  { prop: 'noticeTitle', label: '通知标题' },
  { prop: 'noticeType', label: '通知类型', type: 'select', options: [{ label: '待签提醒', value: '待签提醒' }, { label: '审批提醒', value: '审批提醒' }, { label: '用印通知', value: '用印通知' }, { label: '归档通知', value: '归档通知' }] },
  { prop: 'receiverName', label: '接收人' },
  { prop: 'publishTime', label: '发布时间' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '草稿', value: 'DRAFT' }, { label: '处理中', value: 'PROCESSING' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [{ command: 'process', label: '发布处理', type: 'warning' }, { command: 'finish', label: '完成', type: 'success' }] : [])
const defaults = { noticeType: '待签提醒', status: 'DRAFT' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') {
    await processContractNotice(row.id)
  }
  if (command === 'finish') {
    await finishContractNotice(row.id)
  }
  ElMessage.success('操作成功')
  refresh()
}
</script>
