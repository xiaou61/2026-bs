<template>
  <DataPage title="服务签到" description="维护签到编号、服务项目、签到志愿者、签到时间和服务结果，支撑志愿服务现场记录与确认" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canManage" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getServiceCheckinPage, addServiceCheckin, updateServiceCheckin, deleteServiceCheckin, submitServiceCheckin, approveServiceCheckin } from '../api'

const api = { page: getServiceCheckinPage, add: addServiceCheckin, update: updateServiceCheckin, delete: deleteServiceCheckin }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'MANAGER', 'VOLUNTEER'].includes(role.value))
const canApprove = computed(() => ['ADMIN', 'MANAGER'].includes(role.value))
const columns = [
  { prop: 'checkinNo', label: '签到编号' },
  { prop: 'projectName', label: '服务项目' },
  { prop: 'checkinName', label: '签到志愿者' },
  { prop: 'checkinTime', label: '签到时间', width: 160 },
  { prop: 'serviceResult', label: '服务结果', width: 220 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'checkinNo', label: '签到编号' },
  { prop: 'projectName', label: '服务项目' },
  { prop: 'checkinName', label: '签到志愿者' },
  { prop: 'checkinTime', label: '签到时间' },
  { prop: 'serviceResult', label: '服务结果', type: 'textarea' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '草稿', value: 'DRAFT' }, { label: '已提交', value: 'SUBMITTED' }, { label: '已审批', value: 'APPROVED' }] }
]
const rowActions = computed(() => {
  const actions = []
  if (canManage.value) actions.push({ command: 'submit', label: '提交签到', type: 'warning' })
  if (canApprove.value) actions.push({ command: 'approve', label: '审批通过', type: 'success' })
  return actions
})
const defaults = { status: 'DRAFT' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitServiceCheckin(row.id)
  if (command === 'approve') await approveServiceCheckin(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
