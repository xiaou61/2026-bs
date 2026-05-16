<template><DataPage title="就业统计" description="统计编号、统计学院、统计类型、统计人员、统计时间和统计状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CAREER', 'COLLEGE', 'COUNSELOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addEmploymentStatistics, deleteEmploymentStatistics, finishEmploymentStatistics, getEmploymentStatisticsPage, processEmploymentStatistics, updateEmploymentStatistics } from '../api'
const api = { page: getEmploymentStatisticsPage, add: addEmploymentStatistics, update: updateEmploymentStatistics, delete: deleteEmploymentStatistics, process: processEmploymentStatistics, finish: finishEmploymentStatistics }
const columns = [{ prop: 'recordNo', label: '统计编号' }, { prop: 'recordName', label: '统计学院' }, { prop: 'category', label: '统计类型' }, { prop: 'ownerName', label: '统计人员' }, { prop: 'planTime', label: '统计时间' }, { prop: 'status', label: '统计状态' }, { prop: 'remark', label: '统计说明' }]
const formFields = [{ prop: 'recordNo', label: '统计编号' }, { prop: 'recordName', label: '统计学院' }, { prop: 'category', label: '统计类型' }, { prop: 'ownerName', label: '统计人员' }, { prop: 'planTime', label: '统计时间' }, { prop: 'status', label: '统计状态', type: 'select' }, { prop: 'remark', label: '统计说明', type: 'textarea' }]
</script>
