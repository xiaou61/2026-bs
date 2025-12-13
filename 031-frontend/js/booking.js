let currentMatch = null;
let selectedSeats = [];
let currentPricing = null;

const urlParams = new URLSearchParams(window.location.search);
const matchId = urlParams.get('id');

async function loadMatchInfo() {
    try {
        const response = await fetch(`/api/matches/${matchId}`);
        const result = await response.json();
        
        if (result.code === 200) {
            currentMatch = result.data;
            renderMatchInfo(currentMatch);
            loadPricing();
        }
    } catch (error) {
        console.error('Failed to load match info:', error);
    }
}

function renderMatchInfo(match) {
    document.getElementById('match-info').innerHTML = `
        <h4>${match.title}</h4>
        <p><strong>比赛时间：</strong>${formatDateTime(match.matchDate)}</p>
        <p><strong>赛事类型：</strong>${match.league}</p>
        <p><strong>赛季：</strong>${match.season}</p>
        <p><strong>描述：</strong>${match.description || '暂无描述'}</p>
    `;
}

async function loadPricing() {
    const pricingList = document.getElementById('pricing-list');
    pricingList.innerHTML = `
        <div class="col-md-4">
            <div class="card pricing-card" onclick="selectPricing(1, 688)">
                <div class="card-body text-center">
                    <h5>VIP座位</h5>
                    <h3 class="text-primary">¥688</h3>
                    <p class="text-muted">剩余500座</p>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card pricing-card" onclick="selectPricing(2, 280)">
                <div class="card-body text-center">
                    <h5>A区看台</h5>
                    <h3 class="text-primary">¥280</h3>
                    <p class="text-muted">剩余10000座</p>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card pricing-card" onclick="selectPricing(3, 180)">
                <div class="card-body text-center">
                    <h5>B区看台</h5>
                    <h3 class="text-primary">¥180</h3>
                    <p class="text-muted">剩余8000座</p>
                </div>
            </div>
        </div>
    `;
}

function selectPricing(categoryId, price) {
    currentPricing = { categoryId, price };
    generateSeatGrid();
}

function generateSeatGrid() {
    const seatGrid = document.getElementById('seat-grid');
    let html = '';
    
    for (let i = 1; i <= 10; i++) {
        for (let j = 1; j <= 10; j++) {
            const seatId = `${i}-${j}`;
            const isOccupied = Math.random() > 0.8;
            const status = isOccupied ? 'occupied' : 'available';
            html += `
                <div class="seat ${status}" 
                     data-seat-id="${seatId}" 
                     onclick="toggleSeat('${seatId}')">
                    ${i}-${j}
                </div>
            `;
        }
    }
    
    seatGrid.innerHTML = html;
}

function toggleSeat(seatId) {
    const seatElement = document.querySelector(`[data-seat-id="${seatId}"]`);
    
    if (seatElement.classList.contains('occupied')) {
        return;
    }
    
    if (seatElement.classList.contains('selected')) {
        seatElement.classList.remove('selected');
        selectedSeats = selectedSeats.filter(s => s !== seatId);
    } else {
        seatElement.classList.add('selected');
        selectedSeats.push(seatId);
    }
    
    updateOrderSummary();
}

function updateOrderSummary() {
    const selectedSeatsDiv = document.getElementById('selected-seats');
    const totalPriceSpan = document.getElementById('total-price');
    
    if (selectedSeats.length === 0) {
        selectedSeatsDiv.innerHTML = '<p class="text-muted">暂未选择座位</p>';
        totalPriceSpan.textContent = '¥0';
        return;
    }
    
    const totalPrice = selectedSeats.length * (currentPricing?.price || 0);
    
    selectedSeatsDiv.innerHTML = `
        <h6>已选座位 (${selectedSeats.length})</h6>
        <ul class="list-unstyled">
            ${selectedSeats.map(seat => `<li>座位：${seat} - ¥${currentPricing?.price}</li>`).join('')}
        </ul>
    `;
    
    totalPriceSpan.textContent = '¥' + totalPrice;
}

async function submitOrder() {
    const user = getUser();
    if (!user) {
        alert('请先登录');
        window.location.href = 'login.html';
        return;
    }
    
    if (selectedSeats.length === 0) {
        alert('请先选择座位');
        return;
    }
    
    try {
        const response = await fetch('/api/orders', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + getToken()
            },
            body: JSON.stringify({
                userId: user.id,
                matchId: matchId,
                seatIds: selectedSeats.map((_, index) => index + 1)
            })
        });
        
        const result = await response.json();
        
        if (result.code === 200) {
            alert('订单创建成功！');
            window.location.href = 'my-orders.html';
        } else {
            alert('订单创建失败：' + result.message);
        }
    } catch (error) {
        console.error('Failed to create order:', error);
        alert('订单创建失败');
    }
}

document.addEventListener('DOMContentLoaded', function() {
    if (matchId) {
        loadMatchInfo();
    }
});
