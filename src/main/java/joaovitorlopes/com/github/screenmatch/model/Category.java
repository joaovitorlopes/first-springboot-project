package joaovitorlopes.com.github.screenmatch.model;

public enum Category {
    ACTION("Action"),
    ADVENTURE("Adventure"),
    HORROR("Horror"),
    ANIMATION("Animation"),
    CLASSIC("Classic"),
    COMEDY("Comedy"),
    DRAMA("Drama"),
    ROMANCE("Romance"),
    MUSICAL("Musical"),
    THRILLERS("Thrillers"),
    DOCUMENTARIES("Documentaries"),
    WAR("War"),
    WESTERN("Western"),
    FOREIGN("Foreign"),
    CRIME("Crime");

    private String OmdbCategory;

    Category(String OmdbCategory) {
        this.OmdbCategory = OmdbCategory;
    }

    public static Category fromString(String text) {
        for (Category category : Category.values()) {
            if (category.OmdbCategory.equalsIgnoreCase(text)) {
                return category;
            }
        }
        throw new IllegalArgumentException("No categories found for the following string: " + text);
    }
}
