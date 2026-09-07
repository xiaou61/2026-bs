<template><DataPage title="轮转科室" description="科室编号、科室名称、专业方向、科室主任、开放时间和科室状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SECRETARY', 'DIRECTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addDepartmentProfile, deleteDepartmentProfile, finishDepartmentProfile, getDepartmentProfilePage, processDepartmentProfile, updateDepartmentProfile } from '../api'
const api = { page: getDepartmentProfilePage, add: addDepartmentProfile, update: updateDepartmentProfile, delete: deleteDepartmentProfile, process: processDepartmentProfile, finish: finishDepartmentProfile }
const columns = [{ prop: 'recordNo', label: '科室编号' }, { prop: 'recordName', label: '科室名称' }, { prop: 'category', label: '专业方向' }, { prop: 'ownerName', label: '科室主任' }, { prop: 'planTime', label: '开放时间' }, { prop: 'status', label: '科室状态' }, { prop: 'remark', label: '科室说明' }]
const formFields = [{ prop: 'recordNo', label: '科室编号' }, { prop: 'recordName', label: '科室名称' }, { prop: 'category', label: '专业方向' }, { prop: 'ownerName', label: '科室主任' }, { prop: 'planTime', label: '开放时间' }, { prop: 'status', label: '科室状态', type: 'select' }, { prop: 'remark', label: '科室说明', type: 'textarea' }]
</script>
