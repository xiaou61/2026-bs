<template>
  <el-card>
    <template #header><el-button type="primary" @click="showDialog()">添加文物</el-button></template>
    <el-table :data="list" v-loading="loading">
      <el-table-column prop="relicNo" label="编号" width="100" />
      <el-table-column prop="name" label="名称" />
      <el-table-column prop="categoryName" label="分类" />
      <el-table-column prop="dynastyName" label="朝代" />
      <el-table-column prop="level" label="等级"><template #default="{row}"><el-tag>{{ levelMap[row.level] }}</el-tag></template></el-table-column>
      <el-table-column prop="status" label="状态"><template #default="{row}"><el-tag :type="statusType[row.status]">{{ statusMap[row.status] }}</el-tag></template></el-table-column>
      <el-table-column label="操作" width="150">
        <template #default="{row}">
          <el-button type="primary" link @click="showDialog(row)">编辑</el-button>
          <el-button type="danger" link @click="handleStatus(row, 0)">下架</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination style="margin-top:16px" :total="total" v-model:current-page="current" @change="loadData" />
    <el-dialog v-model="dialogVisible" :title="form.id?'编辑文物':'添加文物'" width="600">
      <el-form :model="form" label-width="80px">
        <el-form-item label="名称"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="编号"><el-input v-model="form.relicNo" /></el-form-item>
        <el-form-item label="分类"><el-select v-model="form.categoryId"><el-option v-for="c in categories" :key="c.id" :label="c.name" :value="c.id" /></el-select></el-form-item>
        <el-form-item label="朝代"><el-select v-model="form.dynastyId"><el-option v-for="d in dynasties" :key="d.id" :label="d.name" :value="d.id" /></el-select></el-form-item>
        <el-form-item label="等级"><el-select v-model="form.level"><el-option label="一级" :value="1" /><el-option label="二级" :value="2" /><el-option label="三级" :value="3" /><el-option label="一般" :value="4" /></el-select></el-form-item>
        <el-form-item label="材质"><el-input v-model="form.material" /></el-form-item>
        <el-form-item label="尺寸"><el-input v-model="form.size" /></el-form-item>
        <el-form-item label="描述"><el-input v-model="form.description" type="textarea" :rows="3" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible=false">取消</el-button><el-button type="primary" @click="handleSave">保存</el-button></template>
    </el-dialog>
  </el-card>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { getAdminRelics, saveRelic, updateRelic, updateRelicStatus, getAdminCategories, getAdminDynasties } from '@/api'
import { ElMessage } from 'element-plus'
const levelMap:Record<number,string> = {1:'一级',2:'二级',3:'三级',4:'一般'}
const statusMap:Record<number,string> = {0:'下架',1:'展出中',2:'修复中',3:'外借中'}
const statusType:Record<number,string> = {0:'info',1:'success',2:'warning',3:'danger'}
const loading = ref(false), list = ref<any[]>([]), total = ref(0), current = ref(1), dialogVisible = ref(false)
const categories = ref<any[]>([]), dynasties = ref<any[]>([])
const form = ref<any>({})
const loadData = async () => { loading.value=true; const res:any=await getAdminRelics({current:current.value,size:10}); list.value=res.data.records; total.value=res.data.total; loading.value=false }
const showDialog = (row?:any) => { form.value = row ? {...row} : {level:3,status:1}; dialogVisible.value=true }
const handleSave = async () => { form.value.id ? await updateRelic(form.value) : await saveRelic(form.value); ElMessage.success('保存成功'); dialogVisible.value=false; loadData() }
const handleStatus = async (row:any, status:number) => { await updateRelicStatus(row.id, status); ElMessage.success('操作成功'); loadData() }
onMounted(async () => { const [c,d]:any = await Promise.all([getAdminCategories(), getAdminDynasties()]); categories.value=c.data; dynasties.value=d.data; loadData() })
</script>
