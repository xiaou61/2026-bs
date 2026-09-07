<template>
  <DataPage title="事故报案" description="维护报案编号、事故类型、报案时间和报案人信息，为材料收集、定损和赔付处理建立案件入口" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getAccidentReportPage, addAccidentReport, updateAccidentReport, deleteAccidentReport, submitAccidentReport, approveAccidentReport } from '../api'

const api = { page: getAccidentReportPage, add: addAccidentReport, update: updateAccidentReport, delete: deleteAccidentReport }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'APPLICANT'].includes(role.value))
const canApprove = computed(() => ['ADMIN', 'LEGAL', 'APPROVER'].includes(role.value))
const canDelete = computed(() => canManage.value)
const columns = [
  { prop: 'reportNo', label: '报案编号' },
  { prop: 'caseName', label: '案件名称', width: 180 },
  { prop: 'accidentType', label: '事故类型' },
  { prop: 'reportTime', label: '报案时间', width: 160 },
  { prop: 'reporterName', label: '报案人' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'reportNo', label: '报案编号' },
  { prop: 'caseName', label: '案件名称' },
  { prop: 'accidentType', label: '事故类型' },
  { prop: 'reportTime', label: '报案时间' },
  { prop: 'reporterName', label: '报案人' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '草稿', value: 'DRAFT' }, { label: '已提交', value: 'SUBMITTED' }, { label: '已审批', value: 'APPROVED' }] }
]
const rowActions = computed(() => {
  const actions = []
  if (canManage.value) actions.push({ command: 'submit', label: '提交', type: 'warning' })
  if (canApprove.value) actions.push({ command: 'approve', label: '审批通过', type: 'success' })
  return actions
})
const defaults = { status: 'DRAFT' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') {
    await submitAccidentReport(row.id)
  }
  if (command === 'approve') {
    await approveAccidentReport(row.id)
  }
  ElMessage.success('操作成功')
  refresh()
}
</script>
