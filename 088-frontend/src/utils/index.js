export const roleLabelMap = {
  admin: '管理员',
  reviewer: '审核员',
  applicant: '申请人'
}

export const applicationStatusMap = {
  1: '待初审',
  2: '待家访',
  3: '待匹配',
  4: '待签约',
  5: '已完成',
  6: '已驳回'
}

export const signStatusMap = {
  0: '待签署',
  1: '已签署'
}

export const adoptionStatusMap = {
  0: '可申请',
  1: '匹配中',
  2: '已收养'
}

export const formatDateTime = (value) => {
  if (!value) {
    return '-'
  }
  return String(value).replace('T', ' ').slice(0, 19)
}
