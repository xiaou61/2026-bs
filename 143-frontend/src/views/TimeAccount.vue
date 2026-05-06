<template>
  <DataPage title="盲审记录" description="评审编号、稿件编号、论文题目、评审得分、提交日期和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getTimeAccountPage, addTimeAccount, updateTimeAccount, deleteTimeAccount, processTimeAccount, finishTimeAccount } from '../api'
const api = { page: getTimeAccountPage, add: addTimeAccount, update: updateTimeAccount, delete: deleteTimeAccount }
const columns = [{"prop": "orderNo", "label": "评审编号"}, {"prop": "supplierName", "label": "稿件编号"}, {"prop": "consumableName", "label": "论文题目"}, {"prop": "orderAmount", "label": "评审得分"}, {"prop": "arrivalDate", "label": "提交日期"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "orderNo", "label": "评审编号"}, {"prop": "supplierName", "label": "稿件编号"}, {"prop": "consumableName", "label": "论文题目"}, {"prop": "orderAmount", "label": "评审得分", "type": "number"}, {"prop": "arrivalDate", "label": "提交日期"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "process", "label": "处理", "type": "warning"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processTimeAccount(row.id)
  if (command === 'finish') await finishTimeAccount(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>






