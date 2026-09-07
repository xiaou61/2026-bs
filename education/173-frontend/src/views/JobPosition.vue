<template><DataPage title="招聘岗位" description="岗位编号、岗位名称、岗位类型、发布单位、发布时间和岗位状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CAREER', 'COLLEGE', 'COUNSELOR', 'STUDENT', 'EMPLOYER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addJobPosition, deleteJobPosition, finishJobPosition, getJobPositionPage, processJobPosition, updateJobPosition } from '../api'
const api = { page: getJobPositionPage, add: addJobPosition, update: updateJobPosition, delete: deleteJobPosition, process: processJobPosition, finish: finishJobPosition }
const columns = [{ prop: 'recordNo', label: '岗位编号' }, { prop: 'recordName', label: '岗位名称' }, { prop: 'category', label: '岗位类型' }, { prop: 'ownerName', label: '发布单位' }, { prop: 'planTime', label: '发布时间' }, { prop: 'status', label: '岗位状态' }, { prop: 'remark', label: '岗位说明' }]
const formFields = [{ prop: 'recordNo', label: '岗位编号' }, { prop: 'recordName', label: '岗位名称' }, { prop: 'category', label: '岗位类型' }, { prop: 'ownerName', label: '发布单位' }, { prop: 'planTime', label: '发布时间' }, { prop: 'status', label: '岗位状态', type: 'select' }, { prop: 'remark', label: '岗位说明', type: 'textarea' }]
</script>
