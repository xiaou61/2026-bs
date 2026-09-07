<template>
  <DataPage title="ESG评分" description="ESG 评分、公司、环境分、社会分、治理分和等级维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getEsgScorePage, addEsgScore, updateEsgScore, deleteEsgScore, activateEsgScore, finishEsgScore } from '../api'
const api = { page: getEsgScorePage, add: addEsgScore, update: updateEsgScore, delete: deleteEsgScore }
const columns = [{"prop": "scoreNo", "label": "评分编号"}, {"prop": "companyName", "label": "公司名称"}, {"prop": "environmentScore", "label": "环境分"}, {"prop": "socialScore", "label": "社会分"}, {"prop": "governanceScore", "label": "治理分"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "scoreNo", "label": "评分编号"}, {"prop": "companyName", "label": "公司名称"}, {"prop": "environmentScore", "label": "环境分", "type": "number"}, {"prop": "socialScore", "label": "社会分", "type": "number"}, {"prop": "governanceScore", "label": "治理分", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "REJECTED", "value": "REJECTED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "FINISHED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateEsgScore(row.id)
  if (command === 'finish') await finishEsgScore(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
