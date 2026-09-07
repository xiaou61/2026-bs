<template>
  <DataPage
    title="监测点位"
    description="维护点位编号、点位名称、点位类型、所属街道和噪声阈值，支撑噪声监测网络管理"
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
import { getMonitoringSitePage, addMonitoringSite, updateMonitoringSite, deleteMonitoringSite, activateMonitoringSite, finishMonitoringSite } from '../api'

const api = { page: getMonitoringSitePage, add: addMonitoringSite, update: updateMonitoringSite, delete: deleteMonitoringSite }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'SUPERVISOR'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [
  { prop: 'siteNo', label: '点位编号' },
  { prop: 'siteName', label: '点位名称', width: 180 },
  { prop: 'siteType', label: '点位类型', width: 140 },
  { prop: 'streetName', label: '所属街道', width: 160 },
  { prop: 'noiseThreshold', label: '噪声阈值', width: 140 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'siteNo', label: '点位编号' },
  { prop: 'siteName', label: '点位名称' },
  { prop: 'siteType', label: '点位类型' },
  { prop: 'streetName', label: '所属街道' },
  { prop: 'noiseThreshold', label: '噪声阈值' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '启用', value: 'ACTIVE' }, { label: '已归档', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [
  { command: 'activate', label: '启用', type: 'success' },
  { command: 'finish', label: '归档', type: 'warning' }
] : [])
const defaults = { status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateMonitoringSite(row.id)
  if (command === 'finish') await finishMonitoringSite(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
