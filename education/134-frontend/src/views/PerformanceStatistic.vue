<template>
  <DataPage title="绩效统计" description="统计编号、项目编号、统计月份、报销次数、成果数量和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getPerformanceStatisticPage, addPerformanceStatistic, updatePerformanceStatistic, deletePerformanceStatistic, processPerformanceStatistic, finishPerformanceStatistic } from '../api'
const api = { page: getPerformanceStatisticPage, add: addPerformanceStatistic, update: updatePerformanceStatistic, delete: deletePerformanceStatistic }
const columns = [{"prop": "statNo", "label": "统计编号"}, {"prop": "projectNo", "label": "项目编号"}, {"prop": "statMonth", "label": "统计月份"}, {"prop": "claimCount", "label": "报销次数"}, {"prop": "achievementCount", "label": "成果数量"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "statNo", "label": "统计编号"}, {"prop": "projectNo", "label": "项目编号"}, {"prop": "statMonth", "label": "统计月份"}, {"prop": "claimCount", "label": "报销次数", "type": "number"}, {"prop": "achievementCount", "label": "成果数量", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "process", "label": "处理", "type": "warning"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "FINISHED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processPerformanceStatistic(row.id)
  if (command === 'finish') await finishPerformanceStatistic(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
