<template><DataPage title="点位投放" description="投放编号、点位名称、投放批次、运营人员、计划时间和投放状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'OPERATOR', 'SITE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addPlacementPlan, deletePlacementPlan, finishPlacementPlan, getPlacementPlanPage, processPlacementPlan, updatePlacementPlan } from '../api'
const api = { page: getPlacementPlanPage, add: addPlacementPlan, update: updatePlacementPlan, delete: deletePlacementPlan, process: processPlacementPlan, finish: finishPlacementPlan }
const columns = [{ prop: 'recordNo', label: '投放编号' }, { prop: 'recordName', label: '点位名称' }, { prop: 'category', label: '投放批次' }, { prop: 'ownerName', label: '运营人员' }, { prop: 'planTime', label: '计划时间' }, { prop: 'status', label: '投放状态' }, { prop: 'remark', label: '投放说明' }]
const formFields = [{ prop: 'recordNo', label: '投放编号' }, { prop: 'recordName', label: '点位名称' }, { prop: 'category', label: '投放批次' }, { prop: 'ownerName', label: '运营人员' }, { prop: 'planTime', label: '计划时间' }, { prop: 'status', label: '投放状态', type: 'select' }, { prop: 'remark', label: '投放说明', type: 'textarea' }]
</script>
