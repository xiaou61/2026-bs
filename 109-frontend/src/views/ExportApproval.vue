<template>
  <DataPage title="导出审批" description="敏感数据导出类型、申请人和文件等级审批管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getExportApprovalPage, addExportApproval, updateExportApproval, deleteExportApproval, approveExportApproval, rejectExportApproval, finishExportApproval } from '../api'
const api = { page: getExportApprovalPage, add: addExportApproval, update: updateExportApproval, delete: deleteExportApproval }
const columns = [{"prop": "approvalNo", "label": "审批编号"}, {"prop": "datasetName", "label": "数据集"}, {"prop": "exportType", "label": "导出类型"}, {"prop": "applicantName", "label": "申请人"}, {"prop": "fileLevel", "label": "文件等级"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "approvalNo", "label": "审批编号"}, {"prop": "datasetName", "label": "数据集"}, {"prop": "exportType", "label": "导出类型", "type": "select", "options": [{"label": "RAW", "value": "RAW"}, {"label": "MASKED", "value": "MASKED"}, {"label": "SUMMARY", "value": "SUMMARY"}]}, {"prop": "applicantName", "label": "申请人"}, {"prop": "fileLevel", "label": "文件等级", "type": "select", "options": [{"label": "L1", "value": "L1"}, {"label": "L2", "value": "L2"}, {"label": "L3", "value": "L3"}, {"label": "L4", "value": "L4"}]}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "DRAFT", "value": "DRAFT"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "REJECTED", "value": "REJECTED"}, {"label": "FINISHED", "value": "FINISHED"}]}]
const rowActions = [{"command": "approve", "label": "通过", "type": "primary"}, {"command": "reject", "label": "驳回", "type": "primary"}, {"command": "finish", "label": "完成", "type": "primary"}]
const defaults = {"exportType": "MASKED", "status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'approve') await approveExportApproval(row.id)
  if (command === 'reject') await rejectExportApproval(row.id)
  if (command === 'finish') await finishExportApproval(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
