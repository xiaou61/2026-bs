<template>
  <DataPage
    title="通知公告"
    description="维护整改通知、结果告知和站内消息"
    :api="api"
    :columns="columns"
    :form-fields="formFields"
    :row-actions="rowActions"
    :defaults="defaults"
    :can-create="canManage"
    :can-edit="canManage"
    :can-delete="canDelete"
    @row-action="handleAction"
  />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { addInspectionNotice, deleteInspectionNotice, finishInspectionNotice, getInspectionNoticePage, processInspectionNotice, updateInspectionNotice } from '../api'
import { useUserStore } from '../store/user'

const api = { page: getInspectionNoticePage, add: addInspectionNotice, update: updateInspectionNotice, delete: deleteInspectionNotice }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role)
const canManage = computed(() => ['ADMIN', 'INSPECTOR', 'REVIEWER'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const rowActions = computed(() => {
  const actions = []
  if (['ADMIN', 'INSPECTOR', 'REVIEWER'].includes(role.value)) actions.push({ command: 'process', label: '发送', type: 'success' })
  if (['ADMIN', 'INSPECTOR', 'REVIEWER'].includes(role.value)) actions.push({ command: 'finish', label: '完成', type: 'warning' })
  return actions
})
const statusOptions = [
  { label: '处理中', value: 'PROCESSING' },
  { label: '已完成', value: 'FINISHED' }
]
const columns = [
  { prop: 'noticeNo', label: '通知编号' },
  { prop: 'foodName', label: '食品名称' },
  { prop: 'noticeType', label: '通知类型' },
  { prop: 'noticeContent', label: '通知内容', width: 180 },
  { prop: 'receiverName', label: '接收人' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'noticeNo', label: '通知编号' },
  { prop: 'foodName', label: '食品名称' },
  { prop: 'noticeType', label: '通知类型' },
  { prop: 'noticeContent', label: '通知内容', type: 'textarea' },
  { prop: 'receiverName', label: '接收人' },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions }
]
const defaults = { status: 'PROCESSING' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processInspectionNotice(row.id)
  if (command === 'finish') await finishInspectionNotice(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
