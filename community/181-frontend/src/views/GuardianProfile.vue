<template><DataPage title="监护人档案" description="监护编号、监护姓名、关系类型、关联儿童、登记时间和监护状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'PARENT', 'SECURITY']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addGuardianProfile, deleteGuardianProfile, finishGuardianProfile, getGuardianProfilePage, processGuardianProfile, updateGuardianProfile } from '../api'
const api = { page: getGuardianProfilePage, add: addGuardianProfile, update: updateGuardianProfile, delete: deleteGuardianProfile, process: processGuardianProfile, finish: finishGuardianProfile }
const columns = [{ prop: 'recordNo', label: '监护编号' }, { prop: 'recordName', label: '监护姓名' }, { prop: 'category', label: '关系类型' }, { prop: 'ownerName', label: '关联儿童' }, { prop: 'planTime', label: '登记时间' }, { prop: 'status', label: '监护状态' }, { prop: 'remark', label: '监护说明' }]
const formFields = [{ prop: 'recordNo', label: '监护编号' }, { prop: 'recordName', label: '监护姓名' }, { prop: 'category', label: '关系类型' }, { prop: 'ownerName', label: '关联儿童' }, { prop: 'planTime', label: '登记时间' }, { prop: 'status', label: '监护状态', type: 'select' }, { prop: 'remark', label: '监护说明', type: 'textarea' }]
</script>
