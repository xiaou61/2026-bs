<template>
  <DataPage title="存放位置" description="维护资产所在位置、当前数量和锁定数量，支撑在库状态与借出占用管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getStorageLocationPage, addStorageLocation, updateStorageLocation, deleteStorageLocation, processStorageLocation, finishStorageLocation } from '../api'

const api = { page: getStorageLocationPage, add: addStorageLocation, update: updateStorageLocation, delete: deleteStorageLocation }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'ASSET_ADMIN'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [
  { prop: 'locationNo', label: '位置编号' },
  { prop: 'assetName', label: '资产名称' },
  { prop: 'locationName', label: '位置名称' },
  { prop: 'currentQty', label: '当前数量' },
  { prop: 'lockedQty', label: '锁定数量' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'locationNo', label: '位置编号' },
  { prop: 'assetName', label: '资产名称' },
  { prop: 'locationName', label: '位置名称' },
  { prop: 'currentQty', label: '当前数量', type: 'number' },
  { prop: 'lockedQty', label: '锁定数量', type: 'number' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '正常', value: 'NORMAL' }, { label: '处理中', value: 'PROCESSING' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [{ command: 'process', label: '处理', type: 'warning' }, { command: 'finish', label: '完成', type: 'success' }] : [])
const defaults = { status: 'NORMAL' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') {
    await processStorageLocation(row.id)
  }
  if (command === 'finish') {
    await finishStorageLocation(row.id)
  }
  ElMessage.success('操作成功')
  refresh()
}
</script>
