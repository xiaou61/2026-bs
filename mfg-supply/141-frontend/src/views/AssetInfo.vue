<template>
  <DataPage title="资产档案" description="维护固定资产编号、型号、归属部门和使用寿命，作为 RFID 盘点与借用管理的基础台账" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getAssetInfoPage, addAssetInfo, updateAssetInfo, deleteAssetInfo, activateAssetInfo, finishAssetInfo } from '../api'

const api = { page: getAssetInfoPage, add: addAssetInfo, update: updateAssetInfo, delete: deleteAssetInfo }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'ASSET_ADMIN'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [
  { prop: 'assetNo', label: '资产编号' },
  { prop: 'assetName', label: '资产名称' },
  { prop: 'assetModel', label: '资产型号' },
  { prop: 'departmentName', label: '归属部门' },
  { prop: 'serviceLifeMonths', label: '寿命(月)' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'assetNo', label: '资产编号' },
  { prop: 'assetName', label: '资产名称' },
  { prop: 'assetModel', label: '资产型号' },
  { prop: 'departmentName', label: '归属部门' },
  { prop: 'serviceLifeMonths', label: '使用寿命(月)', type: 'number' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '草稿', value: 'DRAFT' }, { label: '启用', value: 'ACTIVE' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [{ command: 'activate', label: '启用', type: 'success' }, { command: 'finish', label: '完成', type: 'primary' }] : [])
const defaults = { status: 'DRAFT' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') {
    await activateAssetInfo(row.id)
  }
  if (command === 'finish') {
    await finishAssetInfo(row.id)
  }
  ElMessage.success('操作成功')
  refresh()
}
</script>
