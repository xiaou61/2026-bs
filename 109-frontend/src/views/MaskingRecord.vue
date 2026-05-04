<template>
  <DataPage title="脱敏记录" description="字段脱敏前后样例、处理数量和结果记录" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getMaskingRecordPage, addMaskingRecord, updateMaskingRecord, deleteMaskingRecord } from '../api'
const api = { page: getMaskingRecordPage, add: addMaskingRecord, update: updateMaskingRecord, delete: deleteMaskingRecord }
const columns = [{"prop": "recordNo", "label": "记录编号"}, {"prop": "taskNo", "label": "任务编号"}, {"prop": "fieldName", "label": "字段名"}, {"prop": "beforeSample", "label": "脱敏前样例"}, {"prop": "afterSample", "label": "脱敏后样例"}, {"prop": "processedCount", "label": "处理数量"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "recordNo", "label": "记录编号"}, {"prop": "taskNo", "label": "任务编号"}, {"prop": "fieldName", "label": "字段名"}, {"prop": "beforeSample", "label": "脱敏前样例"}, {"prop": "afterSample", "label": "脱敏后样例"}, {"prop": "processedCount", "label": "处理数量", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "SUCCESS", "value": "SUCCESS"}, {"label": "FAILED", "value": "FAILED"}, {"label": "PARTIAL", "value": "PARTIAL"}]}]
const rowActions = []
const defaults = {"status": "SUCCESS"}
const handleAction = () => {}
</script>
