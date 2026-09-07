<template>
  <DataPage
    title="通知公告"
    description="维护公告编号、咨询主题、公告类型、公告内容和发布时间，支撑预约提醒与心理健康通知触达"
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
import { getSystemNoticePage, addSystemNotice, updateSystemNotice, deleteSystemNotice, processSystemNotice, finishSystemNotice } from '../api'

const api = { page: getSystemNoticePage, add: addSystemNotice, update: updateSystemNotice, delete: deleteSystemNotice }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'TEACHER', 'COUNSELOR'].includes(role.value))
const columns = [
  { prop: 'noticeNo', label: '公告编号' },
  { prop: 'caseTheme', label: '咨询主题', width: 180 },
  { prop: 'noticeType', label: '公告类型', width: 140 },
  { prop: 'noticeContent', label: '公告内容', width: 260 },
  { prop: 'publishTime', label: '发布时间', width: 160 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'noticeNo', label: '公告编号' },
  { prop: 'caseTheme', label: '咨询主题' },
  { prop: 'noticeType', label: '公告类型' },
  { prop: 'noticeContent', label: '公告内容', type: 'textarea' },
  { prop: 'publishTime', label: '发布时间' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '发布中', value: 'PROCESSING' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [
  { command: 'process', label: '发布中', type: 'primary' },
  { command: 'finish', label: '已完成', type: 'success' }
] : [])
const defaults = { status: 'PROCESSING' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processSystemNotice(row.id)
  if (command === 'finish') await finishSystemNotice(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>








