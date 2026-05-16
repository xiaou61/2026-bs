<template><DataPage title="证书档案" description="证书编号、证书名称、证书类型、归档人员、发证时间和证书状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'EDU', 'HR', 'NURSE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCertificateArchive, deleteCertificateArchive, finishCertificateArchive, getCertificateArchivePage, processCertificateArchive, updateCertificateArchive } from '../api'
const api = { page: getCertificateArchivePage, add: addCertificateArchive, update: updateCertificateArchive, delete: deleteCertificateArchive, process: processCertificateArchive, finish: finishCertificateArchive }
const columns = [{ prop: 'recordNo', label: '证书编号' }, { prop: 'recordName', label: '证书名称' }, { prop: 'category', label: '证书类型' }, { prop: 'ownerName', label: '归档人员' }, { prop: 'planTime', label: '发证时间' }, { prop: 'status', label: '证书状态' }, { prop: 'remark', label: '证书说明' }]
const formFields = [{ prop: 'recordNo', label: '证书编号' }, { prop: 'recordName', label: '证书名称' }, { prop: 'category', label: '证书类型' }, { prop: 'ownerName', label: '归档人员' }, { prop: 'planTime', label: '发证时间' }, { prop: 'status', label: '证书状态', type: 'select' }, { prop: 'remark', label: '证书说明', type: 'textarea' }]
</script>
