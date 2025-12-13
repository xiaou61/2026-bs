async function loadProfile() {
    const user = getUser();
    if (!user) {
        window.location.href = 'login.html';
        return;
    }
    
    document.getElementById('username').value = user.username || '';
    document.getElementById('phone').value = user.phone || '';
    document.getElementById('email').value = user.email || '';
    document.getElementById('realName').value = user.realName || '';
    document.getElementById('idCard').value = user.idCard || '';
    
    loadBalance();
}

async function loadBalance() {
    const user = getUser();
    
    try {
        const response = await fetch(`/api/users/${user.id}/balance`, {
            headers: {
                'Authorization': 'Bearer ' + getToken()
            }
        });
        
        const result = await response.json();
        
        if (result.code === 200) {
            document.getElementById('balance-amount').textContent = '¥' + result.data.toFixed(2);
        }
    } catch (error) {
        console.error('Failed to load balance:', error);
    }
}

async function recharge() {
    const amount = document.getElementById('recharge-amount').value;
    
    if (!amount || amount <= 0) {
        alert('请输入有效的充值金额');
        return;
    }
    
    const user = getUser();
    
    try {
        const response = await fetch(`/api/users/${user.id}/recharge`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + getToken()
            },
            body: JSON.stringify({ amount: parseFloat(amount) })
        });
        
        const result = await response.json();
        
        if (result.code === 200) {
            alert('充值成功！');
            document.getElementById('recharge-amount').value = '';
            loadBalance();
        } else {
            alert('充值失败：' + result.message);
        }
    } catch (error) {
        console.error('Failed to recharge:', error);
        alert('充值失败');
    }
}

document.getElementById('profile-form')?.addEventListener('submit', async function(e) {
    e.preventDefault();
    
    const user = getUser();
    const formData = {
        phone: document.getElementById('phone').value,
        email: document.getElementById('email').value,
        realName: document.getElementById('realName').value,
        idCard: document.getElementById('idCard').value
    };
    
    try {
        const response = await fetch(`/api/users/${user.id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + getToken()
            },
            body: JSON.stringify(formData)
        });
        
        const result = await response.json();
        
        if (result.code === 200) {
            alert('保存成功！');
            setUser(result.data);
        } else {
            alert('保存失败：' + result.message);
        }
    } catch (error) {
        console.error('Failed to update profile:', error);
        alert('保存失败');
    }
});

document.addEventListener('DOMContentLoaded', loadProfile);
