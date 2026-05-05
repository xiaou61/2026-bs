<template>
  <DataPage title="采收记录" description="采收编号、作物批次、采收日期、采收重量和质检状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getHarvestRecordPage, addHarvestRecord, updateHarvestRecord, deleteHarvestRecord, activateHarvestRecord, finishHarvestRecord } from '../api'
const api = { page: getHarvestRecordPage, add: addHarvestRecord, update: updateHarvestRecord, delete: deleteHarvestRecord }
const columns = [{"prop": "harvestNo", "label": "采收编号"}, {"prop": "batchNo", "label": "批次编号"}, {"prop": "harvestDate", "label": "采收日期"}, {"prop": "harvestWeight", "label": "采收重量"}, {"prop": "qualityLevel", "label": "质量等级"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "harvestNo", "label": "采收编号"}, {"prop": "batchNo", "label": "批次编号"}, {"prop": "harvestDate", "label": "采收日期"}, {"prop": "harvestWeight", "label": "采收重量", "type": "number"}, {"prop": "qualityLevel", "label": "质量等级"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "FINISHED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateHarvestRecord(row.id)
  if (command === 'finish') await finishHarvestRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
