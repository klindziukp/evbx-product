INSERT INTO product (id, product_name,created_at,updated_at,created_by) VALUES
 (100,'Product-0','2020-02-29 12:43:48','2020-02-29 12:43:48','script-0'),
 (101,'Product-1','2020-02-29 12:43:48','2020-02-29 12:43:48','script-1'),
 (102,'Product-2','2020-02-29 12:43:48','2020-02-29 12:43:48','script-2');
INSERT INTO product_model (id,product_id,model_name,book_id,industry_report_id,specification_report_id, created_at,updated_at,created_by) VALUES
 (100,(SELECT id FROM product WHERE product_name LIKE 'Product-0' limit 1),'Product-model-0',1,1,1,'2020-02-29 12:43:48','2020-02-29 12:43:48','script-0'),
 (101,(SELECT id FROM product WHERE product_name LIKE 'Product-1' limit 1),'Product-model-1',2,2,2,'2020-02-29 12:43:48','2020-02-29 12:43:48','script-1'),
 (102,(SELECT id FROM product WHERE product_name LIKE 'Product-2' limit 1),'Product-model-2',3,3,3,'2020-02-29 12:43:48','2020-02-29 12:43:48','script-2');
INSERT INTO description (id,model_id,description_line,created_at,updated_at,created_by) VALUES
 (100,(SELECT id FROM product_model WHERE model_name LIKE 'Product-model-0' limit 1),'Description-line-0','2020-02-29 12:43:48','2020-02-29 12:43:48','script-0'),
 (101,(SELECT id FROM product_model WHERE model_name LIKE 'Product-model-1' limit 1),'Description-line-1','2020-02-29 12:43:48','2020-02-29 12:43:48','script-1'),
 (102,(SELECT id FROM product_model WHERE model_name LIKE 'Product-model-2' limit 1),'Description-line-2','2020-02-29 12:43:48','2020-02-29 12:43:48','script-2');


