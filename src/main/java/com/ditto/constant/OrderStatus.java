package com.ditto.constant;

public enum OrderStatus {
    //구매상태. (입금대기, 주문완료, 배송준비중, 배송중, 배송완료(자동구매 카운트 시작), 구매확정)
    //주문 취소
    WAITDEPOSIT, COMPLETEORDER, READYDELIVERY, ONDELIVERY, DELIVERED, CONFIRM,
    CANCEL

}
