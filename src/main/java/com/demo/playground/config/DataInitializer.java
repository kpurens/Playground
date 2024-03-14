package com.demo.playground.config;

import com.demo.playground.entity.Attraction;
import com.demo.playground.entity.Kid;
import com.demo.playground.entity.PlaySite;
import com.demo.playground.repository.AttractionRepository;
import com.demo.playground.repository.KidRepository;
import com.demo.playground.repository.PlaySiteRepository;
import com.demo.playground.service.PlaySiteService;
import com.demo.playground.type.AttractionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AttractionRepository attractionRepository;
    private final KidRepository kidRepository;
    private final PlaySiteRepository playSiteRepository;

    private final PlaySiteService playSiteService;

    @Autowired
    public DataInitializer(
            AttractionRepository attractionRepository,
            KidRepository kidRepository,
            PlaySiteRepository playSiteRepository,
            PlaySiteService playSiteService) {
        this.attractionRepository = attractionRepository;
        this.kidRepository = kidRepository;
        this.playSiteRepository = playSiteRepository;
        this.playSiteService = playSiteService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (playSiteRepository.count() == 0) {
            populateDemoEntities();
        }
    }

    private void populateDemoEntities() {
        Attraction attraction1 = saveAttraction(33, 45, 1.0, AttractionType.SWING);
        Attraction attraction2 = saveAttraction(31, 41, 0.9, AttractionType.BALL_PIT);
        Attraction attraction3 = saveAttraction(37, 42, 0.5, AttractionType.SLIDE);
        Attraction attraction4 = saveAttraction(35, 43, 1.0, AttractionType.SWING);
        Attraction attraction5 = saveAttraction(30, 40, 0.0, AttractionType.TRAMPOLINE);

        Kid kid1 = saveKid("John", 11, -1, true);
        Kid kid2 = saveKid("Jack", 12, -1, false);
        Kid kid3 = saveKid("Jill", 13, -1, true);
        Kid kid4 = saveKid("Jeep", 13, -1, true);
        Kid kid5 = saveKid("James", 14, -1, false);
        Kid kid6 = saveKid("Jasper", 15, -1, true);
        Kid kid7 = saveKid("Jenkins", 16, -1, true);
        Kid kid8 = saveKid("Jackson", 17, -1, false);
        Kid kid9 = saveKid("Jakarta", 18, -1, true);

        PlaySite playSite1 = savePlaySite();
        PlaySite playSite2 = savePlaySite();
        PlaySite playSite3 = savePlaySite();
        PlaySite playSite4 = savePlaySite();
        PlaySite playSite5 = savePlaySite();

        playSiteService.addAttractionToPlaySite(playSite1.getId(), attraction1.getId());
        playSiteService.addAttractionToPlaySite(playSite2.getId(), attraction1.getId());
        playSiteService.addAttractionToPlaySite(playSite2.getId(), attraction2.getId());
        playSiteService.addAttractionToPlaySite(playSite3.getId(), attraction4.getId());
        playSiteService.addAttractionToPlaySite(playSite4.getId(), attraction5.getId());
        playSiteService.addAttractionToPlaySite(playSite5.getId(), attraction1.getId());

        playSiteService.addKidToPlaySite(playSite1.getId(), kid1.getId());
        playSiteService.addKidToPlaySite(playSite1.getId(), kid2.getId());
        playSiteService.addKidToPlaySite(playSite2.getId(), kid3.getId());
        playSiteService.addKidToPlaySite(playSite2.getId(), kid4.getId());
        playSiteService.addKidToPlaySite(playSite2.getId(), kid5.getId());
        playSiteService.addKidToPlaySite(playSite2.getId(), kid6.getId());
    }

    private Attraction saveAttraction(double lat, double lng, double durability, AttractionType type) {
        Attraction attraction = Attraction.builder()
                .latitude(lat)
                .longitude(lng)
                .durability(durability)
                .type(type)
                .build();
        return attractionRepository.save(attraction);
    }

    private Kid saveKid(String name, int age, int ticket, boolean acceptQueue) {
        Kid kid = Kid.builder()
                .name(name)
                .age(age)
                .ticket(ticket)
                .acceptQueue(acceptQueue)
                .build();
        return kidRepository.save(kid);
    }

    private PlaySite savePlaySite() {
        PlaySite playSite = PlaySite.builder()
                .build();
        return playSiteRepository.save(playSite);
    }
}
