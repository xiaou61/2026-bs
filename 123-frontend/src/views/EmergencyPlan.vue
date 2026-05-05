<template>
  <DataPage title="应急预案" description="预案编号、名称、适用等级、责任部门、启动条件和预案状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getEmergencyPlanPage, addEmergencyPlan, updateEmergencyPlan, deleteEmergencyPlan, publishEmergencyPlan, disableEmergencyPlan } from '../api'
const api = { page: getEmergencyPlanPage, add: addEmergencyPlan, update: updateEmergencyPlan, delete: deleteEmergencyPlan }
const columns = [{"prop": "planNo", "label": "预案编号"}, {"prop": "planName", "label": "预案名称"}, {"prop": "applyLevel", "label": "适用等级"}, {"prop": "departmentName", "label": "责任部门"}, {"prop": "startCondition", "label": "启动条件"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "planNo", "label": "预案编号"}, {"prop": "planName", "label": "预案名称"}, {"prop": "applyLevel", "label": "适用等级", "type": "select", "options": [{"label": "蓝色", "value": "蓝色"}, {"label": "黄色", "value": "黄色"}, {"label": "橙色", "value": "橙色"}, {"label": "红色", "value": "红色"}]}, {"prop": "departmentName", "label": "责任部门"}, {"prop": "startCondition", "label": "启动条件"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "WARNING", "value": "WARNING"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "ASSIGNED", "value": "ASSIGNED"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "publish", "label": "发布", "type": "success"}, {"command": "disable", "label": "停用", "type": "danger"}]
const defaults = {"status": "PUBLISHED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'publish') await publishEmergencyPlan(row.id)
  if (command === 'disable') await disableEmergencyPlan(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
