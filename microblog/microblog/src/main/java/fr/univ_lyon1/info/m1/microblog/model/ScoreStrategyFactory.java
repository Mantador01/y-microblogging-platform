package fr.univ_lyon1.info.m1.microblog.model;

/**
 * Factory class to create instances of scoring strategies.
 * This allows for dynamic addition of new strategies without modifying existing code.
 */
public final class ScoreStrategyFactory {

    /**
     * Private constructor to prevent instantiation of the utility class.
     */
    private ScoreStrategyFactory() {
        // Prevent instantiation
    }

    /**
     * Returns a scoring strategy based on the provided strategy type.
     *
     * @param strategyType the type of scoring strategy (e.g., "bookmark", "recent", "length")
     * @return a {@code ScoreStrategy} implementation
     * @throws IllegalArgumentException if an unknown strategy type is provided
     */
    public static ScoreStrategy getStrategy(final String strategyType) {
        switch (strategyType) {
            case "bookmark":
                return new BookmarkScoring();
            case "recent":
                return new RecentMessageBonusScoring();
            case "length":
                return new LengthBasedScoring();
            default:
                throw new IllegalArgumentException("Unknown scoring strategy: " + strategyType);
        }
    }
}
