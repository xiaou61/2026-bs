<template>
  <DataPage title="设施点位" description="维护设施编号、设施名称、设施类型、地址位置和开放状态，支撑无障碍服务点维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getFacilityPointPage, addFacilityPoint, updateFacilityPoint, deleteFacilityPoint, activateFacilityPoint, finishFacilityPoint } from '../api'

const api = { page: getFacilityPointPage, add: addFacilityPoint, update: updateFacilityPoint, delete: deleteFacilityPoint }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'DISPATCHER'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [
  { prop: 'pointNo', label: '设施编号' },
  { prop: 'pointName', label: '设施名称' },
  { prop: 'facilityType', label: '设施类型' },
  { prop: 'addressDetail', label: '地址位置', width: 220 },
  { prop: 'openStatus', label: '开放状态' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'pointNo', label: '设施编号' },
  { prop: 'pointName', label: '设施名称' },
  { prop: 'facilityType', label: '设施类型' },
  { prop: 'addressDetail', label: '地址位置' },
  { prop: 'openStatus', label: '开放状态' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '启用', value: 'ACTIVE' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [
  { command: 'activate', label: '启用', type: 'success' },
  { command: 'finish', label: '完成', type: 'primary' }
] : [])
const defaults = { status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateFacilityPoint(row.id)
  if (command === 'finish') await finishFacilityPoint(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
