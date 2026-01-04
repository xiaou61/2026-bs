import request from './request'

export const register = (data) =&gt; {
  return request.post('/user/register', data)
}

export const login = (data) =&gt; {
  return request.post('/user/login', data)
}

export const getUserInfo = () =&gt; {
  return request.get('/user/info')
}
