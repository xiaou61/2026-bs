export const statusOptions = [
  { label: '启用', value: 1 },
  { label: '停用', value: 0 }
]

export const seasonFlagOptions = [
  { label: '当前文化专题', value: 1 },
  { label: '非当前文化专题', value: 0 }
]

export const repertoireTypeOptions = [
  { label: '必修', value: '必修' },
  { label: '选修', value: '选修' },
  { label: '公共课', value: '公共课' }
]

export const resourceTypeOptions = [
  { label: '课件', value: 'ppt' },
  { label: '文档', value: 'doc' },
  { label: 'PDF', value: 'pdf' },
  { label: '视频', value: 'video' }
]

export const checkinStatusOptions = [
  { label: '正常', value: 'present' },
  { label: '迟到', value: 'late' },
  { label: '请假', value: 'leave' },
  { label: '缺勤', value: 'absent' }
]

export const noticeTypeOptions = [
  { label: '系统公告', value: 'system' },
  { label: '剧目通知', value: 'repertoire' },
  { label: '传承安排', value: 'teaching' }
]

export const weekDayOptions = [
  { label: '周一', value: '周一' },
  { label: '周二', value: '周二' },
  { label: '周三', value: '周三' },
  { label: '周四', value: '周四' },
  { label: '周五', value: '周五' },
  { label: '周六', value: '周六' },
  { label: '周日', value: '周日' }
]

export const roleLabelMap = {
  admin: '管理员',
  artist: '艺术家',
  member: '会员'
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


