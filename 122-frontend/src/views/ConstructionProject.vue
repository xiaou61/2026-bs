<template>
  <DataPage title="工地项目" description="项目编号、名称、地址、承建单位、开工日期和风险等级管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getConstructionProjectPage, addConstructionProject, updateConstructionProject, deleteConstructionProject, enableConstructionProject, disableConstructionProject } from '../api'
const api = { page: getConstructionProjectPage, add: addConstructionProject, update: updateConstructionProject, delete: deleteConstructionProject }
const columns = [{"prop": "projectNo", "label": "项目编号"}, {"prop": "projectName", "label": "项目名称"}, {"prop": "locationName", "label": "项目地址"}, {"prop": "contractorName", "label": "承建单位"}, {"prop": "startDate", "label": "开工日期"}, {"prop": "riskLevel", "label": "风险等级"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "projectNo", "label": "项目编号"}, {"prop": "projectName", "label": "项目名称"}, {"prop": "locationName", "label": "项目地址"}, {"prop": "contractorName", "label": "承建单位"}, {"prop": "startDate", "label": "开工日期"}, {"prop": "riskLevel", "label": "风险等级", "type": "select", "options": [{"label": "高", "value": "高"}, {"label": "中", "value": "中"}, {"label": "低", "value": "低"}]}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "CREATED", "value": "CREATED"}, {"label": "ASSIGNED", "value": "ASSIGNED"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PASS", "value": "PASS"}, {"label": "FAIL", "value": "FAIL"}, {"label": "WARNING", "value": "WARNING"}]}]
const rowActions = [{"command": "enable", "label": "启用", "type": "success"}, {"command": "disable", "label": "停用", "type": "danger"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableConstructionProject(row.id)
  if (command === 'disable') await disableConstructionProject(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
