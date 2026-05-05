<template>
  <DataPage title="产量统计" description="统计编号、池塘、统计月份、产量和成活率维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getProductionStatisticPage, addProductionStatistic, updateProductionStatistic, deleteProductionStatistic, activateProductionStatistic, finishProductionStatistic } from '../api'
const api = { page: getProductionStatisticPage, add: addProductionStatistic, update: updateProductionStatistic, delete: deleteProductionStatistic }
const columns = [{"prop": "statNo", "label": "统计编号"}, {"prop": "pondNo", "label": "池塘编号"}, {"prop": "statMonth", "label": "统计月份"}, {"prop": "outputWeight", "label": "产量"}, {"prop": "survivalRate", "label": "成活率"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "statNo", "label": "统计编号"}, {"prop": "pondNo", "label": "池塘编号"}, {"prop": "statMonth", "label": "统计月份"}, {"prop": "outputWeight", "label": "产量", "type": "number"}, {"prop": "survivalRate", "label": "成活率", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "FINISHED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateProductionStatistic(row.id)
  if (command === 'finish') await finishProductionStatistic(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
