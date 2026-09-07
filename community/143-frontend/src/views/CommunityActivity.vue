<template>
  <DataPage title="公益活动" description="维护活动编号、关联项目、活动主题、活动地点和活动时间，支撑社区公益活动发布与归档" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canManage" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getCommunityActivityPage, addCommunityActivity, updateCommunityActivity, deleteCommunityActivity, processCommunityActivity, finishCommunityActivity } from '../api'

const api = { page: getCommunityActivityPage, add: addCommunityActivity, update: updateCommunityActivity, delete: deleteCommunityActivity }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'MANAGER'].includes(role.value))
const columns = [
  { prop: 'activityNo', label: '活动编号' },
  { prop: 'projectName', label: '关联项目' },
  { prop: 'activityTheme', label: '活动主题' },
  { prop: 'activityLocation', label: '活动地点' },
  { prop: 'activityTime', label: '活动时间', width: 160 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'activityNo', label: '活动编号' },
  { prop: 'projectName', label: '关联项目' },
  { prop: 'activityTheme', label: '活动主题' },
  { prop: 'activityLocation', label: '活动地点' },
  { prop: 'activityTime', label: '活动时间' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '已发布', value: 'PUBLISHED' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [{ command: 'process', label: '发布', type: 'warning' }, { command: 'finish', label: '结束', type: 'success' }] : [])
const defaults = { status: 'PUBLISHED' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processCommunityActivity(row.id)
  if (command === 'finish') await finishCommunityActivity(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
