<template>
  <el-card>
    <template #header>
      <h3>我的简历</h3>
    </template>
    <el-form :model="form" label-width="120px">
      <el-form-item label="姓名">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="性别">
        <el-radio-group v-model="form.gender">
          <el-radio value="男">男</el-radio>
          <el-radio value="女">女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="出生日期">
        <el-date-picker v-model="form.birthDate" type="date" placeholder="选择日期" value-format="YYYY-MM-DD" />
      </el-form-item>
      <el-form-item label="联系电话">
        <el-input v-model="form.phone" />
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="form.email" />
      </el-form-item>
      <el-form-item label="院校">
        <el-input v-model="form.university" />
      </el-form-item>
      <el-form-item label="专业">
        <el-input v-model="form.major" />
      </el-form-item>
      <el-form-item label="学历">
        <el-select v-model="form.education">
          <el-option label="专科" value="专科" />
          <el-option label="本科" value="本科" />
          <el-option label="硕士" value="硕士" />
          <el-option label="博士" value="博士" />
        </el-select>
      </el-form-item>
      <el-form-item label="毕业时间">
        <el-date-picker v-model="form.graduationDate" type="date" placeholder="选择日期" value-format="YYYY-MM-DD" />
      </el-form-item>
      <el-form-item label="技能">
        <el-input v-model="form.skills" placeholder="多个技能用逗号分隔" />
      </el-form-item>
      <el-form-item label="实习经历">
        <el-input v-model="form.internshipExperience" type="textarea" :rows="4" />
      </el-form-item>
      <el-form-item label="项目经历">
        <el-input v-model="form.projectExperience" type="textarea" :rows="4" />
      </el-form-item>
      <el-form-item label="自我介绍">
        <el-input v-model="form.selfIntroduction" type="textarea" :rows="4" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSave">保存</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getMyResume, createResume, updateResume } from '@/api/resume'
import { ElMessage } from 'element-plus'

const form = ref({
  name: '',
  gender: '男',
  birthDate: '',
  phone: '',
  email: '',
  university: '',
  major: '',
  education: '本科',
  graduationDate: '',
  skills: '',
  internshipExperience: '',
  projectExperience: '',
  selfIntroduction: ''
})

const resumeId = ref(null)

const loadResume = async () => {
  try {
    const res = await getMyResume()
    form.value = res.data
    resumeId.value = res.data.id
  } catch (error) {
    console.log('暂无简历')
  }
}

const handleSave = async () => {
  try {
    if (resumeId.value) {
      await updateResume({ ...form.value, id: resumeId.value })
    } else {
      const res = await createResume(form.value)
      resumeId.value = res.data.id
    }
    ElMessage.success('保存成功')
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadResume()
})
</script>

