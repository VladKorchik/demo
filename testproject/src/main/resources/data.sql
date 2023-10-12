INSERT INTO Users (id,name, user_Creating_Date)
VALUES (1,'User1', CURRENT_TIMESTAMP),
       (2,'User2', CURRENT_TIMESTAMP),
       (3,'User3', CURRENT_TIMESTAMP);

INSERT INTO Bank_Account (amount, Id, pin,users)
VALUES (1000, 1, 1234, 1),
       (2000, 2, 5678, 1),
       (3000, 3, 4321, 2),
       (4000, 4, 8765, 3);

INSERT INTO Transaction (trans_Type, amount, trans_Date, bank_Account_Sender_Id, bank_Account_Receiver_Id, trans_Result, description)
VALUES ('DEPOSIT', 500, CURRENT_TIMESTAMP, 1, 2, 'SUCCEED', 'Transfer from User1 to User2'),
       ('WITHDRAW', 200, CURRENT_TIMESTAMP, 3, NULL, 'SUCCEED', 'Withdrawal from User3');

