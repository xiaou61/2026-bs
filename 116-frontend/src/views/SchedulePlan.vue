<template>
  <DataPage title="排期计划" description="排期日期、直播场次、商品数量、坑位时段、负责人和确认状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getSchedulePlanPage, addSchedulePlan, updateSchedulePlan, deleteSchedulePlan, confirmSchedulePlan, cancelSchedulePlan } from '../api'
const api = { page: getSchedulePlanPage, add: addSchedulePlan, update: updateSchedulePlan, delete: deleteSchedulePlan }
const columns = [{"prop": "planNo", "label": "计划编号"}, {"prop": "sessionTitle", "label": "直播场次"}, {"prop": "planDate", "label": "排期日期"}, {"prop": "anchorName", "label": "主播"}, {"prop": "productCount", "label": "商品数"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "planNo", "label": "计划编号"}, {"prop": "sessionTitle", "label": "直播场次"}, {"prop": "planDate", "label": "排期日期"}, {"prop": "anchorName", "label": "主播"}, {"prop": "productCount", "label": "商品数", "type": "number"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "DRAFT", "value": "DRAFT"}, {"label": "CONFIRMED", "value": "CONFIRMED"}, {"label": "CANCELLED", "value": "CANCELLED"}]}]
const rowActions = [{"command": "confirm", "label": "确认", "type": "success"}, {"command": "cancel", "label": "取消", "type": "danger"}]
const defaults = {"status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'confirm') await confirmSchedulePlan(row.id)
  if (command === 'cancel') await cancelSchedulePlan(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
