<template>
  <DataPage title="排放因子" description="排放因子编号、名称、能源类型、因子值和单位维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getEmissionFactorPage, addEmissionFactor, updateEmissionFactor, deleteEmissionFactor, activateEmissionFactor, finishEmissionFactor } from '../api'
const api = { page: getEmissionFactorPage, add: addEmissionFactor, update: updateEmissionFactor, delete: deleteEmissionFactor }
const columns = [{"prop": "factorNo", "label": "因子编号"}, {"prop": "factorName", "label": "因子名称"}, {"prop": "energyType", "label": "能源类型"}, {"prop": "factorValue", "label": "因子值"}, {"prop": "unitName", "label": "单位"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "factorNo", "label": "因子编号"}, {"prop": "factorName", "label": "因子名称"}, {"prop": "energyType", "label": "能源类型"}, {"prop": "factorValue", "label": "因子值", "type": "number"}, {"prop": "unitName", "label": "单位"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "REJECTED", "value": "REJECTED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateEmissionFactor(row.id)
  if (command === 'finish') await finishEmissionFactor(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
