<template>
  <DataPage
    title="整改通知"
    description="维护通知编号、整改要求、下发时间和责任单位，支撑整改任务派发与闭环跟踪"
    :api="api"
    :columns="columns"
    :form-fields="formFields"
    :row-actions="rowActions"
    :defaults="defaults"
    :can-create="canManage"
    :can-edit="canManage"
    :can-delete="canManage"
    @row-action="handleAction"
  />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getRectificationNoticePage, addRectificationNotice, updateRectificationNotice, deleteRectificationNotice, processRectificationNotice, finishRectificationNotice } from '../api'

const api = { page: getRectificationNoticePage, add: addRectificationNotice, update: updateRectificationNotice, delete: deleteRectificationNotice }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'SUPERVISOR'].includes(role.value))
const columns = [
  { prop: 'noticeNo', label: '通知编号' },
  { prop: 'complaintTitle', label: '投诉标题', width: 180 },
  { prop: 'rectifyRequirement', label: '整改要求', width: 220 },
  { prop: 'issueTime', label: '下发时间', width: 160 },
  { prop: 'responsibleUnit', label: '责任单位', width: 180 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'noticeNo', label: '通知编号' },
  { prop: 'complaintTitle', label: '投诉标题' },
  { prop: 'rectifyRequirement', label: '整改要求', type: 'textarea' },
  { prop: 'issueTime', label: '下发时间' },
  { prop: 'responsibleUnit', label: '责任单位' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '整改中', value: 'PROCESSING' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [
  { command: 'process', label: '跟进中', type: 'primary' },
  { command: 'finish', label: '完成整改', type: 'success' }
] : [])
const defaults = { status: 'PROCESSING' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processRectificationNotice(row.id)
  if (command === 'finish') await finishRectificationNotice(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
