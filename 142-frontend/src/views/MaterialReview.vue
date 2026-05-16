<template>
  <DataPage title="材料审核" description="维护审核编号、审核结论、审核时间和审核人，形成理赔材料审查与退补件处理台账" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getMaterialReviewPage, addMaterialReview, updateMaterialReview, deleteMaterialReview, processMaterialReview, finishMaterialReview } from '../api'

const api = { page: getMaterialReviewPage, add: addMaterialReview, update: updateMaterialReview, delete: deleteMaterialReview }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'LEGAL'].includes(role.value))
const canWorkflow = computed(() => ['ADMIN', 'LEGAL', 'APPROVER'].includes(role.value))
const canDelete = computed(() => ['ADMIN', 'LEGAL'].includes(role.value))
const columns = [
  { prop: 'reviewNo', label: '审核编号' },
  { prop: 'reportNo', label: '报案编号' },
  { prop: 'reviewResult', label: '审核结论', width: 220 },
  { prop: 'reviewTime', label: '审核时间', width: 160 },
  { prop: 'reviewerName', label: '审核人' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'reviewNo', label: '审核编号' },
  { prop: 'reportNo', label: '报案编号' },
  { prop: 'reviewResult', label: '审核结论' },
  { prop: 'reviewTime', label: '审核时间' },
  { prop: 'reviewerName', label: '审核人' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '草稿', value: 'DRAFT' }, { label: '处理中', value: 'PROCESSING' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canWorkflow.value ? [{ command: 'process', label: '处理', type: 'warning' }, { command: 'finish', label: '办结', type: 'success' }] : [])
const defaults = { status: 'DRAFT' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') {
    await processMaterialReview(row.id)
  }
  if (command === 'finish') {
    await finishMaterialReview(row.id)
  }
  ElMessage.success('操作成功')
  refresh()
}
</script>
