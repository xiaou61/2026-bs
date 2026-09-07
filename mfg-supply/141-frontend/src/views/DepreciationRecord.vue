<template>
  <DataPage title="折旧记录" description="维护折旧编号、原值、净值和归属部门，支撑固定资产折旧核算与审计复核" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getDepreciationRecordPage, addDepreciationRecord, updateDepreciationRecord, deleteDepreciationRecord, processDepreciationRecord, finishDepreciationRecord } from '../api'

const api = { page: getDepreciationRecordPage, add: addDepreciationRecord, update: updateDepreciationRecord, delete: deleteDepreciationRecord }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'ASSET_ADMIN', 'AUDITOR'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [
  { prop: 'depreciationNo', label: '折旧编号' },
  { prop: 'assetName', label: '资产名称' },
  { prop: 'departmentName', label: '归属部门' },
  { prop: 'originalValue', label: '原值' },
  { prop: 'netValue', label: '净值' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'depreciationNo', label: '折旧编号' },
  { prop: 'assetName', label: '资产名称' },
  { prop: 'departmentName', label: '归属部门' },
  { prop: 'originalValue', label: '原值', type: 'number' },
  { prop: 'netValue', label: '净值', type: 'number' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '启用', value: 'ACTIVE' }, { label: '处理中', value: 'PROCESSING' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [{ command: 'process', label: '处理', type: 'warning' }, { command: 'finish', label: '完成', type: 'success' }] : [])
const defaults = { status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') {
    await processDepreciationRecord(row.id)
  }
  if (command === 'finish') {
    await finishDepreciationRecord(row.id)
  }
  ElMessage.success('操作成功')
  refresh()
}
</script>
