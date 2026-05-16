<template><DataPage title="分类打卡" description="打卡编号、打卡居民、垃圾类别、督导人员、打卡时间和打卡状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'COMMUNITY', 'SUPERVISOR', 'RESIDENT', 'VOLUNTEER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addSortingCheckin, deleteSortingCheckin, finishSortingCheckin, getSortingCheckinPage, processSortingCheckin, updateSortingCheckin } from '../api'
const api = { page: getSortingCheckinPage, add: addSortingCheckin, update: updateSortingCheckin, delete: deleteSortingCheckin, process: processSortingCheckin, finish: finishSortingCheckin }
const columns = [{ prop: 'recordNo', label: '打卡编号' }, { prop: 'recordName', label: '打卡居民' }, { prop: 'category', label: '垃圾类别' }, { prop: 'ownerName', label: '督导人员' }, { prop: 'planTime', label: '打卡时间' }, { prop: 'status', label: '打卡状态' }, { prop: 'remark', label: '打卡说明' }]
const formFields = [{ prop: 'recordNo', label: '打卡编号' }, { prop: 'recordName', label: '打卡居民' }, { prop: 'category', label: '垃圾类别' }, { prop: 'ownerName', label: '督导人员' }, { prop: 'planTime', label: '打卡时间' }, { prop: 'status', label: '打卡状态', type: 'select' }, { prop: 'remark', label: '打卡说明', type: 'textarea' }]
</script>
