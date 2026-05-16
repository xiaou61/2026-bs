<template>
  <DataPage title="用印记录" description="维护用印编号、合同标题、印章类型和用印经办信息，形成可追溯用印台账" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getSealRecordPage, addSealRecord, updateSealRecord, deleteSealRecord, activateSealRecord, finishSealRecord } from '../api'

const api = { page: getSealRecordPage, add: addSealRecord, update: updateSealRecord, delete: deleteSealRecord }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'LEGAL'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [
  { prop: 'sealRecordNo', label: '用印编号' },
  { prop: 'contractTitle', label: '合同标题', width: 220 },
  { prop: 'sealType', label: '印章类型' },
  { prop: 'operatorName', label: '经办人' },
  { prop: 'sealTime', label: '用印时间', width: 180 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'sealRecordNo', label: '用印编号' },
  { prop: 'contractTitle', label: '合同标题' },
  { prop: 'sealType', label: '印章类型', type: 'select', options: [{ label: '合同专用章', value: '合同专用章' }, { label: '公司公章', value: '公司公章' }, { label: '财务专用章', value: '财务专用章' }] },
  { prop: 'operatorName', label: '经办人' },
  { prop: 'sealTime', label: '用印时间' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '启用', value: 'ACTIVE' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [{ command: 'activate', label: '启用', type: 'success' }, { command: 'finish', label: '完成', type: 'primary' }] : [])
const defaults = { status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') {
    await activateSealRecord(row.id)
  }
  if (command === 'finish') {
    await finishSealRecord(row.id)
  }
  ElMessage.success('操作成功')
  refresh()
}
</script>
