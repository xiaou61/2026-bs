<template><DataPage title="保质期规则" description="规则编号、适用品类、预警类型、负责人、生效时间和规则状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'MANAGER', 'STOCK']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addShelfLifeRule, deleteShelfLifeRule, finishShelfLifeRule, getShelfLifeRulePage, processShelfLifeRule, updateShelfLifeRule } from '../api'
const api = { page: getShelfLifeRulePage, add: addShelfLifeRule, update: updateShelfLifeRule, delete: deleteShelfLifeRule, process: processShelfLifeRule, finish: finishShelfLifeRule }
const columns = [{ prop: 'recordNo', label: '规则编号' }, { prop: 'recordName', label: '适用品类' }, { prop: 'category', label: '预警类型' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '生效时间' }, { prop: 'status', label: '规则状态' }, { prop: 'remark', label: '规则说明' }]
const formFields = [{ prop: 'recordNo', label: '规则编号' }, { prop: 'recordName', label: '适用品类' }, { prop: 'category', label: '预警类型' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '生效时间' }, { prop: 'status', label: '规则状态', type: 'select' }, { prop: 'remark', label: '规则说明', type: 'textarea' }]
</script>
