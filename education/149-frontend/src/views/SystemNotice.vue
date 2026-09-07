<template>
  <DataPage
    title="通知公告"
    description="公告编号、设备名称、公告类型、公告内容、发布时间和状态维护"
    :api="api"
    :columns="columns"
    :form-fields="formFields"
    :row-actions="rowActions"
    :defaults="defaults"
    :can-create="canManageNotice"
    :can-edit="canManageNotice"
    :can-delete="canManageNotice"
    @row-action="handleAction"
  />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getSystemNoticePage, addSystemNotice, updateSystemNotice, deleteSystemNotice, processSystemNotice, finishSystemNotice } from '../api'
import { useUserStore } from '../store/user'

const userStore = useUserStore()
const api = { page: getSystemNoticePage, add: addSystemNotice, update: updateSystemNotice, delete: deleteSystemNotice }
const canManageNotice = computed(() => ['ADMIN', 'TEACHER', 'MANAGER'].includes(userStore.user?.role))
const columns = [
  { prop: 'noticeNo', label: '公告编号' },
  { prop: 'equipmentName', label: '设备名称' },
  { prop: 'noticeType', label: '公告类型' },
  { prop: 'noticeContent', label: '公告内容' },
  { prop: 'publishTime', label: '发布时间' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'noticeNo', label: '公告编号' },
  { prop: 'equipmentName', label: '设备名称' },
  { prop: 'noticeType', label: '公告类型' },
  { prop: 'noticeContent', label: '公告内容', type: 'textarea' },
  { prop: 'publishTime', label: '发布时间' },
  {
    prop: 'status',
    label: '状态',
    type: 'select',
    options: [
      { label: '待发布', value: 'OPEN' },
      { label: '已发布', value: 'PUBLISHED' },
      { label: '已归档', value: 'FINISHED' }
    ]
  }
]
const rowActions = computed(() => canManageNotice.value ? [
  { command: 'process', label: '发布', type: 'success' },
  { command: 'finish', label: '归档', type: 'warning' }
] : [])
const defaults = { status: 'OPEN' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processSystemNotice(row.id)
  if (command === 'finish') await finishSystemNotice(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
