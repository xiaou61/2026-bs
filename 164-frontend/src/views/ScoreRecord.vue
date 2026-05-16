<template><DataPage title="裁判评分" description="评分编号、比赛对象、评分项目、裁判员、评分时间和评分状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ORGANIZER', 'REFEREE', 'COMMITTEE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addScoreRecord, deleteScoreRecord, finishScoreRecord, getScoreRecordPage, processScoreRecord, updateScoreRecord } from '../api'
const api = { page: getScoreRecordPage, add: addScoreRecord, update: updateScoreRecord, delete: deleteScoreRecord, process: processScoreRecord, finish: finishScoreRecord }
const columns = [{ prop: 'recordNo', label: '评分编号' }, { prop: 'recordName', label: '比赛对象' }, { prop: 'category', label: '评分项目' }, { prop: 'ownerName', label: '裁判员' }, { prop: 'planTime', label: '评分时间' }, { prop: 'status', label: '评分状态' }, { prop: 'remark', label: '评分说明' }]
const formFields = [{ prop: 'recordNo', label: '评分编号' }, { prop: 'recordName', label: '比赛对象' }, { prop: 'category', label: '评分项目' }, { prop: 'ownerName', label: '裁判员' }, { prop: 'planTime', label: '评分时间' }, { prop: 'status', label: '评分状态', type: 'select' }, { prop: 'remark', label: '评分说明', type: 'textarea' }]
</script>
