<template><DataPage title="卫生巡检" description="巡检编号、后厨区域、巡检类型、巡检人员、巡检时间和巡检状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CANTEEN', 'INSPECTOR', 'SCHOOL']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addHygieneInspection, deleteHygieneInspection, finishHygieneInspection, getHygieneInspectionPage, processHygieneInspection, updateHygieneInspection } from '../api'
const api = { page: getHygieneInspectionPage, add: addHygieneInspection, update: updateHygieneInspection, delete: deleteHygieneInspection, process: processHygieneInspection, finish: finishHygieneInspection }
const columns = [{ prop: 'recordNo', label: '巡检编号' }, { prop: 'recordName', label: '后厨区域' }, { prop: 'category', label: '巡检类型' }, { prop: 'ownerName', label: '巡检人员' }, { prop: 'planTime', label: '巡检时间' }, { prop: 'status', label: '巡检状态' }, { prop: 'remark', label: '巡检说明' }]
const formFields = [{ prop: 'recordNo', label: '巡检编号' }, { prop: 'recordName', label: '后厨区域' }, { prop: 'category', label: '巡检类型' }, { prop: 'ownerName', label: '巡检人员' }, { prop: 'planTime', label: '巡检时间' }, { prop: 'status', label: '巡检状态', type: 'select' }, { prop: 'remark', label: '巡检说明', type: 'textarea' }]
</script>
