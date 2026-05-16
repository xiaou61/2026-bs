<template>
  <DataPage title="违规记录" description="违规编号、设备名称、处理人、违规类型、违规时间和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getViolationRecordPage, addViolationRecord, updateViolationRecord, deleteViolationRecord, submitViolationRecord, approveViolationRecord } from '../api'
import { useUserStore } from '../store/user'

const userStore = useUserStore()
const api = { page: getViolationRecordPage, add: addViolationRecord, update: updateViolationRecord, delete: deleteViolationRecord }
const canApprove = computed(() => ['ADMIN', 'MANAGER'].includes(userStore.user?.role))
const columns = [
  { prop: 'violationNo', label: '违规编号' },
  { prop: 'equipmentName', label: '设备名称' },
  { prop: 'handlerName', label: '处理人' },
  { prop: 'violationType', label: '违规类型' },
  { prop: 'violationTime', label: '违规时间' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'violationNo', label: '违规编号' },
  { prop: 'equipmentName', label: '设备名称' },
  { prop: 'handlerName', label: '处理人' },
  { prop: 'violationType', label: '违规类型' },
  { prop: 'violationTime', label: '违规时间' },
  {
    prop: 'status',
    label: '状态',
    type: 'select',
    options: [
      { label: '待上报', value: 'OPEN' },
      { label: '已提交', value: 'SUBMITTED' },
      { label: '已确认', value: 'APPROVED' }
    ]
  }
]
const rowActions = computed(() => {
  const actions = [{ command: 'submit', label: '上报', type: 'primary' }]
  if (canApprove.value) actions.push({ command: 'approve', label: '确认', type: 'success' })
  return actions
})
const defaults = { status: 'OPEN' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitViolationRecord(row.id)
  if (command === 'approve') await approveViolationRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
