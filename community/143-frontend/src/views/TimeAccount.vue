<template>
  <DataPage title="时长账户" description="维护账户编号、关联项目、账户类型、开户时间和账户归属人，支撑时间银行时长存取管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canManage" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getTimeAccountPage, addTimeAccount, updateTimeAccount, deleteTimeAccount, processTimeAccount, finishTimeAccount } from '../api'

const api = { page: getTimeAccountPage, add: addTimeAccount, update: updateTimeAccount, delete: deleteTimeAccount }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'MANAGER'].includes(role.value))
const columns = [
  { prop: 'accountNo', label: '账户编号' },
  { prop: 'projectName', label: '关联项目' },
  { prop: 'accountType', label: '账户类型' },
  { prop: 'openTime', label: '开户时间', width: 160 },
  { prop: 'ownerName', label: '账户归属人' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'accountNo', label: '账户编号' },
  { prop: 'projectName', label: '关联项目' },
  { prop: 'accountType', label: '账户类型' },
  { prop: 'openTime', label: '开户时间' },
  { prop: 'ownerName', label: '账户归属人' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '启用', value: 'ACTIVE' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [{ command: 'process', label: '启用', type: 'warning' }, { command: 'finish', label: '完成', type: 'success' }] : [])
const defaults = { status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processTimeAccount(row.id)
  if (command === 'finish') await finishTimeAccount(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
