<template>
  <DataPage
    title="样品信息"
    description="记录样品编号、样品类型和现场抽样备注"
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
import { addSampleRecord, approveSampleRecord, deleteSampleRecord, getSampleRecordPage, submitSampleRecord, updateSampleRecord } from '../api'
import { useUserStore } from '../store/user'

const api = { page: getSampleRecordPage, add: addSampleRecord, update: updateSampleRecord, delete: deleteSampleRecord }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role)
const canManage = computed(() => ['ADMIN', 'INSPECTOR'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const rowActions = computed(() => {
  const actions = []
  if (['ADMIN', 'INSPECTOR'].includes(role.value)) actions.push({ command: 'submit', label: '提交样品', type: 'success' })
  if (['ADMIN', 'REVIEWER'].includes(role.value)) actions.push({ command: 'approve', label: '确认入库', type: 'warning' })
  return actions
})
const statusOptions = [
  { label: '已提交', value: 'SUBMITTED' },
  { label: '已审批', value: 'APPROVED' },
  { label: '已完成', value: 'FINISHED' }
]
const columns = [
  { prop: 'sampleNo', label: '样品编号' },
  { prop: 'foodName', label: '食品名称' },
  { prop: 'sampleType', label: '样品类型' },
  { prop: 'samplingRemark', label: '抽样说明', width: 180 },
  { prop: 'recordedTime', label: '录入时间' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'sampleNo', label: '样品编号' },
  { prop: 'foodName', label: '食品名称' },
  { prop: 'sampleType', label: '样品类型' },
  { prop: 'samplingRemark', label: '抽样说明', type: 'textarea' },
  { prop: 'recordedTime', label: '录入时间' },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions }
]
const defaults = { status: 'SUBMITTED' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitSampleRecord(row.id)
  if (command === 'approve') await approveSampleRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
