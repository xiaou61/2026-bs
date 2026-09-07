<template>
  <DataPage title="救援队伍" description="队伍编号、名称、类型、人数、联系电话和驻点位置管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getRescueTeamPage, addRescueTeam, updateRescueTeam, deleteRescueTeam, enableRescueTeam, disableRescueTeam } from '../api'
const api = { page: getRescueTeamPage, add: addRescueTeam, update: updateRescueTeam, delete: deleteRescueTeam }
const columns = [{"prop": "teamNo", "label": "队伍编号"}, {"prop": "teamName", "label": "队伍名称"}, {"prop": "teamType", "label": "队伍类型"}, {"prop": "memberCount", "label": "人数"}, {"prop": "contactPhone", "label": "联系电话"}, {"prop": "baseLocation", "label": "驻点位置"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "teamNo", "label": "队伍编号"}, {"prop": "teamName", "label": "队伍名称"}, {"prop": "teamType", "label": "队伍类型"}, {"prop": "memberCount", "label": "人数", "type": "number"}, {"prop": "contactPhone", "label": "联系电话"}, {"prop": "baseLocation", "label": "驻点位置"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "WARNING", "value": "WARNING"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "ASSIGNED", "value": "ASSIGNED"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "enable", "label": "启用", "type": "success"}, {"command": "disable", "label": "停用", "type": "danger"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableRescueTeam(row.id)
  if (command === 'disable') await disableRescueTeam(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
