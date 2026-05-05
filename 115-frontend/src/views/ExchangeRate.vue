<template>
  <DataPage title="汇率牌价" description="币种对、汇率值、生效日期、来源机构和启用状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getExchangeRatePage, addExchangeRate, updateExchangeRate, deleteExchangeRate, publishExchangeRate, expireExchangeRate } from '../api'
const api = { page: getExchangeRatePage, add: addExchangeRate, update: updateExchangeRate, delete: deleteExchangeRate }
const columns = [{"prop": "rateNo", "label": "汇率编号"}, {"prop": "baseCurrency", "label": "基准币种"}, {"prop": "targetCurrency", "label": "目标币种"}, {"prop": "rateValue", "label": "汇率"}, {"prop": "effectiveDate", "label": "生效日期"}, {"prop": "providerName", "label": "来源机构"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "rateNo", "label": "汇率编号"}, {"prop": "baseCurrency", "label": "基准币种", "type": "select", "options": [{"label": "USD", "value": "USD"}, {"label": "EUR", "value": "EUR"}, {"label": "JPY", "value": "JPY"}, {"label": "GBP", "value": "GBP"}]}, {"prop": "targetCurrency", "label": "目标币种", "type": "select", "options": [{"label": "CNY", "value": "CNY"}, {"label": "USD", "value": "USD"}, {"label": "EUR", "value": "EUR"}]}, {"prop": "rateValue", "label": "汇率", "type": "number"}, {"prop": "effectiveDate", "label": "生效日期"}, {"prop": "providerName", "label": "来源机构"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "DRAFT", "value": "DRAFT"}, {"label": "ACTIVE", "value": "ACTIVE"}, {"label": "EXPIRED", "value": "EXPIRED"}]}]
const rowActions = [{"command": "publish", "label": "启用", "type": "success"}, {"command": "expire", "label": "过期", "type": "warning"}]
const defaults = {"baseCurrency": "USD", "targetCurrency": "CNY", "status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'publish') await publishExchangeRate(row.id)
  if (command === 'expire') await expireExchangeRate(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
