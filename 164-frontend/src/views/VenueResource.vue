<template><DataPage title="场地资源" description="场地编号、场地名称、场地类型、场馆管理员、开放时间和场地状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ORGANIZER', 'REFEREE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addVenueResource, deleteVenueResource, finishVenueResource, getVenueResourcePage, processVenueResource, updateVenueResource } from '../api'
const api = { page: getVenueResourcePage, add: addVenueResource, update: updateVenueResource, delete: deleteVenueResource, process: processVenueResource, finish: finishVenueResource }
const columns = [{ prop: 'recordNo', label: '场地编号' }, { prop: 'recordName', label: '场地名称' }, { prop: 'category', label: '场地类型' }, { prop: 'ownerName', label: '场馆管理员' }, { prop: 'planTime', label: '开放时间' }, { prop: 'status', label: '场地状态' }, { prop: 'remark', label: '场地说明' }]
const formFields = [{ prop: 'recordNo', label: '场地编号' }, { prop: 'recordName', label: '场地名称' }, { prop: 'category', label: '场地类型' }, { prop: 'ownerName', label: '场馆管理员' }, { prop: 'planTime', label: '开放时间' }, { prop: 'status', label: '场地状态', type: 'select' }, { prop: 'remark', label: '场地说明', type: 'textarea' }]
</script>
