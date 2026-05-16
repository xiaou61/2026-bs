<template><DataPage title="季节统计" description="统计编号、作业季节、统计类型、统计人员、统计时间和统计状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'STATION', 'DISPATCH']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addSeasonalStatistics, deleteSeasonalStatistics, finishSeasonalStatistics, getSeasonalStatisticsPage, processSeasonalStatistics, updateSeasonalStatistics } from '../api'
const api = { page: getSeasonalStatisticsPage, add: addSeasonalStatistics, update: updateSeasonalStatistics, delete: deleteSeasonalStatistics, process: processSeasonalStatistics, finish: finishSeasonalStatistics }
const columns = [{ prop: 'recordNo', label: '统计编号' }, { prop: 'recordName', label: '作业季节' }, { prop: 'category', label: '统计类型' }, { prop: 'ownerName', label: '统计人员' }, { prop: 'planTime', label: '统计时间' }, { prop: 'status', label: '统计状态' }, { prop: 'remark', label: '统计说明' }]
const formFields = [{ prop: 'recordNo', label: '统计编号' }, { prop: 'recordName', label: '作业季节' }, { prop: 'category', label: '统计类型' }, { prop: 'ownerName', label: '统计人员' }, { prop: 'planTime', label: '统计时间' }, { prop: 'status', label: '统计状态', type: 'select' }, { prop: 'remark', label: '统计说明', type: 'textarea' }]
</script>
