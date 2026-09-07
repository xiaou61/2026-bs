import request from '@/utils/request'

export const uploadApi = {
  uploadImage: (file) => {
    const formData = new FormData()
    formData.append('file', file)

    return request({
      url: '/upload/image',
      method: 'post',
      data: formData
    })
  }
}
