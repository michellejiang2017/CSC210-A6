import java.util.Collections;

/**
 * Program sorts cards using Selection Sort, and demonstrates use of a recorder.
 */
public class SelectionSort {
  
  /**
   * Sorts the given pile of cards using Selection Sort
   * @param unsorted the pile of cards to sort
   * @return a new pile of cards containing the same cards as unsorted, but in sorted order
   */
  public static CardPile sort(CardPile unsorted) {
  return sort(unsorted, null);
  }

  /**
   * Sorts the given pile of cards using Selection Sort, and uses the given recorder to record the steps of the algorithm.
   * @param unsorted the pile of cards to sort
   * @param record the recorder to use to record the steps of the algorithm; if null, no recording will be done
   * @return a new pile of cards containing the same cards as unsorted, but in sorted order
   */
  public static CardPile sort(CardPile unsorted, SortRecorder record) {

    CardPile sorted = new CardPile();

    if (record != null) { 
      record.add(unsorted);
    }

    while (unsorted.size() > 0) {
      Card c1 = unsorted.peekFirst();

      for (Card card : unsorted) {
        if (card.compareTo(c1) < 0) {
          c1 = card;
        }
      }
      unsorted.remove(c1);
      sorted.add(c1);
      
      if (record != null) {
        record.next();
        record.add(sorted);
        record.add(unsorted);
      }
    }

    return sorted;
  }

  /**
   * Starts the program running
   * @param args not used
   */
  public static void main(String args[]) {

    // set up a class to record and display the sorting results
    SortRecorder recorder = new SortRecorder();

    // set up the deck of cards
    Card.loadImages(recorder);
    CardPile cards = new CardPile(Card.newDeck(true), 2, 2);

    // for debugging purposes, uncomment this to
    // work with a smaller number of cards:
    //cards = cards.split(cards.get(39));

    // mix up the cards
    Collections.shuffle(cards);

    // in your program, this would be a call to a real sorting algorithm
    cards = SelectionSort.sort(cards, recorder);

    // We can print out the (un)sorted result:
    System.out.println(cards);

    // make window appear showing the record
    recorder.display("Card Sort Demo: SelectionSort");
  }
}
