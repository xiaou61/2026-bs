<template>
  <DataPage
    title="复测记录"
    description="维护复测编号、复测人员、分贝值和复测时间，支撑整改后复核与结果确认"
    :api="api"
    :columns="columns"
    :form-fields="formFields"
    :row-actions="rowActions"
    :defaults="defaults"
    :can-create="canManage"
    :can-edit="canManage"
    :can-delete="canManage"
    @row-action="handleAction"
  />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getRetestRecordPage, addRetestRecord, updateRetestRecord, deleteRetestRecord, submitRetestRecord, approveRetestRecord } from '../api'

const api = { page: getRetestRecordPage, add: addRetestRecord, update: updateRetestRecord, delete: deleteRetestRecord }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'OFFICER'].includes(role.value))
const canApprove = computed(() => ['ADMIN', 'SUPERVISOR'].includes(role.value))
const columns = [
  { prop: 'retestNo', label: '复测编号' },
  { prop: 'complaintTitle', label: '投诉标题', width: 180 },
  { prop: 'retestOfficer', label: '复测人', width: 140 },
  { prop: 'noiseDbValue', label: '分贝值', width: 120 },
  { prop: 'retestTime', label: '复测时间', width: 160 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'retestNo', label: '复测编号' },
  { prop: 'complaintTitle', label: '投诉标题' },
  { prop: 'retestOfficer', label: '复测人' },
  { prop: 'noiseDbValue', label: '分贝值', type: 'number' },
  { prop: 'retestTime', label: '复测时间' },
  {
    prop: 'status',
    label: '状态',
    type: 'select',
    options: [
      { label: '待提交', value: 'OPEN' },
      { label: '已提交', value: 'SUBMITTED' },
      { label: '已确认', value: 'APPROVED' }
    ]
  }
]
const rowActions = computed(() => {
  const actions = []
  if (canManage.value) actions.push({ command: 'submit', label: '提交复测', type: 'primary' })
  if (canApprove.value) actions.push({ command: 'approve', label: '确认通过', type: 'success' })
  return actions
})
const defaults = { status: 'OPEN' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitRetestRecord(row.id)
  if (command === 'approve') await approveRetestRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
