<template>
  <DataPage
    title="咨询个案"
    description="维护个案编号、咨询主题、问题类型、所属学院和提交时间，支撑来访受理与结案跟踪"
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
import { getCounselCasePage, addCounselCase, updateCounselCase, deleteCounselCase, activateCounselCase, finishCounselCase } from '../api'

const api = { page: getCounselCasePage, add: addCounselCase, update: updateCounselCase, delete: deleteCounselCase }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'TEACHER', 'COUNSELOR'].includes(role.value))
const columns = [
  { prop: 'caseNo', label: '个案编号' },
  { prop: 'caseTheme', label: '咨询主题', width: 180 },
  { prop: 'issueType', label: '问题类型', width: 140 },
  { prop: 'collegeName', label: '所属学院', width: 160 },
  { prop: 'submitTime', label: '提交时间', width: 160 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'caseNo', label: '个案编号' },
  { prop: 'caseTheme', label: '咨询主题' },
  { prop: 'issueType', label: '问题类型' },
  { prop: 'collegeName', label: '所属学院' },
  { prop: 'submitTime', label: '提交时间' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '待受理', value: 'OPEN' }, { label: '跟进中', value: 'ACTIVE' }, { label: '已结案', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [
  { command: 'activate', label: '受理', type: 'success' },
  { command: 'finish', label: '结案', type: 'primary' }
] : [])
const defaults = { status: 'OPEN' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateCounselCase(row.id)
  if (command === 'finish') await finishCounselCase(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>








