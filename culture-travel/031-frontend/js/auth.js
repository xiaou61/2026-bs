async function submitLogin(form) {
    const formData = new FormData(form);
    const payload = {
        username: formData.get('username'),
        password: formData.get('password')
    };

    const resultBox = document.getElementById('login-result');
    resultBox.innerHTML = '';

    try {
        const response = await fetch('/api/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(payload)
        });
        const result = await response.json();

        if (result.code === 200) {
            setToken(result.data.token);
            setUser(result.data.user);
            window.location.href = 'index.html';
            return;
        }

        resultBox.innerHTML = `<div class="alert alert-danger">${result.message}</div>`;
    } catch (error) {
        console.error('Failed to login:', error);
        resultBox.innerHTML = '<div class="alert alert-danger">登录失败，请稍后重试</div>';
    }
}

async function submitRegister(form) {
    const formData = new FormData(form);
    const password = formData.get('password');
    const confirmPassword = formData.get('confirmPassword');
    const resultBox = document.getElementById('register-result');
    resultBox.innerHTML = '';

    if (password !== confirmPassword) {
        resultBox.innerHTML = '<div class="alert alert-danger">两次输入的密码不一致</div>';
        return;
    }

    const payload = {
        username: formData.get('username'),
        password,
        phone: formData.get('phone'),
        email: formData.get('email')
    };

    try {
        const response = await fetch('/api/auth/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(payload)
        });
        const result = await response.json();

        if (result.code === 200) {
            resultBox.innerHTML = '<div class="alert alert-success">注册成功，正在跳转登录页...</div>';
            setTimeout(() => {
                window.location.href = 'login.html';
            }, 1000);
            return;
        }

        resultBox.innerHTML = `<div class="alert alert-danger">${result.message}</div>`;
    } catch (error) {
        console.error('Failed to register:', error);
        resultBox.innerHTML = '<div class="alert alert-danger">注册失败，请稍后重试</div>';
    }
}

document.getElementById('login-form')?.addEventListener('submit', async function (event) {
    event.preventDefault();
    await submitLogin(event.currentTarget);
});

document.getElementById('register-form')?.addEventListener('submit', async function (event) {
    event.preventDefault();
    await submitRegister(event.currentTarget);
});
