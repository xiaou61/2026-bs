<template>
  <DataPage
    title="检测机构"
    description="维护合作检测机构资质、入驻时间和擅长领域"
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
import { addAgencyProfile, approveAgencyProfile, deleteAgencyProfile, getAgencyProfilePage, submitAgencyProfile, updateAgencyProfile } from '../api'
import { useUserStore } from '../store/user'

const api = { page: getAgencyProfilePage, add: addAgencyProfile, update: updateAgencyProfile, delete: deleteAgencyProfile }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role)
const canManage = computed(() => ['ADMIN', 'INSPECTOR'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const rowActions = computed(() => {
  const actions = []
  if (['ADMIN', 'INSPECTOR'].includes(role.value)) actions.push({ command: 'submit', label: '提交准入', type: 'success' })
  if (['ADMIN', 'REVIEWER'].includes(role.value)) actions.push({ command: 'approve', label: '审核通过', type: 'warning' })
  return actions
})
const statusOptions = [
  { label: '已提交', value: 'SUBMITTED' },
  { label: '已审批', value: 'APPROVED' },
  { label: '已完成', value: 'FINISHED' }
]
const columns = [
  { prop: 'agencyNo', label: '机构编号' },
  { prop: 'agencyName', label: '机构名称' },
  { prop: 'qualificationLevel', label: '检测资质' },
  { prop: 'entryDate', label: '入驻时间' },
  { prop: 'specialtyArea', label: '负责领域' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'agencyNo', label: '机构编号' },
  { prop: 'agencyName', label: '机构名称' },
  { prop: 'qualificationLevel', label: '检测资质' },
  { prop: 'entryDate', label: '入驻时间' },
  { prop: 'specialtyArea', label: '负责领域' },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions }
]
const defaults = { status: 'SUBMITTED' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitAgencyProfile(row.id)
  if (command === 'approve') await approveAgencyProfile(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
