INSERT INTO `t_base_goods` (`id`, `code`, `name`, `price`, `revision`, `created_by`, `created_time`, `updated_by`, `updated_time`,
                            `deleted`, `deleted_by`, `deleted_time`)
VALUES
(1, 'beef_noodle_large',   '大碗牛肉面', 18, 0, 'system', NOW(), 'system', NOW(), 0, NULL, NULL),
(2, 'beef_noodle_medium',  '中碗牛肉面', 16, 0, 'system', NOW(), 'system', NOW(), 0, NULL, NULL),
(3, 'beef_noodle_small',   '小碗牛肉面', 14, 0, 'system', NOW(), 'system', NOW(), 0, NULL, NULL),

(4, 'intestine_noodle_large',  '大碗肥肠面', 20, 0, 'system', NOW(), 'system', NOW(), 0, NULL, NULL),
(5, 'intestine_noodle_medium', '中碗肥肠面', 18, 0, 'system', NOW(), 'system', NOW(), 0, NULL, NULL),
(6, 'intestine_noodle_small',  '小碗肥肠面', 16, 0, 'system', NOW(), 'system', NOW(), 0, NULL, NULL),

(7, 'beef_cake', '牛肉饼', 10, 0, 'system', NOW(), 'system', NOW(), 0, NULL, NULL),
(8, 'milk_tea',  '奶茶', 12, 0, 'system', NOW(), 'system', NOW(), 0, NULL, NULL);

INSERT INTO `t_base_combine`
(`id`, `revision`,`created_by`,`created_time`,`updated_by`,`updated_time`,
 `deleted`,`deleted_by`,`deleted_time`,`code`,`name`,`price`)
VALUES
(1,0,'system',NOW(),'system',NOW(),0,NULL,NULL,'combine_1','套餐1',38),
(2,0,'system',NOW(),'system',NOW(),0,NULL,NULL,'combine_2','套餐2',40);


INSERT INTO `t_base_combine_item` (`id`,`combine_id`,`goods_code`) VALUES
(101, 1, 'beef_noodle_large'),
(102, 1, 'beef_cake'),
(103, 1, 'milk_tea');

INSERT INTO `t_base_combine_item` (`id`,`combine_id`,`goods_code`) VALUES
(201, 2, 'intestine_noodle_large'),
(202, 2, 'beef_cake'),
(203, 2, 'milk_tea');
