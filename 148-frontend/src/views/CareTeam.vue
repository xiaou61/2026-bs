<template>
  <DataPage
    title="服务团队"
    description="维护团队编号、团队名称、擅长服务、组建时间和服务片区，支撑护理班组编排"
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
import { getCareTeamPage, addCareTeam, updateCareTeam, deleteCareTeam, submitCareTeam, approveCareTeam } from '../api'

const api = { page: getCareTeamPage, add: addCareTeam, update: updateCareTeam, delete: deleteCareTeam }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'CONSULTANT'].includes(role.value))
const columns = [
  { prop: 'teamNo', label: '团队编号' },
  { prop: 'teamName', label: '团队名称', width: 180 },
  { prop: 'serviceExpertise', label: '擅长服务', width: 160 },
  { prop: 'buildTime', label: '组建时间', width: 140 },
  { prop: 'serviceArea', label: '服务片区', width: 140 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'teamNo', label: '团队编号' },
  { prop: 'teamName', label: '团队名称' },
  { prop: 'serviceExpertise', label: '擅长服务' },
  { prop: 'buildTime', label: '组建时间' },
  { prop: 'serviceArea', label: '服务片区' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '已提交', value: 'SUBMITTED' }, { label: '已审批', value: 'APPROVED' }] }
]
const rowActions = computed(() => canManage.value ? [
  { command: 'submit', label: '提交编组', type: 'primary' },
  { command: 'approve', label: '确认启用', type: 'success' }
] : [])
const defaults = { status: 'SUBMITTED' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitCareTeam(row.id)
  if (command === 'approve') await approveCareTeam(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
