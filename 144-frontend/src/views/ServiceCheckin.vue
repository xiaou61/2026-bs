<template>
  <DataPage title="服务签到" description="维护签到编号、志愿者编号、签到地点、签到时间和签到人，支撑协助服务执行留痕" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canManage" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getServiceCheckinPage, addServiceCheckin, updateServiceCheckin, deleteServiceCheckin, activateServiceCheckin, finishServiceCheckin } from '../api'

const api = { page: getServiceCheckinPage, add: addServiceCheckin, update: updateServiceCheckin, delete: deleteServiceCheckin }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'DISPATCHER', 'VOLUNTEER'].includes(role.value))
const columns = [
  { prop: 'checkinNo', label: '签到编号' },
  { prop: 'volunteerNo', label: '志愿者编号' },
  { prop: 'checkinLocation', label: '签到地点', width: 220 },
  { prop: 'checkinTime', label: '签到时间', width: 160 },
  { prop: 'checkinName', label: '签到人' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'checkinNo', label: '签到编号' },
  { prop: 'volunteerNo', label: '志愿者编号' },
  { prop: 'checkinLocation', label: '签到地点' },
  { prop: 'checkinTime', label: '签到时间' },
  { prop: 'checkinName', label: '签到人' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '启用', value: 'ACTIVE' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [
  { command: 'activate', label: '启用', type: 'success' },
  { command: 'finish', label: '完成', type: 'primary' }
] : [])
const defaults = { status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateServiceCheckin(row.id)
  if (command === 'finish') await finishServiceCheckin(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
