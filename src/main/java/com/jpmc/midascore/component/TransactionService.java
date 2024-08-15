package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRecordRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class TransactionService {

    private final UserRepository userRepository;
    private final TransactionRecordRepository transactionRecordRepository;

    public TransactionService(UserRepository userRepository, TransactionRecordRepository transactionRecordRepository) {
        this.userRepository = userRepository;
        this.transactionRecordRepository = transactionRecordRepository;
    }

    @Transactional
    public void processTransaction(Transaction tx) {
        // 1. Look up sender
        Optional<UserRecord> senderOpt = Optional.ofNullable(userRepository.findById(tx.getSenderId()));
        if (senderOpt.isEmpty()) {
            System.out.println("Sender with ID " + tx.getSenderId() + " not found.");
            return;
        }
        UserRecord sender = senderOpt.get();

        // 2. Look up recipient
        Optional<UserRecord> recipientOpt = Optional.ofNullable(userRepository.findById(tx.getRecipientId()));
        if (recipientOpt.isEmpty()) {
            System.out.println("Recipient with ID " + tx.getRecipientId() + " not found.");
            return;
        }
        UserRecord recipient = recipientOpt.get();

        // 3. Check balance
        if (sender.getBalance() < tx.getAmount()) {
            System.out.println("Insufficient funds for sender ID " + sender.getId());
            return;
        }
        System.out.println("Processing transaction from " + tx.getSenderId() + sender.getName() + " to " + tx.getRecipientId() +recipient.getName());

        System.out.println("Balances before: sender=" + sender.getBalance() +  ", recipient=" + recipient.getBalance());
        // 4. Adjust balances
        System.out.println("Processing transaction amount: " + tx.getAmount());
        sender.setBalance(sender.getBalance() - tx.getAmount());
        recipient.setBalance(recipient.getBalance() + tx.getAmount());
        System.out.println("Balances after: sender=" + sender.getBalance() +  ", recipient=" + recipient.getBalance());

        // 5. Save updated users
        userRepository.save(sender);
        userRepository.save(recipient);

        // 6. Record transaction
        TransactionRecord record = new TransactionRecord();
        record.setSender(sender);
        record.setRecipient(recipient);
        record.setAmount(tx.getAmount());


        transactionRecordRepository.save(record);

        System.out.println("Transaction recorded: " + record);
    }
}
