<template>
  <DataPage title="归档记录" description="维护合同归档编号、归档位置、归档人和归档日期，支撑签后档案留存" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getArchiveRecordPage, addArchiveRecord, updateArchiveRecord, deleteArchiveRecord, activateArchiveRecord, finishArchiveRecord } from '../api'

const api = { page: getArchiveRecordPage, add: addArchiveRecord, update: updateArchiveRecord, delete: deleteArchiveRecord }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'LEGAL'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [
  { prop: 'archiveNo', label: '归档编号' },
  { prop: 'contractTitle', label: '合同标题', width: 220 },
  { prop: 'archiveLocation', label: '归档位置' },
  { prop: 'archivistName', label: '归档人' },
  { prop: 'archiveDate', label: '归档日期', width: 160 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'archiveNo', label: '归档编号' },
  { prop: 'contractTitle', label: '合同标题' },
  { prop: 'archiveLocation', label: '归档位置' },
  { prop: 'archivistName', label: '归档人' },
  { prop: 'archiveDate', label: '归档日期' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '启用', value: 'ACTIVE' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [{ command: 'activate', label: '启用', type: 'success' }, { command: 'finish', label: '完成', type: 'primary' }] : [])
const defaults = { status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') {
    await activateArchiveRecord(row.id)
  }
  if (command === 'finish') {
    await finishArchiveRecord(row.id)
  }
  ElMessage.success('操作成功')
  refresh()
}
</script>
