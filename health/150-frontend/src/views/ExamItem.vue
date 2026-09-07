<template>
  <DataPage title="检查项目" description="项目编号、项目名称、检查类别、适用科室、检查时长和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canManage" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { activateExamItem, addExamItem, deleteExamItem, disableExamItem, getExamItemPage, updateExamItem } from '../api'
import { useUserStore } from '../store/user'

const userStore = useUserStore()
const canManage = computed(() => ['ADMIN', 'DOCTOR'].includes(userStore.user?.role))
const rowActions = computed(() => canManage.value
  ? [
      { command: 'activate', label: '启用', type: 'success' },
      { command: 'disable', label: '停用', type: 'warning' }
    ]
  : [])
const api = { page: getExamItemPage, add: addExamItem, update: updateExamItem, delete: deleteExamItem }
const columns = [
  { prop: 'itemNo', label: '项目编号' },
  { prop: 'itemName', label: '项目名称' },
  { prop: 'category', label: '检查类别' },
  { prop: 'departmentName', label: '适用科室' },
  { prop: 'durationMinutes', label: '检查时长(分钟)' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'itemNo', label: '项目编号' },
  { prop: 'itemName', label: '项目名称' },
  { prop: 'category', label: '检查类别' },
  { prop: 'departmentName', label: '适用科室' },
  { prop: 'durationMinutes', label: '检查时长(分钟)', type: 'number' },
  {
    prop: 'status',
    label: '状态',
    type: 'select',
    options: [
      { label: '启用中', value: 'ACTIVE' },
      { label: '停用', value: 'DISABLED' }
    ]
  }
]
const defaults = { status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateExamItem(row.id)
  if (command === 'disable') await disableExamItem(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
