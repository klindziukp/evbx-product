USE evbx_product;

CREATE TABLE IF NOT EXISTS `product` (
	`id` BIGINT AUTO_INCREMENT,
    `product_name` VARCHAR(255),
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`deleted_at` TIMESTAMP NULL DEFAULT NULL,
	`created_by` VARCHAR(255),
	`updated_by` VARCHAR(255),
	`deleted_by` VARCHAR(255),
	PRIMARY KEY (`id`),
	CONSTRAINT uc_name UNIQUE (product_name)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `product_model` (
	`id` BIGINT AUTO_INCREMENT,
	`product_id` BIGINT,
    `model_name` VARCHAR(255),
  	`book_id` BIGINT,
	`industry_report_id` BIGINT,
	`specification_report_id` BIGINT,
	`created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`deleted_at` TIMESTAMP NULL DEFAULT NULL,
	`created_by` VARCHAR(255),
	`updated_by` VARCHAR(255),
	`deleted_by` VARCHAR(255),
	PRIMARY KEY (`id`),
	CONSTRAINT uc_name UNIQUE (model_name)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `description` (
	`id` BIGINT AUTO_INCREMENT,
	`model_id` BIGINT,
    `description_line` LONGTEXT,
	`created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`deleted_at` TIMESTAMP NULL DEFAULT NULL,
	`created_by` VARCHAR(255),
	`updated_by` VARCHAR(255),
	`deleted_by` VARCHAR(255),
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `product_model` ADD CONSTRAINT `product_model_fk0` FOREIGN KEY (`product_id`) REFERENCES `product`(`id`) ON DELETE CASCADE;
ALTER TABLE `description` ADD CONSTRAINT `description_fk0` FOREIGN KEY (`model_id`) REFERENCES `product_model`(`id`) ON DELETE CASCADE;