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
            renderOrders(result.data || []);
        } else if (result.code === 401) {
            logout();
        } else {
            document.getElementById('orders-list').innerHTML = `<div class="alert alert-danger">${result.message}</div>`;
        }
    } catch (error) {
        console.error('Failed to load orders:', error);
    }
}

function renderOrders(orders) {
    const ordersList = document.getElementById('orders-list');

    if (!orders.length) {
        ordersList.innerHTML = '<p class="text-center text-muted">暂无订单</p>';
        return;
    }

    ordersList.innerHTML = orders.map(order => `
        <div class="card mb-3">
            <div class="card-body">
                <div class="row align-items-center">
                    <div class="col-md-7">
                        <h5>${order.matchTitle || '比赛订单'}</h5>
                        <p class="mb-1">订单号：${order.orderNo}</p>
                        <p class="mb-1">门票数量：${order.ticketCount || 0}</p>
                        <p class="mb-1">金额：¥${Number(order.paymentAmount || 0).toFixed(2)}</p>
                        <p class="mb-1">状态：${getStatusText(order.status)}</p>
                        <p class="mb-0 text-muted">下单时间：${formatDateTime(order.createdAt)}</p>
                    </div>
                    <div class="col-md-5 text-end">
                        ${order.status === 'PENDING' ? `
                            <button class="btn btn-primary mb-2" onclick="payOrder(${order.id})">去支付</button>
                            <button class="btn btn-outline-danger mb-2" onclick="cancelOrder(${order.id})">取消订单</button>
                        ` : ''}
                        ${order.status === 'PAID' ? `
                            <button class="btn btn-success mb-2" onclick="viewTickets(${order.id})">查看门票</button>
                        ` : ''}
                    </div>
                </div>
                <div id="order-tickets-${order.id}" class="mt-3"></div>
            </div>
        </div>
    `).join('');
}

function getStatusText(status) {
    const statusMap = {
        PENDING: '待支付',
        PAID: '已支付',
        CANCELLED: '已取消'
    };
    return statusMap[status] || status;
}

async function payOrder(orderId) {
    if (!confirm('确认支付订单？')) {
        return;
    }

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
    if (!confirm('确认取消订单？')) {
        return;
    }

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

async function viewTickets(orderId) {
    const container = document.getElementById(`order-tickets-${orderId}`);
    if (!container) {
        return;
    }

    if (container.dataset.loaded === 'true') {
        container.innerHTML = container.innerHTML ? '' : container.dataset.content;
        return;
    }

    try {
        const response = await fetch(`/api/orders/${orderId}/tickets`, {
            headers: {
                'Authorization': 'Bearer ' + getToken()
            }
        });
        const result = await response.json();

        if (result.code !== 200) {
            alert('门票加载失败：' + result.message);
            return;
        }

        const content = renderTicketList(result.data || []);
        container.dataset.loaded = 'true';
        container.dataset.content = content;
        container.innerHTML = content;
    } catch (error) {
        console.error('Failed to load tickets:', error);
        alert('门票加载失败');
    }
}

function renderTicketList(tickets) {
    if (!tickets.length) {
        return '<div class="alert alert-warning mb-0">当前订单暂无门票</div>';
    }

    return `
        <div class="border rounded p-3 bg-light">
            <h6 class="mb-3">电子门票</h6>
            ${tickets.map(ticket => `
                <div class="border rounded bg-white p-3 mb-2">
                    <div>票号：${ticket.ticketNo}</div>
                    <div>场次：${ticket.matchTitle || '-'}</div>
                    <div>分区：${ticket.categoryName || '-'}</div>
                    <div>座位：${ticket.seatLabel || '-'}</div>
                    <div>票价：¥${Number(ticket.price || 0).toFixed(2)}</div>
                    <div>验票码：${ticket.qrCode || '待生成'}</div>
                </div>
            `).join('')}
        </div>
    `;
}

document.addEventListener('DOMContentLoaded', loadOrders);
