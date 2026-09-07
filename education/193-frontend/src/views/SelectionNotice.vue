<template><DataPage title="选拔公告" description="公告编号、公告名称、选拔批次、发布人员、发布时间和公告状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ACADEMIC', 'REVIEWER', 'STUDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addSelectionNotice, deleteSelectionNotice, finishSelectionNotice, getSelectionNoticePage, processSelectionNotice, updateSelectionNotice } from '../api'
const api = { page: getSelectionNoticePage, add: addSelectionNotice, update: updateSelectionNotice, delete: deleteSelectionNotice, process: processSelectionNotice, finish: finishSelectionNotice }
const columns = [{ prop: 'recordNo', label: '公告编号' }, { prop: 'recordName', label: '公告名称' }, { prop: 'category', label: '选拔批次' }, { prop: 'ownerName', label: '发布人员' }, { prop: 'planTime', label: '发布时间' }, { prop: 'status', label: '公告状态' }, { prop: 'remark', label: '公告说明' }]
const formFields = [{ prop: 'recordNo', label: '公告编号' }, { prop: 'recordName', label: '公告名称' }, { prop: 'category', label: '选拔批次' }, { prop: 'ownerName', label: '发布人员' }, { prop: 'planTime', label: '发布时间' }, { prop: 'status', label: '公告状态', type: 'select' }, { prop: 'remark', label: '公告说明', type: 'textarea' }]
</script>
