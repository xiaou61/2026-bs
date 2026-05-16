<template>
  <DataPage title="借用申请" description="维护借用申请编号、借用人、借用资产和预计归还日期，支撑固定资产借出流程管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getBorrowApplicationPage, addBorrowApplication, updateBorrowApplication, deleteBorrowApplication, processBorrowApplication, finishBorrowApplication } from '../api'

const api = { page: getBorrowApplicationPage, add: addBorrowApplication, update: updateBorrowApplication, delete: deleteBorrowApplication }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'BORROWER'].includes(role.value))
const canWorkflow = computed(() => ['ADMIN', 'ASSET_ADMIN', 'AUDITOR'].includes(role.value))
const canDelete = computed(() => canManage.value)
const columns = [
  { prop: 'applicationNo', label: '申请编号' },
  { prop: 'borrowerName', label: '借用人' },
  { prop: 'assetName', label: '资产名称' },
  { prop: 'borrowDays', label: '借用天数' },
  { prop: 'plannedReturnDate', label: '预计归还日', width: 160 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'applicationNo', label: '申请编号' },
  { prop: 'borrowerName', label: '借用人' },
  { prop: 'assetName', label: '资产名称' },
  { prop: 'borrowDays', label: '借用天数', type: 'number' },
  { prop: 'plannedReturnDate', label: '预计归还日' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '草稿', value: 'DRAFT' }, { label: '处理中', value: 'PROCESSING' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canWorkflow.value ? [{ command: 'process', label: '处理', type: 'warning' }, { command: 'finish', label: '完成', type: 'success' }] : [])
const defaults = { status: 'DRAFT' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') {
    await processBorrowApplication(row.id)
  }
  if (command === 'finish') {
    await finishBorrowApplication(row.id)
  }
  ElMessage.success('操作成功')
  refresh()
}
</script>
