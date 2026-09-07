export const statusOptions = [
  { label: '启用', value: 1 },
  { label: '停用', value: 0 }
]

export const termFlagOptions = [
  { label: '当前学期', value: 1 },
  { label: '非当前学期', value: 0 }
]

export const activityTypeOptions = [
  { label: '语言表达', value: '语言表达' },
  { label: '艺术启蒙', value: '艺术启蒙' },
  { label: '体能游戏', value: '体能游戏' },
  { label: '生活习惯', value: '生活习惯' }
]

export const recipeTypeOptions = [
  { label: '早餐', value: '早餐' },
  { label: '午餐', value: '午餐' },
  { label: '加餐', value: '加餐' },
  { label: '饮品', value: '饮品' }
]

export const attendanceStatusOptions = [
  { label: '已到园', value: 'present' },
  { label: '迟到', value: 'late' },
  { label: '请假', value: 'leave' },
  { label: '缺勤', value: 'absent' }
]

export const noticeTypeOptions = [
  { label: '园务公告', value: 'system' },
  { label: '家长通知', value: 'parent' },
  { label: '班级活动', value: 'activity' }
]

export const feedbackTypeOptions = [
  { label: '成长反馈', value: '成长反馈' },
  { label: '请假沟通', value: '请假沟通' },
  { label: '接送提醒', value: '接送提醒' },
  { label: '膳食建议', value: '膳食建议' }
]

export const weekDayOptions = [
  { label: '周一', value: '周一' },
  { label: '周二', value: '周二' },
  { label: '周三', value: '周三' },
  { label: '周四', value: '周四' },
  { label: '周五', value: '周五' }
]

export const roleLabelMap = {
  admin: '园长',
  teacher: '教师',
  parent: '家长'
}

export const getOptionLabel = (options, value) => {
  const target = options.find((item) => item.value === value)
  return target ? target.label : value ?? '-'
}

export const buildNameMap = (list, labelKey = 'name') => {
  return (list || []).reduce((acc, item) => {
    acc[item.id] = item[labelKey]
    return acc
  }, {})
}

export const formatDate = (value) => {
  if (!value) return '-'
  return String(value).slice(0, 10)
}

export const formatDateTime = (value) => {
  if (!value) return '-'
  return String(value).replace('T', ' ').slice(0, 19)
}

export const resolvePage = (page) => ({
  list: page?.list || [],
  total: page?.total || 0
})

export const courseTypeOptions = activityTypeOptions
export const resourceTypeOptions = recipeTypeOptions
