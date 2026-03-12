import java.util.Collections;

//import javax.smartcardio.Card;

/**
 * Program pretends to sort cards, and demonstrates use of a recorder.
 *
 * @author Nicholas R. Howe
 * @version October 2021
 */
public class FakeSort {

  /** This method actually doesn't sort at all */
  public static CardPile sort(CardPile unsorted, SortRecorder record) {

    // register the starting configuration with the recorder
    record.add(unsorted);

    // Now move cards from one list to another,
    // and use the recorder to record the steps along the way.
    // In a real implementation, you would actually sort the list.
    // Here, we just move them in the same order they came in
    CardPile sorted = new CardPile();

    // Card c1 = unsorted.removeFirst();
    // Card c2 = unsorted.removeLast();

    // System.out.println(c1 + " vs. " + c2);

    // if (c1.compareTo(c2) > 0) {
    // System.out.println("C1 is bigger!");
    // } else {
    // System.out.println("C2 is bigger!");
    // }

    while (unsorted.size() > 0) {
      // move one card between piles
      sorted.add(unsorted.removeLast());

      // register the new state with the recorder:
      record.next(); // tell it this is a new step
      record.add(sorted); // the allegedly sorted pile
      record.add(unsorted); // the unsorted pile
    }

    // return the (un)sorted result here
    return sorted;
  }

  /** Starts the program running */
  public static void main(String args[]) {

    // set up a class to record and display the sorting results
    SortRecorder recorder = new SortRecorder();

    // set up the deck of cards
    Card.loadImages(recorder);
    CardPile cards = new CardPile(Card.newDeck(true), 2, 2);

    // for debugging purposes, uncomment this to
    // work with a smaller number of cards:
    // cards = cards.split(cards.get(39));

    // mix up the cards
    Collections.shuffle(cards);

    // if you want to sort in array form, use:
    Card[] card_arr = cards.toArray(new Card[0]);

    // in your program, this would be a call to a real sorting algorithm
    cards = FakeSort.sort(cards, recorder);

    // We can print out the (un)sorted result:
    System.out.println(cards);

    // make window appear showing the record
    recorder.display("Card Sort Demo: FakeSort");
  }
}