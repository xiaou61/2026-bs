<template><DataPage title="器械明细" description="器械编号、器械名称、器械类型、保管人员、登记时间和器械状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CSSD', 'STERILE', 'QA']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addInstrumentItem, deleteInstrumentItem, finishInstrumentItem, getInstrumentItemPage, processInstrumentItem, updateInstrumentItem } from '../api'
const api = { page: getInstrumentItemPage, add: addInstrumentItem, update: updateInstrumentItem, delete: deleteInstrumentItem, process: processInstrumentItem, finish: finishInstrumentItem }
const columns = [{ prop: 'recordNo', label: '器械编号' }, { prop: 'recordName', label: '器械名称' }, { prop: 'category', label: '器械类型' }, { prop: 'ownerName', label: '保管人员' }, { prop: 'planTime', label: '登记时间' }, { prop: 'status', label: '器械状态' }, { prop: 'remark', label: '器械说明' }]
const formFields = [{ prop: 'recordNo', label: '器械编号' }, { prop: 'recordName', label: '器械名称' }, { prop: 'category', label: '器械类型' }, { prop: 'ownerName', label: '保管人员' }, { prop: 'planTime', label: '登记时间' }, { prop: 'status', label: '器械状态', type: 'select' }, { prop: 'remark', label: '器械说明', type: 'textarea' }]
</script>
