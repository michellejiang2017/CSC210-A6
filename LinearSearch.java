import java.util.Collections;
/**
 * Filename: LinearSearch.java
 * Author: Michelle Jiang 
 * Date: 2026-03-22
 * Program demonstrates a linear search through a pile of cards.
 */
public class LinearSearch {

  /**
   * Searches through the pile one card at a time.
   * @param cards the pile of cards to search through
   * @param target the card to search for
   * @return true as soon as you find target, false if the whole pile is checked and target is never found
   */
  public static boolean search(CardPile cards, Card target) {
    return search(cards, target, null);
  }

  /**
   * Searches through the pile one card at a time, and records the search visually.
   * @param cards the pile of cards to search through
   * @param target the card to search for
   * @param record the recorder to use to record the search visually. If null, no recording is done.
   * @return true as soon as you find target, false if the whole pile is checked and target is never found
   */
  public static boolean search(CardPile cards, Card target, SortRecorder record) {
    // ***********************************************************
    // Search through the pile one card at a time.
    // Return true as soon as you find target.
    // If the whole pile is checked and target is never found,
    // return false.
    //
    // If you are recording the search visually, take one snapshot
    // per comparison so the viewer shows the search progression.
    // ***********************************************************
    for (Card card : cards) {
      if (record != null) { 
        record.next(); 
        record.add(cards);
      }
        if (card.compareTo(target) == 0) {
          return true;
        }
    }
    return false;
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
    cards = cards.split(cards.get(39));

    // mix up the cards
    Collections.shuffle(cards);

    Card target = cards.get(0);

    // in your program, this would be a call to a real sorting algorithm
    Boolean targetFound = LinearSearch.search(cards, target, recorder);

    // We can print out the (un)sorted result:
    System.out.println(cards);

    System.out.println(targetFound);

    // make window appear showing the record
    recorder.display("Card Sort Demo: Linear Search");
  }
}
