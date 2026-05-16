<template>
  <DataPage
    title="上门签到"
    description="维护签到编号、老人姓名、签到类型、签到说明和签到时间，支撑上门服务到访核验"
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
import { getVisitCheckinPage, addVisitCheckin, updateVisitCheckin, deleteVisitCheckin, submitVisitCheckin, approveVisitCheckin } from '../api'

const api = { page: getVisitCheckinPage, add: addVisitCheckin, update: updateVisitCheckin, delete: deleteVisitCheckin }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'CAREGIVER'].includes(role.value))
const canSubmit = computed(() => ['ADMIN', 'CAREGIVER'].includes(role.value))
const canApprove = computed(() => ['ADMIN', 'CONSULTANT'].includes(role.value))
const columns = [
  { prop: 'checkinNo', label: '签到编号' },
  { prop: 'elderName', label: '老人姓名', width: 140 },
  { prop: 'checkinType', label: '签到类型', width: 140 },
  { prop: 'checkinRemark', label: '签到说明', width: 220 },
  { prop: 'checkinTime', label: '签到时间', width: 160 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'checkinNo', label: '签到编号' },
  { prop: 'elderName', label: '老人姓名' },
  { prop: 'checkinType', label: '签到类型' },
  { prop: 'checkinRemark', label: '签到说明', type: 'textarea' },
  { prop: 'checkinTime', label: '签到时间' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '草稿', value: 'DRAFT' }, { label: '已提交', value: 'SUBMITTED' }, { label: '已审批', value: 'APPROVED' }] }
]
const rowActions = computed(() => {
  const actions = []
  if (canSubmit.value) actions.push({ command: 'submit', label: '提交', type: 'primary' })
  if (canApprove.value) actions.push({ command: 'approve', label: '确认', type: 'success' })
  return actions
})
const defaults = { status: 'DRAFT' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitVisitCheckin(row.id)
  if (command === 'approve') await approveVisitCheckin(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
