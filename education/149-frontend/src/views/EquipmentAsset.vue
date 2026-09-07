<template>
  <DataPage
    title="设备档案"
    description="设备编号、设备名称、设备型号、所属实验室、入库时间和状态维护"
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
import { getEquipmentAssetPage, addEquipmentAsset, updateEquipmentAsset, deleteEquipmentAsset, activateEquipmentAsset, finishEquipmentAsset } from '../api'
import { useUserStore } from '../store/user'

const userStore = useUserStore()
const api = { page: getEquipmentAssetPage, add: addEquipmentAsset, update: updateEquipmentAsset, delete: deleteEquipmentAsset }
const canManage = computed(() => ['ADMIN', 'MANAGER'].includes(userStore.user?.role))
const columns = [
  { prop: 'assetNo', label: '设备编号' },
  { prop: 'assetName', label: '设备名称' },
  { prop: 'assetModel', label: '设备型号' },
  { prop: 'laboratoryName', label: '所属实验室' },
  { prop: 'storageTime', label: '入库时间' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'assetNo', label: '设备编号' },
  { prop: 'assetName', label: '设备名称' },
  { prop: 'assetModel', label: '设备型号' },
  { prop: 'laboratoryName', label: '所属实验室' },
  { prop: 'storageTime', label: '入库时间' },
  {
    prop: 'status',
    label: '状态',
    type: 'select',
    options: [
      { label: '启用中', value: 'ACTIVE' },
      { label: '停用', value: 'DISABLED' }
    ]
  }
]
const rowActions = computed(() => canManage.value ? [
  { command: 'activate', label: '启用', type: 'success' },
  { command: 'finish', label: '停用', type: 'warning' }
] : [])
const defaults = { status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateEquipmentAsset(row.id)
  if (command === 'finish') await finishEquipmentAsset(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
