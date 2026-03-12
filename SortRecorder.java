/////////////////////////////
// DO NOT MODIFY THIS FILE //
/////////////////////////////

import java.util.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;

/**
 *  Program keeps track of sorting activities and then displays them
 *
 *  @author Nicholas R. Howe
 *  @version 28 February 2011
 */
public class SortRecorder extends JComponent implements ChangeListener {
  /** Height of one recorded state in the saved contact sheet */
  private static final int STATE_HEIGHT = 100;

  /** Extra space for a label between saved states */
  private static final int ROW_GAP = 18;

  /** Keeps track of moves */
  private LinkedList<LinkedList<CardPile>> record;

  /** Keeps track of where new piles go */
  int offset;

  /** Keeps track of how big window needs to be */
  int maxoffset;

  /** Keeps track of current state to display */
  int index;

  /** Constructor */
  public SortRecorder() {
    record = new LinkedList<LinkedList<CardPile>>();
    record.add(new LinkedList<CardPile>());
    offset = 2;
    index = 0;
  }

  /** Adds a pile to the current record */
  public void add(CardPile pile) {
    LinkedList<CardPile> piles = record.removeLast();
    CardPile cpile = new CardPile(pile);  // make a copy
    cpile.setX(offset);
    cpile.setY(2);
    piles.add(cpile);
    record.add(piles);
    offset += 72+12*pile.size();
    if (offset > maxoffset) {
      maxoffset = offset;
    }
  }

  /** Adds a card in a newly created singleton pile */
  public void add(Card c) {
    CardPile pile = new CardPile(2,2);
    pile.add(c);
    add(pile);
  }

  /** Adds an array to the current record */
  public void add(Card[] cards) {
    LinkedList<CardPile> piles = record.removeLast();
    CardPile cpile = new CardPile(cards,offset,2);
    piles.add(cpile);
    record.add(piles);
    offset += 72+12*cards.length;
    if (offset > maxoffset) {
      maxoffset = offset;
    }
  }

  /** Moves on to the next record */
  public void next() {
    record.add(new LinkedList<CardPile>());
    offset = 0;
  }

  /** Creates a window to put the display into */
  public void display(String title) {
    finalizeRecord();
    saveContactSheet(title);

    if (GraphicsEnvironment.isHeadless()) {
      return;
    }

    // Make sure we have nice window decorations.
    JFrame.setDefaultLookAndFeelDecorated(true);

    // Create and set up the window.
    JFrame frame = new JFrame(title);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Add components
    JSlider slider = new JSlider(JSlider.HORIZONTAL,1,record.size(),1);
    slider.setMajorTickSpacing(record.size());
    slider.setMinorTickSpacing(1);
    slider.setPaintTicks(true);
    slider.setSnapToTicks(true);
    slider.addChangeListener(this);
    frame.getContentPane().add(this);
    frame.getContentPane().add(slider,BorderLayout.SOUTH);

    // Display the window.
    frame.pack();
    frame.setVisible(true);
  }

  public void stateChanged(ChangeEvent e) {
    JSlider source = (JSlider)e.getSource();
    index = (int)source.getValue()-1;
    repaint();
  }

  public void paintComponent(Graphics g) {
    paintState(g,index,0,false);
  }

  public Dimension getMinimumSize() {
    return new Dimension(canvasWidth(),STATE_HEIGHT);
  }

  public Dimension getPreferredSize() {
    return new Dimension(canvasWidth(),STATE_HEIGHT);
  }

  /** Removes any trailing empty state before display or export. */
  private void finalizeRecord() {
    if ((record.size() > 1) && (record.getLast().size()==0)) {
      record.removeLast();
    }
  }

  /** Paints a single recorded state, optionally with a step label. */
  private void paintState(Graphics g, int stateIndex, int yOffset, boolean includeLabel) {
    Graphics2D g2 = (Graphics2D) g.create();
    int top = yOffset;
    if (includeLabel) {
      g2.setColor(Color.BLACK);
      g2.drawString("Step " + stateIndex, 4, top + 12);
      top += ROW_GAP;
    }

    g2.translate(0, top);
    g2.setColor(Color.GREEN.darker());
    g2.fillRect(0,0,canvasWidth(),STATE_HEIGHT);
    LinkedList<CardPile> piles = record.get(stateIndex);
    for (CardPile pile: piles) {
      pile.draw(g2);
    }
    g2.dispose();
  }

  /** Saves all recorded states into one PNG contact sheet. */
  private void saveContactSheet(String title) {
    int rows = Math.max(1, record.size());
    int width = canvasWidth();
    int height = rows * (STATE_HEIGHT + ROW_GAP);
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2 = image.createGraphics();

    g2.setColor(Color.WHITE);
    g2.fillRect(0,0,width,height);
    for (int i = 0; i < rows; i++) {
      int y = i * (STATE_HEIGHT + ROW_GAP);
      paintState(g2, i, y, true);
    }
    g2.dispose();

    try {
      File output = new File(safeFilename(title) + ".png");
      ImageIO.write(image, "png", output);
      System.out.println("Saved sort visualization to " + output.getName());
    } catch (IOException e) {
      System.err.println("Could not save sort visualization: " + e.getMessage());
    }
  }

  /** Computes the drawing width used for both display and export. */
  private int canvasWidth() {
    return Math.max(100,Math.min(1000,maxoffset));
  }

  /** Converts a window title into a safe output filename stem. */
  private String safeFilename(String title) {
    String cleaned = title.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]+","_");
    cleaned = cleaned.replaceAll("^_+|_+$","");
    if (cleaned.length()==0) {
      return "sort_visualization";
    }
    return cleaned;
  }
}
