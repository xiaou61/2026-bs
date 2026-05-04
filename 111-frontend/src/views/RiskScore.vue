<template>
  <DataPage title="风险评分" description="员工点击行为、考试成绩和综合风险评分管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getRiskScorePage, addRiskScore, updateRiskScore, deleteRiskScore, reviewRiskScore, remediateRiskScore } from '../api'
const api = { page: getRiskScorePage, add: addRiskScore, update: updateRiskScore, delete: deleteRiskScore }
const columns = [{"prop": "employeeName", "label": "员工姓名"}, {"prop": "departmentName", "label": "部门"}, {"prop": "clickCount", "label": "点击次数"}, {"prop": "examScore", "label": "考试分"}, {"prop": "riskScore", "label": "风险分"}, {"prop": "riskLevel", "label": "风险等级"}, {"prop": "reviewerName", "label": "复核人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "employeeName", "label": "员工姓名"}, {"prop": "departmentName", "label": "部门"}, {"prop": "clickCount", "label": "点击次数", "type": "number"}, {"prop": "examScore", "label": "考试分", "type": "number"}, {"prop": "riskScore", "label": "风险分", "type": "number"}, {"prop": "riskLevel", "label": "风险等级", "type": "select", "options": [{"label": "LOW", "value": "LOW"}, {"label": "MEDIUM", "value": "MEDIUM"}, {"label": "HIGH", "value": "HIGH"}, {"label": "CRITICAL", "value": "CRITICAL"}]}, {"prop": "reviewerName", "label": "复核人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "WAIT_REVIEW", "value": "WAIT_REVIEW"}, {"label": "REVIEWED", "value": "REVIEWED"}, {"label": "REMEDIATED", "value": "REMEDIATED"}]}]
const rowActions = [{"command": "review", "label": "复核", "type": "primary"}, {"command": "remediate", "label": "整改", "type": "primary"}]
const defaults = {"riskLevel": "MEDIUM", "status": "WAIT_REVIEW"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'review') await reviewRiskScore(row.id)
  if (command === 'remediate') await remediateRiskScore(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
