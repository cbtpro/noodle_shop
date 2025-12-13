DROP TABLE IF EXISTS `t_base_combine_item`;
DROP TABLE IF EXISTS `t_base_goods`;
DROP TABLE IF EXISTS `t_base_combine`;

CREATE TABLE IF NOT EXISTS `t_base_goods` (
    `id` BIGINT NOT NULL COMMENT '主键ID',

    `code` VARCHAR(64) NOT NULL COMMENT '商品编码',
    `name` VARCHAR(255) NOT NULL COMMENT '商品名称',
    `price` DECIMAL(10,2) NOT NULL COMMENT '商品价格',

    `revision` INT DEFAULT 0 COMMENT '乐观锁版本号',

    `created_by` VARCHAR(50) DEFAULT NULL COMMENT '创建人',
    `created_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by` VARCHAR(50) DEFAULT NULL COMMENT '更新人',
    `updated_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除 0-未删除 1-已删除',
    `deleted_by` VARCHAR(50) DEFAULT NULL COMMENT '删除人',
    `deleted_time` DATETIME DEFAULT NULL COMMENT '删除时间',

    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_goods_code` (`code`)
) COMMENT='商品基础表';

CREATE TABLE IF NOT EXISTS `t_base_combine` (
    `id` BIGINT NOT NULL,
    `code` VARCHAR(64) NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `price` DECIMAL(10,2) NOT NULL,
    `revision` INT DEFAULT 0,
    `created_by` VARCHAR(50),
    `created_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_by` VARCHAR(50),
    `updated_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted` TINYINT DEFAULT 0,
    `deleted_by` VARCHAR(50),
    `deleted_time` DATETIME DEFAULT NULL,

    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_combine_code` (`code`)
) COMMENT='套餐基础表';

CREATE TABLE IF NOT EXISTS `t_base_combine_item` (
    `id` BIGINT NOT NULL,
    `combine_id` BIGINT NOT NULL COMMENT '套餐ID',
    `goods_code` VARCHAR(64) NOT NULL COMMENT '商品Code',

    PRIMARY KEY (`id`),
    KEY `idx_combine_id` (`combine_id`),
    CONSTRAINT `fk_combine_item_combine` FOREIGN KEY (`combine_id`)
        REFERENCES `t_base_combine` (`id`)
) COMMENT='套餐包含商品明细';
