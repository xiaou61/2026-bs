<template>
  <DataPage title="考试记录" description="员工考试成绩、通过状态和补训记录管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getExamAttemptPage, addExamAttempt, updateExamAttempt, deleteExamAttempt, passExamAttempt, failExamAttempt, retrainExamAttempt } from '../api'
const api = { page: getExamAttemptPage, add: addExamAttempt, update: updateExamAttempt, delete: deleteExamAttempt }
const columns = [{"prop": "attemptNo", "label": "考试记录"}, {"prop": "employeeName", "label": "员工姓名"}, {"prop": "examName", "label": "考试名称"}, {"prop": "scoreValue", "label": "得分"}, {"prop": "resultStatus", "label": "结果"}, {"prop": "submitTime", "label": "提交时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "attemptNo", "label": "考试记录"}, {"prop": "employeeName", "label": "员工姓名"}, {"prop": "examName", "label": "考试名称"}, {"prop": "scoreValue", "label": "得分", "type": "number"}, {"prop": "resultStatus", "label": "结果", "type": "select", "options": [{"label": "PASSED", "value": "PASSED"}, {"label": "FAILED", "value": "FAILED"}, {"label": "RETRAINING", "value": "RETRAINING"}]}, {"prop": "submitTime", "label": "提交时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "PASSED", "value": "PASSED"}, {"label": "FAILED", "value": "FAILED"}, {"label": "RETRAINING", "value": "RETRAINING"}]}]
const rowActions = [{"command": "pass", "label": "通过", "type": "primary"}, {"command": "fail", "label": "未通过", "type": "primary"}, {"command": "retrain", "label": "补训", "type": "primary"}]
const defaults = {"status": "SUBMITTED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'pass') await passExamAttempt(row.id)
  if (command === 'fail') await failExamAttempt(row.id)
  if (command === 'retrain') await retrainExamAttempt(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
