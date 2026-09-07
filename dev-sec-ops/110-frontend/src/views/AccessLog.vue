<template>
  <DataPage title="访问日志" description="个人数据访问行为、访问结果和审计轨迹记录" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getAccessLogPage, addAccessLog, updateAccessLog, deleteAccessLog } from '../api'
const api = { page: getAccessLogPage, add: addAccessLog, update: updateAccessLog, delete: deleteAccessLog }
const columns = [{"prop": "logNo", "label": "日志编号"}, {"prop": "operatorName", "label": "访问人"}, {"prop": "subjectName", "label": "数据主体"}, {"prop": "itemName", "label": "数据项"}, {"prop": "accessResult", "label": "访问结果"}, {"prop": "accessTime", "label": "访问时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "logNo", "label": "日志编号"}, {"prop": "operatorName", "label": "访问人"}, {"prop": "subjectName", "label": "数据主体"}, {"prop": "itemName", "label": "数据项"}, {"prop": "accessResult", "label": "访问结果", "type": "select", "options": [{"label": "SUCCESS", "value": "SUCCESS"}, {"label": "DENIED", "value": "DENIED"}, {"label": "FAILED", "value": "FAILED"}]}, {"prop": "accessTime", "label": "访问时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "NORMAL", "value": "NORMAL"}, {"label": "RISK", "value": "RISK"}, {"label": "ARCHIVED", "value": "ARCHIVED"}]}]
const rowActions = []
const defaults = {"accessResult": "SUCCESS", "status": "NORMAL"}
const handleAction = () => {}
</script>
