<template><DataPage title="活动立项" description="活动编号、活动名称、活动类型、负责人、活动时间和立项状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'UNION', 'CLUB', 'MEMBER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addActivityPlan, deleteActivityPlan, finishActivityPlan, getActivityPlanPage, processActivityPlan, updateActivityPlan } from '../api'
const api = { page: getActivityPlanPage, add: addActivityPlan, update: updateActivityPlan, delete: deleteActivityPlan, process: processActivityPlan, finish: finishActivityPlan }
const columns = [{ prop: 'recordNo', label: '活动编号' }, { prop: 'recordName', label: '活动名称' }, { prop: 'category', label: '活动类型' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '活动时间' }, { prop: 'status', label: '立项状态' }, { prop: 'remark', label: '活动说明' }]
const formFields = [{ prop: 'recordNo', label: '活动编号' }, { prop: 'recordName', label: '活动名称' }, { prop: 'category', label: '活动类型' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '活动时间' }, { prop: 'status', label: '立项状态', type: 'select' }, { prop: 'remark', label: '活动说明', type: 'textarea' }]
</script>
