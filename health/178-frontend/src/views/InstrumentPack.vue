<template><DataPage title="器械包档案" description="包编号、器械包名称、包类型、供应中心、建包时间和器械包状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CSSD', 'ORNURSE', 'SURGEON']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addInstrumentPack, deleteInstrumentPack, finishInstrumentPack, getInstrumentPackPage, processInstrumentPack, updateInstrumentPack } from '../api'
const api = { page: getInstrumentPackPage, add: addInstrumentPack, update: updateInstrumentPack, delete: deleteInstrumentPack, process: processInstrumentPack, finish: finishInstrumentPack }
const columns = [{ prop: 'recordNo', label: '包编号' }, { prop: 'recordName', label: '器械包名称' }, { prop: 'category', label: '包类型' }, { prop: 'ownerName', label: '供应中心' }, { prop: 'planTime', label: '建包时间' }, { prop: 'status', label: '器械包状态' }, { prop: 'remark', label: '器械包说明' }]
const formFields = [{ prop: 'recordNo', label: '包编号' }, { prop: 'recordName', label: '器械包名称' }, { prop: 'category', label: '包类型' }, { prop: 'ownerName', label: '供应中心' }, { prop: 'planTime', label: '建包时间' }, { prop: 'status', label: '器械包状态', type: 'select' }, { prop: 'remark', label: '器械包说明', type: 'textarea' }]
</script>
