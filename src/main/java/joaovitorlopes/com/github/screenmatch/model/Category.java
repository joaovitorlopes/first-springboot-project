package joaovitorlopes.com.github.screenmatch.model;

public enum Category {
    ACTION("Action", "Ação"),
    ADVENTURE("Adventure", "Aventura"),
    HORROR("Horror", "Terror"),
    ANIMATION("Animation", "Animação"),
    CLASSIC("Classic", "Clássico"),
    COMEDY("Comedy", "Comédia"),
    DRAMA("Drama", "Drama"),
    ROMANCE("Romance", "Romance"),
    MUSICAL("Musical", "Musical"),
    THRILLERS("Thrillers", "Suspense"),
    DOCUMENTARIES("Documentaries", "Documentários"),
    WAR("War", "Guerra"),
    WESTERN("Western", "Faroeste"),
    FOREIGN("Foreign", "Estrangeiro"),
    CRIME("Crime", "Crime");

    private String omdbCategory;
    private String portugueseCategory;

    Category(String OmdbCategory, String portugueseCategory) {
        this.omdbCategory = OmdbCategory;
        this.portugueseCategory = portugueseCategory;
    }

    public static Category fromString(String text) {
        for (Category category : Category.values()) {
            if (category.omdbCategory.equalsIgnoreCase(text)) {
                return category;
            }
        }
        throw new IllegalArgumentException("No categories found for the following string: " + text);
    }

    public static Category fromPortuguese(String text) {
        for (Category category : Category.values()) {
            if (category.portugueseCategory.equalsIgnoreCase(text) || category.omdbCategory.equalsIgnoreCase(text)) {
                return category;
            }
        }
        throw new IllegalArgumentException("No categories found for the following string: " + text);
    }
}
