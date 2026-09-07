<template>
  <DataPage
    title="处置记录"
    description="记录不合格食品的处置方式、责任单位和完成时间"
    :api="api"
    :columns="columns"
    :form-fields="formFields"
    :row-actions="rowActions"
    :defaults="defaults"
    :can-create="canManage"
    :can-edit="canManage"
    :can-delete="canDelete"
    @row-action="handleAction"
  />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { activateDisposalRecord, addDisposalRecord, deleteDisposalRecord, finishDisposalRecord, getDisposalRecordPage, updateDisposalRecord } from '../api'
import { useUserStore } from '../store/user'

const api = { page: getDisposalRecordPage, add: addDisposalRecord, update: updateDisposalRecord, delete: deleteDisposalRecord }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role)
const canManage = computed(() => ['ADMIN', 'REVIEWER'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const rowActions = computed(() => [
  { command: 'activate', label: '启动处置', type: 'success' },
  { command: 'finish', label: '处置完成', type: 'warning' }
])
const statusOptions = [
  { label: '已启动', value: 'ACTIVE' },
  { label: '已完成', value: 'FINISHED' }
]
const columns = [
  { prop: 'disposalNo', label: '处置编号' },
  { prop: 'foodName', label: '食品名称' },
  { prop: 'disposalMethod', label: '处置方式' },
  { prop: 'disposalTime', label: '处置时间' },
  { prop: 'responsibleUnit', label: '责任单位' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'disposalNo', label: '处置编号' },
  { prop: 'foodName', label: '食品名称' },
  { prop: 'disposalMethod', label: '处置方式' },
  { prop: 'disposalTime', label: '处置时间' },
  { prop: 'responsibleUnit', label: '责任单位' },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions }
]
const defaults = { status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateDisposalRecord(row.id)
  if (command === 'finish') await finishDisposalRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
