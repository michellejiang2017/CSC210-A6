/////////////////////////////
// DO NOT MODIFY THIS FILE //
/////////////////////////////

import java.lang.reflect.Method;

/**
 * Command-line harness for timing search and sorting algorithms without graphics.
 */
public class AlgorithmTimingRunner {

  /**
   * Runs a named algorithm on a random pile of cards.
   *
   * Usage: java AlgorithmTimingRunner <linear|selection|insertion|merge|quick|fake> <count>
   *
   * @param args command-line arguments
   * @throws Exception if reflection fails
   */
  public static void main(String[] args) throws Exception {
    if (args.length < 2) {
      System.err.println("Usage: java AlgorithmTimingRunner <linear|selection|insertion|merge|quick|fake> <count>");
      return;
    }

    String algorithm = args[0].trim().toLowerCase();
    int count = Integer.parseInt(args[1]);
    CardPile cards = randomPile(count);

    long start = System.nanoTime();
    runAlgorithm(algorithm, cards);
    long elapsed = System.nanoTime() - start;

    System.out.printf("%s on %d cards took %.3f ms%n",
        algorithm, count, elapsed / 1_000_000.0);
  }

  /** Builds a random pile of cards, allowing repeats. */
  private static CardPile randomPile(int count) {
    Card[] deck = Card.newDeck(true);
    CardPile cards = new CardPile();
    for (int i = 0; i < count; i++) {
      cards.add(deck[(int) (52 * Math.random())]);
    }
    return cards;
  }

  /** Invokes the chosen algorithm via reflection. */
  private static void runAlgorithm(String algorithm, CardPile cards) throws Exception {
    if (algorithm.equals("linear")) {
      runLinearSearch(cards);
      return;
    }
    runSort(algorithm, cards);
  }

  /** Times linear search by looking for the last card in the pile. */
  private static void runLinearSearch(CardPile cards) throws Exception {
    Class<?> cls = Class.forName("LinearSearch");
    Card target = cards.peekLast();

    try {
      Method noRecorder = cls.getMethod("search", CardPile.class, Card.class);
      noRecorder.invoke(null, cards, target);
    } catch (NoSuchMethodException ignored) {
      Method withRecorder = cls.getMethod("search", CardPile.class, Card.class, SortRecorder.class);
      withRecorder.invoke(null, cards, target, null);
    }
  }

  /** Invokes the chosen sort via reflection so students can implement either merge or quick. */
  private static void runSort(String algorithm, CardPile cards) throws Exception {
    String className;
    switch (algorithm) {
      case "selection":
        className = "SelectionSort";
        break;
      case "insertion":
        className = "InsertionSort";
        break;
      case "merge":
        className = "MergeSort";
        break;
      case "quick":
      case "quicksort":
        className = "Quicksort";
        break;
      case "fake":
        className = "FakeSort";
        break;
      default:
        throw new IllegalArgumentException("Unknown algorithm: " + algorithm);
    }

    Class<?> cls = Class.forName(className);
    try {
      Method noRecorder = cls.getMethod("sort", CardPile.class);
      noRecorder.invoke(null, cards);
    } catch (NoSuchMethodException ignored) {
      Method withRecorder = cls.getMethod("sort", CardPile.class, SortRecorder.class);
      SortRecorder recorder = algorithm.equals("fake") ? new SortRecorder() : null;
      withRecorder.invoke(null, cards, recorder);
    }
  }
}
