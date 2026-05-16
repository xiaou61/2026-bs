<template>
  <DataPage title="积分规则" description="维护规则编号、关联项目、积分事项、积分值和生效时间，支撑时间银行积分激励规则管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canManage" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getPointRulePage, addPointRule, updatePointRule, deletePointRule, activatePointRule, finishPointRule } from '../api'

const api = { page: getPointRulePage, add: addPointRule, update: updatePointRule, delete: deletePointRule }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'MANAGER'].includes(role.value))
const columns = [
  { prop: 'ruleNo', label: '规则编号' },
  { prop: 'projectName', label: '关联项目' },
  { prop: 'pointItem', label: '积分事项' },
  { prop: 'pointValue', label: '积分值' },
  { prop: 'effectiveTime', label: '生效时间', width: 160 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'ruleNo', label: '规则编号' },
  { prop: 'projectName', label: '关联项目' },
  { prop: 'pointItem', label: '积分事项' },
  { prop: 'pointValue', label: '积分值', type: 'number' },
  { prop: 'effectiveTime', label: '生效时间' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '启用', value: 'ACTIVE' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [{ command: 'activate', label: '启用', type: 'success' }, { command: 'finish', label: '完成', type: 'primary' }] : [])
const defaults = { status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activatePointRule(row.id)
  if (command === 'finish') await finishPointRule(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
