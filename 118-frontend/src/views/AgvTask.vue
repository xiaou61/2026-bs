<template>
  <DataPage title="AGV任务" description="AGV任务单、任务类型、车辆、起止库位、优先级和执行状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getAgvTaskPage, addAgvTask, updateAgvTask, deleteAgvTask, dispatchAgvTask, executeAgvTask, completeAgvTask, cancelAgvTask } from '../api'
const api = { page: getAgvTaskPage, add: addAgvTask, update: updateAgvTask, delete: deleteAgvTask }
const columns = [{"prop": "taskNo", "label": "任务编号"}, {"prop": "taskType", "label": "任务类型"}, {"prop": "agvNo", "label": "AGV编号"}, {"prop": "sourceLocation", "label": "起点库位"}, {"prop": "targetLocation", "label": "终点库位"}, {"prop": "priorityLevel", "label": "优先级"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "taskNo", "label": "任务编号"}, {"prop": "taskType", "label": "任务类型", "type": "select", "options": [{"label": "入库上架", "value": "入库上架"}, {"label": "出库拣选", "value": "出库拣选"}, {"label": "库内移库", "value": "库内移库"}, {"label": "自动充电", "value": "自动充电"}]}, {"prop": "agvNo", "label": "AGV编号"}, {"prop": "sourceLocation", "label": "起点库位"}, {"prop": "targetLocation", "label": "终点库位"}, {"prop": "priorityLevel", "label": "优先级", "type": "select", "options": [{"label": "高", "value": "高"}, {"label": "中", "value": "中"}, {"label": "低", "value": "低"}]}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "WAIT_DISPATCH", "value": "WAIT_DISPATCH"}, {"label": "DISPATCHED", "value": "DISPATCHED"}, {"label": "RUNNING", "value": "RUNNING"}, {"label": "COMPLETED", "value": "COMPLETED"}, {"label": "CANCELLED", "value": "CANCELLED"}]}]
const rowActions = [{"command": "dispatch", "label": "派发", "type": "primary"}, {"command": "execute", "label": "执行", "type": "success"}, {"command": "complete", "label": "完成", "type": "success"}, {"command": "cancel", "label": "取消", "type": "danger"}]
const defaults = {"status": "WAIT_DISPATCH"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'dispatch') await dispatchAgvTask(row.id)
  if (command === 'execute') await executeAgvTask(row.id)
  if (command === 'complete') await completeAgvTask(row.id)
  if (command === 'cancel') await cancelAgvTask(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
