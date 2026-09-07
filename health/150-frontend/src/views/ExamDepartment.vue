<template>
  <DataPage title="检查科室" description="科室编号、科室名称、检查设备、开放时间、候诊区域和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canManage" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { activateExamDepartment, addExamDepartment, deleteExamDepartment, disableExamDepartment, getExamDepartmentPage, updateExamDepartment } from '../api'
import { useUserStore } from '../store/user'

const userStore = useUserStore()
const canManage = computed(() => ['ADMIN', 'TECHNICIAN'].includes(userStore.user?.role))
const rowActions = computed(() => canManage.value
  ? [
      { command: 'activate', label: '启用', type: 'success' },
      { command: 'disable', label: '停用', type: 'warning' }
    ]
  : [])
const api = { page: getExamDepartmentPage, add: addExamDepartment, update: updateExamDepartment, delete: deleteExamDepartment }
const columns = [
  { prop: 'departmentNo', label: '科室编号' },
  { prop: 'departmentName', label: '科室名称' },
  { prop: 'deviceName', label: '检查设备' },
  { prop: 'openTime', label: '开放时间' },
  { prop: 'waitingArea', label: '候诊区域' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'departmentNo', label: '科室编号' },
  { prop: 'departmentName', label: '科室名称' },
  { prop: 'deviceName', label: '检查设备' },
  { prop: 'openTime', label: '开放时间' },
  { prop: 'waitingArea', label: '候诊区域' },
  {
    prop: 'status',
    label: '状态',
    type: 'select',
    options: [
      { label: '启用中', value: 'ACTIVE' },
      { label: '停用', value: 'DISABLED' }
    ]
  }
]
const defaults = { status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateExamDepartment(row.id)
  if (command === 'disable') await disableExamDepartment(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
