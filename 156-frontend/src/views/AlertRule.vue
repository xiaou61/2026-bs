<template><DataPage title="预警策略" description="策略编号、策略名称、策略类型、配置人、生效时间和策略状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'LOGISTICS', 'ENERGY']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addAlertRule, deleteAlertRule, finishAlertRule, getAlertRulePage, processAlertRule, updateAlertRule } from '../api'
const api = { page: getAlertRulePage, add: addAlertRule, update: updateAlertRule, delete: deleteAlertRule, process: processAlertRule, finish: finishAlertRule }
const columns = [{ prop: 'recordNo', label: '策略编号' }, { prop: 'recordName', label: '策略名称' }, { prop: 'category', label: '策略类型' }, { prop: 'ownerName', label: '配置人' }, { prop: 'planTime', label: '生效时间' }, { prop: 'status', label: '策略状态' }, { prop: 'remark', label: '策略说明' }]
const formFields = [{ prop: 'recordNo', label: '策略编号' }, { prop: 'recordName', label: '策略名称' }, { prop: 'category', label: '策略类型' }, { prop: 'ownerName', label: '配置人' }, { prop: 'planTime', label: '生效时间' }, { prop: 'status', label: '策略状态', type: 'select' }, { prop: 'remark', label: '策略说明', type: 'textarea' }]
</script>
