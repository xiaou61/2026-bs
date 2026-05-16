<template><DataPage title="读者档案" description="读者编号、读者姓名、所在班级、负责老师、建档时间和读者状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'LIBRARY', 'TEACHER', 'STUDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addReaderProfile, deleteReaderProfile, finishReaderProfile, getReaderProfilePage, processReaderProfile, updateReaderProfile } from '../api'
const api = { page: getReaderProfilePage, add: addReaderProfile, update: updateReaderProfile, delete: deleteReaderProfile, process: processReaderProfile, finish: finishReaderProfile }
const columns = [{ prop: 'recordNo', label: '读者编号' }, { prop: 'recordName', label: '读者姓名' }, { prop: 'category', label: '所在班级' }, { prop: 'ownerName', label: '负责老师' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '读者状态' }, { prop: 'remark', label: '读者说明' }]
const formFields = [{ prop: 'recordNo', label: '读者编号' }, { prop: 'recordName', label: '读者姓名' }, { prop: 'category', label: '所在班级' }, { prop: 'ownerName', label: '负责老师' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '读者状态', type: 'select' }, { prop: 'remark', label: '读者说明', type: 'textarea' }]
</script>
