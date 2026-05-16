<template><DataPage title="帮扶记录" description="帮扶编号、帮扶学生、帮扶类型、辅导人员、帮扶时间和帮扶状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CAREER', 'COLLEGE', 'COUNSELOR', 'STUDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addAssistanceRecord, deleteAssistanceRecord, finishAssistanceRecord, getAssistanceRecordPage, processAssistanceRecord, updateAssistanceRecord } from '../api'
const api = { page: getAssistanceRecordPage, add: addAssistanceRecord, update: updateAssistanceRecord, delete: deleteAssistanceRecord, process: processAssistanceRecord, finish: finishAssistanceRecord }
const columns = [{ prop: 'recordNo', label: '帮扶编号' }, { prop: 'recordName', label: '帮扶学生' }, { prop: 'category', label: '帮扶类型' }, { prop: 'ownerName', label: '辅导人员' }, { prop: 'planTime', label: '帮扶时间' }, { prop: 'status', label: '帮扶状态' }, { prop: 'remark', label: '帮扶说明' }]
const formFields = [{ prop: 'recordNo', label: '帮扶编号' }, { prop: 'recordName', label: '帮扶学生' }, { prop: 'category', label: '帮扶类型' }, { prop: 'ownerName', label: '辅导人员' }, { prop: 'planTime', label: '帮扶时间' }, { prop: 'status', label: '帮扶状态', type: 'select' }, { prop: 'remark', label: '帮扶说明', type: 'textarea' }]
</script>
