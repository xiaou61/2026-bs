<template>
  <DataPage title="使用登记" description="登记编号、设备名称、使用场景、登记时间、指导教师和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getUsageRegistrationPage, addUsageRegistration, updateUsageRegistration, deleteUsageRegistration, processUsageRegistration, finishUsageRegistration } from '../api'

const api = { page: getUsageRegistrationPage, add: addUsageRegistration, update: updateUsageRegistration, delete: deleteUsageRegistration }
const columns = [
  { prop: 'registrationNo', label: '登记编号' },
  { prop: 'equipmentName', label: '设备名称' },
  { prop: 'usageScene', label: '使用场景' },
  { prop: 'registrationTime', label: '登记时间' },
  { prop: 'teacherName', label: '指导教师' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'registrationNo', label: '登记编号' },
  { prop: 'equipmentName', label: '设备名称' },
  { prop: 'usageScene', label: '使用场景' },
  { prop: 'registrationTime', label: '登记时间' },
  { prop: 'teacherName', label: '指导教师' },
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
  { command: 'process', label: '处理', type: 'warning' },
  { command: 'finish', label: '完成', type: 'success' }
]
const defaults = { status: 'OPEN' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processUsageRegistration(row.id)
  if (command === 'finish') await finishUsageRegistration(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
