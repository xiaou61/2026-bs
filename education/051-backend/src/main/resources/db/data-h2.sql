INSERT INTO admin (id, username, password, nickname, status) VALUES
(1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '管理员', 1);

INSERT INTO user (id, openid, nickname, avatar, points, level) VALUES
(1, 'local-demo', '演示用户', '', 120, 2),
(2, 'local-helper', '安全助手', '', 80, 1);

INSERT INTO knowledge_category (id, name, icon, sort, status) VALUES
(1, '密码安全', '', 1, 1),
(2, '防诈骗', '', 2, 1),
(3, '隐私保护', '', 3, 1),
(4, '病毒防护', '', 4, 1),
(5, '网络钓鱼', '', 5, 1),
(6, '社交安全', '', 6, 1);

INSERT INTO knowledge_article (id, category_id, title, cover, content, view_count, like_count, status) VALUES
(1, 1, '如何设置强密码', '', '<p>强密码是保护账户安全的第一道防线，建议包含大小写字母、数字和特殊符号，并避免复用。</p>', 18, 3, 1),
(2, 1, '密码管理器的使用', '', '<p>密码管理器可以帮助用户安全存储和管理不同平台的账号密码，并配合双因素认证提升安全性。</p>', 12, 2, 1),
(3, 2, '识别电信诈骗', '', '<p>遇到自称公检法、客服退款、刷单返利等场景，应通过官方渠道核实，不轻信、不转账。</p>', 25, 5, 1),
(4, 2, '网购防骗指南', '', '<p>网购应选择正规平台，避免点击陌生链接，使用平台官方支付渠道并保留交易记录。</p>', 11, 1, 1),
(5, 3, '保护个人隐私', '', '<p>谨慎授权 APP 权限，不在公共 WiFi 下输入敏感信息，减少在陌生页面填写个人资料。</p>', 21, 4, 1),
(6, 4, '防范勒索病毒', '', '<p>及时更新系统和软件，安装正版安全软件，不打开来历不明的邮件附件，并定期备份重要文件。</p>', 15, 2, 1);

INSERT INTO question (id, category_id, content, options, answer, explanation, difficulty) VALUES
(1, 1, '以下哪个是强密码?', '["A. 123456", "B. password", "C. Abc@123#Xyz!", "D. qwerty"]', 'C', '强密码应包含大小写字母、数字和特殊符号，并具备足够长度。', 1),
(2, 1, '密码多久更换一次比较合适?', '["A. 永不更换", "B. 每天更换", "C. 3-6个月", "D. 10年一次"]', 'C', '建议定期更换重要账号密码，并避免多个平台共用。', 1),
(3, 2, '收到自称银行客服的电话要求转账，正确做法是?', '["A. 立即转账", "B. 挂断后拨打银行官方客服核实", "C. 告诉对方密码", "D. 按对方指示操作"]', 'B', '任何要求转账或索要验证码的电话都要谨慎，应通过官方渠道核实。', 1),
(4, 2, '以下哪种不是常见的诈骗手段?', '["A. 刷单返利", "B. 冒充公检法", "C. 正规银行柜台办理业务", "D. 杀猪盘"]', 'C', '正规银行柜台办理业务属于正常金融服务，其余都是常见诈骗类型。', 1),
(5, 3, '在公共 WiFi 环境下，以下哪种行为相对安全?', '["A. 登录网银", "B. 浏览新闻", "C. 输入支付密码", "D. 登录公司内网"]', 'B', '公共 WiFi 环境下应避免进行涉及账号、支付和公司数据的敏感操作。', 2),
(6, 4, '发现电脑中了病毒，首先应该?', '["A. 继续使用", "B. 断开网络连接", "C. 格式化硬盘", "D. 忽略不管"]', 'B', '发现病毒后应先断网防止扩散，再进行查杀和恢复处理。', 2);

INSERT INTO answer_record (user_id, question_id, user_answer, is_correct) VALUES
(1, 1, 'C', 1),
(1, 3, 'A', 0),
(2, 2, 'C', 1);

INSERT INTO learn_record (user_id, article_id, progress) VALUES
(1, 1, 100),
(1, 3, 60);

INSERT INTO favorite (user_id, article_id) VALUES
(1, 1);

INSERT INTO news (id, title, cover, content, source, view_count, status) VALUES
(1, '2026年网络安全威胁趋势提示', '', '<p>勒索软件、钓鱼邮件和 AI 伪造内容仍是普通用户需要重点关注的风险。</p>', '网络安全中心', 35, 1),
(2, '警惕新型网络诈骗手段', '', '<p>近期出现冒充快递客服、虚假投资平台、AI 换脸诈骗等案例，请提高警惕。</p>', '公安部门', 28, 1),
(3, '国家网络安全宣传周活动启动', '', '<p>宣传周围绕账号安全、个人信息保护、反诈识别等主题开展科普活动。</p>', '新华社', 19, 1);

INSERT INTO qa_post (id, user_id, title, content, view_count, reply_count, status) VALUES
(1, 1, '收到陌生链接应该怎么判断是否安全?', '短信里带了一个领奖链接，我不确定能不能点开。', 9, 1, 1),
(2, 2, '公共 WiFi 下可以登录邮箱吗?', '出差时只有商场 WiFi，想确认有哪些注意事项。', 6, 0, 1);

INSERT INTO qa_reply (post_id, user_id, content, like_count) VALUES
(1, 2, '不要直接点击陌生链接，建议先核对发送方身份，并通过官方 App 或官网进入相关服务。', 2);
