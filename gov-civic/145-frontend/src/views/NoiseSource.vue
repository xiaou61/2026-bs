<template>
  <DataPage
    title="噪声源档案"
    description="维护噪声源编号、责任单位、源头类型和联系人，支撑重点噪声源识别与归口管理"
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
import { useUserStore } from '../store/user'
import { getNoiseSourcePage, addNoiseSource, updateNoiseSource, deleteNoiseSource, activateNoiseSource, finishNoiseSource } from '../api'

const api = { page: getNoiseSourcePage, add: addNoiseSource, update: updateNoiseSource, delete: deleteNoiseSource }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'SUPERVISOR'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [
  { prop: 'sourceNo', label: '源档编号' },
  { prop: 'sourceName', label: '噪声源名称', width: 180 },
  { prop: 'responsibleUnit', label: '责任单位', width: 180 },
  { prop: 'sourceType', label: '源头类型', width: 140 },
  { prop: 'contactName', label: '联系人', width: 120 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'sourceNo', label: '源档编号' },
  { prop: 'sourceName', label: '噪声源名称' },
  { prop: 'responsibleUnit', label: '责任单位' },
  { prop: 'sourceType', label: '源头类型' },
  { prop: 'contactName', label: '联系人' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '启用', value: 'ACTIVE' }, { label: '已归档', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [
  { command: 'activate', label: '启用', type: 'success' },
  { command: 'finish', label: '归档', type: 'warning' }
] : [])
const defaults = { status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateNoiseSource(row.id)
  if (command === 'finish') await finishNoiseSource(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
