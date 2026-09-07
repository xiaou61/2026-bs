<template>
  <DataPage title="指标数据" description="指标数据、指标名称、填报值、单位和数据来源维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getIndicatorDataPage, addIndicatorData, updateIndicatorData, deleteIndicatorData, submitIndicatorData, approveIndicatorData } from '../api'
const api = { page: getIndicatorDataPage, add: addIndicatorData, update: updateIndicatorData, delete: deleteIndicatorData }
const columns = [{"prop": "dataNo", "label": "数据编号"}, {"prop": "indicatorName", "label": "指标名称"}, {"prop": "companyName", "label": "公司名称"}, {"prop": "dataValue", "label": "填报值"}, {"prop": "unitName", "label": "单位"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "dataNo", "label": "数据编号"}, {"prop": "indicatorName", "label": "指标名称"}, {"prop": "companyName", "label": "公司名称"}, {"prop": "dataValue", "label": "填报值", "type": "number"}, {"prop": "unitName", "label": "单位"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "REJECTED", "value": "REJECTED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "success"}]
const defaults = {"status": "SUBMITTED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitIndicatorData(row.id)
  if (command === 'approve') await approveIndicatorData(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
