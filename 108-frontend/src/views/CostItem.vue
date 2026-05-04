<template>
  <DataPage title="成本明细" description="资源级成本、用量和归属空间明细管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getCostItemPage, addCostItem, updateCostItem, deleteCostItem } from '../api'
const api = { page: getCostItemPage, add: addCostItem, update: updateCostItem, delete: deleteCostItem }
const columns = [{"prop": "resourceId", "label": "资源ID"}, {"prop": "resourceName", "label": "资源名称"}, {"prop": "resourceType", "label": "资源类型"}, {"prop": "namespaceName", "label": "空间名称"}, {"prop": "billMonth", "label": "账期"}, {"prop": "costAmount", "label": "成本金额"}, {"prop": "usageQuantity", "label": "使用量"}, {"prop": "unitName", "label": "单位"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "resourceId", "label": "资源ID"}, {"prop": "resourceName", "label": "资源名称"}, {"prop": "resourceType", "label": "资源类型", "type": "select", "options": [{"label": "ECS", "value": "ECS"}, {"label": "RDS", "value": "RDS"}, {"label": "OSS", "value": "OSS"}, {"label": "SLB", "value": "SLB"}, {"label": "K8S", "value": "K8S"}]}, {"prop": "namespaceName", "label": "空间名称"}, {"prop": "billMonth", "label": "账期"}, {"prop": "costAmount", "label": "成本金额", "type": "number"}, {"prop": "usageQuantity", "label": "使用量", "type": "number"}, {"prop": "unitName", "label": "单位"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "NORMAL", "value": "NORMAL"}, {"label": "ABNORMAL", "value": "ABNORMAL"}, {"label": "ALLOCATED", "value": "ALLOCATED"}]}]
const rowActions = []
const defaults = {"resourceType": "ECS", "status": "NORMAL"}
const handleAction = () => {}
</script>
