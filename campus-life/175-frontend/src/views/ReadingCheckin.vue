<template><DataPage title="读书打卡" description="打卡编号、打卡图书、打卡类型、打卡读者、打卡时间和打卡状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'LIBRARY', 'STUDENT', 'CLUB', 'TEACHER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addReadingCheckin, deleteReadingCheckin, finishReadingCheckin, getReadingCheckinPage, processReadingCheckin, updateReadingCheckin } from '../api'
const api = { page: getReadingCheckinPage, add: addReadingCheckin, update: updateReadingCheckin, delete: deleteReadingCheckin, process: processReadingCheckin, finish: finishReadingCheckin }
const columns = [{ prop: 'recordNo', label: '打卡编号' }, { prop: 'recordName', label: '打卡图书' }, { prop: 'category', label: '打卡类型' }, { prop: 'ownerName', label: '打卡读者' }, { prop: 'planTime', label: '打卡时间' }, { prop: 'status', label: '打卡状态' }, { prop: 'remark', label: '打卡说明' }]
const formFields = [{ prop: 'recordNo', label: '打卡编号' }, { prop: 'recordName', label: '打卡图书' }, { prop: 'category', label: '打卡类型' }, { prop: 'ownerName', label: '打卡读者' }, { prop: 'planTime', label: '打卡时间' }, { prop: 'status', label: '打卡状态', type: 'select' }, { prop: 'remark', label: '打卡说明', type: 'textarea' }]
</script>
