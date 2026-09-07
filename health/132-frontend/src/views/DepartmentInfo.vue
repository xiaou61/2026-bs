<template>
  <DataPage title="科室信息" description="科室编号、科室名称、楼层、联系人和联系电话维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getDepartmentInfoPage, addDepartmentInfo, updateDepartmentInfo, deleteDepartmentInfo, activateDepartmentInfo, finishDepartmentInfo } from '../api'
const api = { page: getDepartmentInfoPage, add: addDepartmentInfo, update: updateDepartmentInfo, delete: deleteDepartmentInfo }
const columns = [{"prop": "deptNo", "label": "科室编号"}, {"prop": "deptName", "label": "科室名称"}, {"prop": "floorName", "label": "所在楼层"}, {"prop": "contactName", "label": "联系人"}, {"prop": "phoneNumber", "label": "联系电话"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "deptNo", "label": "科室编号"}, {"prop": "deptName", "label": "科室名称"}, {"prop": "floorName", "label": "所在楼层"}, {"prop": "contactName", "label": "联系人"}, {"prop": "phoneNumber", "label": "联系电话"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateDepartmentInfo(row.id)
  if (command === 'finish') await finishDepartmentInfo(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
