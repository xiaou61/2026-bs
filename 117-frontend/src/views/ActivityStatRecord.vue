<template>
  <DataPage title="活动统计" description="活动发放、领取、核销、GMV、转化率和统计状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getActivityStatRecordPage, addActivityStatRecord, updateActivityStatRecord, deleteActivityStatRecord, publishActivityStatRecord, archiveActivityStatRecord } from '../api'
const api = { page: getActivityStatRecordPage, add: addActivityStatRecord, update: updateActivityStatRecord, delete: deleteActivityStatRecord }
const columns = [{"prop": "statNo", "label": "统计编号"}, {"prop": "activityName", "label": "活动名称"}, {"prop": "merchantName", "label": "商户"}, {"prop": "issueCount", "label": "发放数"}, {"prop": "receiveCount", "label": "领取数"}, {"prop": "verifyCount", "label": "核销数"}, {"prop": "gmvAmount", "label": "GMV"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "statNo", "label": "统计编号"}, {"prop": "activityName", "label": "活动名称"}, {"prop": "merchantName", "label": "商户"}, {"prop": "issueCount", "label": "发放数", "type": "number"}, {"prop": "receiveCount", "label": "领取数", "type": "number"}, {"prop": "verifyCount", "label": "核销数", "type": "number"}, {"prop": "gmvAmount", "label": "GMV", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "DRAFT", "value": "DRAFT"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "ARCHIVED", "value": "ARCHIVED"}]}]
const rowActions = [{"command": "publish", "label": "发布", "type": "success"}, {"command": "archive", "label": "归档", "type": "warning"}]
const defaults = {"status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'publish') await publishActivityStatRecord(row.id)
  if (command === 'archive') await archiveActivityStatRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
