<template>
  <DataPage title="点击追踪" description="邮件点击、凭据提交和处置状态追踪" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getClickTrackingPage, addClickTracking, updateClickTracking, deleteClickTracking, confirmClickTracking, educateClickTracking, ignoreClickTracking } from '../api'
const api = { page: getClickTrackingPage, add: addClickTracking, update: updateClickTracking, delete: deleteClickTracking }
const columns = [{"prop": "trackNo", "label": "追踪编号"}, {"prop": "campaignName", "label": "活动名称"}, {"prop": "employeeName", "label": "员工姓名"}, {"prop": "clientIp", "label": "客户端IP"}, {"prop": "submitCredential", "label": "提交凭据"}, {"prop": "clickTime", "label": "点击时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "trackNo", "label": "追踪编号"}, {"prop": "campaignName", "label": "活动名称"}, {"prop": "employeeName", "label": "员工姓名"}, {"prop": "clientIp", "label": "客户端IP"}, {"prop": "submitCredential", "label": "提交凭据", "type": "select", "options": [{"label": "YES", "value": "YES"}, {"label": "NO", "value": "NO"}]}, {"prop": "clickTime", "label": "点击时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "PENDING", "value": "PENDING"}, {"label": "CONFIRMED", "value": "CONFIRMED"}, {"label": "TRAINED", "value": "TRAINED"}, {"label": "IGNORED", "value": "IGNORED"}]}]
const rowActions = [{"command": "confirm", "label": "确认", "type": "primary"}, {"command": "educate", "label": "培训", "type": "primary"}, {"command": "ignore", "label": "忽略", "type": "primary"}]
const defaults = {"status": "PENDING"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'confirm') await confirmClickTracking(row.id)
  if (command === 'educate') await educateClickTracking(row.id)
  if (command === 'ignore') await ignoreClickTracking(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
