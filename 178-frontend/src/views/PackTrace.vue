<template><DataPage title="器械包追踪" description="追踪编号、器械包名称、追踪类型、责任人员、追踪时间和追踪状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ORNURSE', 'CSSD', 'STERILE', 'QA', 'SURGEON']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addPackTrace, deletePackTrace, finishPackTrace, getPackTracePage, processPackTrace, updatePackTrace } from '../api'
const api = { page: getPackTracePage, add: addPackTrace, update: updatePackTrace, delete: deletePackTrace, process: processPackTrace, finish: finishPackTrace }
const columns = [{ prop: 'recordNo', label: '追踪编号' }, { prop: 'recordName', label: '器械包名称' }, { prop: 'category', label: '追踪类型' }, { prop: 'ownerName', label: '责任人员' }, { prop: 'planTime', label: '追踪时间' }, { prop: 'status', label: '追踪状态' }, { prop: 'remark', label: '追踪说明' }]
const formFields = [{ prop: 'recordNo', label: '追踪编号' }, { prop: 'recordName', label: '器械包名称' }, { prop: 'category', label: '追踪类型' }, { prop: 'ownerName', label: '责任人员' }, { prop: 'planTime', label: '追踪时间' }, { prop: 'status', label: '追踪状态', type: 'select' }, { prop: 'remark', label: '追踪说明', type: 'textarea' }]
</script>
