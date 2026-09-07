<template><DataPage title="社团档案" description="社团编号、社团名称、社团类型、指导老师、成立时间和社团状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'UNION']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addClubProfile, deleteClubProfile, finishClubProfile, getClubProfilePage, processClubProfile, updateClubProfile } from '../api'
const api = { page: getClubProfilePage, add: addClubProfile, update: updateClubProfile, delete: deleteClubProfile, process: processClubProfile, finish: finishClubProfile }
const columns = [{ prop: 'recordNo', label: '社团编号' }, { prop: 'recordName', label: '社团名称' }, { prop: 'category', label: '社团类型' }, { prop: 'ownerName', label: '指导老师' }, { prop: 'planTime', label: '成立时间' }, { prop: 'status', label: '社团状态' }, { prop: 'remark', label: '社团说明' }]
const formFields = [{ prop: 'recordNo', label: '社团编号' }, { prop: 'recordName', label: '社团名称' }, { prop: 'category', label: '社团类型' }, { prop: 'ownerName', label: '指导老师' }, { prop: 'planTime', label: '成立时间' }, { prop: 'status', label: '社团状态', type: 'select' }, { prop: 'remark', label: '社团说明', type: 'textarea' }]
</script>
