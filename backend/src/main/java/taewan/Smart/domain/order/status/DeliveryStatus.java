package taewan.Smart.domain.order.status;

public enum DeliveryStatus {
    WAIT("준비중"),
    ARRIVAL("배송완료"),
    CANCEL("취소"),
    REFUND("환불");

    private final String title;

    DeliveryStatus(String title) { this.title = title; }
    public String title() { return title; }
}
