package hu.bme.aut.retelab2.controller;

import hu.bme.aut.retelab2.domain.Ad;
import hu.bme.aut.retelab2.domain.Note;
import hu.bme.aut.retelab2.repository.AdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ads")
public class AdController {

    @Autowired
    private AdRepository adRepository;

    @PostMapping
    public String create(@RequestBody Ad advert) {
        advert.setId(null);
        return adRepository.save(advert);
    }

    @GetMapping
    public List<Ad> getAllBetweenPrices(@RequestParam(required = false, defaultValue = "0") String minPrice, @RequestParam(required = false, defaultValue = "10000000") String maxPrice) {
        return adRepository.findAllBetween(Integer.parseInt(minPrice), Integer.parseInt(maxPrice));
    }

    @GetMapping("/{tag}")
    public List<Ad> getAdsByTag(@PathVariable String tag){
        return adRepository.findByTag(tag);
    }

    @PutMapping
    public ResponseEntity<Ad> modifyAd(@RequestBody Ad advert){
        Ad a = adRepository.findById(advert.getId());
        if (a == null)
            return ResponseEntity.notFound().build();
        if(adRepository.modify(advert)){
            return ResponseEntity.status(HttpStatus.OK).build();
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Scheduled(fixedDelay = 6000)
    public void deleteExpiredAds(){
        adRepository.deleteExpiredAds();
    }
}