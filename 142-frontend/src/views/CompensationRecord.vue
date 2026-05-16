<template>
  <DataPage title="赔付记录" description="维护赔付编号、赔付金额、赔付时间和收款人，支撑车辆理赔打款登记与到账跟踪管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getCompensationRecordPage, addCompensationRecord, updateCompensationRecord, deleteCompensationRecord, activateCompensationRecord, finishCompensationRecord } from '../api'

const api = { page: getCompensationRecordPage, add: addCompensationRecord, update: updateCompensationRecord, delete: deleteCompensationRecord }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'LEGAL'].includes(role.value))
const canWorkflow = computed(() => ['ADMIN', 'LEGAL', 'APPROVER'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [
  { prop: 'compensationNo', label: '赔付编号' },
  { prop: 'reportNo', label: '报案编号' },
  { prop: 'compensationAmount', label: '赔付金额' },
  { prop: 'compensationTime', label: '赔付时间', width: 160 },
  { prop: 'payeeName', label: '收款人' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'compensationNo', label: '赔付编号' },
  { prop: 'reportNo', label: '报案编号' },
  { prop: 'compensationAmount', label: '赔付金额', type: 'number' },
  { prop: 'compensationTime', label: '赔付时间' },
  { prop: 'payeeName', label: '收款人' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '草稿', value: 'DRAFT' }, { label: '启用', value: 'ACTIVE' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canWorkflow.value ? [{ command: 'activate', label: '发起赔付', type: 'warning' }, { command: 'finish', label: '赔付完成', type: 'success' }] : [])
const defaults = { status: 'DRAFT' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') {
    await activateCompensationRecord(row.id)
  }
  if (command === 'finish') {
    await finishCompensationRecord(row.id)
  }
  ElMessage.success('操作成功')
  refresh()
}
</script>
