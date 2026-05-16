<template><DataPage title="课堂巡查" description="巡查编号、巡查课堂、巡查类型、巡查人员、巡查时间和巡查状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ACADEMIC', 'INSPECTOR', 'TEACHER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addClassroomInspection, deleteClassroomInspection, finishClassroomInspection, getClassroomInspectionPage, processClassroomInspection, updateClassroomInspection } from '../api'
const api = { page: getClassroomInspectionPage, add: addClassroomInspection, update: updateClassroomInspection, delete: deleteClassroomInspection, process: processClassroomInspection, finish: finishClassroomInspection }
const columns = [{ prop: 'recordNo', label: '巡查编号' }, { prop: 'recordName', label: '巡查课堂' }, { prop: 'category', label: '巡查类型' }, { prop: 'ownerName', label: '巡查人员' }, { prop: 'planTime', label: '巡查时间' }, { prop: 'status', label: '巡查状态' }, { prop: 'remark', label: '巡查说明' }]
const formFields = [{ prop: 'recordNo', label: '巡查编号' }, { prop: 'recordName', label: '巡查课堂' }, { prop: 'category', label: '巡查类型' }, { prop: 'ownerName', label: '巡查人员' }, { prop: 'planTime', label: '巡查时间' }, { prop: 'status', label: '巡查状态', type: 'select' }, { prop: 'remark', label: '巡查说明', type: 'textarea' }]
</script>
