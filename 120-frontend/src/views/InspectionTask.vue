<template>
  <DataPage title="巡检任务" description="巡检任务、路线、执行人、计划时间、任务优先级和任务状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getInspectionTaskPage, addInspectionTask, updateInspectionTask, deleteInspectionTask, assignInspectionTask, startInspectionTask, finishInspectionTask } from '../api'
const api = { page: getInspectionTaskPage, add: addInspectionTask, update: updateInspectionTask, delete: deleteInspectionTask }
const columns = [{"prop": "taskNo", "label": "任务编号"}, {"prop": "routeName", "label": "巡检路线"}, {"prop": "inspectorName", "label": "执行人"}, {"prop": "planTime", "label": "计划时间"}, {"prop": "priorityLevel", "label": "优先级"}, {"prop": "pointTotal", "label": "点位总数"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "taskNo", "label": "任务编号"}, {"prop": "routeName", "label": "巡检路线"}, {"prop": "inspectorName", "label": "执行人"}, {"prop": "planTime", "label": "计划时间"}, {"prop": "priorityLevel", "label": "优先级", "type": "select", "options": [{"label": "高", "value": "高"}, {"label": "中", "value": "中"}, {"label": "低", "value": "低"}]}, {"prop": "pointTotal", "label": "点位总数", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "CREATED", "value": "CREATED"}, {"label": "ASSIGNED", "value": "ASSIGNED"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}]}]
const rowActions = [{"command": "assign", "label": "派发", "type": "primary"}, {"command": "start", "label": "开始", "type": "success"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "CREATED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'assign') await assignInspectionTask(row.id)
  if (command === 'start') await startInspectionTask(row.id)
  if (command === 'finish') await finishInspectionTask(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
