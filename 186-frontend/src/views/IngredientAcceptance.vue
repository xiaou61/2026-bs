<template><DataPage title="食材验收" description="验收编号、食材名称、验收类型、仓储人员、验收时间和验收状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CANTEEN', 'WAREHOUSE', 'CHEF', 'SCHOOL']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addIngredientAcceptance, deleteIngredientAcceptance, finishIngredientAcceptance, getIngredientAcceptancePage, processIngredientAcceptance, updateIngredientAcceptance } from '../api'
const api = { page: getIngredientAcceptancePage, add: addIngredientAcceptance, update: updateIngredientAcceptance, delete: deleteIngredientAcceptance, process: processIngredientAcceptance, finish: finishIngredientAcceptance }
const columns = [{ prop: 'recordNo', label: '验收编号' }, { prop: 'recordName', label: '食材名称' }, { prop: 'category', label: '验收类型' }, { prop: 'ownerName', label: '仓储人员' }, { prop: 'planTime', label: '验收时间' }, { prop: 'status', label: '验收状态' }, { prop: 'remark', label: '验收说明' }]
const formFields = [{ prop: 'recordNo', label: '验收编号' }, { prop: 'recordName', label: '食材名称' }, { prop: 'category', label: '验收类型' }, { prop: 'ownerName', label: '仓储人员' }, { prop: 'planTime', label: '验收时间' }, { prop: 'status', label: '验收状态', type: 'select' }, { prop: 'remark', label: '验收说明', type: 'textarea' }]
</script>
