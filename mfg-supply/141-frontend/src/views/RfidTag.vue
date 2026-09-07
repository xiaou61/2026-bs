<template>
  <DataPage title="RFID 标签" description="维护标签编号、EPC 编码、绑定资产和存放区域，支撑快速盘点与定位追踪" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getRfidTagPage, addRfidTag, updateRfidTag, deleteRfidTag, activateRfidTag, finishRfidTag } from '../api'

const api = { page: getRfidTagPage, add: addRfidTag, update: updateRfidTag, delete: deleteRfidTag }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'ASSET_ADMIN'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [
  { prop: 'tagNo', label: '标签编号' },
  { prop: 'epcCode', label: 'EPC 编码', width: 180 },
  { prop: 'assetName', label: '绑定资产' },
  { prop: 'storageArea', label: '存放区域' },
  { prop: 'managerPhone', label: '负责人电话' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'tagNo', label: '标签编号' },
  { prop: 'epcCode', label: 'EPC 编码' },
  { prop: 'assetName', label: '绑定资产' },
  { prop: 'storageArea', label: '存放区域' },
  { prop: 'managerPhone', label: '负责人电话' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '草稿', value: 'DRAFT' }, { label: '启用', value: 'ACTIVE' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [{ command: 'activate', label: '启用', type: 'success' }, { command: 'finish', label: '完成', type: 'primary' }] : [])
const defaults = { status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') {
    await activateRfidTag(row.id)
  }
  if (command === 'finish') {
    await finishRfidTag(row.id)
  }
  ElMessage.success('操作成功')
  refresh()
}
</script>
