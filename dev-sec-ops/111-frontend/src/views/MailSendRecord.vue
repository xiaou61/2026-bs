<template>
  <DataPage title="发送记录" description="演练邮件发送、打开和失败状态记录" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getMailSendRecordPage, addMailSendRecord, updateMailSendRecord, deleteMailSendRecord, sentMailSendRecord, openMailSendRecord, failMailSendRecord } from '../api'
const api = { page: getMailSendRecordPage, add: addMailSendRecord, update: updateMailSendRecord, delete: deleteMailSendRecord }
const columns = [{"prop": "recordNo", "label": "记录编号"}, {"prop": "campaignName", "label": "活动名称"}, {"prop": "employeeName", "label": "员工姓名"}, {"prop": "email", "label": "邮箱"}, {"prop": "sendTime", "label": "发送时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "recordNo", "label": "记录编号"}, {"prop": "campaignName", "label": "活动名称"}, {"prop": "employeeName", "label": "员工姓名"}, {"prop": "email", "label": "邮箱"}, {"prop": "sendTime", "label": "发送时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "WAIT_SEND", "value": "WAIT_SEND"}, {"label": "SENT", "value": "SENT"}, {"label": "OPENED", "value": "OPENED"}, {"label": "FAILED", "value": "FAILED"}]}]
const rowActions = [{"command": "sent", "label": "已发送", "type": "primary"}, {"command": "open", "label": "已打开", "type": "primary"}, {"command": "fail", "label": "失败", "type": "primary"}]
const defaults = {"status": "WAIT_SEND"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'sent') await sentMailSendRecord(row.id)
  if (command === 'open') await openMailSendRecord(row.id)
  if (command === 'fail') await failMailSendRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
