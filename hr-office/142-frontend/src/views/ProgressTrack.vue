<template>
  <DataPage title="进度跟踪" description="维护跟踪编号、当前节点、处理部门和预计完成时间，为客户、理赔专员和审核主管提供案件可视化进度" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getProgressTrackPage, addProgressTrack, updateProgressTrack, deleteProgressTrack, activateProgressTrack, finishProgressTrack } from '../api'

const api = { page: getProgressTrackPage, add: addProgressTrack, update: updateProgressTrack, delete: deleteProgressTrack }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'LEGAL'].includes(role.value))
const canWorkflow = computed(() => ['ADMIN', 'LEGAL', 'APPROVER'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [
  { prop: 'trackNo', label: '跟踪编号' },
  { prop: 'reportNo', label: '报案编号' },
  { prop: 'currentNode', label: '当前节点' },
  { prop: 'handleDept', label: '处理部门' },
  { prop: 'expectedFinishTime', label: '预计完成', width: 160 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'trackNo', label: '跟踪编号' },
  { prop: 'reportNo', label: '报案编号' },
  { prop: 'currentNode', label: '当前节点' },
  { prop: 'handleDept', label: '处理部门' },
  { prop: 'expectedFinishTime', label: '预计完成' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '草稿', value: 'DRAFT' }, { label: '启用', value: 'ACTIVE' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canWorkflow.value ? [{ command: 'activate', label: '推进', type: 'warning' }, { command: 'finish', label: '完成', type: 'success' }] : [])
const defaults = { status: 'DRAFT' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') {
    await activateProgressTrack(row.id)
  }
  if (command === 'finish') {
    await finishProgressTrack(row.id)
  }
  ElMessage.success('操作成功')
  refresh()
}
</script>
