package hu.bme.aut.retelab2.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Ad {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String text;

    private Integer price;

    private String secret;

    @ElementCollection
    private List<String> tags;

    @CreationTimestamp
    private LocalDateTime creationDate;

    private LocalDateTime expirationDate;

    public void setExpirationDate(LocalDateTime expirationDate){
        this.expirationDate = expirationDate;
    }

    public LocalDateTime getExpirationDate(){
        return this.expirationDate;
    }

    public void setTags(List<String> tags){
        this.tags = tags;
    }

    public List<String> getTags(){
        return this.tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public Integer getPrice() {return price;}

    public void setPrice(Integer price) {this.price = price;}

    public void setSecret(String secret){
        this.secret = secret;
    }

    public boolean checkSecret(String secret){
        return this.secret.equals(secret);
    }

    public boolean compareSecret(Ad advert){
        return advert.checkSecret(this.secret);
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
