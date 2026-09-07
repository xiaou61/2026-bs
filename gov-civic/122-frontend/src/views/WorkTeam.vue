<template>
  <DataPage title="施工班组" description="班组编号、名称、班组长、人数、工种和联系电话管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getWorkTeamPage, addWorkTeam, updateWorkTeam, deleteWorkTeam, enableWorkTeam, disableWorkTeam } from '../api'
const api = { page: getWorkTeamPage, add: addWorkTeam, update: updateWorkTeam, delete: deleteWorkTeam }
const columns = [{"prop": "teamNo", "label": "班组编号"}, {"prop": "teamName", "label": "班组名称"}, {"prop": "leaderName", "label": "班组长"}, {"prop": "workerCount", "label": "人数"}, {"prop": "workType", "label": "工种"}, {"prop": "contactPhone", "label": "联系电话"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "teamNo", "label": "班组编号"}, {"prop": "teamName", "label": "班组名称"}, {"prop": "leaderName", "label": "班组长"}, {"prop": "workerCount", "label": "人数", "type": "number"}, {"prop": "workType", "label": "工种"}, {"prop": "contactPhone", "label": "联系电话"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "CREATED", "value": "CREATED"}, {"label": "ASSIGNED", "value": "ASSIGNED"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PASS", "value": "PASS"}, {"label": "FAIL", "value": "FAIL"}, {"label": "WARNING", "value": "WARNING"}]}]
const rowActions = [{"command": "enable", "label": "启用", "type": "success"}, {"command": "disable", "label": "停用", "type": "danger"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableWorkTeam(row.id)
  if (command === 'disable') await disableWorkTeam(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
