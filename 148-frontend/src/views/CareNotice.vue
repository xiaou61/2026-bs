<template>
  <DataPage
    title="通知公告"
    description="维护通知编号、老人姓名、通知类型、通知内容和接收人，支撑服务提醒与随访通知触达"
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
import { getCareNoticePage, addCareNotice, updateCareNotice, deleteCareNotice, processCareNotice, finishCareNotice } from '../api'

const api = { page: getCareNoticePage, add: addCareNotice, update: updateCareNotice, delete: deleteCareNotice }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'CONSULTANT'].includes(role.value))
const columns = [
  { prop: 'noticeNo', label: '通知编号' },
  { prop: 'elderName', label: '老人姓名', width: 140 },
  { prop: 'noticeType', label: '通知类型', width: 140 },
  { prop: 'noticeContent', label: '通知内容', width: 240 },
  { prop: 'receiverName', label: '接收人', width: 140 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'noticeNo', label: '通知编号' },
  { prop: 'elderName', label: '老人姓名' },
  { prop: 'noticeType', label: '通知类型' },
  { prop: 'noticeContent', label: '通知内容', type: 'textarea' },
  { prop: 'receiverName', label: '接收人' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '发布中', value: 'PROCESSING' }, { label: '已结束', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [
  { command: 'process', label: '发布', type: 'primary' },
  { command: 'finish', label: '结束', type: 'success' }
] : [])
const defaults = { status: 'PROCESSING' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processCareNotice(row.id)
  if (command === 'finish') await finishCareNotice(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
