<template>
  <DataPage title="闲置处置" description="维护闲置资产处置编号、处置数量、处置方式和处理人，支撑报废转移等处置流程" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getDisposalRecordPage, addDisposalRecord, updateDisposalRecord, deleteDisposalRecord, activateDisposalRecord, finishDisposalRecord } from '../api'

const api = { page: getDisposalRecordPage, add: addDisposalRecord, update: updateDisposalRecord, delete: deleteDisposalRecord }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'ASSET_ADMIN', 'AUDITOR'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [
  { prop: 'disposalNo', label: '处置编号' },
  { prop: 'assetName', label: '资产名称' },
  { prop: 'disposalQty', label: '处置数量' },
  { prop: 'disposalType', label: '处置方式' },
  { prop: 'handlerName', label: '处理人' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'disposalNo', label: '处置编号' },
  { prop: 'assetName', label: '资产名称' },
  { prop: 'disposalQty', label: '处置数量', type: 'number' },
  { prop: 'disposalType', label: '处置方式', type: 'select', options: [{ label: '报废处置', value: '报废处置' }, { label: '转移处置', value: '转移处置' }, { label: '拍卖处置', value: '拍卖处置' }] },
  { prop: 'handlerName', label: '处理人' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '草稿', value: 'DRAFT' }, { label: '启用', value: 'ACTIVE' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [{ command: 'activate', label: '启用', type: 'success' }, { command: 'finish', label: '完成', type: 'primary' }] : [])
const defaults = { status: 'DRAFT' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') {
    await activateDisposalRecord(row.id)
  }
  if (command === 'finish') {
    await finishDisposalRecord(row.id)
  }
  ElMessage.success('操作成功')
  refresh()
}
</script>
