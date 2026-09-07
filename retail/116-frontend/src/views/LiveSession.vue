<template>
  <DataPage title="直播场次" description="直播主题、渠道、主播、开播时间、GMV目标和场次状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getLiveSessionPage, addLiveSession, updateLiveSession, deleteLiveSession, scheduleLiveSession, startLiveSession, finishLiveSession } from '../api'
const api = { page: getLiveSessionPage, add: addLiveSession, update: updateLiveSession, delete: deleteLiveSession }
const columns = [{"prop": "sessionNo", "label": "场次编号"}, {"prop": "sessionTitle", "label": "直播主题"}, {"prop": "channelName", "label": "渠道"}, {"prop": "anchorName", "label": "主播"}, {"prop": "startTime", "label": "开播时间"}, {"prop": "targetGmv", "label": "GMV目标"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "sessionNo", "label": "场次编号"}, {"prop": "sessionTitle", "label": "直播主题"}, {"prop": "channelName", "label": "渠道"}, {"prop": "anchorName", "label": "主播"}, {"prop": "startTime", "label": "开播时间"}, {"prop": "targetGmv", "label": "GMV目标", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "PLANNED", "value": "PLANNED"}, {"label": "SCHEDULED", "value": "SCHEDULED"}, {"label": "LIVE", "value": "LIVE"}, {"label": "FINISHED", "value": "FINISHED"}]}]
const rowActions = [{"command": "schedule", "label": "排期", "type": "primary"}, {"command": "start", "label": "开播", "type": "success"}, {"command": "finish", "label": "结束", "type": "success"}]
const defaults = {"status": "PLANNED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'schedule') await scheduleLiveSession(row.id)
  if (command === 'start') await startLiveSession(row.id)
  if (command === 'finish') await finishLiveSession(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
