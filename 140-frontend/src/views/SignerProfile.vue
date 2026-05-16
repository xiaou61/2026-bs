<template>
  <DataPage title="签署方档案" description="维护签署人证书、授权状态和签署身份信息，支撑电子签名流程管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getSignerProfilePage, addSignerProfile, updateSignerProfile, deleteSignerProfile, processSignerProfile, finishSignerProfile } from '../api'

const api = { page: getSignerProfilePage, add: addSignerProfile, update: updateSignerProfile, delete: deleteSignerProfile }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'LEGAL'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [
  { prop: 'signerNo', label: '签署方编号' },
  { prop: 'signerName', label: '签署人姓名' },
  { prop: 'signerType', label: '签署身份' },
  { prop: 'certificateNo', label: '证书编号', width: 180 },
  { prop: 'authorizationStatus', label: '授权状态' },
  { prop: 'status', label: '流程状态' }
]
const formFields = [
  { prop: 'signerNo', label: '签署方编号' },
  { prop: 'signerName', label: '签署人姓名' },
  { prop: 'signerType', label: '签署身份', type: 'select', options: [{ label: '企业签署人', value: '企业签署人' }, { label: '代理签署人', value: '代理签署人' }, { label: '法定代表人', value: '法定代表人' }] },
  { prop: 'certificateNo', label: '证书编号' },
  { prop: 'authorizationStatus', label: '授权状态', type: 'select', options: [{ label: '待授权', value: 'PENDING' }, { label: '已授权', value: 'AUTHORIZED' }, { label: '已过期', value: 'EXPIRED' }] },
  { prop: 'status', label: '流程状态', type: 'select', options: [{ label: '启用', value: 'ACTIVE' }, { label: '处理中', value: 'PROCESSING' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [{ command: 'process', label: '流转处理', type: 'warning' }, { command: 'finish', label: '完成', type: 'primary' }] : [])
const defaults = { authorizationStatus: 'PENDING', status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') {
    await processSignerProfile(row.id)
  }
  if (command === 'finish') {
    await finishSignerProfile(row.id)
  }
  ElMessage.success('操作成功')
  refresh()
}
</script>
