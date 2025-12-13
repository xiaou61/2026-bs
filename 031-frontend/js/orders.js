async function loadOrders() {
    const user = getUser();
    if (!user) {
        window.location.href = 'login.html';
        return;
    }
    
    try {
        const response = await fetch(`/api/orders/user/${user.id}`, {
            headers: {
                'Authorization': 'Bearer ' + getToken()
            }
        });
        
        const result = await response.json();
        
        if (result.code === 200) {
            renderOrders(result.data);
        }
    } catch (error) {
        console.error('Failed to load orders:', error);
    }
}

function renderOrders(orders) {
    const ordersList = document.getElementById('orders-list');
    
    if (orders.length === 0) {
        ordersList.innerHTML = '<p class="text-center text-muted">暂无订单</p>';
        return;
    }
    
    ordersList.innerHTML = orders.map(order => `
        <div class="card mb-3">
            <div class="card-body">
                <div class="row align-items-center">
                    <div class="col-md-6">
                        <h5>订单号：${order.orderNo}</h5>
                        <p class="mb-1">比赛ID：${order.matchId}</p>
                        <p class="mb-1">金额：¥${order.paymentAmount}</p>
                        <p class="mb-1">状态：${getStatusText(order.status)}</p>
                        <p class="mb-0 text-muted">下单时间：${formatDateTime(order.createdAt)}</p>
                    </div>
                    <div class="col-md-6 text-end">
                        ${order.status === 'PENDING' ? `
                            <button class="btn btn-primary" onclick="payOrder(${order.id})">去支付</button>
                            <button class="btn btn-outline-danger" onclick="cancelOrder(${order.id})">取消订单</button>
                        ` : ''}
                        ${order.status === 'PAID' ? `
                            <button class="btn btn-success" onclick="viewTickets(${order.id})">查看门票</button>
                        ` : ''}
                    </div>
                </div>
            </div>
        </div>
    `).join('');
}

function getStatusText(status) {
    const statusMap = {
        'PENDING': '待支付',
        'PAID': '已支付',
        'CANCELLED': '已取消'
    };
    return statusMap[status] || status;
}

async function payOrder(orderId) {
    if (!confirm('确认支付订单？')) return;
    
    try {
        const response = await fetch(`/api/orders/${orderId}/pay`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + getToken()
            },
            body: JSON.stringify({
                paymentMethod: 'BALANCE'
            })
        });
        
        const result = await response.json();
        
        if (result.code === 200) {
            alert('支付成功！');
            loadOrders();
        } else {
            alert('支付失败：' + result.message);
        }
    } catch (error) {
        console.error('Failed to pay order:', error);
        alert('支付失败');
    }
}

async function cancelOrder(orderId) {
    if (!confirm('确认取消订单？')) return;
    
    try {
        const response = await fetch(`/api/orders/${orderId}`, {
            method: 'DELETE',
            headers: {
                'Authorization': 'Bearer ' + getToken()
            }
        });
        
        const result = await response.json();
        
        if (result.code === 200) {
            alert('订单已取消');
            loadOrders();
        } else {
            alert('取消失败：' + result.message);
        }
    } catch (error) {
        console.error('Failed to cancel order:', error);
        alert('取消失败');
    }
}

function viewTickets(orderId) {
    alert('查看门票功能（二维码）');
}

document.addEventListener('DOMContentLoaded', loadOrders);
