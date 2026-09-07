$(document).ready(function() {
    if (getToken()) {
        const user = getUser();
        if (user && user.role === 'ADMIN') {
            window.location.href = '../../pages/admin/index.html';
        } else {
            window.location.href = '../../pages/user/index.html';
        }
        return;
    }

    $('#loginForm').on('submit', function(e) {
        e.preventDefault();
        
        const formData = {
            username: $('input[name="username"]', this).val(),
            password: $('input[name="password"]', this).val()
        };

        const loadingIndex = layer.load();
        
        request(API_ENDPOINTS.USER_LOGIN, {
            method: 'POST',
            data: JSON.stringify(formData)
        }).done(function(res) {
            layer.close(loadingIndex);
            
            if (res.code === 200) {
                setToken(res.data.token);
                setUser(res.data.user);
                
                layer.msg('登录成功！', {icon: 1});
                
                setTimeout(() => {
                    if (res.data.user.role === 'ADMIN') {
                        window.location.href = '../../pages/admin/index.html';
                    } else {
                        window.location.href = '../../pages/user/index.html';
                    }
                }, 1000);
            } else {
                layer.msg(res.message || '登录失败', {icon: 2});
            }
        }).fail(function() {
            layer.close(loadingIndex);
            layer.msg('网络错误，请稍后重试', {icon: 2});
        });
    });

    $('#registerForm').on('submit', function(e) {
        e.preventDefault();
        
        const formData = {
            username: $('input[name="username"]', this).val(),
            password: $('input[name="password"]', this).val(),
            nickname: $('input[name="nickname"]', this).val(),
            gender: parseInt($('select[name="gender"]', this).val()),
            age: parseInt($('input[name="age"]', this).val()) || null,
            height: parseFloat($('input[name="height"]', this).val()) || null,
            weight: parseFloat($('input[name="weight"]', this).val()) || null
        };

        const loadingIndex = layer.load();
        
        request(API_ENDPOINTS.USER_REGISTER, {
            method: 'POST',
            data: JSON.stringify(formData)
        }).done(function(res) {
            layer.close(loadingIndex);
            
            if (res.code === 200) {
                layer.msg('注册成功！请登录', {icon: 1});
                setTimeout(() => {
                    $('.nav-tabs button[data-bs-target="#login-tab"]').tab('show');
                    $('#registerForm')[0].reset();
                }, 1000);
            } else {
                layer.msg(res.message || '注册失败', {icon: 2});
            }
        }).fail(function() {
            layer.close(loadingIndex);
            layer.msg('网络错误，请稍后重试', {icon: 2});
        });
    });
});

