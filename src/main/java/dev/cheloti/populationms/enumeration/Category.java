package dev.cheloti.populationms.enumeration;

public enum Category {

    DECLINING("DECLINING"), STAGNANT("STAGNANT"),GROWING("GROWING"), BOOMING("BOOMING"), INVALID("INVALID");

    private final String category;

    Category(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
