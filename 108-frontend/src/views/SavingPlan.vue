<template>
  <DataPage title="节省计划" description="承诺消费、覆盖范围和节省计划执行管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getSavingPlanPage, addSavingPlan, updateSavingPlan, deleteSavingPlan, activateSavingPlan, finishSavingPlan, archiveSavingPlan } from '../api'
const api = { page: getSavingPlanPage, add: addSavingPlan, update: updateSavingPlan, delete: deleteSavingPlan }
const columns = [{"prop": "planName", "label": "计划名称"}, {"prop": "accountName", "label": "云账号"}, {"prop": "coverageScope", "label": "覆盖范围"}, {"prop": "commitAmount", "label": "承诺金额"}, {"prop": "expectedSaving", "label": "预计节省"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "planName", "label": "计划名称"}, {"prop": "accountName", "label": "云账号"}, {"prop": "coverageScope", "label": "覆盖范围"}, {"prop": "commitAmount", "label": "承诺金额", "type": "number"}, {"prop": "expectedSaving", "label": "预计节省", "type": "number"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "DRAFT", "value": "DRAFT"}, {"label": "ACTIVE", "value": "ACTIVE"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "ARCHIVED", "value": "ARCHIVED"}]}]
const rowActions = [{"command": "activate", "label": "生效", "type": "primary"}, {"command": "finish", "label": "完成", "type": "primary"}, {"command": "archive", "label": "归档", "type": "primary"}]
const defaults = {"status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateSavingPlan(row.id)
  if (command === 'finish') await finishSavingPlan(row.id)
  if (command === 'archive') await archiveSavingPlan(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
