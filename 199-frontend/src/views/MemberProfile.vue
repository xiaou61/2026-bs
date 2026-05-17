<template><DataPage title="会员档案" description="会员编号、会员姓名、康复目标、负责教练、建档时间和会员状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'ASSESSOR', 'COACH', 'THERAPIST', 'MEMBER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addMemberProfile, deleteMemberProfile, finishMemberProfile, getMemberProfilePage, processMemberProfile, updateMemberProfile } from '../api'
const api = { page: getMemberProfilePage, add: addMemberProfile, update: updateMemberProfile, delete: deleteMemberProfile, process: processMemberProfile, finish: finishMemberProfile }
const columns = [{ prop: 'recordNo', label: '会员编号' }, { prop: 'recordName', label: '会员姓名' }, { prop: 'category', label: '康复目标' }, { prop: 'ownerName', label: '负责教练' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '会员状态' }, { prop: 'remark', label: '会员说明' }]
const formFields = [{ prop: 'recordNo', label: '会员编号' }, { prop: 'recordName', label: '会员姓名' }, { prop: 'category', label: '康复目标' }, { prop: 'ownerName', label: '负责教练' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '会员状态', type: 'select' }, { prop: 'remark', label: '会员说明', type: 'textarea' }]
</script>
