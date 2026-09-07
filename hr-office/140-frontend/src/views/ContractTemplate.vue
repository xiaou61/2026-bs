<template>
  <DataPage title="合同模板" description="维护合同模板编号、类型、版本和模板负责人，支撑草拟与签署流程复用" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getContractTemplatePage, addContractTemplate, updateContractTemplate, deleteContractTemplate, activateContractTemplate, finishContractTemplate } from '../api'

const api = { page: getContractTemplatePage, add: addContractTemplate, update: updateContractTemplate, delete: deleteContractTemplate }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'LEGAL'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [
  { prop: 'templateNo', label: '模板编号' },
  { prop: 'templateName', label: '模板名称' },
  { prop: 'contractType', label: '合同类型' },
  { prop: 'versionNo', label: '版本号' },
  { prop: 'maintainerName', label: '维护人' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'templateNo', label: '模板编号' },
  { prop: 'templateName', label: '模板名称' },
  { prop: 'contractType', label: '合同类型', type: 'select', options: [{ label: '采购合同', value: '采购合同' }, { label: '服务合同', value: '服务合同' }, { label: '保密协议', value: '保密协议' }, { label: '框架协议', value: '框架协议' }] },
  { prop: 'versionNo', label: '版本号' },
  { prop: 'maintainerName', label: '维护人' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '草稿', value: 'DRAFT' }, { label: '启用', value: 'ACTIVE' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [{ command: 'activate', label: '启用', type: 'success' }, { command: 'finish', label: '完成', type: 'primary' }] : [])
const defaults = { status: 'DRAFT' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') {
    await activateContractTemplate(row.id)
  }
  if (command === 'finish') {
    await finishContractTemplate(row.id)
  }
  ElMessage.success('操作成功')
  refresh()
}
</script>
