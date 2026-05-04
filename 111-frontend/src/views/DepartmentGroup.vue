<template>
  <DataPage title="部门分组" description="演练部门、负责人和员工规模管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getDepartmentGroupPage, addDepartmentGroup, updateDepartmentGroup, deleteDepartmentGroup } from '../api'
const api = { page: getDepartmentGroupPage, add: addDepartmentGroup, update: updateDepartmentGroup, delete: deleteDepartmentGroup }
const columns = [{"prop": "groupName", "label": "分组名称"}, {"prop": "groupCode", "label": "分组编码"}, {"prop": "businessLine", "label": "业务线"}, {"prop": "leaderName", "label": "负责人"}, {"prop": "employeeCount", "label": "员工数"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "groupName", "label": "分组名称"}, {"prop": "groupCode", "label": "分组编码"}, {"prop": "businessLine", "label": "业务线"}, {"prop": "leaderName", "label": "负责人"}, {"prop": "employeeCount", "label": "员工数", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "ARCHIVED", "value": "ARCHIVED"}]}]
const rowActions = []
const defaults = {"status": "ACTIVE"}
const handleAction = () => {}
</script>
