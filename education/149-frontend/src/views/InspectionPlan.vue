<template>
  <DataPage title="巡检计划" description="巡检编号、设备名称、巡检主题、巡检内容、巡检时间和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getInspectionPlanPage, addInspectionPlan, updateInspectionPlan, deleteInspectionPlan, activateInspectionPlan, finishInspectionPlan } from '../api'
import { useUserStore } from '../store/user'

const userStore = useUserStore()
const api = { page: getInspectionPlanPage, add: addInspectionPlan, update: updateInspectionPlan, delete: deleteInspectionPlan }
const canToggle = computed(() => ['ADMIN', 'MANAGER'].includes(userStore.user?.role))
const columns = [
  { prop: 'inspectionNo', label: '巡检编号' },
  { prop: 'equipmentName', label: '设备名称' },
  { prop: 'inspectionTheme', label: '巡检主题' },
  { prop: 'inspectionContent', label: '巡检内容' },
  { prop: 'inspectionTime', label: '巡检时间' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'inspectionNo', label: '巡检编号' },
  { prop: 'equipmentName', label: '设备名称' },
  { prop: 'inspectionTheme', label: '巡检主题' },
  { prop: 'inspectionContent', label: '巡检内容', type: 'textarea' },
  { prop: 'inspectionTime', label: '巡检时间' },
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
const rowActions = computed(() => canToggle.value ? [
  { command: 'activate', label: '启用', type: 'success' },
  { command: 'finish', label: '停用', type: 'warning' }
] : [])
const defaults = { status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateInspectionPlan(row.id)
  if (command === 'finish') await finishInspectionPlan(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
