import java.util.ArrayDeque;
import java.util.Collections;

/**
 * Filename: MergeSort
 * Author: Michelle Jiang 
 * Date: 2026-03-24
 * Program demonstrates a merge sort with a pile of cards. 
 */
public class MergeSort {
  /**
   * Sorts the given pile of cards using Merge Sort
   * @param unsorted the pile of cards to sort
   * @return a new pile of cards containing the same cards as unsorted, but in sorted order
   */
  public static CardPile sort(CardPile unsorted) {
    return sort(unsorted, null);
  }

  /**
   * Sorts the given pile of cards using Merge Sort, and uses the given recorder to record the steps of the algorithm.
   * @param unsorted the pile of cards to sort
   * @param record the recorder to use to record the steps of the algorithm; if null, no recording will be done
   * @return a new pile of cards containing the same cards as unsorted, but in sorted order
   */
  public static CardPile sort(CardPile unsorted, SortRecorder record) {
    
    ArrayDeque<CardPile> queue = new ArrayDeque<CardPile>();
  
    if (record != null) { 
      record.add(unsorted);
    }
    // ***********************************************************
    // Here is where you'll do the "work" of MergeSort:
    //   - Use queue to store the intermediate piles
    //   - Don't forget to register the new state with the
    //     recorder after each merge step:
    //        record.next();        // tell it this is a new step
    //        for (CardPile pile: queue) { // add all piles
    //           record.add(pile);
    //        }
    // ***********************************************************

    for (Card card : unsorted) {
      CardPile singleton = new CardPile(); 
      singleton.add(card); 
      queue.add(singleton);
    }

    while (queue.size() > 1) { 
      CardPile first = queue.removeFirst();
      CardPile second = queue.removeFirst(); 
      queue.add(mergeLists(first, second));
      if (record != null) {
        record.next();
        for (CardPile cardPile : queue) {
          record.add(cardPile); 
        }
      }
    }
    // return the sorted result here
    return queue.remove();
  }

  /**
   * Merges two sorted lists together 
   * @param first the first list to be merged
   * @param second the second list to be merged
   * @return the sorted list composed of both lists 
   */
  public static CardPile mergeLists(CardPile first, CardPile second) { 
    CardPile merged = new CardPile(); 
    while (first.size() > 0 && second.size() > 0 ) { 
      
    if (first.peekFirst().compareTo(second.peekFirst()) > 0) { 
        merged.add(second.removeFirst()); 
      } else { 
        merged.add(first.removeFirst()); 
      }
      
    }

    if (first.size() == 0) { 
      merged.append(second);
    }
    if (second.size() == 0) { 
      merged.append(first); 
    }
    return merged; 
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
    cards = MergeSort.sort(cards, recorder);

    // We can print out the (un)sorted result:
    System.out.println(cards);

    // make window appear showing the record
    recorder.display("Card Sort Demo: MergeSort");
  }
}
