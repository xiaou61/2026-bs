<template>
  <DataPage title="网络分区" description="网络区域、CIDR范围和安全等级管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getNetworkSegmentPage, addNetworkSegment, updateNetworkSegment, deleteNetworkSegment } from '../api'
const api = { page: getNetworkSegmentPage, add: addNetworkSegment, update: updateNetworkSegment, delete: deleteNetworkSegment }
const columns = [{"prop": "segmentName", "label": "分区名称"}, {"prop": "segmentCode", "label": "分区编码"}, {"prop": "cidrBlock", "label": "CIDR"}, {"prop": "securityZone", "label": "安全域"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "segmentName", "label": "分区名称"}, {"prop": "segmentCode", "label": "分区编码"}, {"prop": "cidrBlock", "label": "CIDR"}, {"prop": "securityZone", "label": "安全域", "type": "select", "options": [{"label": "OFFICE", "value": "OFFICE"}, {"label": "FINANCE", "value": "FINANCE"}, {"label": "PROD", "value": "PROD"}, {"label": "DMZ", "value": "DMZ"}]}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}]}]
const rowActions = []
const defaults = {"securityZone": "OFFICE", "status": "ACTIVE"}
const handleAction = () => {}
</script>
