<template>
  <DataPage title="识别任务" description="敏感信息扫描任务和执行状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getRecognitionTaskPage, addRecognitionTask, updateRecognitionTask, deleteRecognitionTask, startRecognitionTask, finishRecognitionTask, failRecognitionTask } from '../api'
const api = { page: getRecognitionTaskPage, add: addRecognitionTask, update: updateRecognitionTask, delete: deleteRecognitionTask }
const columns = [{"prop": "taskNo", "label": "任务编号"}, {"prop": "datasetName", "label": "数据集"}, {"prop": "ruleScope", "label": "规则范围"}, {"prop": "scanMode", "label": "扫描模式"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "startedTime", "label": "开始时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "taskNo", "label": "任务编号"}, {"prop": "datasetName", "label": "数据集"}, {"prop": "ruleScope", "label": "规则范围"}, {"prop": "scanMode", "label": "扫描模式", "type": "select", "options": [{"label": "FULL", "value": "FULL"}, {"label": "INCREMENT", "value": "INCREMENT"}, {"label": "SAMPLE", "value": "SAMPLE"}]}, {"prop": "ownerName", "label": "负责人"}, {"prop": "startedTime", "label": "开始时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "DRAFT", "value": "DRAFT"}, {"label": "RUNNING", "value": "RUNNING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "FAILED", "value": "FAILED"}]}]
const rowActions = [{"command": "start", "label": "启动", "type": "primary"}, {"command": "finish", "label": "完成", "type": "primary"}, {"command": "fail", "label": "失败", "type": "primary"}]
const defaults = {"scanMode": "FULL", "status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'start') await startRecognitionTask(row.id)
  if (command === 'finish') await finishRecognitionTask(row.id)
  if (command === 'fail') await failRecognitionTask(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
