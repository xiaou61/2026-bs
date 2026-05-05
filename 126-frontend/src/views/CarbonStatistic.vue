<template>
  <DataPage title="碳排统计" description="统计编号、家庭、月份、用电量、碳排量和同比变化管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getCarbonStatisticPage, addCarbonStatistic, updateCarbonStatistic, deleteCarbonStatistic, confirmCarbonStatistic, publishCarbonStatistic } from '../api'
const api = { page: getCarbonStatisticPage, add: addCarbonStatistic, update: updateCarbonStatistic, delete: deleteCarbonStatistic }
const columns = [{"prop": "statNo", "label": "统计编号"}, {"prop": "homeNo", "label": "家庭编号"}, {"prop": "statMonth", "label": "统计月份"}, {"prop": "energyKwh", "label": "用电量"}, {"prop": "carbonKg", "label": "碳排kg"}, {"prop": "yearRate", "label": "同比变化"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "statNo", "label": "统计编号"}, {"prop": "homeNo", "label": "家庭编号"}, {"prop": "statMonth", "label": "统计月份"}, {"prop": "energyKwh", "label": "用电量", "type": "number"}, {"prop": "carbonKg", "label": "碳排kg", "type": "number"}, {"prop": "yearRate", "label": "同比变化", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "WARNING", "value": "WARNING"}, {"label": "CREATED", "value": "CREATED"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "PAID", "value": "PAID"}, {"label": "OVER_BUDGET", "value": "OVER_BUDGET"}]}]
const rowActions = [{"command": "confirm", "label": "确认", "type": "success"}, {"command": "publish", "label": "发布", "type": "primary"}]
const defaults = {"status": "FINISHED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'confirm') await confirmCarbonStatistic(row.id)
  if (command === 'publish') await publishCarbonStatistic(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
