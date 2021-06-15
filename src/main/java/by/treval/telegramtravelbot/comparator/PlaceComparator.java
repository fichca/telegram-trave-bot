package by.treval.telegramtravelbot.comparator;

import by.treval.telegramtravelbot.entity.Place;

import java.util.Comparator;

public class PlaceComparator implements Comparator<Place> {
    @Override
    public int compare(Place o1, Place o2) {
        return o2.getRating() - o1.getRating();
    }
}
