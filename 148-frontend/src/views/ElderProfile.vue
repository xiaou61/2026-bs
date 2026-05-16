<template>
  <DataPage
    title="老人档案"
    description="维护老人编号、老人姓名、年龄分层、居住地址和照护等级，支撑长者服务台账管理"
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
import { getElderProfilePage, addElderProfile, updateElderProfile, deleteElderProfile, activateElderProfile, finishElderProfile } from '../api'

const api = { page: getElderProfilePage, add: addElderProfile, update: updateElderProfile, delete: deleteElderProfile }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'CONSULTANT'].includes(role.value))
const columns = [
  { prop: 'elderNo', label: '老人编号' },
  { prop: 'elderName', label: '老人姓名', width: 140 },
  { prop: 'ageGroup', label: '年龄分层', width: 120 },
  { prop: 'homeAddress', label: '居住地址', width: 220 },
  { prop: 'careLevel', label: '照护等级', width: 140 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'elderNo', label: '老人编号' },
  { prop: 'elderName', label: '老人姓名' },
  { prop: 'ageGroup', label: '年龄分层' },
  { prop: 'homeAddress', label: '居住地址' },
  { prop: 'careLevel', label: '照护等级' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '启用中', value: 'ACTIVE' }, { label: '已归档', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [
  { command: 'activate', label: '启用', type: 'success' },
  { command: 'finish', label: '归档', type: 'warning' }
] : [])
const defaults = { status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateElderProfile(row.id)
  if (command === 'finish') await finishElderProfile(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
