<template><DataPage title="道口分配" description="分配编号、分配车辆、作业类型、调度人、分配时间和分配状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'DISPATCHER', 'YARDMASTER', 'CARRIER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addDockAssignment, deleteDockAssignment, finishDockAssignment, getDockAssignmentPage, processDockAssignment, updateDockAssignment } from '../api'
const api = { page: getDockAssignmentPage, add: addDockAssignment, update: updateDockAssignment, delete: deleteDockAssignment, process: processDockAssignment, finish: finishDockAssignment }
const columns = [{ prop: 'recordNo', label: '分配编号' }, { prop: 'recordName', label: '分配车辆' }, { prop: 'category', label: '作业类型' }, { prop: 'ownerName', label: '调度人' }, { prop: 'planTime', label: '分配时间' }, { prop: 'status', label: '分配状态' }, { prop: 'remark', label: '分配说明' }]
const formFields = [{ prop: 'recordNo', label: '分配编号' }, { prop: 'recordName', label: '分配车辆' }, { prop: 'category', label: '作业类型' }, { prop: 'ownerName', label: '调度人' }, { prop: 'planTime', label: '分配时间' }, { prop: 'status', label: '分配状态', type: 'select' }, { prop: 'remark', label: '分配说明', type: 'textarea' }]
</script>
