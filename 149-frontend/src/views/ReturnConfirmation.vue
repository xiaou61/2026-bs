<template>
  <DataPage title="归还确认" description="确认编号、设备名称、归还人、归还状态、确认时间和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getReturnConfirmationPage, addReturnConfirmation, updateReturnConfirmation, deleteReturnConfirmation, processReturnConfirmation, finishReturnConfirmation } from '../api'

const api = { page: getReturnConfirmationPage, add: addReturnConfirmation, update: updateReturnConfirmation, delete: deleteReturnConfirmation }
const columns = [
  { prop: 'confirmNo', label: '确认编号' },
  { prop: 'equipmentName', label: '设备名称' },
  { prop: 'returnerName', label: '归还人' },
  { prop: 'returnStatus', label: '归还状态' },
  { prop: 'confirmTime', label: '确认时间' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'confirmNo', label: '确认编号' },
  { prop: 'equipmentName', label: '设备名称' },
  { prop: 'returnerName', label: '归还人' },
  { prop: 'returnStatus', label: '归还状态' },
  { prop: 'confirmTime', label: '确认时间' },
  {
    prop: 'status',
    label: '状态',
    type: 'select',
    options: [
      { label: '待处理', value: 'OPEN' },
      { label: '处理中', value: 'PROCESSING' },
      { label: '已完成', value: 'FINISHED' }
    ]
  }
]
const rowActions = [
  { command: 'process', label: '确认', type: 'warning' },
  { command: 'finish', label: '归档', type: 'success' }
]
const defaults = { returnStatus: '待管理员确认', status: 'OPEN' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processReturnConfirmation(row.id)
  if (command === 'finish') await finishReturnConfirmation(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
