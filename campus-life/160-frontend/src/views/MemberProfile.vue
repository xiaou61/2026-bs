<template><DataPage title="成员档案" description="成员编号、成员姓名、所属社团、负责人、入社时间和成员状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'UNION', 'CLUB']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addMemberProfile, deleteMemberProfile, finishMemberProfile, getMemberProfilePage, processMemberProfile, updateMemberProfile } from '../api'
const api = { page: getMemberProfilePage, add: addMemberProfile, update: updateMemberProfile, delete: deleteMemberProfile, process: processMemberProfile, finish: finishMemberProfile }
const columns = [{ prop: 'recordNo', label: '成员编号' }, { prop: 'recordName', label: '成员姓名' }, { prop: 'category', label: '所属社团' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '入社时间' }, { prop: 'status', label: '成员状态' }, { prop: 'remark', label: '成员说明' }]
const formFields = [{ prop: 'recordNo', label: '成员编号' }, { prop: 'recordName', label: '成员姓名' }, { prop: 'category', label: '所属社团' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '入社时间' }, { prop: 'status', label: '成员状态', type: 'select' }, { prop: 'remark', label: '成员说明', type: 'textarea' }]
</script>
