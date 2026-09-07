<template>
  <DataPage title="访问会话" description="零信任访问会话、来源地址和会话状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getAccessSessionPage, addAccessSession, updateAccessSession, deleteAccessSession, terminateAccessSession, expireAccessSession } from '../api'
const api = { page: getAccessSessionPage, add: addAccessSession, update: updateAccessSession, delete: deleteAccessSession }
const columns = [{"prop": "sessionNo", "label": "会话编号"}, {"prop": "deviceName", "label": "设备"}, {"prop": "employeeName", "label": "员工"}, {"prop": "resourceName", "label": "资源"}, {"prop": "sourceIp", "label": "来源IP"}, {"prop": "loginTime", "label": "登录时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "sessionNo", "label": "会话编号"}, {"prop": "deviceName", "label": "设备"}, {"prop": "employeeName", "label": "员工"}, {"prop": "resourceName", "label": "资源"}, {"prop": "sourceIp", "label": "来源IP"}, {"prop": "loginTime", "label": "登录时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "TERMINATED", "value": "TERMINATED"}, {"label": "EXPIRED", "value": "EXPIRED"}]}]
const rowActions = [{"command": "terminate", "label": "终止", "type": "primary"}, {"command": "expire", "label": "过期", "type": "primary"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'terminate') await terminateAccessSession(row.id)
  if (command === 'expire') await expireAccessSession(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
