<template>
  <DataPage
    title="执法人员档案"
    description="维护执法编号、姓名、负责片区和联系方式，支撑噪声治理执法队伍管理"
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
import { useUserStore } from '../store/user'
import { getOfficerProfilePage, addOfficerProfile, updateOfficerProfile, deleteOfficerProfile, processOfficerProfile, finishOfficerProfile } from '../api'

const api = { page: getOfficerProfilePage, add: addOfficerProfile, update: updateOfficerProfile, delete: deleteOfficerProfile }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'OFFICER'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [
  { prop: 'officerNo', label: '执法编号' },
  { prop: 'officerName', label: '执法姓名', width: 140 },
  { prop: 'dutyArea', label: '负责片区', width: 180 },
  { prop: 'contactPhone', label: '联系电话', width: 140 },
  { prop: 'entryTime', label: '入职时间', width: 140 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'officerNo', label: '执法编号' },
  { prop: 'officerName', label: '执法姓名' },
  { prop: 'dutyArea', label: '负责片区' },
  { prop: 'contactPhone', label: '联系电话' },
  { prop: 'entryTime', label: '入职时间' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '在岗', value: 'PROCESSING' }, { label: '已归档', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [
  { command: 'process', label: '在岗', type: 'success' },
  { command: 'finish', label: '归档', type: 'warning' }
] : [])
const defaults = { status: 'PROCESSING' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processOfficerProfile(row.id)
  if (command === 'finish') await finishOfficerProfile(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
