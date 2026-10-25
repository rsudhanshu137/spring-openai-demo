DELETE FROM support_tickets;

INSERT INTO support_tickets (id, category, question, answer) VALUES
(1, 'Orders', 'How do I track my order?', 'You can track your order from the "My Orders" section using the tracking ID sent to your registered email.'),
(2, 'Orders', 'How do I cancel my order?', 'Orders can be cancelled within 1 hour of placing them from the "My Orders" section, as long as they have not shipped.'),
(3, 'Refunds', 'How long does a refund take?', 'Refunds are processed within 5-7 business days after the returned item is received and inspected.'),
(4, 'Refunds', 'What is the return policy?', 'Items can be returned within 30 days of delivery if they are unused and in original packaging.'),
(5, 'Account', 'How do I reset my password?', 'Click on "Forgot Password" on the login page and follow the instructions sent to your registered email.'),
(6, 'Account', 'How do I update my email address?', 'Go to Account Settings, select "Edit Profile", and update your email address, then verify it via the OTP sent.'),
(7, 'Payments', 'Which payment methods are supported?', 'We support credit/debit cards, UPI, net banking, and popular digital wallets.'),
(8, 'Payments', 'Why did my payment fail?', 'Payment failures usually occur due to incorrect card details, insufficient balance, or bank server issues. Please retry or use a different payment method.'),
(9, 'Shipping', 'What are the delivery charges?', 'Delivery is free for orders above 499 rupees; a flat fee of 49 rupees applies to orders below that amount.'),
(10, 'Shipping', 'Do you deliver internationally?', 'Currently we only deliver within India. International shipping is not supported at this time.'),
(11, 'Orders', 'Can I change the delivery address after placing an order?', 'You can change the delivery address within 1 hour of placing the order, provided it has not been shipped yet.'),
(12, 'Orders', 'How do I check my order history?', 'Go to "My Orders" from your account dashboard to see a complete history of past and current orders.'),
(13, 'Refunds', 'How do I request a refund?', 'Go to "My Orders", select the item, and click "Return/Refund" to initiate a refund request.'),
(14, 'Refunds', 'Can I exchange a product instead of a refund?', 'Yes, exchanges are available for eligible items within 30 days of delivery through the "Return/Refund" option.'),
(15, 'Account', 'How do I delete my account?', 'Go to Account Settings, select "Delete Account", and confirm via the OTP sent to your registered email or phone.'),
(16, 'Account', 'How do I enable two-factor authentication?', 'Go to Account Settings, select "Security", and enable two-factor authentication using your phone number.'),
(17, 'Payments', 'Is it safe to save my card details?', 'Yes, all card details are encrypted and stored securely using PCI-DSS compliant practices.'),
(18, 'Payments', 'Can I pay using EMI?', 'Yes, EMI options are available on orders above 3000 rupees for select credit cards.'),
(19, 'Shipping', 'How can I change my delivery slot?', 'You can reschedule your delivery slot from the "Track Order" page up to 1 day before the scheduled delivery.'),
(20, 'Shipping', 'What happens if I miss my delivery?', 'If delivery is missed, our courier partner will attempt redelivery on the next business day.');
