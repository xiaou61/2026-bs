<template>
  <DataPage title="检查报告" description="报告编号、患者姓名、检查结论、完成时间、检查技师和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canManage" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { addExamReport, deleteExamReport, finishExamReport, getExamReportPage, processExamReport, updateExamReport } from '../api'
import { useUserStore } from '../store/user'

const userStore = useUserStore()
const canManage = computed(() => ['ADMIN', 'TECHNICIAN'].includes(userStore.user?.role))
const rowActions = computed(() => canManage.value
  ? [
      { command: 'process', label: '处理中', type: 'warning' },
      { command: 'finish', label: '完成报告', type: 'success' }
    ]
  : [])
const api = { page: getExamReportPage, add: addExamReport, update: updateExamReport, delete: deleteExamReport }
const columns = [
  { prop: 'reportNo', label: '报告编号' },
  { prop: 'patientName', label: '患者姓名' },
  { prop: 'conclusion', label: '检查结论', width: 220 },
  { prop: 'completedTime', label: '完成时间' },
  { prop: 'technicianName', label: '检查技师' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'reportNo', label: '报告编号' },
  { prop: 'patientName', label: '患者姓名' },
  { prop: 'conclusion', label: '检查结论', type: 'textarea' },
  { prop: 'completedTime', label: '完成时间' },
  { prop: 'technicianName', label: '检查技师' },
  {
    prop: 'status',
    label: '状态',
    type: 'select',
    options: [
      { label: '处理中', value: 'PROCESSING' },
      { label: '已完成', value: 'FINISHED' }
    ]
  }
]
const defaults = { status: 'PROCESSING' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processExamReport(row.id)
  if (command === 'finish') await finishExamReport(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
