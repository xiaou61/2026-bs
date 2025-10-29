<template>
  <div>
    <el-card>
      <el-form :inline="true">
        <el-form-item label="关键词">
          <el-input v-model="searchKeyword" placeholder="请输入" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadReferrals">搜索</el-button>
          <el-button type="success" @click="dialogVisible = true">发布内推</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card style="margin-top: 20px">
      <el-table :data="referrals" v-loading="loading">
        <el-table-column prop="companyName" label="公司名称" width="200" />
        <el-table-column prop="jobTitle" label="岗位名称" width="200" />
        <el-table-column prop="description" label="内推说明" />
        <el-table-column prop="referralCode" label="内推码" width="150" />
        <el-table-column prop="contactWay" label="联系方式" width="200" />
        <el-table-column prop="views" label="浏览次数" width="100" />
      </el-table>
      <el-pagination
        v-model:current-page="pagination.page"
        v-model:page-size="pagination.size"
        :total="pagination.total"
        layout="total, prev, pager, next"
        @current-change="loadReferrals"
        style="margin-top: 20px; justify-content: center"
      />
    </el-card>
  </div>

  <el-dialog v-model="dialogVisible" title="发布内推" width="600px">
    <el-form :model="form" label-width="100px">
      <el-form-item label="公司名称">
        <el-input v-model="form.companyName" />
      </el-form-item>
      <el-form-item label="岗位名称">
        <el-input v-model="form.jobTitle" />
      </el-form-item>
      <el-form-item label="内推说明">
        <el-input v-model="form.description" type="textarea" :rows="3" />
      </el-form-item>
      <el-form-item label="要求">
        <el-input v-model="form.requirement" type="textarea" :rows="3" />
      </el-form-item>
      <el-form-item label="内推码">
        <el-input v-model="form.referralCode" />
      </el-form-item>
      <el-form-item label="联系方式">
        <el-input v-model="form.contactWay" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="handlePublish">发布</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getReferralList, publishReferral } from '@/api/referral'
import { ElMessage } from 'element-plus'

const referrals = ref([])
const loading = ref(false)
const searchKeyword = ref('')
const pagination = ref({
  page: 1,
  size: 10,
  total: 0
})

const dialogVisible = ref(false)
const form = ref({
  companyName: '',
  jobTitle: '',
  description: '',
  requirement: '',
  referralCode: '',
  contactWay: ''
})

const loadReferrals = async () => {
  loading.value = true
  try {
    const res = await getReferralList({
      page: pagination.value.page,
      size: pagination.value.size,
      keyword: searchKeyword.value
    })
    referrals.value = res.data.records
    pagination.value.total = res.data.total
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

const handlePublish = async () => {
  try {
    await publishReferral(form.value)
    ElMessage.success('发布成功')
    dialogVisible.value = false
    loadReferrals()
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadReferrals()
})
</script>

