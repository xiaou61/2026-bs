<template><DataPage title="隐患闭环" description="隐患编号、隐患位置、隐患类型、发现人、整改期限和整改状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SAFETY', 'GUARDIAN']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addHiddenDanger, deleteHiddenDanger, finishHiddenDanger, getHiddenDangerPage, processHiddenDanger, updateHiddenDanger } from '../api'
const api = { page: getHiddenDangerPage, add: addHiddenDanger, update: updateHiddenDanger, delete: deleteHiddenDanger, process: processHiddenDanger, finish: finishHiddenDanger }
const columns = [{ prop: 'recordNo', label: '隐患编号' }, { prop: 'recordName', label: '隐患位置' }, { prop: 'category', label: '隐患类型' }, { prop: 'ownerName', label: '发现人' }, { prop: 'planTime', label: '整改期限' }, { prop: 'status', label: '整改状态' }, { prop: 'remark', label: '隐患描述' }]
const formFields = [{ prop: 'recordNo', label: '隐患编号' }, { prop: 'recordName', label: '隐患位置' }, { prop: 'category', label: '隐患类型' }, { prop: 'ownerName', label: '发现人' }, { prop: 'planTime', label: '整改期限' }, { prop: 'status', label: '整改状态', type: 'select' }, { prop: 'remark', label: '隐患描述', type: 'textarea' }]
</script>
