<template>
  <DataPage title="应急联系人" description="维护联系人编号、出行用户编号、联系人姓名、联系电话和关系备注，支撑协助过程安全保障" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canManage" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getEmergencyContactPage, addEmergencyContact, updateEmergencyContact, deleteEmergencyContact, activateEmergencyContact, finishEmergencyContact } from '../api'

const api = { page: getEmergencyContactPage, add: addEmergencyContact, update: updateEmergencyContact, delete: deleteEmergencyContact }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'TRAVELER'].includes(role.value))
const columns = [
  { prop: 'contactNo', label: '联系人编号' },
  { prop: 'travelerNo', label: '用户编号' },
  { prop: 'contactName', label: '联系人姓名' },
  { prop: 'contactPhone', label: '联系电话' },
  { prop: 'relationRemark', label: '关系备注', width: 180 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'contactNo', label: '联系人编号' },
  { prop: 'travelerNo', label: '用户编号' },
  { prop: 'contactName', label: '联系人姓名' },
  { prop: 'contactPhone', label: '联系电话' },
  { prop: 'relationRemark', label: '关系备注' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '启用', value: 'ACTIVE' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [
  { command: 'activate', label: '启用', type: 'success' },
  { command: 'finish', label: '完成', type: 'primary' }
] : [])
const defaults = { status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateEmergencyContact(row.id)
  if (command === 'finish') await finishEmergencyContact(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
