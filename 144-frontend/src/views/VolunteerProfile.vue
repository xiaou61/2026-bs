<template>
  <DataPage title="志愿者档案" description="维护志愿者编号、姓名、服务专长、加入时间和服务区域，支撑协助任务调度与审核" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canManage" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getVolunteerProfilePage, addVolunteerProfile, updateVolunteerProfile, deleteVolunteerProfile, submitVolunteerProfile, approveVolunteerProfile } from '../api'

const api = { page: getVolunteerProfilePage, add: addVolunteerProfile, update: updateVolunteerProfile, delete: deleteVolunteerProfile }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'VOLUNTEER'].includes(role.value))
const canApprove = computed(() => ['ADMIN', 'DISPATCHER'].includes(role.value))
const columns = [
  { prop: 'volunteerNo', label: '志愿者编号' },
  { prop: 'volunteerName', label: '志愿者姓名' },
  { prop: 'serviceExpertise', label: '服务专长', width: 180 },
  { prop: 'joinTime', label: '加入时间', width: 160 },
  { prop: 'serviceArea', label: '服务区域', width: 180 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'volunteerNo', label: '志愿者编号' },
  { prop: 'volunteerName', label: '志愿者姓名' },
  { prop: 'serviceExpertise', label: '服务专长' },
  { prop: 'joinTime', label: '加入时间' },
  { prop: 'serviceArea', label: '服务区域' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '已提交', value: 'SUBMITTED' }, { label: '已审批', value: 'APPROVED' }] }
]
const rowActions = computed(() => {
  const actions = []
  if (canManage.value) actions.push({ command: 'submit', label: '提交', type: 'primary' })
  if (canApprove.value) actions.push({ command: 'approve', label: '通过', type: 'success' })
  return actions
})
const defaults = { status: 'SUBMITTED' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitVolunteerProfile(row.id)
  if (command === 'approve') await approveVolunteerProfile(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
