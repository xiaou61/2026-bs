<template><DataPage title="饮食禁忌" description="禁忌编号、老人档案、禁忌类型、记录人员、记录时间和禁忌状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'DIETITIAN', 'ELDER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addDietaryRestriction, deleteDietaryRestriction, finishDietaryRestriction, getDietaryRestrictionPage, processDietaryRestriction, updateDietaryRestriction } from '../api'
const api = { page: getDietaryRestrictionPage, add: addDietaryRestriction, update: updateDietaryRestriction, delete: deleteDietaryRestriction, process: processDietaryRestriction, finish: finishDietaryRestriction }
const columns = [{ prop: 'recordNo', label: '禁忌编号' }, { prop: 'recordName', label: '老人档案' }, { prop: 'category', label: '禁忌类型' }, { prop: 'ownerName', label: '记录人员' }, { prop: 'planTime', label: '记录时间' }, { prop: 'status', label: '禁忌状态' }, { prop: 'remark', label: '禁忌说明' }]
const formFields = [{ prop: 'recordNo', label: '禁忌编号' }, { prop: 'recordName', label: '老人档案' }, { prop: 'category', label: '禁忌类型' }, { prop: 'ownerName', label: '记录人员' }, { prop: 'planTime', label: '记录时间' }, { prop: 'status', label: '禁忌状态', type: 'select' }, { prop: 'remark', label: '禁忌说明', type: 'textarea' }]
</script>
