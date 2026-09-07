<template>
  <DataPage title="车辆档案" description="维护车辆编号、车牌号码、车架号码和使用性质，作为理赔受理、定损和赔付的基础车辆台账" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getVehicleProfilePage, addVehicleProfile, updateVehicleProfile, deleteVehicleProfile, activateVehicleProfile, finishVehicleProfile } from '../api'

const api = { page: getVehicleProfilePage, add: addVehicleProfile, update: updateVehicleProfile, delete: deleteVehicleProfile }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'LEGAL'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [
  { prop: 'vehicleNo', label: '车辆编号' },
  { prop: 'plateNumber', label: '车牌号码' },
  { prop: 'vinCode', label: '车架号码', width: 200 },
  { prop: 'vehicleModel', label: '品牌型号', width: 180 },
  { prop: 'usageType', label: '使用性质' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'vehicleNo', label: '车辆编号' },
  { prop: 'plateNumber', label: '车牌号码' },
  { prop: 'vinCode', label: '车架号码' },
  { prop: 'vehicleModel', label: '品牌型号' },
  { prop: 'usageType', label: '使用性质' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '草稿', value: 'DRAFT' }, { label: '启用', value: 'ACTIVE' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [{ command: 'activate', label: '启用', type: 'success' }, { command: 'finish', label: '完成', type: 'primary' }] : [])
const defaults = { status: 'DRAFT' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') {
    await activateVehicleProfile(row.id)
  }
  if (command === 'finish') {
    await finishVehicleProfile(row.id)
  }
  ElMessage.success('操作成功')
  refresh()
}
</script>
