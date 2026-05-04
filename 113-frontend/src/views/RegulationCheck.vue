<template>
  <DataPage title="监管检查" description="监管抽查、检查结论、整改要求和闭环状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getRegulationCheckPage, addRegulationCheck, updateRegulationCheck, deleteRegulationCheck, publishRegulationCheck, rectifyRegulationCheck, closeRegulationCheck } from '../api'
const api = { page: getRegulationCheckPage, add: addRegulationCheck, update: updateRegulationCheck, delete: deleteRegulationCheck }
const columns = [{"prop": "checkNo", "label": "检查编号"}, {"prop": "targetName", "label": "检查对象"}, {"prop": "checkType", "label": "检查类型"}, {"prop": "regulatorName", "label": "监管员"}, {"prop": "checkDate", "label": "检查日期"}, {"prop": "resultStatus", "label": "检查结论"}, {"prop": "rectifyText", "label": "整改要求"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "checkNo", "label": "检查编号"}, {"prop": "targetName", "label": "检查对象"}, {"prop": "checkType", "label": "检查类型", "type": "select", "options": [{"label": "ROUTINE", "value": "ROUTINE"}, {"label": "SPECIAL", "value": "SPECIAL"}, {"label": "RANDOM", "value": "RANDOM"}, {"label": "COMPLAINT", "value": "COMPLAINT"}]}, {"prop": "regulatorName", "label": "监管员"}, {"prop": "checkDate", "label": "检查日期"}, {"prop": "resultStatus", "label": "检查结论", "type": "select", "options": [{"label": "PASS", "value": "PASS"}, {"label": "RECTIFY", "value": "RECTIFY"}, {"label": "FAILED", "value": "FAILED"}]}, {"prop": "rectifyText", "label": "整改要求", "type": "textarea"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "DRAFT", "value": "DRAFT"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "RECTIFYING", "value": "RECTIFYING"}, {"label": "CLOSED", "value": "CLOSED"}]}]
const rowActions = [{"command": "publish", "label": "发布", "type": "primary"}, {"command": "rectify", "label": "整改", "type": "primary"}, {"command": "close", "label": "闭环", "type": "primary"}]
const defaults = {"resultStatus": "PASS", "status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'publish') await publishRegulationCheck(row.id)
  if (command === 'rectify') await rectifyRegulationCheck(row.id)
  if (command === 'close') await closeRegulationCheck(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
