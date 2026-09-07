<template><DataPage title="商户档案" description="商户编号、商户名称、证照类型、联系人、登记时间和经营状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'MARKET', 'INSPECTOR', 'VENDOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addVendorProfile, deleteVendorProfile, finishVendorProfile, getVendorProfilePage, processVendorProfile, updateVendorProfile } from '../api'
const api = { page: getVendorProfilePage, add: addVendorProfile, update: updateVendorProfile, delete: deleteVendorProfile, process: processVendorProfile, finish: finishVendorProfile }
const columns = [{ prop: 'recordNo', label: '商户编号' }, { prop: 'recordName', label: '商户名称' }, { prop: 'category', label: '证照类型' }, { prop: 'ownerName', label: '联系人' }, { prop: 'planTime', label: '登记时间' }, { prop: 'status', label: '经营状态' }, { prop: 'remark', label: '商户说明' }]
const formFields = [{ prop: 'recordNo', label: '商户编号' }, { prop: 'recordName', label: '商户名称' }, { prop: 'category', label: '证照类型' }, { prop: 'ownerName', label: '联系人' }, { prop: 'planTime', label: '登记时间' }, { prop: 'status', label: '经营状态', type: 'select' }, { prop: 'remark', label: '商户说明', type: 'textarea' }]
</script>
