<template>
  <DataPage title="预警通知" description="维护超期未还、维修逾期等预警通知，帮助资产管理员及时跟踪处理风险" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getWarningNoticePage, addWarningNotice, updateWarningNotice, deleteWarningNotice, processWarningNotice, finishWarningNotice } from '../api'

const api = { page: getWarningNoticePage, add: addWarningNotice, update: updateWarningNotice, delete: deleteWarningNotice }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'ASSET_ADMIN', 'AUDITOR'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [
  { prop: 'noticeNo', label: '通知编号' },
  { prop: 'assetName', label: '资产名称' },
  { prop: 'remainingCount', label: '剩余数量' },
  { prop: 'warningType', label: '预警类型' },
  { prop: 'handlerName', label: '处理人' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'noticeNo', label: '通知编号' },
  { prop: 'assetName', label: '资产名称' },
  { prop: 'remainingCount', label: '剩余数量', type: 'number' },
  { prop: 'warningType', label: '预警类型', type: 'select', options: [{ label: '超期未还预警', value: '超期未还预警' }, { label: '维修逾期提醒', value: '维修逾期提醒' }, { label: '盘点异常通知', value: '盘点异常通知' }] },
  { prop: 'handlerName', label: '处理人' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '预警', value: 'WARNING' }, { label: '处理中', value: 'PROCESSING' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [{ command: 'process', label: '处理', type: 'warning' }, { command: 'finish', label: '完成', type: 'success' }] : [])
const defaults = { status: 'WARNING' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') {
    await processWarningNotice(row.id)
  }
  if (command === 'finish') {
    await finishWarningNotice(row.id)
  }
  ElMessage.success('操作成功')
  refresh()
}
</script>
