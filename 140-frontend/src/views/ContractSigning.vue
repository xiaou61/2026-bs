<template>
  <DataPage title="合同签署" description="维护签署编号、签署人、签署时间和签约状态，跟踪合同签署进度" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getContractSigningPage, addContractSigning, updateContractSigning, deleteContractSigning, processContractSigning, finishContractSigning } from '../api'

const api = { page: getContractSigningPage, add: addContractSigning, update: updateContractSigning, delete: deleteContractSigning }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'LEGAL', 'APPROVER'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [
  { prop: 'signingNo', label: '签署编号' },
  { prop: 'contractTitle', label: '合同标题', width: 220 },
  { prop: 'signatoryName', label: '签署人' },
  { prop: 'signTime', label: '签署时间', width: 180 },
  { prop: 'signingStatus', label: '签约状态' },
  { prop: 'status', label: '流程状态' }
]
const formFields = [
  { prop: 'signingNo', label: '签署编号' },
  { prop: 'contractTitle', label: '合同标题' },
  { prop: 'signatoryName', label: '签署人' },
  { prop: 'signTime', label: '签署时间' },
  { prop: 'signingStatus', label: '签约状态', type: 'select', options: [{ label: '待签署', value: 'PENDING' }, { label: '已签署', value: 'SIGNED' }, { label: '已驳回', value: 'REJECTED' }] },
  { prop: 'status', label: '流程状态', type: 'select', options: [{ label: '启用', value: 'ACTIVE' }, { label: '处理中', value: 'PROCESSING' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [{ command: 'process', label: '处理中', type: 'warning' }, { command: 'finish', label: '完成签署', type: 'success' }] : [])
const defaults = { signingStatus: 'PENDING', status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') {
    await processContractSigning(row.id)
  }
  if (command === 'finish') {
    await finishContractSigning(row.id)
  }
  ElMessage.success('操作成功')
  refresh()
}
</script>
