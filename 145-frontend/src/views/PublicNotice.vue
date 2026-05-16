<template>
  <DataPage
    title="公告公示"
    description="维护公告编号、公告类型、公告内容和发布时间，支撑治理结果公示与提醒发布"
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
import { getPublicNoticePage, addPublicNotice, updatePublicNotice, deletePublicNotice, processPublicNotice, finishPublicNotice } from '../api'

const api = { page: getPublicNoticePage, add: addPublicNotice, update: updatePublicNotice, delete: deletePublicNotice }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'SUPERVISOR'].includes(role.value))
const columns = [
  { prop: 'noticeNo', label: '公告编号' },
  { prop: 'complaintTitle', label: '关联投诉', width: 180 },
  { prop: 'noticeType', label: '公告类型', width: 140 },
  { prop: 'noticeContent', label: '公告内容', width: 240 },
  { prop: 'publishTime', label: '发布时间', width: 160 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'noticeNo', label: '公告编号' },
  { prop: 'complaintTitle', label: '关联投诉' },
  { prop: 'noticeType', label: '公告类型' },
  { prop: 'noticeContent', label: '公告内容', type: 'textarea' },
  { prop: 'publishTime', label: '发布时间' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '发布中', value: 'PROCESSING' }, { label: '公示完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [
  { command: 'process', label: '发布中', type: 'primary' },
  { command: 'finish', label: '公示完成', type: 'success' }
] : [])
const defaults = { status: 'PROCESSING' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processPublicNotice(row.id)
  if (command === 'finish') await finishPublicNotice(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
