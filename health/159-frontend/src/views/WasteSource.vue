<template><DataPage title="医废来源" description="来源编号、来源名称、来源类型、责任人、启用时间和来源状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'HOSPITAL', 'REGULATOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addWasteSource, deleteWasteSource, finishWasteSource, getWasteSourcePage, processWasteSource, updateWasteSource } from '../api'
const api = { page: getWasteSourcePage, add: addWasteSource, update: updateWasteSource, delete: deleteWasteSource, process: processWasteSource, finish: finishWasteSource }
const columns = [{ prop: 'recordNo', label: '来源编号' }, { prop: 'recordName', label: '来源名称' }, { prop: 'category', label: '来源类型' }, { prop: 'ownerName', label: '责任人' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '来源状态' }, { prop: 'remark', label: '来源说明' }]
const formFields = [{ prop: 'recordNo', label: '来源编号' }, { prop: 'recordName', label: '来源名称' }, { prop: 'category', label: '来源类型' }, { prop: 'ownerName', label: '责任人' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '来源状态', type: 'select' }, { prop: 'remark', label: '来源说明', type: 'textarea' }]
</script>
