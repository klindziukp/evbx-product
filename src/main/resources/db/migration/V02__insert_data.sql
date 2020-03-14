DELETE FROM `description`;
DELETE FROM `product_model`;
DELETE FROM `product`;

INSERT INTO product (product_name,created_by) VALUES
 ('Home Chargers','script'),
 ('Fast Chargers','script'),
 ('Ultra-Fast Chargers','script'),
 ('Buiseness Chargers','script'),
 ('Public Chargers','script'),
 ('Smart Chargers','script'),
 ('Charging Management','script');
INSERT INTO product_model (product_id,model_name,book_id,industry_report_id,specification_report_id,created_by) VALUES
 ((SELECT id FROM product WHERE product_name LIKE 'Home Chargers' limit 1),'EVBox HomeLine',1,1,1,'script'),
 ((SELECT id FROM product WHERE product_name LIKE 'Home Chargers' limit 1),'EVBox Elvi',1,1,1,'script'),
 ((SELECT id FROM product WHERE product_name LIKE 'Fast Chargers' limit 1),'EVBox Troniq',2,2,2,'script'),
 ((SELECT id FROM product WHERE product_name LIKE 'Ultra-Fast Chargers' limit 1),'EVBox UlTroniq',3,3,3,'script'),
 ((SELECT id FROM product WHERE product_name LIKE 'Buiseness Chargers' limit 1),'EVBox BusinessLine',4,4,4,'script'),
 ((SELECT id FROM product WHERE product_name LIKE 'Public Chargers' limit 1),'EVBox PublicLine',5,5,5,'script'),
 ((SELECT id FROM product WHERE product_name LIKE 'Smart Chargers' limit 1),'EV smart',6,6,6,'script'),
 ((SELECT id FROM product WHERE product_name LIKE 'Charging Management' limit 1),'Home', 7,7,7,'script'),
 ((SELECT id FROM product WHERE product_name LIKE 'Charging Management' limit 1),'Business',8,8,8,'script');
INSERT INTO description (model_id,description_line,created_by) VALUES
 ((SELECT id FROM product_model WHERE model_name LIKE 'EVBox HomeLine' limit 1),'Description-line-0','script'),
 ((SELECT id FROM product_model WHERE model_name LIKE 'EVBox Elvi' limit 1),'Description-line-1','script'),
 ((SELECT id FROM product_model WHERE model_name LIKE 'EVBox Troniq' limit 1),'Description-line-2','script'),
 ((SELECT id FROM product_model WHERE model_name LIKE 'EVBox UlTroniq' limit 1),'Description-line-3','script'),
 ((SELECT id FROM product_model WHERE model_name LIKE 'EVBox BusinessLine' limit 1),'Description-line-4','script'),
 ((SELECT id FROM product_model WHERE model_name LIKE 'EVBox PublicLine' limit 1),'Description-line-5','script'),
 ((SELECT id FROM product_model WHERE model_name LIKE 'EV smart' limit 1),'Description-line-6','script'),
 ((SELECT id FROM product_model WHERE model_name LIKE 'Home' limit 1),'Description-line-7','script'),
 ((SELECT id FROM product_model WHERE model_name LIKE 'Business' limit 1),'Description-line-8','script');


