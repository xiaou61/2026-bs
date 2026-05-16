<template><DataPage title="积分记录" description="积分编号、积分事项、积分类型、获得人、积分时间和积分状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SECRETARY', 'ORGANIZER', 'VOLUNTEER', 'RESIDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addPointRecord, deletePointRecord, finishPointRecord, getPointRecordPage, processPointRecord, updatePointRecord } from '../api'
const api = { page: getPointRecordPage, add: addPointRecord, update: updatePointRecord, delete: deletePointRecord, process: processPointRecord, finish: finishPointRecord }
const columns = [{ prop: 'recordNo', label: '积分编号' }, { prop: 'recordName', label: '积分事项' }, { prop: 'category', label: '积分类型' }, { prop: 'ownerName', label: '获得人' }, { prop: 'planTime', label: '积分时间' }, { prop: 'status', label: '积分状态' }, { prop: 'remark', label: '积分说明' }]
const formFields = [{ prop: 'recordNo', label: '积分编号' }, { prop: 'recordName', label: '积分事项' }, { prop: 'category', label: '积分类型' }, { prop: 'ownerName', label: '获得人' }, { prop: 'planTime', label: '积分时间' }, { prop: 'status', label: '积分状态', type: 'select' }, { prop: 'remark', label: '积分说明', type: 'textarea' }]
</script>
