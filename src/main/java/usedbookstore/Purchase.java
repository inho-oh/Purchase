package usedbookstore;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import usedbookstore.external.Delivery;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="Purchase_table")
public class Purchase {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long bookid;
    private Long customerid;
    private Integer qty;
    private Date purchasedate;
    private Date canceldate;
    private Integer point;

    @PostPersist
    public void onPostPersist(){
        Purchased purchased = new Purchased();
        BeanUtils.copyProperties(this, purchased);
        purchased.publishAfterCommit();

    }

    @PreUpdate
    public void onPreUpdate(){
        PurchaseCanceled purchaseCanceled = new PurchaseCanceled();
        BeanUtils.copyProperties(this, purchaseCanceled);
        purchaseCanceled.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        usedbookstore.external.Delivery delivery = new usedbookstore.external.Delivery();
     //   delivery = PurchaseApplication.applicationContext.getBean(usedbookstore.external.DeliveryService.class)
    //            .read(purchaseCanceled.getId(), delivery);
        //delivery.setId(purchaseCanceled.getId());
        delivery.setStatus("배송취소");
        delivery.setPurchaseid(purchaseCanceled.getId());
        PurchaseApplication.applicationContext.getBean(usedbookstore.external.DeliveryService.class)
            .cancel(purchaseCanceled.getId(),delivery);


    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getBookid() {
        return bookid;
    }

    public void setBookid(Long bookid) {
        this.bookid = bookid;
    }
    public Long getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Long customerid) {
        this.customerid = customerid;
    }
    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
    public Date getPurchasedate() {
        return purchasedate;
    }

    public void setPurchasedate(Date purchasedate) {
        this.purchasedate = purchasedate;
    }
    public Date getCanceldate() {
        return canceldate;
    }

    public void setCanceldate(Date canceldate) {
        this.canceldate = canceldate;
    }
    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }




}
