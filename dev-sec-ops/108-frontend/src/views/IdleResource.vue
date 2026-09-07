<template>
  <DataPage title="闲置资源" description="闲置资源识别、月成本和确认状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getIdleResourcePage, addIdleResource, updateIdleResource, deleteIdleResource, confirmIdleResource, ignoreIdleResource, cleanIdleResource } from '../api'
const api = { page: getIdleResourcePage, add: addIdleResource, update: updateIdleResource, delete: deleteIdleResource }
const columns = [{"prop": "resourceName", "label": "资源名称"}, {"prop": "resourceType", "label": "资源类型"}, {"prop": "accountName", "label": "云账号"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "idleDays", "label": "闲置天数"}, {"prop": "monthlyCost", "label": "月成本"}, {"prop": "detectedTime", "label": "发现时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "resourceName", "label": "资源名称"}, {"prop": "resourceType", "label": "资源类型", "type": "select", "options": [{"label": "ECS", "value": "ECS"}, {"label": "DISK", "value": "DISK"}, {"label": "SLB", "value": "SLB"}, {"label": "RDS", "value": "RDS"}]}, {"prop": "accountName", "label": "云账号"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "idleDays", "label": "闲置天数", "type": "number"}, {"prop": "monthlyCost", "label": "月成本", "type": "number"}, {"prop": "detectedTime", "label": "发现时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "PENDING", "value": "PENDING"}, {"label": "CONFIRMED", "value": "CONFIRMED"}, {"label": "IGNORED", "value": "IGNORED"}, {"label": "CLEANED", "value": "CLEANED"}]}]
const rowActions = [{"command": "confirm", "label": "确认闲置", "type": "primary"}, {"command": "ignore", "label": "忽略", "type": "primary"}, {"command": "clean", "label": "已清理", "type": "primary"}]
const defaults = {"resourceType": "ECS", "status": "PENDING"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'confirm') await confirmIdleResource(row.id)
  if (command === 'ignore') await ignoreIdleResource(row.id)
  if (command === 'clean') await cleanIdleResource(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
