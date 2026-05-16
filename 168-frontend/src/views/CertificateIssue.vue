<template><DataPage title="证书发放" description="证书编号、证书名称、证书类型、发证人员、发证时间和发证状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'TRAINING', 'EXAMINER', 'CERTIFIER', 'TRAINEE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCertificateIssue, deleteCertificateIssue, finishCertificateIssue, getCertificateIssuePage, processCertificateIssue, updateCertificateIssue } from '../api'
const api = { page: getCertificateIssuePage, add: addCertificateIssue, update: updateCertificateIssue, delete: deleteCertificateIssue, process: processCertificateIssue, finish: finishCertificateIssue }
const columns = [{ prop: 'recordNo', label: '证书编号' }, { prop: 'recordName', label: '证书名称' }, { prop: 'category', label: '证书类型' }, { prop: 'ownerName', label: '发证人员' }, { prop: 'planTime', label: '发证时间' }, { prop: 'status', label: '发证状态' }, { prop: 'remark', label: '证书说明' }]
const formFields = [{ prop: 'recordNo', label: '证书编号' }, { prop: 'recordName', label: '证书名称' }, { prop: 'category', label: '证书类型' }, { prop: 'ownerName', label: '发证人员' }, { prop: 'planTime', label: '发证时间' }, { prop: 'status', label: '发证状态', type: 'select' }, { prop: 'remark', label: '证书说明', type: 'textarea' }]
</script>
