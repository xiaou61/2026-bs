<template><DataPage title="党员档案" description="党员编号、党员姓名、所属支部、联系方式、入党时间和档案状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SECRETARY', 'RESIDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addMemberProfile, deleteMemberProfile, finishMemberProfile, getMemberProfilePage, processMemberProfile, updateMemberProfile } from '../api'
const api = { page: getMemberProfilePage, add: addMemberProfile, update: updateMemberProfile, delete: deleteMemberProfile, process: processMemberProfile, finish: finishMemberProfile }
const columns = [{ prop: 'recordNo', label: '党员编号' }, { prop: 'recordName', label: '党员姓名' }, { prop: 'category', label: '所属支部' }, { prop: 'ownerName', label: '联系方式' }, { prop: 'planTime', label: '入党时间' }, { prop: 'status', label: '档案状态' }, { prop: 'remark', label: '个人说明' }]
const formFields = [{ prop: 'recordNo', label: '党员编号' }, { prop: 'recordName', label: '党员姓名' }, { prop: 'category', label: '所属支部' }, { prop: 'ownerName', label: '联系方式' }, { prop: 'planTime', label: '入党时间' }, { prop: 'status', label: '档案状态', type: 'select' }, { prop: 'remark', label: '个人说明', type: 'textarea' }]
</script>
