<template>
  <DataPage title="直播订单" description="直播间订单、场次、商品、买家、金额、支付渠道和履约状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getLiveOrderPage, addLiveOrder, updateLiveOrder, deleteLiveOrder, shipLiveOrder, completeLiveOrder } from '../api'
const api = { page: getLiveOrderPage, add: addLiveOrder, update: updateLiveOrder, delete: deleteLiveOrder }
const columns = [{"prop": "orderNo", "label": "订单编号"}, {"prop": "sessionTitle", "label": "直播场次"}, {"prop": "productName", "label": "商品"}, {"prop": "buyerName", "label": "买家"}, {"prop": "orderAmount", "label": "订单金额"}, {"prop": "payChannel", "label": "支付渠道"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "orderNo", "label": "订单编号"}, {"prop": "sessionTitle", "label": "直播场次"}, {"prop": "productName", "label": "商品"}, {"prop": "buyerName", "label": "买家"}, {"prop": "orderAmount", "label": "订单金额", "type": "number"}, {"prop": "payChannel", "label": "支付渠道", "type": "select", "options": [{"label": "微信支付", "value": "微信支付"}, {"label": "支付宝", "value": "支付宝"}, {"label": "抖音支付", "value": "抖音支付"}]}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "PAID", "value": "PAID"}, {"label": "SHIPPED", "value": "SHIPPED"}, {"label": "COMPLETED", "value": "COMPLETED"}]}]
const rowActions = [{"command": "ship", "label": "发货", "type": "primary"}, {"command": "complete", "label": "完成", "type": "success"}]
const defaults = {"status": "PAID"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'ship') await shipLiveOrder(row.id)
  if (command === 'complete') await completeLiveOrder(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
