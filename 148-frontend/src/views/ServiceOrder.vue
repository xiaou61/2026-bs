<template>
  <DataPage
    title="服务工单"
    description="维护工单编号、服务主题、服务地址、上门时间和派单人，支撑家属申请与顾问派单流程"
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
import { getServiceOrderPage, addServiceOrder, updateServiceOrder, deleteServiceOrder, submitServiceOrder, approveServiceOrder } from '../api'

const api = { page: getServiceOrderPage, add: addServiceOrder, update: updateServiceOrder, delete: deleteServiceOrder }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'CONSULTANT', 'FAMILY'].includes(role.value))
const canSubmit = computed(() => ['ADMIN', 'CONSULTANT', 'FAMILY'].includes(role.value))
const canApprove = computed(() => ['ADMIN', 'CONSULTANT'].includes(role.value))
const columns = [
  { prop: 'orderNo', label: '工单编号' },
  { prop: 'serviceSubject', label: '服务主题', width: 180 },
  { prop: 'serviceAddress', label: '服务地址', width: 220 },
  { prop: 'visitTime', label: '上门时间', width: 160 },
  { prop: 'dispatcherName', label: '派单人', width: 140 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'orderNo', label: '工单编号' },
  { prop: 'serviceSubject', label: '服务主题' },
  { prop: 'serviceAddress', label: '服务地址' },
  { prop: 'visitTime', label: '上门时间' },
  { prop: 'dispatcherName', label: '派单人' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '草稿', value: 'DRAFT' }, { label: '已提交', value: 'SUBMITTED' }, { label: '已审批', value: 'APPROVED' }] }
]
const rowActions = computed(() => {
  const actions = []
  if (canSubmit.value) actions.push({ command: 'submit', label: '提交', type: 'primary' })
  if (canApprove.value) actions.push({ command: 'approve', label: '审批通过', type: 'success' })
  return actions
})
const defaults = { status: 'DRAFT' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitServiceOrder(row.id)
  if (command === 'approve') await approveServiceOrder(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
