<template>
  <DataPage title="相对方档案" description="维护合同相对方主体、统一社会信用代码和联系人信息，保障签约对象信息完整" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getCounterpartyProfilePage, addCounterpartyProfile, updateCounterpartyProfile, deleteCounterpartyProfile, activateCounterpartyProfile, finishCounterpartyProfile } from '../api'

const api = { page: getCounterpartyProfilePage, add: addCounterpartyProfile, update: updateCounterpartyProfile, delete: deleteCounterpartyProfile }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'LEGAL'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [
  { prop: 'counterpartyNo', label: '相对方编号' },
  { prop: 'counterpartyName', label: '相对方名称' },
  { prop: 'creditCode', label: '统一社会信用代码', width: 180 },
  { prop: 'contactName', label: '联系人' },
  { prop: 'contactPhone', label: '联系电话' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'counterpartyNo', label: '相对方编号' },
  { prop: 'counterpartyName', label: '相对方名称' },
  { prop: 'creditCode', label: '统一社会信用代码' },
  { prop: 'contactName', label: '联系人' },
  { prop: 'contactPhone', label: '联系电话' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '草稿', value: 'DRAFT' }, { label: '启用', value: 'ACTIVE' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [{ command: 'activate', label: '启用', type: 'success' }, { command: 'finish', label: '完成', type: 'primary' }] : [])
const defaults = { status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') {
    await activateCounterpartyProfile(row.id)
  }
  if (command === 'finish') {
    await finishCounterpartyProfile(row.id)
  }
  ElMessage.success('操作成功')
  refresh()
}
</script>
