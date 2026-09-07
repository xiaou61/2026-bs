<template>
  <DataPage
    title="复检申请"
    description="维护商户复检申请、申请原因和申请时间"
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
import { activateRecheckApplication, addRecheckApplication, deleteRecheckApplication, finishRecheckApplication, getRecheckApplicationPage, updateRecheckApplication } from '../api'
import { useUserStore } from '../store/user'

const api = { page: getRecheckApplicationPage, add: addRecheckApplication, update: updateRecheckApplication, delete: deleteRecheckApplication }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role)
const canManage = computed(() => ['ADMIN', 'MERCHANT'].includes(role.value))
const canDelete = computed(() => ['ADMIN', 'MERCHANT'].includes(role.value))
const rowActions = computed(() => {
  const actions = []
  if (['ADMIN', 'MERCHANT'].includes(role.value)) actions.push({ command: 'activate', label: '提交复检', type: 'success' })
  if (['ADMIN', 'REVIEWER'].includes(role.value)) actions.push({ command: 'finish', label: '复检办结', type: 'warning' })
  return actions
})
const statusOptions = [
  { label: '已受理', value: 'ACTIVE' },
  { label: '已完成', value: 'FINISHED' }
]
const columns = [
  { prop: 'applicationNo', label: '申请编号' },
  { prop: 'foodName', label: '食品名称' },
  { prop: 'recheckReason', label: '复检原因', width: 180 },
  { prop: 'applicationTime', label: '申请时间' },
  { prop: 'applicantName', label: '申请人' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'applicationNo', label: '申请编号' },
  { prop: 'foodName', label: '食品名称' },
  { prop: 'recheckReason', label: '复检原因', type: 'textarea' },
  { prop: 'applicationTime', label: '申请时间' },
  { prop: 'applicantName', label: '申请人' },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions }
]
const defaults = { status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateRecheckApplication(row.id)
  if (command === 'finish') await finishRecheckApplication(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
