<template>
  <DataPage title="保险保单" description="维护保单编号、险种类型、承保公司和保障期间，作为车辆理赔案件受理与核赔的基础保单台账" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getInsurancePolicyPage, addInsurancePolicy, updateInsurancePolicy, deleteInsurancePolicy, activateInsurancePolicy, finishInsurancePolicy } from '../api'

const api = { page: getInsurancePolicyPage, add: addInsurancePolicy, update: updateInsurancePolicy, delete: deleteInsurancePolicy }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'LEGAL'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [
  { prop: 'policyNo', label: '保单编号' },
  { prop: 'policyName', label: '保单名称' },
  { prop: 'policyType', label: '险种类型' },
  { prop: 'insurerName', label: '承保公司' },
  { prop: 'coveragePeriod', label: '保障期间', width: 180 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'policyNo', label: '保单编号' },
  { prop: 'policyName', label: '保单名称' },
  { prop: 'policyType', label: '险种类型' },
  { prop: 'insurerName', label: '承保公司' },
  { prop: 'coveragePeriod', label: '保障期间' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '草稿', value: 'DRAFT' }, { label: '启用', value: 'ACTIVE' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [{ command: 'activate', label: '启用', type: 'success' }, { command: 'finish', label: '完成', type: 'primary' }] : [])
const defaults = { status: 'DRAFT' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') {
    await activateInsurancePolicy(row.id)
  }
  if (command === 'finish') {
    await finishInsurancePolicy(row.id)
  }
  ElMessage.success('操作成功')
  refresh()
}
</script>
