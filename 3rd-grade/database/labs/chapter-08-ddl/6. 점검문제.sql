-- 점검문제1
ALTER TABLE 제품 ADD CHECK(재고 >= 0);
ALTER TABLE 제품 ADD CONSTRAINT ck_제품_재고 CHECK(재고 >= 0);

-- 점검문제2
ALTER TABLE 제품 ADD 재고금액 INT AS (단가 * 재고) STORED;

-- 점검문제3
ALTER TABLE 주문세부 ADD FOREIGN KEY(제품번호) REFERENCES 제품(제품번호) ON DELETE CASCADE;


