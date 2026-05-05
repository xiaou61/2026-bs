<template>
  <DataPage title="使用记录" description="备件装机记录、设备、装机时间、累计运行小时和使用状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getUsageRecordPage, addUsageRecord, updateUsageRecord, deleteUsageRecord, replaceUsageRecord, scrapUsageRecord } from '../api'
const api = { page: getUsageRecordPage, add: addUsageRecord, update: updateUsageRecord, delete: deleteUsageRecord }
const columns = [{"prop": "usageNo", "label": "使用编号"}, {"prop": "partName", "label": "备件名称"}, {"prop": "equipmentName", "label": "装机设备"}, {"prop": "installTime", "label": "装机时间"}, {"prop": "runtimeHours", "label": "累计小时"}, {"prop": "installerName", "label": "安装人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "usageNo", "label": "使用编号"}, {"prop": "partName", "label": "备件名称"}, {"prop": "equipmentName", "label": "装机设备"}, {"prop": "installTime", "label": "装机时间"}, {"prop": "runtimeHours", "label": "累计小时", "type": "number"}, {"prop": "installerName", "label": "安装人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "IN_USE", "value": "IN_USE"}, {"label": "REPLACED", "value": "REPLACED"}, {"label": "SCRAPPED", "value": "SCRAPPED"}]}]
const rowActions = [{"command": "replace", "label": "更换", "type": "warning"}, {"command": "scrap", "label": "报废", "type": "danger"}]
const defaults = {"status": "IN_USE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'replace') await replaceUsageRecord(row.id)
  if (command === 'scrap') await scrapUsageRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
