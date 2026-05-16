<template>
  <DataPage title="消息通知" description="维护通知编号、用户编号、通知类型、通知内容和接收人，支撑协助过程提醒与状态同步" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canManage" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getMessageNoticePage, addMessageNotice, updateMessageNotice, deleteMessageNotice, processMessageNotice, finishMessageNotice } from '../api'

const api = { page: getMessageNoticePage, add: addMessageNotice, update: updateMessageNotice, delete: deleteMessageNotice }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'DISPATCHER'].includes(role.value))
const columns = [
  { prop: 'noticeNo', label: '通知编号' },
  { prop: 'travelerNo', label: '用户编号' },
  { prop: 'noticeType', label: '通知类型' },
  { prop: 'noticeContent', label: '通知内容', width: 220 },
  { prop: 'receiverName', label: '接收人' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'noticeNo', label: '通知编号' },
  { prop: 'travelerNo', label: '用户编号' },
  { prop: 'noticeType', label: '通知类型' },
  { prop: 'noticeContent', label: '通知内容', type: 'textarea' },
  { prop: 'receiverName', label: '接收人' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '处理中', value: 'PROCESSING' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [
  { command: 'process', label: '处理', type: 'warning' },
  { command: 'finish', label: '完成', type: 'success' }
] : [])
const defaults = { status: 'PROCESSING' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processMessageNotice(row.id)
  if (command === 'finish') await finishMessageNotice(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
