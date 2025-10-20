package com.xiaou.express.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.express.common.BusinessException;
import com.xiaou.express.common.Constants;
import com.xiaou.express.entity.Transaction;
import com.xiaou.express.entity.Wallet;
import com.xiaou.express.mapper.TransactionMapper;
import com.xiaou.express.mapper.WalletMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class WalletService {

    @Autowired
    private WalletMapper walletMapper;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private NotificationService notificationService;

    public Wallet getWallet(Long userId) {
        LambdaQueryWrapper<Wallet> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Wallet::getUserId, userId);
        return walletMapper.selectOne(wrapper);
    }

    @Transactional
    public void recharge(Long userId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("充值金额必须大于0");
        }

        Wallet wallet = getWallet(userId);
        if (wallet == null) {
            throw new BusinessException("钱包不存在");
        }

        BigDecimal balanceBefore = wallet.getBalance();
        wallet.setBalance(wallet.getBalance().add(amount));
        walletMapper.updateById(wallet);

        Transaction transaction = new Transaction();
        transaction.setTransactionNo(generateTransactionNo());
        transaction.setUserId(userId);
        transaction.setType(Constants.TransactionType.RECHARGE);
        transaction.setAmount(amount);
        transaction.setBalanceBefore(balanceBefore);
        transaction.setBalanceAfter(wallet.getBalance());
        transaction.setDescription("余额充值");
        transactionMapper.insert(transaction);

        notificationService.sendNotification(userId, Constants.NotificationType.TRANSACTION,
                "充值成功", "充值" + amount + "元成功", "transaction", transaction.getId());
    }

    @Transactional
    public void withdraw(Long userId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("提现金额必须大于0");
        }

        Wallet wallet = getWallet(userId);
        if (wallet == null) {
            throw new BusinessException("钱包不存在");
        }

        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new BusinessException("余额不足");
        }

        BigDecimal balanceBefore = wallet.getBalance();
        wallet.setBalance(wallet.getBalance().subtract(amount));
        wallet.setTotalExpense(wallet.getTotalExpense().add(amount));
        walletMapper.updateById(wallet);

        Transaction transaction = new Transaction();
        transaction.setTransactionNo(generateTransactionNo());
        transaction.setUserId(userId);
        transaction.setType(Constants.TransactionType.WITHDRAW);
        transaction.setAmount(amount);
        transaction.setBalanceBefore(balanceBefore);
        transaction.setBalanceAfter(wallet.getBalance());
        transaction.setDescription("余额提现");
        transactionMapper.insert(transaction);

        notificationService.sendNotification(userId, Constants.NotificationType.TRANSACTION,
                "提现成功", "提现" + amount + "元成功", "transaction", transaction.getId());
    }

    @Transactional
    public void freezeAmount(Long userId, BigDecimal amount, String description) {
        Wallet wallet = getWallet(userId);
        if (wallet == null) {
            throw new BusinessException("钱包不存在");
        }

        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new BusinessException("余额不足");
        }

        wallet.setBalance(wallet.getBalance().subtract(amount));
        wallet.setFrozenAmount(wallet.getFrozenAmount().add(amount));
        walletMapper.updateById(wallet);
    }

    @Transactional
    public void unfreezeAndTransfer(Long fromUserId, Long toUserId, BigDecimal amount, String description, Long orderId) {
        Wallet fromWallet = getWallet(fromUserId);
        Wallet toWallet = getWallet(toUserId);

        if (fromWallet == null || toWallet == null) {
            throw new BusinessException("钱包不存在");
        }

        fromWallet.setFrozenAmount(fromWallet.getFrozenAmount().subtract(amount));
        fromWallet.setTotalExpense(fromWallet.getTotalExpense().add(amount));
        walletMapper.updateById(fromWallet);

        BigDecimal balanceBefore = toWallet.getBalance();
        toWallet.setBalance(toWallet.getBalance().add(amount));
        toWallet.setTotalIncome(toWallet.getTotalIncome().add(amount));
        walletMapper.updateById(toWallet);

        Transaction transaction = new Transaction();
        transaction.setTransactionNo(generateTransactionNo());
        transaction.setUserId(toUserId);
        transaction.setOrderId(orderId);
        transaction.setType(Constants.TransactionType.INCOME);
        transaction.setAmount(amount);
        transaction.setBalanceBefore(balanceBefore);
        transaction.setBalanceAfter(toWallet.getBalance());
        transaction.setDescription(description);
        transactionMapper.insert(transaction);
    }

    @Transactional
    public void refund(Long userId, BigDecimal amount, String description, Long orderId) {
        Wallet wallet = getWallet(userId);
        if (wallet == null) {
            throw new BusinessException("钱包不存在");
        }

        BigDecimal balanceBefore = wallet.getBalance();
        wallet.setBalance(wallet.getBalance().add(amount));
        wallet.setFrozenAmount(wallet.getFrozenAmount().subtract(amount));
        walletMapper.updateById(wallet);

        Transaction transaction = new Transaction();
        transaction.setTransactionNo(generateTransactionNo());
        transaction.setUserId(userId);
        transaction.setOrderId(orderId);
        transaction.setType(Constants.TransactionType.REFUND);
        transaction.setAmount(amount);
        transaction.setBalanceBefore(balanceBefore);
        transaction.setBalanceAfter(wallet.getBalance());
        transaction.setDescription(description);
        transactionMapper.insert(transaction);

        notificationService.sendNotification(userId, Constants.NotificationType.TRANSACTION,
                "退款成功", description + " " + amount + "元", "transaction", transaction.getId());
    }

    public Page<Transaction> getTransactions(Long userId, int pageNum, int pageSize) {
        Page<Transaction> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Transaction> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Transaction::getUserId, userId)
                .orderByDesc(Transaction::getCreateTime);
        return transactionMapper.selectPage(page, wrapper);
    }

    private String generateTransactionNo() {
        return "TXN" + System.currentTimeMillis() + (int)(Math.random() * 1000);
    }
}

