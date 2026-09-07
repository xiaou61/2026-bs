<template>
  <DataPage title="维修工单" description="工单编号、设备名称、故障类型、报修人、处理结果和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getMaintenanceWorkOrderPage, addMaintenanceWorkOrder, updateMaintenanceWorkOrder, deleteMaintenanceWorkOrder, submitMaintenanceWorkOrder, approveMaintenanceWorkOrder } from '../api'

const api = { page: getMaintenanceWorkOrderPage, add: addMaintenanceWorkOrder, update: updateMaintenanceWorkOrder, delete: deleteMaintenanceWorkOrder }
const columns = [
  { prop: 'workOrderNo', label: '工单编号' },
  { prop: 'equipmentName', label: '设备名称' },
  { prop: 'faultType', label: '故障类型' },
  { prop: 'reporterName', label: '报修人' },
  { prop: 'resultInfo', label: '处理结果' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'workOrderNo', label: '工单编号' },
  { prop: 'equipmentName', label: '设备名称' },
  { prop: 'faultType', label: '故障类型' },
  { prop: 'reporterName', label: '报修人' },
  { prop: 'resultInfo', label: '处理结果', type: 'textarea' },
  {
    prop: 'status',
    label: '状态',
    type: 'select',
    options: [
      { label: '待提交', value: 'OPEN' },
      { label: '已提交', value: 'SUBMITTED' },
      { label: '已结单', value: 'APPROVED' }
    ]
  }
]
const rowActions = [
  { command: 'submit', label: '提交', type: 'primary' },
  { command: 'approve', label: '结单', type: 'success' }
]
const defaults = { status: 'OPEN' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitMaintenanceWorkOrder(row.id)
  if (command === 'approve') await approveMaintenanceWorkOrder(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
