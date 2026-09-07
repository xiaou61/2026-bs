<template><DataPage title="传承人档案" description="传承人编号、传承人姓名、技艺方向、管理人员、建档时间和档案状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'WORKSHOP', 'TEACHER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addInheritorProfile, deleteInheritorProfile, finishInheritorProfile, getInheritorProfilePage, processInheritorProfile, updateInheritorProfile } from '../api'
const api = { page: getInheritorProfilePage, add: addInheritorProfile, update: updateInheritorProfile, delete: deleteInheritorProfile, process: processInheritorProfile, finish: finishInheritorProfile }
const columns = [{ prop: 'recordNo', label: '传承人编号' }, { prop: 'recordName', label: '传承人姓名' }, { prop: 'category', label: '技艺方向' }, { prop: 'ownerName', label: '管理人员' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '档案状态' }, { prop: 'remark', label: '传承人说明' }]
const formFields = [{ prop: 'recordNo', label: '传承人编号' }, { prop: 'recordName', label: '传承人姓名' }, { prop: 'category', label: '技艺方向' }, { prop: 'ownerName', label: '管理人员' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '档案状态', type: 'select' }, { prop: 'remark', label: '传承人说明', type: 'textarea' }]
</script>
