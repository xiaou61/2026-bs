<template>
  <DataPage title="借用记录" description="借用编号、设备名称、借用人、借出时间、借用用途和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getBorrowRecordPage, addBorrowRecord, updateBorrowRecord, deleteBorrowRecord, submitBorrowRecord, approveBorrowRecord } from '../api'
import { useUserStore } from '../store/user'

const userStore = useUserStore()
const api = { page: getBorrowRecordPage, add: addBorrowRecord, update: updateBorrowRecord, delete: deleteBorrowRecord }
const canApprove = computed(() => ['ADMIN', 'TEACHER'].includes(userStore.user?.role))
const columns = [
  { prop: 'borrowNo', label: '借用编号' },
  { prop: 'equipmentName', label: '设备名称' },
  { prop: 'borrowerName', label: '借用人' },
  { prop: 'borrowTime', label: '借出时间' },
  { prop: 'borrowPurpose', label: '借用用途' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'borrowNo', label: '借用编号' },
  { prop: 'equipmentName', label: '设备名称' },
  { prop: 'borrowerName', label: '借用人' },
  { prop: 'borrowTime', label: '借出时间' },
  { prop: 'borrowPurpose', label: '借用用途', type: 'textarea' },
  {
    prop: 'status',
    label: '状态',
    type: 'select',
    options: [
      { label: '待提交', value: 'OPEN' },
      { label: '已提交', value: 'SUBMITTED' },
      { label: '已通过', value: 'APPROVED' }
    ]
  }
]
const rowActions = computed(() => {
  const actions = [{ command: 'submit', label: '提交', type: 'primary' }]
  if (canApprove.value) actions.push({ command: 'approve', label: '通过', type: 'success' })
  return actions
})
const defaults = { status: 'OPEN' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitBorrowRecord(row.id)
  if (command === 'approve') await approveBorrowRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
