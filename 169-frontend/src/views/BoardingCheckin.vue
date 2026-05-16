<template><DataPage title="上车核验" description="上车编号、核验线路、核验类型、核验人员、上车时间和上车状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SCHOOL', 'DISPATCH', 'DRIVER', 'TEACHER', 'GUARDIAN']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addBoardingCheckin, deleteBoardingCheckin, finishBoardingCheckin, getBoardingCheckinPage, processBoardingCheckin, updateBoardingCheckin } from '../api'
const api = { page: getBoardingCheckinPage, add: addBoardingCheckin, update: updateBoardingCheckin, delete: deleteBoardingCheckin, process: processBoardingCheckin, finish: finishBoardingCheckin }
const columns = [{ prop: 'recordNo', label: '上车编号' }, { prop: 'recordName', label: '核验线路' }, { prop: 'category', label: '核验类型' }, { prop: 'ownerName', label: '核验人员' }, { prop: 'planTime', label: '上车时间' }, { prop: 'status', label: '上车状态' }, { prop: 'remark', label: '上车说明' }]
const formFields = [{ prop: 'recordNo', label: '上车编号' }, { prop: 'recordName', label: '核验线路' }, { prop: 'category', label: '核验类型' }, { prop: 'ownerName', label: '核验人员' }, { prop: 'planTime', label: '上车时间' }, { prop: 'status', label: '上车状态', type: 'select' }, { prop: 'remark', label: '上车说明', type: 'textarea' }]
</script>
