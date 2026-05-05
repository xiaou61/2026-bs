<template>
  <DataPage title="主播绩效" description="主播场次、GMV、订单数、转化率、评分和绩效状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getAnchorPerformancePage, addAnchorPerformance, updateAnchorPerformance, deleteAnchorPerformance, confirmAnchorPerformance, archiveAnchorPerformance } from '../api'
const api = { page: getAnchorPerformancePage, add: addAnchorPerformance, update: updateAnchorPerformance, delete: deleteAnchorPerformance }
const columns = [{"prop": "performanceNo", "label": "绩效编号"}, {"prop": "anchorName", "label": "主播"}, {"prop": "sessionTitle", "label": "直播场次"}, {"prop": "gmvAmount", "label": "GMV"}, {"prop": "orderCount", "label": "订单数"}, {"prop": "conversionRate", "label": "转化率"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "performanceNo", "label": "绩效编号"}, {"prop": "anchorName", "label": "主播"}, {"prop": "sessionTitle", "label": "直播场次"}, {"prop": "gmvAmount", "label": "GMV", "type": "number"}, {"prop": "orderCount", "label": "订单数", "type": "number"}, {"prop": "conversionRate", "label": "转化率", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "DRAFT", "value": "DRAFT"}, {"label": "CONFIRMED", "value": "CONFIRMED"}, {"label": "ARCHIVED", "value": "ARCHIVED"}]}]
const rowActions = [{"command": "confirm", "label": "确认", "type": "success"}, {"command": "archive", "label": "归档", "type": "warning"}]
const defaults = {"status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'confirm') await confirmAnchorPerformance(row.id)
  if (command === 'archive') await archiveAnchorPerformance(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
