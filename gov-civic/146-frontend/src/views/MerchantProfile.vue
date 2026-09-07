<template>
  <DataPage
    title="商户档案"
    description="维护抽检商户联系人、电话和经营地址信息"
    :api="api"
    :columns="columns"
    :form-fields="formFields"
    :row-actions="rowActions"
    :defaults="defaults"
    :can-create="canCreate"
    :can-edit="canEdit"
    :can-delete="canDelete"
    @row-action="handleAction"
  />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { addMerchantProfile, deleteMerchantProfile, finishMerchantProfile, getMerchantProfilePage, processMerchantProfile, updateMerchantProfile } from '../api'
import { useUserStore } from '../store/user'

const api = { page: getMerchantProfilePage, add: addMerchantProfile, update: updateMerchantProfile, delete: deleteMerchantProfile }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role)
const canCreate = computed(() => ['ADMIN', 'INSPECTOR'].includes(role.value))
const canEdit = computed(() => ['ADMIN', 'INSPECTOR', 'MERCHANT'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const rowActions = computed(() => {
  const actions = []
  if (['ADMIN', 'INSPECTOR', 'REVIEWER'].includes(role.value)) actions.push({ command: 'process', label: '跟进', type: 'success' })
  if (['ADMIN', 'REVIEWER'].includes(role.value)) actions.push({ command: 'finish', label: '办结', type: 'warning' })
  return actions
})
const statusOptions = [
  { label: '已启用', value: 'ACTIVE' },
  { label: '处理中', value: 'PROCESSING' },
  { label: '已完成', value: 'FINISHED' }
]
const columns = [
  { prop: 'merchantNo', label: '商户编号' },
  { prop: 'merchantName', label: '商户名称' },
  { prop: 'contactName', label: '联系人' },
  { prop: 'contactPhone', label: '联系电话' },
  { prop: 'businessAddress', label: '经营地址', width: 180 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'merchantNo', label: '商户编号' },
  { prop: 'merchantName', label: '商户名称' },
  { prop: 'contactName', label: '联系人' },
  { prop: 'contactPhone', label: '联系电话' },
  { prop: 'businessAddress', label: '经营地址', type: 'textarea' },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions }
]
const defaults = { status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processMerchantProfile(row.id)
  if (command === 'finish') await finishMerchantProfile(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
