<template>
  <DataPage title="站内通知" description="维护通知编号、关联项目、通知类型、通知内容和发布时间，支撑社区服务提醒与兑换通知发布" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canManage" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getSiteNoticePage, addSiteNotice, updateSiteNotice, deleteSiteNotice, processSiteNotice, finishSiteNotice } from '../api'

const api = { page: getSiteNoticePage, add: addSiteNotice, update: updateSiteNotice, delete: deleteSiteNotice }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'MANAGER'].includes(role.value))
const columns = [
  { prop: 'noticeNo', label: '通知编号' },
  { prop: 'projectName', label: '关联项目' },
  { prop: 'noticeType', label: '通知类型' },
  { prop: 'noticeContent', label: '通知内容', width: 240 },
  { prop: 'publishTime', label: '发布时间', width: 160 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'noticeNo', label: '通知编号' },
  { prop: 'projectName', label: '关联项目' },
  { prop: 'noticeType', label: '通知类型' },
  { prop: 'noticeContent', label: '通知内容', type: 'textarea' },
  { prop: 'publishTime', label: '发布时间' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '已发布', value: 'PUBLISHED' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [{ command: 'process', label: '发布', type: 'warning' }, { command: 'finish', label: '结束', type: 'success' }] : [])
const defaults = { status: 'PUBLISHED' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processSiteNotice(row.id)
  if (command === 'finish') await finishSiteNotice(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
