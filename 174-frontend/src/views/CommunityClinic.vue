<template><DataPage title="社区站点" description="站点编号、站点名称、服务区域、负责人、启用时间和站点状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'DOCTOR', 'NURSE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCommunityClinic, deleteCommunityClinic, finishCommunityClinic, getCommunityClinicPage, processCommunityClinic, updateCommunityClinic } from '../api'
const api = { page: getCommunityClinicPage, add: addCommunityClinic, update: updateCommunityClinic, delete: deleteCommunityClinic, process: processCommunityClinic, finish: finishCommunityClinic }
const columns = [{ prop: 'recordNo', label: '站点编号' }, { prop: 'recordName', label: '站点名称' }, { prop: 'category', label: '服务区域' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '站点状态' }, { prop: 'remark', label: '站点说明' }]
const formFields = [{ prop: 'recordNo', label: '站点编号' }, { prop: 'recordName', label: '站点名称' }, { prop: 'category', label: '服务区域' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '站点状态', type: 'select' }, { prop: 'remark', label: '站点说明', type: 'textarea' }]
</script>
