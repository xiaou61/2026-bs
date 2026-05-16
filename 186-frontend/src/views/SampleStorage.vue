<template><DataPage title="留样存储" description="存储编号、留样登记、存储类型、管理人员、存储时间和存储状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CANTEEN', 'CHEF', 'INSPECTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addSampleStorage, deleteSampleStorage, finishSampleStorage, getSampleStoragePage, processSampleStorage, updateSampleStorage } from '../api'
const api = { page: getSampleStoragePage, add: addSampleStorage, update: updateSampleStorage, delete: deleteSampleStorage, process: processSampleStorage, finish: finishSampleStorage }
const columns = [{ prop: 'recordNo', label: '存储编号' }, { prop: 'recordName', label: '留样登记' }, { prop: 'category', label: '存储类型' }, { prop: 'ownerName', label: '管理人员' }, { prop: 'planTime', label: '存储时间' }, { prop: 'status', label: '存储状态' }, { prop: 'remark', label: '存储说明' }]
const formFields = [{ prop: 'recordNo', label: '存储编号' }, { prop: 'recordName', label: '留样登记' }, { prop: 'category', label: '存储类型' }, { prop: 'ownerName', label: '管理人员' }, { prop: 'planTime', label: '存储时间' }, { prop: 'status', label: '存储状态', type: 'select' }, { prop: 'remark', label: '存储说明', type: 'textarea' }]
</script>
