<template>
  <DataPage title="客户档案" description="维护客户编号、证件号码、联系电话和客户类型，用于理赔申请受理与回访通知的客户信息归集" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getCustomerProfilePage, addCustomerProfile, updateCustomerProfile, deleteCustomerProfile, activateCustomerProfile, finishCustomerProfile } from '../api'

const api = { page: getCustomerProfilePage, add: addCustomerProfile, update: updateCustomerProfile, delete: deleteCustomerProfile }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'LEGAL'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [
  { prop: 'customerNo', label: '客户编号' },
  { prop: 'customerName', label: '客户姓名' },
  { prop: 'idCardNo', label: '证件号码', width: 180 },
  { prop: 'phone', label: '联系电话' },
  { prop: 'customerType', label: '客户类型' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'customerNo', label: '客户编号' },
  { prop: 'customerName', label: '客户姓名' },
  { prop: 'idCardNo', label: '证件号码' },
  { prop: 'phone', label: '联系电话' },
  { prop: 'customerType', label: '客户类型' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '草稿', value: 'DRAFT' }, { label: '启用', value: 'ACTIVE' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [{ command: 'activate', label: '启用', type: 'success' }, { command: 'finish', label: '完成', type: 'primary' }] : [])
const defaults = { status: 'DRAFT' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') {
    await activateCustomerProfile(row.id)
  }
  if (command === 'finish') {
    await finishCustomerProfile(row.id)
  }
  ElMessage.success('操作成功')
  refresh()
}
</script>
