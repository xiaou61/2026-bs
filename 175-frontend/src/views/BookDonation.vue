<template><DataPage title="图书捐赠" description="捐赠编号、捐赠图书、捐赠类型、接收人员、捐赠时间和捐赠状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'LIBRARY', 'CURATOR', 'STUDENT', 'CLUB']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addBookDonation, deleteBookDonation, finishBookDonation, getBookDonationPage, processBookDonation, updateBookDonation } from '../api'
const api = { page: getBookDonationPage, add: addBookDonation, update: updateBookDonation, delete: deleteBookDonation, process: processBookDonation, finish: finishBookDonation }
const columns = [{ prop: 'recordNo', label: '捐赠编号' }, { prop: 'recordName', label: '捐赠图书' }, { prop: 'category', label: '捐赠类型' }, { prop: 'ownerName', label: '接收人员' }, { prop: 'planTime', label: '捐赠时间' }, { prop: 'status', label: '捐赠状态' }, { prop: 'remark', label: '捐赠说明' }]
const formFields = [{ prop: 'recordNo', label: '捐赠编号' }, { prop: 'recordName', label: '捐赠图书' }, { prop: 'category', label: '捐赠类型' }, { prop: 'ownerName', label: '接收人员' }, { prop: 'planTime', label: '捐赠时间' }, { prop: 'status', label: '捐赠状态', type: 'select' }, { prop: 'remark', label: '捐赠说明', type: 'textarea' }]
</script>
