<template>
  <DataPage title="收益统计" description="统计编号、站点、日期、订单数、充电电量和收益金额管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getRevenueStatisticPage, addRevenueStatistic, updateRevenueStatistic, deleteRevenueStatistic, confirmRevenueStatistic, closeRevenueStatistic } from '../api'
const api = { page: getRevenueStatisticPage, add: addRevenueStatistic, update: updateRevenueStatistic, delete: deleteRevenueStatistic }
const columns = [{"prop": "statNo", "label": "统计编号"}, {"prop": "stationName", "label": "站点"}, {"prop": "statDate", "label": "统计日期"}, {"prop": "orderCount", "label": "订单数"}, {"prop": "energyKwh", "label": "充电电量"}, {"prop": "revenueAmount", "label": "收益金额"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "statNo", "label": "统计编号"}, {"prop": "stationName", "label": "站点"}, {"prop": "statDate", "label": "统计日期"}, {"prop": "orderCount", "label": "订单数", "type": "number"}, {"prop": "energyKwh", "label": "充电电量", "type": "number"}, {"prop": "revenueAmount", "label": "收益金额", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "AVAILABLE", "value": "AVAILABLE"}, {"label": "OCCUPIED", "value": "OCCUPIED"}, {"label": "CREATED", "value": "CREATED"}, {"label": "RESERVED", "value": "RESERVED"}, {"label": "CHARGING", "value": "CHARGING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PAID", "value": "PAID"}, {"label": "REFUNDED", "value": "REFUNDED"}, {"label": "WARNING", "value": "WARNING"}]}]
const rowActions = [{"command": "confirm", "label": "确认", "type": "success"}, {"command": "close", "label": "关闭", "type": "info"}]
const defaults = {"status": "FINISHED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'confirm') await confirmRevenueStatistic(row.id)
  if (command === 'close') await closeRevenueStatistic(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
