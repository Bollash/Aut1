package hu.bme.aut.retelab2.repository;

import hu.bme.aut.retelab2.SecretGenerator;
import hu.bme.aut.retelab2.domain.Ad;
import hu.bme.aut.retelab2.domain.Note;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class AdRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public String save(Ad advert) {
        String secret = SecretGenerator.generate();
        advert.setSecret(secret);
        em.merge(advert);
        return secret;
    }

    @Transactional
    public boolean modify(Ad advert){
        Ad original = em.find(Ad.class, advert.getId());
        if(original.compareSecret(advert)){
            em.merge(advert);
            return true;
        }else{
            return false;
        }
    }

    public List<Ad> findByTag(String tag){
        return em.createQuery("SELECT n FROM Ad n JOIN n.tags t WHERE t = LOWER(?1)", Ad.class).setParameter(1, tag).getResultList();
    }

    public List<Ad> findAll() {
        return em.createQuery("SELECT n FROM Ad n", Ad.class).getResultList();
    }

    public List<Ad> findAllBetween(int minPrice, int maxPrice){
        return em.createQuery("SELECT n FROM Ad n WHERE n.price BETWEEN ?0 AND ?1", Ad.class).setParameter(0, minPrice).setParameter(1, maxPrice).getResultList();
    }

    public Ad findById(long id) {
        return em.find(Ad.class, id);
    }


    @Transactional
    public void deleteExpiredAds() {
        List<Ad> removeList = em.createQuery("SELECT n FROM Ad n WHERE n.expirationDate <= ?1", Ad.class).setParameter(1, LocalDateTime.now()).getResultList();
        for (Ad a : removeList) {
            em.remove(a);
        }

    }

}